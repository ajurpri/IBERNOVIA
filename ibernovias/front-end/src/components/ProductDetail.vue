<template>
  <div class="product-detail-shell">
    <section class="product-hero">
      <div class="product-hero-glow product-hero-glow-left"></div>
      <div class="product-hero-glow product-hero-glow-right"></div>

      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 sm:py-12 lg:py-16">
        <nav aria-label="Breadcrumb" class="mb-8 sm:mb-10">
          <ol class="flex flex-wrap items-center gap-2 text-[11px] uppercase tracking-[0.22em] text-black/45">
            <li>
              <router-link to="/" class="hover:text-luxury-black transition">Inicio</router-link>
            </li>
            <li aria-hidden="true">/</li>
            <li>
              <router-link to="/tienda" class="hover:text-luxury-black transition">Catalogo</router-link>
            </li>
            <li v-if="producto?.familia" aria-hidden="true">/</li>
            <li v-if="producto?.familia">
              <router-link :to="familyLink" class="hover:text-luxury-black transition">{{ producto.familia }}</router-link>
            </li>
            <li aria-hidden="true">/</li>
            <li v-if="producto" class="text-luxury-black font-semibold tracking-[0.16em]">{{ producto.nombre }}</li>
          </ol>
        </nav>

        <div v-if="loading" class="py-20 text-center">
          <div class="inline-block h-12 w-12 animate-spin rounded-full border-4 border-luxury-gold border-t-transparent"></div>
        </div>

        <div v-else-if="error" class="product-empty-state">
          <p class="product-empty-title">Producto no encontrado</p>
          <p class="product-empty-copy">No hemos podido cargar esta referencia en este momento.</p>
          <router-link to="/tienda" class="product-empty-link">Volver al catalogo</router-link>
        </div>

        <div v-else-if="producto" class="grid grid-cols-1 lg:grid-cols-[1.08fr_0.92fr] gap-10 xl:gap-16 items-start">
          <div class="space-y-5">
            <div class="product-visual-card">
              <div class="product-visual-inner">
                <img
                  v-if="producto.imagen && !imageError"
                  :src="getImageUrl(producto.imagen)"
                  :alt="producto.nombre"
                  @error="imageError = true"
                  class="product-main-image"
                  loading="lazy"
                  decoding="async"
                >
                <div v-else class="product-image-fallback">
                  <svg class="w-16 h-16 text-black/20 mb-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                  </svg>
                  <p class="text-xs uppercase tracking-[0.28em] text-black/45">Imagen no disponible</p>
                </div>

                <div class="product-visual-badge-wrap">
                  <span class="product-visual-badge">{{ producto.familia || 'Ibernovia' }}</span>
                  <span v-if="producto.categoria" class="product-visual-badge product-visual-badge-soft">{{ producto.categoria }}</span>
                </div>
              </div>
            </div>

            <div class="product-side-note">
              <div class="product-side-note-icon">01</div>
              <div>
                <p class="product-side-note-title">Seleccion de atelier</p>
                <p class="product-side-note-copy">
                  Referencia presentada con enfoque editorial para mostrar acabados, familia y uso dentro del catalogo profesional.
                </p>
              </div>
            </div>
          </div>

          <div class="product-copy-column">
            <div class="product-copy-card">
              <p class="product-kicker">{{ productLabel }}</p>
              <h1 class="product-title">{{ producto.nombre }}</h1>

              <p class="product-intro">
                {{ introductionText }}
              </p>

              <div class="product-meta-grid">
                <div class="product-meta-item">
                  <span class="product-meta-label">Coleccion</span>
                  <span class="product-meta-value">{{ producto.familia || 'Catalogo Ibernovia' }}</span>
                </div>
                <div class="product-meta-item">
                  <span class="product-meta-label">Categoria</span>
                  <span class="product-meta-value">{{ producto.categoria || 'Complemento' }}</span>
                </div>
                <div class="product-meta-item">
                  <span class="product-meta-label">Disponibilidad</span>
                  <span class="product-meta-value">{{ stockLabel }}</span>
                </div>
                <div class="product-meta-item">
                  <span class="product-meta-label">Uso recomendado</span>
                  <span class="product-meta-value">{{ occasionLabel }}</span>
                </div>
              </div>

              <div class="product-price-box">
                <template v-if="authStore.canSeePrices && producto.precio !== null && producto.precio !== undefined">
                  <p class="product-price-eyebrow">Tarifa visible</p>
                  <p class="product-price">{{ producto.precio }} EUR</p>
                  <p class="product-price-copy">Precio accesible para cuentas profesionales autorizadas.</p>
                </template>
                <template v-else>
                  <p class="product-price-eyebrow">Acceso profesional</p>
                  <p class="product-price-locked">Tarifas reservadas para empresas registradas</p>
                  <p class="product-price-copy">
                    Mantenemos el catalogo publico para inspiracion. El acceso profesional habilita precios, pedidos y seguimiento comercial.
                  </p>
                  <router-link to="/acceso-empresarial" class="product-inline-link">
                    Solicitar acceso comercial
                  </router-link>
                </template>
              </div>

              <div v-if="producto.descripcion" class="product-description-block">
                <p class="product-section-label">Descripcion</p>
                <p class="product-description">{{ producto.descripcion }}</p>
              </div>

              <div class="product-atelier-panel">
                <div>
                  <p class="product-section-label">Orientacion Ibernovia</p>
                  <p class="product-atelier-copy">
                    {{ atelierAdvice }}
                  </p>
                </div>
                <ul class="product-atelier-points">
                  <li>Atencion personalizada en tienda y soporte comercial.</li>
                  <li>Catalogo curado por familias para una busqueda mas agil.</li>
                  <li>Acceso profesional para consultar tarifas y gestionar pedidos.</li>
                </ul>
              </div>

              <div v-if="authStore.canRequestQuote" class="product-purchase-card">
                <div class="product-quantity-head">
                  <div>
                    <p class="product-section-label">Cantidad</p>
                    <p class="text-sm text-black/55">Ajusta las unidades antes de incorporarlo al carrito.</p>
                  </div>
                  <div class="product-stock-pill" :class="producto.stock ? 'product-stock-pill-ok' : 'product-stock-pill-empty'">
                    {{ stockPillLabel }}
                  </div>
                </div>

                <div class="product-quantity-controls">
                  <button @click="cantidad = Math.max(1, cantidad - 1)" class="product-stepper-button" type="button">-</button>
                  <input v-model.number="cantidad" type="number" min="1" class="product-quantity-input">
                  <button @click="cantidad++" class="product-stepper-button" type="button">+</button>
                </div>

                <div v-if="producto.precio !== null && producto.precio !== undefined" class="grid grid-cols-1 sm:grid-cols-2 gap-3">
                  <button
                    @click="agregarCarrito"
                    :disabled="!producto.stock || agregando"
                    class="product-primary-button"
                    :aria-label="isInCart ? 'Producto ya en carrito' : 'Agregar al carrito'"
                  >
                    <span v-if="!agregando">{{ isInCart ? 'Ya en carrito' : 'Agregar al carrito' }}</span>
                    <span v-else>Agregando...</span>
                  </button>
                  <router-link to="/carrito" class="product-secondary-button">
                    Ver carrito
                  </router-link>
                </div>
              </div>

              <div v-else class="product-public-box">
                <p class="product-public-title">Catalogo informativo para publico general</p>
                <p class="product-public-copy">
                  Si eres empresa o tienda especializada, solicita alta para consultar tarifas y tramitar pedidos con tu cuenta validada.
                </p>
              </div>

              <transition
                enter-active-class="transition duration-300 ease-out"
                enter-from-class="opacity-0 translate-y-2"
                enter-to-class="opacity-100 translate-y-0"
                leave-active-class="transition duration-300 ease-in"
                leave-from-class="opacity-100 translate-y-0"
                leave-to-class="opacity-0 translate-y-2"
              >
                <div v-if="agregado && authStore.canRequestQuote" class="product-success-banner">
                  <svg class="w-5 h-5 shrink-0" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
                  </svg>
                  <div>
                    <p class="font-semibold">Producto anadido correctamente</p>
                    <p class="text-xs text-black/60">
                      Cantidad: {{ cantidadAgregada }} · Total en carrito: {{ cartStore.items.find(i => i.id === producto.id)?.cantidad || 0 }}
                    </p>
                  </div>
                </div>
              </transition>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section v-if="producto && relatedProducts.length" class="product-related-section">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-14 sm:py-16">
        <div class="product-related-head">
          <div>
            <p class="product-section-label">Continuidad de coleccion</p>
            <h2 class="product-related-title">Piezas que mantienen la misma linea</h2>
          </div>
          <router-link :to="familyLink" class="product-inline-link">Ver coleccion completa</router-link>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-8 gap-y-10">
          <ProductCard
            v-for="related in relatedProducts"
            :key="related.id"
            :producto="related"
          />
        </div>
      </div>
    </section>

    <section v-if="producto" class="product-closing-section">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-14 sm:py-16">
        <div class="product-closing-card">
          <div>
            <p class="product-section-label">Atencion comercial</p>
            <h2 class="product-closing-title">Una referencia cuidada merece una presentacion igual de cuidada</h2>
            <p class="product-closing-copy">
              Ibernovia trabaja el catalogo como una herramienta comercial y visual. Si necesitas revisar esta familia con mas detalle, podemos orientarte desde tienda o mediante acceso profesional.
            </p>
          </div>
          <div class="product-closing-actions">
            <router-link to="/contacto" class="product-primary-button product-primary-button-link">
              Contactar con Ibernovia
            </router-link>
            <router-link to="/acceso-empresarial" class="product-secondary-button">
              Solicitar alta profesional
            </router-link>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, inject, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import { apiClient, getImageUrl } from '../lib/api'

