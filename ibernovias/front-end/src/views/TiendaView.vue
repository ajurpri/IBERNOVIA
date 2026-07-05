<template>
  <div class="catalog-shell min-h-screen">
    <section class="catalog-hero">
      <div class="catalog-glow catalog-glow-left"></div>
      <div class="catalog-glow catalog-glow-right"></div>

      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-14 pb-10 sm:pt-16 sm:pb-12 lg:pt-20 lg:pb-14">
        <div class="grid grid-cols-1 lg:grid-cols-[1.06fr_0.94fr] gap-10 xl:gap-16 items-end">
          <div class="animate-rise">
            <p class="catalog-kicker">Coleccion Ibernovia</p>
            <h1 class="catalog-title">Un catalogo pensado para presentar la marca con mas orden, mas criterio y mas valor comercial</h1>
            <p class="catalog-intro">
              Reunimos las familias principales de Ibernovia en una navegacion mas limpia para que tanto publico general como cliente profesional entiendan mejor la coleccion y encuentren antes la referencia adecuada.
            </p>

            <div class="catalog-stat-row">
              <div class="catalog-stat-card">
                <span class="catalog-stat-value">{{ productos.length }}</span>
                <span class="catalog-stat-label">Referencias visibles</span>
              </div>
              <div class="catalog-stat-card">
                <span class="catalog-stat-value">{{ familias.length }}</span>
                <span class="catalog-stat-label">Familias activas</span>
              </div>
              <div class="catalog-stat-card">
                <span class="catalog-stat-value">{{ authStore.canSeePrices ? 'B2B' : 'Publico' }}</span>
                <span class="catalog-stat-label">{{ authStore.canSeePrices ? 'Tarifa activa' : 'Vista inspiracional' }}</span>
              </div>
            </div>
          </div>

          <aside class="catalog-spotlight animate-rise delay-100">
            <div class="catalog-spotlight-card">
              <p class="catalog-spotlight-kicker">Lectura de coleccion</p>
              <h2 class="catalog-spotlight-title">{{ currentCollectionTitle }}</h2>
              <p class="catalog-spotlight-copy">{{ currentCollectionDescription }}</p>

              <div class="catalog-spotlight-tags">
                <span v-for="tag in currentCollectionTags" :key="tag" class="catalog-tag">{{ tag }}</span>
              </div>

              <router-link to="/acceso-empresarial" class="catalog-inline-link">
                {{ authStore.canSeePrices ? 'Gestion profesional activa' : 'Solicitar acceso profesional' }}
              </router-link>
            </div>
          </aside>
        </div>
      </div>
    </section>

    <section id="catalogo" class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pb-16 sm:pb-20">
      <div class="catalog-toolbar animate-rise delay-150">
        <div class="catalog-toolbar-main">
          <label class="catalog-search">
            <span class="catalog-toolbar-label">Buscar referencia</span>
            <div class="catalog-search-input-wrap">
              <svg class="catalog-search-icon" fill="none" stroke="currentColor" stroke-width="1.7" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-4.35-4.35m1.85-5.15a7 7 0 1 1-14 0 7 7 0 0 1 14 0Z" />
              </svg>
              <input
                id="search"
                v-model="searchTerm"
                type="search"
                placeholder="Buscar por nombre, familia o categoria"
                class="catalog-search-input"
                aria-label="Buscar productos"
              >
            </div>
          </label>

          <div class="catalog-toolbar-meta">
            <div class="catalog-toolbar-block">
              <span class="catalog-toolbar-label">Coleccion</span>
              <div class="catalog-chip-row">
                <button
                  type="button"
                  @click="selectedFamily = 'Todas'"
                  class="catalog-chip"
                  :class="selectedFamily === 'Todas' ? 'catalog-chip-active' : ''"
                >
                  Todas
                </button>
                <button
                  v-for="fam in familias"
                  :key="fam"
                  type="button"
                  @click="selectedFamily = fam"
                  class="catalog-chip"
                  :class="selectedFamily === fam ? 'catalog-chip-active' : ''"
                >
                  {{ fam }}
                </button>
              </div>
            </div>

            <div class="catalog-toolbar-grid">
              <label class="catalog-select-wrap">
                <span class="catalog-toolbar-label">Subfamilia</span>
                <select v-model="selectedCategory" class="catalog-select">
                  <option value="Todos">Todas las subfamilias</option>
                  <option v-for="cat in categorias" :key="cat" :value="cat">{{ cat }}</option>
                </select>
              </label>

              <label class="catalog-select-wrap">
                <span class="catalog-toolbar-label">Orden</span>
                <select v-model="sortOrder" class="catalog-select">
                  <option value="destacados">Destacados</option>
                  <option value="nombre-asc">Nombre</option>
                  <option v-if="authStore.canSeePrices" value="precio-asc">Precio ascendente</option>
                  <option v-if="authStore.canSeePrices" value="precio-desc">Precio descendente</option>
                </select>
              </label>
            </div>
          </div>
        </div>

        <div class="catalog-toolbar-footer">
          <p class="catalog-results" aria-live="polite">
            {{ sortedProducts.length }} referencias
            <span v-if="selectedFamily !== 'Todas'">en {{ selectedFamily }}</span>
            <span v-if="selectedCategory !== 'Todos'">· {{ selectedCategory }}</span>
          </p>

          <button
            v-if="searchTerm || selectedFamily !== 'Todas' || selectedCategory !== 'Todos' || sortOrder !== 'destacados'"
            type="button"
            @click="resetFilters"
            class="catalog-reset"
          >
            Limpiar filtros
          </button>
        </div>
      </div>

      <div v-if="isLoading" class="catalog-loading-grid" role="status" aria-live="polite">
        <div v-for="n in 8" :key="n" class="catalog-loading-card"></div>
      </div>

      <div v-else-if="loadError" class="catalog-error">
        <p class="catalog-error-title">No se pudo cargar el catalogo</p>
        <p class="catalog-error-copy">{{ loadError }}</p>
        <button type="button" @click="fetchProductos" class="catalog-error-button">Reintentar</button>
      </div>

      <div v-else class="animate-rise delay-200">
        <div v-if="sortedProducts.length === 0" class="catalog-empty">
          <p class="catalog-empty-title">No hemos encontrado referencias con esos criterios</p>
          <p class="catalog-empty-copy">
            Prueba otra familia, elimina parte de la busqueda o vuelve a la vista general del catalogo.
          </p>
          <button type="button" @click="resetFilters" class="catalog-error-button">Volver a empezar</button>
        </div>

        <div v-else class="catalog-grid">
          <ProductCard
            v-for="prod in sortedProducts"
            :key="prod.id"
            :producto="prod"
          />
        </div>
      </div>

      <section class="catalog-closing animate-rise delay-200">
        <div class="catalog-closing-card">
          <div>
            <p class="catalog-kicker catalog-kicker-light">Acceso profesional</p>
            <h2 class="catalog-closing-title">Una lectura mas completa del catalogo empieza con una cuenta validada</h2>
            <p class="catalog-closing-copy">
              El acceso profesional permite trabajar mejor la tarifa, la disponibilidad y la relacion comercial con Ibernovia sin perder la presentacion cuidada de la marca.
            </p>
          </div>
          <div class="catalog-closing-actions">
            <router-link to="/acceso-empresarial" class="catalog-cta-primary">Solicitar alta profesional</router-link>
            <router-link to="/contacto" class="catalog-cta-secondary">Hablar con Ibernovia</router-link>
          </div>
        </div>
      </section>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
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
    const matchesFamily = selectedFamily.value === 'Todas' ? true : p.familia === selectedFamily.value
    const matchesCategory = selectedCategory.value === 'Todos' ? true : p.categoria === selectedCategory.value
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
  return products.sort((a, b) => (a.nombre || '').localeCompare(b.nombre || ''))
})

