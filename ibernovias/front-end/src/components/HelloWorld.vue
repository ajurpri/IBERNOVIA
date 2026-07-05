<template>
  <div class="home-page">
    <section class="home-hero">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-14 sm:py-16 lg:py-20">
        <div class="home-hero-grid">
          <div>
            <p class="home-eyebrow">Ibernovia · Andujar</p>
            <h1 class="home-title">Coleccion para ceremonia y complemento</h1>
            <p class="home-intro">
              Una presentacion clara del catalogo de Ibernovia para consulta general, con acceso profesional reservado a empresas registradas.
            </p>

            <div class="home-actions">
              <router-link to="/tienda" class="home-primary-link">Ver catalogo</router-link>
              <router-link to="/acceso-empresarial" class="home-secondary-link">Acceso profesional</router-link>
              <button
                type="button"
                class="home-text-button"
                @click="openConcierge('Necesito ayuda con productos o acceso profesional')"
              >
                Consultar por chat
              </button>
            </div>
          </div>

          <div class="home-hero-image-card">
            <img
              src="/images/optimized/complemento-12-960.jpg"
              srcset="/images/optimized/complemento-12-480.jpg 480w, /images/optimized/complemento-12-960.jpg 960w, /images/optimized/complemento-12-1440.jpg 1440w"
              sizes="(max-width: 1024px) 100vw, 42vw"
              alt="Coleccion Ibernovia"
              class="home-hero-image"
              loading="eager"
              decoding="async"
              width="1200"
              height="800"
            />
          </div>
        </div>
      </div>
    </section>

    <section class="home-block">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12 sm:py-14">
        <div class="home-section-head">
          <div>
            <p class="home-section-label">La marca</p>
            <h2 class="home-section-title">Una web mas ordenada para presentar mejor la coleccion</h2>
          </div>
        </div>

        <div class="home-info-grid">
          <article class="home-info-card">
            <h3>Catalogo visible</h3>
            <p>La parte publica permite recorrer familias y referencias de forma sencilla.</p>
          </article>
          <article class="home-info-card">
            <h3>Acceso profesional</h3>
            <p>Las condiciones comerciales y las tarifas siguen reservadas a cuentas validadas.</p>
          </article>
          <article class="home-info-card">
            <h3>Contacto directo</h3>
            <p>La informacion principal de la empresa queda clara y accesible sin exceso de elementos.</p>
          </article>
        </div>
      </div>
    </section>

    <section id="coleccion" class="home-catalog">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-14 sm:py-16">
        <div class="home-section-head home-section-head-center">
          <div>
            <p class="home-section-label">Catalogo</p>
            <h2 class="home-section-title">Seleccion destacada</h2>
            <p class="home-section-copy">
              Una muestra inicial de referencias para recorrer la coleccion.
            </p>
          </div>
        </div>

        <div v-if="loading" class="text-center py-20">
          <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-luxury-gold border-t-transparent"></div>
          <p class="mt-4 text-gray-500 text-sm">Cargando productos...</p>
        </div>

        <div v-else-if="error" class="home-error-box">
          <p class="font-semibold">No se han podido cargar los productos.</p>
          <p class="text-sm mt-2">{{ errorDetalle }}</p>
        </div>

        <div v-else>
          <div class="home-filter-row">
            <button
              @click="cambiarFamilia('Todas')"
              :aria-pressed="familiaSeleccionada === 'Todas' ? 'true' : 'false'"
              :class="familiaSeleccionada === 'Todas' ? 'home-filter-active' : 'home-filter-button'"
              class="home-filter-base"
            >
              Todas
            </button>
            <button
              v-for="fam in familias"
              :key="fam"
              @click="cambiarFamilia(fam)"
              :aria-pressed="familiaSeleccionada === fam ? 'true' : 'false'"
              :class="familiaSeleccionada === fam ? 'home-filter-active' : 'home-filter-button'"
              class="home-filter-base"
            >
              {{ fam }}
            </button>
          </div>

          <div class="home-filter-row home-filter-row-secondary">
            <button
              @click="cambiarCategoria('Todos')"
              :aria-pressed="categoriaSeleccionada === 'Todos' ? 'true' : 'false'"
              :class="categoriaSeleccionada === 'Todos' ? 'home-filter-active' : 'home-filter-button'"
              class="home-filter-base"
            >
              Todas
            </button>
            <button
              v-for="cat in categorias"
              :key="cat"
              @click="cambiarCategoria(cat)"
              :aria-pressed="categoriaSeleccionada === cat ? 'true' : 'false'"
              :class="categoriaSeleccionada === cat ? 'home-filter-active' : 'home-filter-button'"
              class="home-filter-base"
            >
              {{ cat }}
            </button>
          </div>

          <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 md:gap-6">
            <ProductCard
              v-for="prod in productosVisibles"
              :key="prod.id"
              :producto="prod"
            />
          </div>

          <div v-if="hayMasProductos" class="text-center mt-10">
            <button @click="cargarMas" class="home-more-button">
              Ver mas productos ({{ productosRestantes }})
            </button>
          </div>
        </div>
      </div>
    </section>

    <section class="home-footer-block">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12 sm:py-14">
        <div class="home-footer-card">
          <div>
            <p class="home-section-label">Profesionales</p>
            <h2 class="home-section-title">Trabajamos con tiendas y empresas del sector</h2>
            <p class="home-section-copy">
              Si necesitas acceso comercial, puedes solicitar el alta desde el area profesional.
            </p>
          </div>
          <router-link to="/acceso-empresarial" class="home-primary-link">Solicitar acceso</router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { apiClient } from '../lib/api'
