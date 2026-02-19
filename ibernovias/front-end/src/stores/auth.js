import { defineStore } from 'pinia'
import { ref } from 'vue'
import { 
  signOut,
  onAuthStateChanged
} from 'firebase/auth'
import { auth } from '../lib/firebase'

// Get admin emails from .env (con fallback)
const ADMIN_EMAILS = [
  'admin@ibernovia.com',
  'dam@ibernovia.com'
]

// Códigos de acceso para empresas (en producción, esto vendría de un backend)
const VALID_BUSINESS_CODES = [
  'EMPRESA2025',
  'IBERNOVIA2025',
  'DEMO123'
]

console.log('🔐 Auth Store Initialized with ADMIN_EMAILS:', ADMIN_EMAILS)

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const token = ref(null)
  const isAuthenticated = ref(false)
  const isAdmin = ref(false)
  const isBusinessUser = ref(false)
  const loading = ref(false)

  // Inicializar escuchador de auth
  onAuthStateChanged(auth, async (firebaseUser) => {
    if (firebaseUser) {
      const idToken = await firebaseUser.getIdToken()
      
      // Recuperar estado de usuario empresa del localStorage
      const savedBusinessStatus = localStorage.getItem(`business_${firebaseUser.uid}`)
      
      user.value = {
        id: firebaseUser.uid,
        email: firebaseUser.email,
        nombre: firebaseUser.displayName || 'Usuario'
      }
      token.value = idToken
      isAuthenticated.value = true
      isAdmin.value = ADMIN_EMAILS.includes(firebaseUser.email)
      isBusinessUser.value = savedBusinessStatus === 'true' || isAdmin.value
      
      console.log(`✅ User logged in: ${firebaseUser.email}`)
      console.log(`🔑 Is Admin: ${isAdmin.value}`)
      console.log(`🏢 Is Business User: ${isBusinessUser.value}`)
    } else {
      user.value = null
      token.value = null
      isAuthenticated.value = false
      isAdmin.value = false
      isBusinessUser.value = false
      console.log('❌ User logged out')
    }
    loading.value = false
  })

  const activateBusinessAccess = (code) => {
    if (VALID_BUSINESS_CODES.includes(code)) {
      if (user.value) {
        localStorage.setItem(`business_${user.value.id}`, 'true')
        isBusinessUser.value = true
        console.log('✅ Acceso para empresas activado')
        return true
      }
    }
    console.log('❌ Código inválido')
    return false
  }

  const logout = async () => {
    try {
      if (user.value) {
        localStorage.removeItem(`business_${user.value.id}`)
      }
      await signOut(auth)
      console.log('✅ Logout successful')
    } catch (error) {
      console.error('Error logout:', error)
    }
  }

  return {
    user,
    token,
    isAuthenticated,
    isAdmin,
    isBusinessUser,
    loading,
    activateBusinessAccess,
    logout
  }
})

