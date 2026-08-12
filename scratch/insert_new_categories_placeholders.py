import pymysql
from datetime import datetime

# Database connection settings
db_config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': 'toor',
    'database': 'ibernovia',
    'charset': 'utf8mb4'
}

new_categories = [
    'Cordones',
    'Medallas',
    'Libritos',
    'Cinturones',
    'Gemelos',
    'Pines'
]

family = 'Comunión'

# Placeholder configurations for realistic mock images (portrait style 400x600)
placeholders = {
    'Cordones': {
        'nombre': 'Cordón de Comunión Muestra',
        'desc': 'Producto de muestra para la nueva categoría de Cordones de Comunión. Edita este producto en el panel de administración para actualizar su nombre, descripción, precio y fotos reales.',
        'img': 'https://images.unsplash.com/photo-1602751584552-8ba73aad10e1?w=400&h=600&fit=crop'
    },
    'Medallas': {
        'nombre': 'Medalla de Comunión Muestra',
        'desc': 'Producto de muestra para la nueva categoría de Medallas de Comunión. Edita este producto en el panel de administración para actualizar su nombre, descripción, precio y fotos reales.',
        'img': 'https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?w=400&h=600&fit=crop'
    },
    'Libritos': {
        'nombre': 'Librito de Comunión Muestra',
        'desc': 'Producto de muestra para la nueva categoría de Libritos de Comunión. Edita este producto en el panel de administración para actualizar su nombre, descripción, precio y fotos reales.',
        'img': 'https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400&h=600&fit=crop'
    },
    'Cinturones': {
        'nombre': 'Cinturón de Comunión Muestra',
        'desc': 'Producto de muestra para la nueva categoría de Cinturones de Comunión. Edita este producto en el panel de administración para actualizar su nombre, descripción, precio y fotos reales.',
        'img': 'https://images.unsplash.com/photo-1624222247344-550fb8ecf7db?w=400&h=600&fit=crop'
    },
    'Gemelos': {
        'nombre': 'Gemelos de Comunión Muestra',
        'desc': 'Producto de muestra para la nueva categoría de Gemelos de Comunión. Edita este producto en el panel de administración para actualizar su nombre, descripción, precio y fotos reales.',
        'img': 'https://images.unsplash.com/photo-1617137968427-85924c800a22?w=400&h=600&fit=crop'
    },
    'Pines': {
        'nombre': 'Pin de Comunión Muestra',
        'desc': 'Producto de muestra para la nueva categoría de Pines de Comunión. Edita este producto en el panel de administración para actualizar su nombre, descripción, precio y fotos reales.',
        'img': 'https://images.unsplash.com/photo-1630019852942-f89202989a59?w=400&h=600&fit=crop'
    }
}

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    print("SUCCESS: Conectado a la base de datos MySQL")

    now = datetime.now()
    inserted_count = 0

    for cat in new_categories:
        # Check if we already have products under this category & family
        check_query = "SELECT COUNT(*) FROM productos WHERE familia = %s AND categoria = %s"
        cursor.execute(check_query, (family, cat))
        count = cursor.fetchone()[0]

        if count == 0:
            config = placeholders[cat]
            insert_query = """
                INSERT INTO productos 
                (nombre, descripcion, precio, imagen, familia, categoria, stock, activo, created_at) 
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
            """
            cursor.execute(insert_query, (
                config['nombre'],
                config['desc'],
                0.0,
                config['img'],
                family,
                cat,
                10, # default stock
                True, # activo
                now
            ))
            print(f"ADD: Insertado placeholder para la categoria: {cat}")
            inserted_count += 1
        else:
            print(f"SKIP: La categoria '{cat}' ya tiene {count} productos. Saltando...")

    if inserted_count > 0:
        conn.commit()
        print(f"SUCCESS: Se han insertado {inserted_count} productos de muestra correctamente.")
    else:
        print("SKIP: No fue necesario realizar inserciones.")

except Exception as e:
    print(f"ERROR: Error al interactuar con la base de datos: {e}")
    if 'conn' in locals():
        conn.rollback()
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
