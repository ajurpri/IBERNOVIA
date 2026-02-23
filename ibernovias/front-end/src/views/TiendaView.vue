<template>
  <div class="min-h-screen bg-luxury-gray">
    <div class="bg-white/80 backdrop-blur border-b border-white/70 shadow-sm">
      <div class="max-w-7xl mx-auto px-4 py-8 sm:py-10">
        <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-5 sm:gap-6">
          <div>
            <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-2">Coleccion 2026</span>
            <h1 class="ib-title font-serif text-3xl sm:text-4xl md:text-5xl font-bold text-luxury-black">Catálogo profesional</h1>
            <p class="text-gray-600 mt-2 max-w-2xl text-sm sm:text-base">Mostramos colección para público general. Los precios y pedidos son exclusivos para empresas registradas.</p>
          </div>
          <div class="hidden sm:flex items-center gap-4">
            <div class="px-4 py-2 rounded-full border border-white/80 bg-white/90 text-xs uppercase tracking-widest text-gray-500 shadow-sm">Solo empresas</div>
            <div class="px-4 py-2 rounded-full border border-white/80 bg-white/90 text-xs uppercase tracking-widest text-gray-500 shadow-sm">Calidad premium</div>
          </div>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 py-10 sm:py-12">
      <div class="grid grid-cols-2 sm:grid-cols-4 gap-3 sm:gap-4 mb-8 sm:mb-10">
        <div class="bg-white/90 rounded-2xl p-3 sm:p-4 border border-white/80 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Ubicación</p>
          <p class="text-sm text-luxury-black font-semibold">Plaza de Abastos · Andújar</p>
        </div>
        <div class="bg-white/90 rounded-2xl p-3 sm:p-4 border border-white/80 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Horario</p>
          <p class="text-sm text-luxury-black font-semibold">Closes soon · 8 pm</p>
        </div>
        <div class="bg-white/90 rounded-2xl p-3 sm:p-4 border border-white/80 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Atención</p>
          <p class="text-sm text-luxury-black font-semibold">Personalizada</p>
        </div>
        <div class="bg-white/90 rounded-2xl p-3 sm:p-4 border border-white/80 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Contacto</p>
          <p class="text-sm text-luxury-black font-semibold">953 51 50 70</p>
        </div>
      </div>

      <div class="mb-6 sm:mb-8 rounded-2xl border border-white/70 bg-white/90 p-4 sm:p-6 shadow-sm">
        <div class="flex flex-col lg:flex-row lg:items-end gap-4">
          <div class="flex-1">
            <label class="block text-xs uppercase tracking-widest text-gray-500 mb-2" for="search">Buscar</label>
            <input
              id="search"
              v-model="searchTerm"
              type="search"
              placeholder="Buscar por nombre o categoria"
              class="w-full px-3 sm:px-4 py-2.5 sm:py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold text-sm bg-white"
              aria-label="Buscar productos"
            />
          </div>
          <div class="flex-1">
            <label class="block text-xs uppercase tracking-widest text-gray-500 mb-2" for="category">Categoria</label>
            <select
              id="category"
              v-model="selectedCategory"
              class="w-full px-3 sm:px-4 py-2.5 sm:py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold text-sm bg-white"
              aria-label="Filtrar por categoria"
            >
              <option value="Todos">Todas</option>
              <option v-for="cat in categorias" :key="cat" :value="cat">{{ cat }}</option>
            </select>
          </div>
          <div v-if="authStore.isBusinessUser" class="flex-1">
            <label class="block text-xs uppercase tracking-widest text-gray-500 mb-2" for="sort">Ordenar</label>
            <select
              id="sort"
              v-model="sortOrder"
              class="w-full px-3 sm:px-4 py-2.5 sm:py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold text-sm bg-white"
              aria-label="Ordenar productos"
            >
              <option value="destacados">Destacados</option>
              <option value="precio-asc">Precio: menor a mayor</option>
              <option value="precio-desc">Precio: mayor a menor</option>
              <option value="nombre-asc">Nombre: A a Z</option>
            </select>
          </div>
          <div class="lg:self-end">
            <button
              type="button"
              @click="resetFilters"
              class="w-full lg:w-auto px-5 sm:px-6 py-2.5 sm:py-3 text-[11px] sm:text-xs uppercase tracking-widest border border-gray-300 rounded-lg hover:border-luxury-black hover:bg-luxury-black hover:text-white transition"
            >
              Limpiar filtros
            </button>
          </div>
        </div>
      </div>

      <div v-if="isLoading" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 sm:gap-4 lg:gap-6" role="status" aria-live="polite">
        <div v-for="n in 8" :key="n" class="h-[260px] sm:h-[320px] lg:h-[360px] rounded-2xl bg-gray-100 animate-pulse"></div>
      </div>

      <div v-else-if="loadError" class="rounded-2xl border border-red-100 bg-red-50/80 p-6 text-red-700">
        <p class="font-semibold">No se pudo cargar el catálogo.</p>
        <p class="text-sm mt-2">{{ loadError }}</p>
        <button
          type="button"
          @click="fetchProductos"
          class="mt-4 px-5 py-2 text-xs uppercase tracking-widest bg-luxury-black text-white rounded hover:bg-luxury-gold hover:text-luxury-black transition"
        >
          Reintentar
        </button>
      </div>

      <div v-else>
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3 mb-5 sm:mb-6">
          <p class="text-[11px] sm:text-xs uppercase tracking-widest text-gray-500" aria-live="polite">
            {{ sortedProducts.length }} resultados
          </p>
          <router-link to="/acceso-empresarial" class="text-[11px] sm:text-xs uppercase tracking-widest text-luxury-black border-b border-luxury-gold hover:text-luxury-gold transition">
            Alta profesional y contacto
          </router-link>
        </div>

        <div v-if="sortedProducts.length === 0" class="rounded-2xl border border-white/80 bg-white/90 p-8 text-center">
          <p class="text-luxury-black font-semibold">No hay productos con estos filtros.</p>
          <p class="text-sm text-gray-500 mt-2">Prueba con otra busqueda o categoria.</p>
        </div>

        <div v-else class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 sm:gap-4 lg:gap-6 items-stretch">
          <ProductCard
            v-for="prod in sortedProducts"
            :key="prod.id"
            :producto="prod"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import ProductCard from '../components/ProductCard.vue'