const route = useRoute()
const cartStore = useCartStore()
const authStore = useAuthStore()
const toast = inject('toast')

const producto = ref(null)
const productos = ref([])
const loading = ref(true)
const error = ref(false)
const cantidad = ref(1)
const agregado = ref(false)
const agregando = ref(false)
const cantidadAgregada = ref(1)
const imageError = ref(false)

const fold = (value) => (value ?? '').toString().normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase()

const isInCart = computed(() => {
  return !!cartStore.items.find((item) => item.id === producto.value?.id)
})

const familyLink = computed(() => ({
  path: '/tienda',
  hash: '#catalogo',
  query: producto.value?.familia ? { familia: producto.value.familia } : {}
}))

const stockLabel = computed(() => {
  if (!producto.value) return ''
  if (!authStore.canSeePrices) return 'Consulta profesional'
  return producto.value.stock ? `Disponible (${producto.value.stock})` : 'Bajo consulta'
})

const stockPillLabel = computed(() => {
  if (!producto.value) return ''
  return producto.value.stock ? `Stock ${producto.value.stock}` : 'Sin stock'
})

const productLabel = computed(() => {
  if (!producto.value) return ''
  return [producto.value.familia, producto.value.categoria].filter(Boolean).join(' · ') || 'Catalogo Ibernovia'
})

