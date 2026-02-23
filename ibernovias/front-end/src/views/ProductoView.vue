<template>
  <div class="min-h-screen bg-white py-6 sm:py-8 md:py-12">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Breadcrumb -->
      <div class="mb-6 sm:mb-8 text-xs sm:text-sm text-gray-500">
        <router-link to="/" class="hover:text-luxury-gold transition">Inicio</router-link> /
        <router-link to="/tienda" class="ml-2 hover:text-luxury-gold transition">Catálogo</router-link> /
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
      <div v-else-if="producto" class="grid grid-cols-1 md:grid-cols-2 gap-6 sm:gap-8 lg:gap-12">
        <!-- Galería de Imágenes -->
        <div class="flex flex-col gap-4">
          <!-- Imagen Principal -->
          <div class="bg-gray-50 aspect-[3/4] overflow-hidden">
              <img
                :src="producto.imagen"
                :alt="producto.nombre"
                @error="imagenError = true"
                loading="lazy"
                decoding="async"
                width="900"
                height="1200"
                :class="mainImageClass"
              >
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
          <div class="flex flex-wrap gap-2">
            <span class="inline-block bg-luxury-gold text-luxury-black px-3 sm:px-4 py-2 text-[11px] sm:text-xs font-bold uppercase tracking-widest">
              {{ producto.categoria }}
            </span>
            <span v-if="authStore.isBusinessUser && producto.stock > 0" class="inline-block bg-luxury-gold/10 text-luxury-black px-3 sm:px-4 py-2 text-[11px] sm:text-xs font-bold uppercase tracking-widest border border-luxury-gold/30">
              En Stock ({{ producto.stock }})
            </span>
            <span v-else-if="authStore.isBusinessUser" class="inline-block bg-red-100 text-red-700 px-3 sm:px-4 py-2 text-[11px] sm:text-xs font-bold uppercase tracking-widest">
              Agotado
            </span>
          </div>
        </div>

        <!-- Información del Producto -->
        <div class="flex flex-col">
          <!-- Nombre y precio -->
          <div class="mb-5 sm:mb-6 pb-5 sm:pb-6 border-b border-gray-200">
            <h1 class="font-serif text-2xl sm:text-3xl md:text-4xl text-luxury-black mb-3">
              {{ producto.nombre }}
            </h1>
            <p class="text-gray-500 text-sm mb-5 sm:mb-6 leading-relaxed">
              {{ producto.descripcion }}
            </p>
            <div v-if="authStore.isBusinessUser" class="flex items-baseline gap-3">
              <span class="text-3xl sm:text-4xl font-bold text-luxury-gold">{{ producto.precio }}€</span>
              <span class="text-base sm:text-lg text-gray-400 line-through">{{ (producto.precio * 1.1).toFixed(2) }}€</span>
            </div>
            <div v-else class="rounded-lg border border-luxury-gold/30 bg-luxury-gray/60 px-4 py-4">
              <p class="text-sm text-gray-600">Catálogo público. Tarifas y pedidos disponibles solo para clientes profesionales.</p>
              <router-link to="/acceso-empresarial" class="inline-block mt-2 text-xs uppercase tracking-widest font-semibold text-luxury-black hover:text-luxury-gold transition">
                Solicitar acceso empresarial
              </router-link>
            </div>
          </div>

          <!-- SKU y Ref -->
          <div class="mb-6 pb-6 border-b border-gray-200 space-y-2 text-sm">
            <div>
              <span class="text-gray-500">Referencia:</span>
              <span class="font-mono text-gray-900 ml-2">#{{ producto.id }}</span>
            </div>
            <div v-if="authStore.isBusinessUser">
              <span class="text-gray-500">Stock:</span>
              <span class="text-gray-900 ml-2">{{ producto.stock }} unidades</span>
            </div>
          </div>

          <!-- Selector de Cantidad y Agregar al Carrito -->
          <div v-if="authStore.isBusinessUser" class="mb-6 sm:mb-8">
            <label class="block text-xs sm:text-sm font-bold uppercase tracking-widest text-gray-700 mb-3">Cantidad</label>
            <div class="flex gap-4 items-end">
              <div class="flex items-center border border-gray-300 rounded">
                <button @click="cantidad > 1 && cantidad--" class="px-3 sm:px-4 py-2 text-gray-600 hover:bg-gray-100 text-lg">−</button>
                <input 
                  v-model.number="cantidad" 
                  type="number" 
                  min="1" 
                  :max="producto.stock"
                  class="w-14 sm:w-16 text-center border-0 focus:outline-none text-base sm:text-lg font-semibold"
                >
                <button @click="cantidad < producto.stock && cantidad++" class="px-3 sm:px-4 py-2 text-gray-600 hover:bg-gray-100 text-lg">+</button>
              </div>
            </div>
          </div>

          <!-- Botón Agregar al Carrito -->
          <button
            v-if="authStore.isBusinessUser"
            @click="agregarAlCarrito"
            :disabled="agregando"
            class="w-full bg-luxury-black text-white py-3.5 sm:py-4 font-bold uppercase tracking-widest text-sm sm:text-lg hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 mb-3">
            {{ agregando ? 'Agregando...' : (producto.stock <= 0 ? 'Reservar Producto' : 'Agregar al Carrito') }}
          </button>

          <router-link
            v-else
            to="/acceso-empresarial"
            class="block w-full text-center bg-luxury-black text-white py-3.5 sm:py-4 font-bold uppercase tracking-widest text-sm sm:text-lg hover:bg-luxury-gold hover:text-luxury-black transition mb-3"
          >
            Solicitar condiciones comerciales
          </router-link>

          <!-- Link a Carrito -->
          <div v-if="productoAgregado && authStore.isBusinessUser" class="text-center">
            <router-link to="/carrito" class="inline-block text-luxury-gold hover:text-luxury-black font-bold uppercase tracking-widest text-sm transition border-b-2 border-luxury-gold hover:border-luxury-black">
              Ver Carrito →
            </router-link>
          </div>

          <!-- Info comercial -->
          <div class="mt-8 pt-8 border-t border-gray-200 space-y-3 text-sm text-gray-600">
            <div class="flex items-start gap-3">
              <svg class="w-5 h-5 text-luxury-gold mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path d="M8 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM15 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z"/>
                <path d="M3 4a1 1 0 00-1 1v10a1 1 0 001 1h1.05a2.5 2.5 0 014.9 0H10a1 1 0 001-1V5a1 1 0 00-1-1H3zM14 7a1 1 0 00-1 1v6.05A2.5 2.5 0 0015.95 16H17a1 1 0 001-1v-5a1 1 0 00-.293-.707l-2-2A1 1 0 0015 7h-1z"/>
              </svg>
              <span>Venta al por mayor para clientes profesionales</span>
            </div>
            <div class="flex items-start gap-3">
              <svg class="w-5 h-5 text-luxury-gold mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M5 2a1 1 0 011 1v1h1a1 1 0 010 2H6v1a1 1 0 0-2 0V6H3a1 1 0 010-2h1V3a1 1 0 011-1zm0 10a1 1 0 011 1v1h1a1 1 0 110 2H6v1a1 1 0 11-2 0v-1H3a1 1 0 110-2h1v-1a1 1 0 011-1zM14 2a1 1 0 011 1v1h1a1 1 0 110 2h-1v1a1 1 0 11-2 0V6h-1a1 1 0 110-2h1V3a1 1 0 011-1z" clip-rule="evenodd"/>
              </svg>
              <span>Asesoramiento y atención comercial personalizada</span>
            </div>
            <div class="flex items-start gap-3">
              <svg class="w-5 h-5 text-luxury-gold mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M5 2a1 1 0 011 1v1h1a1 1 0 010 2H6v1a1 1 0 0-2 0V6H3a1 1 0 010-2h1V3a1 1 0 011-1zm0 10a1 1 0 011 1v1h1a1 1 0 110 2H6v1a1 1 0 11-2 0v-1H3a1 1 0 110-2h1v-1a1 1 0 011-1zM14 2a1 1 0 011 1v1h1a1 1 0 110 2h-1v1a1 1 0 11-2 0V6h-1a1 1 0 110-2h1V3a1 1 0 011-1z" clip-rule="evenodd"/>
              </svg>
              <span>Condiciones y tarifas visibles tras validación de empresa</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Productos Relacionados -->
      <div v-if="relatedProducts.length > 0" class="mt-14 sm:mt-20 border-t border-gray-100 pt-12 sm:pt-16">
        <div class="text-center mb-8 sm:mb-12">
          <h2 class="font-serif text-xl sm:text-2xl md:text-3xl text-luxury-black mb-2">También te podría gustar</h2>
          <div class="h-0.5 w-12 bg-luxury-gold mx-auto"></div>
        </div>
        
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4 sm:gap-6">
          <div v-for="rel in relatedProducts" :key="rel.id" class="group cursor-pointer">
            <router-link :to="`/producto/${rel.id}`" class="block">
              <div class="aspect-[3/4] bg-gray-100 mb-3 overflow-hidden">
                <img
                  :src="rel.imagen"
                  :alt="rel.nombre"
                  class="w-full h-full object-cover group-hover:scale-105 transition duration-500"
                  loading="lazy"
                  decoding="async"
                  width="400"
                  height="533"
                >
              </div>
              <h3 class="text-sm font-medium text-gray-900 truncate">{{ rel.nombre }}</h3>
              <p v-if="authStore.isBusinessUser" class="text-sm text-luxury-gold font-bold">{{ rel.precio }}€</p>
              <p v-else class="text-xs text-gray-500">Solo empresas</p>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute } from 'vue-router'
