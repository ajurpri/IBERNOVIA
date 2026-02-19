<template>
  <div class="bg-white min-h-screen pb-20">
    <!-- Header de la Colección -->
    <div class="bg-luxury-black text-white py-12 px-4 mb-8 shadow-lg">
      <div class="max-w-7xl mx-auto text-center">
        <h1 class="font-serif text-3xl md:text-5xl text-luxury-gold mb-3">Colección Exclusiva</h1>
        <p class="text-gray-400 text-xs md:text-sm uppercase tracking-[0.2em] max-w-2xl mx-auto">
          Complementos diseñados para el momento más importante de tu vida
        </p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex flex-col lg:flex-row gap-8">
        
        <!-- Sidebar de Filtros -->
        <aside class="w-full lg:w-64 flex-shrink-0">
          <!-- Toggle Mobile -->
          <button 
            @click="showMobileFilters = !showMobileFilters"
            class="lg:hidden w-full bg-gray-100 text-gray-800 py-3 px-4 rounded mb-4 flex justify-between items-center font-bold uppercase text-xs tracking-widest hover:bg-gray-200 transition">
            <span>{{ showMobileFilters ? 'Ocultar Filtros' : 'Filtrar Productos' }}</span>
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path></svg>
          </button>

          <div :class="['lg:block space-y-8', showMobileFilters ? 'block' : 'hidden']">
            
            <!-- Buscador -->
            <div>
              <h3 class="font-serif text-lg mb-4 border-b border-gray-200 pb-2">Buscar</h3>
              <div class="relative">
                <input 
                  v-model="searchQuery" 
                  type="text" 
                  placeholder="Buscar pieza..." 
                  class="w-full pl-10 pr-4 py-2.5 bg-gray-50 border border-gray-200 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold transition text-sm"
                >
                <svg class="w-4 h-4 text-gray-400 absolute left-3 top-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
              </div>
            </div>

            <!-- Categorías -->
            <div>
              <h3 class="font-serif text-lg mb-4 border-b border-gray-200 pb-2">Categorías</h3>
              <div class="space-y-2.5">
                <label v-for="cat in categories" :key="cat" class="flex items-center cursor-pointer group">
                  <input 
                    type="checkbox" 
                    :value="cat" 
                    v-model="selectedCategories"
                    class="w-4 h-4 text-luxury-gold border-gray-300 rounded focus:ring-luxury-gold cursor-pointer"
                  >
                  <span class="ml-3 text-sm text-gray-600 group-hover:text-luxury-black transition">{{ cat }}</span>
                </label>
              </div>
            </div>

            <!-- Rango de Precio -->
            <div>
              <h3 class="font-serif text-lg mb-4 border-b border-gray-200 pb-2">Precio</h3>
              <div class="space-y-4">
                <div class="flex items-center justify-between text-sm font-medium text-gray-700">
                  <span>0€</span>
                  <span class="text-luxury-gold">{{ maxPrice }}€</span>
                </div>
                <input 
                  type="range" 
                  v-model.number="maxPrice" 
                  :min="0" 
                  :max="200" 
                  class="w-full h-1.5 bg-gray-200 rounded-lg appearance-none cursor-pointer accent-luxury-gold"
                >
                <p class="text-xs text-gray-400 text-center">Mostrando hasta {{ maxPrice }}€</p>
              </div>
            </div>

            <!-- Badge de Seguridad (Top Trust) -->
            <div class="pt-6 border-t border-gray-100">
              <div class="bg-gray-50 p-4 rounded border border-gray-100 text-center">
                <p class="text-[10px] uppercase tracking-widest text-gray-500 mb-2 font-bold">Compra 100% Segura</p>
                <div class="flex justify-center gap-3 opacity-60 grayscale hover:grayscale-0 transition duration-500">
                  <!-- Iconos simulados con texto/svg simple para no depender de imagenes externas rotas -->
                  <span class="text-xs font-bold text-blue-800">VISA</span>
                  <span class="text-xs font-bold text-red-600">Mastercard</span>
                  <span class="text-xs font-bold text-blue-500">PayPal</span>
                </div>
                <div class="mt-2 flex items-center justify-center gap-1 text-[10px] text-luxury-gold font-medium">
                  <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"></path></svg>
                  SSL Encriptado
                </div>
              </div>
            </div>

          </div>
        </aside>

        <!-- Grid de Productos -->
        <main class="flex-1">
          <!-- Toolbar Superior -->
          <div class="flex flex-col sm:flex-row justify-between items-center mb-6 pb-4 border-b border-gray-100 gap-4">
            <p class="text-sm text-gray-500">Mostrando <span class="font-bold text-luxury-black">{{ filteredProducts.length }}</span> resultados</p>
            
            <div class="flex items-center gap-2">
              <label class="text-sm text-gray-500">Ordenar:</label>
              <select v-model="sortBy" class="text-sm border-none bg-gray-50 rounded px-3 py-1.5 focus:ring-1 focus:ring-luxury-gold cursor-pointer">
                <option value="default">Relevancia</option>
                <option value="price-asc">Precio: Menor a Mayor</option>
                <option value="price-desc">Precio: Mayor a Menor</option>
                <option value="name-asc">Nombre: A-Z</option>
              </select>
            </div>
          </div>

          <!-- Loading -->
          <div v-if="loading" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-x-6 gap-y-10">
            <ProductCardSkeleton v-for="i in 9" :key="i" />
          </div>

          <!-- Empty State -->
          <div v-else-if="filteredProducts.length === 0" class="text-center py-20 bg-gray-50 rounded-lg border border-dashed border-gray-200">
            <svg class="w-16 h-16 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
            <h3 class="text-lg font-medium text-gray-900">No encontramos coincidencias</h3>
            <p class="text-gray-500 mt-2 text-sm">Prueba a ajustar los filtros o busca otro término.</p>
            <button @click="resetFilters" class="mt-6 text-luxury-gold font-bold text-sm uppercase tracking-widest hover:text-black transition">Limpiar filtros</button>
          </div>

          <!-- Grid -->
          <div v-else>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-x-6 gap-y-10 mb-12">
            <ProductCard 
                v-for="producto in paginatedProducts" 
              :key="producto.id" 
              :producto="producto" 
            />
          </div>

            <!-- Paginación -->
            <div v-if="totalPages > 1" class="flex justify-center items-center gap-2">
              <button 
                @click="currentPage--" 
                :disabled="currentPage === 1"
                class="w-10 h-10 flex items-center justify-center border border-gray-200 rounded hover:border-luxury-gold hover:text-luxury-gold disabled:opacity-30 disabled:cursor-not-allowed transition">
                ←
              </button>
              
              <span class="text-sm font-medium text-gray-600 px-4">
                Página {{ currentPage }} de {{ totalPages }}
              </span>

              <button 
                @click="currentPage++" 
                :disabled="currentPage === totalPages"
                class="w-10 h-10 flex items-center justify-center border border-gray-200 rounded hover:border-luxury-gold hover:text-luxury-gold disabled:opacity-30 disabled:cursor-not-allowed transition">
                →
              </button>
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { apiClient } from '../lib/api'
import ProductCard from '../components/ProductCard.vue'
import ProductCardSkeleton from '../components/ProductCardSkeleton.vue'

