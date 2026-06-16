<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
    <!-- Breadcrumbs -->
    <nav aria-label="Breadcrumb" class="mb-12">
      <ol class="flex items-center gap-2 text-sm">
        <li>
          <router-link to="/" class="text-luxury-gold hover:text-luxury-black transition">Inicio</router-link>
        </li>
        <li className="text-gray-400">→</li>
        <li>
          <router-link to="/tienda" class="text-luxury-gold hover:text-luxury-black transition">Tienda</router-link>
        </li>
        <li className="text-gray-400">→</li>
        <li v-if="producto" class="text-luxury-black font-semibold">{{ producto.nombre }}</li>
      </ol>
    </nav>

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
          :src="getImageUrl(producto.imagen)" 
          :alt="producto.nombre"
          class="w-full h-full object-cover hover:scale-105 transition duration-500"
          loading="lazy"
          decoding="async"
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

        <p v-if="authStore.canSeePrices && producto.precio !== null && producto.precio !== undefined" class="text-3xl text-luxury-gold font-bold mb-8">
          {{ producto.precio }}€
        </p>
        <div v-else class="mb-8 rounded-lg border border-luxury-gold/30 bg-luxury-gray/60 px-4 py-3">
          <p class="text-sm text-gray-600">Precio visible solo para empresas registradas.</p>
          <router-link to="/acceso-empresarial" class="text-xs uppercase tracking-widest font-semibold text-luxury-black hover:text-luxury-gold transition">
            Solicitar acceso comercial
          </router-link>
        </div>

        <div v-if="producto.descripcion" class="text-gray-600 leading-relaxed mb-8 text-sm border-b pb-8">
          {{ producto.descripcion }}
        </div>

        <div v-if="authStore.canSeePrices" class="mb-8 grid grid-cols-2 gap-4">
          <div v-if="producto.stock" class="text-sm text-luxury-black font-semibold">
            ✓ En stock ({{ producto.stock }})
          </div>
          <div v-else class="text-sm text-red-600 font-semibold">
            ✗ Agotado
          </div>
        </div>

        <!-- Selector de cantidad -->
        <div v-if="authStore.canRequestQuote" class="flex items-center gap-4 mb-8">
          <label class="text-sm font-semibold text-gray-700">Cantidad:</label>
          <div class="flex items-center border border-gray-300 rounded">
            <button @click="cantidad = Math.max(1, cantidad - 1)" class="px-3 py-2 hover:bg-gray-100">-</button>
            <input v-model.number="cantidad" type="number" min="1" class="w-12 text-center border-none outline-none">
            <button @click="cantidad++" class="px-3 py-2 hover:bg-gray-100">+</button>
          </div>
        </div>

        <div v-if="authStore.canRequestQuote && producto.precio !== null && producto.precio !== undefined" class="flex gap-4">
          <button 
            @click="agregarCarrito"
            :disabled="!producto.stock"
            class="flex-1 bg-luxury-gold text-white px-8 py-4 hover:bg-opacity-80 transition uppercase text-sm font-bold tracking-widest disabled:opacity-50 disabled:cursor-not-allowed shadow-lg hover:shadow-xl"
            :aria-label="isInCart ? 'Producto ya en carrito' : 'Agregar al carrito'">
            <span v-if="!agregando">{{ isInCart ? '✓ En carrito' : 'Agregar al Carrito' }}</span>
            <span v-else class="inline-flex items-center gap-2">
              <span class="inline-block animate-spin">⏳</span> Agregando...
            </span>
          </button>
          <button class="flex-1 border border-luxury-gold text-luxury-gold px-8 py-4 hover:bg-luxury-gold hover:text-white transition uppercase text-sm font-bold tracking-widest">
            ❤️ Guardar
          </button>
        </div>

        <div v-else class="rounded-lg border border-gray-200 bg-white px-4 py-4 text-sm text-gray-600">
          Catálogo informativo para público general. Si eres empresa, solicita alta para ver tarifas y realizar pedidos.
        </div>

        <!-- Notificación flotante mejorada -->
        <transition
          enter-active-class="transition duration-300 ease-out"
          enter-from-class="opacity-0 translate-y-2"
          enter-to-class="opacity-100 translate-y-0"
          leave-active-class="transition duration-300 ease-in"
          leave-from-class="opacity-100 translate-y-0"
          leave-to-class="opacity-0 translate-y-2">
          <div v-if="agregado && authStore.canRequestQuote" class="mt-6 p-4 bg-gradient-to-r from-luxury-gold/20 to-luxury-gold/10 border-2 border-luxury-gold text-luxury-black text-sm rounded-lg shadow-lg flex items-center gap-3">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" /></svg>
            <div>
              <p class="font-bold">¡Producto agregado!</p>
              <p class="text-xs text-gray-600">Cantidad: {{ cantidadAgregada }} • Ya en carrito: {{ cartStore.items.find(i => i.id === producto.id)?.cantidad || 0 }}</p>
            </div>
          </div>
        </transition>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, inject } from 'vue'
import { useRoute } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import { apiClient, getImageUrl } from '../lib/api'

const route = useRoute()
const cartStore = useCartStore()
const authStore = useAuthStore()
const toast = inject('toast')
const producto = ref(null)
const loading = ref(true)
const error = ref(false)
const cantidad = ref(1)
const agregado = ref(false)
const agregando = ref(false)
const cantidadAgregada = ref(1)

const isInCart = computed(() => {
  return !!cartStore.items.find(i => i.id === producto.value?.id)
})

onMounted(async () => {
  try {
    const id = route.params.id
    const res = await apiClient.get(`/api/productos/${id}`)
    producto.value = res.data
  } catch (e) {
    console.error('Error cargando producto:', e)
    error.value = true
  } finally {
    loading.value = false
  }
})

const agregarCarrito = async () => {
  if (!authStore.canRequestQuote) return
  if (agregando.value) return
  agregando.value = true
  cantidadAgregada.value = cantidad.value
  
  cartStore.addItem(producto.value, cantidad.value)
  agregado.value = true
  
  if (toast) {
    const msg = `✓ ${cantidad.value}x "${producto.value.nombre}" añadido(s) al carrito`
    toast.show(msg, 'success', 2500)
  }
  
  setTimeout(() => {
    agregando.value = false
  }, 500)
  
  setTimeout(() => agregado.value = false, 4000)
}
</script>