import { apiClient } from '../lib/api'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'

const route = useRoute()
const cartStore = useCartStore()
const authStore = useAuthStore()

const producto = ref(null)
const relatedProducts = ref([])
const loading = ref(true)
const error = ref(false)
const imagenError = ref(false)
const cantidad = ref(1)
const agregando = ref(false)
const productoAgregado = ref(false)

const mainImageClass = computed(() => [
  'w-full h-full object-cover transition duration-500',
  !imagenError.value ? '' : 'hidden'
])

const loadProductData = async (id) => {
  loading.value = true
  error.value = false
  try {
    // 1. Cargar producto principal
    const res = await apiClient.get(`/api/productos/${id}`)
    producto.value = res.data
    
    // 2. Cargar relacionados (misma categoría)
    if (producto.value.categoria) {
      const resRel = await apiClient.get(`/api/productos/categoria/${producto.value.categoria}`)
      // Filtramos para no mostrar el mismo producto y tomamos solo 4
      relatedProducts.value = resRel.data
        .filter(p => p.id !== producto.value.id)
        .slice(0, 4)
    }
  } catch (e) {
    console.error('Error:', e)
    error.value = true
  } finally {
    loading.value = false
  }
}

onMounted(() => loadProductData(route.params.id))

// Recargar si cambia la URL (al hacer clic en un relacionado)
watch(() => route.params.id, (newId) => {
  if (newId) loadProductData(newId)
})

const agregarAlCarrito = async () => {
  if (!authStore.isBusinessUser) return
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
