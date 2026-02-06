<template>
  <nav class="w-full bg-white/90 backdrop-blur-md border-b border-gray-100 sticky top-0 z-50 shadow">
    <!-- Signature thread -->
    <div class="h-px bg-gradient-to-r from-transparent via-luxury-gold/70 to-transparent"></div>
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Top Row: Logo + Search + Cart + User -->
      <div class="flex justify-between items-center h-20 gap-4">
        <!-- Logo -->
        <router-link to="/" class="flex items-center gap-3 flex-shrink-0">
          <img :src="logoSrc" alt="IBERNOVIA" class="h-10 w-auto" />
          <span class="font-serif font-bold text-2xl text-luxury-black hover:text-luxury-gold transition">IBERNOVIA</span>
        </router-link>

        <!-- Búsqueda -->
        <div ref="searchContainer" class="flex-1 max-w-md mx-4">
          <div class="relative">
            <input 
              v-model="searchQuery"
              @input="handleSearch"
              @focus="openSearch"
              @keydown.escape.prevent="closeSearch"
              type="text"
              placeholder="Buscar productos..."
              aria-label="Buscar productos"
              class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
            >
            <!-- Resultados búsqueda -->
            <div v-if="showSearchDropdown && searchQuery" class="absolute top-full left-0 right-0 mt-1 bg-white border border-gray-200 rounded shadow-lg z-50 max-h-72 overflow-y-auto">
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
              >
                <div class="w-10 h-10 rounded bg-gray-100 overflow-hidden flex-shrink-0">
                  <img
                    v-if="prod.imagen"
                    :src="prod.imagen"
                    :alt="prod.nombre"
                    class="w-full h-full object-cover"
                    loading="lazy"
                    @error="(e) => { e.target.style.display = 'none' }"
                  />
                </div>
                <div class="min-w-0">
                  <div class="font-semibold text-sm truncate">{{ prod.nombre }}</div>
                  <div class="text-xs text-gray-500">€{{ prod.precio }}</div>
                </div>
              </router-link>
            </div>
          </div>
        </div>

        <!-- Carrito + Usuario -->
        <div class="flex items-center space-x-6">
          <!-- Carrito -->
          <router-link to="/carrito" class="relative p-2 hover:text-luxury-gold transition">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
            <span v-if="cartStore.totalItems > 0" class="absolute -top-2 -right-2 bg-luxury-gold text-white text-xs font-bold rounded-full w-5 h-5 flex items-center justify-center">
              {{ cartStore.totalItems }}
            </span>
          </router-link>

          <!-- Usuario -->
          <div ref="userMenuContainer" class="relative">
            <button 
              @click="showUserMenu = !showUserMenu"
              :aria-expanded="showUserMenu ? 'true' : 'false'"
              aria-label="Menú de usuario"
              class="flex items-center gap-2 p-2 hover:text-luxury-gold transition">
              <svg v-if="!authStore.isAuthenticated" class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
              </svg>
              <span v-else class="text-sm font-semibold">{{ authStore.user?.nombre || 'Usuario' }}</span>
            </button>

            <!-- Dropdown Menu -->
            <div v-if="showUserMenu" class="absolute right-0 mt-2 w-56 bg-white border border-gray-200 rounded shadow-xl z-50">
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
                  <router-link to="/carrito" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">Mi Carrito</router-link>
                  <router-link to="/cuenta" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">Mi Cuenta</router-link>
                  <button 
                    @click="handleLogout"
                    class="w-full text-left px-4 py-2 text-sm text-red-500 hover:bg-red-50 transition font-semibold">
                    Cerrar Sesión
                  </button>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Bottom Menu -->
      <div class="border-t border-gray-100 py-3">
        <div class="relative flex items-center justify-center">
          <!-- Ornament line -->
          <div class="absolute left-0 right-0 top-1/2 -translate-y-1/2 h-px bg-gradient-to-r from-transparent via-luxury-gold/40 to-transparent"></div>

          <!-- Links group -->
          <div class="relative flex items-center justify-center gap-2 sm:gap-3 bg-white/70 backdrop-blur px-2 sm:px-3 py-1.5 rounded-full border border-luxury-gold/20 shadow-sm">
            <router-link to="/" :class="navPillClass('/')">Inicio</router-link>

            <!-- diamond divider -->
            <span class="mx-1 sm:mx-2 w-2.5 h-2.5 rotate-45 border border-luxury-gold/70 bg-white"></span>

            <router-link to="/tienda" :class="navPillClass('/tienda')">Tienda</router-link>

            <span class="mx-1 sm:mx-2 w-2.5 h-2.5 rotate-45 border border-luxury-gold/70 bg-white"></span>

            <router-link to="/contacto" :class="navPillClass('/contacto')">Contacto</router-link>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'

const logoSrc = import.meta.env.BASE_URL + 'logo/logo.jpg'

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

const searchContainer = ref(null)
const userMenuContainer = ref(null)

const searchResults = computed(() => {
  if (!searchQuery.value.trim()) return []
  const query = searchQuery.value.toLowerCase()
  return allProducts.value.filter(p => p.nombre.toLowerCase().includes(query)).slice(0, 5)
})

const navPillClass = (path) => {
  const isActive = route.path === path
  const base = 'px-3 sm:px-4 py-2 text-[11px] sm:text-xs font-semibold uppercase tracking-[0.18em] rounded-full transition focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2 focus-visible:ring-offset-white'
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

const loadAllProducts = async () => {
  if (allProducts.value.length > 0 || isLoadingProducts.value) return
  isLoadingProducts.value = true
  productLoadError.value = ''
  try {
    const res = await fetch('http://localhost:8080/api/productos')
    const data = await res.json()
    allProducts.value = Array.isArray(data) ? data : []
  } catch (e) {
    productLoadError.value = 'No se pudo cargar'
    console.error('Error cargando productos:', e)
  } finally {
    isLoadingProducts.value = false
  }
}

const handleSearch = async () => {
  showSearchDropdown.value = true
  await loadAllProducts()
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

const handleDocumentClick = (event) => {
  const target = event.target

  if (userMenuContainer.value && !userMenuContainer.value.contains(target)) {
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
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleDocumentClick)
  document.removeEventListener('keydown', handleDocumentKeydown)
})
</script>
