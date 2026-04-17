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
  return config
})

export const apiFetch = (path, options = {}) => {
  const normalizedPath = path.startsWith('/') ? path : `/${path}`
  return fetch(`${API_BASE_URL}${normalizedPath}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {})
    }
  })
}
