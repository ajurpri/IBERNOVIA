<template>
  <div class="product-page">
    <section class="product-section">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 sm:py-12 lg:py-14">
        <nav aria-label="Breadcrumb" class="mb-8">
          <ol class="product-breadcrumb">
            <li><router-link to="/">Inicio</router-link></li>
            <li>/</li>
            <li><router-link to="/tienda">Catalogo</router-link></li>
            <li v-if="producto?.familia">/</li>
            <li v-if="producto?.familia">
              <router-link :to="familyLink">{{ producto.familia }}</router-link>
            </li>
            <li v-if="producto">/</li>
            <li v-if="producto" class="product-breadcrumb-current">{{ producto.nombre }}</li>
          </ol>
        </nav>

        <div v-if="loading" class="py-20 text-center">
          <div class="inline-block h-12 w-12 animate-spin rounded-full border-4 border-luxury-gold border-t-transparent"></div>
        </div>

        <div v-else-if="error" class="product-message-box">
          <p class="product-message-title">Producto no encontrado</p>
          <p class="product-message-copy">No hemos podido cargar esta referencia en este momento.</p>
          <router-link to="/tienda" class="product-inline-link">Volver al catalogo</router-link>
        </div>

        <div v-else-if="producto" class="product-layout">
          <div class="product-image-panel">
            <div class="product-image-card">
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
                <p>Imagen no disponible</p>
              </div>
            </div>
          </div>

          <div class="product-info-panel">
            <p class="product-label">{{ productLabel }}</p>
            <h1 class="product-title">{{ producto.nombre }}</h1>
            <p class="product-intro">{{ introductionText }}</p>

            <div class="product-meta-list">
              <div class="product-meta-item">
                <span>Coleccion</span>
                <strong>{{ producto.familia || 'Catalogo Ibernovia' }}</strong>
              </div>
              <div class="product-meta-item">
                <span>Categoria</span>
                <strong>{{ producto.categoria || 'Complemento' }}</strong>
              </div>
              <div class="product-meta-item">
                <span>Disponibilidad</span>
                <strong>{{ stockLabel }}</strong>
              </div>
              <div class="product-meta-item">
                <span>Uso orientativo</span>
                <strong>{{ occasionLabel }}</strong>
              </div>
            </div>

            <div class="product-box">
              <template v-if="authStore.canSeePrices && producto.precio !== null && producto.precio !== undefined">
                <p class="product-box-label">Tarifa visible</p>
                <p class="product-price">{{ producto.precio }} EUR</p>
              </template>
              <template v-else>
                <p class="product-box-label">Acceso profesional</p>
                <p class="product-box-copy">
                  Las tarifas estan reservadas a cuentas profesionales autorizadas.
                </p>
                <router-link to="/acceso-empresarial" class="product-inline-link">Solicitar acceso profesional</router-link>
              </template>
            </div>

            <div v-if="producto.descripcion" class="product-box">
              <p class="product-box-label">Descripcion</p>
              <p class="product-box-copy">{{ producto.descripcion }}</p>
            </div>

            <div class="product-box">
              <p class="product-box-label">Informacion</p>
              <p class="product-box-copy">{{ atelierAdvice }}</p>
            </div>

            <div v-if="authStore.canRequestQuote" class="product-box">
              <div class="product-quantity-head">
                <div>
                  <p class="product-box-label">Cantidad</p>
                  <p class="product-box-copy">Ajusta las unidades antes de anadirlo al carrito.</p>
                </div>
                <span class="product-stock-pill" :class="producto.stock ? 'product-stock-pill-ok' : 'product-stock-pill-empty'">
                  {{ stockPillLabel }}
                </span>
              </div>

              <div class="product-quantity-controls">
                <button @click="cantidad = Math.max(1, cantidad - 1)" class="product-stepper-button" type="button">-</button>
                <input v-model.number="cantidad" type="number" min="1" class="product-quantity-input">
                <button @click="cantidad++" class="product-stepper-button" type="button">+</button>
              </div>

              <div v-if="producto.precio !== null && producto.precio !== undefined" class="product-action-grid">
                <button
                  @click="agregarCarrito"
                  :disabled="!producto.stock || agregando"
                  class="product-primary-button"
                  :aria-label="isInCart ? 'Producto ya en carrito' : 'Agregar al carrito'"
                >
                  <span v-if="!agregando">{{ isInCart ? 'Ya en carrito' : 'Agregar al carrito' }}</span>
                  <span v-else>Agregando...</span>
                </button>
                <router-link to="/carrito" class="product-secondary-button">Ver carrito</router-link>
              </div>
            </div>

            <div v-else class="product-box">
              <p class="product-box-label">Catalogo publico</p>
              <p class="product-box-copy">
                Si eres empresa o tienda especializada, solicita el alta para consultar tarifas y tramitar pedidos.
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
    </section>

    <section v-if="producto && relatedProducts.length" class="product-related-section">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12 sm:py-14">
        <div class="product-related-head">
          <div>
            <p class="product-box-label">Relacionados</p>
            <h2 class="product-related-title">Otras referencias de la misma linea</h2>
          </div>
          <router-link :to="familyLink" class="product-inline-link">Ver coleccion</router-link>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-6 gap-y-8">
          <ProductCard
            v-for="related in relatedProducts"
            :key="related.id"
            :producto="related"
          />
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

