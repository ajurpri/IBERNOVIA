<template>
  <div class="min-h-screen bg-[#fcfaf6]">
    <section class="border-b border-black/5 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-14 sm:py-16">
        <div class="max-w-3xl">
          <p class="text-[10px] uppercase tracking-[0.35em] text-luxury-gold font-bold mb-4">Catalogo Ibernovia</p>
          <h1 class="font-serif text-4xl sm:text-5xl lg:text-6xl leading-[1.02] text-luxury-black mb-5">
            Coleccion para profesionales del sector ceremonia
          </h1>
          <p class="text-sm sm:text-base text-gray-600 leading-relaxed max-w-2xl">
            Consulta nuestras familias y categorias de producto. El catalogo esta visible para consulta general, y las tarifas quedan reservadas a clientes profesionales validados.
          </p>
        </div>
      </div>
    </section>

    <section id="catalogo" class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 sm:py-14">
      <div class="grid grid-cols-1 gap-6 border-b border-black/6 pb-8">
        <div>
          <label for="search" class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2">
            Buscar en catalogo
          </label>
          <div class="relative flex items-center">
            <input
              id="search"
              v-model="searchTerm"
              type="search"
              placeholder="Ej. velo, tocado, novia..."
              class="w-full h-12 pl-4 pr-12 rounded-2xl border border-gray-200 bg-[#fcfaf6] text-sm text-luxury-black placeholder-gray-400/70 focus:outline-none focus:border-luxury-gold transition-colors"
              aria-label="Buscar productos"
            >
            <div class="absolute right-3 flex items-center gap-2">
              <span v-if="isSearchingImage" class="animate-spin h-5 w-5 border-2 border-t-transparent border-luxury-gold rounded-full" role="status"></span>
              <label 
                v-else
                class="cursor-pointer text-gray-400 hover:text-luxury-gold transition-colors p-1"
                title="Buscar por imagen con IA"
              >
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M6.827 6.175A2.31 2.31 0 0 1 5.186 7.23c-.38.054-.757.112-1.134.175C2.999 7.58 2.25 8.507 2.25 9.574V18a2.25 2.25 0 0 0 2.25 2.25h15A2.25 2.25 0 0 0 21.75 18V9.574c0-1.067-.75-1.994-1.802-2.169a47.865 47.865 0 0 0-1.134-.175 2.31 2.31 0 0 1-1.64-1.055l-.822-1.316A2.192 2.192 0 0 0 14.68 4h-5.36a2.192 2.192 0 0 0-1.677.777l-.822 1.316Z" />
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                </svg>
                <input
                  type="file"
                  @change="handleImageSearchUpload"
                  accept="image/*"
                  class="hidden"
                >
              </label>
            </div>
          </div>
        </div>

        <div>
          <p class="text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-3">
            Familias
          </p>
          <div class="flex flex-wrap gap-x-5 gap-y-3">
            <button
              type="button"
              @click="selectedFamily = 'Todas'"
              class="text-sm transition-colors"
              :class="selectedFamily === 'Todas' ? 'text-luxury-black font-semibold' : 'text-gray-500 hover:text-luxury-black'"
            >
              Todas <span class="text-gray-400">({{ productos.length }})</span>
            </button>
            <button
              v-for="fam in familias"
              :key="fam"
              type="button"
              @click="selectedFamily = fam"
              class="text-sm transition-colors"
              :class="selectedFamily === fam ? 'text-luxury-black font-semibold' : 'text-gray-500 hover:text-luxury-black'"
            >
              {{ fam }} <span class="text-gray-400">({{ familyCounts[fam] || 0 }})</span>
            </button>
          </div>
        </div>

        <div v-if="selectedFamily !== 'Todas' && categorias.length">
          <p class="text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-3">
            Categorias dentro de {{ selectedFamily }}
          </p>
          <div class="flex flex-wrap gap-x-5 gap-y-3">
            <button
              type="button"
              @click="selectedCategory = 'Todos'"
              class="text-sm transition-colors"
              :class="selectedCategory === 'Todos' ? 'text-luxury-black font-semibold' : 'text-gray-500 hover:text-luxury-black'"
            >
              Todas <span class="text-gray-400">({{ familyCounts[selectedFamily] || 0 }})</span>
            </button>
            <button
              v-for="cat in categorias"
              :key="cat"
              type="button"
              @click="selectedCategory = cat"
              class="text-sm transition-colors"
              :class="selectedCategory === cat ? 'text-luxury-black font-semibold' : 'text-gray-500 hover:text-luxury-black'"
            >
              {{ cat }} <span class="text-gray-400">({{ categoryCounts[cat] || 0 }})</span>
            </button>
          </div>
        </div>

        <!-- Filtros adicionales y Ordenación -->
        <div class="flex flex-wrap items-center gap-x-6 gap-y-3 pt-4 border-t border-black/5 mt-2">
          <!-- Ordenar (Solo si puede ver precios) -->
          <div v-if="authStore.canSeePrices" class="flex items-center gap-2">
            <label for="sort-select" class="text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold">
              Ordenar por:
            </label>
            <select
              id="sort-select"
              v-model="sortBy"
              class="h-9 px-3 rounded-xl border border-gray-200 bg-white text-xs text-luxury-black focus:outline-none focus:border-luxury-gold transition-colors cursor-pointer"
            >
              <option value="default">Por defecto</option>
              <option value="price-asc">Precio: de menor a mayor</option>
              <option value="price-desc">Precio: de mayor a menor</option>
            </select>
          </div>

          <!-- Filtro Ofertas -->
          <div class="flex items-center gap-2">
            <label class="relative inline-flex items-center cursor-pointer select-none">
              <input 
                type="checkbox" 
                v-model="onlyOffers" 
                class="sr-only peer"
              >
              <div class="w-9 h-5 bg-gray-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-4 after:w-4 after:transition-all peer-checked:bg-luxury-gold"></div>
              <span class="ml-2.5 text-[10px] uppercase tracking-[0.25em] text-gray-500 font-bold peer-checked:text-luxury-gold transition-colors">
                Solo en oferta 🔥
              </span>
            </label>
          </div>
        </div>

        <div class="mt-2 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3 pt-2">
          <p class="text-[11px] uppercase tracking-[0.18em] text-gray-500 font-semibold" aria-live="polite">
            {{ visibleProducts.length }} productos encontrados
          </p>

          <div class="flex flex-wrap gap-4 items-center">
            <button
              type="button"
              @click="printCatalog"
              class="text-[11px] uppercase tracking-[0.18em] font-semibold text-luxury-black hover:text-luxury-gold transition-colors flex items-center gap-1.5"
            >
              📄 Descargar Catálogo (PDF)
            </button>

            <router-link
              to="/acceso-empresarial"
              class="text-[11px] uppercase tracking-[0.18em] font-semibold text-luxury-gold hover:text-luxury-black transition-colors"
            >
              Acceso profesional
            </router-link>

            <button
              v-if="searchTerm || selectedFamily !== 'Todas' || selectedCategory !== 'Todos' || sortBy !== 'default' || onlyOffers"
              type="button"
              @click="resetFilters"
              class="text-[11px] uppercase tracking-[0.18em] font-semibold text-luxury-black hover:text-luxury-gold transition-colors"
            >
              Limpiar filtros
            </button>
          </div>
        </div>
      </div>

      <div v-if="isLoading" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-8 gap-y-12 mt-10" role="status" aria-live="polite">
        <div v-for="n in 8" :key="n" class="aspect-[3/4] rounded-2xl bg-white border border-black/5 animate-pulse"></div>
      </div>

      <div v-else-if="loadError" class="mt-10 rounded-2xl border border-red-100 bg-red-50/60 p-6 text-red-700">
        <p class="font-semibold text-sm">No se pudo cargar el catalogo.</p>
        <p class="text-xs mt-1">{{ loadError }}</p>
        <button
          type="button"
          @click="fetchProductos"
          class="mt-4 px-6 py-2.5 text-[10px] uppercase tracking-widest bg-luxury-black text-white hover:bg-luxury-gold transition rounded-full"
        >
          Reintentar
        </button>
      </div>

      <div v-else class="mt-10">
        <div v-if="visibleProducts.length === 0" class="rounded-2xl border border-black/5 bg-white p-10 text-center">
          <p class="font-serif text-2xl text-luxury-black">No hay productos con esos filtros</p>
          <p class="text-sm text-gray-500 mt-2">Prueba otra busqueda o vuelve a la vista general.</p>
        </div>

        <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-x-8 gap-y-12 items-stretch">
          <ProductCard
            v-for="prod in paginatedProducts"
            :key="prod.id"
            :producto="prod"
          />
        </div>

        <!-- Botón Cargar Más -->
        <div v-if="visibleLimit < visibleProducts.length" class="text-center mt-12">
          <button
            type="button"
            @click="visibleLimit += 24"
            class="inline-flex items-center justify-center px-8 py-3.5 bg-luxury-black text-white hover:bg-luxury-gold hover:text-luxury-black font-bold uppercase tracking-widest text-[10px] rounded-full transition shadow-sm hover:shadow-md"
          >
            Cargar más productos
          </button>
        </div>
      </div>

      <div class="mt-14 pt-8 border-t border-black/6">
        <div class="flex flex-col lg:flex-row lg:items-end lg:justify-between gap-6">
          <div class="max-w-2xl">
            <p class="text-[10px] uppercase tracking-[0.3em] text-luxury-gold font-bold mb-3">Area profesional</p>
            <h2 class="font-serif text-3xl sm:text-4xl text-luxury-black mb-3">Tarifas y condiciones reservadas para clientes validados</h2>
            <p class="text-gray-600 text-sm leading-relaxed">
              Si eres empresa del sector, solicita acceso para consultar la informacion comercial disponible para perfiles profesionales.
            </p>
          </div>

          <div class="flex flex-col sm:flex-row gap-3">
            <router-link
              to="/acceso-empresarial"
              class="inline-flex items-center justify-center px-8 py-3 bg-luxury-black text-white font-bold uppercase tracking-widest text-xs rounded-full hover:bg-luxury-gold transition"
            >
              Solicitar alta
            </router-link>
            <router-link
              to="/contacto"
              class="inline-flex items-center justify-center px-8 py-3 border border-black/15 text-luxury-black font-bold uppercase tracking-widest text-xs rounded-full hover:border-luxury-gold transition"
            >
              Contacto
            </router-link>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch, inject } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import { apiClient, getImageUrl } from '../lib/api'