const currentCollectionTitle = computed(() => {
  if (selectedFamily.value !== 'Todas') return selectedFamily.value
  if (selectedCategory.value !== 'Todos') return selectedCategory.value
  return 'Lectura general de coleccion'
})

const currentCollectionDescription = computed(() => {
  const family = fold(selectedFamily.value)
  if (family.includes('novia')) return 'Una seleccion centrada en delicadeza, ceremonia y detalles que completan el estilismo nupcial.'
  if (family.includes('fiesta')) return 'Referencias pensadas para invitada, evento y ceremonia con mas presencia visual.'
  if (family.includes('comunion')) return 'Piezas suaves y cuidadas para comunion y celebraciones con un lenguaje mas luminoso.'
  if (family.includes('novio')) return 'Complementos sobrios para ceremonia masculina y presentacion formal.'
  if (family.includes('arras')) return 'Elementos ceremoniales y detalles de presentacion con enfoque clasico.'
  if (selectedCategory.value !== 'Todos') return `Vista filtrada por ${selectedCategory.value} para facilitar una comparativa mas limpia dentro del catalogo.`
  return 'Panoramica general de familias y categorias para recorrer Ibernovia con una lectura mas clara y mas ordenada.'
})

const currentCollectionTags = computed(() => {
  const tags = []
  if (selectedFamily.value !== 'Todas') tags.push(selectedFamily.value)
  if (selectedCategory.value !== 'Todos') tags.push(selectedCategory.value)
  if (searchTerm.value.trim()) tags.push('Busqueda activa')
  if (authStore.canSeePrices) tags.push('Modo profesional')
  else tags.push('Vista inspiracional')
  return tags.slice(0, 4)
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

<style scoped>
.catalog-shell {
  background:
    radial-gradient(circle at top left, rgb(var(--luxury-gold-rgb) / 0.12), transparent 24%),
    linear-gradient(180deg, #f7f2ea 0%, #fbf8f3 16%, #ffffff 56%, #f8f4ed 100%);
}

.catalog-hero {
  position: relative;
  overflow: hidden;
}

.catalog-glow {
  position: absolute;
  width: 28rem;
  height: 28rem;
  border-radius: 999px;
  filter: blur(72px);
  opacity: 0.22;
  pointer-events: none;
}

.catalog-glow-left {
  top: -10rem;
  left: -10rem;
  background: rgb(var(--luxury-gold-rgb) / 0.72);
}

.catalog-glow-right {
  top: 3rem;
  right: -12rem;
  background: rgb(var(--luxury-black-rgb) / 0.32);
}

.catalog-kicker,
.catalog-toolbar-label,
.catalog-spotlight-kicker {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.28em;
  text-transform: uppercase;
  color: rgb(var(--luxury-gold-rgb) / 0.95);
}

.catalog-title,
.catalog-spotlight-title,
.catalog-closing-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  color: var(--luxury-black);
}

.catalog-title {
  margin-top: 0.9rem;
  font-size: clamp(2.8rem, 5vw, 5.1rem);
  line-height: 0.95;
  max-width: 13ch;
}

.catalog-intro,
.catalog-spotlight-copy,
.catalog-empty-copy,
.catalog-closing-copy {
  color: rgb(var(--luxury-black-rgb) / 0.68);
}

.catalog-intro {
  margin-top: 1.15rem;
  max-width: 60ch;
  font-size: 15px;
  line-height: 1.85;
}

.catalog-stat-row {
  margin-top: 2rem;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.8rem;
}

.catalog-stat-card,
.catalog-spotlight-card,
.catalog-toolbar,
.catalog-empty,
.catalog-error {
  border-radius: 28px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  background: rgb(255 255 255 / 0.8);
  box-shadow:
    0 24px 50px rgb(var(--luxury-black-rgb) / 0.06),
    inset 0 1px 0 rgb(255 255 255 / 0.88);
  backdrop-filter: blur(14px);
}

.catalog-stat-card {
  padding: 1rem;
  min-height: 124px;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.92), rgb(249 244 236 / 0.9));
}