const occasionLabel = computed(() => {
  const family = fold(producto.value?.familia)
  if (family.includes('novia')) return 'Ceremonia y estilismo de novia'
  if (family.includes('fiesta')) return 'Invitada, evento y ceremonia'
  if (family.includes('comunion')) return 'Comunion y celebracion infantil'
  if (family.includes('novio')) return 'Ceremonia y complemento de novio'
  if (family.includes('arras')) return 'Ceremonia y detalles de arras'
  return 'Asesoramiento personalizado'
})

const introductionText = computed(() => {
  if (!producto.value) return ''

  const category = producto.value.categoria || 'pieza'
  const family = producto.value.familia || 'coleccion'
  return `${producto.value.nombre} forma parte de la ${family.toLowerCase()} de Ibernovia y se presenta como ${category.toLowerCase()} dentro de una propuesta visual elegante, serena y comercial.`
})

const atelierAdvice = computed(() => {
  const family = fold(producto.value?.familia)
  if (family.includes('novia')) {
    return 'Ideal para completar un estilismo nupcial con una lectura delicada y coherente con el resto de la coleccion.'
  }
  if (family.includes('fiesta')) {
    return 'Encaja especialmente bien en propuestas de invitada o ceremonia donde el complemento debe aportar presencia sin perder equilibrio.'
  }
  if (family.includes('comunion')) {
    return 'Se integra en una linea de comunion cuidada, con enfoque luminoso y proporcionado para celebraciones especiales.'
  }
  if (family.includes('novio')) {
    return 'Funciona como detalle de ceremonia sobrio y bien medido, pensado para completar una imagen formal.'
  }
  return 'Referencia recomendable para ampliar la presentacion de coleccion y apoyar una venta guiada con mejor contexto visual.'
})

