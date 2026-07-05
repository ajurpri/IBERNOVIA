<template>
  <div class="home-shell">
    <section class="home-hero">
      <div class="home-glow home-glow-left"></div>
      <div class="home-glow home-glow-right"></div>

      <div class="max-w-7xl mx-auto px-6 sm:px-8 py-20 sm:py-24 lg:py-28">
        <div class="grid grid-cols-1 lg:grid-cols-[1.06fr_0.94fr] gap-10 lg:gap-14 items-center">
          <div class="animate-rise">
            <div class="inline-flex items-center gap-3 mb-6">
              <span class="h-px w-10 bg-luxury-gold"></span>
              <span class="text-xs uppercase tracking-[0.35em] text-luxury-gold font-semibold">Andujar · Jaen</span>
            </div>

            <h1 class="home-title">
              Coleccion profesional
              <span class="text-luxury-gold">para ceremonia y complemento</span>
            </h1>

            <p class="home-intro">
              Ibernovia presenta su catalogo como una herramienta de inspiracion para publico general y de trabajo para empresas registradas. La parte profesional activa tarifas y relacion comercial con una cuenta validada.
            </p>

            <div class="flex flex-col sm:flex-row gap-3 sm:gap-4 items-start sm:items-center">
              <router-link
                to="/tienda"
                class="group relative bg-luxury-black text-white px-8 sm:px-9 py-4 font-semibold uppercase tracking-[0.2em] text-xs sm:text-sm hover:bg-luxury-gold hover:text-luxury-black transition-all duration-300 overflow-hidden rounded focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
              >
                <span class="relative z-10">Ver catalogo</span>
                <div class="absolute inset-0 bg-luxury-gold/30 transform -translate-x-full group-hover:translate-x-0 transition-transform duration-300"></div>
              </router-link>

              <router-link
                to="/acceso-empresarial"
                class="border border-luxury-black/20 text-luxury-black px-8 sm:px-9 py-4 uppercase tracking-[0.2em] text-xs sm:text-sm font-semibold rounded hover:border-luxury-black hover:bg-luxury-black hover:text-white transition-all duration-300 focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
              >
                Solicitar acceso profesional
              </router-link>

              <button
                type="button"
                class="text-luxury-black/75 uppercase tracking-[0.24em] text-xs sm:text-sm font-semibold inline-flex items-center gap-3 px-2 py-2 transition hover:text-luxury-gold focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
                @click="openConcierge('Busco recomendacion para mi tienda o para una clienta')"
              >
                <span class="inline-flex h-10 w-10 items-center justify-center rounded-full border border-luxury-gold/35 bg-white/70 text-luxury-gold shadow-sm">IA</span>
                Hablar con concierge
              </button>
            </div>

            <div class="home-stat-row">
              <div class="home-stat-card">
                <p class="home-stat-title">Catalogo visible</p>
                <p class="home-stat-copy">Consulta publica de coleccion con lectura mas clara de familias y categorias.</p>
              </div>
              <div class="home-stat-card">
                <p class="home-stat-title">Acceso profesional</p>
                <p class="home-stat-copy">Tarifas y condiciones reservadas a perfiles comerciales validados.</p>
              </div>
              <div class="home-stat-card">
                <p class="home-stat-title">Atencion Ibernovia</p>
                <p class="home-stat-copy">Trato directo desde atelier para una relacion mas cuidada con la marca.</p>
              </div>
            </div>
          </div>

          <div class="relative animate-rise delay-100">
            <div class="absolute -inset-6 rounded-[32px] bg-luxury-gold/15 blur-2xl"></div>
            <div class="home-image-card">
              <img
                src="https://images.unsplash.com/photo-1519741497674-611481863552?q=80&w=2070&auto=format&fit=crop"
                alt="Coleccion Ibernovia"
                class="home-hero-image"
                loading="eager"
                decoding="async"
                fetchpriority="high"
                width="2070"
                height="1380"
              />
              <div class="home-image-meta">
                <span>Plaza de Abastos · Andujar</span>
                <span class="text-luxury-gold">Atelier Ibernovia</span>
              </div>
              <div class="absolute -left-6 top-8 hidden sm:block">
                <div class="rounded-full border border-luxury-gold/40 bg-white/90 px-4 py-2 text-[10px] uppercase tracking-[0.35em] text-luxury-black shadow-lg">
                  Ceremonia · B2B
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
              <span>Condiciones reservadas a profesionales</span>
            </div>
            <div class="flex items-center gap-3">
              <span class="text-luxury-gold text-lg">◆</span>
              <span>Catalogo visible para consulta general</span>
            </div>
            <div class="flex items-center gap-3">
              <span class="text-luxury-gold text-lg">●</span>
              <span>Presentacion cuidada de coleccion</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-16 sm:py-20 bg-white border-b">
      <div class="max-w-7xl mx-auto px-6">
        <div class="text-center mb-10 sm:mb-12 animate-rise">
          <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Experiencia Ibernovia</span>
          <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Una presencia mas serena, mas clara y mas de atelier</h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="group hover-lift rounded-3xl p-7 border border-gray-100 bg-gradient-to-b from-white to-gray-50">
            <div class="inline-flex items-center justify-center w-14 h-14 mb-5 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
              <svg class="w-7 h-7 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
              </svg>
            </div>
            <h3 class="font-serif text-xl mb-2">Seleccion curada</h3>
            <p class="text-gray-600 text-sm">Cada familia se presenta con una lectura mas limpia para que el catalogo respire mejor.</p>
          </div>

          <div class="group hover-lift rounded-3xl p-7 border border-gray-100 bg-gradient-to-b from-white to-gray-50">
            <div class="inline-flex items-center justify-center w-14 h-14 mb-5 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
              <svg class="w-7 h-7 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h3 class="font-serif text-xl mb-2">Lectura comercial clara</h3>
            <p class="text-gray-600 text-sm">La parte publica inspira y la parte profesional conserva la operativa reservada a empresa.</p>
          </div>

          <div class="group hover-lift rounded-3xl p-7 border border-gray-100 bg-gradient-to-b from-white to-gray-50">
            <div class="inline-flex items-center justify-center w-14 h-14 mb-5 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
              <svg class="w-7 h-7 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
              </svg>
            </div>
            <h3 class="font-serif text-xl mb-2">Atencion cuidada</h3>
            <p class="text-gray-600 text-sm">El tono de la web acompana la imagen del atelier sin exagerar ni prometer mas de la cuenta.</p>
          </div>
        </div>
      </div>
    </section>

    <section class="py-16 sm:py-20 bg-white">
      <div class="max-w-7xl mx-auto px-6 grid grid-cols-1 lg:grid-cols-2 gap-10 lg:gap-12 items-center">
        <div class="animate-rise">
          <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Andujar · Jaen</span>
          <h2 class="font-serif text-4xl md:text-5xl text-luxury-black mb-6">Una relacion comercial pensada para empresas del sector</h2>
          <p class="text-gray-600 text-lg mb-6">
            Ibernovia trabaja con profesionales y comercios especializados en ceremonia, novia y complemento desde una presentacion de coleccion mas ordenada y una atencion mas directa.
          </p>

          <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 text-sm">
            <div class="p-4 border rounded-lg">
              <p class="font-bold text-luxury-black">Ubicacion</p>
              <p class="text-gray-600">Plaza de Abastos, C. Ollerias, 48, 23740 Andujar, Jaen</p>
            </div>
            <div class="p-4 border rounded-lg">
              <p class="font-bold text-luxury-black">Horario</p>
              <p class="text-gray-600 text-xs leading-relaxed">
                Lunes a Viernes: 9:00 - 14:00 y 17:00 - 20:00
              </p>
            </div>
          </div>
        </div>

        <div class="relative animate-rise delay-100">
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

    <section class="py-16 sm:py-20 bg-luxury-gray">
      <div class="max-w-7xl mx-auto px-6">
        <div class="flex flex-col lg:flex-row lg:items-end lg:justify-between gap-6 mb-8 sm:mb-10">
          <div>
            <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Lookbook 2026</span>
            <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Una forma visual de entender la coleccion</h2>
            <p class="text-gray-600 mt-3 max-w-xl">Piezas, acabados y detalles presentados con una imagen mas editorial y mas alineada con la marca.</p>
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

    <section id="coleccion" class="preview-shell w-full px-6 sm:px-8 lg:px-10 py-20 sm:py-24">
      <div class="max-w-7xl mx-auto">
        <div class="text-center mb-12 sm:mb-16 animate-rise">
          <span class="text-luxury-gold text-xs font-semibold tracking-[0.35em] uppercase block mb-3">Catalogo 2026</span>
          <h2 class="font-serif text-4xl sm:text-5xl md:text-6xl lg:text-7xl text-luxury-black">Una seleccion representativa</h2>
          <div class="mt-5 flex items-center justify-center gap-4">
            <span class="h-px w-14 bg-luxury-gold/60"></span>
            <span class="h-2 w-2 rounded-full bg-luxury-gold"></span>
            <span class="h-px w-14 bg-luxury-gold/60"></span>
          </div>
          <p class="mt-6 text-gray-600 max-w-2xl mx-auto text-xl">
            Un acceso inicial a la coleccion para entender la linea visual de Ibernovia antes de entrar en el catalogo completo.
          </p>
        </div>

        <div v-if="loading" class="text-center py-20">
          <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-luxury-gold border-t-transparent"></div>
          <p class="mt-4 text-gray-500 font-light tracking-widest text-sm">Cargando seleccion...</p>
        </div>

        <div v-else-if="error" class="text-center py-20 text-red-500">
          <p class="font-bold text-lg mb-2">Error de conexion</p>
          <p class="text-sm md:text-base">No se puede cargar la seleccion de productos en este momento.</p>
          <p class="text-xs mt-4 font-mono bg-red-50 inline-block px-4 py-2 rounded border border-red-100">{{ errorDetalle }}</p>
        </div>

        <div v-else>
          <div class="flex flex-wrap justify-center gap-3 md:gap-4 mb-12 text-xs uppercase tracking-[0.25em] font-semibold">
            <button
              @click="cambiarFamilia('Todas')"
              :aria-pressed="familiaSeleccionada === 'Todas' ? 'true' : 'false'"
              :class="familiaSeleccionada === 'Todas' ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
              class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
            >
              Todas
            </button>
            <button
              v-for="fam in familias"
              :key="fam"
              @click="cambiarFamilia(fam)"
              :aria-pressed="familiaSeleccionada === fam ? 'true' : 'false'"
              :class="familiaSeleccionada === fam ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
              class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
            >
              {{ fam }}
            </button>
          </div>

          <div class="flex flex-wrap justify-center gap-3 md:gap-4 mb-14 text-xs uppercase tracking-[0.25em] font-semibold">
            <button
              @click="cambiarCategoria('Todos')"
              :aria-pressed="categoriaSeleccionada === 'Todos' ? 'true' : 'false'"
              :class="categoriaSeleccionada === 'Todos' ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
              class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
            >
              Todas
            </button>
            <button
              v-for="cat in categorias"
              :key="cat"
              @click="cambiarCategoria(cat)"
              :aria-pressed="categoriaSeleccionada === cat ? 'true' : 'false'"
              :class="categoriaSeleccionada === cat ? 'bg-luxury-black text-white shadow-lg' : 'bg-white/90 text-gray-700 hover:text-luxury-black hover:bg-white'"
              class="px-6 py-3 transition-all duration-300 rounded-full border border-white/80 shadow-sm whitespace-nowrap focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white"
            >
              {{ cat }}
            </button>
          </div>

          <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 md:gap-6 lg:gap-8">
            <ProductCard
              v-for="prod in productosVisibles"
              :key="prod.id"
              :producto="prod"
            />
          </div>

          <div v-if="hayMasProductos" class="text-center mt-12 md:mt-20">
            <button
              @click="cargarMas"
              class="border border-gray-300 px-8 md:px-12 py-3 md:py-4 text-xs uppercase tracking-widest hover:border-luxury-black hover:bg-luxury-black hover:text-white transition duration-500 rounded"
            >
              Ver mas productos ({{ productosRestantes }})
            </button>
          </div>

          <div class="text-center mt-6 md:mt-8 text-gray-400 text-xs tracking-widest">
            Mostrando {{ productosVisibles.length }} de {{ productosFiltrados.length }} productos
          </div>
        </div>
      </div>
    </section>

    <section class="py-16 sm:py-20 bg-white">
      <div class="max-w-7xl mx-auto px-6">
        <div class="text-center mb-10 sm:mb-12 animate-rise">
          <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Operativa B2B</span>
          <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Una relacion pensada para trabajar mejor la coleccion</h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div class="p-7 border rounded-3xl shadow-sm bg-gradient-to-b from-white to-gray-50">
            <p class="text-gray-600">Validacion de cuentas profesionales para proteger tarifas y condiciones comerciales.</p>
            <p class="mt-6 font-semibold text-luxury-black">Acceso controlado</p>
          </div>
          <div class="p-7 border rounded-3xl shadow-sm bg-gradient-to-b from-white to-gray-50">
            <p class="text-gray-600">Atencion comercial directa para resolver dudas de coleccion, seguimiento y criterio de seleccion.</p>
            <p class="mt-6 font-semibold text-luxury-black">Soporte comercial</p>
          </div>
          <div class="p-7 border rounded-3xl shadow-sm bg-gradient-to-b from-white to-gray-50">
            <p class="text-gray-600">Catalogo visible con una lectura mas clara para negocio, escaparatismo y trabajo de boutique.</p>
            <p class="mt-6 font-semibold text-luxury-black">Presentacion cuidada</p>
          </div>
        </div>
      </div>
    </section>

    <section class="py-16 sm:py-20 bg-luxury-black text-white">
      <div class="max-w-7xl mx-auto px-6 text-center">
        <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl mb-4">¿Eres profesional del sector?</h2>
        <p class="text-white/80 max-w-2xl mx-auto mb-8">Solicita tu alta empresarial y accede a tarifas y condiciones profesionales con una cuenta validada.</p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <router-link to="/acceso-empresarial" class="bg-luxury-gold text-luxury-black px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-white transition">Solicitar alta</router-link>
          <router-link to="/tienda" class="border border-white px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-white hover:text-luxury-black transition">Ver catalogo</router-link>
        </div>
      </div>
    </section>

    <section class="py-16 sm:py-20 bg-white">
      <div class="max-w-7xl mx-auto px-6">
        <div class="text-center mb-10 sm:mb-12 animate-rise">
          <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Profesionales</span>
          <h2 class="font-serif text-3xl sm:text-4xl md:text-5xl text-luxury-black">Una forma mas clara de trabajar la marca</h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div class="p-7 border rounded-lg bg-gradient-to-b from-white to-gray-50 hover:shadow-lg transition">
            <p class="text-gray-600 mb-4">Cada familia del catalogo se presenta con mas orden para facilitar la lectura comercial y la toma de decision.</p>
            <p class="font-bold text-luxury-black text-sm">Coleccion estructurada</p>
            <p class="text-gray-500 text-xs">Novia, fiesta, comunion y complementos</p>
          </div>
          <div class="p-7 border rounded-lg bg-gradient-to-b from-white to-gray-50 hover:shadow-lg transition">
            <p class="text-gray-600 mb-4">La parte profesional queda reservada a perfiles validados, manteniendo una separacion clara entre inspiracion publica y operativa comercial.</p>
            <p class="font-bold text-luxury-black text-sm">Acceso profesional</p>
            <p class="text-gray-500 text-xs">Condiciones reservadas para empresa</p>
          </div>
          <div class="p-7 border rounded-lg bg-gradient-to-b from-white to-gray-50 hover:shadow-lg transition">
            <p class="text-gray-600 mb-4">La experiencia digital busca acompanar la imagen del atelier con una presentacion sobria, util y coherente.</p>
            <p class="font-bold text-luxury-black text-sm">Imagen de marca</p>
            <p class="text-gray-500 text-xs">Presentacion cuidada y alineada</p>
          </div>
        </div>
      </div>
    </section>

    <section class="py-16 sm:py-20 bg-luxury-gold/5 border-y border-luxury-gold/20">
      <div class="max-w-7xl mx-auto px-6">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-8 items-center">
          <div>
            <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Condiciones</span>
            <h2 class="font-serif text-4xl md:text-5xl text-luxury-black mb-4">Trabajamos con empresas</h2>
            <p class="text-xl text-luxury-gold font-bold mb-2">Alta y validacion profesional</p>
            <p class="text-gray-600 mb-6">Solicita acceso y revisaremos tu perfil para habilitar una cuenta profesional acorde a la linea comercial de Ibernovia.</p>
            <router-link to="/acceso-empresarial" class="inline-block bg-luxury-black text-white px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-luxury-gold hover:text-luxury-black transition">Solicitar alta</router-link>
          </div>

          <div class="bg-white rounded-3xl p-8 shadow-lg border border-luxury-gold/20">
            <p class="text-center text-4xl font-bold text-luxury-gold mb-2">24/48h</p>
            <p class="text-center text-gray-600 text-sm mb-4">Tiempo habitual de revision comercial</p>
            <p class="text-center text-gray-500 text-xs">Recibiras confirmacion una vez revisados tus datos.</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { apiClient } from '../lib/api'
