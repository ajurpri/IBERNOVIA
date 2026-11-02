from pathlib import Path
import re
root=Path('ibernovias/front-end/src')
def edit(name, fn):
 p=root/name; s=p.read_text(encoding='utf-8'); p.write_text(fn(s),encoding='utf-8')
edit('App.vue',lambda s:s.replace('    <FloatingChat />\n','').replace("import FloatingChat from './components/FloatingChat.vue'\n",''))
# Retain the chat component and server, but remove it from the public dependency graph.
edit('components/FooterSection.vue',lambda s:re.sub(r'          <div class="mt-7 flex items-center gap-4">.*?\n          </div>','          <!-- Social profiles hidden until official URLs are confirmed. -->',s,flags=re.S).replace('Complementos y moda nupcial con una presentación elegante, cuidada y profesional para ceremonia, fiesta y comunión.','Complementos de ceremonia para tiendas y profesionales: novia, novio, fiesta, comunión y arras.').replace('<router-link to="/acceso-empresarial">Acceso empresarial</router-link>','<router-link to="/auth">Acceso profesional</router-link>\n            <router-link to="/acceso-empresarial">Solicitar alta</router-link>'))
edit('components/NavBar.vue',lambda s:s.replace('to="/acceso-empresarial"\n              class="business-btn','to="/auth"\n              class="business-btn').replace('Alta Empresa','Acceso profesional').replace('>Catalogo<','>Catálogo<').replace('Iniciar sesion','Iniciar sesión').replace('Cerrar sesion','Cerrar sesión').replace('Menu de usuario','Menú de usuario').replace('menu movil','menú móvil').replace('placeholder="Que estas buscando?"','placeholder="¿Qué estás buscando?" aria-label="Buscar por nombre o referencia"'))
edit('components/ProductCard.vue',lambda s:s.replace("import { getImageUrl } from '../lib/api'","import { getImageUrl } from '../lib/api'\nimport { productName, productReference } from '../lib/productPresentation'").replace('object-cover transition duration-700 ease-out group-hover:scale-105','object-contain p-4 transition-opacity duration-200').replace('{{ producto.nombre }}','{{ productName(producto) }}').replace(':alt="producto.nombre"',':alt="productName(producto)"').replace('</h3>','</h3>\n        <p class="mt-1 text-sm text-gray-600">Referencia: {{ productReference(producto) }}</p>').replace('to="/acceso-empresarial"','to="/auth"').replace('text-[10px] text-gray-400','text-xs text-gray-600').replace('w-9 h-9','w-11 h-11').replace('width: 2rem;','width: 2.75rem;').replace('height: 2rem;','height: 2.75rem;').replace('animation: image-shimmer 1.35s ease-in-out infinite;','animation: none;').replace('removido de favoritos','eliminado de favoritos').replace('♥ "','"'))
def product(s):
 s=s.replace("import { apiClient, getImageUrl } from '../lib/api'","import { apiClient, getImageUrl } from '../lib/api'\nimport { productName, productReference, sentenceCase, productWhatsAppUrl } from '../lib/productPresentation'")
 s=s.replace('{{ producto.nombre }}','{{ productName(producto) }}').replace('{{ producto.descripcion }}','{{ sentenceCase(producto.descripcion) }}').replace(':alt="producto.nombre"',':alt="productName(producto)"')
 s=s.replace('text-gray-500 text-sm mb-5','whitespace-pre-line text-gray-600 text-sm mb-5')
 s=s.replace('#{{ producto.id }}','{{ productReference(producto) }}')
 s=s.replace('Solicitar acceso empresarial','Solicitar alta')
 s=s.replace('<!-- SKU y Ref -->','''<a :href="getImageUrl(currentImageUrl)" target="_blank" rel="noopener" class="inline-block mb-4 underline text-sm">Ver fotografía ampliada</a>
          <a :href="productWhatsAppUrl(producto)" target="_blank" rel="noopener" class="product-enquiry mb-6">Consultar este producto por WhatsApp</a>

          <!-- Commercial reference -->''')
 s=s.replace('to="/acceso-empresarial"\n            class="block w-full','to="/auth"\n            class="block w-full').replace('Solicitar condiciones comerciales','Acceso profesional: tarifas y condiciones')
 s=s.replace('object-cover','object-contain p-3')
 s=s.replace('const title = `${producto.value.nombre} | IBERNOVIA`','const title = `${productName(producto.value)} · ${productReference(producto.value)} | IBERNOVIA`')
 s=s.replace('.filter(p => p.id !== producto.value.id)\n        .slice(0, 4)', '.filter(p => p.id !== producto.value.id)\n        .sort((a, b) => Number(b.categoria === producto.value.categoria) - Number(a.categoria === producto.value.categoria))\n        .slice(0, 4)')
 s=s.replace('<label class="block text-xs sm:text-sm font-bold','<label for="product-quantity" class="block text-xs sm:text-sm font-bold').replace('v-model.number="cantidad"','id="product-quantity" v-model.number="cantidad"')
 return s
edit('views/ProductoView.vue',product)
def catalog(s):
 s=s.replace('Catalogo Ibernovia','Catálogo Ibernovia').replace('Coleccion para','Colección para').replace('familias y categorias','familias y categorías').replace('El catalogo esta visible','El catálogo está visible').replace('Buscar en catalogo','Buscar en catálogo').replace('Categorias dentro','Categorías dentro').replace('Solo en oferta 🔥','Solo en oferta').replace('📄 Descargar Catálogo (PDF)','Descargar catálogo (PDF)').replace('to="/acceso-empresarial"','to="/auth"')
 return s
edit('views/TiendaView.vue',catalog)
def alta(s):
 for a,b in {'Esta area esta':'Esta área está','Revision':'Revisión','Recepcion':'Recepción','Activacion':'Activación','Telefono':'Teléfono','Direccion':'Dirección','Informacion':'Información','>Acceso profesional<':'>Solicitar alta<','Pais':'País','Codigo postal':'Código postal'}.items(): s=s.replace(a,b)
 return s.replace('<div class="b2b-hero-actions">','<p class="mt-4 text-sm">¿Ya tienes cuenta? <router-link to="/auth" class="underline">Acceso profesional</router-link></p>\n            <div class="b2b-hero-actions">')
edit('views/AccesoEmpresarialView.vue',alta)
edit('views/AuthView.vue',lambda s:s.replace('Iniciar Sesion','Iniciar sesión').replace('Contrasena','Contraseña').replace('Acceso Clientes y Profesionales','Acceso profesional').replace('<h1 class="font-serif text-4xl md:text-5xl font-light text-luxury-black tracking-wide">Mi Cuenta</h1>','<h1 class="font-serif text-4xl md:text-5xl font-light text-luxury-black tracking-wide">Acceso profesional</h1>'))
