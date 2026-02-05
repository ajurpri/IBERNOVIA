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

# Definir colecciones con nombres y descripciones REALES
productos_config = {
    'Ligas': [
        'Liga Encaje Blanco Con Moño', 'Liga Marfil Clásica', 'Liga Azul Zafiro', 'Liga Rosa Pastel Delicada',
        'Liga Negra Elegante', 'Liga Dorada Lujo', 'Liga Encaje Español Tradicional', 'Liga Cristal Swarovski',
        'Liga Marfil Perlas Cultivadas', 'Liga Roja Pasión', 'Liga Plata Antigua Grabada', 'Liga Champagne Brillante',
        'Liga Coral Romántica', 'Liga Verde Esmeralda Profundo', 'Liga Naranja Atardecer', 'Liga Turquesa Mar',
        'Liga Gris Platino', 'Liga Beige Natural Premium', 'Liga Borgoña Oscuro', 'Liga Lavanda Suave',
        'Liga Blanco Roto Vintage', 'Liga Menta Fresca', 'Liga Melocotón Durazno', 'Liga Café Con Leche',
        'Liga Oro Pálido', 'Liga Plata Satinada', 'Liga Nude Piel', 'Liga Crema Nata', 'Liga Gris Perla',
        'Liga Burgundy Sofisticada', 'Liga Azul Marino Profundo', 'Liga Verde Oliva', 'Liga Marrón Chocolate',
        'Liga Gris Humo', 'Liga Plateada Brillante', 'Liga Dorada Mate', 'Liga Blanca Radiante', 'Liga Rosa Chicle',
        'Liga Púrpura Profundo', 'Liga Terracota Clásica', 'Liga Marfil Antiguo', 'Liga Blanco Hueso', 'Liga Crema Luz',
        'Liga Beige Dorado', 'Liga Champagne Rosado', 'Liga Blanco Encaje Francés', 'Liga Marfil Encaje Belga',
        'Liga Blanco Algodón Puro'
    ],
    'Pendientes': [
        'Pendientes Perla Redonda Blanca', 'Pendientes Diamante Talla Brillante', 'Pendientes Esmeralda Colombiana',
        'Pendientes Topacio Azul Suizo', 'Pendientes Cristal Swarovski Aurora', 'Pendientes Ópalo Australiano',
        'Pendientes Rubí Sintético Oval', 'Pendientes Perla Barroca Dorada', 'Pendientes Amatista Púrpura Natural',
        'Pendientes Citrina Dorada Pura', 'Pendientes Zafiro Azul Tailandés', 'Pendientes Granate Profundo',
        'Pendientes Calcedonia Blanca', 'Pendientes Turmalina Verde', 'Pendientes Cuarzo Rosa Delicado',
        'Pendientes Ámbar Dorado', 'Pendientes Agata Banda Marrón', 'Pendientes Cristal Violeta',
        'Pendientes Perla Negra Tahiti', 'Pendientes Diamante Certificado', 'Pendientes Agua Marina Suave',
        'Pendientes Tanzanita Azul', 'Pendientes Turquesa Americana', 'Pendientes Jade Verde Oscuro',
        'Pendientes Lapislázuli Profundo', 'Pendientes Malaquita Verde', 'Pendientes Coral Rojo Natural',
        'Pendientes Concha Nácar Blanca', 'Pendientes Magnetita Gris', 'Pendientes Hematita Plateada',
        'Pendientes Aventurina Verde', 'Pendientes Fluorita Púrpura', 'Pendientes Rodocrosita Rosa',
        'Pendientes Sodalita Azul Oscuro', 'Pendientes Celestina Azul Claro', 'Pendientes Smithsonita Rosa',
        'Pendientes Calcopirita Dorada', 'Pendientes Esfalerita Negra', 'Pendientes Magnetita Brillante',
        'Pendientes Galena Plateada', 'Pendientes Blenda Dorada', 'Pendientes Pirita Dorada Brillante',
        'Pendientes Cristal Bohemio Checo', 'Pendientes Vidrio Murano Veneciano', 'Pendientes Acrílico Gema',
        'Pendientes Resina Epoxi Premium', 'Pendientes Pasta Cerámica Artística'
    ],
    'Abanicos': [
        'Abanico Encaje Blanco Artesanal', 'Abanico Vintage Rosa Romántico', 'Abanico Marfil Decoraciones',
        'Abanico Español Tradicional', 'Abanico Plumas Avestruz Natural', 'Abanico Rojo Pasión',
        'Abanico Azul Elegancia', 'Abanico Negro Sofisticado', 'Abanico Dorado Lujo',
        'Abanico Seda Pura Japonesa', 'Abanico Tela Encaje Francés', 'Abanico Pliegues Clásicos',
        'Abanico Flores Secas Preservadas', 'Abanico Lentejuelas Brillantes', 'Abanico Plumas Colores',
        'Abanico Madera Nogal Tallado', 'Abanico Metal Fileteado', 'Abanico Tela Damasco',
        'Abanico Punto Aguja Holandés', 'Abanico Encaje Ganchillo', 'Abanico Piel Cabra Suave',
        'Abanico Ante Terciopelo', 'Abanico Brocado Bordado', 'Abanico Lino Natural',
        'Abanico Algodón Estampado', 'Abanico Organza Plegada', 'Abanico Tul Capas',
        'Abanico Gasa Ligera', 'Abanico Chiffón Etéreo', 'Abanico Raso Brillante',
        'Abanico Terciopelo Suave', 'Abanico Pana Acanalada', 'Abanico Popelina Almidonada',
        'Abanico Lino Irlandés', 'Abanico Lana Merino', 'Abanico Seda Salvaje',
        'Abanico Ceda Tailandesa', 'Abanico Tafetán Crujiente', 'Abanico Lúrex Plateado',
        'Abanico Hologramas Iridiscentes', 'Abanico Paillettes Doradas', 'Abanico Canutillos Plateados',
        'Abanico Abalorios Vidrio', 'Abanico Cristales Austriacos', 'Abanico Gemas Sintéticas'
    ],
    'Gemelos': [
        'Gemelos Plata Antigua Grabada', 'Gemelos Oro 24 Quilates Puro', 'Gemelos Perla Negra Tahiti',
        'Gemelos Diamante Blanco Certificado', 'Gemelos Ónix Negro Pulido', 'Gemelos Esmalte Azul Marino',
        'Gemelos Nácar Iridiscente', 'Gemelos Platino Puro Absoluto', 'Gemelos Rubí Oscuro Sintético',
        'Gemelos Zafiro Azul Natural', 'Gemelos Jade Verde Oscuro', 'Gemelos Lapislázuli Profundo',
        'Gemelos Turmalina Negra', 'Gemelos Cuarzo Ahumado', 'Gemelos Ámbar Dorado Báltico',
        'Gemelos Coral Rojo Mediterráneo', 'Gemelos Marfil Vegetal', 'Gemelos Madera Ébano',
        'Gemelos Caoba Oscura', 'Gemelos Roble Blanco Claro', 'Gemelos Acero Inoxidable Satinado',
        'Gemelos Titanio Anodizado', 'Gemelos Tungsteno Gris', 'Gemelos Paladio Brillante',
        'Gemelos Latón Envejecido', 'Gemelos Bronce Antiguo', 'Gemelos Cobre Puro',
        'Gemelos Plata de Ley 925', 'Gemelos Oro Blanco 18 Quilates', 'Gemelos Oro Rosa Rosado',
        'Gemelos Esmalte Negro Ébano', 'Gemelos Esmalte Rojo Sangre', 'Gemelos Esmalte Verde Bosque',
        'Gemelos Esmalte Azul Cielo', 'Gemelos Esmalte Púrpura Real', 'Gemelos Esmalte Dorado Brillante',
        'Gemelos Resina Epoxi Transparente', 'Gemelos Vidrio Murano Veneciano', 'Gemelos Cerámica Porcelana',
        'Gemelos Terracota Natural', 'Gemelos Piedra Volcánica', 'Gemelos Pizarra Gris'
    ],
    'Cruces': [
        'Cruz Plata Esterlina Delicada', 'Cruz Oro Antiguo Barroco', 'Cruz Diamantes Incrustados',
        'Cruz Románica Medieval Clásica', 'Cruz Cristal Swarovski Brillante', 'Cruz Gótica Plateada',
        'Cruz Bizantina Dorada Ornamental', 'Cruz Perlas Cultivadas Blancas', 'Cruz Céltica Plata Pura',
        'Cruz Ortodoxa Oro Puro', 'Cruz Copta Africana Tradicional', 'Cruz Hueca Vacía Moderna',
        'Cruz Geométrica Minimalista', 'Cruz Claddagh Irlandesa Clásica', 'Cruz Maltesa Templaria',
        'Cruz Ansata Egipcia Antigua', 'Cruz Griega Equilibrada', 'Cruz Latina Tradicional',
        'Cruz Papal Ceremonial', 'Cruz Carmelita Descalza', 'Cruz Franciscana Franciscanos',
        'Cruz Jesuita Jesuitas', 'Cruz Dominica Orden Dominicos', 'Cruz Benedictina Monjes',
        'Cruz Cistercien Cistercienses', 'Cruz Trapense Trappenses', 'Cruz Cartujo Cartujos',
        'Cruz Cartuja Monasterio', 'Cruz Benedictina Clásica', 'Cruz Ortodoxo Eslava',
        'Cruz Copta Etíope', 'Cruz Armenia Khachkar', 'Cruz Siria Nestoriana',
        'Cruz Maronita Libanesa', 'Cruz Caldea Caldeos', 'Cruz Jacobita Antioquía',
        'Cruz Nestoriana Persia', 'Cruz Monofisita Antioquía', 'Cruz Miafisita Oriental',
        'Cruz Docetista Antigua', 'Cruz Pneumatomaca Herética', 'Cruz Pelagiana Antigua'
    ],
    'Tocados': [
        'Tocado Flores Tela Blancas', 'Tocado Perlas Diamantes Suntuoso', 'Tocado Corona Princesa',
        'Tocado Novia Velo Clásico', 'Tocado Velo Encaje Francés', 'Tocado Flores Secas Preservadas',
        'Tocado Hojas Doradas Metalizado', 'Tocado Cintillo Platino Brillante', 'Tocado Cristales Bohemios',
        'Tocado Perlas Caída Larga', 'Tocado Flores Artificiales Realistas', 'Tocado Plumas Avestruz Finas',
        'Tocado Lentejuelas Brillantes', 'Tocado Tul Capas Voluminoso', 'Tocado Organza Etéreo',
        'Tocado Cinta Satén Brillante', 'Tocado Moño Encaje Lateral', 'Tocado Media Luna Frontal',
        'Tocado Tiara Corona Delgada', 'Tocado Velo Fingertip Largo', 'Tocado Velo Catedral Ceremonial',
        'Tocado Ramita Rama Fina', 'Tocado Enredadera Hiedra', 'Tocado Floral Guirnalda',
        'Tocado Rama Eucalipto Verde', 'Tocado Helecho Silvestre Natural', 'Tocado Aspidistra Tropical',
        'Tocado Flores Lilas Púrpura', 'Tocado Flores Rosas Romántico', 'Tocado Flores Nomeolvides Azul',
        'Tocado Flores Hielo Blanco Puro', 'Tocado Flores Orquídeas Exóticas', 'Tocado Flores Magnolia',
        'Tocado Flores Peonía Frondosa', 'Tocado Flores Loto Oriental', 'Tocado Flores Cerezo Sakura',
        'Tocado Flores Dalia Española', 'Tocado Flores Margarita Campestre', 'Tocado Flores Girasol Dorado',
        'Tocado Flores Tulipán Primavera', 'Tocado Flores Lirio Blanco', 'Tocado Flores Jacinto Fragante',
        'Tocado Flores Azalea Colorida', 'Tocado Flores Camelias Orientales', 'Tocado Flores Camelia Occidental'
    ]
}

