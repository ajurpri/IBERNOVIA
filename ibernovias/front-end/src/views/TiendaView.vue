<template>
  <div class="min-h-screen bg-luxury-gray">
    <!-- Hero moved to HomeView to keep '/' as landing only -->

    <div id="catalogo" class="max-w-7xl mx-auto px-4 py-8 sm:py-10">
      <div class="grid grid-cols-1 sm:grid-cols-3 gap-3 mb-8 sm:mb-10">
        <div class="bg-white/65 rounded-2xl p-3 sm:p-4 border border-black/8 shadow-sm backdrop-blur-[1px]">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Ubicación</p>
          <p class="text-sm text-luxury-black font-semibold">Plaza de Abastos · Andújar</p>
        </div>
        <div class="bg-white/65 rounded-2xl p-3 sm:p-4 border border-black/8 shadow-sm backdrop-blur-[1px]">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Horario</p>
          <p class="text-sm text-luxury-black font-semibold">Lun–Vie · 09:00–20:00 · Sáb · 10:00–14:00</p>
        </div>
        <div class="bg-white/65 rounded-2xl p-3 sm:p-4 border border-black/8 shadow-sm backdrop-blur-[1px]">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Contacto</p>
          <p class="text-sm text-luxury-black font-semibold">953 51 50 70</p>
        </div>
      </div>

      <div class="mb-6 sm:mb-8 rounded-2xl border border-black/10 bg-white/95 p-4 sm:p-6 shadow-sm backdrop-blur-[1px]">
        <div class="flex flex-col md:flex-row md:items-center gap-4">
          <div class="flex-1">
            <label class="block text-xs uppercase tracking-widest text-gray-500 mb-2" for="search">Buscar</label>
            <input
              id="search"
              v-model="searchTerm"
              type="search"
              placeholder="Buscar por nombre, referencia o subfamilia"
              class="w-full px-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold text-sm bg-white"
              aria-label="Buscar productos"
            />
          </div>
          <div class="w-56">
            <label class="block text-xs uppercase tracking-widest text-gray-500 mb-2" for="family">Familia</label>
            <select
              id="family"
              v-model="selectedFamily"
              class="w-full px-3 py-3 border border-gray-200 rounded-xl focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold text-sm bg-white"
              aria-label="Filtrar por familia"
            >
              <option value="Todas">Todas</option>
              <option v-for="fam in familias" :key="fam" :value="fam">{{ fam }}</option>
            </select>
          </div>
          <div class="w-56">
            <label class="block text-xs uppercase tracking-widest text-gray-500 mb-2" for="category">Subfamilia</label>
            <select
              id="category"
              v-model="selectedCategory"
              class="w-full px-3 py-3 border border-gray-200 rounded-xl focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold text-sm bg-white"
              aria-label="Filtrar por subfamilia"
            >
              <option value="Todos">Todas</option>
              <option v-for="cat in categorias" :key="cat" :value="cat">{{ cat }}</option>
            </select>
          </div>
          <div class="flex items-center">
            <button
              type="button"
              @click="resetFilters"
              class="px-4 py-3 text-[12px] uppercase tracking-widest border border-gray-300 rounded-xl hover:border-luxury-black hover:bg-luxury-black hover:text-white transition"
            >
              Limpiar
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

        <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 items-stretch">
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
import { ref, onMounted, computed, watch } from 'vue'
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

const familias = computed(() => {
  const set = new Set(productos.value.map((p) => p.familia).filter(Boolean))
  const list = Array.from(set)

  // Ordenar poniendo primero las familias principales si existen
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

    // Fuzzy simple: si falta 1 carácter (por ejemplo por textos con �), seguimos encontrando
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

  // Si hay resultados "buenos" (todas las palabras), usamos esos.
  // Si no, caemos a coincidencias parciales para no dejar el buscador "muerto".
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

onMounted(async () => {
  await fetchProductos()
  applyQueryFilters()
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
