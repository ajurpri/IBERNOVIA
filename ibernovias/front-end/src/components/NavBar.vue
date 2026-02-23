<template>
  <nav class="w-full bg-white/88 backdrop-blur-md border-b border-white/70 sticky top-0 z-[60] shadow-sm overflow-visible">
    <!-- Business Access Modal -->
    <BusinessAccessModal ref="businessModalRef" />
    <EventsModal ref="eventsModalRef" />
    
    <!-- Signature thread -->
    <div class="h-px bg-gradient-to-r from-transparent via-luxury-gold/70 to-transparent"></div>
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 overflow-visible">
      <!-- Top Row: Logo + Search + Cart + User -->
      <div class="flex flex-col gap-4 sm:gap-6 py-5 sm:py-6 sm:flex-row sm:items-center sm:justify-between">
        <!-- Logo -->
        <router-link to="/" class="flex items-center gap-4 sm:gap-5 flex-shrink-0">
          <img
            :src="logoSrc"
            :srcset="logoSrcSet"
            sizes="(max-width: 640px) 140px, 180px"
            alt="IBERNOVIA"
            class="h-14 sm:h-16 w-auto"
            loading="eager"
            decoding="async"
            width="140"
            height="56"
          />
          <span class="hidden sm:flex flex-col leading-tight">
            <span class="font-serif font-semibold text-3xl text-luxury-black hover:text-luxury-gold transition">IBERNOVIA</span>
            <span class="text-[11px] uppercase tracking-[0.4em] text-gray-500">Atelier</span>
          </span>
        </router-link>

        <!-- Búsqueda -->
        <div ref="searchContainer" class="w-full sm:flex-1 sm:max-w-md sm:mx-4">
          <div class="relative">
            <input 
              v-model="searchQuery"
              @input="handleSearch"
              @focus="openSearch"
              @keydown.escape.prevent="closeSearch"
              type="text"
              placeholder="Buscar productos..."
              aria-label="Buscar productos"
              class="w-full px-4 sm:px-5 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold transition text-base bg-white/95"
            >
            <!-- Resultados búsqueda -->
            <div v-if="showSearchDropdown && searchQuery" class="absolute top-full left-0 right-0 mt-1 bg-white border border-gray-200 rounded shadow-lg z-50 max-h-72 overflow-y-auto" role="listbox" aria-live="polite">
              <div v-if="isLoadingProducts" class="px-4 py-3 text-xs text-gray-500 uppercase tracking-widest">
                Cargando productos...
              </div>

              <div v-else-if="productLoadError" class="px-4 py-3 text-sm text-red-600">
                No se puede conectar con el servidor.
              </div>

              <div v-else-if="searchResults.length === 0" class="px-4 py-3 text-sm text-gray-500">
                Sin resultados para "{{ searchQuery }}"
              </div>

              <router-link 
                v-else
                v-for="prod in searchResults" 
                :key="prod.id"
                :to="`/producto/${prod.id}`"
                @click="handlePickProduct"
                class="flex items-center gap-3 px-4 py-3 hover:bg-gray-50 transition border-b last:border-b-0"
                role="option"
              >
                <div class="w-10 h-10 rounded bg-gray-100 overflow-hidden flex-shrink-0">
                  <img
                    v-if="prod.imagen"
                    :src="prod.imagen"
                    :alt="prod.nombre"
                    class="w-full h-full object-cover"
                    loading="lazy"
                    decoding="async"
                    @error="(e) => { e.target.style.display = 'none' }"
                  />
                </div>
                <div class="min-w-0">
                  <div class="font-semibold text-sm truncate">{{ prod.nombre }}</div>
                  <div v-if="authStore.isBusinessUser" class="text-xs text-gray-500">€{{ prod.precio }}</div>
                  <div v-else class="text-xs text-gray-400">Solo empresas</div>
                </div>
              </router-link>
            </div>
          </div>
        </div>

        <!-- Carrito + Usuario -->
        <div class="flex items-center flex-wrap gap-4 sm:gap-7">
          <button
            type="button"
            @click="openEventsModal"
            class="text-xs font-semibold uppercase tracking-widest px-3 py-2 bg-white border border-luxury-gold/40 text-luxury-black rounded hover:bg-luxury-black hover:text-white transition"
          >
            Eventos
          </button>

          <!-- Botón Acceso Empresarial (si no es usuario empresarial) -->
          <button
            v-if="!authStore.isBusinessUser"
            @click="openBusinessModal"
            class="text-xs font-bold uppercase tracking-widest px-3 py-2 bg-blue-50 text-blue-600 rounded hover:bg-blue-100 transition border border-blue-200">
            🏢 Acceso Empresarial
          </button>
          
          <!-- Indicador de Acceso Empresarial -->
          <div v-else class="text-xs font-bold uppercase tracking-widest px-3 py-2 bg-green-50 text-green-600 rounded border border-green-200">
            ✓ Acceso Empresarial
          </div>

          <!-- Carrito (solo empresas) -->
          <router-link v-if="authStore.isBusinessUser" to="/carrito" class="relative p-2.5 hover:text-luxury-gold transition" aria-label="Ver carrito">
            <svg class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
            <transition
              enter-active-class="transition duration-200 ease-out"
              enter-from-class="scale-150 opacity-0"
              enter-to-class="scale-100 opacity-100"
              leave-active-class="transition duration-200 ease-in">
              <span
                v-if="cartStore.totalItems > 0"
                :key="cartStore.totalItems"
                class="absolute -top-2 -right-2 bg-luxury-gold text-white text-xs font-bold rounded-full w-6 h-6 flex items-center justify-center shadow-lg"
                role="status"
                aria-live="polite"
                aria-label="Productos en carrito"
              >
                {{ cartStore.totalItems }}
              </span>
            </transition>
          </router-link>

          <!-- Usuario -->
          <div ref="userMenuContainer" class="relative z-[70]">
            <button 
              @click="toggleUserMenu"
              :aria-expanded="showUserMenu ? 'true' : 'false'"
              aria-label="Menú de usuario"
              class="flex items-center gap-2 p-2.5 hover:text-luxury-gold transition"
              ref="userMenuButton">
              <svg v-if="!authStore.isAuthenticated" class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
              </svg>
              <span v-else class="text-base font-semibold">{{ authStore.user?.nombre || 'Usuario' }}</span>
            </button>

            <!-- Dropdown Menu -->
            <Teleport to="body">
              <div
                v-if="showUserMenu"
                class="fixed w-56 bg-white border border-gray-200 rounded shadow-xl z-[9999]"
                :style="userMenuStyles"
                ref="userMenuDropdown"
              >
                <div v-if="!authStore.isAuthenticated" class="p-4">
                  <router-link 
                    to="/auth" 
                    @click="showUserMenu = false"
                    class="block text-center bg-luxury-black text-white py-2 font-bold uppercase text-sm hover:bg-luxury-gold hover:text-black transition rounded">
                    Iniciar Sesión
                  </router-link>
                </div>
                <div v-else>
                  <div class="p-4 border-b border-gray-200">
                    <p class="font-semibold">{{ authStore.user?.nombre }} {{ authStore.user?.apellido }}</p>
                    <p class="text-xs text-gray-500">{{ authStore.user?.email }}</p>
                  </div>
                  <nav class="p-2">
                    <router-link v-if="authStore.isBusinessUser" to="/carrito" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">Pedidos</router-link>
                    <router-link v-else to="/acceso-empresarial" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">Alta empresarial</router-link>
                    <router-link to="/cuenta" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">Mi Cuenta</router-link>
                    <router-link v-if="authStore.isAdmin" to="/admin" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">Panel Admin</router-link>
                    <button 
                      @click="handleLogout"
                      class="w-full text-left px-4 py-2 text-sm text-red-500 hover:bg-red-50 transition font-semibold">
                      Cerrar Sesión
                    </button>
                  </nav>
                </div>
              </div>
            </Teleport>
          </div>
        </div>
      </div>

      <!-- Bottom Menu -->
      <div class="border-t border-gray-100 py-4 hidden sm:block">
        <div class="relative flex items-center justify-center">
          <!-- Ornament line -->
          <div class="absolute left-0 right-0 top-1/2 -translate-y-1/2 h-px bg-gradient-to-r from-transparent via-luxury-gold/40 to-transparent"></div>

          <!-- Links group -->
          <div class="relative flex items-center justify-center gap-2 sm:gap-4 bg-white/80 backdrop-blur px-3 sm:px-4 py-2 rounded-full border border-luxury-gold/20 shadow-sm">
            <router-link to="/" :class="navPillClass('/')">Inicio</router-link>

            <!-- diamond divider -->
            <span class="mx-1 sm:mx-2 w-3 h-3 rotate-45 border border-luxury-gold/70 bg-white"></span>

            <router-link to="/tienda" :class="navPillClass('/tienda')">Catálogo</router-link>

            <span class="mx-1 sm:mx-2 w-3 h-3 rotate-45 border border-luxury-gold/70 bg-white"></span>

            <router-link to="/contacto" :class="navPillClass('/contacto')">Contacto</router-link>
          </div>
        </div>
      </div>

      <router-link
        v-if="!authStore.isBusinessUser"
        to="/acceso-empresarial"
        class="fixed sm:hidden bottom-4 left-1/2 -translate-x-1/2 z-[70] bg-luxury-black text-white px-5 py-2.5 rounded-full text-[11px] uppercase tracking-[0.2em] font-semibold shadow-lg hover:bg-luxury-gold hover:text-luxury-black transition"
      >
        Alta empresarial
      </router-link>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount, nextTick } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import { apiFetch } from '../lib/api'
