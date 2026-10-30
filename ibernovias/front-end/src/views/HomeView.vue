<template>
  <div class="home-page min-h-screen bg-[#fdfdfc] flex flex-col justify-between">

    <section class="home-hero" aria-label="Portada">
      <div class="home-hero-copy">
        <p class="home-eyebrow">Complementos de ceremonia</p>
        <h1 class="home-wordmark">IBERNOVIA</h1>
        <p class="home-hero-description">Complementos de novia, novio, fiesta, comunión y arras para tiendas y profesionales.</p>
        <div class="home-hero-actions">
          <router-link to="/tienda" class="home-primary">Explorar catálogo</router-link>
          <router-link to="/auth" class="home-secondary">Acceso profesional</router-link>
        </div>
      </div>
      <picture class="home-hero-photo">
        <source media="(max-width: 640px)" srcset="/images/portada/velo-640.webp 640w, /images/portada/velo-960.webp 960w" sizes="100vw">
        <img src="/images/catalogo/novia/velos/17815-lentejuelas.webp" alt="Detalle del velo de novia del catálogo Ibernovia" width="1200" height="797" loading="eager" fetchpriority="high" decoding="async">
      </picture>
    </section>

    <section class="home-families" aria-label="Accesos por familia">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="home-families-head">
          <div>
            <p class="home-families-kicker">Explorar catálogo</p>
            <h2 class="home-families-title">Colecciones principales</h2>
          </div>
          <router-link :to="{ path: '/tienda', hash: '#catalogo' }" class="home-families-link">Ver todo</router-link>
        </div>

        <div class="home-family-grid">
          <router-link
            v-for="family in quickFamilies"
            :key="family.name"
            :to="{ path: '/tienda', hash: '#catalogo', query: { familia: family.query } }"
            class="home-family-card"
          >
            <img :src="family.image" :alt="family.alt" width="400" height="533" loading="lazy" fetchpriority="low" decoding="async">
            <span class="home-family-shade"></span>
            <span class="home-family-copy">
              <strong>{{ family.name }}</strong>
              <small>{{ family.caption }}</small>
            </span>
          </router-link>
        </div>
      </div>
    </section>

    <!-- Static selection; no duplicated products or automatic movement. -->
    <section class="home-featured py-12 bg-[#fdfdfc] border-t border-b border-black/5 overflow-hidden" aria-label="Colección destacada">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 mb-6">
        <div class="text-center md:text-left">
          <h2 class="font-serif text-2xl sm:text-3xl text-luxury-black tracking-wide">Nuestra Selección</h2>
          <p class="text-xs uppercase tracking-widest text-gray-500 mt-1">Complementos para tiendas y profesionales</p>
        </div>
      </div>

      <p v-if="loading" class="text-center py-8 text-sm" role="status">Cargando selección…</p>
      <div v-else-if="products.length > 0" class="featured-products max-w-7xl mx-auto px-4 sm:px-6">
        <router-link v-for="prod in products" :key="prod.id" :to="`/producto/${prod.id}`" class="featured-product">
          <img :src="getProductImage(prod)" :alt="productName(prod)" width="400" height="533" loading="lazy" fetchpriority="low" decoding="async">
          <h3>{{ productName(prod) }}</h3>
          <p>Referencia: {{ productReference(prod) }}</p>
        </router-link>
      </div>

      <div v-else class="text-center py-6 text-sm text-gray-500">
        No se pudieron cargar los productos destacados
      </div>
    </section>

    <!-- Promotions Section (Editorial & Luxury Aesthetic) -->
    <section v-if="promociones.length > 0" class="home-promos py-16 bg-[#faf9f5] border-t border-black/5" aria-label="Promociones especiales">
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
                  loading="lazy"
                  fetchpriority="low"
                  decoding="async"
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
                <div v-if="authStore.canSeePrices && (promo.precioOriginal || promo.precioOferta)" class="flex items-center gap-3 pt-2">
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
import { ref, onMounted, inject } from 'vue'
import { apiClient, fetchCachedProducts, getImageUrl } from '../lib/api'
import { productName, productReference } from '../lib/productPresentation'
import { useAuthStore } from '../stores/auth'