import { useAuthStore } from '../stores/auth'

const productos = ref([])
const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const isLoading = ref(true)
const loadError = ref('')
const searchTerm = ref('')
const isSearchingImage = ref(false)
const toast = inject('toast')

const handleImageSearchUpload = async (event) => {
  const file = event.target.files?.[0]
  if (!file) return
  
  isSearchingImage.value = true
  if (toast) toast.show('Analizando imagen con IA...', 'info', 3000)
  
  try {
    const base64Image = await compressImageForSearch(file)
    const res = await apiClient.post('/api/productos/buscar-por-imagen', {
      image: base64Image
    })
    
    if (res.data && res.data.keywords) {
      searchTerm.value = res.data.keywords
      const el = document.getElementById('catalogo')
      if (el) {
        el.scrollIntoView({ behavior: 'smooth' })
      }
      if (toast) toast.show(`Búsqueda IA: "${res.data.keywords}"`, 'success', 2500)
    } else {
      throw new Error('No se pudieron extraer términos de búsqueda.')
    }
  } catch (error) {
    if (toast) toast.show('✗ Error en búsqueda visual IA', 'error', 3000)
  } finally {
    isSearchingImage.value = false
    event.target.value = ''
  }
}

const compressImageForSearch = (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = (e) => {
      const img = new Image()
      img.src = e.target.result
      img.onload = () => {
        const canvas = document.createElement('canvas')
        const max_dim = 600
        let w = img.width
        let h = img.height
        
        if (w > max_dim || h > max_dim) {
          if (w > h) {
            h = Math.round((h * max_dim) / w)
            w = max_dim
          } else {
            w = Math.round((w * max_dim) / h)
            h = max_dim
          }
        }
        
        canvas.width = w
        canvas.height = h
        const ctx = canvas.getContext('2d')
        ctx.drawImage(img, 0, 0, w, h)
        resolve(canvas.toDataURL('image/jpeg', 0.75))
      }
      img.onerror = reject
    }
    reader.onerror = reject
  })
}

