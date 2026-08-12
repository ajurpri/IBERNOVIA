<template>
  <div class="min-h-screen bg-[#fdfdfc] flex flex-col justify-between">
    
    <!-- Hero Section (Minimalist Full-Bleed Editorial Banner) -->
    <section class="relative h-[85vh] min-h-[550px] flex items-center justify-center overflow-hidden" aria-label="Portada">
      
      <!-- Fading Campaign Background -->
      <div class="absolute inset-0 z-0 bg-[#fbfbfa]">
        <div 
          v-for="(img, idx) in heroImages" 
          :key="idx"
          class="absolute inset-0 transition-opacity duration-1500 ease-in-out"
          :class="currentHeroIndex === idx ? 'opacity-100' : 'opacity-0'"
        >
          <img
            :src="img.url"
            :alt="img.alt"
            class="w-full h-full object-cover select-none pointer-events-none"
            :loading="idx === 0 ? 'eager' : 'lazy'"
          />
        </div>
        
        <!-- Light Frosted Editorial Overlay -->
        <div class="absolute inset-0 bg-[#fdfdfc]/80 backdrop-blur-[1px] z-10"></div>
        <div class="absolute inset-0 bg-gradient-to-b from-[#fdfdfc]/10 via-transparent to-[#fdfdfc] z-10"></div>
      </div>

      <!-- Centered Premium Typography & Content -->
      <div class="relative z-20 max-w-4xl mx-auto px-6 text-center space-y-6 sm:space-y-8 animate-fade-in-up">
        <p class="text-[10px] sm:text-xs uppercase tracking-[0.45em] text-luxury-gold font-bold mb-2">
          Colección Atelier 2026
        </p>
        
        <div class="space-y-4">
          <h1 class="font-serif text-5xl sm:text-7xl lg:text-8xl font-light tracking-[0.15em] text-luxury-black leading-none">
            IBERNOVIA
          </h1>
          <p class="font-serif text-base sm:text-lg lg:text-xl tracking-[0.55em] text-luxury-black/50 uppercase font-light">
            ALTA COSTURA & COMPLEMENTOS
          </p>
        </div>
        
        <div class="w-16 h-[1px] bg-luxury-gold mx-auto my-6 sm:my-8"></div>
        
        <p class="text-xs sm:text-sm text-gray-500 leading-relaxed max-w-lg mx-auto tracking-wide font-light">
          Alta costura nupcial y accesorios exclusivos seleccionados para profesionales. Descubra nuestras colecciones de velos, tocados, comunión, fiesta y arras.
        </p>
        
        <div class="pt-6 sm:pt-8">
          <router-link
            to="/acceso-empresarial"
            class="inline-flex items-center px-10 py-3.5 border border-luxury-black text-luxury-black hover:bg-luxury-black hover:text-white transition-all duration-500 text-[10px] uppercase tracking-[0.25em] font-bold"
          >
            Acceso Empresarial
          </router-link>
        </div>
      </div>

      <!-- Discrete Slideshow Indicators (Bottom Center) -->
      <div class="absolute bottom-8 left-1/2 -translate-x-1/2 z-20 flex gap-3">
        <button 
          v-for="(_, idx) in heroImages" 
          :key="idx"
          @click="currentHeroIndex = idx"
          class="w-1.5 h-1.5 rounded-full transition-all duration-500"
          :class="currentHeroIndex === idx ? 'bg-luxury-gold w-6' : 'bg-luxury-black/25 hover:bg-luxury-black/50'"
          :aria-label="`Ver imagen ${idx + 1}`"
        ></button>
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

    <!-- Promotions Section (Editorial & Luxury Aesthetic) -->
    <section v-if="promociones.length > 0" class="py-16 bg-[#faf9f5] border-t border-black/5" aria-label="Promociones especiales">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <!-- Refined Title Area -->
        <div class="text-center max-w-3xl mx-auto mb-16 animate-fade-in-up">
          <p class="text-xs uppercase tracking-[0.3em] text-luxury-gold font-bold mb-3">
            Oportunidades únicas
          </p>
          <h2 class="font-serif text-3xl sm:text-4xl lg:text-5xl font-light text-luxury-black tracking-wide leading-tight">
            Promociones Exclusivas
          </h2>
          <div class="w-12 h-[1px] bg-luxury-gold mx-auto mt-6 mb-4"></div>
          <p class="text-xs sm:text-sm text-gray-500 max-w-md mx-auto leading-relaxed">
            Avance de ofertas y beneficios especiales diseñados para nuestras novias y clientes colaboradores.
          </p>
        </div>

        <!-- Promotions Cards Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 sm:gap-10">
          <div 
            v-for="promo in promociones" 
            :key="promo.id"
            class="bg-white rounded-2xl border border-black/5 overflow-hidden shadow-sm hover:shadow-md transition-all duration-300 flex flex-col justify-between group"
          >
            <div>
              <!-- Promotion Image with Gold Overlay on Hover -->
              <div class="relative aspect-[16/9] w-full overflow-hidden bg-gray-50">
                <img 
                  v-if="promo.imagen" 
                  :src="getImageUrl(promo.imagen)" 
                  :alt="promo.titulo" 
                  class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-102"
                />
                <!-- Elegant placeholder if no image -->
                <div v-else class="w-full h-full flex flex-col items-center justify-center bg-gradient-to-br from-[#faf9f5] to-white p-6 border-b border-black/5">
                  <span class="font-serif text-5xl font-light text-luxury-gold/30">%</span>
                </div>
                <!-- Discount Gold Badge -->
                <span 
                  v-if="promo.descuento" 
                  class="absolute top-4 left-4 bg-luxury-black text-[#faf9f5] text-[10px] font-bold uppercase tracking-[0.2em] px-3.5 py-1.5 rounded-full shadow-md z-10"
                >
                  {{ promo.descuento }}
                </span>
              </div>

              <!-- Card Details -->
              <div class="p-6 sm:p-8 space-y-4">
                <h3 class="font-serif text-xl text-luxury-black font-normal tracking-wide group-hover:text-luxury-gold transition-colors duration-300">
                  {{ promo.titulo }}
                </h3>
                <p class="text-xs sm:text-sm text-gray-600 leading-relaxed">
                  {{ promo.descripcion }}
                </p>
                <div v-if="promo.precioOriginal || promo.precioOferta" class="flex items-center gap-3 pt-2">
                  <span v-if="promo.precioOriginal" class="text-sm line-through text-gray-400 font-light">
                    {{ promo.precioOriginal }}€
                  </span>
                  <span v-if="promo.precioOferta" class="text-base font-bold text-luxury-gold tracking-wide">
                    {{ promo.precioOferta }}€
                  </span>
                </div>
              </div>
            </div>

            <!-- Coupon Code & Expiration Date -->
            <div class="px-6 pb-6 sm:px-8 sm:pb-8 pt-2">
              <div v-if="promo.codigo" class="bg-[#faf9f5] border border-black/5 rounded-xl p-4 flex items-center justify-between gap-4">
                <div>
                  <p class="text-[9px] uppercase tracking-widest text-gray-400 font-bold mb-0.5">Código de Cupón</p>
                  <p class="font-mono text-sm font-bold text-luxury-black uppercase tracking-wider">{{ promo.codigo }}</p>
                </div>
                <button 
                  @click="copyToClipboard(promo.codigo)"
                  class="text-xs text-luxury-gold hover:text-luxury-black font-bold uppercase tracking-wider transition-colors focus:outline-none"
                  title="Copiar cupón"
                >
                  Copiar
                </button>
              </div>
              
              <!-- Expiry date -->
              <div v-if="promo.fechaFin" class="mt-4 flex items-center justify-center gap-2 text-[10px] uppercase tracking-widest text-gray-400">
                <span>Válido hasta: {{ formatDate(promo.fechaFin) }}</span>
              </div>
            </div>

          </div>
        </div>

      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, inject } from 'vue'
