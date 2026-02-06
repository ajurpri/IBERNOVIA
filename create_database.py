import pymysql

# Conexión a MySQL
try:
    conn = pymysql.connect(
        host='127.0.0.1',
        port=3306,
        user='root',
        password='toor'
    )
    print("✅ Conexión establecida")
except pymysql.Error as e:
    print(f"❌ Error: {e.args[1] if len(e.args) > 1 else e}")
    import sys
    sys.exit(1)

cursor = conn.cursor()

try:
    # Crear base de datos si no existe
    cursor.execute("CREATE DATABASE IF NOT EXISTS ibernovia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    conn.commit()
    print("✅ Base de datos 'ibernovia' creada/verificada")
except pymysql.Error as e:
    print(f"❌ Error al crear base de datos: {e}")

cursor.close()
conn.close()
