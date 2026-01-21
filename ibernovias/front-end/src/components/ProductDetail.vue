<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
    <router-link to="/" class="text-luxury-gold hover:underline mb-8 inline-block text-sm tracking-widest uppercase">
      ← Volver a Colección
    </router-link>

    <div v-if="loading" class="text-center py-20">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-luxury-gold border-t-transparent"></div>
    </div>

    <div v-else-if="error" class="text-center py-20 text-red-500">
      <p class="text-lg font-bold">Producto no encontrado</p>
    </div>

    <div v-else-if="producto" class="grid grid-cols-1 md:grid-cols-2 gap-12">
      <!-- Imagen -->
      <div class="bg-gray-100 aspect-square rounded-lg overflow-hidden flex items-center justify-center sticky top-20">
        <img 
          v-if="producto.imagen" 
          :src="producto.imagen" 
          :alt="producto.nombre"
          class="w-full h-full object-cover hover:scale-105 transition duration-500"
        >
        <div v-else class="text-gray-400 text-center">
          <p>Sin imagen disponible</p>
        </div>
      </div>

      <!-- Detalles -->
      <div class="flex flex-col justify-center">
        <span class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase mb-4">
          {{ producto.categoria }}
        </span>
        
        <h1 class="font-serif text-4xl md:text-5xl text-luxury-black mb-6">
          {{ producto.nombre }}
        </h1>

        <p class="text-3xl text-luxury-gold font-bold mb-8">
          {{ producto.precio }}€
        </p>

        <div v-if="producto.descripcion" class="text-gray-600 leading-relaxed mb-8 text-sm border-b pb-8">
          {{ producto.descripcion }}
        </div>

        <div class="mb-8 grid grid-cols-2 gap-4">
          <div v-if="producto.stock" class="text-sm text-green-600 font-semibold">
            ✓ En stock ({{ producto.stock }})
          </div>
          <div v-else class="text-sm text-red-600 font-semibold">
            ✗ Agotado
          </div>
        </div>

        <!-- Selector de cantidad -->
        <div class="flex items-center gap-4 mb-8">
          <label class="text-sm font-semibold text-gray-700">Cantidad:</label>
          <div class="flex items-center border border-gray-300 rounded">
            <button @click="cantidad = Math.max(1, cantidad - 1)" class="px-3 py-2 hover:bg-gray-100">-</button>
            <input v-model.number="cantidad" type="number" min="1" class="w-12 text-center border-none outline-none">
            <button @click="cantidad++" class="px-3 py-2 hover:bg-gray-100">+</button>
          </div>
        </div>

        <div class="flex gap-4">
          <button 
            @click="agregarCarrito"
            :disabled="!producto.stock"
            class="flex-1 bg-luxury-gold text-white px-8 py-4 hover:bg-opacity-80 transition uppercase text-sm font-bold tracking-widest disabled:opacity-50">
            {{ cartStore.items.find(i => i.id === producto.id) ? '✓ En carrito' : 'Agregar al Carrito' }}
          </button>
          <button class="flex-1 border border-luxury-gold text-luxury-gold px-8 py-4 hover:bg-luxury-gold hover:text-white transition uppercase text-sm font-bold tracking-widest">
            ❤️ Guardar
          </button>
        </div>

        <div v-if="agregado" class="mt-4 p-4 bg-green-50 border border-green-200 text-green-700 text-sm rounded">
          ✓ Producto agregado al carrito
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useCartStore } from '../stores/cart'
import axios from 'axios'

const route = useRoute()
const cartStore = useCartStore()
const producto = ref(null)
const loading = ref(true)
const error = ref(false)
const cantidad = ref(1)
const agregado = ref(false)

onMounted(async () => {
  try {
    const id = route.params.id
    const res = await axios.get(`http://localhost:8080/api/productos/${id}`)
    producto.value = res.data
  } catch (e) {
    console.error('Error cargando producto:', e)
    error.value = true
  } finally {
    loading.value = false
  }
})

const agregarCarrito = () => {
  cartStore.addItem(producto.value, cantidad.value)
  agregado.value = true
  setTimeout(() => agregado.value = false, 2000)
}
</script>