const printCatalog = () => {
  const productsToPrint = visibleProducts.value
  if (productsToPrint.length === 0) {
    if (toast) toast.show('No hay productos para exportar', 'warning', 2500)
    return
  }
  
  const printWindow = window.open('', '_blank')
  if (!printWindow) {
    if (toast) toast.show('✗ El navegador bloqueó la ventana emergente. Por favor, permítela.', 'error', 3000)
    return
  }
  
  const productCardsHtml = productsToPrint.map(p => {
    const imageUrl = getImageUrl(p.imagen)
    const precioHtml = authStore.canSeePrices && p.precio 
      ? `<div class="price">${p.enOferta && p.precioOferta ? p.precioOferta : p.precio} €</div>`
      : ''
    const refHtml = p.nombre ? `<div class="ref">Ref: ${p.nombre}</div>` : ''
    
    return `
      <div class="card">
        <div class="img-wrapper">
          <img src="${imageUrl}" alt="${p.nombre || 'Producto'}">
        </div>
        <div class="info">
          <div class="name">${p.nombre || 'Sin nombre'}</div>
          <div class="meta">${p.familia || ''} - ${p.categoria || ''}</div>
          ${refHtml}
          ${precioHtml}
        </div>
      </div>
    `
  }).join('')
  
  const htmlContent = `
    <!DOCTYPE html>
    <html lang="es">
    <head>
      <meta charset="UTF-8">
      <title>Catálogo Ibernovia - ${selectedFamily.value}</title>
      <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600&family=Playfair+Display:ital,wght@0,400;0,700;1,400&display=swap" rel="stylesheet">
      <style>
        body {
          font-family: 'Outfit', sans-serif;
          color: #1a1a1a;
          margin: 0;
          padding: 20px;
          background: #ffffff;
        }
        header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          border-bottom: 2px solid #1a1a1a;
          padding-bottom: 15px;
          margin-bottom: 30px;
        }
        .logo {
          font-family: 'Playfair Display', serif;
          font-size: 24px;
          font-weight: 700;
          letter-spacing: 2px;
          text-transform: uppercase;
        }
        .catalog-title {
          font-size: 14px;
          text-transform: uppercase;
          letter-spacing: 1.5px;
          color: #666;
        }
        .grid {
          display: grid;
          grid-template-columns: repeat(3, 1fr);
          gap: 20px;
        }
        .card {
          border: 1px solid #eaeaea;
          border-radius: 8px;
          padding: 10px;
          display: flex;
          flex-direction: column;
          page-break-inside: avoid;
          background: #fff;
        }
        .img-wrapper {
          aspect-ratio: 3/4;
          overflow: hidden;
          background: #fcfcfc;
          border-radius: 4px;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        .img-wrapper img {
          max-width: 100%;
          max-height: 100%;
          object-fit: cover;
        }
        .info {
          margin-top: 10px;
          text-align: center;
        }
        .name {
          font-family: 'Playfair Display', serif;
          font-size: 16px;
          font-weight: 700;
          color: #1a1a1a;
        }
        .meta {
          font-size: 10px;
          text-transform: uppercase;
          letter-spacing: 1px;
          color: #888;
          margin: 4px 0;
        }
        .ref {
          font-size: 11px;
          color: #444;
        }
        .price {
          font-size: 14px;
          font-weight: 600;
          color: #b89244;
          margin-top: 5px;
        }
        footer {
          margin-top: 50px;
          border-top: 1px solid #eaeaea;
          padding-top: 15px;
          text-align: center;
          font-size: 10px;
          color: #888;
        }
        @media print {
          body {
            padding: 0;
          }
          .card {
            border: none;
            padding: 0;
            margin-bottom: 20px;
          }
          @page {
            margin: 1.5cm;
          }
        }
      </style>
    </head>
    <body>
      <header>
        <div class="logo">IBERNOVIA</div>
        <div class="catalog-title">Colección: ${selectedFamily.value} / ${selectedCategory.value}</div>
      </header>
      
      <div class="grid">
        ${productCardsHtml}
      </div>
      
      <footer>
        <p>Ibernovia Atelier - Plaza de Abastos, C. Ollerias, 48, 23740 Andújar, Jaén - Tlf: 953 51 50 70 - info@ibernovia.es</p>
        <p>Documento generado dinámicamente como catálogo comercial de referencia.</p>
      </footer>
      
      ` + `<script>` + `
        window.addEventListener('load', () => {
          setTimeout(() => {
            window.print();
            window.close();
          }, 600);
        });
      ` + `</` + `script>` + `
    </body>
    </html>
  `
  
  printWindow.document.open()
  printWindow.document.write(htmlContent)
  printWindow.document.close()
}
const selectedFamily = ref('Todas')
const selectedCategory = ref('Todos')
const sortBy = ref('default')
const onlyOffers = ref(false)
const syncFromRoute = ref(false)
const visibleLimit = ref(24)

