<template>
  <div class="min-h-screen bg-[#fdfdfc] flex items-center justify-center py-20 px-4 sm:px-6 lg:px-8">
    <div class="w-full max-w-md space-y-10 animate-fade-in-up">
      <div class="text-center space-y-3">
        <span class="text-[10px] uppercase tracking-[0.4em] text-luxury-gold font-bold block">ATELIER IBERNOVIA</span>
        <h1 class="font-serif text-4xl md:text-5xl font-light text-luxury-black tracking-wide">Mi Cuenta</h1>
        <p class="text-xs text-gray-400 uppercase tracking-widest">Acceso Clientes y Profesionales</p>
      </div>

      <div class="bg-white border border-black/5 p-8 md:p-12 space-y-8 shadow-sm">
        <div class="flex border-b border-black/5 pb-4">
          <button
            @click="activeTab = 'login'"
            :class="[
              'flex-1 text-center pb-2 text-[11px] font-bold uppercase tracking-[0.2em] transition-colors duration-300 border-b-2',
              activeTab === 'login'
                ? 'border-luxury-gold text-luxury-black'
                : 'border-transparent text-gray-400 hover:text-luxury-black'
            ]">
            Iniciar Sesion
          </button>
          <button
            @click="activeTab = 'register'"
            :class="[
              'flex-1 text-center pb-2 text-[11px] font-bold uppercase tracking-[0.2em] transition-colors duration-300 border-b-2',
              activeTab === 'register'
                ? 'border-luxury-gold text-luxury-black'
                : 'border-transparent text-gray-400 hover:text-luxury-black'
            ]">
            Crear Cuenta
          </button>
        </div>

        <div v-if="errorGlobal" class="p-4 bg-red-50/50 border-l-2 border-red-500 text-red-700 text-xs tracking-wide">
          {{ errorGlobal }}
        </div>
        <div v-if="successMessage" class="p-4 bg-luxury-gold/5 border-l-2 border-luxury-gold text-luxury-black text-xs tracking-wide">
          {{ successMessage }}
        </div>

        <form v-if="activeTab === 'login'" @submit.prevent="handleLogin" class="space-y-8">
          <div class="relative group">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
              Email
            </label>
            <input
              v-model="loginForm.email"
              type="email"
              required
              autocomplete="email"
              class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
              placeholder="introduce tu email">
          </div>

          <div class="relative group">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
              Contrasena
            </label>
            <input
              v-model="loginForm.password"
              type="password"
              required
              autocomplete="current-password"
              class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
              placeholder="********">
          </div>

          <div class="text-right">
            <span class="text-[10px] text-gray-400 font-bold uppercase tracking-wider">
              Acceso seguro
            </span>
          </div>

          <button
            type="submit"
            :disabled="loadingLogin"
            class="w-full py-4 bg-luxury-black text-white hover:bg-luxury-gold hover:text-white text-[11px] font-bold uppercase tracking-[0.25em] transition-all duration-300 rounded-full shadow-md hover:shadow-lg disabled:opacity-50 mt-6">
            {{ loadingLogin ? 'Accediendo...' : 'Acceder' }}
          </button>
        </form>

        <form v-if="activeTab === 'register'" @submit.prevent="handleRegister" class="space-y-8">
          <div class="relative group">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
              Nombre
            </label>
            <input
              v-model="registerForm.nombre"
              type="text"
              required
              autocomplete="given-name"
              class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
              placeholder="tu nombre">
          </div>

          <div class="relative group">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
              Apellido
            </label>
            <input
              v-model="registerForm.apellido"
              type="text"
              required
              autocomplete="family-name"
              class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
              placeholder="tu apellido">
          </div>

          <div class="relative group">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
              Email
            </label>
            <input
              v-model="registerForm.email"
              type="email"
              required
              autocomplete="email"
              class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
              placeholder="tu@email.com">
          </div>

          <div class="relative group">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
              Contrasena
            </label>
            <input
              v-model="registerForm.password"
              type="password"
              required
              minlength="6"
              autocomplete="new-password"
              class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
              placeholder="********">
            <p class="text-[9px] text-gray-400 tracking-wider uppercase mt-1">Minimo 6 caracteres</p>
          </div>

          <div class="relative group">
            <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
              Confirmar Contrasena
            </label>
            <input
              v-model="registerForm.confirmPassword"
              type="password"
              required
              autocomplete="new-password"
              class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
              placeholder="repite tu contrasena">
          </div>

          <div class="flex items-start gap-3 pt-2">
            <input
              v-model="registerForm.acceptTerms"
              type="checkbox"
              id="terms"
              class="mt-1 rounded-none border-gray-300 text-luxury-gold focus:ring-luxury-gold">
            <label for="terms" class="text-[10px] text-gray-500 uppercase tracking-wider leading-relaxed">
              Acepto los terminos y condiciones y la politica de privacidad de Ibernovia
            </label>
          </div>

          <button
            type="submit"
            :disabled="loadingRegister"
            class="w-full py-4 bg-luxury-black text-white hover:bg-luxury-gold hover:text-white text-[11px] font-bold uppercase tracking-[0.25em] transition-all duration-300 rounded-full shadow-md hover:shadow-lg disabled:opacity-50 mt-6">
            {{ loadingRegister ? 'Creando Cuenta...' : 'Crear Cuenta' }}
          </button>
        </form>

        <template v-if="showGoogleSection">
          <div class="flex items-center gap-3 my-6">
            <div class="flex-1 h-[1px] bg-black/5"></div>
            <span class="text-[9px] text-gray-400 uppercase tracking-[0.2em] font-bold">O CONECTA CON</span>
            <div class="flex-1 h-[1px] bg-black/5"></div>
          </div>

          <button
            v-if="googleLoginReady"
            type="button"
            @click="handleGoogleLogin"
            :disabled="loadingGoogle"
            class="w-full bg-white border border-black/10 text-luxury-black py-3 px-4 rounded-full text-[10px] font-bold uppercase tracking-[0.2em] hover:bg-[#fafafa] hover:border-luxury-gold transition-all duration-300 flex items-center justify-center gap-3 disabled:opacity-50">
            <svg class="w-4 h-4 shrink-0" viewBox="0 0 24 24">
              <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
              <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
              <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
            </svg>
            <span>{{ loadingGoogle ? 'Conectando...' : 'Google' }}</span>
          </button>

          <div
            v-else
            class="rounded-3xl border border-amber-200 bg-amber-50/70 px-5 py-4 text-[11px] uppercase tracking-[0.16em] text-amber-800">
            {{ googleAvailabilityMessage }}
          </div>
        </template>
      </div>

      <div class="text-center text-[10px] text-gray-400 uppercase tracking-widest space-y-1">
        <p>Acceso seguro mediante protocolo SSL</p>
        <p>Atelier Ibernovia - Garantia de confidencialidad</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { GoogleAuthProvider, signInWithPopup, updateProfile } from 'firebase/auth'
