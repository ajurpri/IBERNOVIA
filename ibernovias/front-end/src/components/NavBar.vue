<template>
  <nav class="w-full bg-white border-b border-gray-200 sticky top-0 z-50 shadow-sm">
    <div class="w-full px-4 sm:px-6 lg:px-8">
      <!-- Top Row: Logo + Search + Cart -->
      <div class="flex justify-between items-center h-20 gap-4">
        <!-- Logo -->
        <router-link to="/" class="flex-shrink-0 group flex items-center gap-2">
          <img class="h-14 w-auto transition transform group-hover:scale-110 duration-500" 
               src="/logo/logo.png" 
               alt="Ibernovia Logo"
               @error="logoError = true">
          <h1 v-if="logoError" class="text-2xl font-serif font-bold text-luxury-black tracking-tighter">IBERNOVIA</h1>
        </router-link>

        <!-- Búsqueda - Centrada -->
        <div class="flex-1 max-w-sm mx-auto px-4">
          <div class="relative">
            <input 
              v-model="searchQuery"
              @input="handleSearch"
              type="text"
              placeholder="Buscar productos..."
              class="w-full px-4 py-2 border-2 border-gray-300 rounded text-sm focus:outline-none focus:border-luxury-gold transition"
            >
            <button v-if="searchQuery" @click="searchQuery = ''" class="absolute right-3 top-2 text-gray-400 hover:text-gray-600">✕</button>
          </div>
          <!-- Dropdown de búsqueda -->
          <div v-if="searchQuery && searchResults.length > 0" class="absolute top-12 left-4 right-4 bg-white border border-gray-300 rounded shadow-lg z-50 max-h-60 overflow-y-auto">
            <router-link 
              v-for="prod in searchResults.slice(0, 5)" 
              :key="prod.id"
              :to="`/producto/${prod.id}`"
              @click="searchQuery = ''"
              class="block px-4 py-3 hover:bg-luxury-gold hover:text-white transition border-b last:border-b-0 text-sm"
            >
              {{ prod.nombre }} - €{{ prod.precio }}
            </router-link>
          </div>
        </div>

        <!-- Carrito + Usuario -->
        <div class="flex items-center space-x-4">
          <!-- Carrito -->
          <router-link to="/carrito" class="relative p-2 hover:text-luxury-gold transition group">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
            <span v-if="cartStore.totalItems > 0" class="absolute top-0 right-0 bg-luxury-gold text-white text-xs font-bold rounded-full w-5 h-5 flex items-center justify-center">
              {{ cartStore.totalItems }}
            </span>
          </router-link>

          <!-- Menú Usuario -->
          <div class="relative">
            <button 
              @click="showUserMenu = !showUserMenu"
              class="flex items-center gap-2 p-2 hover:text-luxury-gold transition group">
              <svg v-if="!authStore.isAuthenticated" class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
              </svg>
              <span v-else class="text-sm font-semibold truncate max-w-[150px]">{{ authStore.user?.nombre }}</span>
            </button>

            <!-- Dropdown Menu -->
            <div v-if="showUserMenu" class="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded shadow-lg z-50">
              <div v-if="!authStore.isAuthenticated" class="p-4 space-y-2">
                <router-link 
                  to="/auth" 
                  @click="showUserMenu = false"
                  class="block w-full text-center bg-luxury-black text-white py-2 font-bold uppercase tracking-widest text-sm hover:bg-luxury-gold hover:text-luxury-black transition rounded">
                  Iniciar Sesión
                </router-link>
              </div>
              <div v-else class="p-4 space-y-2 border-b border-gray-200">
                <p class="text-sm font-semibold text-gray-900">{{ authStore.user?.nombre }} {{ authStore.user?.apellido }}</p>
                <p class="text-xs text-gray-500">{{ authStore.user?.email }}</p>
              </div>
              <nav v-if="authStore.isAuthenticated" class="p-2 space-y-1">
                <router-link to="/carrito" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">
                  Mi Carrito
                </router-link>
                <router-link to="/" @click="showUserMenu = false" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">
                  Mis Órdenes
                </router-link>
                <a href="#" class="block px-4 py-2 text-sm hover:bg-gray-100 transition">
                  Mi Perfil
                </a>
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

      <!-- Bottom Row: Menu -->
      <div class="border-t border-gray-100 py-3">
        <div class="flex justify-center space-x-8 text-xs font-medium uppercase tracking-[0.15em] text-gray-600">
          <router-link to="/" class="hover:text-luxury-gold transition">Inicio</router-link>
          <router-link to="/productos" class="hover:text-luxury-gold transition">Colección</router-link>
          <router-link to="/about" class="hover:text-luxury-gold transition">Nosotros</router-link>
          <router-link to="/contacto" class="hover:text-luxury-gold transition">Contacto</router-link>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import axios from 'axios'

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()

const searchQuery = ref('')
const searchResults = ref([])
const showUserMenu = ref(false)
const logoError = ref(false)

const handleSearch = async () => {
  if (searchQuery.value.trim().length < 2) {
    searchResults.value = []
    return
  }
  
  try {
    const res = await axios.get(`http://localhost:8080/api/productos/buscar/${searchQuery.value}`)
    searchResults.value = res.data
  } catch (e) {
    console.error('Error buscando:', e)
    searchResults.value = []
  }
}

const handleLogout = () => {
  authStore.logout()
  showUserMenu.value = false
  router.push('/')
}
</script>

<style scoped>
nav {
  width: 100vw;
  margin-left: calc(-50vw + 50%);
}
</style>