import ProductCard from '../components/ProductCard.vue'

const productos = ref([])
const loading = ref(true)
const error = ref(false)
const errorDetalle = ref('')

const familiaSeleccionada = ref('Todas')
const categoriaSeleccionada = ref('Todos')
const paginaActual = ref(1)
const productosPorPagina = 12

const familias = computed(() => {
  const set = new Set(productos.value.map((p) => p.familia).filter(Boolean))
  const list = Array.from(set)
  const preferred = ['Novia', 'Novio', 'Fiesta', 'Comunion', 'Comunion', 'Arras']

  return list.sort((a, b) => {
    const ia = preferred.indexOf(a)
    const ib = preferred.indexOf(b)
    if (ia !== -1 || ib !== -1) {
      return (ia === -1 ? 999 : ia) - (ib === -1 ? 999 : ib)
    }
    return a.localeCompare(b)
  })
})

const categorias = computed(() => {
  const base = familiaSeleccionada.value === 'Todas'
    ? productos.value
    : productos.value.filter((p) => p.familia === familiaSeleccionada.value)

  const counts = new Map()
  for (const p of base) {
    const cat = p?.categoria
    if (!cat) continue
    counts.set(cat, (counts.get(cat) || 0) + 1)
  }

  return Array.from(counts.entries())
    .sort((a, b) => (b[1] - a[1]) || a[0].localeCompare(b[0]))
    .slice(0, 8)
    .map(([cat]) => cat)
})

onMounted(async () => {
  try {
    loading.value = true
    const res = await apiClient.get('/api/productos')
    if (Array.isArray(res.data)) {
      productos.value = res.data
    } else {
      throw new Error('Formato de datos invalido')
    }
    loading.value = false
  } catch (e) {
    console.error('Error cargando productos:', e)
    error.value = true
    errorDetalle.value = e.message
    loading.value = false
  }
})

const cambiarFamilia = (fam) => {
  familiaSeleccionada.value = fam
  categoriaSeleccionada.value = 'Todos'
  paginaActual.value = 1
}

const cambiarCategoria = (cat) => {
  categoriaSeleccionada.value = cat
  paginaActual.value = 1
}

const productosFiltrados = computed(() => {
  const base = familiaSeleccionada.value === 'Todas'
    ? productos.value
    : productos.value.filter((p) => p.familia === familiaSeleccionada.value)

  if (categoriaSeleccionada.value === 'Todos') {
    return base
  }

  return base.filter((p) => p.categoria === categoriaSeleccionada.value)
})

const productosVisibles = computed(() => {
  const fin = paginaActual.value * productosPorPagina
  return productosFiltrados.value.slice(0, fin)
})

const hayMasProductos = computed(() => productosVisibles.value.length < productosFiltrados.value.length)

const productosRestantes = computed(() => productosFiltrados.value.length - productosVisibles.value.length)

const cargarMas = () => {
  paginaActual.value++
}