import { useAuthStore } from '../stores/auth'
import { apiClient } from '../lib/api'
import { auth, canUseGoogleAuth, firebaseStatus } from '../lib/firebase'

const router = useRouter()
const authStore = useAuthStore()

const activeTab = ref('login')
const errorGlobal = ref('')
const successMessage = ref('')
const loadingLogin = ref(false)
const loadingRegister = ref(false)
const loadingGoogle = ref(false)
const googleLoginReady = ref(false)
const googleAvailabilityMessage = ref(firebaseStatus.message)

const showGoogleSection = computed(() => canUseGoogleAuth || Boolean(googleAvailabilityMessage.value))

const loginForm = ref({
  email: '',
  password: ''
})

const registerForm = ref({
  nombre: '',
  apellido: '',
  email: '',
  password: '',
  confirmPassword: '',
  acceptTerms: false
})

const redirectAfterAuth = (payload) => {
  if (payload?.isAdmin) {
    router.push('/admin')
    return
  }
  router.push('/tienda')
}

const loadFirebaseStatus = async () => {
  if (!canUseGoogleAuth) {
    googleLoginReady.value = false
    googleAvailabilityMessage.value = firebaseStatus.message
    return
  }

  try {
    const response = await apiClient.get('/api/auth/firebase-status')
    googleLoginReady.value = response.data?.available === true
    googleAvailabilityMessage.value = response.data?.message || ''
  } catch (error) {
    googleLoginReady.value = false
    googleAvailabilityMessage.value = 'El acceso con Google no esta disponible temporalmente. Usa email y contrasena.'
    console.error('Error consultando estado de Firebase:', error)
  }
}

