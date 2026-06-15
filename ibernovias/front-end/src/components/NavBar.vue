<template>
  <nav class="header-shell sticky top-0 z-[70] border-b border-black/10">
    <BusinessAccessModal ref="businessModalRef" />
    <EventsModal ref="eventsModalRef" />

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="h-[86px] flex items-center justify-between gap-3">
        <router-link to="/" class="wordmark" aria-label="Ir a inicio">
          <span class="brand">IBERNOVIA</span>
          <span class="sub">ATELIER</span>
        </router-link>

        <div class="hidden lg:flex items-center gap-8">
          <router-link to="/" class="menu-link">Inicio</router-link>
          <router-link :to="{ path: '/tienda', hash: '#catalogo' }" class="menu-link">Catalogo</router-link>
          <button type="button" class="menu-link" @click="openEventsModal">Eventos</button>
          <router-link to="/contacto" class="menu-link">Contacto</router-link>
        </div>

        <div class="flex items-center gap-2 sm:gap-3">
          <button
            type="button"
            @click="toggleSearch"
            class="icon-btn"
            aria-label="Abrir buscador"
          >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="m21 21-4.3-4.3m1.5-5.2a6.8 6.8 0 1 1-13.6 0 6.8 6.8 0 0 1 13.6 0Z" />
            </svg>
          </button>

          <button
            v-if="authStore.canSeePrices"
            type="button"
            class="business-btn hidden sm:inline-flex"
            @click="router.push('/cuenta')"
          >
            Acceso empresarial
          </button>

          <router-link
            v-if="authStore.canRequestQuote"
            to="/carrito"
            class="relative icon-btn"
            aria-label="Ver carrito"
          >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M3 3h2l.5 2.2M7 13h10.7l3.3-6.6H6.2M7 13l-2 2.1c-.5.5-.1 1.4.6 1.4H17m-9 2a1.8 1.8 0 1 0 0 3.6 1.8 1.8 0 0 0 0-3.6Zm9 0a1.8 1.8 0 1 0 0 3.6 1.8 1.8 0 0 0 0-3.6Z" />
            </svg>
            <span
              v-if="cartStore.totalItems > 0"
              class="absolute -top-2 -right-2 bg-black text-white text-[10px] font-semibold rounded-full w-5 h-5 flex items-center justify-center"
            >
              {{ cartStore.totalItems }}
            </span>
          </router-link>

          <div ref="userMenuContainer" class="relative z-[71]">
            <button
              ref="userMenuButton"
              @click="toggleUserMenu"
              :aria-expanded="showUserMenu ? 'true' : 'false'"
              class="icon-btn"
              aria-label="Menu de usuario"
            >
              <svg v-if="!authStore.isAuthenticated" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M16 7a4 4 0 1 1-8 0 4 4 0 0 1 8 0ZM12 14a7 7 0 0 0-7 7h14a7 7 0 0 0-7-7Z" />
              </svg>
              <span v-else class="text-sm font-medium max-w-[130px] truncate">{{ authStore.user?.nombre || 'Usuario' }}</span>
            </button>

            <Teleport to="body">
              <div v-if="showUserMenu" class="fixed w-56 bg-white border border-black/15 rounded-md shadow-xl z-[9999]" :style="userMenuStyles" ref="userMenuDropdown">
                <div v-if="!authStore.isAuthenticated" class="p-4">
                  <router-link to="/auth" @click="showUserMenu = false" class="block text-center bg-black text-white py-2 font-semibold uppercase text-xs tracking-wider hover:bg-black/80 transition rounded">
                    Iniciar sesion
                  </router-link>
                </div>
                <div v-else>
                  <div class="p-4 border-b border-black/10">
                    <p class="font-semibold">{{ authStore.user?.nombre }} {{ authStore.user?.apellido }}</p>
                    <p class="text-xs text-gray-500">{{ authStore.user?.email }}</p>
                  </div>
                  <nav class="p-2">
                    <router-link v-if="authStore.canRequestQuote" to="/carrito" @click="showUserMenu = false" class="menu-item">Presupuestos</router-link>
                    <router-link v-else to="/acceso-empresarial" @click="showUserMenu = false" class="menu-item">Alta empresarial</router-link>
                    <router-link to="/cuenta" @click="showUserMenu = false" class="menu-item">Mi cuenta</router-link>
                    <router-link v-if="authStore.isAdmin" to="/admin" @click="showUserMenu = false" class="menu-item">Panel admin</router-link>
                    <button @click="handleLogout" class="w-full text-left px-3 py-2 text-sm text-red-600 hover:bg-red-50 rounded">Cerrar sesion</button>
                  </nav>
                </div>
              </div>
            </Teleport>
          </div>

          <button @click="toggleMobileMenu" class="icon-btn lg:hidden" aria-label="Abrir menu movil">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M4 6h16M4 12h16M4 18h16"/>
            </svg>
          </button>
        </div>
      </div>
    </div>

    <Teleport to="body">
      <div v-if="mobileOpen" class="fixed inset-0 z-[9998] bg-black/45 backdrop-blur-sm" @click.self="mobileOpen = false">
        <div class="bg-[#ececec] w-11/12 max-w-sm mt-10 mx-auto rounded-md shadow-xl border border-black/10 max-h-[84vh] overflow-hidden flex flex-col">
          <div class="p-4 border-b border-black/10 flex items-center justify-between">
            <span class="font-semibold tracking-[0.2em] text-sm">MENÚ</span>
            <button @click="mobileOpen = false" class="icon-btn" aria-label="Cerrar menu movil">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
          <nav class="p-4 overflow-y-auto max-h-[70vh] space-y-4">
            <!-- Menú Navegación Rápida -->
            <div class="border-b border-black/10 pb-4 space-y-1">
              <router-link to="/" @click="mobileOpen = false" class="block px-3 py-2.5 rounded-lg text-sm uppercase tracking-wider font-semibold hover:bg-white/60">Inicio</router-link>
              <router-link :to="{ path: '/tienda', hash: '#catalogo' }" @click="mobileOpen = false" class="block px-3 py-2.5 rounded-lg text-sm uppercase tracking-wider font-semibold hover:bg-white/60">Catálogo</router-link>
              <button type="button" class="w-full text-left block px-3 py-2.5 rounded-lg text-sm uppercase tracking-wider font-semibold hover:bg-white/60" @click="openEventsFromMobile">Eventos</button>
              <router-link to="/contacto" @click="mobileOpen = false" class="block px-3 py-2.5 rounded-lg text-sm uppercase tracking-wider font-semibold hover:bg-white/60">Contacto</router-link>
            </div>

            <!-- Categorías en Acordeón -->
            <div>
              <p class="px-3 pb-3 text-xs uppercase tracking-[0.18em] text-black/60 font-bold">Colecciones</p>
              <div v-if="mobileCategories.length === 0" class="px-3 py-2 text-xs text-black/60">Cargando categorías...</div>
              <ul class="space-y-2">
                <li v-for="family in mobileCategories" :key="family.name" class="bg-white/50 rounded-lg overflow-hidden border border-black/5">
                  <button 
                    @click="toggleFamily(family.name)" 
                    class="w-full flex items-center justify-between px-4 py-3 text-sm font-semibold hover:bg-white/80 transition"
                  >
                    <span>{{ family.name }}</span>
                    <svg 
                      :class="['w-4 h-4 transition-transform duration-200', expandedFamily === family.name ? 'rotate-180' : '']" 
                      fill="none" stroke="currentColor" viewBox="0 0 24 24"
                    >
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
                    </svg>
                  </button>
                  
                  <div v-if="expandedFamily === family.name" class="bg-white/80 border-t border-black/5 p-3 space-y-2">
                    <div class="grid grid-cols-2 gap-2">
                      <router-link 
                        :to="{ path: '/tienda', hash: '#catalogo', query: { familia: family.name } }" 
                        @click="mobileOpen = false"
                        class="block px-3 py-2 bg-white/90 rounded border border-black/5 text-xs text-center font-bold text-luxury-gold uppercase tracking-wider hover:bg-luxury-gold hover:text-white transition"
                      >
                        Ver Todo
                      </router-link>
                      <router-link 
                        v-for="cat in family.categories" 
                        :key="cat" 
                        :to="{ path: '/tienda', hash: '#catalogo', query: { familia: family.name, categoria: cat } }" 
                        @click="mobileOpen = false" 
                        class="block px-3 py-2 bg-white/70 hover:bg-white rounded border border-black/5 text-xs text-center text-black/80 truncate"
                      >
                        {{ cat }}
                      </router-link>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </Teleport>

    <Teleport to="body">
      <div v-if="searchOpen" class="fixed inset-0 z-[9997] search-overlay" @click.self="closeSearch">
        <div class="search-shell h-full flex flex-col">
          <div class="search-head px-5 sm:px-10 lg:px-14 py-6 sm:py-8 border-b border-black/10">
            <div class="max-w-7xl mx-auto flex items-center gap-3 sm:gap-4">
              <svg class="w-5 h-5 text-black/70 shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="m21 21-4.3-4.3m1.5-5.2a6.8 6.8 0 1 1-13.6 0 6.8 6.8 0 0 1 13.6 0Z" />
              </svg>
              <input
                ref="searchInput"
                v-model="searchQuery"
                type="text"
                placeholder="Que estas buscando?"
                class="search-input"
              />
              <button type="button" @click="closeSearch" class="close-search-btn" aria-label="Cerrar buscador">
                X
              </button>
            </div>
          </div>

          <div class="flex-1 overflow-y-auto px-5 sm:px-10 lg:px-14 py-6 sm:py-8">
            <div class="max-w-7xl mx-auto">
              <p class="text-xs uppercase tracking-[0.18em] text-black/55 mb-5">{{ searchHint }}</p>

              <div v-if="isLoadingProducts" class="py-10 text-center text-sm text-black/65">Cargando productos...</div>
              <div v-else-if="productLoadError" class="py-10 text-center text-sm text-red-600">{{ productLoadError }}</div>

              <template v-else>
                <div v-if="searchQuery.trim() && displayedProducts.length === 0" class="py-10 text-center text-sm text-black/70">
                  No se encontraron productos
                </div>

                <div v-if="searchQuery.trim()" class="results-strip">
                  <router-link
                    v-for="product in displayedProducts"
                    :key="`search-${product.id}`"
                    :to="`/producto/${product.id}`"
                    @click="handlePickProduct"
                    class="editorial-card"
                  >
                    <div class="editorial-media">
                      <img
                        :src="getProductImage(product)"
                        :alt="product.nombre"
                        class="w-full h-full object-cover"
                        loading="lazy"
                        decoding="async"
                      />
                    </div>
                    <p class="editorial-title">{{ product.nombre }}</p>
                  </router-link>
                </div>

                <div v-else class="space-y-8 sm:space-y-10">
                  <section v-for="group in featuredGroups" :key="group.family">
                    <div class="flex items-center justify-between mb-3">
                      <h3 class="text-sm sm:text-base tracking-[0.2em] uppercase text-black/85">{{ group.family }}</h3>
                      <router-link :to="{ path: '/tienda', hash: '#catalogo', query: { familia: group.family } }" @click="closeSearch" class="text-[11px] uppercase tracking-[0.2em] text-black/55 hover:text-black/90 transition">
                        Ver coleccion
                      </router-link>
                    </div>

                    <div class="results-strip">
                      <router-link
                        v-for="product in group.products"
                        :key="`featured-${group.family}-${product.id}`"
                        :to="`/producto/${product.id}`"
                        @click="handlePickProduct"
                        class="editorial-card"
                      >
                        <div class="editorial-media">
                          <img
                            :src="getProductImage(product)"
                            :alt="product.nombre"
                            class="w-full h-full object-cover"
                            loading="lazy"
                            decoding="async"
                          />
                        </div>
                        <p class="editorial-title">{{ product.nombre }}</p>
                      </router-link>
                    </div>
                  </section>
                </div>
              </template>
            </div>
          </div>
        </div>
      </div>
    </Teleport>
  </nav>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import { apiFetch } from '../lib/api'