const openConcierge = (message) => {
  if (typeof window === 'undefined') return

  window.dispatchEvent(new CustomEvent('ibernovia:open-chat', {
    detail: {
      message,
      autoSend: true
    }
  }))
}
</script>

<style scoped>
.home-page {
  background: #fffdf9;
}

.home-hero {
  background: #f8f4ed;
  border-bottom: 1px solid rgb(var(--luxury-gold-rgb) / 0.12);
}

.home-hero-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.1fr) minmax(280px, 0.9fr);
  gap: 2rem;
  align-items: center;
}

.home-eyebrow,
.home-section-label {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.22em;
  text-transform: uppercase;
  color: var(--luxury-gold);
}

.home-title,
.home-section-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  color: var(--luxury-black);
}

.home-title {
  margin-top: 0.6rem;
  font-size: clamp(2.8rem, 5vw, 4.8rem);
  line-height: 0.95;
  max-width: 11ch;
}

.home-intro,
.home-section-copy {
  margin-top: 1rem;
  color: rgb(var(--luxury-black-rgb) / 0.68);
  line-height: 1.8;
  max-width: 58ch;
}

.home-actions {
  margin-top: 1.75rem;
  display: flex;
  flex-wrap: wrap;
  gap: 0.9rem;
}

.home-primary-link,
.home-secondary-link,
.home-text-button,
.home-more-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-height: 48px;
  padding: 0 1.35rem;
  border-radius: 999px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  text-decoration: none;
  transition: background 0.2s ease, color 0.2s ease, border-color 0.2s ease;
}

.home-primary-link,
.home-more-button {
  background: var(--luxury-black);
  color: white;
  border: none;
}

.home-secondary-link {
  border: 1px solid rgb(var(--luxury-black-rgb) / 0.18);
  color: var(--luxury-black);
}

.home-text-button {
  border: none;
  background: transparent;
  color: var(--luxury-black);
  padding-left: 0;
  padding-right: 0;
}

.home-hero-image-card {
  background: white;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  border-radius: 18px;
  padding: 0.9rem;
}

.home-hero-image {
  width: 100%;
  height: 100%;
  min-height: 340px;
  object-fit: cover;
  border-radius: 12px;
  display: block;
}

.home-block,
.home-footer-block {
  background: white;
}

.home-section-head {
  margin-bottom: 1.75rem;
}

.home-section-head-center {
  text-align: center;
}

.home-section-title {
  margin-top: 0.35rem;
  font-size: clamp(2rem, 4vw, 3.3rem);
  line-height: 1;
}

.home-info-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 1rem;
}

.home-info-card,
.home-footer-card,
.home-error-box {
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  border-radius: 16px;
  background: white;
}

.home-info-card {
  padding: 1.4rem;
}

.home-info-card h3 {
  font-weight: 700;
  color: var(--luxury-black);
}

.home-info-card p {
  margin-top: 0.5rem;
  color: rgb(var(--luxury-black-rgb) / 0.66);
  line-height: 1.7;
}

.home-catalog {
  background: #fcfaf6;
  border-top: 1px solid rgb(var(--luxury-gold-rgb) / 0.08);
  border-bottom: 1px solid rgb(var(--luxury-gold-rgb) / 0.08);
}

.home-filter-row {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 0.65rem;
  margin-bottom: 1rem;
}

.home-filter-row-secondary {
  margin-bottom: 2rem;
}

.home-filter-base {
  min-height: 42px;
  padding: 0 1rem;
  border-radius: 999px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  border: 1px solid rgb(var(--luxury-black-rgb) / 0.12);
}

.home-filter-button {
  background: white;
  color: rgb(var(--luxury-black-rgb) / 0.72);
}

.home-filter-active {
  background: var(--luxury-black);
  color: white;
  border-color: var(--luxury-black);
}

.home-error-box {
  max-width: 40rem;
  margin: 0 auto;
  padding: 1.25rem;
  text-align: center;
  color: #7a2f2f;
  background: rgb(254 239 239);
  border-color: rgb(230 185 185);
}

.home-footer-card {
  padding: 1.5rem;
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: center;
}

@media (max-width: 1024px) {
  .home-hero-grid,
  .home-info-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .home-footer-card {
    flex-direction: column;
    align-items: start;
  }

  .home-hero-image {
    min-height: 260px;
  }
}
</style>
