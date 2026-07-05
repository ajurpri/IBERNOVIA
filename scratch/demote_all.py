import os
import pymysql
import sys

try:
    conn = pymysql.connect(
        host=os.getenv('DB_HOST', '127.0.0.1'),
        port=int(os.getenv('DB_PORT', '3306')),
        user=os.getenv('DB_USER', 'root'),
        password=os.getenv('DB_PASSWORD', ''),
        database=os.getenv('DB_NAME', 'ibernovia'),
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
