import os
import pymysql
import re

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
    
    print("Reading insert_280_productos.sql...")
    with open('insert_280_productos.sql', 'r', encoding='utf-8') as f:
        sql_content = f.read()
    
    # Remove single line comments starting with --
    clean_sql = re.sub(r'--.*?\n', '\n', sql_content)
    
    # Split queries by semicolon
    queries = [q.strip() for q in clean_sql.split(';') if q.strip()]
    
    print(f"Found {len(queries)} statements. Executing...")
    executed_count = 0
    for idx, query in enumerate(queries, 1):
        if not query:
            continue
        try:
            cursor.execute(query)
            executed_count += 1
            print(f"[OK] Executed query {idx}/{len(queries)}")
        except Exception as eq:
            print(f"[ERROR] Error in query {idx}: {eq}")
            print(f"Failed query was: {query[:100]}...")
    
    conn.commit()
    print(f"SUCCESS: {executed_count} queries executed and products populated successfully!")
    cursor.close()
    conn.close()

except Exception as e:
    print(f"ERROR: Database error: {e}")
