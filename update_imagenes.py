import pymysql

# Conexión a MySQL
try:
    conn = pymysql.connect(
        host='127.0.0.1',
        port=3306,
        user='root',
        password='toor',
        database='ibernovia',
        charset='utf8mb4'
    )
    print("✅ Conexión establecida con éxito")
except pymysql.Error as e:
    print(f"❌ Error de conexión: {e}")
    print(f"   Código de error: {e.args[0] if e.args else 'desconocido'}")
    import sys
    sys.exit(1)

cursor = conn.cursor()

# Actualizar imágenes
try:
    print("Actualizando rutas de imágenes en la base de datos...")
    for producto_id in range(1, 281):
        imagen_nombre = f"Complemento ({producto_id}).jpg"
        imagen_ruta = f"/images/{imagen_nombre}"
        
        sql = "UPDATE productos SET imagen = %s WHERE id = %s"
        cursor.execute(sql, (imagen_ruta, producto_id))
        
        if producto_id % 50 == 0:
            conn.commit()
            print(f"✅ Actualizados {producto_id} productos")

    conn.commit()
    print(f"✅ Insertadas {280} rutas de imágenes en la base de datos")
    
except pymysql.Error as e:
    print(f"❌ Error ejecutando query: {e}")
    conn.rollback()
finally:
    cursor.close()
    conn.close()