import BusinessAccessModal from './BusinessAccessModal.vue'
import EventsModal from './EventsModal.vue'

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()

const showUserMenu = ref(false)
const userMenuContainer = ref(null)
const userMenuButton = ref(null)
const userMenuDropdown = ref(null)
const userMenuStyles = ref({ top: '0px', left: '0px' })

const mobileOpen = ref(false)
const businessModalRef = ref(null)
const eventsModalRef = ref(null)

const searchOpen = ref(false)
const searchInput = ref(null)
const searchQuery = ref('')
const allProducts = ref([])
const isLoadingProducts = ref(false)
const productLoadError = ref('')
const expandedFamily = ref('')

const normalize = (value) => String(value || '').toLowerCase().trim()

const displayedProducts = computed(() => {
  const query = normalize(searchQuery.value)
  if (!query) return allProducts.value.slice(0, 12)

  return allProducts.value
    .filter((p) => normalize(`${p.nombre} ${p.familia || ''} ${p.categoria || ''}`).includes(query))
    .slice(0, 18)
})

const searchHint = computed(() => {
  if (!searchQuery.value.trim()) return 'Explora por colecciones'
  return `${displayedProducts.value.length} resultados`
})

const featuredGroups = computed(() => {
  if (!allProducts.value.length) return []

  const preferred = ['Novia', 'Fiesta', 'Comunion', 'Arras', 'Novio']
  const byFamily = {}

  for (const product of allProducts.value) {
    const family = String(product?.familia || '').trim() || 'Coleccion'
    if (!byFamily[family]) {
      byFamily[family] = []
    }
    if (byFamily[family].length < 12) {
      byFamily[family].push(product)
    }
  }

  const families = Object.keys(byFamily)
  families.sort((a, b) => {
    const ia = preferred.findIndex((x) => x.toLowerCase() === a.toLowerCase())
    const ib = preferred.findIndex((x) => x.toLowerCase() === b.toLowerCase())
    if (ia !== -1 || ib !== -1) {
      return (ia === -1 ? 999 : ia) - (ib === -1 ? 999 : ib)
    }
    return a.localeCompare(b)
  })

  return families.slice(0, 5).map((family) => ({
    family,
    products: byFamily[family].slice(0, 10)
  }))
})

