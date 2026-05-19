<template>
  <div class="group h-full flex flex-col bg-white rounded-2xl overflow-hidden shadow-md hover:shadow-xl transition-all">
    <router-link :to="`/producto/${producto.id}`" class="block overflow-hidden">
      <div class="relative bg-[#faf9f7]">
        <img
          :src="producto.imagen"
          :alt="producto.nombre"
          @error="imageError = true"
          loading="lazy"
          decoding="async"
          width="600"
          height="800"
          class="w-full aspect-[3/4] object-cover transition-transform duration-500 group-hover:scale-105"
        />

        <button
          @click.stop.prevent="toggleFavorite"
          :aria-pressed="isFavorite ? 'true' : 'false'"
          class="absolute top-3 right-3 w-9 h-9 bg-white/90 rounded-full flex items-center justify-center shadow-sm hover:scale-105 transition-transform"
          aria-label="Marcar favorito"
        >
          <svg :class="['w-4 h-4', isFavorite ? 'text-red-500' : 'text-gray-600']" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
          </svg>
        </button>
      </div>
    </router-link>

    <div class="p-4 flex-1 flex flex-col justify-between">
      <div>
        <p class="text-[11px] text-gray-500 uppercase tracking-[0.22em] mb-2">{{ producto.categoria || 'Sin categoria' }}</p>
        <h3 class="font-serif text-lg text-gray-900 line-clamp-2 mb-2">{{ producto.nombre }}</h3>
      </div>

      <div class="mt-3">
        <div v-if="authStore.canSeePrices" class="text-lg font-semibold text-luxury-black">€{{ producto.precio || '0' }}</div>
        <div v-else class="text-sm text-gray-500 italic">Contacta para precios · <router-link to="/acceso-empresarial" class="text-luxury-gold font-semibold">Acceso empresarial</router-link></div>
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