const relatedProducts = computed(() => {
  if (!producto.value) return []

  const currentId = producto.value.id
  const family = producto.value.familia
  const category = producto.value.categoria

  return productos.value
    .filter((item) => item.id !== currentId)
    .map((item) => {
      let score = 0
      if (family && item.familia === family) score += 5
      if (category && item.categoria === category) score += 3
      if (fold(item.nombre).includes(fold(producto.value.nombre))) score += 1
      return { item, score }
    })
    .filter((entry) => entry.score > 0)
    .sort((a, b) => b.score - a.score || (a.item.nombre || '').localeCompare(b.item.nombre || ''))
    .slice(0, 4)
    .map((entry) => entry.item)
})

const fetchData = async () => {
  loading.value = true
  error.value = false
  imageError.value = false
  cantidad.value = 1

  try {
    const id = route.params.id
    const [productResponse, productsResponse] = await Promise.all([
      apiClient.get(`/api/productos/${id}`),
      apiClient.get('/api/productos')
    ])

    producto.value = productResponse.data
    productos.value = Array.isArray(productsResponse.data) ? productsResponse.data : []
  } catch (e) {
    console.error('Error cargando producto:', e)
    error.value = true
  } finally {
    loading.value = false
  }
}

onMounted(fetchData)

watch(() => route.params.id, fetchData)

const agregarCarrito = async () => {
  if (!authStore.canRequestQuote || !producto.value || agregando.value) return

  agregando.value = true
  cantidadAgregada.value = cantidad.value

  cartStore.addItem(producto.value, cantidad.value)
  agregado.value = true

  if (toast) {
    toast.show(`${cantidad.value}x "${producto.value.nombre}" anadido(s) al carrito`, 'success', 2500)
  }

  setTimeout(() => {
    agregando.value = false
  }, 500)

  setTimeout(() => {
    agregado.value = false
  }, 4000)
}
</script>