import ProductCard from '../components/ProductCard.vue'

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
  const preferred = ['Novia', 'Novio', 'Fiesta', 'Comunion', 'Comunión', 'Arras']

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
      throw new Error('Formato de datos invalido')
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
  const fin = paginaActual.value * productosPorPagina
  return productosFiltrados.value.slice(0, fin)
})

const hayMasProductos = computed(() => productosVisibles.value.length < productosFiltrados.value.length)

const productosRestantes = computed(() => productosFiltrados.value.length - productosVisibles.value.length)

const cargarMas = () => {
  paginaActual.value++
}

const openConcierge = (message) => {
  if (typeof window === 'undefined') return

  window.dispatchEvent(new CustomEvent('ibernovia:open-chat', {
    detail: {
      message,
      autoSend: true
    }
  }))
}
</script>

<style scoped>
.home-shell {
  background: linear-gradient(180deg, #fffdf9 0%, #f7f2ea 18%, #ffffff 46%, #fbf7f0 100%);
}

.home-hero {
  position: relative;
  overflow: hidden;
}

.home-glow {
  position: absolute;
  width: 30rem;
  height: 30rem;
  border-radius: 999px;
  filter: blur(72px);
  opacity: 0.22;
  pointer-events: none;
}

.home-glow-left {
  top: -10rem;
  left: -10rem;
  background: rgb(var(--luxury-gold-rgb) / 0.72);
}

.home-glow-right {
  top: 4rem;
  right: -12rem;
  background: rgb(var(--luxury-black-rgb) / 0.3);
}

.home-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: clamp(3rem, 5vw, 5.4rem);
  line-height: 0.96;
  color: var(--luxury-black);
  max-width: 12ch;
  margin-bottom: 1.4rem;
}