const handleLogin = async () => {
  errorGlobal.value = ''
  successMessage.value = ''

  if (!loginForm.value.email || !loginForm.value.password) {
    errorGlobal.value = 'Por favor completa todos los campos'
    return
  }

  loadingLogin.value = true

  try {
    const response = await apiClient.post('/api/auth/login', {
      email: loginForm.value.email,
      password: loginForm.value.password
    })

    authStore.setUser(response.data)
    successMessage.value = 'Acceso correcto. Iniciando sesion...'

    setTimeout(() => {
      redirectAfterAuth(response.data)
    }, 800)
  } catch (error) {
    errorGlobal.value = error.response?.data || error.message || 'Error en el login'
    console.error('Error Login:', error)
  } finally {
    loadingLogin.value = false
  }
}

const handleRegister = async () => {
  errorGlobal.value = ''
  successMessage.value = ''

  if (!registerForm.value.nombre || !registerForm.value.apellido || !registerForm.value.email || !registerForm.value.password) {
    errorGlobal.value = 'Por favor completa todos los campos'
    return
  }

  if (registerForm.value.password.length < 6) {
    errorGlobal.value = 'La contrasena debe tener al menos 6 caracteres'
    return
  }

  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    errorGlobal.value = 'Las contrasenas no coinciden'
    return
  }

  if (!registerForm.value.acceptTerms) {
    errorGlobal.value = 'Debes aceptar los terminos y condiciones'
    return
  }

  loadingRegister.value = true

  try {
    const response = await apiClient.post('/api/auth/register', {
      email: registerForm.value.email,
      nombre: registerForm.value.nombre,
      apellido: registerForm.value.apellido,
      password: registerForm.value.password,
      confirmPassword: registerForm.value.confirmPassword
    })

    authStore.setUser(response.data)
    successMessage.value = 'Bienvenido a IBERNOVIA'

    setTimeout(() => {
      router.push('/tienda')
    }, 800)
  } catch (error) {
    errorGlobal.value = error.response?.data || error.message || 'Error en el registro'
    console.error('Error Registro:', error)
  } finally {
    loadingRegister.value = false
  }
}

const handleGoogleLogin = async () => {
  errorGlobal.value = ''
  successMessage.value = ''
  loadingGoogle.value = true

  try {
    if (!googleLoginReady.value || !auth) {
      errorGlobal.value = googleAvailabilityMessage.value || 'El acceso con Google no esta disponible temporalmente.'
      return
    }

    const provider = new GoogleAuthProvider()
    provider.addScope('profile email')

    const result = await signInWithPopup(auth, provider)
    const firebaseUser = result.user

    if (!firebaseUser.displayName) {
      await updateProfile(firebaseUser, {
        displayName: firebaseUser.email?.split('@')[0] || 'Cliente Ibernovia'
      })
    }

    const firebaseIdToken = await firebaseUser.getIdToken()
    const response = await apiClient.post('/api/auth/firebase-login', { token: firebaseIdToken })

    authStore.setUser(response.data)
    successMessage.value = 'Acceso correcto. Iniciando sesion...'

    setTimeout(() => {
      redirectAfterAuth(response.data)
    }, 800)
  } catch (error) {
    const errorCode = error?.code
    if (errorCode === 'auth/popup-closed-by-user' || errorCode === 'auth/cancelled-popup-request') {
      return
    }

    errorGlobal.value = error.response?.data || error.message || 'Error al conectar con Google'
    console.error('Error Google Auth:', error)
  } finally {
    loadingGoogle.value = false
  }
}

onMounted(() => {
  loadFirebaseStatus()
})
</script>

<style scoped>
.animate-fade-in-up {
  animation: fadeInUp 1s cubic-bezier(0.16, 1, 0.3, 1) both;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
