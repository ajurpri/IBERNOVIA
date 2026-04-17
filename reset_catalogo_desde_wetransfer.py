import argparse
import base64
import json
import os
import re
import shutil
import unicodedata
from collections import defaultdict
from datetime import datetime, date
from pathlib import Path

import pymysql


WORKSPACE_ROOT = Path(__file__).resolve().parent
DEFAULT_SOURCE_ROOT = WORKSPACE_ROOT / 'wetransfer_fotos-y-arbol-web_2026-04-16_1721' / 'FOTOS WEB'
DEFAULT_DEST_ROOT = WORKSPACE_ROOT / 'ibernovias' / 'front-end' / 'public' / 'images' / 'catalogo'


IMAGE_EXTS = {'.jpg', '.jpeg', '.png', '.webp', '.gif', '.bmp', '.tif', '.tiff'}


FAMILY_DISPLAY = {
    'NOVIA': 'Novia',
    'NOVIO': 'Novio',
    'FIESTA': 'Fiesta',
    'COMUNION': 'Comunión',
    'ARRAS': 'Arras',
}


def _json_default(o):
    if isinstance(o, (datetime, date)):
        return o.isoformat()
    if isinstance(o, (bytes, bytearray)):
        return base64.b64encode(o).decode('ascii')
    return str(o)


def strip_accents(s: str) -> str:
    return ''.join(
        c for c in unicodedata.normalize('NFKD', s)
        if not unicodedata.combining(c)
    )


def slugify_segment(s: str) -> str:
    s = s.strip()
    s = strip_accents(s)
    s = s.lower()
    s = re.sub(r'[^a-z0-9]+', '-', s)
    s = re.sub(r'-{2,}', '-', s).strip('-')
    return s or 'item'


def normalize_ref(stem: str) -> str:
    # Para que el buscador funcione por referencia, normalizamos a algo consistente tipo "20-guantes"
    s = stem.strip()
    s = strip_accents(s)
    s = s.lower()
    s = re.sub(r'\s+', '-', s)
    s = re.sub(r'-{2,}', '-', s)
    return s


def title_case_es(s: str) -> str:
    # Title-case simple con stopwords en minúscula ("de", "y", ...), suficiente para UI.
    s = s.strip().lower()
    if not s:
        return s

    stop = {'y', 'de', 'del', 'la', 'las', 'el', 'los', 'a', 'al'}
    words = s.split()
    out = []
    for i, w in enumerate(words):
        if i > 0 and w in stop:
            out.append(w)
        else:
            out.append(w[:1].upper() + w[1:])
    return ' '.join(out)


def singularize_es(word: str) -> str:
    w = word.strip()
    wl = w.lower()
    if len(w) <= 3:
        return w
    if wl.endswith('es') and len(w) > 4:
        # "Broches" -> "Broche", "Pendientes" -> "Pendiente" (quita solo la 's')
        if wl.endswith('ches') or wl.endswith('entes') or wl.endswith('ones') or wl.endswith('ares'):
            return w[:-1]
        return w[:-2]
    if wl.endswith('s'):
        return w[:-1]
    return w


def normalize_category(family_folder: str, category_folder: str) -> str:
    raw = category_folder.strip()
    raw_up = re.sub(r'\s+', ' ', raw).upper()

    # Normalizaciones comunes de las carpetas del WeTransfer
    replacements = [
        (' DE NOVIA', ''),
        (' DE FIESTA', ''),
        (' DE COMUNION', ''),
        (' DE CABALLERO', ''),
        (' COMUNION', ''),
        (' NOVIA', ''),
        (' FIESTA', ''),
    ]
    for a, b in replacements:
        raw_up = raw_up.replace(a, b)

    raw_up = raw_up.replace('PAÑUELOS', 'PANUELOS')  # para evitar problemas sin acentos
    raw_up = raw_up.replace('PULSERAS DE AZAHAR', 'PULSERAS AZAHAR')
    raw_up = raw_up.replace('DIADEMAS CORONAS', 'DIADEMAS Y CORONAS')

    # Volvemos a formato visible
    visible = title_case_es(raw_up.replace('PANUELOS', 'Pañuelos').lower())

    # Ajustes puntuales para que coincida con el Word del cliente
    if visible.lower() == 'can can':
        visible = 'Can Can'

    return visible


def iter_images(source_root: Path):
    for path in source_root.rglob('*'):
        if not path.is_file():
            continue
        if path.name.lower().endswith('.docx'):
            continue
        if path.suffix.lower() not in IMAGE_EXTS:
            continue
        yield path


def connect_mysql():
    host = os.getenv('DB_HOST', '127.0.0.1')
    port = int(os.getenv('DB_PORT', '3306'))
    user = os.getenv('DB_USERNAME', 'root')
    password = os.getenv('DB_PASSWORD', 'toor')
    database = os.getenv('DB_NAME', 'ibernovia')

    return pymysql.connect(
        host=host,
        port=port,
        user=user,
        password=password,
        database=database,
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor,
        autocommit=False,
    )