# Precios por categoría
precios_categoria = {
    'Ligas': (35.99, 70.00),
    'Pendientes': (45.00, 350.00),
    'Abanicos': (75.00, 150.00),
    'Gemelos': (75.00, 500.00),
    'Cruces': (95.00, 525.00),
    'Tocados': (175.00, 450.00)
}

stock_categoria = {
    'Ligas': (5, 30),
    'Pendientes': (2, 35),
    'Abanicos': (1, 10),
    'Gemelos': (1, 15),
    'Cruces': (1, 10),
    'Tocados': (1, 6)
}

# Generar productos
contador = 0
producto_idx = 0

for imagen_num in range(1, 281):
    # Distribuir entre categorías (cada una con ~47 productos)
    categoria_idx = (imagen_num - 1) // 47
    if categoria_idx >= 6:
        categoria_idx = 5
    
    categoria_actual = list(productos_config.keys())[categoria_idx]
    nombres = productos_config[categoria_actual]
    
    # Obtener nombre del producto
    nombre_idx = (imagen_num - 1) % len(nombres)
    nombre = nombres[nombre_idx]
    
    descripcion = f"Complemento nupcial exclusivo de la colección {categoria_actual}. Diseño delicado y elegante."
    
    # Generar precio basado en rango de categoría
    precios = precios_categoria[categoria_actual]
    precio = round(precios[0] + (imagen_num % 20) * (precios[1] - precios[0]) / 20, 2)
    
    # Stock aleatorio
    stock_rango = stock_categoria[categoria_actual]
    stock = stock_rango[0] + (imagen_num % (stock_rango[1] - stock_rango[0] + 1))
    
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

print(f"✅ Insertados {contador} productos con nombres REALES en la base de datos")
