import pymysql

conn = pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='toor',
    database='ibernovia',
    charset='utf8mb4'
)

try:
    with conn.cursor() as cursor:
        # Check if index already exists
        cursor.execute("SHOW INDEX FROM productos")
        indexes = cursor.fetchall()
        index_names = [idx[2] for idx in indexes]
        
        if 'idx_productos_busqueda' in index_names:
            print("Index idx_productos_busqueda already exists.")
        else:
            print("Creating index idx_productos_busqueda...")
            cursor.execute("CREATE INDEX idx_productos_busqueda ON productos (familia, categoria, activo)")
            conn.commit()
            print("SUCCESS: Index created successfully.")
finally:
    conn.close()
