import axios from 'axios'

const envBaseUrl = import.meta.env.VITE_API_URL
const rawBaseUrl =
  typeof envBaseUrl === 'string' && envBaseUrl.trim().length > 0
    ? envBaseUrl
    : import.meta.env.PROD
      ? ''
      : 'http://localhost:8080'

export const API_BASE_URL = rawBaseUrl.replace(/\/$/, '')

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
})

apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  const businessCode = localStorage.getItem('business_code')
  if (businessCode) {
    config.headers['X-Business-Code'] = businessCode
  }
  return config
})

export const apiFetch = (path, options = {}) => {
  const normalizedPath = path.startsWith('/') ? path : `/${path}`
  const token = localStorage.getItem('token')
  const businessCode = localStorage.getItem('business_code')
  
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {})
  }
  
  if (token) {
    headers['Authorization'] = `Bearer ${token}`
  }
  if (businessCode) {
    headers['X-Business-Code'] = businessCode
  }

  return fetch(`${API_BASE_URL}${normalizedPath}`, {
    ...options,
    headers
  })
}

// Cache de catálogo por tipo de acceso. Separar el ámbito público del
// profesional evita reutilizar precios al cerrar sesión en un equipo compartido.
const cache = new Map()
const pendingRequests = new Map()
const CACHE_TTL = 5 * 60 * 1000
const PRODUCT_CACHE_VERSION = 2

const getProductCacheScope = () => {
  try {
    if (localStorage.getItem('business_code')) return 'professional'
    const user = JSON.parse(localStorage.getItem('user') || 'null')
    return user?.isAdmin || user?.isBusiness ? 'professional' : 'public'
  } catch {
    return 'public'
  }
}

const getProductCacheKey = () => `api_productos_${getProductCacheScope()}`
const getPersistentProductCacheKey = () => `ibernovia_products_${getProductCacheScope()}_v${PRODUCT_CACHE_VERSION}`
const getProductStorage = () => getProductCacheScope() === 'professional' ? sessionStorage : localStorage

const readPersistentProducts = (allowExpired = false) => {
  try {
    const stored = getProductStorage().getItem(getPersistentProductCacheKey())
    if (!stored) return null

    const parsed = JSON.parse(stored)
    if (!Array.isArray(parsed?.data) || parsed.data.length === 0) return null
    if (!allowExpired && Date.now() - Number(parsed.timestamp || 0) >= CACHE_TTL) return null

    return parsed.data
  } catch {
    return null
  }
}

const persistProducts = (data, timestamp) => {
  try {
    getProductStorage().setItem(
      getPersistentProductCacheKey(),
      JSON.stringify({ version: PRODUCT_CACHE_VERSION, timestamp, data })
    )
  } catch {
    // Algunos navegadores o modos privados limitan localStorage.
  }
}

export const fetchCachedProducts = async (forceRefresh = false) => {
  const cacheKey = getProductCacheKey()
  const cached = cache.get(cacheKey)
  const now = Date.now()

  if (!forceRefresh && cached && (now - cached.timestamp < CACHE_TTL)) {
    return cached.data
  }

  if (!forceRefresh) {
    const persisted = readPersistentProducts()
    if (persisted) {
      cache.set(cacheKey, { timestamp: now, data: persisted })
      return persisted
    }
  }

  // Inicio, buscador y catálogo pueden pedir los productos a la vez. Todos
  // comparten una sola petición para no despertar el backend varias veces.
  if (pendingRequests.has(cacheKey)) {
    return pendingRequests.get(cacheKey)
  }

  const request = (async () => {
    try {
      const res = await apiClient.get('/api/productos')
      const data = Array.isArray(res.data) ? res.data : []
      if (data.length > 0) {
        const timestamp = Date.now()
        cache.set(cacheKey, { timestamp, data })
        persistProducts(data, timestamp)
      }
      return data
    } catch (error) {
      console.warn('Network error or server delay fetching products, attempting local backup:', error)

      if (cached?.data?.length > 0) {
        return cached.data
      }

      const persisted = readPersistentProducts(true)
      if (persisted) {
        cache.set(cacheKey, { timestamp: now, data: persisted })
        return persisted
      }

      throw error
    } finally {
      pendingRequests.delete(cacheKey)
    }
  })()

  pendingRequests.set(cacheKey, request)
  return request
}

export const invalidateProductsCache = () => {
  cache.clear()
  pendingRequests.clear()
  try {
    localStorage.removeItem('ibernovia_products_backup')
    for (const storage of [localStorage, sessionStorage]) {
      Object.keys(storage)
        .filter((key) => key.startsWith('ibernovia_products_'))
        .forEach((key) => storage.removeItem(key))
    }
  } catch {
    // Error de almacenamiento ignorado.
  }
}

export const getImageUrl = (img) => {
  if (!img) return '/logo.jpg'
  if (img.startsWith('http://') || img.startsWith('https://') || img.startsWith('data:')) {
    return img
  }
  const normalized = img.startsWith('/') ? img : '/' + img
  
  if (normalized.startsWith('/images/productos/') || normalized.startsWith('/images/promociones/')) {
    if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
      return `https://ibernovia.es${normalized}`
    }
    return normalized
  }
  return normalized
}
