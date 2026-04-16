import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiClient } from '../lib/api'

// Códigos de acceso para empresas (en producción, esto vendría de un backend)
const VALID_BUSINESS_CODES = [
  'EMPRESA2025',
  'IBERNOVIA2025',
  'DEMO123'
]

export const useAuthStore = defineStore('auth', () => {
  const savedUser = JSON.parse(localStorage.getItem('user') || 'null')
  const savedToken = localStorage.getItem('token')
  const savedGuestBusinessAccess = localStorage.getItem('guest_business_access') === 'true'

  const user = ref(savedUser)
  const token = ref(savedToken)
  const isAuthenticated = ref(Boolean(savedToken && savedUser))
  const isAdmin = ref(Boolean(savedUser?.isAdmin === true))
  const isBusinessUser = ref(Boolean(savedUser?.isAdmin) || savedGuestBusinessAccess)
  const loading = ref(false)

  if (savedUser?.email) {
    const savedBusinessStatus = localStorage.getItem(`business_${savedUser.userId || savedUser.id}`)
    const savedUserIsAdmin = Boolean(savedUser.isAdmin === true)
    isAdmin.value = savedUserIsAdmin
    isBusinessUser.value = savedBusinessStatus === 'true' || savedUserIsAdmin || savedGuestBusinessAccess
  }

  const viewMode = computed(() => {
    if (isAdmin.value) return 'admin'
    if (isBusinessUser.value) return 'business'
    return 'public'
  })

  const canSeePrices = computed(() => isBusinessUser.value || isAdmin.value)
  const canRequestQuote = computed(() => isBusinessUser.value || isAdmin.value)
  const canAccessAdmin = computed(() => isAuthenticated.value && isAdmin.value)

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
    isAdmin.value = normalizedUser.isAdmin === true

    const savedBusinessStatus = localStorage.getItem(`business_${normalizedUser.userId}`)
    const hasGuestBusinessAccess = localStorage.getItem('guest_business_access') === 'true'
    isBusinessUser.value = savedBusinessStatus === 'true' || hasGuestBusinessAccess || isAdmin.value

    localStorage.setItem('token', authPayload.token)
    localStorage.setItem('user', JSON.stringify(normalizedUser))
  }

  const activateBusinessAccess = (code) => {
    if (VALID_BUSINESS_CODES.includes(code)) {
      localStorage.setItem('guest_business_access', 'true')
      if (user.value) {
        localStorage.setItem(`business_${user.value.userId || user.value.id}`, 'true')
      }
      isBusinessUser.value = true
      console.log('✅ Acceso para empresas activado')
      return true
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
    isBusinessUser.value = localStorage.getItem('guest_business_access') === 'true'
    console.log('✅ Logout successful')
  }

  const verifyAdminSession = async () => {
    if (!token.value) return false
    try {
      const response = await apiClient.get('/api/auth/verify')
      const payload = response.data

      const normalizedUser = {
        userId: payload.userId,
        email: payload.email,
        nombre: payload.nombre,
        apellido: payload.apellido,
        isAdmin: Boolean(payload.isAdmin)
      }

      user.value = normalizedUser
      isAuthenticated.value = true
      isAdmin.value = normalizedUser.isAdmin

      const savedBusinessStatus = localStorage.getItem(`business_${normalizedUser.userId}`)
      const hasGuestBusinessAccess = localStorage.getItem('guest_business_access') === 'true'
      isBusinessUser.value = savedBusinessStatus === 'true' || hasGuestBusinessAccess || isAdmin.value

      localStorage.setItem('user', JSON.stringify(normalizedUser))
      return isAdmin.value
    } catch (e) {
      await logout()
      return false
    }
  }

  return {
    user,
    token,
    isAuthenticated,
    isAdmin,
    isBusinessUser,
    viewMode,
    canSeePrices,
    canRequestQuote,
    canAccessAdmin,
    loading,
    setUser,
    verifyAdminSession,
    activateBusinessAccess,
    logout
  }
})

