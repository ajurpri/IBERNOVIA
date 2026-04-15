import { defineStore } from 'pinia'
import { ref } from 'vue'

// Get admin emails from .env (con fallback)
const ADMIN_EMAILS = (import.meta.env.VITE_ADMIN_EMAILS || 'admin@ibernovia.com,dam@ibernovia.com')
  .split(',')
  .map((email) => email.trim().toLowerCase())
  .filter(Boolean)

// Códigos de acceso para empresas (en producción, esto vendría de un backend)
const VALID_BUSINESS_CODES = [
  'EMPRESA2025',
  'IBERNOVIA2025',
  'DEMO123'
]

console.log('🔐 Auth Store Initialized with ADMIN_EMAILS:', ADMIN_EMAILS)

export const useAuthStore = defineStore('auth', () => {
  const savedUser = JSON.parse(localStorage.getItem('user') || 'null')
  const savedToken = localStorage.getItem('token')

  const user = ref(savedUser)
  const token = ref(savedToken)
  const isAuthenticated = ref(Boolean(savedToken && savedUser))
  const isAdmin = ref(Boolean(savedUser?.isAdmin))
  const isBusinessUser = ref(Boolean(savedUser?.isAdmin))
  const loading = ref(false)

  if (savedUser?.email) {
    const savedBusinessStatus = localStorage.getItem(`business_${savedUser.userId || savedUser.id}`)
    const savedUserIsAdmin = Boolean(savedUser.isAdmin) || ADMIN_EMAILS.includes((savedUser.email || '').toLowerCase())
    isAdmin.value = savedUserIsAdmin
    isBusinessUser.value = savedBusinessStatus === 'true' || savedUserIsAdmin
  }

  const setUser = (authPayload) => {
    const normalizedUser = {
      userId: authPayload.userId,
      email: authPayload.email,
      nombre: authPayload.nombre,
      apellido: authPayload.apellido,
      isAdmin: Boolean(authPayload.isAdmin)
    }

    user.value = normalizedUser
    token.value = authPayload.token
    isAuthenticated.value = true
    isAdmin.value = normalizedUser.isAdmin || ADMIN_EMAILS.includes((normalizedUser.email || '').toLowerCase())

    const savedBusinessStatus = localStorage.getItem(`business_${normalizedUser.userId}`)
    isBusinessUser.value = savedBusinessStatus === 'true' || isAdmin.value

    localStorage.setItem('token', authPayload.token)
    localStorage.setItem('user', JSON.stringify(normalizedUser))
  }

  const activateBusinessAccess = (code) => {
    if (VALID_BUSINESS_CODES.includes(code)) {
      if (user.value) {
        localStorage.setItem(`business_${user.value.userId || user.value.id}`, 'true')
        isBusinessUser.value = true
        console.log('✅ Acceso para empresas activado')
        return true
      }
    }
    console.log('❌ Código inválido')
    return false
  }

  const logout = async () => {
    if (user.value) {
      localStorage.removeItem(`business_${user.value.userId || user.value.id}`)
    }
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    user.value = null
    token.value = null
    isAuthenticated.value = false
    isAdmin.value = false
    isBusinessUser.value = false
    console.log('✅ Logout successful')
  }

  return {
    user,
    token,
    isAuthenticated,
    isAdmin,
    isBusinessUser,
    loading,
    setUser,
    activateBusinessAccess,
    logout
  }
})