const paginatedProducts = computed(() => {
  return visibleProducts.value.slice(0, visibleLimit.value)
})

const familyOrder = ['novia', 'novio', 'fiesta', 'comunion', 'arras']

const fold = (v) => (v ?? '').toString()
  .replace(/\uFFFD/g, '')
  .normalize('NFD')
  .replace(/[\u0300-\u036f]/g, '')
  .toLowerCase()

const normalizeForSearch = (v) => fold(v)
  .replace(/[^a-z0-9]+/g, ' ')
  .trim()
  .replace(/\s+/g, ' ')

const familySortIndex = (family) => {
  const index = familyOrder.indexOf(fold(family))
  return index === -1 ? 999 : index
}

const familias = computed(() => {
  const set = new Set(productos.value.map((p) => p.familia).filter(Boolean))
  return Array.from(set).sort((a, b) => {
    const diff = familySortIndex(a) - familySortIndex(b)
    if (diff !== 0) return diff
    return a.localeCompare(b)
  })
})

const categorias = computed(() => {
  const list = selectedFamily.value === 'Todas'
    ? productos.value
    : productos.value.filter((p) => p.familia === selectedFamily.value)

  const set = new Set(list.map((p) => p.categoria).filter(Boolean))
  return Array.from(set).sort()
})

