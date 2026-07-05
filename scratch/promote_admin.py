import os
import pymysql
import sys

email = os.getenv('ADMIN_EMAIL', 'admin@ibernovia.com')

try:
    conn = pymysql.connect(
        host=os.getenv('DB_HOST', '127.0.0.1'),
        port=int(os.getenv('DB_PORT', '3306')),
        user=os.getenv('DB_USER', 'root'),
        password=os.getenv('DB_PASSWORD', ''),
        database=os.getenv('DB_NAME', 'ibernovia'),
        charset='utf8mb4'
    )
    print("Connection established")
    
    cursor = conn.cursor()
    cursor.execute("UPDATE usuario SET is_admin = 1 WHERE email = %s", (email,))
    conn.commit()
    
    print(f"User {email} promoted to admin.")
            
    cursor.close()
    conn.close()
except Exception as e:
    print(f"Error: {e}")
    sys.exit(1)
