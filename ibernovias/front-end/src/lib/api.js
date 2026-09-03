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

// In-memory cache for GET requests to prevent slow duplicate network calls
const cache = new Map()
const CACHE_TTL = 3 * 60 * 1000 // 3 minutos de caché

export const fetchCachedProducts = async (forceRefresh = false) => {
  const cacheKey = 'api_productos'
  const cached = cache.get(cacheKey)
  const now = Date.now()

  if (!forceRefresh && cached && (now - cached.timestamp < CACHE_TTL)) {
    return cached.data
  }

  try {
    const res = await apiClient.get('/api/productos')
    const data = Array.isArray(res.data) ? res.data : []
    if (data.length > 0) {
      cache.set(cacheKey, { timestamp: now, data })
      try {
        localStorage.setItem('ibernovia_products_backup', JSON.stringify(data))
      } catch (e) {
        // localStorage quotas or privacy mode error
      }
    }
    return data
  } catch (error) {
    console.warn('Network error or server delay fetching products, attempting local backup:', error)
    
    // 1. Return in-memory cache if available (even if expired)
    if (cached && cached.data && cached.data.length > 0) {
      return cached.data
    }

    // 2. Return persistent localStorage backup (resilient against Ctrl+F5)
    try {
      const stored = localStorage.getItem('ibernovia_products_backup')
      if (stored) {
        const parsed = JSON.parse(stored)
        if (Array.isArray(parsed) && parsed.length > 0) {
          cache.set(cacheKey, { timestamp: now, data: parsed })
          return parsed
        }
      }
    } catch (e) {
      // LocalStorage read error
    }

    throw error
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
