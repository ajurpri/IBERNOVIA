<template>
  <div class="min-h-screen bg-white py-8 md:py-12">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Breadcrumb -->
      <div class="mb-8 text-sm text-gray-500">
        <router-link to="/" class="hover:text-luxury-gold transition">Inicio</router-link> /
        <span class="ml-2">{{ producto?.nombre }}</span>
      </div>

      <!-- Estado de Carga -->
      <div v-if="loading" class="text-center py-20">
        <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-luxury-gold border-t-transparent"></div>
      </div>

      <!-- Producto No Encontrado -->
      <div v-else-if="error" class="text-center py-20">
        <p class="text-red-500 font-bold text-lg mb-4">Producto no encontrado</p>
        <router-link to="/" class="inline-block bg-luxury-black text-white px-6 py-2 hover:bg-luxury-gold hover:text-luxury-black transition">
          Volver
        </router-link>
      </div>

      <!-- Contenido del Producto -->
      <div v-else-if="producto" class="grid grid-cols-1 md:grid-cols-2 gap-8 lg:gap-12">
        <!-- Galería de Imágenes -->
        <div class="flex flex-col gap-4">
          <!-- Imagen Principal -->
          <div class="bg-gray-50 aspect-[3/4] overflow-hidden">
            <img :src="producto.imagen" 
                 :alt="producto.nombre"
                 @error="imagenError = true"
                 :class="[
                   'w-full h-full object-cover transition duration-500',
                   !imagenError ? '' : 'hidden'
                 ]">
            <div v-if="imagenError" class="w-full h-full flex items-center justify-center">
              <div class="text-center">
                <svg class="w-20 h-20 mx-auto text-gray-300 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                </svg>
                <p class="text-gray-400 text-sm">Imagen no disponible</p>
              </div>
            </div>
          </div>

          <!-- Botón Badge -->
          <div class="flex gap-2">
            <span class="inline-block bg-luxury-gold text-luxury-black px-4 py-2 text-xs font-bold uppercase tracking-widest">
              {{ producto.categoria }}
            </span>
            <span v-if="producto.stock > 0" class="inline-block bg-green-100 text-green-700 px-4 py-2 text-xs font-bold uppercase tracking-widest">
              En Stock ({{ producto.stock }})
            </span>
            <span v-else class="inline-block bg-red-100 text-red-700 px-4 py-2 text-xs font-bold uppercase tracking-widest">
              Agotado
            </span>
          </div>
        </div>

        <!-- Información del Producto -->
        <div class="flex flex-col">
          <!-- Nombre y Precio -->
          <div class="mb-6 pb-6 border-b border-gray-200">
            <h1 class="font-serif text-3xl md:text-4xl text-luxury-black mb-3">
              {{ producto.nombre }}
            </h1>
            <p class="text-gray-500 text-sm mb-6 leading-relaxed">
              {{ producto.descripcion }}
            </p>
            <div class="flex items-baseline gap-3">
              <span class="text-4xl font-bold text-luxury-gold">{{ producto.precio }}€</span>
              <span class="text-lg text-gray-400 line-through">{{ (producto.precio * 1.1).toFixed(2) }}€</span>
            </div>
          </div>

          <!-- SKU y Ref -->
          <div class="mb-6 pb-6 border-b border-gray-200 space-y-2 text-sm">
            <div>
              <span class="text-gray-500">Referencia:</span>
              <span class="font-mono text-gray-900 ml-2">#{{ producto.id }}</span>
            </div>
            <div>
              <span class="text-gray-500">Stock:</span>
              <span class="text-gray-900 ml-2">{{ producto.stock }} unidades</span>
            </div>
          </div>

          <!-- Selector de Cantidad y Agregar al Carrito -->
          <div class="mb-8">
            <label class="block text-sm font-bold uppercase tracking-widest text-gray-700 mb-3">Cantidad</label>
            <div class="flex gap-4 items-end">
              <div class="flex items-center border border-gray-300 rounded">
                <button @click="cantidad > 1 && cantidad--" class="px-4 py-2 text-gray-600 hover:bg-gray-100 text-lg">−</button>
                <input 
                  v-model.number="cantidad" 
                  type="number" 
                  min="1" 
                  :max="producto.stock"
                  class="w-16 text-center border-0 focus:outline-none text-lg font-semibold"
                >
                <button @click="cantidad < producto.stock && cantidad++" class="px-4 py-2 text-gray-600 hover:bg-gray-100 text-lg">+</button>
              </div>
            </div>
          </div>

          <!-- Botón Agregar al Carrito -->
          <button 
            v-if="producto.stock > 0"
            @click="agregarAlCarrito"
            :disabled="agregando"
            class="w-full bg-luxury-black text-white py-4 font-bold uppercase tracking-widest text-lg hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 mb-3">
            {{ agregando ? 'Agregando...' : 'Agregar al Carrito' }}
          </button>
          <button v-else disabled class="w-full bg-gray-300 text-gray-500 py-4 font-bold uppercase tracking-widest text-lg cursor-not-allowed">
            Producto Agotado
          </button>

          <!-- Link a Carrito -->
          <div v-if="productoAgregado" class="text-center">
            <router-link to="/carrito" class="inline-block text-luxury-gold hover:text-luxury-black font-bold uppercase tracking-widest text-sm transition border-b-2 border-luxury-gold hover:border-luxury-black">
              Ver Carrito →
            </router-link>
          </div>

          <!-- Info de Envío -->
          <div class="mt-8 pt-8 border-t border-gray-200 space-y-3 text-sm text-gray-600">
            <div class="flex items-start gap-3">
              <svg class="w-5 h-5 text-luxury-gold mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path d="M8 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM15 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z"/>
                <path d="M3 4a1 1 0 00-1 1v10a1 1 0 001 1h1.05a2.5 2.5 0 014.9 0H10a1 1 0 001-1V5a1 1 0 00-1-1H3zM14 7a1 1 0 00-1 1v6.05A2.5 2.5 0 0015.95 16H17a1 1 0 001-1v-5a1 1 0 00-.293-.707l-2-2A1 1 0 0015 7h-1z"/>
              </svg>
              <span>Envío gratis a partir de 50€</span>
            </div>
            <div class="flex items-start gap-3">
              <svg class="w-5 h-5 text-luxury-gold mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M5 2a1 1 0 011 1v1h1a1 1 0 010 2H6v1a1 1 0 0-2 0V6H3a1 1 0 010-2h1V3a1 1 0 011-1zm0 10a1 1 0 011 1v1h1a1 1 0 110 2H6v1a1 1 0 11-2 0v-1H3a1 1 0 110-2h1v-1a1 1 0 011-1zM14 2a1 1 0 011 1v1h1a1 1 0 110 2h-1v1a1 1 0 11-2 0V6h-1a1 1 0 110-2h1V3a1 1 0 011-1z" clip-rule="evenodd"/>
              </svg>
              <span>Devoluciones sin costo en 30 días</span>
            </div>
            <div class="flex items-start gap-3">
              <svg class="w-5 h-5 text-luxury-gold mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M5 2a1 1 0 011 1v1h1a1 1 0 010 2H6v1a1 1 0 0-2 0V6H3a1 1 0 010-2h1V3a1 1 0 011-1zm0 10a1 1 0 011 1v1h1a1 1 0 110 2H6v1a1 1 0 11-2 0v-1H3a1 1 0 110-2h1v-1a1 1 0 011-1zM14 2a1 1 0 011 1v1h1a1 1 0 110 2h-1v1a1 1 0 11-2 0V6h-1a1 1 0 110-2h1V3a1 1 0 011-1z" clip-rule="evenodd"/>
              </svg>
              <span>Pago seguro 100% garantizado</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import { useCartStore } from '../stores/cart'

const route = useRoute()
const cartStore = useCartStore()

const producto = ref(null)
const loading = ref(true)
const error = ref(false)
const imagenError = ref(false)
const cantidad = ref(1)
const agregando = ref(false)
const productoAgregado = ref(false)

onMounted(async () => {
  try {
    const res = await axios.get(`http://localhost:8080/api/productos/${route.params.id}`)
    producto.value = res.data
    loading.value = false
  } catch (e) {
    console.error('Error cargando producto:', e)
    error.value = true
    loading.value = false
  }
})

const agregarAlCarrito = async () => {
  agregando.value = true
  // Simular pequeño delay de UI
  setTimeout(() => {
    cartStore.addItem(producto.value, cantidad.value)
    agregando.value = false
    productoAgregado.value = true
    // Reset mensaje después de 3 segundos
    setTimeout(() => {
      productoAgregado.value = false
    }, 3000)
  }, 300)
}
</script>
