<template>
  <!-- Hero Section: Minimal Modern -->
  <section class="hero-shell relative w-full overflow-hidden">
    <div class="relative max-w-7xl mx-auto px-6 sm:px-8 py-20 sm:py-24 lg:py-28">
      <div class="grid grid-cols-1 lg:grid-cols-[1.05fr_0.95fr] gap-10 lg:gap-12 items-center">
        <div class="animate-fade-in-up">
          <div class="inline-flex items-center gap-3 mb-6">
            <span class="h-px w-10 bg-luxury-gold"></span>
            <span class="text-xs uppercase tracking-[0.35em] text-luxury-gold font-semibold">Andujar · Jaen</span>
          </div>
          <h1 class="font-serif text-5xl sm:text-6xl lg:text-7xl leading-[1.05] text-luxury-black mb-6">
            Catálogo mayorista
            <span class="text-luxury-gold">para profesionales</span>
          </h1>
          <p class="text-base sm:text-xl text-gray-600 max-w-xl mb-8 sm:mb-10">
            Mostramos nuestra colección para inspiración. Precios y pedidos únicamente para empresas registradas.
          </p>

          <div class="flex flex-col sm:flex-row gap-3 sm:gap-4 items-start sm:items-center">
            <router-link
              to="/tienda"
              class="group relative bg-luxury-black text-white px-8 sm:px-9 py-4 font-semibold uppercase tracking-[0.2em] text-xs sm:text-sm hover:bg-luxury-gold hover:text-luxury-black transition-all duration-300 overflow-hidden rounded focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
            >
              <span class="relative z-10">Ver catálogo</span>
              <div class="absolute inset-0 bg-luxury-gold/30 transform -translate-x-full group-hover:translate-x-0 transition-transform duration-300"></div>
            </router-link>
            <router-link
              to="/acceso-empresarial"
              class="border border-luxury-black/20 text-luxury-black px-8 sm:px-9 py-4 uppercase tracking-[0.2em] text-xs sm:text-sm font-semibold rounded hover:border-luxury-black hover:bg-luxury-black hover:text-white transition-all duration-300 focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
            >
              Solicitar acceso profesional
            </router-link>
          </div>

          <div class="mt-8 sm:mt-10 grid grid-cols-2 sm:grid-cols-3 gap-4 text-[11px] sm:text-xs uppercase tracking-[0.2em] text-gray-500">
            <div class="rounded-2xl border border-white/70 bg-white/80 px-5 py-4 shadow-sm">
              <p class="text-luxury-black font-semibold">Distribución B2B</p>
              <p class="mt-1">Andujar · Jaen</p>
            </div>
            <div class="rounded-2xl border border-white/70 bg-white/80 px-5 py-4 shadow-sm">
              <p class="text-luxury-black font-semibold">Tarifa profesional</p>
              <p class="mt-1">Acceso verificado</p>
            </div>
            <div class="rounded-2xl border border-white/70 bg-white/80 px-5 py-4 shadow-sm">
              <p class="text-luxury-black font-semibold">Atención comercial</p>
              <p class="mt-1">Soporte personalizado</p>
            </div>
          </div>
        </div>

        <div class="relative">
          <div class="absolute -inset-6 rounded-[32px] bg-luxury-gold/15 blur-2xl"></div>
          <div class="relative rounded-[28px] border border-white/70 bg-white/90 p-4 sm:p-6 shadow-2xl">
            <img
              src="https://images.unsplash.com/photo-1519741497674-611481863552?q=80&w=2070&auto=format&fit=crop"
              alt="Novia Ibernovia"
              class="h-[320px] sm:h-[380px] w-full rounded-[22px] object-cover"
              loading="eager"
              decoding="async"
              fetchpriority="high"
              width="2070"
              height="1380"
            />
            <div class="mt-5 flex items-center justify-between gap-4 text-xs uppercase tracking-[0.25em] text-gray-500">
              <span>Plaza de Abastos</span>
              <span class="text-luxury-gold">Desde 2015</span>
            </div>
            <div class="absolute -left-6 top-8 hidden sm:block">
              <div class="rounded-full border border-luxury-gold/40 bg-white/90 px-4 py-2 text-[10px] uppercase tracking-[0.35em] text-luxury-black shadow-lg">
                Atelier
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="hidden md:block">
      <div class="max-w-6xl mx-auto px-6 pb-10">
        <div class="grid grid-cols-3 gap-4 bg-white/70 backdrop-blur border border-white/70 rounded-2xl px-6 py-5 text-luxury-black text-sm shadow-sm">
          <div class="flex items-center gap-3">
            <span class="text-luxury-gold text-lg">★</span>
            <span>Venta exclusiva para empresas</span>
          </div>
          <div class="flex items-center gap-3">
            <span class="text-luxury-gold text-lg">◆</span>
            <span>Catálogo visible para público general</span>
          </div>
          <div class="flex items-center gap-3">
            <span class="text-luxury-gold text-lg">●</span>
            <span>Precios y pedidos con acceso profesional</span>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- Caracteristicas Destacadas -->
  <section class="py-16 sm:py-20 bg-white border-b">
    <div class="max-w-7xl mx-auto px-6">
      <div class="text-center mb-10 sm:mb-12">
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Experiencia Ibernovia</span>
        <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Lujo cercano, detalle perfecto</h2>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div class="group hover-lift rounded-3xl p-7 border border-gray-100 bg-gradient-to-b from-white to-gray-50">
          <div class="inline-flex items-center justify-center w-14 h-14 mb-5 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
            <svg class="w-7 h-7 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
            </svg>
          </div>
          <h3 class="font-serif text-xl mb-2">Seleccion premium</h3>
          <p class="text-gray-600 text-sm">Materiales nobles y acabados artesanales para un toque inolvidable.</p>
        </div>
        <div class="group hover-lift rounded-3xl p-7 border border-gray-100 bg-gradient-to-b from-white to-gray-50">
          <div class="inline-flex items-center justify-center w-14 h-14 mb-5 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
            <svg class="w-7 h-7 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
          </div>
          <h3 class="font-serif text-xl mb-2">Entrega rapida</h3>
          <p class="text-gray-600 text-sm">Envio 24-48h con packaging premium listo para regalar.</p>
        </div>
        <div class="group hover-lift rounded-3xl p-7 border border-gray-100 bg-gradient-to-b from-white to-gray-50">
          <div class="inline-flex items-center justify-center w-14 h-14 mb-5 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
            <svg class="w-7 h-7 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
            </svg>
          </div>
          <h3 class="font-serif text-xl mb-2">Atencion curada</h3>
          <p class="text-gray-600 text-sm">Asesoramiento comercial personalizado para profesionales.</p>
        </div>
      </div>
    </div>
  </section>

  <!-- Experiencia profesional -->
  <section class="py-16 sm:py-20 bg-white">
    <div class="max-w-7xl mx-auto px-6 grid grid-cols-1 lg:grid-cols-2 gap-10 lg:gap-12 items-center">
      <div>
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Andújar · Jaén</span>
        <h2 class="font-serif text-4xl md:text-5xl text-luxury-black mb-6">Proveedor mayorista para empresas</h2>
        <p class="text-gray-600 text-lg mb-6">
          Trabajamos con profesionales y comercios especializados en moda nupcial con catálogo actualizado y soporte comercial.
        </p>
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 text-sm">
          <div class="p-4 border rounded-lg">
            <p class="font-bold text-luxury-black">Ubicación</p>
            <p class="text-gray-600">C. Ollerías, 48, 23740 Andújar, Jaén</p>
          </div>
          <div class="p-4 border rounded-lg">
            <p class="font-bold text-luxury-black">Horario</p>
            <p class="text-gray-600 text-xs leading-relaxed">
              Lunes a Jueves: 9:00 a 13:45 y 17:00 a 20:00<br>
              Viernes: 9:00 a 13:45 (Tardes cerrado)
            </p>
          </div>

        </div>
      </div>
      <div class="relative">
        <div class="absolute -inset-4 bg-luxury-gold/10 rounded-3xl"></div>
        <img
          src="https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=1974&auto=format&fit=crop"
          alt="Atelier Ibernovia"
          class="relative rounded-3xl shadow-xl w-full object-cover h-[280px] sm:h-[320px] lg:h-[360px]"
          loading="lazy"
          decoding="async"
        />
      </div>
    </div>
  </section>

  <!-- Lookbook -->
  <section class="py-16 sm:py-20 bg-luxury-gray">
    <div class="max-w-7xl mx-auto px-6">
      <div class="flex flex-col lg:flex-row lg:items-end lg:justify-between gap-6 mb-8 sm:mb-10">
        <div>
          <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Lookbook 2026</span>
          <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Inspiracion para tu dia</h2>
          <p class="text-gray-600 mt-3 max-w-xl">Piezas que capturan luz, textura y movimiento con un equilibrio entre tradicion y modernidad.</p>
        </div>
        <router-link to="/tienda" class="uppercase tracking-widest text-[11px] font-bold text-luxury-black border border-luxury-black px-6 py-3 hover:bg-luxury-black hover:text-white transition">Ver catalogo</router-link>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="relative overflow-hidden rounded-3xl shadow-lg">
          <img
            src="/images/optimized/complemento-12-960.jpg"
            srcset="/images/optimized/complemento-12-480.jpg 480w, /images/optimized/complemento-12-960.jpg 960w, /images/optimized/complemento-12-1440.jpg 1440w"
            sizes="(max-width: 768px) 100vw, (max-width: 1200px) 50vw, 33vw"
            alt="Complementos Ibernovia"
            class="h-64 sm:h-72 lg:h-80 w-full object-cover"
            loading="lazy"
            decoding="async"
            width="1200"
            height="800"
          />
          <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
          <p class="absolute bottom-4 left-4 text-white text-sm uppercase tracking-[0.3em]">Complementos</p>
        </div>
        <div class="relative overflow-hidden rounded-3xl shadow-lg hidden sm:block">
          <img
            src="/images/optimized/complemento-45-960.jpg"
            srcset="/images/optimized/complemento-45-480.jpg 480w, /images/optimized/complemento-45-960.jpg 960w, /images/optimized/complemento-45-1440.jpg 1440w"
            sizes="(max-width: 768px) 100vw, (max-width: 1200px) 50vw, 33vw"
            alt="Detalles Ibernovia"
            class="h-64 sm:h-72 lg:h-80 w-full object-cover"
            loading="lazy"
            decoding="async"
            width="1200"
            height="800"
          />
          <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
          <p class="absolute bottom-4 left-4 text-white text-sm uppercase tracking-[0.3em]">Detalles</p>
        </div>
        <div class="relative overflow-hidden rounded-3xl shadow-lg hidden md:block">
          <img
            src="/images/optimized/complemento-90-960.jpg"
            srcset="/images/optimized/complemento-90-480.jpg 480w, /images/optimized/complemento-90-960.jpg 960w, /images/optimized/complemento-90-1440.jpg 1440w"
            sizes="(max-width: 768px) 100vw, (max-width: 1200px) 50vw, 33vw"
            alt="Acabados Ibernovia"
            class="h-64 sm:h-72 lg:h-80 w-full object-cover"
            loading="lazy"
            decoding="async"
            width="1200"
            height="800"
          />
          <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
          <p class="absolute bottom-4 left-4 text-white text-sm uppercase tracking-[0.3em]">Acabados</p>
        </div>
      </div>
    </div>
  </section>

  <section id="coleccion" class="catalog-shell w-full px-6 sm:px-8 lg:px-10 py-20 sm:py-24">
    <div class="max-w-7xl mx-auto">
      <div class="text-center mb-12 sm:mb-16">
        <span class="text-luxury-gold text-xs font-semibold tracking-[0.35em] uppercase block mb-3">Catalogo 2026</span>
        <h2 class="font-serif text-4xl sm:text-5xl md:text-6xl lg:text-7xl text-luxury-black">Nuestros complementos</h2>
        <div class="mt-5 flex items-center justify-center gap-4">
          <span class="h-px w-14 bg-luxury-gold/60"></span>
          <span class="h-2 w-2 rounded-full bg-luxury-gold"></span>
          <span class="h-px w-14 bg-luxury-gold/60"></span>
        </div>
        <p class="mt-6 text-gray-600 max-w-2xl mx-auto text-xl">
          Cada pieza esta cuidadosamente diseñada para añadir ese toque de distincion que hara de tu dia algo inolvidable.
        </p>
      </div>

      <!-- Estado de Carga -->
      <div v-if="loading" class="text-center py-20">
        <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-luxury-gold border-t-transparent"></div>
        <p class="mt-4 text-gray-500 font-light tracking-widest text-sm">CARGANDO COLECCIÓN...</p>
      </div>

      <!-- Estado de Error -->
      <div v-else-if="error" class="text-center py-20 text-red-500">
        <p class="font-bold text-lg mb-2">⚠️ Error de Conexión</p>
        <p class="text-sm md:text-base">No se puede conectar con el servidor (Backend).</p>
        <p class="text-xs mt-4 font-mono bg-red-50 inline-block px-4 py-2 rounded border border-red-100">{{ errorDetalle }}</p>
        <p class="text-xs mt-4 text-gray-400">Asegúrate de tener abierta la terminal con Spring Boot.</p>
      </div>

      <!-- Contenido Principal -->
      <div v-else>
        <!-- Filtros: Familias + Subfamilias -->
        <div class="flex flex-wrap justify-center gap-3 md:gap-4 mb-6 text-xs uppercase tracking-[0.25em] font-semibold">
          <button
            @click="cambiarFamilia('Todas')"
            :aria-pressed="familiaSeleccionada === 'Todas' ? 'true' : 'false'"
            :class="familiaSeleccionada === 'Todas' ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
            class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white">
            Todas
          </button>
          <button
            v-for="fam in familias"
            :key="fam"
            @click="cambiarFamilia(fam)"
            :aria-pressed="familiaSeleccionada === fam ? 'true' : 'false'"
            :class="familiaSeleccionada === fam ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
            class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white">
            {{ fam }}
          </button>
        </div>

        <div id="categorias" class="flex flex-wrap justify-center gap-3 md:gap-4 mb-16 text-xs uppercase tracking-[0.25em] font-semibold">
          <button
            @click="cambiarCategoria('Todos')"
            :aria-pressed="categoriaSeleccionada === 'Todos' ? 'true' : 'false'"
            :class="categoriaSeleccionada === 'Todos' ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
            class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white">
            Todas
          </button>
          <button
            v-for="cat in categorias"
            :key="cat"
            @click="cambiarCategoria(cat)"
            :aria-pressed="categoriaSeleccionada === cat ? 'true' : 'false'"
            :class="categoriaSeleccionada === cat ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
            class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white">
            {{ cat }}
          </button>
        </div>

        <!-- Grid de Productos - Responsive -->
        <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 md:gap-6 lg:gap-8">
          <ProductCard 
            v-for="prod in productosVisibles" 
            :key="prod.id" 
            :producto="prod" 
          />
        </div>

        <!-- Botón Cargar Más -->
        <div v-if="hayMasProductos" class="text-center mt-12 md:mt-20">
          <button 
            @click="cargarMas"
            class="border border-gray-300 px-8 md:px-12 py-3 md:py-4 text-xs uppercase tracking-widest hover:border-luxury-black hover:bg-luxury-black hover:text-white transition duration-500 rounded">
            Ver más productos ({{ productosRestantes }})
          </button>
        </div>
        
        <div class="text-center mt-6 md:mt-8 text-gray-400 text-xs tracking-widest">
          Mostrando {{ productosVisibles.length }} de {{ productosFiltrados.length }} productos
        </div>
      </div>
    </div>
  </section>

  <!-- Garantias -->
  <section class="py-16 sm:py-20 bg-white">
    <div class="max-w-7xl mx-auto px-6">
      <div class="text-center mb-10 sm:mb-12">
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Operativa B2B</span>
        <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Soporte para clientes profesionales</h2>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="p-7 border rounded-3xl shadow-sm bg-gradient-to-b from-white to-gray-50">
          <p class="text-gray-600">Validación de cuentas profesionales para proteger tarifas y condiciones comerciales.</p>
          <p class="mt-6 font-semibold text-luxury-black">Acceso controlado</p>
        </div>
        <div class="p-7 border rounded-3xl shadow-sm bg-gradient-to-b from-white to-gray-50">
          <p class="text-gray-600">Atención comercial directa para incidencias, reposición y asesoramiento de compra.</p>
          <p class="mt-6 font-semibold text-luxury-black">Soporte comercial</p>
        </div>
        <div class="p-7 border rounded-3xl shadow-sm bg-gradient-to-b from-white to-gray-50">
          <p class="text-gray-600">Catálogo actualizado y condiciones orientadas a negocio y volumen profesional.</p>
          <p class="mt-6 font-semibold text-luxury-black">Relación a largo plazo</p>
        </div>
      </div>
    </div>
  </section>

  <!-- CTA final -->
  <section class="py-16 sm:py-20 bg-luxury-black text-white">
    <div class="max-w-7xl mx-auto px-6 text-center">
      <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl mb-4">¿Eres profesional del sector?</h2>
      <p class="text-white/80 max-w-2xl mx-auto mb-8">Solicita tu alta empresarial y accede a tarifas, stock y pedidos mayoristas.</p>
      <div class="flex flex-col sm:flex-row gap-4 justify-center">
        <router-link to="/acceso-empresarial" class="bg-luxury-gold text-luxury-black px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-white transition">Solicitar alta</router-link>
        <router-link to="/tienda" class="border border-white px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-white hover:text-luxury-black transition">Ver catálogo</router-link>
      </div>
    </div>
  </section>

  <!-- RESEÑAS -->
  <section class="py-16 sm:py-20 bg-white">
    <div class="max-w-7xl mx-auto px-6">
      <div class="text-center mb-10 sm:mb-12">
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Profesionales</span>
        <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Lo que dicen nuestros clientes B2B</h2>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="p-7 border rounded-lg bg-gradient-to-b from-white to-gray-50 hover:shadow-lg transition">
          <div class="flex gap-1 mb-4">
            <span v-for="i in 5" :key="i" class="text-luxury-gold text-lg">★</span>
          </div>
          <p class="text-gray-600 mb-4 italic">"Nos resolvieron catálogo y reposición de forma ágil. La atención comercial es excelente."</p>
          <p class="font-bold text-luxury-black text-sm">Boutique Luna</p>
          <p class="text-gray-500 text-xs">Sevilla</p>
        </div>
        <div class="p-7 border rounded-lg bg-gradient-to-b from-white to-gray-50 hover:shadow-lg transition">
          <div class="flex gap-1 mb-4">
            <span v-for="i in 5" :key="i" class="text-luxury-gold text-lg">★</span>
          </div>
          <p class="text-gray-600 mb-4 italic">"Desde el alta profesional tenemos visibilidad de tarifas y trabajamos mejor cada campaña."</p>
          <p class="font-bold text-luxury-black text-sm">Ceremonia Élite</p>
          <p class="text-gray-500 text-xs">Madrid</p>
        </div>
        <div class="p-7 border rounded-lg bg-gradient-to-b from-white to-gray-50 hover:shadow-lg transition">
          <div class="flex gap-1 mb-4">
            <span v-for="i in 5" :key="i" class="text-luxury-gold text-lg">★</span>
          </div>
          <p class="text-gray-600 mb-4 italic">"Un proveedor serio para negocio: catálogo cuidado, soporte constante y comunicación clara."</p>
          <p class="font-bold text-luxury-black text-sm">Atelier Sur</p>
          <p class="text-gray-500 text-xs">Jaén</p>
        </div>
      </div>
    </div>
  </section>

  <!-- CONDICIONES PROFESIONALES -->
  <section class="py-16 sm:py-20 bg-luxury-gold/5 border-y border-luxury-gold/20">
    <div class="max-w-7xl mx-auto px-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8 items-center">
        <div>
          <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Condiciones</span>
          <h2 class="font-serif text-4xl md:text-5xl text-luxury-black mb-4">Trabajamos con empresas</h2>
          <p class="text-xl text-luxury-gold font-bold mb-2">Alta y validación profesional</p>
          <p class="text-gray-600 mb-6">Solicita acceso y habilitaremos tu perfil para consultar precios, stock y gestión de pedidos.</p>
          <router-link to="/acceso-empresarial" class="inline-block bg-luxury-black text-white px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-luxury-gold hover:text-luxury-black transition">Solicitar alta</router-link>
        </div>
        <div class="bg-white rounded-3xl p-8 shadow-lg border border-luxury-gold/20">
          <p class="text-center text-4xl font-bold text-luxury-gold mb-2">24/48h</p>
          <p class="text-center text-gray-600 text-sm mb-4">Tiempo estimado de validación comercial</p>
          <p class="text-center text-gray-500 text-xs">Recibirás confirmación por email tras revisar tus datos.</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { apiClient } from '../lib/api'
