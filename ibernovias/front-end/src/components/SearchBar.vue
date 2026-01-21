<template>
  <form @submit.prevent="buscar" class="flex items-center gap-2">
    <div class="flex-1 relative">
      <input 
        v-model="query"
        type="text"
        placeholder="Buscar productos..."
        class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
      >
      <button v-if="query" @click="query = ''" class="absolute right-3 top-2.5 text-gray-400 hover:text-gray-600">✕</button>
    </div>
    <button type="submit" class="px-4 py-2 bg-luxury-gold text-white rounded hover:bg-opacity-80 transition">
      🔍
    </button>
  </form>

  <div v-if="resultados.length > 0" class="absolute top-full left-0 right-0 bg-white border shadow-lg z-40 mt-2 rounded">
    <div class="px-4 py-4 max-h-96 overflow-y-auto">
      <p class="text-sm text-gray-600 mb-3">{{ resultados.length }} resultados encontrados</p>
      <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
        <router-link 
          v-for="prod in resultados.slice(0, 6)" 
          :key="prod.id"
          :to="`/producto/${prod.id}`"
          @click="query = ''"
          class="text-center hover:bg-gray-50 p-2 rounded transition"
        >
          <img :src="prod.imagen || 'https://placehold.co/100x100'" :alt="prod.nombre" class="w-full aspect-square object-cover rounded mb-2">
          <p class="text-xs font-semibold truncate">{{ prod.nombre }}</p>
          <p class="text-xs text-luxury-gold">{{ prod.precio }}€</p>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const query = ref('')
const resultados = ref([])

const buscar = async () => {
  if (query.value.trim().length < 2) return
  
  try {
    const res = await axios.get(`http://localhost:8080/api/productos/buscar/${query.value}`)
    resultados.value = res.data
  } catch (e) {
    console.error('Error buscando:', e)
  }
}
</script>

