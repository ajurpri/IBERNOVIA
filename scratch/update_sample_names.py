import pymysql

conn = pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='toor',
    database='ibernovia',
    charset='utf8mb4'
)

updates = [
    (1053, 'Casquete Metálico Muestra'),
    (1054, 'Corona Metálica Muestra'),
    (1055, 'Diadema Metálica Muestra'),
    (1056, 'Espiral Metálica Muestra'),
    (1057, 'Hilo Metálico Muestra'),
    (1058, 'Horquilla Metálica Muestra'),
    (1074, 'Peina Metálica Muestra'),
    (1075, 'Pendiente Metálico Muestra'),
    (977, 'Mantilla de Niña Muestra'),
]

with conn.cursor() as cur:
    for pid, new_name in updates:
        cur.execute("UPDATE productos SET nombre = %s WHERE id = %s", (new_name, pid))

conn.commit()
conn.close()
print("SUCCESS: Updated product names in MySQL!")
