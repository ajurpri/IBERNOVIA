<template>
  <div class="min-h-screen bg-gray-50 py-12 px-4">
    <div class="max-w-7xl mx-auto">
      <h1 class="font-serif text-4xl font-bold text-luxury-black mb-8">Tienda</h1>
      <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <ProductCard 
          v-for="prod in productos" 
          :key="prod.id" 
          :producto="prod" 
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ProductCard from '../components/ProductCard.vue'
import axios from 'axios'

const productos = ref([])

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/productos')
    productos.value = res.data
  } catch (error) {
    console.error('Error cargando productos:', error)
  }
})
</script>
