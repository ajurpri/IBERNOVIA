<template>
  <div class="home-page min-h-screen bg-[#fdfdfc] flex flex-col justify-between">

    <section class="home-hero" aria-label="Portada">
      <picture class="home-hero-photo">
        <source media="(max-width: 640px)" srcset="/images/portada/velo-640.webp 640w, /images/portada/velo-960.webp 960w" sizes="100vw">
        <img src="/images/catalogo/novia/velos/17815-lentejuelas.webp" alt="Detalle de un velo del catálogo Ibernovia" width="1200" height="797" loading="eager" fetchpriority="high" decoding="async">
      </picture>
      <div class="home-hero-copy">
        <h1 class="home-wordmark">IBERNOVIA</h1>
        <p class="home-subline">Complementos de novia y ceremonia</p>
        <p class="home-hero-description">Para tiendas y profesionales.</p>
        <div class="home-hero-actions">
          <router-link to="/tienda" class="home-primary">Explorar catálogo</router-link>
          <router-link to="/auth" class="home-secondary">Acceso profesional</router-link>
        </div>
      </div>
    </section>

    <section ref="familiesSection" class="home-families" aria-label="Accesos por familia">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="home-families-head">
          <div>
            <h2 class="home-families-title">Colecciones</h2>
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
            <img v-if="familiesReady" :src="family.image" :alt="family.alt" width="600" height="400" loading="eager" fetchpriority="low" decoding="async">
            <span v-else class="home-family-image-space" aria-hidden="true"></span>
            <span class="home-family-copy">
              <strong>{{ family.name }}</strong>
            </span>
          </router-link>
        </div>
      </div>
    </section>

    <section class="max-w-7xl mx-auto w-full px-6 py-16 md:py-24 grid md:grid-cols-2 gap-10 items-start" aria-labelledby="local-title">
      <div>
        <p class="text-xs tracking-widest uppercase text-luxury-gold mb-4">Andújar · Jaén</p>
        <h2 id="local-title" class="font-serif text-3xl md:text-4xl leading-tight">Complementos para cada celebración</h2>
        <p class="mt-5 text-gray-600 leading-relaxed">Descubre las colecciones de Ibernovia para novia, novio, fiesta, comunión y arras. Velos, tocados, diademas, pendientes y otros detalles para completar tu conjunto de ceremonia.</p>
        <p class="mt-4 text-gray-600 leading-relaxed">Nuestro catálogo se puede consultar libremente. Las tarifas y solicitudes de presupuesto están reservadas a tiendas y profesionales con acceso validado.</p>
      </div>
      <div class="border-l-2 border-luxury-gold pl-7 md:pl-10">
        <p class="text-xs tracking-widest uppercase text-gray-500 mb-4">Dónde encontrarnos</p>
        <h3 class="font-serif text-3xl">En Andújar, en Paola Novias</h3>
        <p class="mt-5 text-gray-600 leading-relaxed">Puedes encontrar los complementos de Ibernovia en Paola Novias, en Andújar. Consulta con la tienda la disponibilidad de la pieza que te interesa antes de tu visita.</p>
        <a href="/complementos-novia-andujar.html" class="inline-block mt-6 underline underline-offset-4 text-luxury-black">Complementos de novia y ceremonia en Andújar →</a>
      </div>
    </section>

    <!-- Moving product selection; photography stays unobstructed. -->
    <section ref="featuredSection" class="home-featured py-12 bg-[#fdfdfc] border-t border-b border-black/5 overflow-hidden" aria-label="Colección destacada">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mb-6">
        <div class="home-families-head">
          <h2 class="home-families-title">Nuestra selección</h2>
          <router-link to="/tienda" class="home-families-link">Ver catálogo</router-link>
        </div>
      </div>

      <p v-if="loading" class="text-center py-8 text-sm" role="status">Cargando selección…</p>
      <div v-else-if="products.length > 0" class="featured-window">
        <div class="featured-track">
          <div v-for="copy in 2" :key="copy" class="featured-group" :aria-hidden="copy === 2 ? 'true' : undefined">
            <router-link v-for="prod in products" :key="prod.id" :to="`/producto/${prod.id}`" class="featured-product" :tabindex="copy === 2 ? -1 : undefined">
              <img v-if="selectionReady" :src="getProductImage(prod)" :alt="productName(prod)" width="600" height="400" loading="eager" fetchpriority="low" decoding="async">
              <span v-else class="featured-image-space" aria-hidden="true"></span>
              <h3>{{ productName(prod) }}</h3>
              <p v-if="productName(prod) !== productReference(prod)">Referencia: {{ productReference(prod) }}</p>
            </router-link>
          </div>
        </div>
      </div>

      <div v-else class="text-center py-6 text-sm text-gray-500">
        No se pudieron cargar los productos destacados
      </div>
    </section>

    <section v-if="promociones.length > 0" class="home-promos" aria-label="Promociones">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="home-families-title">Promociones</h2>
        <div class="promo-grid">
          <article v-for="promo in promociones" :key="promo.id" class="promo-item">
            <img v-if="promo.imagen" :src="getImageUrl(promo.imagen)" :alt="promo.titulo" width="600" height="400" loading="lazy" decoding="async">
            <p v-if="promo.descuento" class="promo-discount">{{ promo.descuento }}</p>
            <h3>{{ promo.titulo }}</h3>
            <p>{{ promo.descripcion }}</p>
            <div v-if="authStore.canSeePrices && (promo.precioOriginal || promo.precioOferta)" class="flex items-center gap-3">
              <span v-if="promo.precioOriginal" class="line-through text-gray-500">{{ promo.precioOriginal }}?</span>
              <strong v-if="promo.precioOferta">{{ promo.precioOferta }}?</strong>
            </div>
            <div v-if="promo.codigo" class="promo-code">
              <span>Código: <strong>{{ promo.codigo }}</strong></span>
              <button type="button" @click="copyToClipboard(promo.codigo)">Copiar código</button>
            </div>
            <p v-if="promo.fechaFin" class="promo-expiry">Válido hasta {{ formatDate(promo.fechaFin) }}</p>
          </article>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, inject } from 'vue'
