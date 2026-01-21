import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || null)
  const isAuthenticated = ref(!!token.value)

  const setUser = (userData) => {
    user.value = {
      id: userData.userId,
      email: userData.email,
      nombre: userData.nombre,
      apellido: userData.apellido
    }
    token.value = userData.token
    isAuthenticated.value = true
  }

  const logout = () => {
    user.value = null
    token.value = null
    isAuthenticated.value = false
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
    setUser,
    logout,
    clearError
  }
})
