<template>
  <!-- Hero Section: Portada Impactante -->
  <div class="relative min-h-[100svh] w-full bg-luxury-black overflow-hidden">
    <!-- Video/Image Background -->
    <div class="absolute inset-0">
      <img src="https://images.unsplash.com/photo-1519741497674-611481863552?q=80&w=2070&auto=format&fit=crop" 
           alt="Novia Ibernovia" 
           class="w-full h-full object-cover opacity-50 transform scale-105 animate-subtle-zoom">
    </div>
    
    <!-- Gradient Overlay -->
    <div class="absolute inset-0 bg-gradient-to-b from-black/70 via-black/50 to-black/80"></div>
    
    <!-- Content -->
    <div class="relative min-h-[100svh] flex flex-col items-center justify-center text-white text-center px-4 py-20 z-10">
      <div class="animate-fade-in-up w-full max-w-5xl mx-auto">
        <div class="flex items-center justify-center mb-6">
          <img :src="logoSrc" alt="IBERNOVIA" class="h-14 md:h-16 w-auto drop-shadow-xl" />
        </div>
        <div class="mb-4 inline-block">
          <span class="text-luxury-gold text-xs md:text-sm font-bold tracking-[0.3em] uppercase border-t border-b border-luxury-gold py-2 px-6">Desde 2015</span>
        </div>
        <h1 class="font-serif text-4xl md:text-6xl lg:text-8xl mb-6 tracking-tight leading-[1.05]">
          Elegancia<br/>
          <span class="text-luxury-gold">Atemporal</span>
        </h1>
        <p class="text-base md:text-xl lg:text-2xl tracking-[0.2em] uppercase mb-10 font-light max-w-3xl mx-auto opacity-90">
          Complementos Nupciales de Alta Costura
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center items-center">
          <router-link
            to="/tienda"
            class="group relative bg-luxury-gold text-luxury-black px-10 py-4 font-bold uppercase tracking-widest text-sm hover:bg-white transition-all duration-300 overflow-hidden rounded focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-black">
            <span class="relative z-10">Explorar Colección</span>
            <div class="absolute inset-0 bg-white transform -translate-x-full group-hover:translate-x-0 transition-transform duration-300"></div>
          </router-link>
          <a
            href="#categorias"
            class="border-2 border-white px-10 py-4 hover:bg-white hover:text-luxury-black transition-all duration-300 uppercase tracking-widest text-sm font-bold rounded focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-offset-2 focus-visible:ring-offset-black">
            Nuestras Categorías
          </a>
        </div>
      </div>
      
      <!-- Scroll Indicator -->
      <div class="absolute bottom-8 left-1/2 transform -translate-x-1/2 animate-bounce">
        <svg class="w-6 h-6 text-luxury-gold" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 14l-7 7m0 0l-7-7m7 7V3"></path>
        </svg>
      </div>
    </div>
  </div>
  
  <!-- Características Destacadas -->
  <div class="py-16 bg-white border-b">
    <div class="max-w-7xl mx-auto px-4">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div class="text-center group cursor-pointer hover-lift rounded-2xl p-6 border border-transparent hover:border-gray-100">
          <div class="inline-flex items-center justify-center w-16 h-16 mb-4 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
            <svg class="w-8 h-8 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
            </svg>
          </div>
          <h3 class="font-serif text-xl mb-2">Calidad Premium</h3>
          <p class="text-gray-600 text-sm">Materiales de la más alta calidad seleccionados cuidadosamente</p>
        </div>
        <div class="text-center group cursor-pointer hover-lift rounded-2xl p-6 border border-transparent hover:border-gray-100">
          <div class="inline-flex items-center justify-center w-16 h-16 mb-4 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
            <svg class="w-8 h-8 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
          </div>
          <h3 class="font-serif text-xl mb-2">Envío 24-48h</h3>
          <p class="text-gray-600 text-sm">Entrega rápida y segura en toda España</p>
        </div>
        <div class="text-center group cursor-pointer hover-lift rounded-2xl p-6 border border-transparent hover:border-gray-100">
          <div class="inline-flex items-center justify-center w-16 h-16 mb-4 border-2 border-luxury-gold rounded-full group-hover:bg-luxury-gold transition-all duration-300">
            <svg class="w-8 h-8 text-luxury-gold group-hover:text-white transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
            </svg>
          </div>
          <h3 class="font-serif text-xl mb-2">Atención Personalizada</h3>
          <p class="text-gray-600 text-sm">Asesoramiento experto para tu día especial</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Experiencia en tienda -->
  <div class="py-16 bg-white">
    <div class="max-w-7xl mx-auto px-4 grid grid-cols-1 lg:grid-cols-2 gap-10 items-center">
      <div>
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Andújar · Jaén</span>
        <h2 class="font-serif text-4xl md:text-5xl text-luxury-black mb-6">Atelier y asesoramiento en tienda</h2>
        <p class="text-gray-600 text-lg mb-6">
          Te acompañamos en cada detalle con una selección exclusiva de complementos nupciales. Visítanos en Plaza de Abastos y descubre piezas únicas.
        </p>
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 text-sm">
          <div class="p-4 border rounded-lg">
            <p class="font-bold text-luxury-black">Ubicación</p>
            <p class="text-gray-600">C. Ollerías, 48, 23740 Andújar, Jaén</p>
          </div>
          <div class="p-4 border rounded-lg">
            <p class="font-bold text-luxury-black">Horario</p>
            <p class="text-gray-600">De lunes a viernes de 9 a 14 y 17 a 20</p>
          </div>
        </div>
      </div>
      <div class="relative">
        <div class="absolute -inset-4 bg-luxury-gold/10 rounded-3xl"></div>
        <img
          src="https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=1974&auto=format&fit=crop"
          alt="Atelier Ibernovia"
          class="relative rounded-3xl shadow-xl w-full object-cover h-[360px]"
        />
      </div>
    </div>
  </div>

  <div id="coleccion" class="w-full px-4 sm:px-6 lg:px-8 py-20 bg-luxury-gray">
    <div class="max-w-7xl mx-auto">
      <div class="text-center mb-16">
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Catálogo 2026</span>
        <h2 class="ib-title font-serif text-4xl md:text-5xl lg:text-6xl text-luxury-black mb-6">Nuestros Complementos</h2>
        <div class="h-1 w-24 bg-luxury-gold mx-auto"></div>
        <p class="mt-6 text-gray-600 max-w-2xl mx-auto text-lg">
          Cada pieza está cuidadosamente diseñada para añadir ese toque de distinción que hará de tu día algo inolvidable
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
        <!-- Filtros de Categoría -->
        <div id="categorias" class="flex flex-wrap justify-center gap-3 md:gap-4 mb-16 text-xs uppercase tracking-[0.2em] font-bold">
          <button 
            @click="cambiarCategoria('Todos')"
            :aria-pressed="categoriaSeleccionada === 'Todos' ? 'true' : 'false'"
            :class="categoriaSeleccionada === 'Todos' ? 'bg-luxury-gold text-luxury-black shadow-lg scale-105' : 'bg-white text-gray-700 hover:bg-gray-100 hover:shadow-md'"
            class="px-6 py-3 transition-all duration-300 rounded-full shadow whitespace-nowrap transform hover:scale-105 focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white">
            Todos
          </button>
          <button 
            v-for="cat in categorias" 
            :key="cat"
            @click="cambiarCategoria(cat)"
            :aria-pressed="categoriaSeleccionada === cat ? 'true' : 'false'"
            :class="categoriaSeleccionada === cat ? 'bg-luxury-gold text-luxury-black shadow-lg scale-105' : 'bg-white text-gray-700 hover:bg-gray-100 hover:shadow-md'"
            class="px-6 py-3 transition-all duration-300 rounded-full shadow whitespace-nowrap transform hover:scale-105 focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white">
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
  </div>

  <!-- Testimonios -->
  <div class="py-16 bg-white">
    <div class="max-w-7xl mx-auto px-4">
      <div class="text-center mb-12">
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-3">Reseñas</span>
        <h2 class="font-serif text-4xl md:text-5xl text-luxury-black">Amor a primera vista</h2>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="p-6 border rounded-2xl shadow-sm">
          <p class="text-gray-600">“Calidad impecable y trato cercano. Encontré el complemento perfecto para mi boda.”</p>
          <p class="mt-4 font-semibold text-luxury-black">Laura M.</p>
        </div>
        <div class="p-6 border rounded-2xl shadow-sm">
          <p class="text-gray-600">“Diseños elegantes y asesoramiento excepcional. Repetiría sin duda.”</p>
          <p class="mt-4 font-semibold text-luxury-black">María C.</p>
        </div>
        <div class="p-6 border rounded-2xl shadow-sm">
          <p class="text-gray-600">“El envío fue rápido y el packaging precioso. Todo cuidado al detalle.”</p>
          <p class="mt-4 font-semibold text-luxury-black">Patricia G.</p>
        </div>
      </div>
    </div>
  </div>

  <!-- CTA final -->
  <div class="py-16 bg-luxury-black text-white">
    <div class="max-w-7xl mx-auto px-4 text-center">
      <h2 class="font-serif text-4xl md:text-5xl mb-4">Tu día merece algo único</h2>
      <p class="text-white/80 max-w-2xl mx-auto mb-8">Explora nuestra colección o visítanos en tienda para un asesoramiento personalizado.</p>
      <div class="flex flex-col sm:flex-row gap-4 justify-center">
        <router-link to="/tienda" class="bg-luxury-gold text-luxury-black px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-white transition">Ver Tienda</router-link>
        <router-link to="/contacto" class="border border-white px-8 py-3 font-bold uppercase tracking-widest text-sm hover:bg-white hover:text-luxury-black transition">Contacto</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import ProductCard from '../components/ProductCard.vue'

const logoSrc = import.meta.env.BASE_URL + 'logo/logo.jpg'

const productos = ref([])
const loading = ref(true)
const error = ref(false)
const errorDetalle = ref('')
const categorias = ['Ligas', 'Pendientes', 'Abanicos', 'Gemelos', 'Cruces', 'Tocados']
const categoriaSeleccionada = ref('Todos')
const paginaActual = ref(1)
const productosPorPagina = 12

onMounted(async () => {
  try {
    loading.value = true
    const res = await axios.get('http://localhost:8080/api/productos')
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

const cambiarCategoria = (cat) => {
  categoriaSeleccionada.value = cat
  paginaActual.value = 1
}

const productosFiltrados = computed(() => {
  if (categoriaSeleccionada.value === 'Todos') {
    return productos.value
  }
  return productos.value.filter(p => p.categoria === categoriaSeleccionada.value)
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
