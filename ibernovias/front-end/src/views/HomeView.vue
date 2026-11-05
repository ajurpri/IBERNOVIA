<template>
  <div class="home-page min-h-screen bg-[#fdfdfc] flex flex-col justify-between">

    <section class="home-hero" aria-label="Portada">
      <picture class="home-hero-photo">
        <source v-if="!heroFallback" media="(max-width: 640px)" srcset="/images/portada/campana-burdeos-movil-480.webp 480w, /images/portada/campana-burdeos-movil-768.webp 768w" sizes="100vw">
        <source v-if="!heroFallback" srcset="/images/portada/campana-burdeos-960.webp 960w, /images/portada/campana-burdeos-1536.webp 1536w" sizes="100vw">
        <img :key="heroFallback" src="/images/catalogo/novia/velos/17815-lentejuelas.webp" @error="heroFallback = true" :alt="heroFallback ? 'Detalle de un velo Ibernovia' : 'Composición de campaña con tocado floral y pendientes sobre fondo burdeos'" width="1536" height="1024" loading="eager" fetchpriority="high" decoding="async">
      </picture>
      <div class="home-hero-copy">
        <h1 class="home-wordmark">Complementos de novia y ceremonia</h1>
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
            <span class="home-family-visual">
              <img v-if="familiesReady" :src="family.image" :alt="family.alt" width="600" height="400" loading="lazy" fetchpriority="low" decoding="async">
              <span v-else class="home-family-image-space" aria-hidden="true"></span>
            </span>
            <span class="home-family-copy">
              <strong>{{ family.name }}</strong>
            </span>
          </router-link>
        </div>
      </div>
    </section>



    <!-- Moving product selection; photography stays unobstructed. -->
    <section ref="featuredSection" class="home-featured py-12 bg-[#fdfdfc] border-t border-b border-black/5 overflow-hidden" aria-label="Colección destacada">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mb-6">
        <div class="home-families-head">
          <div>
            <h2 class="home-families-title">Nuestra selección</h2>
          </div>
          <router-link to="/tienda" class="home-families-link">Ver catálogo</router-link>
        </div>
      </div>

      <p v-if="loading" class="text-center py-8 text-sm" role="status">Cargando selección…</p>
      <div v-else-if="products.length > 0" class="featured-window">
        <div class="featured-track" :class="{ 'is-paused': !selectionVisible }">
          <div v-for="copy in 2" :key="copy" class="featured-group" :aria-hidden="copy === 2 ? 'true' : undefined">
            <router-link v-for="prod in products" :key="prod.id" :to="`/producto/${prod.id}`" class="featured-product" :tabindex="copy === 2 ? -1 : undefined">
              <img v-if="selectionReady" :src="getProductImage(prod)" :alt="productName(prod)" width="600" height="400" loading="eager" fetchpriority="low" decoding="async">
              <span v-else class="featured-image-space" aria-hidden="true"></span>
              <h3><span>Ref.</span> {{ productReference(prod) }}</h3>
            </router-link>
          </div>
        </div>
      </div>

      <div v-else class="text-center py-6 text-sm text-gray-500">
        No se pudieron cargar los productos destacados
      </div>
    </section>

    <aside class="retail-note" aria-label="Compra en tienda">
      <div>
        <p>¿No eres profesional? Consúltanos dónde comprar nuestros complementos cerca de ti.</p>
        <p class="retail-note-detail">En Andújar, <a href="/complementos-novia-andujar.html">Paola Novias</a> es nuestra tienda oficial.</p>
      </div>
      <router-link to="/contacto" class="retail-note-link">Consultar tienda</router-link>
    </aside>

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
const heroFallback = ref(false)
const familiesSection = ref(null)
const familiesReady = ref(false)
const featuredSection = ref(null)
const selectionReady = ref(false)
const selectionVisible = ref(false)
let selectionObserver
let motionObserver
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
    motionObserver = new IntersectionObserver(([entry]) => {
      selectionVisible.value = entry.isIntersecting
    })
    if (featuredSection.value) motionObserver.observe(featuredSection.value)
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
    selectionVisible.value = true
    selectionReady.value = true
    familiesReady.value = true
  }

  // Render each section as soon as its own request completes.
  await Promise.allSettled([
    fetchCachedProducts().then((catalog) => {
      const available = catalog.filter(p => p.activo !== false)
      // A small display selection from existing references; the full catalogue is unchanged.
      const displayIds = [564, 518, 393, 632, 357, 639, 403, 631, 355, 578]
      products.value = displayIds.map(id => available.find(product => product.id === id)).filter(Boolean)
      if (!products.value.length) products.value = available.slice(0, 15)
    }).finally(() => { loading.value = false }),
    apiClient.get('/api/promociones').then(({ data }) => {
      promociones.value = Array.isArray(data) ? data : []
    })
  ])
})

onBeforeUnmount(() => {
  selectionObserver?.disconnect()
  motionObserver?.disconnect()
})
</script>

