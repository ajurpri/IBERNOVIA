from pathlib import Path
p=Path('scratch/ibernovia-review/preview.py');s=p.read_text(encoding='utf-8').replace('from urllib.parse import urlsplit, unquote','from urllib.parse import urlsplit, unquote, parse_qs').replace("frame-src 'none'","frame-src 'self'")
s=s.replace("        if path.startswith('/api/'):",'''        if path == '/__qa/mobile':
            view=parse_qs(urlsplit(self.path).query).get('view',['home'])[0]
            target={'home':'/','catalog':'/tienda','product':'/producto/639'}.get(view,'/')
            html=f'<html lang="es"><meta charset="utf-8"><title>Revisión móvil Ibernovia</title><style>body{{margin:0;padding:16px;background:#e9e9e9;font:14px sans-serif}}nav{{text-align:center;margin-bottom:16px}}a{{margin:0 10px;color:#333}}iframe{{display:block;width:390px;height:844px;max-width:100%;border:0;margin:auto;background:white}}</style><nav>Vista de prueba 390 × 844 · <a href="?view=home">Inicio</a><a href="?view=catalog">Catálogo</a><a href="?view=product">Ficha</a></nav><iframe title="Vista móvil de Ibernovia" src="{target}"></iframe></html>'
            return self.respond(html.encode(),'text/html; charset=utf-8')
        if path.startswith('/api/'):''');p.write_text(s,encoding='utf-8')