<style scoped>
.product-detail-shell {
  background:
    radial-gradient(circle at top left, rgb(var(--luxury-gold-rgb) / 0.14), transparent 26%),
    linear-gradient(180deg, #f7f2ea 0%, #fbf8f3 20%, #ffffff 56%, #f8f4ed 100%);
}

.product-hero {
  position: relative;
  overflow: hidden;
}

.product-hero-glow {
  position: absolute;
  width: 30rem;
  height: 30rem;
  border-radius: 999px;
  filter: blur(70px);
  opacity: 0.22;
  pointer-events: none;
}

.product-hero-glow-left {
  top: -10rem;
  left: -10rem;
  background: rgb(var(--luxury-gold-rgb) / 0.7);
}

.product-hero-glow-right {
  top: 6rem;
  right: -12rem;
  background: rgb(var(--luxury-black-rgb) / 0.38);
}

.product-visual-card {
  position: relative;
  padding: 1rem;
  border-radius: 30px;
  background: linear-gradient(145deg, rgb(255 255 255 / 0.88), rgb(248 242 234 / 0.92));
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.18);
  box-shadow:
    0 30px 60px rgb(var(--luxury-black-rgb) / 0.08),
    inset 0 1px 0 rgb(255 255 255 / 0.85);
}

.product-visual-inner {
  position: relative;
  overflow: hidden;
  border-radius: 24px;
  background: linear-gradient(180deg, #f2ece4 0%, #fcfaf7 100%);
  min-height: 560px;
}

.product-main-image {
  width: 100%;
  height: 100%;
  min-height: 560px;
  object-fit: cover;
  display: block;
}

.product-image-fallback {
  min-height: 560px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.product-visual-badge-wrap {
  position: absolute;
  left: 1rem;
  right: 1rem;
  bottom: 1rem;
  display: flex;
  flex-wrap: wrap;
  gap: 0.6rem;
}

.product-visual-badge {
  display: inline-flex;
  align-items: center;
  min-height: 34px;
  padding: 0 0.95rem;
  border-radius: 999px;
  background: rgb(34 29 24 / 0.82);
  color: white;
  font-size: 10px;
  letter-spacing: 0.22em;
  text-transform: uppercase;
  backdrop-filter: blur(14px);
}

.product-visual-badge-soft {
  background: rgb(255 255 255 / 0.85);
  color: var(--luxury-black);
}

.product-copy-column {
  position: relative;
}

.product-copy-card {
  position: sticky;
  top: 5.5rem;
  border-radius: 30px;
  padding: 1.7rem;
  background: rgb(255 255 255 / 0.82);
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  box-shadow:
    0 24px 50px rgb(var(--luxury-black-rgb) / 0.07),
    inset 0 1px 0 rgb(255 255 255 / 0.88);
  backdrop-filter: blur(14px);
}

.product-kicker,
.product-section-label {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.28em;
  text-transform: uppercase;
  color: rgb(var(--luxury-gold-rgb) / 0.95);
}

.product-title {
  margin-top: 0.8rem;
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: clamp(2.5rem, 4vw, 4.35rem);
  line-height: 0.96;
  color: var(--luxury-black);
}

.product-intro {
  margin-top: 1rem;
  color: rgb(var(--luxury-black-rgb) / 0.68);
  font-size: 15px;
  line-height: 1.8;
  max-width: 54ch;
}

.product-meta-grid {
  margin-top: 1.5rem;
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.8rem;
}

.product-meta-item {
  padding: 0.9rem 1rem;
  border-radius: 18px;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.94), rgb(250 246 239 / 0.86));
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
}

.product-meta-label {
  display: block;
  font-size: 10px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  color: rgb(var(--luxury-black-rgb) / 0.45);
}

.product-meta-value {
  display: block;
  margin-top: 0.32rem;
  color: var(--luxury-black);
  font-size: 14px;
  line-height: 1.5;
}

.product-price-box,
.product-description-block,
.product-atelier-panel,
.product-purchase-card,
.product-public-box {
  margin-top: 1.3rem;
  padding: 1.2rem 1.25rem;
  border-radius: 22px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  background: linear-gradient(180deg, rgb(255 255 255 / 0.96), rgb(249 245 237 / 0.88));
}

.product-price-eyebrow {
  font-size: 10px;
  letter-spacing: 0.24em;
  text-transform: uppercase;
  color: rgb(var(--luxury-black-rgb) / 0.48);
}

