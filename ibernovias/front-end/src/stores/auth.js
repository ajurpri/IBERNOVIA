import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiClient } from '../lib/api'


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
    const savedUserIsBusiness = Boolean(savedUser.isBusiness === true)
    isAdmin.value = savedUserIsAdmin
    isBusinessUser.value = savedBusinessStatus === 'true' || savedUserIsAdmin || savedUserIsBusiness || savedGuestBusinessAccess
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
      isAdmin: Boolean(authPayload.isAdmin),
      isBusiness: Boolean(authPayload.isBusiness)
    }

    user.value = normalizedUser
    token.value = authPayload.token
    isAuthenticated.value = true
    isAdmin.value = normalizedUser.isAdmin === true

    const savedBusinessStatus = localStorage.getItem(`business_${normalizedUser.userId}`)
    const hasGuestBusinessAccess = localStorage.getItem('guest_business_access') === 'true'
    isBusinessUser.value = savedBusinessStatus === 'true' || hasGuestBusinessAccess || isAdmin.value || normalizedUser.isBusiness === true

    localStorage.setItem('token', authPayload.token)
    localStorage.setItem('user', JSON.stringify(normalizedUser))
  }

  const activateBusinessAccess = async (code) => {
    try {
      const response = await apiClient.post('/api/auth/validar-codigo', { codigo: code })
      if (response.data && response.data.success === true) {
        localStorage.setItem('guest_business_access', 'true')
        localStorage.setItem('business_code', code)
        if (user.value) {
          localStorage.setItem(`business_${user.value.userId || user.value.id}`, 'true')
        }
        isBusinessUser.value = true
        console.log('✅ Acceso para empresas activado')
        return true
      }
      console.log('❌ Código inválido')
      return false
    } catch (e) {
      console.error('Error al validar código de empresa:', e)
      throw e
    }
  }

  const logout = async () => {
    if (user.value) {
      localStorage.removeItem(`business_${user.value.userId || user.value.id}`)
    }
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    localStorage.removeItem('guest_business_access')
    localStorage.removeItem('business_code')
    user.value = null
    token.value = null
    isAuthenticated.value = false
    isAdmin.value = false
    isBusinessUser.value = false
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
        isAdmin: Boolean(payload.isAdmin),
        isBusiness: Boolean(payload.isBusiness)
      }

      user.value = normalizedUser
      isAuthenticated.value = true
      isAdmin.value = normalizedUser.isAdmin

      const savedBusinessStatus = localStorage.getItem(`business_${normalizedUser.userId}`)
      const hasGuestBusinessAccess = localStorage.getItem('guest_business_access') === 'true'
      isBusinessUser.value = savedBusinessStatus === 'true' || hasGuestBusinessAccess || isAdmin.value || normalizedUser.isBusiness === true

      localStorage.setItem('user', JSON.stringify(normalizedUser))
      return isAdmin.value
    } catch (e) {
      await logout()
      return false
    }
  }

  const refreshUserSession = async () => {
    if (!token.value) return false
    try {
      const response = await apiClient.get('/api/auth/verify')
      const payload = response.data

      const normalizedUser = {
        userId: payload.userId,
        email: payload.email,
        nombre: payload.nombre,
        apellido: payload.apellido,
        isAdmin: Boolean(payload.isAdmin),
        isBusiness: Boolean(payload.isBusiness)
      }

      user.value = normalizedUser
      isAuthenticated.value = true
      isAdmin.value = normalizedUser.isAdmin

      const savedBusinessStatus = localStorage.getItem(`business_${normalizedUser.userId}`)
      const hasGuestBusinessAccess = localStorage.getItem('guest_business_access') === 'true'
      isBusinessUser.value = savedBusinessStatus === 'true' || hasGuestBusinessAccess || isAdmin.value || normalizedUser.isBusiness === true

      localStorage.setItem('user', JSON.stringify(normalizedUser))
      return true
    } catch (e) {
      console.error('Session refresh failed:', e)
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
    refreshUserSession,
    activateBusinessAccess,
    logout
  }
})