const familyCounts = computed(() => {
  return productos.value.reduce((acc, product) => {
    if (product?.familia) {
      acc[product.familia] = (acc[product.familia] || 0) + 1
    }
    return acc
  }, {})
})

const categoryCounts = computed(() => {
  const base = selectedFamily.value === 'Todas'
    ? productos.value
    : productos.value.filter((p) => p.familia === selectedFamily.value)

  return base.reduce((acc, product) => {
    if (product?.categoria) {
      acc[product.categoria] = (acc[product.categoria] || 0) + 1
    }
    return acc
  }, {})
})

watch(selectedFamily, () => {
  if (selectedCategory.value !== 'Todos' && !categorias.value.includes(selectedCategory.value)) {
    selectedCategory.value = 'Todos'
  }
})

const scoredProducts = computed(() => {
  const term = normalizeForSearch(searchTerm.value)
  const rawTokens = term ? term.split(' ') : []
  const tokens = rawTokens.filter((t) => t.length >= 2 || /^\d+$/.test(t))

  const base = productos.value.filter((p) => {
    const matchesFamily = selectedFamily.value === 'Todas'
      ? true
      : p.familia === selectedFamily.value

    const matchesCategory = selectedCategory.value === 'Todos'
      ? true
      : p.categoria === selectedCategory.value

    const matchesOnlyOffers = onlyOffers.value
      ? p.enOferta === true
      : true

    return matchesFamily && matchesCategory && matchesOnlyOffers
  })

  if (tokens.length === 0) {
    return base.map((p) => ({ producto: p, score: 0 }))
  }

  const tokenMatches = (hay, hayCompact, t) => {
    if (hay.includes(t) || hayCompact.includes(t)) return true

    if (t.length >= 6) {
      for (let i = 0; i < t.length; i++) {
        const variant = t.slice(0, i) + t.slice(i + 1)
        if (variant.length >= 5 && hayCompact.includes(variant)) return true
      }
    }
    return false
  }

  const strict = []
  const loose = []

  for (const p of base) {
    const haystack = normalizeForSearch(`${p.nombre || ''} ${p.familia || ''} ${p.categoria || ''} ${p.imagen || ''}`)
    const haystackCompact = haystack.replace(/\s+/g, '')

    let matched = 0
    for (const t of tokens) {
      if (tokenMatches(haystack, haystackCompact, t)) matched++
    }

    if (matched === tokens.length) {
      strict.push({ producto: p, score: 1000 + matched + (haystack.includes(term) ? 10 : 0) })
    } else if (matched > 0) {
      loose.push({ producto: p, score: matched + (haystack.includes(term) ? 3 : 0) })
    }
  }

  return strict.length ? strict : loose
})