.home-intro {
  color: rgb(var(--luxury-black-rgb) / 0.68);
  font-size: 15px;
  line-height: 1.85;
  max-width: 58ch;
  margin-bottom: 2rem;
}

.home-stat-row {
  margin-top: 2rem;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.8rem;
}

.home-stat-card,
.home-image-card {
  border-radius: 28px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  background: rgb(255 255 255 / 0.8);
  box-shadow:
    0 24px 50px rgb(var(--luxury-black-rgb) / 0.06),
    inset 0 1px 0 rgb(255 255 255 / 0.88);
  backdrop-filter: blur(14px);
}

.home-stat-card {
  padding: 1rem;
  min-height: 124px;
}

.home-stat-title {
  color: var(--luxury-black);
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.16em;
  text-transform: uppercase;
}

.home-stat-copy {
  margin-top: 0.55rem;
  color: rgb(var(--luxury-black-rgb) / 0.58);
  font-size: 12px;
  line-height: 1.65;
}

.home-image-card {
  position: relative;
  padding: 1rem;
}

.home-hero-image {
  width: 100%;
  height: 320px;
  border-radius: 22px;
  object-fit: cover;
  display: block;
}

.home-image-meta {
  margin-top: 1rem;
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  color: rgb(var(--luxury-black-rgb) / 0.52);
  font-size: 11px;
  letter-spacing: 0.22em;
  text-transform: uppercase;
}

.preview-shell {
  position: relative;
  background: linear-gradient(180deg, rgba(251, 248, 242, 0.9), rgba(246, 242, 237, 0.75));
}

.preview-shell::before {
  content: '';
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 12% 20%, rgba(196, 160, 106, 0.12), transparent 40%),
    radial-gradient(circle at 90% 30%, rgba(196, 160, 106, 0.08), transparent 40%);
  pointer-events: none;
}

.animate-rise {
  animation: rise 0.9s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.delay-100 {
  animation-delay: 100ms;
}

@keyframes rise {
  from {
    opacity: 0;
    transform: translateY(18px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 1024px) {
  .home-stat-row {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .home-image-meta {
    flex-direction: column;
  }

  .home-stat-card,
  .home-image-card {
    border-radius: 24px;
  }
}
</style>