import ProductCard from '../components/ProductCard.vue'

const logoBase = import.meta.env.BASE_URL + 'logo/optimized/'
const logoSrc = `${logoBase}logo-140.jpg`
const logoSrcSet = `${logoBase}logo-80.jpg 80w, ${logoBase}logo-140.jpg 140w, ${logoBase}logo-280.jpg 280w`

const productos = ref([])
const loading = ref(true)
const error = ref(false)
const errorDetalle = ref('')

const familiaSeleccionada = ref('Todas')
const categoriaSeleccionada = ref('Todos')
const paginaActual = ref(1)
const productosPorPagina = 12

const familias = computed(() => {
  const set = new Set(productos.value.map((p) => p.familia).filter(Boolean))
  const list = Array.from(set)

  const preferred = ['Novia', 'Novio', 'Fiesta', 'Comunión', 'Arras']
  return list.sort((a, b) => {
    const ia = preferred.indexOf(a)
    const ib = preferred.indexOf(b)
    if (ia !== -1 || ib !== -1) {
      return (ia === -1 ? 999 : ia) - (ib === -1 ? 999 : ib)
    }
    return a.localeCompare(b)
  })
})

const categorias = computed(() => {
  const base = familiaSeleccionada.value === 'Todas'
    ? productos.value
    : productos.value.filter((p) => p.familia === familiaSeleccionada.value)

  const counts = new Map()
  for (const p of base) {
    const cat = p?.categoria
    if (!cat) continue
    counts.set(cat, (counts.get(cat) || 0) + 1)
  }

  // Mostramos solo las subfamilias más frecuentes para que el menú quede limpio
  return Array.from(counts.entries())
    .sort((a, b) => (b[1] - a[1]) || a[0].localeCompare(b[0]))
    .slice(0, 8)
    .map(([cat]) => cat)
})