import BusinessAccessModal from './BusinessAccessModal.vue'
import EventsModal from './EventsModal.vue'
const logoBase = import.meta.env.BASE_URL + 'logo/optimized/'
const logoSrc = `${logoBase}logo-140.jpg`
const logoSrcSet = `${logoBase}logo-80.jpg 80w, ${logoBase}logo-140.jpg 140w, ${logoBase}logo-280.jpg 280w`

const router = useRouter()
const route = useRoute()
const cartStore = useCartStore()
const authStore = useAuthStore()

const searchQuery = ref('')
const showSearchDropdown = ref(false)
const isLoadingProducts = ref(false)
const productLoadError = ref('')
const showUserMenu = ref(false)
const allProducts = ref([])
const searchTimeout = ref(null)
const businessModalRef = ref(null)
const eventsModalRef = ref(null)

const searchContainer = ref(null)
const userMenuContainer = ref(null)
const userMenuButton = ref(null)
const userMenuDropdown = ref(null)
const userMenuStyles = ref({ top: '0px', left: '0px' })

const searchResults = computed(() => {
  if (!searchQuery.value.trim()) return []
  const query = searchQuery.value.toLowerCase()
  return allProducts.value.filter(p => p.nombre.toLowerCase().includes(query)).slice(0, 5)
})