import { apiClient } from '../lib/api'
import { useAuthStore } from '../stores/auth'

const productos = ref([])
const authStore = useAuthStore()
const isLoading = ref(true)
const loadError = ref('')
const searchTerm = ref('')
const selectedCategory = ref('Todos')
const sortOrder = ref('destacados')

const categorias = computed(() => {
  const set = new Set(productos.value.map((p) => p.categoria).filter(Boolean))
  return Array.from(set).sort()
})

const filteredProducts = computed(() => {
  const term = searchTerm.value.trim().toLowerCase()
  return productos.value.filter((p) => {
    const matchesTerm = term
      ? `${p.nombre || ''} ${p.categoria || ''}`.toLowerCase().includes(term)
      : true
    const matchesCategory = selectedCategory.value === 'Todos'
      ? true
      : p.categoria === selectedCategory.value
    return matchesTerm && matchesCategory
  })
})

const sortedProducts = computed(() => {
  const list = [...filteredProducts.value]
  if (!authStore.isBusinessUser) {
    return list.sort((a, b) => (a.nombre || '').localeCompare(b.nombre || ''))
  }
  if (sortOrder.value === 'precio-asc') {
    return list.sort((a, b) => (a.precio || 0) - (b.precio || 0))
  }
  if (sortOrder.value === 'precio-desc') {
    return list.sort((a, b) => (b.precio || 0) - (a.precio || 0))
  }
  if (sortOrder.value === 'nombre-asc') {
    return list.sort((a, b) => (a.nombre || '').localeCompare(b.nombre || ''))
  }
  return list
})

const resetFilters = () => {
  searchTerm.value = ''
  selectedCategory.value = 'Todos'
  sortOrder.value = 'destacados'
}

const fetchProductos = async () => {
  isLoading.value = true
  loadError.value = ''
  try {
    const res = await apiClient.get('/api/productos')
    productos.value = Array.isArray(res.data) ? res.data : []
  } catch (error) {
    loadError.value = error?.message || 'Error desconocido'
    console.error('Error cargando productos:', error)
  } finally {
    isLoading.value = false
  }
}

onMounted(async () => {
  await fetchProductos()
})
</script>