const toast = inject('toast', null)
const authStore = useAuthStore()
const products = ref([])
const promociones = ref([])
const loading = ref(true)
const quickFamilies = [
  {
    name: 'Novia',
    query: 'Novia',
    caption: 'Velos, tocados y detalles de ceremonia',
    image: '/images/catalogo/novia/tocados/3345-ma.webp',
    alt: 'Tocado de novia Ibernovia'
  },
  {
    name: 'Fiesta',
    query: 'Fiesta',
    caption: 'Invitada, madrina y complementos de evento',
    image: '/images/catalogo/fiesta/tocados/3150-ma.webp',
    alt: 'Tocado de fiesta Ibernovia'
  },
  {
    name: 'Comunión',
    query: 'Comunion',
    caption: 'Piezas delicadas para celebración',
    image: '/images/catalogo/comunion/diademas-y-coronas/2835-d.webp',
    alt: 'Diadema de comunión Ibernovia'
  },
  {
    name: 'Novio',
    query: 'Novio',
    caption: 'Gemelos y complementos formales',
    image: '/images/catalogo/novio/gemelos/5550rng.webp',
    alt: 'Complemento de novio Ibernovia'
  },
  {
    name: 'Arras',
    query: 'Arras',
    caption: 'Cestas, cojines y arras matrimoniales',
    image: '/images/catalogo/arras/cestas-y-cojines/1403-coj.webp',
    alt: 'Cojín de arras Ibernovia'
  }
]

const getProductImage = (product) => {
  if (product && product.imagen) return getImageUrl(product.imagen)
  return '/logo/logo.jpg'
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
    const [productsResult, promosResult] = await Promise.allSettled([
      fetchCachedProducts(),
      apiClient.get('/api/promociones')
    ])

    if (productsResult.status === 'fulfilled') {
      products.value = productsResult.value
        .filter(p => p.activo !== false)
        .slice(0, 6)
    }

    if (promosResult.status === 'fulfilled') {
      promociones.value = Array.isArray(promosResult.value.data) ? promosResult.value.data : []
    }
  } finally {
    loading.value = false
  }
})

</script>

<style scoped>
.home-families {
  padding: 3.2rem 0;
  background: #fffdf9;
  border-top: 1px solid rgb(var(--luxury-black-rgb) / 0.05);
}

.home-families-head {
  display: flex;
  justify-content: space-between;
  align-items: end;
  gap: 1.5rem;
  margin-bottom: 1.4rem;
}

.home-families-kicker {
  font-size: 12px;
  font-weight: 800;
  letter-spacing: 0.26em;
  text-transform: uppercase;
  color: var(--luxury-gold);
}

.home-families-title {
  margin-top: 0.35rem;
  font-family: 'Fraunces', Georgia, serif;
  font-size: clamp(2rem, 4vw, 3.1rem);
  font-weight: 300;
  line-height: 1;
  color: var(--luxury-black);
}

.home-families-link {
  padding-bottom: 0.25rem;
  border-bottom: 1px solid currentColor;
  font-size: 13px;
  font-weight: 800;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  color: rgb(var(--luxury-black-rgb) / 0.72);
}

.home-family-grid {
  display: grid;
  grid-template-columns: repeat(5, minmax(0, 1fr));
  gap: 0.85rem;
}

.home-family-card {
  position: relative;
  display: block;
  min-height: 280px;
  overflow: hidden;
  border-radius: 8px;
  background: #eee8df;
  border: 1px solid rgb(var(--luxury-black-rgb) / 0.06);
  color: white;
  isolation: isolate;
}

.home-family-card img {
  width: 100%;
  height: 100%;
  min-height: 280px;
  display: block;
  object-fit: cover;
  transition: transform 0.7s ease;
}

.home-family-card:hover img {
  transform: scale(1.04);
}

.home-family-shade {
  position: absolute;
  inset: 0;
  z-index: 1;
  background: linear-gradient(180deg, rgb(0 0 0 / 0.02), rgb(0 0 0 / 0.7));
}

.home-family-copy {
  position: absolute;
  left: 1rem;
  right: 1rem;
  bottom: 1rem;
  z-index: 2;
  display: grid;
  gap: 0.35rem;
}

