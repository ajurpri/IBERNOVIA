<template>
  <div class="group h-full flex flex-col bg-transparent overflow-hidden transition-all duration-300">

    <div class="relative">
    <!-- Image Wrapper -->
    <router-link
      :to="`/producto/${producto.id}`"
      class="product-visual block relative"
    >
      <img
        v-if="!imageError"
        :src="getImageUrl(producto.imagen)"
        :alt="productName(producto)"
        @error="imageError = true"
        :loading="priority ? 'eager' : 'lazy'"
        :fetchpriority="priority ? 'high' : 'low'"
        decoding="async"
        width="600"
        height="600"
        sizes="(max-width: 767px) 50vw, (max-width: 1199px) 33vw, 25vw"
        class="product-photo w-full aspect-[1/1] object-contain"
      />
      <div v-else class="w-full aspect-[1/1] flex flex-col items-center justify-center bg-gray-50 text-gray-400">
        <svg class="w-12 h-12 text-gray-300 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
        </svg>
        <span class="text-xs text-gray-400">Imagen no disponible</span>
      </div>

    </router-link>
    </div>

    <div class="product-details pt-3 pb-2 flex-1 flex flex-col">
      <h3 class="product-name text-luxury-black">
        {{ productName(producto) }}
      </h3>
      <p v-if="producto.enOferta && producto.tagOferta" class="product-offer">{{ producto.tagOferta }}</p>
      <div class="product-reference-row">
        <p v-if="productName(producto) !== productReference(producto)">Referencia: {{ productReference(producto) }}</p>
        <button
          @click.stop.prevent="toggleFavorite"
          :aria-pressed="isFavorite ? 'true' : 'false'"
          class="favorite-control"
          :aria-label="`${isFavorite ? 'Quitar de favoritos' : 'Marcar favorito'}: ${productReference(producto)}`"
        >
          <svg :class="isFavorite ? 'text-red-500 fill-red-500' : ''" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" aria-hidden="true">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
          </svg>
        </button>
      </div>
      <div v-if="authStore.canSeePrices" class="mt-2 text-base font-sans font-medium flex items-center flex-wrap gap-x-2 gap-y-0.5">
        <span v-if="producto.enOferta" class="line-through text-gray-400 text-xs sm:text-sm font-normal">{{ producto.precio || '0' }} €</span>
        <span :class="producto.enOferta ? 'text-red-600 font-bold' : 'text-luxury-black'">{{ producto.enOferta ? producto.precioOferta : producto.precio }} €</span>
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
const isFavorite = computed(() => favoriteIds.value.includes(String(props.producto.id)))
const authStore = useAuthStore()
const toast = inject('toast')

watch(() => props.producto.imagen, () => {
  imageError.value = false
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
.product-visual {aspect-ratio:1;}
.product-photo {display:block;}
.product-offer {font-size:.75rem;color:#815733;margin-top:.25rem;}
.product-name {font-family:'Manrope','Segoe UI',sans-serif;letter-spacing:0;font-size:.9rem;font-weight:500;line-height:1.55;}
.product-reference-row {display:flex;align-items:center;justify-content:space-between;gap:.25rem;}
.product-reference-row p {font-size:.8rem;color:#655e55;}
.favorite-control {display:flex;align-items:center;justify-content:center;width:44px;height:44px;flex-shrink:0;background:transparent;border:0;color:#655e55;margin-right:-.65rem;margin-left:auto;}
.favorite-control:hover {color:#302a24;}
@media(max-width:640px){.product-name{font-size:.85rem;} .product-details p{font-size:.75rem;} }
</style>
