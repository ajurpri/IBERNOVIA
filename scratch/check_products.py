import os
import pymysql
from pathlib import Path

conn = pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='toor',
    database='ibernovia',
    charset='utf8mb4',
    cursorclass=pymysql.cursors.DictCursor
)

try:
    with conn.cursor() as cursor:
        cursor.execute("SELECT COUNT(*) as cnt FROM productos")
        print("Total products:", cursor.fetchone()['cnt'])
        
        cursor.execute("SELECT DISTINCT familia, categoria, COUNT(*) as cnt FROM productos GROUP BY familia, categoria")
        print("\nProducts by family/category:")
        for row in cursor.fetchall():
            print(f"  - {row['familia']} / {row['categoria']}: {row['cnt']}")
            
        cursor.execute("SELECT id, nombre, imagen, familia, categoria FROM productos LIMIT 10")
        print("\nSample products:")
        for row in cursor.fetchall():
            img_path = row['imagen']
            # Check if image exists on disk
            # Front-end public directory
            full_path = Path(r"c:\Users\alvar\Videos\IBERNOVIA\ibernovias\front-end\public") / img_path.lstrip('/')
            exists = full_path.exists()
            print(f"  ID: {row['id']} | Name: {row['nombre']} | Image: {img_path} | Exists on disk: {exists}")
            if not exists:
                print(f"    Expected path on disk: {full_path}")
                
        # Search for specific product refs from the screenshot
        cursor.execute("SELECT id, nombre, imagen, familia, categoria FROM productos WHERE nombre LIKE '%1239-AB%' OR nombre LIKE '%1242-AB-MF%' OR nombre LIKE '%14126-AB%' OR nombre LIKE '%14136-AB%'")
        print("\nSearching for screenshot products in DB:")
        screenshot_products = cursor.fetchall()
        for row in screenshot_products:
            img_path = row['imagen']
            full_path = Path(r"c:\Users\alvar\Videos\IBERNOVIA\ibernovias\front-end\public") / img_path.lstrip('/')
            exists = full_path.exists()
            print(f"  ID: {row['id']} | Name: {row['nombre']} | Image: {img_path} | Exists on disk: {exists}")
            if not exists:
                print(f"    Expected path on disk: {full_path}")
        
        # Let's count how many images exist vs don't exist
        cursor.execute("SELECT imagen FROM productos")
        all_imgs = cursor.fetchall()
        missing = 0
        found = 0
        for row in all_imgs:
            img_path = row['imagen']
            if not img_path:
                missing += 1
                continue
            full_path = Path(r"c:\Users\alvar\Videos\IBERNOVIA\ibernovias\front-end\public") / img_path.lstrip('/')
            if full_path.exists():
                found += 1
            else:
                missing += 1
        print(f"\nImage check: Found {found} on disk, Missing {missing}")
        
finally:
    conn.close()
