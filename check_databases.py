import pymysql

# Conexión a MySQL SIN especificar base de datos
try:
    conn = pymysql.connect(
        host='127.0.0.1',
        port=3306,
        user='root',
        password='toor',
        charset='utf8mb4'
    )
    print("✅ Conexión establecida")
except pymysql.Error as e:
    print(f"❌ Error: {e.args[1] if len(e.args) > 1 else e}")
    import sys
    sys.exit(1)

cursor = conn.cursor()

# Listar todas las bases de datos
try:
    cursor.execute("SHOW DATABASES")
    databases = cursor.fetchall()
    print("Bases de datos disponibles:")
    for db in databases:
        print(f"  - {db[0]}")
except pymysql.Error as e:
    print(f"❌ Error: {e}")

cursor.close()
conn.close()
