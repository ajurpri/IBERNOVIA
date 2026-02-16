import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const storedUser = localStorage.getItem('user')
  const user = ref(storedUser ? JSON.parse(storedUser) : null)
  const token = ref(localStorage.getItem('token') || null)
  const isAuthenticated = ref(!!token.value)
  const isAdmin = ref(!!user.value?.isAdmin)

  const setUser = (userData) => {
    user.value = {
      id: userData.userId,
      email: userData.email,
      nombre: userData.nombre,
      apellido: userData.apellido,
      isAdmin: userData.isAdmin
    }
    token.value = userData.token
    isAuthenticated.value = true
    isAdmin.value = !!userData.isAdmin
  }

  const logout = () => {
    user.value = null
    token.value = null
    isAuthenticated.value = false
    isAdmin.value = false
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  const clearError = () => {
    // Se usa en componentes para limpiar errores
  }

  return {
    user,
    token,
    isAuthenticated,
    isAdmin,
    setUser,
    logout,
    clearError
  }
})
