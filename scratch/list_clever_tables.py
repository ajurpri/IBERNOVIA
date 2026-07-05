import os
import pymysql

host = os.getenv('DB_HOST', '')
user = os.getenv('DB_USER', '')
password = os.getenv('DB_PASSWORD', '')
database = os.getenv('DB_NAME', '')

if not all([host, user, password, database]):
    raise SystemExit('Define DB_HOST, DB_USER, DB_PASSWORD y DB_NAME antes de ejecutar este script.')

print(f"Connecting to Clever Cloud MySQL: {host}...")
try:
    conn = pymysql.connect(
        host=host,
        port=3306,
        user=user,
        password=password,
        database=database,
        charset='utf8mb4'
    )
    cursor = conn.cursor()
    cursor.execute("SHOW TABLES;")
    tables = cursor.fetchall()
    print("Tables currently in the database:")
    for t in tables:
        print(f" - {t[0]}")
    cursor.close()
    conn.close()
except Exception as e:
    print(f"Error: {e}")
