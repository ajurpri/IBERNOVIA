import mysql.connector
from datetime import datetime

# Conexión a MySQL
conn = mysql.connector.connect(
    host='localhost',
    user='root',
    password='toor',
    database='ibernovia'
)
cursor = conn.cursor()

# Limpiar tabla
cursor.execute("DELETE FROM productos")

# Definir colecciones y productos
colecciones = {
    'Ligas': {
        'precios': (35.99, 70.00),
        'stock': (5, 25),
        'nombre_base': 'Liga',
        'descripciones': [
            'Encaje con aplicaciones',
            'Satén y detalles bordados',
            'Con cristales Swarovski',
            'Estilo vintage clásico',
            'Encaje francés puro',
            'Con perlas cultivadas',
            'Diseño romántico',
            'Encaje español',
            'Con hilo metalizado',
            'Detalles de gema'
        ]
    },
    'Pendientes': {
        'precios': (45.00, 350.00),
        'stock': (2, 35),
        'nombre_base': 'Pendientes',
        'descripciones': [
            'Con perla cultivada',
            'Diamantes auténticos',
            'Esmeralda certificada',
            'Cristales Swarovski',
            'Ópalo australiano',
            'Con rubíes',
            'Perlas barrocas',
            'Amatista natural',
            'Topacio azul',
            'Zafiro tailandés'
        ]
    },
    'Abanicos': {
        'precios': (75.00, 150.00),
        'stock': (1, 10),
        'nombre_base': 'Abanico',
        'descripciones': [
            'Artesanal de encaje',
            'Vintage con flores',
            'Marfil con decoraciones',
            'Español plegable',
            'Con plumas naturales',
            'Diseño clásico',
            'Encaje delicado',
            'Seda pura',
            'Con cristales',
            'Decoración francesa'
        ]
    },
    'Gemelos': {
        'precios': (75.00, 500.00),
        'stock': (1, 15),
        'nombre_base': 'Gemelos',
        'descripciones': [
            'Plata antigua grabada',
            'Oro de 24 quilates',
            'Con perla negra',
            'Con diamantes blancos',
            'Ónix pulido',
            'Esmalte azul marino',
            'Nácar iridiscente',
            'Platino puro',
            'Con rubíes oscuros',
            'Zafiros azules'
        ]
    },
    'Cruces': {
        'precios': (95.00, 525.00),
        'stock': (1, 10),
        'nombre_base': 'Cruz',
        'descripciones': [
            'Plata esterlina delicada',
            'Oro antiguo barroco',
            'Con incrustaciones de diamantes',
            'Romanica medieval',
            'Cristales Swarovski',
            'Gótica plateada',
            'Bizantina dorada',
            'Con perlas cultivadas',
            'Céltica de plata',
            'Ortodoxa de oro'
        ]
    },
    'Tocados': {
        'precios': (175.00, 450.00),
        'stock': (1, 6),
        'nombre_base': 'Tocado',
        'descripciones': [
            'Flores de tela y cristales',
            'Con perlas y diamantes',
            'Corona princesa',
            'Novia clásico',
            'Velo francés',
            'Flores secas preservadas',
            'Hojas doradas',
            'Cintillo platino',
            'Cristal bohemio',
            'Perlas largas'
        ]
    }
}

# Generar productos
contador = 0
for imagen_num in range(1, 281):
    categoria_actual = list(colecciones.keys())[(imagen_num - 1) % 6]
    idx_descripcion = (imagen_num - 1) % 10
    col = colecciones[categoria_actual]
    
    nombre = f"{col['nombre_base']} {['Lujo', 'Elegancia', 'Clásico', 'Moderno', 'Romántico', 'Vintage', 'Exclusivo', 'Premium', 'Delicado', 'Exquisito'][(imagen_num - 1) % 10]} {(imagen_num - 1) % 10 + 1}"
    descripcion = f"{col['descripciones'][idx_descripcion]} - Colección {categoria_actual}"
    precio = round(col['precios'][0] + (imagen_num % 20) * (col['precios'][1] - col['precios'][0]) / 20, 2)
    stock = col['stock'][0] + (imagen_num % (col['stock'][1] - col['stock'][0] + 1))
    imagen = f"/images/Complemento ({imagen_num}).jpg"
    
    query = """INSERT INTO productos 
    (nombre, descripcion, precio, imagen, categoria, stock, activo, created_at) 
    VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""
    
    values = (nombre, descripcion, precio, imagen, categoria_actual, stock, True, datetime.now())
    cursor.execute(query, values)
    contador += 1

conn.commit()
cursor.close()
conn.close()

print(f"✅ Insertados {contador} productos en la base de datos")
