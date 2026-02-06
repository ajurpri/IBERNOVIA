import pymysql

# Conexión a MySQL
conn = pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='toor',
    database='ibernovia',
    charset='utf8mb4'
)

cursor = conn.cursor()

try:
    # Crear tabla productos
    create_table_sql = """
    CREATE TABLE IF NOT EXISTS productos (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(255),
        categoria VARCHAR(100),
        precio DOUBLE,
        imagen LONGTEXT,
        descripcion VARCHAR(500),
        stock INT,
        activo BOOLEAN DEFAULT TRUE,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
    """
    cursor.execute(create_table_sql)
    conn.commit()
    print("✅ Tabla 'productos' creada satisfactoriamente")
except pymysql.Error as e:
    print(f"❌ Error: {e}")
    conn.rollback()
finally:
    cursor.close()
    conn.close()
