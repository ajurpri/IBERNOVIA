from pathlib import Path
import re

root=Path(__file__).resolve().parents[2]/'ibernovias/front-end/src'
def edit(name, fn):
    p=root/name
    p.write_text(fn(p.read_text(encoding='utf-8')),encoding='utf-8')

def app(s):
    s=re.sub(r'    <div class="flex items-center justify-center gap-6.*?</div>\n', '', s, count=1, flags=re.S)
    return re.sub(r"import (BackToTop|WhatsAppButton) from .*\n",'',s)
edit('App.vue',app)

def auth(s):
    s=re.sub(r'        <span[^>]*>ATELIER IBERNOVIA</span>\n','',s)
    s=re.sub(r'          <div class="text-right">.*?</div>\n','',s,count=1,flags=re.S)
    s=re.sub(r'      <div class="text-center text-\[10px\].*?protocolo SSL.*?</div>\n','',s,count=1,flags=re.S)
    s=s.replace('Crear Cuenta','Crear cuenta')
    s=s.replace('<form v-if="activeTab === \'register\'" @submit.prevent="handleRegister" class="space-y-8">','<form v-if="activeTab === \'register\'" @submit.prevent="handleRegister" class="space-y-8">\n          <p class="text-sm text-gray-600 leading-relaxed">Crear una cuenta no activa las tarifas profesionales. Si tienes una tienda, <router-link to="/acceso-empresarial" class="underline">solicita el alta de empresa</router-link> para que revisemos tu acceso.</p>')
    s=s.replace('Acepto los términos y condiciones y la política de privacidad de Ibernovia','He leído el <router-link to="/legal" class="underline">aviso legal</router-link> y la <router-link to="/privacidad" class="underline">política de privacidad</router-link> de Ibernovia')
    return s
edit('views/AuthView.vue',auth)

def alta(s):
    start=s.index('    <section class="b2b-header">'); end=s.index('    <section class="b2b-content">',start)
    s=s[:start]+'''    <section class="b2b-header">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 class="b2b-title">Alta profesional</h1>
        <p class="b2b-intro">Para tiendas y profesionales del sector. Revisamos tu solicitud en un plazo habitual de 24 a 48 horas laborables.</p>
        <p class="mt-4 text-sm">¿Ya tienes cuenta? <router-link to="/auth" class="underline">Iniciar sesión</router-link></p>
      </div>
    </section>

'''+s[end:]
    s=re.sub(r'        <ol class="b2b-process-strip".*?</ol>\n','',s,count=1,flags=re.S)
    s=s.replace('<p class="b2b-section-label">Formulario</p>','').replace('Atencion directa','Atención directa')
    return s
edit('views/AccesoEmpresarialView.vue',alta)

def contact(s):
    s=re.sub(r'        <span[^>]*>ATELIER IBERNOVIA</span>\n','',s)
    for a,b in {'Nuestras Oficinas':'Datos de contacto','Contacto Digital':'Correo electrónico','Direccion':'Dirección','Telefono':'Teléfono','Horarios de Atencion':'Horario de atención','Escribenos':'Escríbenos','Correo electronico':'Correo electrónico','Sabados y Domingos':'Sábados y domingos','Lunes a Viernes':'Lunes a viernes','consulta mas concreta':'consulta más concreta','sobre la coleccion':'sobre la colección','tu direccion de correo':'tu dirección de correo','Escribe aqui':'Escribe aquí','Enviar Mensaje':'Enviar mensaje'}.items():s=s.replace(a,b)
    s=s.replace('>953 51 50 70</p>','><a href="tel:+34953515070">953 51 50 70</a></p>')
    s=s.replace('grid-cols-1 lg:grid-cols-12','grid-cols-1 md:grid-cols-12').replace('lg:col-span-5','md:col-span-5').replace('lg:col-span-7','md:col-span-7')
    return s
edit('views/ContactoView.vue',contact)

def product(s):
    s=s.replace('product-gallery aspect-[4/5]','product-gallery aspect-[3/2]')
    a=s.index('          <!-- Botón Badge -->');b=s.index('        </div>',a)
    # Replace the whole badge block, keeping the gallery column closing tag.
    b=s.index('\n          </div>',a)+len('\n          </div>')
    s=s[:a]+'''          <p class="text-sm text-gray-600">
            <router-link :to="{path:'/tienda',query:{familia:producto.familia}}" class="underline">{{ producto.familia }}</router-link>
            <span v-if="producto.categoria"> · {{ producto.categoria }}</span>
          </p>'''+s[b:]
    a=s.index('            <div v-else class="rounded-lg');b=s.index('\n            </div>',a)+len('\n            </div>')
    s=s[:a]+s[b:]
    a=s.index('          <!-- Commercial reference -->');b=s.index('          <!-- Selector de Cantidad',a)
    s=s[:a]+s[b:]
    marker='            </h1>'
    s=s.replace(marker,marker+'\n            <p class="text-sm text-gray-600 mb-4">Referencia: {{ productReference(producto) }}<span v-if="authStore.canSeePrices"> · {{ producto.stock }} unidades disponibles</span></p>',1)
    a=s.index('          <!-- Info comercial -->');b=s.index('\n        </div>\n      </div>',a)
    s=s[:a]+'''          <p v-if="!authStore.canSeePrices" class="text-sm text-gray-600 mt-3 leading-relaxed">Las tarifas requieren una cuenta profesional validada. <router-link to="/acceso-empresarial" class="underline">Solicitar alta</router-link>.</p>'''+s[b:]
    s=s.replace('Acceso profesional: tarifas y condiciones','Acceso profesional').replace('<div class="h-0.5 w-12 bg-luxury-gold mx-auto"></div>','')
    return s
