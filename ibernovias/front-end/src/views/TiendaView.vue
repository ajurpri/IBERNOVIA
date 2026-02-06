<template>
  <div class="min-h-screen bg-luxury-gray">
    <div class="bg-white border-b border-gray-100">
      <div class="max-w-7xl mx-auto px-4 py-10">
        <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-6">
          <div>
            <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase block mb-2">Colección 2026</span>
            <h1 class="ib-title font-serif text-4xl md:text-5xl font-bold text-luxury-black">Tienda</h1>
            <p class="text-gray-600 mt-2 max-w-2xl">Complementos nupciales de alta costura, selección premium y asesoramiento en tienda.</p>
          </div>
          <div class="flex items-center gap-4">
            <div class="px-4 py-2 rounded-full border border-gray-200 bg-white text-xs uppercase tracking-widest text-gray-500">Envío 24-48h</div>
            <div class="px-4 py-2 rounded-full border border-gray-200 bg-white text-xs uppercase tracking-widest text-gray-500">Calidad premium</div>
          </div>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 py-12">
      <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-10">
        <div class="bg-white rounded-2xl p-4 border border-gray-100 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Ubicación</p>
          <p class="text-sm text-luxury-black font-semibold">Plaza de Abastos · Andújar</p>
        </div>
        <div class="bg-white rounded-2xl p-4 border border-gray-100 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Horario</p>
          <p class="text-sm text-luxury-black font-semibold">Closes soon · 8 pm</p>
        </div>
        <div class="bg-white rounded-2xl p-4 border border-gray-100 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Atención</p>
          <p class="text-sm text-luxury-black font-semibold">Personalizada</p>
        </div>
        <div class="bg-white rounded-2xl p-4 border border-gray-100 shadow-sm">
          <p class="text-xs text-gray-500 uppercase tracking-widest">Contacto</p>
          <p class="text-sm text-luxury-black font-semibold">953 51 50 70</p>
        </div>
      </div>

      <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 sm:gap-4 lg:gap-6 items-stretch">
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
