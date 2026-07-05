import os
import pymysql
import sys

email = 'ajupr06@gmail.com'
if len(sys.argv) > 1:
    email = sys.argv[1]

host = os.getenv('DB_HOST', '')
user = os.getenv('DB_USER', '')
password = os.getenv('DB_PASSWORD', '')
database = os.getenv('DB_NAME', '')

if not all([host, user, password, database]):
    raise SystemExit('Define DB_HOST, DB_USER, DB_PASSWORD y DB_NAME antes de ejecutar este script.')

print(f"Connecting to Clever Cloud MySQL to promote '{email}' to admin...")
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
    # First, let's verify if the user exists
    cursor.execute("SELECT id, nombre, is_admin FROM usuario WHERE email = %s", (email,))
    user_row = cursor.fetchone()
    if not user_row:
        print(f"User '{email}' not found in the database. Please register first on the frontend website, then run this script.")
    else:
        cursor.execute("UPDATE usuario SET is_admin = 1 WHERE email = %s", (email,))
        conn.commit()
        print(f"SUCCESS: User '{email}' (ID: {user_row[0]}, Nombre: {user_row[1]}) has been promoted to admin!")

    
    cursor.close()
    conn.close()
except Exception as e:
    print(f"Error: {e}")
