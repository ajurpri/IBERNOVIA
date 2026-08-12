import pymysql

db_config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': 'toor',
    'database': 'ibernovia',
    'charset': 'utf8mb4'
}

queries = [
    ("Novia", "Velos"),
    ("Novia", "Tocados"),
    ("Comunión", "Diademas y Coronas"),
    ("Fiesta", "Tocados")
]

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor(pymysql.cursors.DictCursor)
    
    print("CATEGORICAL IMAGES FOR BANNER SLIDESHOW:")
    for fam, cat in queries:
        query = "SELECT id, nombre, categoria, imagen FROM productos WHERE familia = %s AND categoria = %s AND imagen IS NOT NULL AND imagen != '' AND activo = 1 LIMIT 5"
        cursor.execute(query, (fam, cat))
        rows = cursor.fetchall()
        print(f"\n{fam} - {cat}:")
        for r in rows:
            print(f"  - [{r['id']}] {r['nombre']} -> {r['imagen']}")
            
except Exception as e:
    print(f"ERROR: {e}")
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
