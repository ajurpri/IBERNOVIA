<template>
  <nav class="w-full bg-white border-b-2 border-luxury-gold sticky top-0 z-50 shadow">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Top Row: Logo + Search + Cart + User -->
      <div class="flex justify-between items-center h-20 gap-4">
        <!-- Logo -->
        <router-link to="/" class="flex-shrink-0 font-serif font-bold text-2xl text-luxury-black hover:text-luxury-gold transition">
          IBERNOVIA
        </router-link>

        <!-- Búsqueda -->
        <div class="flex-1 max-w-md mx-4">
          <div class="relative">
            <input 
              v-model="searchQuery"
              @input="handleSearch"
              type="text"
              placeholder="Buscar productos..."
              class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
            >
            <!-- Resultados búsqueda -->
            <div v-if="searchQuery && searchResults.length > 0" class="absolute top-full left-0 right-0 mt-1 bg-white border border-gray-300 rounded shadow-lg z-50 max-h-64 overflow-y-auto">
              <router-link 
                v-for="prod in searchResults" 
                :key="prod.id"
                :to="`/producto/${prod.id}`"
                @click="searchQuery = ''"
                class="block px-4 py-2 hover:bg-gray-100 transition border-b last:border-b-0"
              >
                <div class="font-semibold text-sm">{{ prod.nombre }}</div>
                <div class="text-xs text-gray-500">€{{ prod.precio }}</div>
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
          <div class="relative">
            <button 
              @click="showUserMenu = !showUserMenu"
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
      <div class="border-t border-gray-200 py-3 flex justify-center space-x-8 text-xs font-semibold uppercase">
        <router-link to="/" class="hover:text-luxury-gold transition">Inicio</router-link>
        <router-link to="/tienda" class="hover:text-luxury-gold transition">Tienda</router-link>
        <router-link to="/contacto" class="hover:text-luxury-gold transition">Contacto</router-link>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()

const searchQuery = ref('')
const showUserMenu = ref(false)
const allProducts = ref([])

const searchResults = computed(() => {
  if (!searchQuery.value.trim()) return []
  const query = searchQuery.value.toLowerCase()
  return allProducts.value.filter(p => p.nombre.toLowerCase().includes(query)).slice(0, 5)
})

const handleSearch = async () => {
  if (allProducts.value.length === 0) {
    try {
      const res = await fetch('http://localhost:8080/api/productos')
      allProducts.value = await res.json()
    } catch (e) {
      console.error('Error cargando productos:', e)
    }
  }
}

const handleLogout = () => {
  authStore.logout()
  showUserMenu.value = false
  router.push('/')
}
</script>
