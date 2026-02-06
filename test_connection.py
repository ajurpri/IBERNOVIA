import pymysql
from pathlib import Path

# Intentar diferentes contraseñas
passwords = ['1234', 'toor', 'torr', '', '123456', 'password']

for pwd in passwords:
    try:
        print(f"Intentando con contraseña: '{pwd}'...")
        conn = pymysql.connect(
            host='127.0.0.1',
            port=3306,
            user='root',
            password=pwd,
            charset='utf8mb4'
        )
        cursor = conn.cursor()
        cursor.execute("SHOW DATABASES;")
        print(f"✅ Conexión exitosa con contraseña: '{pwd}'")
        cursor.close()
        conn.close()
        break
    except Exception as e:
        print(f"❌ Falló con '{pwd}': {e}")
