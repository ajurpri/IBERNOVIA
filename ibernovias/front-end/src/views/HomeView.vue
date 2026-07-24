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
                src="/images/catalogo/novia/velos/17815-lentejuelas.webp"
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
import { ref, onMounted, inject } from 'vue'
import { useRouter } from 'vue-router'
import { apiClient, getImageUrl } from '../lib/api'

const router = useRouter()
const toast = inject('toast', null)
const products = ref([])
const promociones = ref([])
const loading = ref(true)

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
