import pymysql
from pathlib import Path

# Conexión a MySQL
try:
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='toor',
        database='ibernovia',
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )
except Exception as e:
    print(f"Error al conectar: {e}")
    import sys
    sys.exit(1)

cursor = conn.cursor()

# Ruta de las imágenes
imagenes_dir = Path(r'c:\Users\alvar\Documents\IBERNOVIA\FOTOS WEB')

# Procesar cada imagen
contador = 0
for imagen_num in range(1, 281):
    # Buscar el archivo de imagen
    img_path_jpg = imagenes_dir / f'Complemento ({imagen_num}).jpg'
    img_path_JPG = imagenes_dir / f'Complemento ({imagen_num}).JPG'
    
    img_path = None
    if img_path_jpg.exists():
        img_path = img_path_jpg
    elif img_path_JPG.exists():
        img_path = img_path_JPG
    
    if img_path is None:
        print(f"⚠️  No se encontró imagen para producto {imagen_num}")
        continue
    
    try:
        # Leer archivo binario
        with open(img_path, 'rb') as f:
            imagen_data = f.read()
        
        # Actualizar el producto con la imagen BLOB
        sql = "UPDATE productos SET imagen = %s WHERE id = %s"
        cursor.execute(sql, (imagen_data, imagen_num))
        contador += 1
        
        if contador % 50 == 0:
            conn.commit()
            print(f"✅ Procesadas {contador} imágenes...")
    
    except Exception as e:
        print(f"❌ Error al procesar imagen {imagen_num}: {e}")

conn.commit()
cursor.close()
conn.close()

print(f"✅ Insertadas {contador} imágenes en la base de datos")
