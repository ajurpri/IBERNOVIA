import pymysql
import os
from pathlib import Path

db_config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': 'toor',
    'database': 'ibernovia',
    'charset': 'utf8mb4'
}

# The folder where static images are served in the front-end project
public_dir = Path(r'c:\Users\alvar\Videos\IBERNOVIA\ibernovias\front-end\public')

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor(pymysql.cursors.DictCursor)
    
    # Get all products with an image
    cursor.execute("SELECT id, nombre, imagen FROM productos WHERE imagen IS NOT NULL AND imagen != ''")
    products = cursor.fetchall()
    
    updated_count = 0
    missing_count = 0
    
    print(f"Checking {len(products)} products...")
    
    for p in products:
        db_path = p['imagen']
        
        # Determine the physical path on disk
        clean_db_path = db_path.lstrip('/')
        disk_path = public_dir / clean_db_path
        
        if disk_path.exists():
            # Image already exists on disk, no action needed
            continue
            
        # Try to find alternatives
        filename = disk_path.name
        stem = disk_path.stem
        suffix = disk_path.suffix
        
        alternative_stems = []
        if '_' in stem:
            parts = stem.split('_')
            if parts[-1].isdigit():
                alternative_stems.append('_'.join(parts[:-1]))
                
        alternative_found = False
        for alt_stem in alternative_stems:
            alt_filename = f"{alt_stem}{suffix}"
            alt_disk_path = disk_path.parent / alt_filename
            
            if alt_disk_path.exists():
                new_db_path = '/' + alt_disk_path.relative_to(public_dir).as_posix()
                
                update_query = "UPDATE productos SET imagen = %s WHERE id = %s"
                cursor.execute(update_query, (new_db_path, p['id']))
                
                print(f"SYNC [{p['id']}]: {db_path} -> {new_db_path}")
                updated_count += 1
                alternative_found = True
                break
                
        if not alternative_found:
            if missing_count < 10:
                print(f"MISSING [{p['id']}] {p['nombre']}: {db_path} (Disk: {disk_path})")
            missing_count += 1
            
    if updated_count > 0:
        conn.commit()
        print(f"\nSUCCESS: Sync completed! Updated {updated_count} products in the database.")
    else:
        print("\nSKIP: All product images are already in sync with files on disk.")
        
    if missing_count > 0:
        print(f"Total missing images on disk: {missing_count}")
        
except Exception as e:
    print(f"ERROR: {e}")
    if 'conn' in locals():
        conn.rollback()
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