.product-price {
  margin-top: 0.35rem;
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 2.2rem;
  color: var(--luxury-black);
}

.product-price-locked {
  margin-top: 0.35rem;
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 1.65rem;
  line-height: 1.12;
  color: var(--luxury-black);
}

.product-price-copy,
.product-description,
.product-public-copy,
.product-closing-copy,
.product-side-note-copy,
.product-atelier-copy {
  margin-top: 0.45rem;
  font-size: 14px;
  line-height: 1.75;
  color: rgb(var(--luxury-black-rgb) / 0.64);
}

.product-description {
  margin-top: 0.75rem;
}

.product-inline-link {
  margin-top: 0.75rem;
  display: inline-flex;
  align-items: center;
  gap: 0.45rem;
  color: var(--luxury-black);
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  text-decoration: none;
  transition: color 0.2s ease;
}

.product-inline-link:hover {
  color: var(--luxury-gold);
}

.product-atelier-panel {
  display: grid;
  gap: 0.9rem;
}

.product-atelier-points {
  display: grid;
  gap: 0.55rem;
  padding-left: 1rem;
  color: rgb(var(--luxury-black-rgb) / 0.72);
  font-size: 13px;
  line-height: 1.6;
}

.product-quantity-head {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: flex-start;
}

.product-stock-pill {
  padding: 0.5rem 0.8rem;
  border-radius: 999px;
  font-size: 10px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  white-space: nowrap;
}

.product-stock-pill-ok {
  background: rgb(46 125 89 / 0.12);
  color: #22573e;
}

.product-stock-pill-empty {
  background: rgb(129 38 38 / 0.09);
  color: #8c2d2d;
}

.product-quantity-controls {
  margin-top: 1rem;
  display: inline-grid;
  grid-template-columns: 46px 88px 46px;
  border-radius: 999px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.2);
  overflow: hidden;
  background: white;
}

.product-stepper-button,
.product-quantity-input {
  height: 46px;
  border: none;
  background: transparent;
  color: var(--luxury-black);
  text-align: center;
  outline: none;
}

.product-stepper-button {
  cursor: pointer;
  font-size: 1.1rem;
  transition: background 0.2s ease;
}

.product-stepper-button:hover {
  background: rgb(var(--luxury-gold-rgb) / 0.08);
}

.product-quantity-input {
  border-left: 1px solid rgb(var(--luxury-gold-rgb) / 0.16);
  border-right: 1px solid rgb(var(--luxury-gold-rgb) / 0.16);
  font-size: 15px;
}

.product-primary-button,
.product-secondary-button {
  min-height: 52px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0 1.4rem;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  text-decoration: none;
  transition: transform 0.2s ease, box-shadow 0.2s ease, background 0.2s ease, color 0.2s ease, border-color 0.2s ease;
}

.product-primary-button {
  border: none;
  background: linear-gradient(135deg, rgb(34 29 24), rgb(71 59 48));
  color: white;
  box-shadow: 0 18px 30px rgb(var(--luxury-black-rgb) / 0.14);
}

.product-primary-button:hover:not(:disabled),
.product-primary-button-link:hover {
  transform: translateY(-1px);
  box-shadow: 0 22px 34px rgb(var(--luxury-black-rgb) / 0.18);
}

.product-primary-button:disabled {
  opacity: 0.45;
  cursor: not-allowed;
  box-shadow: none;
}

.product-secondary-button {
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.28);
  color: var(--luxury-black);
  background: rgb(255 255 255 / 0.72);
}

.product-secondary-button:hover {
  transform: translateY(-1px);
  border-color: rgb(var(--luxury-gold-rgb) / 0.56);
  background: rgb(var(--luxury-gold-rgb) / 0.1);
}

.product-public-title,
.product-empty-title,
.product-side-note-title,
.product-related-title,
.product-closing-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  color: var(--luxury-black);
}

.product-public-title {
  font-size: 1.5rem;
}