const visibleProducts = computed(() => {
  const term = normalizeForSearch(searchTerm.value)
  const hasTerm = term.length > 0
  const list = scoredProducts.value.map((x) => x.producto)

  if (sortBy.value === 'price-asc') {
    list.sort((a, b) => {
      const priceA = a.enOferta && a.precioOferta != null ? a.precioOferta : (a.precio || 0)
      const priceB = b.enOferta && b.precioOferta != null ? b.precioOferta : (b.precio || 0)
      return priceA - priceB
    })
    return list
  } else if (sortBy.value === 'price-desc') {
    list.sort((a, b) => {
      const priceA = a.enOferta && a.precioOferta != null ? a.precioOferta : (a.precio || 0)
      const priceB = b.enOferta && b.precioOferta != null ? b.precioOferta : (b.precio || 0)
      return priceB - priceA
    })
    return list
  }

  const scoredList = scoredProducts.value.map((x) => x)
  if (hasTerm) {
    scoredList.sort((a, b) => {
      if (b.score !== a.score) return b.score - a.score
      const familyDiff = familySortIndex(a.producto.familia) - familySortIndex(b.producto.familia)
      if (familyDiff !== 0) return familyDiff
      const categoryDiff = (a.producto.categoria || '').localeCompare(b.producto.categoria || '')
      if (categoryDiff !== 0) return categoryDiff
      return (a.producto.nombre || '').localeCompare(b.producto.nombre || '')
    })
    return scoredList.map((x) => x.producto)
  }

  return scoredList
    .map((x) => x.producto)
    .sort((a, b) => {
      const familyDiff = familySortIndex(a.familia) - familySortIndex(b.familia)
      if (familyDiff !== 0) return familyDiff
      const categoryDiff = (a.categoria || '').localeCompare(b.categoria || '')
      if (categoryDiff !== 0) return categoryDiff
      return (a.nombre || '').localeCompare(b.nombre || '')
    })
})

const applyQueryFilters = () => {
  syncFromRoute.value = true

  const q = route.query
  const querySearch = typeof q.q === 'string' ? q.q : ''
  const queryFamily = typeof q.familia === 'string' ? q.familia : 'Todas'
  const queryCategory = typeof q.categoria === 'string' ? q.categoria : 'Todos'
  const querySortBy = typeof q.orden === 'string' ? q.orden : 'default'
  const queryOnlyOffers = q.ofertas === 'true'

  searchTerm.value = querySearch
  selectedFamily.value = familias.value.includes(queryFamily) ? queryFamily : 'Todas'
  selectedCategory.value = categorias.value.includes(queryCategory) ? queryCategory : 'Todos'
  sortBy.value = ['default', 'price-asc', 'price-desc'].includes(querySortBy) ? querySortBy : 'default'
  onlyOffers.value = queryOnlyOffers

  syncFromRoute.value = false
}

const updateRouteQuery = () => {
  if (syncFromRoute.value) return

  const query = {}
  if (searchTerm.value.trim()) query.q = searchTerm.value.trim()
  if (selectedFamily.value !== 'Todas') query.familia = selectedFamily.value
  if (selectedCategory.value !== 'Todos') query.categoria = selectedCategory.value
  if (sortBy.value !== 'default') query.orden = sortBy.value
  if (onlyOffers.value) query.ofertas = 'true'

  router.replace({
    path: route.path,
    hash: route.hash || '#catalogo',
    query
  })
}

const resetFilters = () => {
  searchTerm.value = ''
  selectedFamily.value = 'Todas'
  selectedCategory.value = 'Todos'
  visibleLimit.value = 24
  sortBy.value = 'default'
  onlyOffers.value = false
}

const fetchProductos = async () => {
  isLoading.value = true
  loadError.value = ''
  try {
    const res = await apiClient.get('/api/productos')
    productos.value = Array.isArray(res.data) ? res.data : []
  } catch (error) {
    loadError.value = error?.message || 'Error desconocido'
    console.error('Error cargando productos:', error)
  } finally {
    isLoading.value = false
  }
}

onMounted(async () => {
  await fetchProductos()
  applyQueryFilters()
})

watch(() => route.query, () => {
  if (isLoading.value) return
  applyQueryFilters()
}, { deep: true })

watch([searchTerm, selectedFamily, selectedCategory, sortBy, onlyOffers], () => {
  visibleLimit.value = 24
  if (isLoading.value) return
  updateRouteQuery()
})
</script>
