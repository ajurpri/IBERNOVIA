<template>
  <div class="min-h-screen bg-[#fdfdfc] flex flex-col justify-between">
    
    <!-- Hero Section (Editorial Split Layout) -->
    <section class="relative bg-[#fdfdfc] pt-8 pb-16 lg:pt-16 lg:pb-24 overflow-hidden" aria-label="Portada">
      <!-- Background subtle elements -->
      <div class="absolute inset-0 bg-[radial-gradient(circle_at_30%_20%,rgba(168,117,71,0.05),transparent_40%)] pointer-events-none"></div>

      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        <div class="grid grid-cols-1 lg:grid-cols-12 gap-12 lg:gap-16 items-center">
          
          <!-- Text Content (Left Column) -->
          <div class="lg:col-span-5 flex flex-col items-center lg:items-start text-center lg:text-left space-y-6 lg:space-y-8 animate-fade-in-up">
            <p class="text-xs uppercase tracking-[0.3em] text-luxury-gold font-bold">
              Colección 2026
            </p>
            <div class="space-y-3">
              <h1 class="font-serif text-5xl sm:text-6xl lg:text-7xl font-light tracking-wide text-luxury-black leading-tight">
                IBERNOVIA
              </h1>
              <p class="font-serif text-xl sm:text-2xl lg:text-3xl tracking-[0.35em] text-luxury-black/60 uppercase font-light">
                ATELIER
              </p>
            </div>
            <p class="text-sm sm:text-base text-gray-600 leading-relaxed max-w-md text-balance">
              Diseño nupcial y complementos profesionales de alta costura seleccionados para ceremonia, fiesta y comunión.
            </p>
            <div class="pt-4">
              <router-link
                to="/acceso-empresarial"
                class="inline-flex items-center px-8 py-3.5 bg-luxury-black text-white hover:bg-luxury-gold transition-all duration-300 rounded-full text-xs uppercase tracking-[0.2em] font-bold shadow-md hover:shadow-lg hover:-translate-y-0.5"
              >
                Acceso empresarial
              </router-link>
            </div>
          </div>

          <!-- Image Frame (Right Column) -->
          <div class="lg:col-span-7 flex justify-center w-full animate-fade-in-up delay-150">
            <div class="relative w-full max-w-[520px] aspect-[3/4] rounded-2xl overflow-hidden shadow-2xl border border-black/5 group">
              <!-- Elegant double gold thin frame inside -->
              <div class="absolute inset-4 border border-white/40 rounded-xl z-20 pointer-events-none group-hover:inset-3 transition-all duration-500"></div>
              <div class="absolute inset-5 border border-white/20 rounded-lg z-20 pointer-events-none group-hover:inset-4 transition-all duration-500"></div>
              
              <!-- Subtle dark overlay for contrast -->
              <div class="absolute inset-0 bg-gradient-to-t from-black/20 via-transparent to-black/5 z-10"></div>
              
              <img
                src="/images/catalogo/novia/velos/17815-lentejuelas_3.webp"
                alt="Colección Ibernovia Atelier"
                class="w-full h-full object-cover transition-transform duration-700 ease-out group-hover:scale-105"
                loading="eager"
              />
            </div>
          </div>

        </div>
      </div>
    </section>

    <!-- Horizontal Infinite Slider Section -->
    <section class="py-12 bg-[#fdfdfc] border-t border-b border-black/5 overflow-hidden" aria-label="Colección destacada">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 mb-6">
        <div class="text-center md:text-left">
          <h2 class="font-serif text-2xl sm:text-3xl text-luxury-black tracking-wide">Nuestra Selección</h2>
          <p class="text-xs uppercase tracking-widest text-gray-500 mt-1">Avance de catálogo • Diseños exclusivos para profesionales</p>
        </div>
      </div>

      <!-- Marquee container -->
      <div v-if="loading" class="flex justify-center py-8">
        <div class="animate-spin rounded-full h-8 w-8 border-2 border-luxury-gold border-t-transparent"></div>
      </div>
      
      <div v-else-if="products.length > 0" class="relative w-full overflow-hidden py-4">
        <!-- Overlays de gradiente para difuminado lateral premium -->
        <div class="absolute left-0 top-0 bottom-0 w-12 sm:w-28 bg-gradient-to-r from-[#fdfdfc] to-transparent z-10 pointer-events-none"></div>
        <div class="absolute right-0 top-0 bottom-0 w-12 sm:w-28 bg-gradient-to-l from-[#fdfdfc] to-transparent z-10 pointer-events-none"></div>

        <div class="flex marquee-track">
          <!-- Wrapper 1 -->
          <div class="flex gap-6 pr-6 shrink-0">
            <div 
              v-for="prod in products" 
              :key="`marquee-1-${prod.id}`" 
              class="w-[150px] sm:w-[190px] shrink-0 group cursor-pointer"
              @click="router.push(`/producto/${prod.id}`)"
            >
              <div class="aspect-[3/4] rounded-xl overflow-hidden bg-gray-50 border border-black/5 shadow-sm">
                <img 
                  :src="getProductImage(prod)" 
                  :alt="prod.nombre" 
                  class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
                  loading="lazy"
                />
              </div>
              <h3 class="text-[11px] sm:text-xs font-semibold tracking-wider text-luxury-black mt-2.5 truncate text-center uppercase group-hover:text-luxury-gold transition-colors">
                {{ prod.nombre }}
              </h3>
              <p class="text-[9px] sm:text-[10px] tracking-widest text-gray-400 text-center uppercase mt-0.5">
                {{ prod.categoria }}
              </p>
            </div>
          </div>

          <!-- Wrapper 2 (Duplicado para scroll infinito sin cortes) -->
          <div class="flex gap-6 pr-6 shrink-0" aria-hidden="true">
            <div 
              v-for="prod in products" 
              :key="`marquee-2-${prod.id}`" 
              class="w-[150px] sm:w-[190px] shrink-0 group cursor-pointer"
              @click="router.push(`/producto/${prod.id}`)"
            >
              <div class="aspect-[3/4] rounded-xl overflow-hidden bg-gray-50 border border-black/5 shadow-sm">
                <img 
                  :src="getProductImage(prod)" 
                  :alt="prod.nombre" 
                  class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
                  loading="lazy"
                />
              </div>
              <h3 class="text-[11px] sm:text-xs font-semibold tracking-wider text-luxury-black mt-2.5 truncate text-center uppercase group-hover:text-luxury-gold transition-colors">
                {{ prod.nombre }}
              </h3>
              <p class="text-[9px] sm:text-[10px] tracking-widest text-gray-400 text-center uppercase mt-0.5">
                {{ prod.categoria }}
              </p>
            </div>
          </div>
        </div>
      </div>

      <div v-else class="text-center py-6 text-sm text-gray-500">
        No se pudieron cargar los productos destacados
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { apiClient } from '../lib/api'

const router = useRouter()
const products = ref([])
const loading = ref(true)

const getProductImage = (product) => {
  if (product && product.imagen) return product.imagen
  return 'https://placehold.co/400x400/e5e5e5/3a3a3a?text=Ibernovia'
}

onMounted(async () => {
  try {
    const res = await apiClient.get('/api/productos')
    const list = Array.isArray(res.data) 
      ? res.data.filter(p => p.activo !== false).slice(0, 15)
      : []
    products.value = list
  } catch (e) {
    console.error('Error fetching products for home slider:', e)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
/* Transiciones suaves en la carga (Cubic-Bezier) */
.animate-fade-in-up {
  animation: fadeInUp 1.2s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.delay-150 {
  animation-delay: 150ms;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(24px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Infinite Marquee CSS Slider */
.marquee-track {
  display: flex;
  width: max-content;
  animation: scrollMarquee 35s linear infinite;
}

.marquee-track:hover {
  animation-play-state: paused;
}

@keyframes scrollMarquee {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}
</style>