.product-success-banner {
  margin-top: 1rem;
  display: flex;
  gap: 0.8rem;
  align-items: flex-start;
  padding: 0.95rem 1rem;
  border-radius: 18px;
  background: rgb(var(--luxury-gold-rgb) / 0.12);
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.26);
  color: var(--luxury-black);
}

.product-side-note {
  display: grid;
  grid-template-columns: 58px 1fr;
  gap: 0.9rem;
  align-items: start;
  padding: 1rem 1.1rem;
  border-radius: 22px;
  background: rgb(255 255 255 / 0.7);
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
}

.product-side-note-icon {
  width: 58px;
  height: 58px;
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(145deg, rgb(37 31 26), rgb(82 67 51));
  color: white;
  font-size: 12px;
  letter-spacing: 0.22em;
}

.product-related-section {
  border-top: 1px solid rgb(var(--luxury-gold-rgb) / 0.12);
}

.product-related-head {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: end;
  margin-bottom: 2rem;
}

.product-related-title {
  margin-top: 0.45rem;
  font-size: clamp(2rem, 3vw, 3rem);
  line-height: 1;
}

.product-closing-section {
  background: linear-gradient(180deg, rgb(253 250 244 / 0.65), rgb(248 242 233 / 0.92));
  border-top: 1px solid rgb(var(--luxury-gold-rgb) / 0.12);
}

.product-closing-card {
  display: grid;
  grid-template-columns: minmax(0, 1.15fr) minmax(280px, 0.85fr);
  gap: 1.4rem;
  align-items: center;
  padding: 1.6rem;
  border-radius: 30px;
  background:
    linear-gradient(135deg, rgb(31 26 22 / 0.98), rgb(63 50 39 / 0.95)),
    var(--luxury-black);
  color: white;
  box-shadow: 0 28px 50px rgb(var(--luxury-black-rgb) / 0.18);
}

.product-closing-card .product-section-label,
.product-closing-card .product-closing-title,
.product-closing-card .product-closing-copy {
  color: white;
}

.product-closing-card .product-closing-copy {
  opacity: 0.78;
}

.product-closing-actions {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
}

.product-closing-actions .product-secondary-button {
  background: transparent;
  color: white;
  border-color: rgb(255 255 255 / 0.26);
}

.product-closing-actions .product-secondary-button:hover {
  background: rgb(255 255 255 / 0.08);
}

.product-empty-state {
  padding: 3rem 1.5rem;
  text-align: center;
  border-radius: 28px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.15);
  background: rgb(255 255 255 / 0.72);
}

.product-empty-title {
  font-size: 2rem;
}

.product-empty-copy {
  margin-top: 0.55rem;
  color: rgb(var(--luxury-black-rgb) / 0.58);
}

.product-empty-link {
  margin-top: 1rem;
  display: inline-flex;
  color: var(--luxury-black);
  text-transform: uppercase;
  letter-spacing: 0.16em;
  font-size: 11px;
  font-weight: 700;
  text-decoration: none;
}

.product-empty-link:hover {
  color: var(--luxury-gold);
}

@media (max-width: 1024px) {
  .product-copy-card {
    position: static;
  }

  .product-visual-inner,
  .product-main-image,
  .product-image-fallback {
    min-height: 460px;
  }

  .product-closing-card {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .product-visual-card,
  .product-copy-card,
  .product-closing-card {
    border-radius: 24px;
  }

  .product-title {
    font-size: 2.55rem;
  }

  .product-meta-grid {
    grid-template-columns: 1fr;
  }

  .product-related-head,
  .product-quantity-head {
    flex-direction: column;
    align-items: flex-start;
  }

  .product-visual-inner,
  .product-main-image,
  .product-image-fallback {
    min-height: 360px;
  }

  .product-quantity-controls {
    width: 100%;
    grid-template-columns: 46px 1fr 46px;
  }
}
</style>
