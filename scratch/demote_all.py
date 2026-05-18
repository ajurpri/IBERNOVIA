import pymysql
import sys

try:
    conn = pymysql.connect(
        host='127.0.0.1',
        port=3306,
        user='root',
        password='toor',
        database='ibernovia',
        charset='utf8mb4'
    )
    cursor = conn.cursor()
    cursor.execute("UPDATE usuario SET is_admin = 0")
    conn.commit()
    print("All users demoted from admin temporarily.")
    cursor.close()
    conn.close()
except Exception as e:
    print(f"Error: {e}")
    sys.exit(1)