const mobileCategories = computed(() => {
  const map = {}
  for (const product of allProducts.value) {
    const family = String(product?.familia || '').trim()
    const category = String(product?.categoria || '').trim()
    if (!family) continue

    if (!map[family]) {
      map[family] = new Set()
    }
    if (category) {
      map[family].add(category)
    }
  }

  const preferredOrder = ['Novia', 'Novio', 'Fiesta', 'Comunion', 'Comunion', 'Arras']
  return Object.keys(map)
    .sort((a, b) => {
      const ia = preferredOrder.findIndex((x) => x.toLowerCase() === a.toLowerCase())
      const ib = preferredOrder.findIndex((x) => x.toLowerCase() === b.toLowerCase())
      if (ia !== -1 || ib !== -1) {
        return (ia === -1 ? 999 : ia) - (ib === -1 ? 999 : ib)
      }
      return a.localeCompare(b)
    })
    .map((family) => ({
      name: family,
      categories: Array.from(map[family]).sort((a, b) => a.localeCompare(b))
    }))
})

const getProductImage = (product) => {
  if (product && product.imagen) return product.imagen
  return 'https://placehold.co/400x400/e5e5e5/3a3a3a?text=Ibernovia'
}

const loadAllProducts = async () => {
  if (allProducts.value.length > 0 || isLoadingProducts.value) return
  isLoadingProducts.value = true
  productLoadError.value = ''

  try {
    const res = await apiFetch('/api/productos')
    const data = await res.json()
    allProducts.value = Array.isArray(data) ? data : []
  } catch (error) {
    productLoadError.value = 'No se pudieron cargar los productos'
    console.error('Error cargando productos:', error)
  } finally {
    isLoadingProducts.value = false
  }
}

