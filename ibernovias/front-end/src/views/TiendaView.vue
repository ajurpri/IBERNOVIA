<template>
  <div class="min-h-screen bg-[#fcfaf6]">
    <section class="border-b border-black/5 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-14 sm:py-16">
        <div class="max-w-3xl">
          <p class="text-[10px] uppercase tracking-[0.35em] text-luxury-gold font-bold mb-4">Catalogo Ibernovia</p>
          <h1 class="font-serif text-4xl sm:text-5xl lg:text-6xl leading-[1.02] text-luxury-black mb-5">
            Coleccion para profesionales del sector ceremonia
          </h1>
          <p class="text-sm sm:text-base text-gray-600 leading-relaxed max-w-2xl">
            Consulta nuestras familias y categorias de producto. El catalogo esta visible para consulta general, y las tarifas quedan reservadas a clientes profesionales validados.
          </p>
        </div>
      </div>
    </section>

    <section id="catalogo" class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 sm:py-14">
      <div class="grid grid-cols-1 gap-6 border-b border-black/6 pb-8">
          <div>
            <label for="search" class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">
              Buscar en catalogo
            </label>
            <input
              id="search"
              v-model="searchTerm"
              type="search"
              placeholder="Ej. velo, tocado, novia..."
              class="w-full h-12 px-4 rounded-2xl border border-gray-200 bg-[#fcfaf6] text-sm text-luxury-black placeholder-gray-400/70 focus:outline-none focus:border-luxury-gold transition-colors"
              aria-label="Buscar productos"
            >
          </div>

          <div>
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">
              Familia
            </label>
            <div class="flex flex-wrap gap-2">
              <button
                type="button"
                @click="selectedFamily = 'Todas'"
                class="px-4 py-2 rounded-full border text-[11px] uppercase tracking-[0.18em] font-semibold transition-colors"
                :class="selectedFamily === 'Todas' ? 'bg-luxury-black text-white border-luxury-black' : 'bg-[#fcfaf6] text-luxury-black border-gray-200 hover:border-luxury-gold'"
              >
                Todas
              </button>
              <button
                v-for="fam in familias"
                :key="fam"
                type="button"
                @click="selectedFamily = fam"
                class="px-4 py-2 rounded-full border text-[11px] uppercase tracking-[0.18em] font-semibold transition-colors"
                :class="selectedFamily === fam ? 'bg-luxury-black text-white border-luxury-black' : 'bg-[#fcfaf6] text-luxury-black border-gray-200 hover:border-luxury-gold'"
              >
                {{ fam }}
              </button>
            </div>
          </div>

          <div>
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">
              Categoria
            </label>
            <div class="flex flex-wrap gap-2">
              <button
                type="button"
                @click="selectedCategory = 'Todos'"
                class="px-4 py-2 rounded-full border text-[11px] uppercase tracking-[0.18em] font-semibold transition-colors"
                :class="selectedCategory === 'Todos' ? 'bg-luxury-black text-white border-luxury-black' : 'bg-[#fcfaf6] text-luxury-black border-gray-200 hover:border-luxury-gold'"
              >
                Todas
              </button>
              <button
                v-for="cat in categorias"
                :key="cat"
                type="button"
                @click="selectedCategory = cat"
                class="px-4 py-2 rounded-full border text-[11px] uppercase tracking-[0.18em] font-semibold transition-colors"
                :class="selectedCategory === cat ? 'bg-luxury-black text-white border-luxury-black' : 'bg-[#fcfaf6] text-luxury-black border-gray-200 hover:border-luxury-gold'"
              >
                {{ cat }}
              </button>
            </div>
          </div>

          <div class="max-w-xs">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">
              Ordenar
            </label>
            <select v-model="sortOrder" class="w-full h-12 px-4 rounded-2xl border border-gray-200 bg-[#fcfaf6] text-sm text-luxury-black focus:outline-none focus:border-luxury-gold transition-colors">
              <option value="destacados">Destacados</option>
              <option value="nombre-asc">Nombre</option>
              <option v-if="authStore.canSeePrices" value="precio-asc">Precio ascendente</option>
              <option v-if="authStore.canSeePrices" value="precio-desc">Precio descendente</option>
            </select>
          </div>
        </div>

        <div class="mt-2 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3 pt-2">
          <p class="text-[11px] uppercase tracking-[0.18em] text-gray-500 font-semibold" aria-live="polite">
            {{ sortedProducts.length }} productos encontrados
          </p>

          <div class="flex flex-wrap gap-3 items-center">
            <router-link
              to="/acceso-empresarial"
              class="text-[11px] uppercase tracking-[0.18em] font-semibold text-luxury-gold hover:text-luxury-black transition-colors"
            >
              Acceso profesional
            </router-link>

            <button
              v-if="searchTerm || selectedFamily !== 'Todas' || selectedCategory !== 'Todos' || sortOrder !== 'destacados'"
              type="button"
              @click="resetFilters"
              class="text-[11px] uppercase tracking-[0.18em] font-semibold text-luxury-black hover:text-luxury-gold transition-colors"
            >
              Limpiar filtros
            </button>
          </div>
        </div>
      <div v-if="isLoading" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-8 gap-y-12 mt-10" role="status" aria-live="polite">
        <div v-for="n in 8" :key="n" class="aspect-[3/4] rounded-2xl bg-white border border-black/5 animate-pulse"></div>
      </div>

      <div v-else-if="loadError" class="mt-10 rounded-2xl border border-red-100 bg-red-50/60 p-6 text-red-700">
        <p class="font-semibold text-sm">No se pudo cargar el catalogo.</p>
        <p class="text-xs mt-1">{{ loadError }}</p>
        <button
          type="button"
          @click="fetchProductos"
          class="mt-4 px-6 py-2.5 text-[10px] uppercase tracking-widest bg-luxury-black text-white hover:bg-luxury-gold transition rounded-full"
        >
          Reintentar
        </button>
      </div>

      <div v-else class="mt-10">
        <div v-if="sortedProducts.length === 0" class="rounded-2xl border border-black/5 bg-white p-10 text-center">
          <p class="font-serif text-2xl text-luxury-black">No hay productos con esos filtros</p>
          <p class="text-sm text-gray-500 mt-2">Prueba otra busqueda o vuelve a la vista general.</p>
        </div>

        <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-8 gap-y-12 items-stretch">
          <ProductCard
            v-for="prod in sortedProducts"
            :key="prod.id"
            :producto="prod"
          />
        </div>
      </div>

      <div class="mt-14 pt-8 border-t border-black/6">
        <div class="flex flex-col lg:flex-row lg:items-end lg:justify-between gap-6">
          <div class="max-w-2xl">
            <p class="text-[10px] uppercase tracking-[0.3em] text-luxury-gold font-bold mb-3">Area profesional</p>
            <h2 class="font-serif text-3xl sm:text-4xl text-luxury-black mb-3">Tarifas y condiciones reservadas para clientes validados</h2>
            <p class="text-gray-600 text-sm leading-relaxed">
              Si eres empresa del sector, solicita acceso para consultar la informacion comercial disponible para perfiles profesionales.
            </p>
          </div>

          <div class="flex flex-col sm:flex-row gap-3">
            <router-link
              to="/acceso-empresarial"
              class="inline-flex items-center justify-center px-8 py-3 bg-luxury-black text-white font-bold uppercase tracking-widest text-xs rounded-full hover:bg-luxury-gold transition"
            >
              Solicitar alta
            </router-link>
            <router-link
              to="/contacto"
              class="inline-flex items-center justify-center px-8 py-3 border border-black/15 text-luxury-black font-bold uppercase tracking-widest text-xs rounded-full hover:border-luxury-gold transition"
            >
              Contacto
            </router-link>
          </div>
        </div>
      </div>
    </section>
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

  const preferred = ['Novia', 'Novio', 'Fiesta', 'ComuniÃ³n', 'Arras']
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