.home-family-copy strong {
  font-family: 'Fraunces', Georgia, serif;
  font-size: 1.55rem;
  font-weight: 300;
}

.home-family-copy small {
  max-width: 18ch;
  font-size: 13px;
  line-height: 1.45;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: #fff;
}


@media (max-width: 640px) {
  .home-featured,
  .home-promos {
    padding-top: 2.5rem;
    padding-bottom: 2.5rem;
  }

  .home-families {
    padding: 2.5rem 0;
  }

  .home-families-head {
    align-items: start;
    margin-bottom: 1rem;
  }

  .home-families-kicker,
  .home-families-link {
    letter-spacing: 0.14em;
  }

  .home-family-grid {
    display: grid;
    grid-auto-flow: column;
    grid-auto-columns: minmax(142px, 44vw);
    grid-template-columns: none;
    gap: 0.65rem;
    overflow-x: auto;
    padding-bottom: 0.55rem;
    scroll-snap-type: x mandatory;
  }

  .home-family-card {
    min-height: 210px;
    scroll-snap-align: start;
  }

  .home-family-card img {
    min-height: 210px;
  }

  .home-family-copy {
    left: 0.8rem;
    right: 0.8rem;
    bottom: 0.85rem;
  }

  .home-family-copy strong {
    font-size: 1.28rem;
  }

  .home-family-copy small {
    font-size: 12px;
    letter-spacing: 0.05em;
  }

  .home-promos .mb-16 {
    margin-bottom: 2.25rem;
  }

  .home-promos .rounded-2xl {
    border-radius: 0.75rem;
  }
}

.home-hero {display:grid;grid-template-columns:1fr 1fr;min-height:660px;background:#fcfaf6;align-items:center;}
.home-hero-copy {padding:5rem clamp(1.5rem,5vw,6rem);}
.home-eyebrow {font-size:.8rem;letter-spacing:.16em;text-transform:uppercase;color:#795333;margin-bottom:1.8rem;}
.home-wordmark {font-family:'Fraunces',Georgia,serif;font-size:clamp(2.7rem,5.2vw,5.8rem);font-weight:300;letter-spacing:.045em;line-height:1.1;color:#2d2924;}
.home-hero-description {font-size:1rem;line-height:1.9;color:#575047;max-width:36ch;margin:2rem 0;}
.home-hero-actions {display:flex;flex-wrap:wrap;gap:1rem;}
.home-hero-actions a {display:inline-flex;align-items:center;justify-content:center;min-height:48px;padding:.85rem 1.25rem;font-size:.85rem;border:1px solid #2d2924;}
.home-primary {background:#2d2924;color:#fff;}
.home-secondary {color:#2d2924;}
.home-hero-photo {display:block;height:100%;min-height:660px;overflow:hidden;}
.home-hero-photo img {width:100%;height:100%;object-fit:cover;object-position:52% center;}
.featured-products {display:grid;grid-template-columns:repeat(6,minmax(0,1fr));gap:1.5rem;}
.featured-product img {width:100%;aspect-ratio:3/4;object-fit:contain;padding:.75rem;background:#f7f4ef;border:1px solid #e9e3da;}
.featured-product h3 {font-family:'Fraunces',Georgia,serif;font-size:1rem;line-height:1.5;margin-top:1rem;}
.featured-product p {font-size:.8rem;color:#655e55;margin-top:.4rem;}
@media(max-width:900px){.featured-products{grid-template-columns:repeat(3,minmax(0,1fr));}}
@media(max-width:640px){
 .home-hero{grid-template-columns:1fr;min-height:0;}
 .home-hero-copy{padding:3rem 1.25rem 2.5rem;}
 .home-wordmark{font-size:clamp(2.4rem,11vw,3.6rem);}
 .home-hero-description{margin:1.5rem 0;}
 .home-hero-actions{gap:.65rem;}
 .home-hero-actions a{flex:1 1 auto;padding:.85rem .8rem;}
 .home-hero-photo{height:auto;min-height:0;aspect-ratio:1200/797;}
 .featured-products{grid-template-columns:repeat(2,minmax(0,1fr));gap:1.25rem;}
}
</style>
