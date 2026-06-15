<template>
  <div class="min-h-screen bg-[#fdfdfc]">
    
    <div id="catalogo" class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 sm:py-24 animate-fade-in-up">
      
      <!-- Sleek Editorial Filter Bar -->
      <div class="flex flex-col lg:flex-row lg:items-end justify-between gap-8 pb-10 border-b border-black/5 mb-12">
        
        <!-- Search Field -->
        <div class="flex-1 min-w-[280px]">
          <span class="block text-[9px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">Buscar en Colección</span>
          <div class="relative group">
            <input
              id="search"
              v-model="searchTerm"
              type="search"
              placeholder="Ej. Vestido corte sirena, velo..."
              class="w-full pb-2.5 pt-1 border-b border-gray-200 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/40"
              aria-label="Buscar productos"
            />
          </div>
        </div>

        <!-- Dropdowns Row -->
        <div class="flex flex-wrap items-end gap-6 sm:gap-8">
          
          <!-- Custom Family Selector -->
          <div class="w-full sm:w-48 relative custom-dropdown">
            <span class="block text-[9px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">Colección / Familia</span>
            <button
              type="button"
              @click="familyDropdownOpen = !familyDropdownOpen; categoryDropdownOpen = false"
              class="w-full pb-2.5 pt-1 border-b border-gray-200 bg-transparent text-left text-sm text-luxury-black flex justify-between items-center cursor-pointer transition-colors hover:border-luxury-gold focus:outline-none"
            >
              <span>{{ selectedFamily === 'Todas' ? 'Todas las colecciones' : selectedFamily }}</span>
              <svg 
                class="w-3.5 h-3.5 text-luxury-gold transition-transform duration-300"
                :class="{ 'rotate-180': familyDropdownOpen }" 
                fill="none" 
                stroke="currentColor" 
                stroke-width="1.8" 
                viewBox="0 0 24 24"
              >
                <path stroke-linecap="round" stroke-linejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
              </svg>
            </button>
            
            <transition name="dropdown-slide">
              <div 
                v-show="familyDropdownOpen" 
                class="absolute left-0 right-0 mt-2 bg-[#fdfdfc] border border-black/10 rounded-xl shadow-xl z-50 py-1 overflow-hidden"
              >
                <button
                  type="button"
                  @click="selectedFamily = 'Todas'; familyDropdownOpen = false"
                  class="w-full px-4 py-2.5 text-left text-xs uppercase tracking-wider transition-colors hover:bg-luxury-gold/10 focus:outline-none"
                  :class="selectedFamily === 'Todas' ? 'text-luxury-gold font-bold' : 'text-luxury-black'"
                >
                  Todas las colecciones
                </button>
                <button
                  v-for="fam in familias"
                  :key="fam"
                  type="button"
                  @click="selectedFamily = fam; familyDropdownOpen = false"
                  class="w-full px-4 py-2.5 text-left text-xs uppercase tracking-wider transition-colors hover:bg-luxury-gold/10 focus:outline-none"
                  :class="selectedFamily === fam ? 'text-luxury-gold font-bold' : 'text-luxury-black'"
                >
                  {{ fam }}
                </button>
              </div>
            </transition>
          </div>

          <!-- Custom Subfamily Selector -->
          <div class="w-full sm:w-48 relative custom-dropdown">
            <span class="block text-[9px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">Subfamilia</span>
            <button
              type="button"
              @click="categoryDropdownOpen = !categoryDropdownOpen; familyDropdownOpen = false"
              class="w-full pb-2.5 pt-1 border-b border-gray-200 bg-transparent text-left text-sm text-luxury-black flex justify-between items-center cursor-pointer transition-colors hover:border-luxury-gold focus:outline-none"
            >
              <span>{{ selectedCategory === 'Todos' ? 'Todas las subfamilias' : selectedCategory }}</span>
              <svg 
                class="w-3.5 h-3.5 text-luxury-gold transition-transform duration-300"
                :class="{ 'rotate-180': categoryDropdownOpen }" 
                fill="none" 
                stroke="currentColor" 
                stroke-width="1.8" 
                viewBox="0 0 24 24"
              >
                <path stroke-linecap="round" stroke-linejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
              </svg>
            </button>
            
            <transition name="dropdown-slide">
              <div 
                v-show="categoryDropdownOpen" 
                class="absolute left-0 right-0 mt-2 bg-[#fdfdfc] border border-black/10 rounded-xl shadow-xl z-50 py-1 overflow-hidden"
              >
                <button
                  type="button"
                  @click="selectedCategory = 'Todos'; categoryDropdownOpen = false"
                  class="w-full px-4 py-2.5 text-left text-xs uppercase tracking-wider transition-colors hover:bg-luxury-gold/10 focus:outline-none"
                  :class="selectedCategory === 'Todos' ? 'text-luxury-gold font-bold' : 'text-luxury-black'"
                >
                  Todas las subfamilias
                </button>
                <button
                  v-for="cat in categorias"
                  :key="cat"
                  type="button"
                  @click="selectedCategory = cat; categoryDropdownOpen = false"
                  class="w-full px-4 py-2.5 text-left text-xs uppercase tracking-wider transition-colors hover:bg-luxury-gold/10 focus:outline-none"
                  :class="selectedCategory === cat ? 'text-luxury-gold font-bold' : 'text-luxury-black'"
                >
                  {{ cat }}
                </button>
              </div>
            </transition>
          </div>

          <!-- Clear Button -->
          <button
            type="button"
            @click="resetFilters"
            v-if="searchTerm || selectedFamily !== 'Todas' || selectedCategory !== 'Todos'"
            class="pb-2.5 text-[10px] uppercase tracking-[0.2em] text-luxury-gold hover:text-luxury-black font-bold transition-colors border-b border-transparent hover:border-luxury-black focus:outline-none"
          >
            Limpiar Filtros
          </button>
        </div>
      </div>

      <!-- Loading skeleton -->
      <div v-if="isLoading" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-8 gap-y-12" role="status" aria-live="polite">
        <div v-for="n in 8" :key="n" class="aspect-[3/4] rounded-xl bg-gray-50 border border-black/5 animate-pulse"></div>
      </div>

      <!-- Load error view -->
      <div v-else-if="loadError" class="rounded-xl border border-red-100 bg-red-50/50 p-6 text-red-700">
        <p class="font-semibold text-sm">No se pudo cargar el catálogo de productos.</p>
        <p class="text-xs mt-1 text-red-500">{{ loadError }}</p>
        <button
          type="button"
          @click="fetchProductos"
          class="mt-4 px-6 py-2.5 text-[10px] uppercase tracking-widest bg-luxury-black text-white hover:bg-luxury-gold hover:text-white transition rounded-full"
        >
          Reintentar Carga
        </button>
      </div>

      <!-- Products Content -->
      <div v-else class="animate-fade-in delay-150">
        <!-- Results Counter & Action link -->
        <div class="flex items-center justify-between gap-4 mb-8">
          <p class="text-[10px] uppercase tracking-widest text-gray-400 font-bold" aria-live="polite">
            {{ sortedProducts.length }} diseños encontrados
          </p>
          <router-link 
            to="/acceso-empresarial" 
            class="text-[10px] uppercase tracking-widest text-luxury-gold hover:text-luxury-black font-bold border-b border-transparent hover:border-luxury-black transition-colors"
          >
            Alta profesional y contacto
          </router-link>
        </div>

        <!-- No products match -->
        <div v-if="sortedProducts.length === 0" class="rounded-xl border border-black/5 bg-transparent p-12 text-center">
          <p class="text-luxury-black font-serif text-lg font-light">No se encontraron diseños coincidentes.</p>
          <p class="text-xs text-gray-400 mt-2">Prueba modificando tus términos de búsqueda o filtrando por otra colección.</p>
        </div>

        <!-- Products Grid (4 Columns, Luxury Spacing) -->
        <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-8 gap-y-12 items-stretch">
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
import { ref, onMounted, onUnmounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import { apiClient } from '../lib/api'
import { useAuthStore } from '../stores/auth'

const productos = ref([])
const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const isLoading = ref(true)
const loadError = ref('')
const searchTerm = ref('')
const selectedFamily = ref('Todas')
const selectedCategory = ref('Todos')
const sortOrder = ref('destacados')
const syncFromRoute = ref(false)

// Dropdowns open state
const familyDropdownOpen = ref(false)
const categoryDropdownOpen = ref(false)

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

const fold = (v) => (v ?? '').toString()
  .replace(/\uFFFD/g, '')
  .normalize('NFD')
  .replace(/[\u0300-\u036f]/g, '')
  .toLowerCase()

const normalizeForSearch = (v) => fold(v)
  .replace(/[^a-z0-9]+/g, ' ')
  .trim()
  .replace(/\s+/g, ' ')

const categorias = computed(() => {
  const list = selectedFamily.value === 'Todas'
    ? productos.value
    : productos.value.filter((p) => p.familia === selectedFamily.value)

  const set = new Set(list.map((p) => p.categoria).filter(Boolean))
  return Array.from(set).sort()
})

watch(selectedFamily, () => {
  if (selectedCategory.value !== 'Todos' && !categorias.value.includes(selectedCategory.value)) {
    selectedCategory.value = 'Todos'
  }
})

const scoredProducts = computed(() => {
  const term = normalizeForSearch(searchTerm.value)
  const rawTokens = term ? term.split(' ') : []
  const tokens = rawTokens.filter((t) => t.length >= 2 || /^\d+$/.test(t))

  const base = productos.value.filter((p) => {
    const matchesFamily = selectedFamily.value === 'Todas'
      ? true
      : p.familia === selectedFamily.value

    const matchesCategory = selectedCategory.value === 'Todos'
      ? true
      : p.categoria === selectedCategory.value

    return matchesFamily && matchesCategory
  })

  if (tokens.length === 0) {
    return base.map((p) => ({ producto: p, score: 0 }))
  }

  const tokenMatches = (hay, hayCompact, t) => {
    if (hay.includes(t) || hayCompact.includes(t)) return true

    if (t.length >= 6) {
      for (let i = 0; i < t.length; i++) {
        const variant = t.slice(0, i) + t.slice(i + 1)
        if (variant.length >= 5 && hayCompact.includes(variant)) return true
      }
    }
    return false
  }

  const strict = []
  const loose = []

  for (const p of base) {
    const haystack = normalizeForSearch(`${p.nombre || ''} ${p.familia || ''} ${p.categoria || ''} ${p.imagen || ''}`)
    const haystackCompact = haystack.replace(/\s+/g, '')

    let matched = 0
    for (const t of tokens) {
      if (tokenMatches(haystack, haystackCompact, t)) matched++
    }

    if (matched === tokens.length) {
      strict.push({ producto: p, score: 1000 + matched + (haystack.includes(term) ? 10 : 0) })
    } else if (matched > 0) {
      loose.push({ producto: p, score: matched + (haystack.includes(term) ? 3 : 0) })
    }
  }

  return strict.length ? strict : loose
})

const sortedProducts = computed(() => {
  const term = normalizeForSearch(searchTerm.value)
  const hasTerm = term.length > 0

  const list = scoredProducts.value.map((x) => x)

  if (hasTerm) {
    list.sort((a, b) => {
      if (b.score !== a.score) return b.score - a.score

      if (authStore.canSeePrices) {
        if (sortOrder.value === 'precio-asc') return (a.producto.precio || 0) - (b.producto.precio || 0)
        if (sortOrder.value === 'precio-desc') return (b.producto.precio || 0) - (a.producto.precio || 0)
        if (sortOrder.value === 'nombre-asc') return (a.producto.nombre || '').localeCompare(b.producto.nombre || '')
      }

      return (a.producto.nombre || '').localeCompare(b.producto.nombre || '')
    })

    return list.map((x) => x.producto)
  }

  const products = list.map((x) => x.producto)

  if (!authStore.canSeePrices) {
    return products.sort((a, b) => (a.nombre || '').localeCompare(b.nombre || ''))
  }
  if (sortOrder.value === 'precio-asc') {
    return products.sort((a, b) => (a.precio || 0) - (b.precio || 0))
  }
  if (sortOrder.value === 'precio-desc') {
    return products.sort((a, b) => (b.precio || 0) - (a.precio || 0))
  }
  if (sortOrder.value === 'nombre-asc') {
    return products.sort((a, b) => (a.nombre || '').localeCompare(b.nombre || ''))
  }
  return products
})

const applyQueryFilters = () => {
  syncFromRoute.value = true

  const q = route.query
  const querySearch = typeof q.q === 'string' ? q.q : ''
  const queryFamily = typeof q.familia === 'string' ? q.familia : 'Todas'
  const querySort = typeof q.sort === 'string' ? q.sort : 'destacados'

  searchTerm.value = querySearch
  selectedFamily.value = familias.value.includes(queryFamily) ? queryFamily : 'Todas'
  sortOrder.value = ['destacados', 'precio-asc', 'precio-desc', 'nombre-asc'].includes(querySort) ? querySort : 'destacados'

  const queryCategory = typeof q.categoria === 'string' ? q.categoria : 'Todos'
  selectedCategory.value = categorias.value.includes(queryCategory) ? queryCategory : 'Todos'

  syncFromRoute.value = false
}

const updateRouteQuery = () => {
  if (syncFromRoute.value) return

  const query = {}
  if (searchTerm.value.trim()) query.q = searchTerm.value.trim()
  if (selectedFamily.value !== 'Todas') query.familia = selectedFamily.value
  if (selectedCategory.value !== 'Todos') query.categoria = selectedCategory.value
  if (sortOrder.value !== 'destacados') query.sort = sortOrder.value

  router.replace({
    path: route.path,
    hash: route.hash || '#catalogo',
    query
  })
}

const resetFilters = () => {
  searchTerm.value = ''
  selectedFamily.value = 'Todas'
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

const closeDropdowns = (e) => {
  if (!e.target.closest('.custom-dropdown')) {
    familyDropdownOpen.value = false
    categoryDropdownOpen.value = false
  }
}

onMounted(async () => {
  await fetchProductos()
  applyQueryFilters()
  document.addEventListener('click', closeDropdowns)
})

onUnmounted(() => {
  document.removeEventListener('click', closeDropdowns)
})

watch(() => route.query, () => {
  if (isLoading.value) return
  applyQueryFilters()
}, { deep: true })

watch([searchTerm, selectedFamily, selectedCategory, sortOrder], () => {
  if (isLoading.value) return
  updateRouteQuery()
})
</script>

<style scoped>
.animate-fade-in-up {
  animation: fadeInUp 1.2s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.animate-fade-in {
  animation: fadeIn 1s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.delay-150 {
  animation-delay: 150ms;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(24px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* Custom Dropdown Animation */
.dropdown-slide-enter-active,
.dropdown-slide-leave-active {
  transition: opacity 0.25s cubic-bezier(0.16, 1, 0.3, 1), transform 0.25s cubic-bezier(0.16, 1, 0.3, 1);
}
.dropdown-slide-enter-from,
.dropdown-slide-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}
</style>
