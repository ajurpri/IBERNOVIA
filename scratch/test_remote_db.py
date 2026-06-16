import pymysql

host = '89.248.107.58'
user = 'iber2026Jar'
password = 'BO0G^mg@yiw97cdx'
database = 'iber_26'

print(f"Connecting to database '{database}' on '{host}' as '{user}'...")
try:
    conn = pymysql.connect(
        host=host,
        port=3306,
        user=user,
        password=password,
        database=database,
        charset='utf8mb4',
        connect_timeout=5
    )
    cursor = conn.cursor()
    cursor.execute("SHOW TABLES;")
    tables = cursor.fetchall()
    print("✅ Connection successful!")
    print(f"Tables in '{database}': {tables}")
    cursor.close()
    conn.close()
except Exception as e:
    print(f"❌ Connection failed: {e}")