.catalog-stat-value {
  display: block;
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 2rem;
  color: var(--luxury-black);
}

.catalog-stat-label {
  display: block;
  margin-top: 0.35rem;
  color: rgb(var(--luxury-black-rgb) / 0.58);
  font-size: 12px;
  line-height: 1.55;
}

.catalog-spotlight-card {
  padding: 1.45rem;
  background:
    linear-gradient(135deg, rgb(31 26 22 / 0.98), rgb(63 50 39 / 0.95)),
    var(--luxury-black);
  color: white;
  box-shadow: 0 30px 55px rgb(var(--luxury-black-rgb) / 0.18);
}

.catalog-spotlight-card .catalog-spotlight-kicker,
.catalog-spotlight-card .catalog-spotlight-title,
.catalog-spotlight-card .catalog-spotlight-copy,
.catalog-spotlight-card .catalog-inline-link {
  color: white;
}

.catalog-spotlight-title {
  margin-top: 0.7rem;
  font-size: clamp(2rem, 3vw, 3rem);
  line-height: 0.98;
}

.catalog-spotlight-copy {
  margin-top: 1rem;
  font-size: 14px;
  line-height: 1.8;
  opacity: 0.82;
}

.catalog-spotlight-tags {
  margin-top: 1.25rem;
  display: flex;
  flex-wrap: wrap;
  gap: 0.55rem;
}

