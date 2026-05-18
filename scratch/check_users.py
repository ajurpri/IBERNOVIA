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
    cursor.execute("SELECT id, email, nombre, is_admin FROM usuario")
    users = cursor.fetchall()
    
    if not users:
        print("No users found in database.")
    else:
        print("Users found:")
        for user in users:
            print(f"ID: {user[0]}, Email: {user[1]}, Name: {user[2]}, Admin: {user[3]}")
            
    cursor.close()
    conn.close()
except Exception as e:
    print(f"Error: {e}")
    sys.exit(1)