import { useRouter } from 'vue-router'
import { apiClient, getImageUrl } from '../lib/api'

const router = useRouter()
const toast = inject('toast', null)
const products = ref([])
const promociones = ref([])
const loading = ref(true)

// Slideshow state
const currentHeroIndex = ref(0)
const heroImages = ref([
  {
    url: '/images/catalogo/novia/velos/17815-lentejuelas.webp',
    alt: 'Velo de Novia con Lentejuelas - Ibernovia Atelier'
  },
  {
    url: '/images/catalogo/comunion/diademas-y-coronas/2835-d.webp',
    alt: 'Diadema de Comunión - Ibernovia Atelier'
  },
  {
    url: '/images/catalogo/novia/tocados/3345-ma.webp',
    alt: 'Tocado de Novia Artesanal - Ibernovia Atelier'
  },
  {
    url: '/images/catalogo/fiesta/tocados/3150-ma.webp',
    alt: 'Tocado de Fiesta y Madrina - Ibernovia Atelier'
  }
])
let heroInterval = null

const getProductImage = (product) => {
  if (product && product.imagen) return getImageUrl(product.imagen)
  return 'https://placehold.co/400x400/e5e5e5/3a3a3a?text=Ibernovia'
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', { day: '2-digit', month: 'long', year: 'numeric' })
}

const copyToClipboard = (text) => {
  navigator.clipboard.writeText(text)
  if (toast) {
    toast.show('✓ Cupón copiado al portapapeles', 'success', 2000)
  } else {
    alert('Cupón copiado: ' + text)
  }
}

onMounted(async () => {
  // Slideshow rotation every 5 seconds
  heroInterval = setInterval(() => {
    currentHeroIndex.value = (currentHeroIndex.value + 1) % heroImages.value.length
  }, 5000)

  try {
    // Cargar productos
    const resProd = await apiClient.get('/api/productos')
    const listProd = Array.isArray(resProd.data) 
      ? resProd.data.filter(p => p.activo !== false).slice(0, 15)
      : []
    products.value = listProd
    
    // Cargar promociones activas
    const resPromo = await apiClient.get('/api/promociones')
    promociones.value = Array.isArray(resPromo.data) ? resPromo.data : []
  } catch (e) {
    console.error('Error fetching data for home:', e)
  } finally {
    loading.value = false
  }
})

onBeforeUnmount(() => {
  if (heroInterval) {
    clearInterval(heroInterval)
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