const isInCart = computed(() => !!cartStore.items.find((item) => item.id === producto.value?.id))

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
  return [producto.value.familia, producto.value.categoria].filter(Boolean).join(' / ') || 'Catalogo Ibernovia'
})

const occasionLabel = computed(() => {
  const family = fold(producto.value?.familia)
  if (family.includes('novia')) return 'Ceremonia y estilismo de novia'
  if (family.includes('fiesta')) return 'Invitada y evento'
  if (family.includes('comunion')) return 'Comunion y celebracion'
  if (family.includes('novio')) return 'Ceremonia y complemento de novio'
  if (family.includes('arras')) return 'Ceremonia y arras'
  return 'Asesoramiento en tienda'
})

const introductionText = computed(() => {
  if (!producto.value) return ''

  const category = producto.value.categoria || 'referencia'
  const family = producto.value.familia || 'coleccion'
  return `${producto.value.nombre} forma parte de la ${family.toLowerCase()} de Ibernovia y se presenta dentro de la categoria ${category.toLowerCase()}.`
})

const atelierAdvice = computed(() => {
  const family = fold(producto.value?.familia)
  if (family.includes('novia')) return 'Referencia pensada para acompanar estilismos de novia dentro de la coleccion.'
  if (family.includes('fiesta')) return 'Puede encajar en propuestas de invitada y ceremonia.'
  if (family.includes('comunion')) return 'Se integra en la linea de comunion y celebracion.'
  if (family.includes('novio')) return 'Pensado para completar una propuesta formal de ceremonia.'
  return 'Si necesitas orientacion comercial o de producto, puedes consultarnos directamente.'
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
.product-page {
  background: #fffdf9;
}

.product-section {
  background: white;
}

.product-breadcrumb {
  display: flex;
  flex-wrap: wrap;
  gap: 0.45rem;
  font-size: 11px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  color: rgb(var(--luxury-black-rgb) / 0.48);
}

.product-breadcrumb a:hover {
  color: var(--luxury-black);
}

.product-breadcrumb-current {
  color: var(--luxury-black);
  font-weight: 700;
}

.product-layout {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(320px, 0.9fr);
  gap: 2rem;
  align-items: start;
}

.product-image-card,
.product-box,
.product-message-box {
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  border-radius: 16px;
  background: white;
}

.product-image-card {
  padding: 0.9rem;
}

.product-main-image,
.product-image-fallback {
  width: 100%;
  min-height: 520px;
  object-fit: cover;
  border-radius: 12px;
  background: #f5f1ea;
}

.product-image-fallback {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: rgb(var(--luxury-black-rgb) / 0.48);
}

.product-label,
.product-box-label {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: var(--luxury-gold);
}

.product-title,
.product-related-title,
.product-message-title {
  margin-top: 0.55rem;
  font-family: 'Cormorant Garamond', Georgia, serif;
  color: var(--luxury-black);
}

.product-title {
  font-size: clamp(2.4rem, 5vw, 4rem);
  line-height: 0.96;
}

.product-intro,
.product-box-copy,
.product-message-copy {
  margin-top: 0.9rem;
  color: rgb(var(--luxury-black-rgb) / 0.68);
  line-height: 1.8;
}

.product-meta-list {
  margin-top: 1.5rem;
  display: grid;
  gap: 0.8rem;
}

.product-meta-item {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  padding-bottom: 0.75rem;
  border-bottom: 1px solid rgb(var(--luxury-gold-rgb) / 0.1);
}

.product-meta-item span {
  color: rgb(var(--luxury-black-rgb) / 0.56);
}

.product-meta-item strong {
  color: var(--luxury-black);
  text-align: right;
}

.product-box {
  margin-top: 1.25rem;
  padding: 1.2rem;
}

.product-price {
  margin-top: 0.45rem;
  font-size: 2rem;
  font-family: 'Cormorant Garamond', Georgia, serif;
  color: var(--luxury-black);
}

.product-inline-link {
  margin-top: 0.8rem;
  display: inline-flex;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  color: var(--luxury-black);
  text-decoration: underline;
  text-underline-offset: 3px;
}

.product-quantity-head {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: start;
}

.product-stock-pill {
  padding: 0.45rem 0.75rem;
  border-radius: 999px;
  font-size: 10px;
  letter-spacing: 0.14em;
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
  overflow: hidden;
  border-radius: 999px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.2);
}

.product-stepper-button,
.product-quantity-input {
  height: 46px;
  border: none;
  background: white;
  color: var(--luxury-black);
  text-align: center;
}

.product-quantity-input {
  border-left: 1px solid rgb(var(--luxury-gold-rgb) / 0.16);
  border-right: 1px solid rgb(var(--luxury-gold-rgb) / 0.16);
}

.product-action-grid {
  margin-top: 1rem;
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.75rem;
}

.product-primary-button,
.product-secondary-button {
  min-height: 48px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0 1.25rem;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  text-decoration: none;
}

.product-primary-button {
  border: none;
  background: var(--luxury-black);
  color: white;
}

.product-primary-button:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}