def main():
    parser = argparse.ArgumentParser(description='Resetea productos e importa catálogo desde la carpeta de WeTransfer.')
    parser.add_argument('--source', default=str(DEFAULT_SOURCE_ROOT), help='Ruta a la carpeta "FOTOS WEB" del cliente')
    parser.add_argument('--dest', default=str(DEFAULT_DEST_ROOT), help='Destino dentro de front-end/public/images')
    parser.add_argument('--apply', action='store_true', help='Aplicar cambios en BD (borra e inserta)')
    parser.add_argument('--copy-images', action='store_true', help='Copiar imágenes al destino y ajustar ruta de imagen')
    parser.add_argument('--optimize-images', action='store_true', help='Convertir a .webp y redimensionar para web (mejora velocidad)')
    args = parser.parse_args()

    source_root = Path(args.source)
    dest_root = Path(args.dest)

    if not source_root.exists():
        raise SystemExit(f'No existe source: {source_root}')

    images = list(iter_images(source_root))
    if not images:
        raise SystemExit(f'No se encontraron imágenes en: {source_root}')

    plan = []
    counts = defaultdict(int)

    for img in images:
        rel = img.relative_to(source_root)
        parts = rel.parts
        if len(parts) < 2:
            # imagen suelta: la ignoramos para no inventar jerarquía
            continue

        family_folder = parts[0]
        category_folder = parts[1]

        family_key = family_folder.strip().upper()
        family_display = FAMILY_DISPLAY.get(family_key, title_case_es(family_folder))
        category_display = normalize_category(family_folder, category_folder)

        ref_norm = normalize_ref(img.stem)
        categoria_singular = singularize_es(category_display)
        nombre = f'{categoria_singular} de {family_display} {ref_norm}'

        family_slug = slugify_segment(family_display)
        category_slug = slugify_segment(category_display)
        file_slug = slugify_segment(ref_norm)
        ext = '.webp' if args.optimize_images else img.suffix.lower()

        rel_dest = Path(family_slug) / category_slug / f'{file_slug}{ext}'
        imagen_url = f'/images/catalogo/{rel_dest.as_posix()}'

        plan.append({
            'source': str(img),
            'family': family_display,
            'category': category_display,
            'ref': ref_norm,
            'name': nombre,
            'imagen_url': imagen_url,
            'dest_rel': str(rel_dest),
        })

        counts[(family_display, category_display)] += 1

    plan.sort(key=lambda x: (x['family'], x['category'], x['ref']))

    print(f'📦 Imágenes detectadas: {len(images)}')
    print(f'🧾 Productos planificados: {len(plan)}')

    for (fam, cat), n in sorted(counts.items(), key=lambda t: (t[0][0], t[0][1])):
        print(f'  - {fam} / {cat}: {n}')

    if not args.apply and not args.copy_images:
        print('\n(Preview) No se ha aplicado nada. Usa --apply y/o --copy-images.')
        return

    # Copia de imágenes (no toca el original)
    if args.copy_images:
        dest_root.mkdir(parents=True, exist_ok=True)
        used_paths = set()

        for item in plan:
            src = Path(item['source'])
            rel_dest = Path(item['dest_rel'])
            dest = dest_root / rel_dest

            # Evitar colisiones si dos ficheros generan el mismo slug
            if dest.as_posix() in used_paths or dest.exists():
                base = dest.with_suffix('')
                ext = dest.suffix
                k = 2
                while True:
                    candidate = Path(str(base) + f'_{k}' + ext)
                    if not candidate.exists() and candidate.as_posix() not in used_paths:
                        dest = candidate
                        # Actualizar imagen_url acorde al renombrado
                        rel = dest.relative_to(dest_root)
                        item['imagen_url'] = f"/images/catalogo/{rel.as_posix()}"
                        break
                    k += 1
            used_paths.add(dest.as_posix())

            dest.parent.mkdir(parents=True, exist_ok=True)
            if args.optimize_images:
                try:
                    from PIL import Image, ImageOps
                except ImportError as e:
                    raise SystemExit('Falta Pillow. Instala con: pip install pillow') from e

                with Image.open(src) as im:
                    im = ImageOps.exif_transpose(im)
                    if im.mode != 'RGB':
                        im = im.convert('RGB')
                    im.thumbnail((1200, 1200), Image.Resampling.LANCZOS)
                    im.save(dest, format='WEBP', quality=80, method=6)
            else:
                shutil.copy2(src, dest)

        print(f'🖼️  Copiadas {len(plan)} imágenes a: {dest_root}')

    # Cambios en BD
    if args.apply:
        conn = connect_mysql()
        try:
            with conn.cursor() as cursor:
                cursor.execute('SELECT * FROM productos')
                existing = cursor.fetchall()

            backup_path = WORKSPACE_ROOT / f'productos_backup_{datetime.now().strftime("%Y%m%d_%H%M%S")}.json'
            backup_path.write_text(
                json.dumps(existing, ensure_ascii=False, indent=2, default=_json_default),
                encoding='utf-8',
            )
            print(f'🧷 Backup guardado: {backup_path.name} (rows={len(existing)})')

            with conn.cursor() as cursor:
                cursor.execute('DELETE FROM productos')

                insert_sql = (
                    'INSERT INTO productos '
                    '(nombre, descripcion, precio, imagen, familia, categoria, stock, activo, created_at) '
                    'VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)'
                )

                now = datetime.now()
                for item in plan:
                    cursor.execute(
                        insert_sql,
                        (
                            item['name'],
                            '',
                            0.0,
                            item['imagen_url'],
                            item['family'],
                            item['category'],
                            0,
                            True,
                            now,
                        ),
                    )

            conn.commit()
            print(f'✅ BD actualizada. Insertados: {len(plan)}')

        except Exception:
            conn.rollback()
            raise
        finally:
            conn.close()


if __name__ == '__main__':
    main()
