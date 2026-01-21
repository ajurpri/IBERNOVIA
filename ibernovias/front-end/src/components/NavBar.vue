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

        <!-- Carrito -->
        <div class="flex items-center space-x-4">
          <router-link to="/carrito" class="relative p-2 hover:text-luxury-gold transition group">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
            <span v-if="cartStore.totalItems > 0" class="absolute top-0 right-0 bg-luxury-gold text-white text-xs font-bold rounded-full w-5 h-5 flex items-center justify-center">
              {{ cartStore.totalItems }}
            </span>
          </router-link>
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
import { ref, computed } from 'vue'
import { useCartStore } from '../stores/cart'
import axios from 'axios'

const cartStore = useCartStore()
const searchQuery = ref('')
const searchResults = ref([])
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
</script>

<style scoped>
nav {
  width: 100vw;
  margin-left: calc(-50vw + 50%);
}
</style>
