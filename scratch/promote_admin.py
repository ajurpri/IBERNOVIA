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
    print("Connection established")
    
    cursor = conn.cursor()
    # Promote ajupr06@gmail.com to admin
    cursor.execute("UPDATE usuario SET is_admin = 1 WHERE email = 'ajupr06@gmail.com'")
    conn.commit()
    
    print("User ajupr06@gmail.com promoted to admin.")
            
    cursor.close()
    conn.close()
except Exception as e:
    print(f"Error: {e}")
    sys.exit(1)