const debounceSearch = () => {
  clearTimeout(searchTimeout.value)
  searchTimeout.value = setTimeout(() => {
    loadAllProducts()
  }, 300)
}

const navPillClass = (path) => {
  const isActive = route.path === path
  const base = 'px-4 sm:px-5 py-2.5 text-[12px] sm:text-sm font-semibold uppercase tracking-[0.22em] rounded-full transition focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white'
  const active = 'bg-luxury-black text-white shadow border border-luxury-gold/60'
  const inactive = 'text-gray-700 hover:text-luxury-black hover:bg-white border border-transparent'
  return `${base} ${isActive ? active : inactive}`
}

const openSearch = () => {
  showSearchDropdown.value = true
}

const closeSearch = () => {
  showSearchDropdown.value = false
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

const loadAllProducts = async () => {
  if (allProducts.value.length > 0 || isLoadingProducts.value) return
  isLoadingProducts.value = true
  productLoadError.value = ''
  try {
    const res = await apiFetch('/api/productos')
    const data = await res.json()
    allProducts.value = Array.isArray(data) ? data : []
  } catch (e) {
    productLoadError.value = 'No se pudo cargar'
    console.error('Error cargando productos:', e)
  } finally {
    isLoadingProducts.value = false
  }
}

const handleSearch = () => {
  showSearchDropdown.value = true
  if (searchQuery.value.trim()) {
    debounceSearch()
  }
}

const handlePickProduct = () => {
  closeSearch()
  searchQuery.value = ''
}

const handleLogout = () => {
  authStore.logout()
  showUserMenu.value = false
  router.push('/')
}

const openBusinessModal = () => {
  if (businessModalRef.value) {
    businessModalRef.value.openModal()
  }
}

const openEventsModal = () => {
  if (eventsModalRef.value) {
    eventsModalRef.value.openModal()
  }
}

const toggleUserMenu = async () => {
  showUserMenu.value = !showUserMenu.value
  if (showUserMenu.value) {
    await nextTick()
    updateUserMenuPosition()
  }
}

const handleDocumentClick = (event) => {
  const target = event.target

  const clickedOutsideUserMenu =
    userMenuContainer.value && !userMenuContainer.value.contains(target) &&
    (!userMenuDropdown.value || !userMenuDropdown.value.contains(target))

  if (clickedOutsideUserMenu) {
    showUserMenu.value = false
  }
  if (searchContainer.value && !searchContainer.value.contains(target)) {
    closeSearch()
  }
}

const handleDocumentKeydown = (event) => {
  if (event.key === 'Escape') {
    showUserMenu.value = false
    closeSearch()
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
