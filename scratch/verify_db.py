import pymysql

db_config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': 'toor',
    'database': 'ibernovia',
    'charset': 'utf8mb4'
}

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    query = "SELECT DISTINCT familia, categoria FROM productos WHERE familia = 'Comunión' ORDER BY categoria"
    cursor.execute(query)
    rows = cursor.fetchall()
    
    print("CATEGORIES UNDER COMUNIÓN IN DATABASE:")
    for r in rows:
        print(f"  - {r[0]} -> {r[1]}")
        
except Exception as e:
    print(f"ERROR: {e}")
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
