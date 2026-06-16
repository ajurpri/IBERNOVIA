import pymysql

host = 'bbmd7ggskeyrsryrrz1x-mysql.services.clever-cloud.com'
user = 'ugcvowu3hsekr0fm'
password = 'ioHzPRomuRQcBPSPTFMV'
database = 'bbmd7ggskeyrsryrrz1x'

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