.product-secondary-button {
  border: 1px solid rgb(var(--luxury-black-rgb) / 0.16);
  color: var(--luxury-black);
  background: white;
}

.product-success-banner {
  margin-top: 1rem;
  padding: 1rem;
  border-radius: 12px;
  background: rgb(228 245 236);
  border: 1px solid rgb(150 205 170);
  color: #21553a;
}

.product-message-box {
  padding: 2rem 1.25rem;
  text-align: center;
}

.product-related-section {
  background: #faf7f1;
  border-top: 1px solid rgb(var(--luxury-gold-rgb) / 0.1);
}

.product-related-head {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: end;
  margin-bottom: 1.75rem;
}

.product-related-title {
  font-size: clamp(1.9rem, 4vw, 2.8rem);
  line-height: 1;
}

@media (max-width: 1024px) {
  .product-layout {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .product-main-image,
  .product-image-fallback {
    min-height: 340px;
  }

  .product-meta-item,
  .product-related-head,
  .product-quantity-head,
  .product-action-grid {
    grid-template-columns: 1fr;
    flex-direction: column;
    align-items: start;
  }

  .product-action-grid {
    display: grid;
  }

  .product-quantity-controls {
    width: 100%;
    grid-template-columns: 46px 1fr 46px;
  }
}
</style>