const products = ref([])
const loading = ref(true)
const showMobileFilters = ref(false)

// Estado de Filtros
const searchQuery = ref('')
const selectedCategories = ref([])
const maxPrice = ref(200)
const sortBy = ref('default')
const currentPage = ref(1)
const itemsPerPage = 9

const categories = ["Ligas", "Pendientes", "Abanicos", "Gemelos", "Cruces", "Tocados", "Broches", "Can-canes", "Religioso"]

const fetchProducts = async () => {
  loading.value = true
  try {
    const response = await apiClient.get('/api/productos')
    products.value = response.data
  } catch (error) {
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}

const resetFilters = () => {
  searchQuery.value = ''
  selectedCategories.value = []
  maxPrice.value = 200
  sortBy.value = 'default'
}

const filteredProducts = computed(() => {
  let result = products.value.filter(p => {
    const matchesSearch = p.nombre.toLowerCase().includes(searchQuery.value.toLowerCase())
    const matchesCategory = selectedCategories.value.length === 0 || selectedCategories.value.includes(p.categoria)
    const matchesPrice = p.precio <= maxPrice.value
    return matchesSearch && matchesCategory && matchesPrice
  })

  if (sortBy.value === 'price-asc') result.sort((a, b) => a.precio - b.precio)
  else if (sortBy.value === 'price-desc') result.sort((a, b) => b.precio - a.precio)
  else if (sortBy.value === 'name-asc') result.sort((a, b) => a.nombre.localeCompare(b.nombre))

  return result
})

// Paginación Computada
const totalPages = computed(() => Math.ceil(filteredProducts.value.length / itemsPerPage))

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filteredProducts.value.slice(start, end)
})

// Resetear a página 1 si cambian los filtros
watch([searchQuery, selectedCategories, maxPrice, sortBy], () => {
  currentPage.value = 1
})

onMounted(fetchProducts)
</script>