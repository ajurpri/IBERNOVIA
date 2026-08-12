import pymysql

db_config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': 'toor',
    'database': 'ibernovia',
    'charset': 'utf8mb4'
}

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    # Update the 6 placeholder products in the Comunión family to have NULL images
    placeholder_names = [
        'Cordón de Comunión Muestra',
        'Medalla de Comunión Muestra',
        'Librito de Comunión Muestra',
        'Cinturón de Comunión Muestra',
        'Gemelos de Comunión Muestra',
        'Pin de Comunión Muestra'
    ]
    
    # We will set the image to NULL (or empty string) for these specific products
    query = "UPDATE productos SET imagen = NULL WHERE nombre = %s"
    
    updated_count = 0
    for name in placeholder_names:
        cursor.execute(query, (name,))
        updated_count += cursor.rowcount
        print(f"Cleared image path for product: {name}")
        
    conn.commit()
    print(f"\nSUCCESS: Cleared placeholder images for {updated_count} products.")
    
except Exception as e:
    print(f"ERROR: {e}")
    if 'conn' in locals():
        conn.rollback()
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