<style scoped>
.retail-note {
  width: calc(100% - 48px);
  max-width: 1200px;
  margin: 0 auto 48px;
  padding: 20px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px 32px;
  background: transparent;
  border-top: 1px solid #e8e3dc;
  color: #49423b;
  font-size: 14px;
  line-height: 1.7;
}
.retail-note p { margin: 0; }
.retail-note .retail-note-detail { margin-top: 3px; color: #6b6258; font-size: 13px; }
.retail-note a { text-underline-offset: 4px; }
.retail-note-detail a { text-decoration: underline; }
.retail-note-link { white-space: nowrap; text-decoration: underline; }
@media (max-width: 640px) {
  .retail-note { width: calc(100% - 32px); padding: 18px 20px; flex-direction: column; align-items: flex-start; gap: 10px; }
}

/* One full-width photograph; the lace remains visible above the copy. */
.home-hero {position:relative;display:flex;align-items:center;min-height:520px;height:min(76svh,760px);background:#450812;isolation:isolate;}
.home-hero-photo {position:absolute;inset:0;display:block;overflow:hidden;z-index:-2;}
.home-hero-photo img {display:block;width:100%;height:100%;object-fit:cover;object-position:50% 42%;}
.home-hero::after {content:'';position:absolute;inset:0;z-index:-1;pointer-events:none;background:linear-gradient(90deg,rgba(35,0,8,.25),transparent 55%);}
.home-hero-copy {width:100%;max-width:1280px;margin:0 auto;padding:3rem 2rem;color:#fff;}
.home-wordmark {max-width:13ch;font-family:'Fraunces',Georgia,serif;font-size:clamp(2.2rem,4vw,3.8rem);font-weight:300;letter-spacing:-.025em;line-height:1.12;color:#fff;text-wrap:balance;}
.home-hero-description {font-size:.85rem;line-height:1.7;color:#f2eee6;margin:1rem 0 1.5rem;}
.home-hero-actions {display:flex;align-items:center;flex-wrap:wrap;gap:1rem 1.5rem;}
.home-hero-actions a {display:inline-flex;align-items:center;justify-content:center;min-height:46px;font-size:.8rem;}
.home-primary {background:#fff;color:#292822;padding:.8rem 1.5rem;}
.home-secondary {color:#fff;text-decoration:underline;text-underline-offset:5px;}

/* Photography-led layout with a shared rhythm for both sections. */
.home-families {padding:clamp(2.5rem,5vw,4rem) 0;background:#fff;}
.home-families-head {display:flex;justify-content:space-between;align-items:end;gap:1rem;margin-bottom:1.75rem;}
.home-families-title {font-family:'Fraunces',Georgia,serif;font-size:clamp(1.65rem,2.8vw,2.2rem);font-weight:300;line-height:1.15;letter-spacing:-.025em;}
.home-families-link {white-space:nowrap;font-size:.8rem;text-decoration:underline;text-underline-offset:5px;min-height:44px;display:inline-flex;align-items:center;}
.home-family-grid {display:grid;grid-template-columns:repeat(5,minmax(0,1fr));gap:1.25rem;}
.home-family-card {display:block;min-width:0;color:#302a24;}
.home-family-visual {display:block;overflow:hidden;background:#f7f6f3;}
.home-family-card img,.home-family-image-space {display:block;width:100%;height:auto;aspect-ratio:4/5;object-fit:contain;padding:.75rem;}
.home-family-copy {display:block;text-align:left;padding:1rem 0 .25rem;}
.home-family-copy strong {font-family:'Fraunces',Georgia,serif;font-size:1.35rem;font-weight:400;}
.home-featured {border:0;border-top:1px solid #eeeae4;background:#fff;padding:clamp(2.5rem,5vw,4rem) 0;}
.featured-window {overflow:hidden;}
.featured-track {display:flex;width:max-content;animation:selection-scroll 110s linear infinite;}
.featured-track.is-paused,.featured-window:focus-within .featured-track {animation-play-state:paused;}
.featured-group {display:flex;gap:1.25rem;padding-right:1.25rem;flex-shrink:0;}
.featured-product {display:block;width:260px;flex-shrink:0;background:#fff;}
.featured-product img,.featured-image-space {display:block;width:100%;height:260px;padding:.5rem;object-fit:contain;}
.featured-product h3 {display:flex;align-items:center;justify-content:flex-start;gap:.4rem;margin:0;padding:1rem 0;font-family:'Manrope','Segoe UI',sans-serif;font-size:.85rem;font-weight:500;line-height:1.5;}
.featured-product h3 > span {font-size:.7rem;color:#7c6c58;font-weight:400;}
@keyframes selection-scroll {to {transform:translateX(-50%);}}
@media(hover:hover) and (pointer:fine) {
  .featured-window:hover .featured-track {animation-play-state:paused;}
  .home-family-card:hover .home-family-copy {text-decoration:underline;text-underline-offset:5px;}
}
@media(max-width:640px){
  .home-hero{align-items:flex-end;min-height:560px;height:80svh;max-height:740px;}
  .home-hero-photo img{object-position:50% 0;}
  .home-hero::after{background:linear-gradient(0deg,rgba(35,0,8,.5),transparent 48%);}
  .home-hero-copy{padding:2rem 1.25rem;}
  .home-wordmark{max-width:20ch;font-size:clamp(1.9rem,7.8vw,2.65rem);letter-spacing:-.025em;}
  .home-hero-actions{gap:1rem;}
  .home-hero-description{margin:1rem 0 1.25rem;}
  .home-hero-actions a{padding:.85rem 1rem;}
  .home-families-head{align-items:end;gap:.75rem;}
  .home-family-grid{grid-template-columns:repeat(2,minmax(0,1fr));gap:1.75rem 1rem;}
  .home-family-card img,.home-family-image-space{aspect-ratio:1;padding:.65rem;}
  .home-family-copy{padding:.65rem 0;}
  .home-family-copy strong{font-size:1.15rem;}
  .featured-group{gap:.85rem;padding-right:.85rem;}
  .featured-product{width:190px;}
  .featured-product img,.featured-image-space{height:190px;padding:.65rem;}
  .featured-track{animation-duration:85s;}
}
@media(prefers-reduced-motion:reduce){
  .featured-track{animation:none;transform:none;width:max-content;}
  .featured-window{overflow-x:auto;}
  .featured-group[aria-hidden="true"]{display:none;}
}
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
