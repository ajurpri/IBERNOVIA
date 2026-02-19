<template>
  <div class="group h-full flex flex-col bg-white/90 rounded-3xl overflow-hidden border border-white/80 shadow-sm transition-all duration-500 hover:shadow-2xl hover:-translate-y-1">
    <!-- Imagen y Botones -->
    <router-link
      :to="`/producto/${producto.id}`"
      class="relative overflow-hidden bg-[#f6f2ed] aspect-[3/4] cursor-pointer focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2">
      <!-- Imagen del producto -->
       <img
         :src="producto.imagen"
         :alt="producto.nombre"
         @error="imageError = true"
         loading="lazy"
         decoding="async"
         width="400"
         height="533"
         :class="imageClass"
       >
      
      <!-- Fallback si imagen no carga -->
      <div v-if="imageError" class="w-full h-full bg-gradient-to-br from-luxury-gold/20 to-luxury-black/20 flex items-center justify-center">
        <div class="text-center">
          <svg class="w-16 h-16 mx-auto text-gray-300 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
          </svg>
          <p class="text-xs text-gray-400">Cargando imagen...</p>
        </div>
      </div>
      
      <!-- Overlay gradient on hover -->
      <div class="absolute inset-0 bg-gradient-to-t from-luxury-black/45 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
      
      <!-- Botón Ver Detalle (sobre la imagen) -->
      <div class="absolute bottom-0 left-0 right-0 p-4 translate-y-full group-hover:translate-y-0 transition-transform duration-500 ease-out">
        <span class="block w-full bg-white/95 text-luxury-black text-xs uppercase tracking-wider py-3 text-center font-semibold shadow-lg cursor-pointer hover:bg-luxury-gold transition-colors duration-300">
          Ver Detalle
        </span>
      </div>

      <!-- Badge de Stock/Oferta -->
      <div class="absolute top-3 left-3 flex flex-col gap-2">
        <div v-if="producto.precio < 50" class="bg-red-500 text-white text-xs font-bold px-3 py-1 uppercase tracking-wider shadow-lg">
          Oferta
        </div>
        <div v-if="producto.stock && producto.stock < 10" class="bg-orange-500 text-white text-xs font-bold px-3 py-1 uppercase tracking-wider shadow-lg">
          ¡Últimas {{ producto.stock }}!
        </div>
      </div>

      <!-- Botón Favoritos (Corazón) -->
      <button
        type="button"
        @click.prevent="toggleFavorite"
        :aria-pressed="isFavorite ? 'true' : 'false'"
        aria-label="Marcar como favorito"
        class="absolute top-3 right-3 bg-white/90 backdrop-blur-sm shadow-lg p-2.5 rounded-full hover:bg-luxury-gold transition-all duration-300 cursor-pointer z-10 hover:scale-110 transform focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2">
        <svg :class="['h-5 w-5 transition-colors duration-300', isFavorite ? 'text-red-500 fill-current' : 'text-gray-600']" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
        </svg>
      </button>
    </router-link>

    <!-- Info del producto -->
    <div class="p-5 flex-1 flex flex-col justify-between">
      <div class="mb-3">
        <p class="text-[11px] text-gray-500 uppercase tracking-[0.25em] font-semibold mb-2">{{ producto.categoria || 'Sin categoria' }}</p>
        <h3 class="font-serif text-base md:text-lg text-gray-900 group-hover:text-luxury-black transition-colors duration-300 line-clamp-2 min-h-[3rem]">
          {{ producto.nombre }}
        </h3>
      </div>
      
      <div>
        <!-- Rating (simulado) -->
        <div class="flex items-center justify-center gap-1 mb-2 opacity-80">
          <svg v-for="i in 5" :key="i" class="w-4 h-4 text-luxury-gold fill-current" viewBox="0 0 20 20">
            <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
          </svg>
          <span class="text-xs text-gray-500 ml-1">(4.5)</span>
        </div>
        
        <!-- Precio o llamada a acción -->
        <div v-if="authStore.isBusinessUser" class="text-xl font-semibold text-luxury-black mb-3 text-center">€{{ producto.precio || '0' }}</div>
        <div v-else class="text-sm text-gray-500 mb-3 text-center italic">
          <p>Contacta para precios</p>
          <p class="text-xs text-luxury-gold font-semibold">Acceso empresarial</p>
        </div>
        
        <!-- Botón Agregar al Carrito -->
        <button 
          @click.stop="agregarAlCarrito"
          :disabled="agregando"
          class="w-full border border-luxury-black/20 text-luxury-black text-xs py-3 font-semibold uppercase tracking-wider hover:bg-luxury-black hover:text-white transition-all duration-300 disabled:opacity-50 shadow-sm hover:shadow-md rounded focus:outline-none focus-visible:ring-2 focus-visible:ring-luxury-gold focus-visible:ring-offset-2">
          {{ agregando ? 'Agregando...' : '+ Añadir al Carrito' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, inject } from 'vue'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'

const props = defineProps({
  producto: {
    type: Object,
    required: true
  }
});

const imageError = ref(false)
const agregando = ref(false)
const isFavorite = ref(false)
const cartStore = useCartStore()
const authStore = useAuthStore()
const toast = inject('toast')

const imageClass = computed(() => [
  'object-cover w-full h-full transition duration-700 ease-out',
  !imageError.value ? 'group-hover:scale-110 opacity-95 group-hover:opacity-100' : 'hidden'
])

const agregarAlCarrito = async () => {
  agregando.value = true
  setTimeout(() => {
    cartStore.addItem(props.producto, 1)
    agregando.value = false
    if (toast) toast.show(`✓ "${props.producto.nombre}" añadido al carrito`, 'success', 2500)
  }, 300)
}

const toggleFavorite = () => {
  isFavorite.value = !isFavorite.value
  if (toast) {
    const msg = isFavorite.value ? `♥ "${props.producto.nombre}" añadido a favoritos` : `"${props.producto.nombre}" removido de favoritos`
    toast.show(msg, isFavorite.value ? 'success' : 'info', 2000)
  }
}
</script>

<style scoped>
/* Asegurar que el card sea responsive en móvil */
@media (max-width: 640px) {
  :deep(.group) {
    aspect-ratio: 3 / 4;
  }
}
</style>