import { apiClient, fetchCachedProducts, getImageUrl } from '../lib/api'
import { productName, productReference } from '../lib/productPresentation'
import { useAuthStore } from '../stores/auth'

const toast = inject('toast', null)
const authStore = useAuthStore()
const products = ref([])
const familiesSection = ref(null)
const familiesReady = ref(false)
const featuredSection = ref(null)
const selectionReady = ref(false)
let selectionObserver
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
    image: '/images/catalogo/fiesta/tocados/3287-ma.webp',
    alt: 'Tocado de fiesta Ibernovia'
  },
  {
    name: 'Comunión',
    query: 'Comunión',
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
    image: '/images/catalogo/arras/cestas-y-cojines/1390-coj.webp',
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
  if ('IntersectionObserver' in window) {
    selectionObserver = new IntersectionObserver((entries) => {
      for (const entry of entries) {
        if (!entry.isIntersecting) continue
        if (entry.target === featuredSection.value) selectionReady.value = true
        if (entry.target === familiesSection.value) familiesReady.value = true
        selectionObserver.unobserve(entry.target)
      }
    }, { rootMargin: '400px' })
    if (featuredSection.value) selectionObserver.observe(featuredSection.value)
    if (familiesSection.value) selectionObserver.observe(familiesSection.value)
  } else {
    selectionReady.value = true
    familiesReady.value = true
  }

  try {
    const [productsResult, promosResult] = await Promise.allSettled([
      fetchCachedProducts(),
      apiClient.get('/api/promociones')
    ])

    if (productsResult.status === 'fulfilled') {
      const available = productsResult.value.filter(p => p.activo !== false)
      // A small display selection from existing references; the full catalogue is unchanged.
      const displayIds = [564, 518, 393, 632, 357, 639, 403, 631, 355, 578]
      products.value = displayIds.map(id => available.find(product => product.id === id)).filter(Boolean)
      if (!products.value.length) products.value = available.slice(0, 15)
    }

    if (promosResult.status === 'fulfilled') {
      promociones.value = Array.isArray(promosResult.value.data) ? promosResult.value.data : []
    }
  } finally {
    loading.value = false
  }
})

onBeforeUnmount(() => selectionObserver?.disconnect())
</script>