onMounted(async () => {
  try {
    loading.value = true
    const res = await apiClient.get('/api/productos')
    if (Array.isArray(res.data)) {
      productos.value = res.data
    } else {
      throw new Error('Formato de datos inválido')
    }
    loading.value = false
  } catch (e) {
    console.error('Error cargando productos:', e)
    error.value = true
    errorDetalle.value = e.message
    loading.value = false
  }
})

const cambiarFamilia = (fam) => {
  familiaSeleccionada.value = fam
  categoriaSeleccionada.value = 'Todos'
  paginaActual.value = 1
}

const cambiarCategoria = (cat) => {
  categoriaSeleccionada.value = cat
  paginaActual.value = 1
}

const productosFiltrados = computed(() => {
  const base = familiaSeleccionada.value === 'Todas'
    ? productos.value
    : productos.value.filter((p) => p.familia === familiaSeleccionada.value)

  if (categoriaSeleccionada.value === 'Todos') {
    return base
  }

  return base.filter((p) => p.categoria === categoriaSeleccionada.value)
})

const productosVisibles = computed(() => {
  const inicio = 0
  const fin = paginaActual.value * productosPorPagina
  return productosFiltrados.value.slice(inicio, fin)
})

const hayMasProductos = computed(() => {
  return productosVisibles.value.length < productosFiltrados.value.length
})

const productosRestantes = computed(() => {
  return productosFiltrados.value.length - productosVisibles.value.length
})

const cargarMas = () => {
  paginaActual.value++
}
</script>

<style scoped>
.hero-shell::after {
  content: '';
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 18% 18%, rgba(196, 160, 106, 0.2), transparent 45%),
    radial-gradient(circle at 85% 12%, rgba(255, 255, 255, 0.6), transparent 35%),
    linear-gradient(135deg, rgba(196, 160, 106, 0.12) 0%, transparent 40%, rgba(196, 160, 106, 0.08) 100%);
  pointer-events: none;
  mix-blend-mode: normal;
}

.catalog-shell {
  position: relative;
  background: linear-gradient(180deg, rgba(251, 248, 242, 0.9), rgba(246, 242, 237, 0.75));
}

.catalog-shell::before {
  content: '';
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 12% 20%, rgba(196, 160, 106, 0.12), transparent 40%),
    radial-gradient(circle at 90% 30%, rgba(196, 160, 106, 0.08), transparent 40%);
  pointer-events: none;
}
</style>
