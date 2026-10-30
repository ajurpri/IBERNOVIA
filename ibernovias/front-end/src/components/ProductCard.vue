<template>
  <div class="group h-full flex flex-col bg-transparent overflow-hidden transition-all duration-300">

    <div class="relative">
    <!-- Image Wrapper -->
    <router-link
      :to="`/producto/${producto.id}`"
      class="product-visual block overflow-hidden rounded-xl border border-black/5 bg-[#faf9f7] shadow-sm relative group-hover:shadow-md transition-shadow duration-500"
      :aria-busy="!imageLoaded && !imageError"
    >
      <div
        v-if="!imageLoaded && !imageError"
        class="image-placeholder absolute inset-0 aspect-[3/4]"
        aria-hidden="true"
      ></div>
      <img
        v-if="!imageError"
        :src="getImageUrl(producto.imagen)"
        :alt="productName(producto)"
        @error="imageError = true"
        @load="imageLoaded = true"
        :loading="priority ? 'eager' : 'lazy'"
        :fetchpriority="priority ? 'high' : 'low'"
        decoding="async"
        width="600"
        height="800"
        sizes="(max-width: 640px) calc(100vw - 2rem), (max-width: 1024px) 50vw, 25vw"
        :class="[
          'w-full aspect-[3/4] object-contain p-4 transition-opacity duration-200',
          imageLoaded ? 'opacity-100' : 'opacity-0'
        ]"
      />
      <div v-else class="w-full aspect-[3/4] flex flex-col items-center justify-center bg-gray-50 text-gray-400">
        <svg class="w-12 h-12 text-gray-300 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
        </svg>
        <span class="text-xs text-gray-400">Imagen no disponible</span>
      </div>

    </router-link>
      <!-- Favorite Button -->
      <button
        @click.stop.prevent="toggleFavorite"
        :aria-pressed="isFavorite ? 'true' : 'false'"
        class="absolute top-4 right-4 w-11 h-11 bg-white/95 rounded-full flex items-center justify-center shadow-md hover:scale-105 hover:bg-white transition-all duration-300 focus:outline-none"
        :aria-label="`${isFavorite ? 'Quitar de favoritos' : 'Marcar favorito'}: ${productReference(producto)}`"
      >
        <svg :class="['w-4 h-4 transition-colors duration-300', isFavorite ? 'text-red-500 fill-red-500' : 'text-gray-600']" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
        </svg>
      </button>

      <!-- Badge de Oferta -->
      <div
        v-if="producto.enOferta && producto.tagOferta"
        class="absolute top-4 left-4 bg-red-600 text-white text-[9px] sm:text-[10px] font-bold uppercase tracking-widest px-2.5 py-1 rounded shadow-md z-10"
      >
        {{ producto.tagOferta }}
      </div>
    </div>

    <!-- Details below image -->
    <div class="pt-4 pb-2 flex-1 flex flex-col justify-between">
      <div>
        <p class="text-xs text-gray-600 uppercase tracking-[0.25em] mb-1.5">{{ producto.categoria || 'Sin categoría' }}</p>
        <h3 class="font-serif text-base text-luxury-black font-light group-hover:text-luxury-gold transition-colors line-clamp-2 leading-relaxed">
          {{ productName(producto) }}
        </h3>
        <p class="mt-1 text-sm text-gray-600">Referencia: {{ productReference(producto) }}</p>
      </div>

      <div class="mt-3">
        <!-- Authorized Price Display -->
        <div v-if="authStore.canSeePrices" class="text-base font-serif font-medium flex items-center flex-wrap gap-x-2 gap-y-0.5">
          <span v-if="producto.enOferta" class="line-through text-gray-400 text-xs sm:text-sm font-sans font-normal">
            {{ producto.precio || '0' }} €
          </span>
          <span :class="producto.enOferta ? 'text-red-600 font-bold' : 'text-luxury-black'">
            {{ producto.enOferta ? producto.precioOferta : producto.precio }} €
          </span>
        </div>
        <!-- Restricted Professional Access Prompt -->
        <div v-else class="text-xs text-gray-600 tracking-wide font-sans flex items-center gap-1.5">
          <span>Precios para profesionales</span>
          <span class="w-1.5 h-1.5 rounded-full bg-luxury-gold"></span>
          <router-link
            to="/auth"
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
import { ref, inject, watch, computed } from 'vue'
import { favoriteIds, toggleFavoriteId } from '../stores/favorites'
import { useAuthStore } from '../stores/auth'
import { getImageUrl } from '../lib/api'
import { productName, productReference } from '../lib/productPresentation'

const props = defineProps({
  producto: {
    type: Object,
    required: true
  },
  priority: {
    type: Boolean,
    default: false
  }
})

const imageError = ref(false)
const imageLoaded = ref(false)
const isFavorite = computed(() => favoriteIds.value.includes(String(props.producto.id)))
const authStore = useAuthStore()
const toast = inject('toast')

watch(() => props.producto.imagen, () => {
  imageError.value = false
  imageLoaded.value = false
})

const toggleFavorite = () => {
  toggleFavoriteId(props.producto.id)
  if (toast) {
    const msg = isFavorite.value
      ? `"${props.producto.nombre}" añadido a favoritos`
      : `"${props.producto.nombre}" eliminado de favoritos`
    toast.show(msg, isFavorite.value ? 'success' : 'info', 2000)
  }
}
</script>

<style scoped>
.product-visual {
  contain: layout paint;
}

.image-placeholder {
  background:
    linear-gradient(105deg, transparent 38%, rgb(255 255 255 / 0.72) 48%, transparent 58%),
    linear-gradient(135deg, #f2eee7, #faf9f7);
  background-size: 220% 100%, 100% 100%;
  animation: none;
}

@keyframes image-shimmer {
  to { background-position: -120% 0, 0 0; }
}

@media (max-width: 640px) {
  a {
    border-radius: 0.75rem;
  }

  button[aria-pressed] {
    top: 0.65rem;
    right: 0.65rem;
    width: 2.75rem;
    height: 2.75rem;
  }

  .tracking-\[0\.25em\],
  .tracking-widest {
    letter-spacing: 0.12em;
  }
}

@media (prefers-reduced-motion: reduce) {
  .image-placeholder {
    animation: none;
  }
}
</style>