const toggleSearch = async () => {
  searchOpen.value = !searchOpen.value
  if (!searchOpen.value) return

  await loadAllProducts()
  await nextTick()
  searchInput.value?.focus()
}

const closeSearch = () => {
  searchOpen.value = false
  searchQuery.value = ''
}

const handlePickProduct = () => {
  closeSearch()
}

const openBusinessModal = () => {
  businessModalRef.value?.openModal()
}

const openEventsModal = () => {
  eventsModalRef.value?.openModal()
}

const openEventsFromMobile = () => {
  mobileOpen.value = false
  openEventsModal()
}

const toggleMobileMenu = async () => {
  mobileOpen.value = !mobileOpen.value
  if (!mobileOpen.value) return

  await loadAllProducts()
  if (!expandedFamily.value && mobileCategories.value.length > 0) {
    expandedFamily.value = mobileCategories.value[0].name
  }
}

const toggleFamily = (familyName) => {
  expandedFamily.value = expandedFamily.value === familyName ? '' : familyName
}

const handleLogout = () => {
  authStore.logout()
  showUserMenu.value = false
  router.push('/')
}

const updateUserMenuPosition = () => {
  if (!userMenuButton.value) return

  const rect = userMenuButton.value.getBoundingClientRect()
  const menuWidth = 224
  const menuHeight = 240
  const padding = 12

  let left = rect.right - menuWidth
  if (left < padding) left = padding
  if (left + menuWidth > window.innerWidth - padding) {
    left = window.innerWidth - menuWidth - padding
  }

  let top = rect.bottom + 8
  if (top + menuHeight > window.innerHeight - padding) {
    top = rect.top - menuHeight - 8
  }

  userMenuStyles.value = {
    top: `${Math.max(padding, top)}px`,
    left: `${left}px`
  }
}

