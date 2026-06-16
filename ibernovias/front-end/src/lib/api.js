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

export const getImageUrl = (img) => {
  if (!img) return '/logo.jpg'
  if (img.startsWith('http://') || img.startsWith('https://') || img.startsWith('data:')) {
    return img
  }
  const normalized = img.startsWith('/') ? img : '/' + img
  // Solo las imágenes subidas por el admin se sirven desde el backend (Render)
  if (normalized.startsWith('/images/productos/')) {
    return `${API_BASE_URL}${normalized}`
  }
  // El catálogo estático se sirve desde el frontend (VPS)
  return normalized
}
