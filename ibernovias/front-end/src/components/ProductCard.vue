<template>
  <div class="group h-full flex flex-col bg-transparent overflow-hidden transition-all duration-300">
    
    <!-- Image Wrapper -->
    <router-link 
      :to="`/producto/${producto.id}`" 
      class="block overflow-hidden rounded-xl border border-black/5 bg-[#faf9f7] shadow-sm relative group-hover:shadow-md transition-shadow duration-500"
    >
      <img
        v-if="!imageError"
        :src="getImageUrl(producto.imagen)"
        :alt="producto.nombre"
        @error="imageError = true"
        loading="lazy"
        decoding="async"
        width="600"
        height="800"
        class="w-full aspect-[3/4] object-cover transition-transform duration-700 ease-out group-hover:scale-105"
      />
      <div v-else class="w-full aspect-[3/4] flex flex-col items-center justify-center bg-gray-50 text-gray-400">
        <svg class="w-12 h-12 text-gray-300 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
        </svg>
        <span class="text-xs text-gray-400">Imagen no disponible</span>
      </div>

      <!-- Favorite Button -->
      <button
        @click.stop.prevent="toggleFavorite"
        :aria-pressed="isFavorite ? 'true' : 'false'"
        class="absolute top-4 right-4 w-9 h-9 bg-white/95 rounded-full flex items-center justify-center shadow-md hover:scale-105 hover:bg-white transition-all duration-300 focus:outline-none"
        aria-label="Marcar favorito"
      >
        <svg :class="['w-4 h-4 transition-colors duration-300', isFavorite ? 'text-red-500 fill-red-500' : 'text-gray-600']" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
        </svg>
      </button>
    </router-link>

    <!-- Details below image -->
    <div class="pt-4 pb-2 flex-1 flex flex-col justify-between">
      <div>
        <p class="text-[10px] text-gray-400 uppercase tracking-[0.25em] mb-1.5">{{ producto.categoria || 'Sin categoría' }}</p>
        <h3 class="font-serif text-base text-luxury-black font-light group-hover:text-luxury-gold transition-colors line-clamp-2 leading-relaxed">
          {{ producto.nombre }}
        </h3>
      </div>

      <div class="mt-3">
        <!-- Authorized Price Display -->
        <div v-if="authStore.canSeePrices" class="text-base font-serif text-luxury-black font-medium">
          {{ producto.precio || '0' }} €
        </div>
        <!-- Restricted Professional Access Prompt -->
        <div v-else class="text-[10px] text-gray-400 tracking-wide font-sans flex items-center gap-1.5">
          <span>Precios para profesionales</span>
          <span class="w-1.5 h-1.5 rounded-full bg-luxury-gold"></span>
          <router-link 
            to="/acceso-empresarial" 
            class="text-luxury-gold hover:text-luxury-black font-semibold underline transition-colors"
          >
            Acceso
          </router-link>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script setup>
import { ref, inject } from 'vue'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import { getImageUrl } from '../lib/api'

const props = defineProps({
  producto: {
    type: Object,
    required: true
  }
})

const imageError = ref(false)
const isFavorite = ref(false)
const cartStore = useCartStore()
const authStore = useAuthStore()
const toast = inject('toast')

const toggleFavorite = () => {
  isFavorite.value = !isFavorite.value
  if (toast) {
    const msg = isFavorite.value 
      ? `♥ "${props.producto.nombre}" añadido a favoritos` 
      : `"${props.producto.nombre}" removido de favoritos`
    toast.show(msg, isFavorite.value ? 'success' : 'info', 2000)
  }
}
</script>

<style scoped>
@media (max-width: 640px) {
  :deep(.group) {
    aspect-ratio: 3 / 4;
  }
}
</style>