const toggleUserMenu = async () => {
  showUserMenu.value = !showUserMenu.value
  if (!showUserMenu.value) return

  await nextTick()
  updateUserMenuPosition()
}

const handleDocumentClick = (event) => {
  const target = event.target

  const clickedOutsideUserMenu =
    userMenuContainer.value &&
    !userMenuContainer.value.contains(target) &&
    (!userMenuDropdown.value || !userMenuDropdown.value.contains(target))

  if (clickedOutsideUserMenu) {
    showUserMenu.value = false
  }
}

const handleDocumentKeydown = (event) => {
  if (event.key === 'Escape') {
    showUserMenu.value = false
    closeSearch()
    mobileOpen.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleDocumentClick)
  document.addEventListener('keydown', handleDocumentKeydown)
  window.addEventListener('resize', updateUserMenuPosition)
  window.addEventListener('scroll', updateUserMenuPosition, true)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleDocumentClick)
  document.removeEventListener('keydown', handleDocumentKeydown)
  window.removeEventListener('resize', updateUserMenuPosition)
  window.removeEventListener('scroll', updateUserMenuPosition, true)
})
</script>

<style scoped>
.header-shell {
  background: rgba(253, 253, 252, 0.85);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  color: #1a1a1a;
}

.wordmark {
  display: inline-flex;
  flex-direction: column;
  gap: 0;
  line-height: 1;
}

.brand {
  font-family: 'Fraunces', Georgia, serif;
  font-size: clamp(1.3rem, 2.8vw, 2rem);
  letter-spacing: 0.22em;
  font-weight: 400;
}