edit('views/ProductoView.vue',product)

def catalog(s):
    s=s.replace('Buscar por imagen con IA','Buscar por foto').replace('Analizando imagen con IA...','Buscando productos similares...')
    s=s.replace('title="Buscar por foto"','title="Buscar productos similares con una foto"')
    s=s.replace('class="hidden"\n                >','class="sr-only" aria-label="Buscar productos similares con una foto"\n                >',1)
    return s
edit('views/TiendaView.vue',catalog)

def nav(s):
    s=s.replace('v-if="mobileOpen" class="fixed inset-0 z-[9998] bg-black/45 backdrop-blur-sm"','v-if="mobileOpen" class="mobile-nav-overlay fixed inset-0 z-[9998] bg-black/30"')
    s=s.replace('class="bg-[#ececec] w-11/12 max-w-sm mt-10 mx-auto rounded-md shadow-xl border border-black/10 max-h-[84vh] overflow-hidden flex flex-col"','class="mobile-nav-panel bg-white w-11/12 max-w-sm ml-auto h-full overflow-hidden flex flex-col"')
    s=s.replace('p-4 overflow-y-auto max-h-[70vh] space-y-4','p-4 overflow-y-auto flex-1 space-y-4')
    s=s.replace('class="relative z-[71]"','class="relative z-[71]" :class="{ \'sm:hidden\': !authStore.isAuthenticated }"',1)
    return s
edit('components/NavBar.vue',nav)

def events(s):
    s=s.replace('bg-black/50 backdrop-blur-sm','events-overlay bg-black/30')
    s=s.replace('¿Deseas asistir a alguno de nuestros desfiles o presentaciones? Solicita tu acreditación profesional.','Para consultar información sobre la agenda, contacta con Ibernovia.')
    s=s.replace('to="/acceso-empresarial"','to="/contacto"').replace('Solicitar acceso','Contacto')
    return s
edit('components/EventsModal.vue',events)

def cart(s):
    s=s.replace('class="min-h-screen bg-white py-12 md:py-16"','class="quote-page min-h-screen bg-white py-12 md:py-16"',1)
    s=s.replace('Tu Carrito','Solicitud de presupuesto').replace('Continuar Comprando','Seguir viendo productos').replace('to="/"','to="/tienda"').replace('object-cover','object-contain')
    s=s.replace('📋 Solicitar Presupuesto','Solicitar presupuesto')
    a=s.index('              <div class="flex justify-between text-sm">\n                <span class="text-gray-600">Envío');b=s.index('\n            </div>',a)
    s=s[:a]+s[b:]
    a=s.index('            <!-- Info de Envío -->');b=s.index('\n          </div>\n        </div>',a)
    s=s[:a]+'''            <p class="mt-6 text-sm text-gray-600 leading-relaxed">El envío, los impuestos y las condiciones se detallarán en el presupuesto. Esta solicitud no realiza ningún pago.</p>'''+s[b:]
    s=s.replace('>Total</span>','>Importe de productos</span>')
    return s
edit('views/CarritoView.vue',cart)

for name in ['PrivacidadView.vue','CookiesView.vue','LegalView.vue']:
    edit('views/'+name,lambda s:s.replace('<section class="min-h-screen','<section class="reading-page min-h-screen'))
edit('views/CuentaView.vue',lambda s:s.replace('<div class="min-h-screen','<div class="account-page min-h-screen',1).replace('Mi Cuenta','Mi cuenta').replace('Cerrar Sesión','Cerrar sesión').replace('      <div v-if="authStore.user"','      <p class="text-sm text-gray-600 mb-6">Datos de tu cuenta. Para consultas sobre solicitudes, <router-link to="/contacto" class="underline">contacta con Ibernovia</router-link>.</p>\n      <div v-if="authStore.user"'))
edit('views/AdminView.vue',lambda s:s.replace('Una vista limpia para gestionar catálogo, usuarios, eventos y solicitudes con más claridad y menos ruido visual.','Catálogo, usuarios y solicitudes.'))
