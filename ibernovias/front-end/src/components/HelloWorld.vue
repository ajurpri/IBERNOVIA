<template>
  <!-- Hero Section: Portada Impactante -->
  <div class="relative h-screen md:h-[85vh] w-full bg-gray-900 mb-12 md:mb-20">
    <img src="https://images.unsplash.com/photo-1546167889-0b4f5ff97e3e?q=80&w=1974&auto=format&fit=crop" 
         alt="Novia Ibernovia" 
         class="w-full h-full object-cover opacity-60">
    <div class="absolute inset-0 flex flex-col items-center justify-center text-white text-center px-4">
      <h1 class="font-serif text-3xl md:text-5xl lg:text-7xl mb-4 md:mb-6 tracking-tight">Elegancia Atemporal</h1>
      <p class="text-sm md:text-lg lg:text-xl tracking-[0.3em] uppercase mb-6 md:mb-10 font-light px-2">Complementos exclusivos para tu día especial</p>
      <a href="#coleccion" class="border border-white px-6 md:px-10 py-2 md:py-4 hover:bg-white hover:text-luxury-black transition duration-500 uppercase tracking-widest text-xs md:text-xs font-bold">Ver Colección</a>
    </div>
  </div>

  <div id="coleccion" class="w-full px-4 sm:px-6 lg:px-8 py-8 md:py-12">
    <div class="max-w-7xl mx-auto">
      <div class="text-center mb-8 md:mb-12">
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-2 md:mb-3">Catálogo 2026</span>
        <h2 class="font-serif text-3xl md:text-4xl lg:text-5xl text-luxury-black mb-4 md:mb-6">Nuestros Complementos</h2>
        <div class="h-1 w-20 bg-luxury-gold mx-auto mt-4"></div>
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
        <div class="flex flex-wrap justify-center gap-2 md:gap-4 lg:gap-6 mb-12 md:mb-16 text-xs uppercase tracking-[0.2em] font-medium overflow-x-auto pb-2">
          <button 
            @click="cambiarCategoria('Todos')"
            :class="categoriaSeleccionada === 'Todos' ? 'bg-luxury-black text-white' : 'bg-gray-100 text-gray-500 hover:bg-gray-200'"
            class="px-4 md:px-6 py-2 md:py-3 transition duration-300 rounded-sm whitespace-nowrap">
            Todos
          </button>
          <button 
            v-for="cat in categorias" 
            :key="cat"
            @click="cambiarCategoria(cat)"
            :class="categoriaSeleccionada === cat ? 'bg-luxury-black text-white' : 'bg-gray-100 text-gray-500 hover:bg-gray-200'"
            class="px-4 md:px-6 py-2 md:py-3 transition duration-300 rounded-sm whitespace-nowrap">
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
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import ProductCard from '../components/ProductCard.vue'

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
    productos.value = res.data
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
