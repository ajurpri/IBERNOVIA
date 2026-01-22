<template>
  <div class="group h-full flex flex-col">
    <!-- Imagen y Botones -->
    <router-link :to="`/producto/${producto.id}`" class="relative overflow-hidden bg-gradient-to-br from-gray-50 to-gray-100 aspect-[3/4] mb-3 shadow-sm group-hover:shadow-xl group-hover:-translate-y-1 transition-all duration-500 ease-out cursor-pointer flex-1 rounded-sm">
      <!-- Imagen del producto -->
      <img :src="producto.imagen" 
           :alt="producto.nombre"
           @error="imageError = true"
           :class="[
             'object-cover w-full h-full transition duration-1000 ease-in-out',
             !imageError ? 'group-hover:scale-105 opacity-95 group-hover:opacity-100' : 'hidden'
           ]">
      
      <!-- Fallback si imagen no carga -->
      <div v-if="imageError" class="w-full h-full bg-gradient-to-br from-luxury-gold/20 to-luxury-black/20 flex items-center justify-center">
        <div class="text-center">
          <svg class="w-16 h-16 mx-auto text-gray-300 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
          </svg>
          <p class="text-xs text-gray-400">Cargando imagen...</p>
        </div>
      </div>
      
      <!-- Botón Ver Detalle (sobre la imagen) -->
      <div class="absolute bottom-0 left-0 right-0 p-3 translate-y-full group-hover:translate-y-0 transition duration-300 bg-gradient-to-t from-black/90 via-black/60 to-transparent">
        <span class="block w-full bg-luxury-gold text-luxury-black text-xs md:text-sm uppercase tracking-[0.1em] py-2.5 md:py-3 text-center font-bold rounded-sm shadow-lg cursor-pointer hover:bg-white transition">
          Ver Detalle
        </span>
      </div>

      <!-- Badge de Oferta (Nuevo) -->
      <div v-if="producto.precio < 30" class="absolute top-3 left-3 bg-red-600 text-white text-[10px] font-bold px-2 py-1 uppercase tracking-wider shadow-sm animate-pulse">
        Oferta
      </div>

      <!-- Botón Favoritos (Corazón) -->
      <div 
        @click.prevent="toggleFavorite"
        class="absolute top-3 right-3 bg-white shadow-md p-2 rounded-full opacity-90 hover:opacity-100 transition cursor-pointer z-10 hover:scale-110">
        <svg :class="['h-5 w-5 transition', isFavorite ? 'text-red-500 fill-current' : 'text-gray-400']" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
        </svg>
      </div>
    </router-link>

    <!-- Info del producto -->
    <div class="mt-3 text-center space-y-1.5 flex-1 flex flex-col justify-between">
      <div>
        <p class="text-xs text-gray-400 uppercase tracking-[0.15em] font-medium">{{ producto.categoria || 'Sin categoría' }}</p>
        <h3 class="font-serif text-sm md:text-base text-gray-900 group-hover:text-luxury-gold transition line-clamp-2 h-9 flex items-center justify-center">
          {{ producto.nombre }}
        </h3>
      </div>
      <div>
        <p class="text-lg md:text-xl font-bold text-luxury-gold mb-2">{{ producto.precio || '0' }}€</p>
        <!-- Botón Agregar al Carrito -->
        <button 
          @click.stop="agregarAlCarrito"
          :disabled="agregando"
          class="w-full bg-luxury-black text-white text-xs py-2 font-bold uppercase tracking-widest hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 rounded-sm">
          {{ agregando ? 'Agregando...' : '+ Carrito' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useCartStore } from '../stores/cart'

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

const agregarAlCarrito = async () => {
  agregando.value = true
  setTimeout(() => {
    cartStore.addItem(props.producto, 1)
    agregando.value = false
  }, 300)
}

const toggleFavorite = () => {
  isFavorite.value = !isFavorite.value
  // Aquí podrías conectar con un store de Pinia para guardar favoritos
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