.catalog-tag {
  display: inline-flex;
  align-items: center;
  min-height: 34px;
  padding: 0 0.9rem;
  border-radius: 999px;
  background: rgb(255 255 255 / 0.08);
  border: 1px solid rgb(255 255 255 / 0.08);
  color: white;
  font-size: 10px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.catalog-inline-link {
  margin-top: 1.2rem;
  display: inline-flex;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  text-decoration: none;
  opacity: 0.86;
}

.catalog-toolbar {
  padding: 1.3rem;
  margin-top: 0.5rem;
}

.catalog-toolbar-main {
  display: grid;
  grid-template-columns: minmax(280px, 1fr) minmax(0, 1.1fr);
  gap: 1rem 1.4rem;
  align-items: start;
}

.catalog-search {
  display: block;
}

.catalog-search-input-wrap {
  position: relative;
  margin-top: 0.65rem;
}

.catalog-search-icon {
  position: absolute;
  left: 1rem;
  top: 50%;
  transform: translateY(-50%);
  width: 18px;
  height: 18px;
  color: rgb(var(--luxury-black-rgb) / 0.42);
}

.catalog-search-input,
.catalog-select {
  width: 100%;
  min-height: 54px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.18);
  border-radius: 18px;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.96), rgb(249 245 238 / 0.9));
  color: var(--luxury-black);
  font-size: 14px;
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.catalog-search-input {
  padding: 0.95rem 1rem 0.95rem 2.8rem;
}

.catalog-select {
  padding: 0.95rem 1rem;
}

.catalog-search-input:focus,
.catalog-select:focus {
  border-color: rgb(var(--luxury-gold-rgb) / 0.56);
  box-shadow: 0 0 0 4px rgb(var(--luxury-gold-rgb) / 0.08);
}

.catalog-toolbar-meta {
  display: grid;
  gap: 1rem;
}

.catalog-toolbar-block,
.catalog-select-wrap {
  display: block;
}

.catalog-chip-row {
  display: flex;
  flex-wrap: wrap;
  gap: 0.55rem;
  margin-top: 0.65rem;
}

.catalog-chip {
  min-height: 38px;
  padding: 0 0.9rem;
  border-radius: 999px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.16);
  background: rgb(255 255 255 / 0.74);
  color: var(--luxury-black);
  font-size: 10px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  cursor: pointer;
  transition: transform 0.2s ease, border-color 0.2s ease, background 0.2s ease, color 0.2s ease;
}

.catalog-chip:hover {
  transform: translateY(-1px);
  border-color: rgb(var(--luxury-gold-rgb) / 0.4);
}

.catalog-chip-active {
  background: linear-gradient(135deg, rgb(34 29 24), rgb(71 59 48));
  border-color: transparent;
  color: white;
}

.catalog-toolbar-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1rem;
}

.catalog-toolbar-footer {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid rgb(var(--luxury-gold-rgb) / 0.12);
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: center;
}