.sub {
  margin-top: 0.22rem;
  font-size: 0.62rem;
  letter-spacing: 0.35em;
  color: rgba(0, 0, 0, 0.58);
}

.menu-link {
  text-transform: uppercase;
  letter-spacing: 0.16em;
  font-size: 0.74rem;
  color: rgba(0, 0, 0, 0.75);
  transition: color 0.18s ease;
}

.menu-link:hover {
  color: rgba(0, 0, 0, 1);
}

.icon-btn {
  min-width: 2.2rem;
  height: 2.2rem;
  border: 0;
  border-radius: 6px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: transparent;
  transition: all 0.18s ease;
}

@media (max-width: 768px) {
  .icon-btn {
    min-width: 48px;
    height: 48px;
  }
}

.icon-btn:hover {
  background: rgba(0, 0, 0, 0.04);
}

.business-btn {
  text-transform: uppercase;
  letter-spacing: 0.13em;
  font-size: 0.68rem;
  font-weight: 600;
  color: #1f1f1f;
  padding: 0.42rem 0.6rem;
  border: 1px solid transparent;
  border-radius: 6px;
  background: transparent;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  transition: all 0.18s ease;
}

.business-btn:hover {
  background: rgba(0,0,0,0.05);
}

.dot {
  /* hidden: remove decorative dot indicators */
  display: none !important;
}

.menu-item {
  display: block;
  padding: 0.5rem 0.75rem;
  border-radius: 0.4rem;
  font-size: 0.88rem;
}

.menu-item:hover {
  background: #f2f2f2;
}

.business-btn {
  display: inline-flex;
  align-items: center;
  border: 1px solid rgb(0 0 0 / 0.1);
  border-radius: 999px;
  background: rgb(255 255 255 / 0.72);
  padding: 0.72rem 1.05rem;
  font-size: 0.72rem;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  color: #101010;
  transition: all 0.25s ease;
}

.business-btn:hover {
  background: #101010;
  color: #fff;
  transform: translateY(-1px);
}

.search-head {
  background: rgba(245, 245, 245, 0.97);
  backdrop-filter: blur(2px);
}

.search-shell {
  background: linear-gradient(180deg, #efefef 0%, #f4f4f4 42%, #f2f2f2 100%);
}

.search-input {
  width: 100%;
  background: transparent;
  border: 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.26);
  padding: 0.5rem 0.1rem;
  outline: none;
  font-size: clamp(1rem, 2.2vw, 1.35rem);
  letter-spacing: 0.03em;
}

.close-search-btn {
  width: 2.1rem;
  height: 2.1rem;
  border-radius: 6px;
  border: 1px solid rgba(0, 0, 0, 0.12);
  font-size: 0.74rem;
  text-transform: uppercase;
  background: transparent;
}

.results-strip {
  display: grid;
  grid-auto-flow: column;
  grid-auto-columns: minmax(170px, 230px);
  gap: 0.9rem;
  overflow-x: auto;
  padding-bottom: 0.45rem;
  scrollbar-width: thin;
  scrollbar-color: rgba(0, 0, 0, 0.35) transparent;
}

.results-strip::-webkit-scrollbar {
  height: 6px;
}

.results-strip::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.35);
  border-radius: 999px;
}

.editorial-card {
  display: block;
  color: inherit;
}

.editorial-media {
  width: 100%;
  aspect-ratio: 3 / 4;
  background: #d9d9d9;
  overflow: hidden;
}

.editorial-media img {
  transition: transform 0.35s ease;
}

.editorial-card:hover .editorial-media img {
  transform: scale(1.03);
}

.editorial-title {
  margin-top: 0.55rem;
  font-size: 0.72rem;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: rgba(0, 0, 0, 0.78);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

@media (max-width: 640px) {
  .brand {
    letter-spacing: 0.12em;
  }
}
</style>