<style scoped>
.home-hero {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 460px;
  height: min(70svh, 700px);
  overflow: hidden;
  background: #fcfaf6;
}
.home-hero-photo {position:absolute;inset:0;}
.home-hero-photo img {width:100%;height:100%;object-fit:cover;object-position:center;}
.home-hero::after {content:'';position:absolute;inset:0;background:linear-gradient(180deg,rgba(253,253,252,.78),rgba(253,253,252,.68) 55%,rgba(253,253,252,.24));}
.home-hero-copy {position:relative;z-index:1;text-align:center;max-width:900px;padding:3rem 1.5rem;}
.home-eyebrow {font-size:.8rem;letter-spacing:.2em;text-transform:uppercase;color:#715035;margin-bottom:1.5rem;}
.home-wordmark {font-family:'Fraunces',Georgia,serif;font-size:clamp(3rem,7vw,6rem);font-weight:300;letter-spacing:.09em;line-height:1.1;color:#2d2924;}
.home-subline {font-family:'Fraunces',Georgia,serif;font-size:clamp(1.1rem,2vw,1.5rem);letter-spacing:.025em;margin-top:1.2rem;color:#3a342d;}
.home-rule {width:4rem;height:1px;background:#a87547;margin:1.8rem auto;}
.home-hero-description {font-size:.95rem;line-height:1.8;color:#3d3730;margin:1.25rem auto 2rem;}
.home-hero-actions {display:flex;justify-content:center;flex-wrap:wrap;gap:1rem;}
.home-hero-actions a {display:inline-flex;align-items:center;justify-content:center;min-height:48px;padding:.85rem 1.5rem;font-size:.85rem;border:1px solid #2d2924;}
.home-primary {background:#2d2924;color:#fff;}
.home-secondary {color:#2d2924;background:rgba(253,253,252,.65);}

.home-families {padding:clamp(2.5rem,5vw,4.5rem) 0;background:#fdfdfc;}
.home-families-head {display:flex;justify-content:space-between;align-items:end;gap:1.5rem;margin-bottom:1.8rem;}
.home-families-kicker {font-size:.75rem;letter-spacing:.12em;text-transform:uppercase;color:#795333;}
.home-families-title {margin-top:.4rem;font-family:'Fraunces',Georgia,serif;font-size:clamp(1.8rem,3vw,2.5rem);font-weight:300;line-height:1.2;}
.home-families-link {white-space:nowrap;font-size:.85rem;text-decoration:underline;text-underline-offset:4px;}
.home-family-grid {display:grid;grid-template-columns:repeat(5,minmax(0,1fr));gap:1.25rem;}
.home-family-card {display:block;color:#302a24;}
.home-family-card img,.home-family-image-space {display:block;width:100%;height:auto;aspect-ratio:1200/797;object-fit:contain;}
.home-family-copy {display:block;margin-top:.85rem;}
.home-family-copy strong {font-family:'Manrope','Segoe UI',sans-serif;font-size:.95rem;font-weight:500;}

.home-featured {border:0;padding-top:1rem;padding-bottom:clamp(3rem,6vw,5rem);}
.featured-window {overflow:hidden;}
.featured-track {display:flex;width:max-content;animation:selection-scroll 150s linear infinite;}
.featured-group {display:flex;gap:2rem;padding-right:2rem;flex-shrink:0;}
.featured-product {display:block;width:340px;flex-shrink:0;}
.featured-product img,.featured-image-space {display:block;width:100%;height:240px;object-fit:contain;}
.featured-product h3 {font-family:'Manrope','Segoe UI',sans-serif;font-size:.9rem;font-weight:500;line-height:1.5;margin-top:.85rem;}
.featured-product p {font-size:.8rem;color:#655e55;margin-top:.35rem;}
.featured-window:hover .featured-track,.featured-window:focus-within .featured-track {animation-play-state:paused;}
@keyframes selection-scroll {to {transform:translateX(-50%);}}

@media(max-width:640px){
  .home-hero{min-height:420px;height:64svh;}
  .home-hero-copy{padding:2rem 1rem;}
  .home-wordmark{font-size:clamp(2.25rem,11vw,3.2rem);letter-spacing:.07em;}
  .home-subline{font-size:1.1rem;letter-spacing:.02em;}
  .home-hero-actions{gap:.65rem;}
  .home-hero-actions a{padding:.85rem 1rem;}
  .home-families,.home-featured,.home-promos{padding-top:2.5rem;padding-bottom:2.5rem;}
  .home-families-head{align-items:start;gap:1rem;}
  .home-family-grid{grid-auto-flow:column;grid-auto-columns:minmax(220px,74vw);grid-template-columns:none;gap:1rem;overflow-x:auto;padding-bottom:.75rem;scroll-snap-type:x proximity;}
  .home-family-card{scroll-snap-align:start;}
  .home-family-copy strong{font-size:.9rem;}
  .featured-product{width:300px;}
  .featured-product img,.featured-image-space{height:210px;}
  .home-promos .mb-16{margin-bottom:2.25rem;}
}
@media(prefers-reduced-motion:reduce){
  .featured-track{animation:none;transform:none;width:auto;}
  .featured-window{overflow-x:auto;}
  .featured-group[aria-hidden="true"]{display:none;}
}

.home-family-grid {scrollbar-width:thin;scrollbar-color:#bfb7ad transparent;}
.home-family-grid::-webkit-scrollbar {height:3px;}
.home-family-grid::-webkit-scrollbar-track {background:transparent;}
.home-family-grid::-webkit-scrollbar-thumb {background:#bfb7ad;}
.home-promos {padding:3rem 0 4rem;border-top:1px solid #e8e5df;}
.promo-grid {display:grid;grid-template-columns:repeat(auto-fit,minmax(min(100%,280px),1fr));gap:2.5rem;margin-top:2rem;}
.promo-item {display:flex;flex-direction:column;align-items:start;gap:1rem;}
.promo-item img {width:100%;aspect-ratio:3/2;object-fit:contain;}
.promo-item h3 {font-size:1.1rem;font-weight:500;}
.promo-item p {font-size:.9rem;line-height:1.7;color:#514b43;}
.promo-discount {font-weight:600;}
.promo-code {display:flex;flex-wrap:wrap;align-items:center;gap:1rem;font-size:.85rem;}
.promo-code button {min-height:44px;text-decoration:underline;text-underline-offset:4px;}
.promo-item .promo-expiry {font-size:.8rem;}
</style>