.catalog-results {
  color: rgb(var(--luxury-black-rgb) / 0.56);
  font-size: 11px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.catalog-reset {
  border: none;
  background: transparent;
  color: var(--luxury-black);
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  cursor: pointer;
}

.catalog-loading-grid,
.catalog-grid {
  margin-top: 1.7rem;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 2.25rem 2rem;
}

.catalog-loading-card {
  aspect-ratio: 3 / 4.7;
  border-radius: 22px;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.92), rgb(245 240 232 / 0.82));
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.1);
  animation: pulse 1.8s ease-in-out infinite;
}

.catalog-error,
.catalog-empty {
  margin-top: 1.7rem;
  padding: 2rem 1.5rem;
  text-align: center;
}

.catalog-error-title,
.catalog-empty-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 2rem;
  color: var(--luxury-black);
}

.catalog-error-copy,
.catalog-empty-copy {
  margin-top: 0.55rem;
  font-size: 14px;
  line-height: 1.75;
}

.catalog-error-button,
.catalog-cta-primary,
.catalog-cta-secondary {
  min-height: 52px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0 1.4rem;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  text-decoration: none;
  transition: transform 0.2s ease, box-shadow 0.2s ease, background 0.2s ease, color 0.2s ease, border-color 0.2s ease;
}

.catalog-error-button,
.catalog-cta-primary {
  border: none;
  background: linear-gradient(135deg, rgb(34 29 24), rgb(71 59 48));
  color: white;
  box-shadow: 0 18px 30px rgb(var(--luxury-black-rgb) / 0.14);
}

.catalog-error-button {
  margin-top: 1rem;
}

.catalog-error-button:hover,
.catalog-cta-primary:hover,
.catalog-cta-secondary:hover {
  transform: translateY(-1px);
}

.catalog-cta-secondary {
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.28);
  color: var(--luxury-black);
  background: rgb(255 255 255 / 0.72);
}

.catalog-closing {
  margin-top: 2rem;
}

.catalog-closing-card {
  display: grid;
  grid-template-columns: minmax(0, 1.15fr) minmax(280px, 0.85fr);
  gap: 1.6rem;
  align-items: center;
  padding: 1.6rem;
  border-radius: 30px;
  background:
    linear-gradient(135deg, rgb(31 26 22 / 0.98), rgb(63 50 39 / 0.95)),
    var(--luxury-black);
  box-shadow: 0 28px 50px rgb(var(--luxury-black-rgb) / 0.18);
}

.catalog-kicker-light,
.catalog-closing-title,
.catalog-closing-copy {
  color: white;
}

.catalog-closing-title {
  margin-top: 0.5rem;
  font-size: clamp(2rem, 3vw, 3rem);
  line-height: 1;
}

.catalog-closing-copy {
  margin-top: 0.75rem;
  max-width: 54ch;
  font-size: 14px;
  line-height: 1.75;
  opacity: 0.78;
}

.catalog-closing-actions {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
}

.catalog-cta-secondary:hover {
  background: rgb(var(--luxury-gold-rgb) / 0.1);
}

.animate-rise {
  animation: rise 0.9s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.delay-100 {
  animation-delay: 100ms;
}

.delay-150 {
  animation-delay: 150ms;
}

.delay-200 {
  animation-delay: 200ms;
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

@keyframes pulse {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.6;
  }
}

@media (max-width: 1200px) {
  .catalog-loading-grid,
  .catalog-grid {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}

@media (max-width: 1024px) {
  .catalog-stat-row,
  .catalog-toolbar-main,
  .catalog-closing-card {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .catalog-loading-grid,
  .catalog-grid,
  .catalog-toolbar-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .catalog-toolbar-footer {
    flex-direction: column;
    align-items: flex-start;
  }
}

@media (max-width: 640px) {
  .catalog-stat-row,
  .catalog-loading-grid,
  .catalog-grid,
  .catalog-toolbar-grid,
  .catalog-closing-card {
    grid-template-columns: 1fr;
  }

  .catalog-toolbar,
  .catalog-stat-card,
  .catalog-spotlight-card,
  .catalog-empty,
  .catalog-error,
  .catalog-closing-card {
    border-radius: 24px;
  }
}
</style>
