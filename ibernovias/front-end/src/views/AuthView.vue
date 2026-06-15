<template>
  <div class="min-h-screen bg-[#fdfdfc] flex">
    
    <!-- Left Column: Visual Brand Editorial (Hidden on mobile) -->
    <div class="hidden md:flex md:w-1/2 lg:w-3/5 relative bg-luxury-black overflow-hidden select-none">
      <!-- Background Image -->
      <img 
        src="/images/auth_bg.png" 
        alt="Atelier Ibernovia" 
        class="absolute inset-0 w-full h-full object-cover opacity-80 scale-105 animate-subtle-zoom"
      >
      <!-- Elegant Gradient Overlay -->
      <div class="absolute inset-0 bg-gradient-to-t from-black/80 via-black/40 to-black/20"></div>
      
      <!-- Editorial Brand Text -->
      <div class="absolute bottom-16 left-16 right-16 space-y-4 text-white z-10 animate-fade-in-up">
        <span class="text-[10px] uppercase tracking-[0.4em] text-luxury-gold font-bold block">ALTA COSTURA Y COMPLEMENTOS</span>
        <h2 class="font-serif text-5xl font-light tracking-wide leading-tight text-white/95">
          Creando recuerdos <br>
          <span class="italic font-normal text-luxury-gold">únicos e inolvidables</span>
        </h2>
        <div class="w-12 h-[1px] bg-luxury-gold my-6"></div>
        <p class="text-xs text-gray-300 max-w-md leading-relaxed tracking-wide">
          Descubre colecciones exclusivas para novia, fiesta y complementos de lujo diseñados con la máxima delicadeza en nuestro Atelier.
        </p>
      </div>
    </div>

    <!-- Right Column: Premium Auth Forms -->
    <div class="w-full md:w-1/2 lg:w-2/5 flex flex-col justify-center py-12 px-6 sm:px-12 lg:px-16 bg-[#fdfdfc]">
      <div class="w-full max-y-auto space-y-8 max-w-md mx-auto">
        
        <!-- Header for Mobile / Tablet (Logo & Subtitle) -->
        <div class="text-center md:text-left">
          <span class="text-[10px] uppercase tracking-[0.4em] text-luxury-gold font-bold block mb-3">ATELIER IBERNOVIA</span>
          <h1 class="font-serif text-3xl sm:text-4xl font-light text-luxury-black tracking-wide mb-2">Mi Cuenta</h1>
          <p class="text-xs text-gray-400 uppercase tracking-widest">Acceso para clientes y boutiques profesionales</p>
        </div>

        <!-- Auth Card Container -->
        <div class="bg-white border border-black/5 rounded-none shadow-sm p-8 space-y-6">
          
          <!-- Tab Selector -->
          <div class="flex border-b border-black/5 pb-4 mb-6">
            <button 
              @click="activeTab = 'login'"
              :class="[
                'flex-1 text-center pb-2 text-[11px] font-bold uppercase tracking-[0.2em] transition-colors duration-300 border-b-2',
                activeTab === 'login' 
                  ? 'border-luxury-gold text-luxury-black' 
                  : 'border-transparent text-gray-400 hover:text-luxury-black'
              ]">
              Iniciar Sesión
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

          <!-- Alert / Success Feedback -->
          <div v-if="errorGlobal" class="p-4 bg-red-50/50 border-l-2 border-red-500 text-red-700 text-xs tracking-wide">
            {{ errorGlobal }}
          </div>
          <div v-if="successMessage" class="p-4 bg-luxury-gold/5 border-l-2 border-luxury-gold text-luxury-black text-xs tracking-wide">
            {{ successMessage }}
          </div>

          <!-- FORM: LOGIN -->
          <form v-if="activeTab === 'login'" @submit.prevent="handleLogin" class="space-y-6">
            <!-- Email Field -->
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
                placeholder="introduce tu email"
              >
            </div>

            <!-- Password Field -->
            <div class="relative group">
              <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                Contraseña
              </label>
              <input 
                v-model="loginForm.password"
                type="password" 
                required
                autocomplete="current-password"
                class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                placeholder="••••••••"
              >
            </div>

            <!-- Forgot Password -->
            <div class="text-right">
              <a href="#" class="text-[10px] text-gray-400 hover:text-luxury-gold transition-colors font-bold uppercase tracking-wider">
                ¿Olvidaste tu contraseña?
              </a>
            </div>

            <!-- Submit Button -->
            <button 
              type="submit"
              :disabled="loadingLogin"
              class="w-full py-4 bg-luxury-black text-white hover:bg-luxury-gold hover:text-white text-[11px] font-bold uppercase tracking-[0.25em] transition-all duration-300 rounded-full shadow-md hover:shadow-lg disabled:opacity-50 mt-6">
              {{ loadingLogin ? 'Accediendo...' : 'Acceder' }}
            </button>
          </form>

          <!-- FORM: REGISTER -->
          <form v-if="activeTab === 'register'" @submit.prevent="handleRegister" class="space-y-6">
            <!-- Nombre -->
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
                placeholder="tu nombre"
              >
            </div>

            <!-- Apellido -->
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
                placeholder="tu apellido"
              >
            </div>

            <!-- Email -->
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
                placeholder="tu@email.com"
              >
            </div>

            <!-- Password -->
            <div class="relative group">
              <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                Contraseña
              </label>
              <input 
                v-model="registerForm.password"
                type="password" 
                required
                minlength="6"
                autocomplete="new-password"
                class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                placeholder="••••••••"
              >
              <p class="text-[9px] text-gray-400 tracking-wider uppercase mt-1">Mínimo 6 caracteres</p>
            </div>

            <!-- Confirmar Contraseña -->
            <div class="relative group">
              <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                Confirmar Contraseña
              </label>
              <input 
                v-model="registerForm.confirmPassword"
                type="password" 
                required
                autocomplete="new-password"
                class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                placeholder="repite tu contraseña"
              >
            </div>

            <!-- Accept Terms -->
            <div class="flex items-start gap-3 pt-2">
              <input 
                v-model="registerForm.acceptTerms"
                type="checkbox" 
                id="terms"
                class="mt-1 rounded-none border-gray-300 text-luxury-gold focus:ring-luxury-gold"
              >
              <label for="terms" class="text-[10px] text-gray-500 uppercase tracking-wider leading-relaxed">
                Acepto los términos y condiciones y la política de privacidad de Ibernovia
              </label>
            </div>

            <!-- Submit Button -->
            <button 
              type="submit"
              :disabled="loadingRegister"
              class="w-full py-4 bg-luxury-black text-white hover:bg-luxury-gold hover:text-white text-[11px] font-bold uppercase tracking-[0.25em] transition-all duration-300 rounded-full shadow-md hover:shadow-lg disabled:opacity-50 mt-6">
              {{ loadingRegister ? 'Creando Cuenta...' : 'Crear Cuenta' }}
            </button>
          </form>

          <!-- Divider for Google Auth -->
          <div class="flex items-center gap-3 my-6">
            <div class="flex-1 h-[1px] bg-black/5"></div>
            <span class="text-[9px] text-gray-400 uppercase tracking-[0.2em] font-bold">O CONECTA CON</span>
            <div class="flex-1 h-[1px] bg-black/5"></div>
          </div>

          <!-- Google Auth Button -->
          <button 
            type="button"
            @click="handleGoogleLogin"
            :disabled="loadingGoogle"
            class="w-full bg-white border border-black/10 text-luxury-black py-3 px-4 rounded-full text-[10px] font-bold uppercase tracking-[0.2em] hover:bg-[#fafafa] hover:border-luxury-gold transition-all duration-300 flex items-center justify-center gap-3">
            <svg class="w-4 h-4 shrink-0" viewBox="0 0 24 24">
              <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
              <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
              <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
            </svg>
            <span>{{ loadingGoogle ? 'Conectando...' : 'Google' }}</span>
          </button>

        </div>

        <!-- Trust Badges -->
        <div class="text-center text-[10px] text-gray-400 uppercase tracking-widest space-y-1">
          <p>🔒 Acceso seguro mediante protocolo SSL</p>
          <p>Atelier Ibernovia • Garantía de confidencialidad</p>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { 
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword,
  updateProfile,
  signInWithPopup,
  GoogleAuthProvider
} from 'firebase/auth'
import { auth } from '../lib/firebase'
import { useAuthStore } from '../stores/auth'
import { apiClient } from '../lib/api'

const router = useRouter()
const authStore = useAuthStore()

const activeTab = ref('login')
const errorGlobal = ref('')
const successMessage = ref('')
const loadingLogin = ref(false)
const loadingRegister = ref(false)
const loadingGoogle = ref(false)

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

const handleLogin = async () => {
  errorGlobal.value = ''
  successMessage.value = ''
  
  if (!loginForm.value.email || !loginForm.value.password) {
    errorGlobal.value = 'Por favor completa todos los campos'
    return
  }

  loadingLogin.value = true

  try {
    const userCredential = await signInWithEmailAndPassword(
      auth,
      loginForm.value.email,
      loginForm.value.password
    )

    // Sincronizar con el backend
    const firebaseIdToken = await userCredential.user.getIdToken()
    const response = await apiClient.post('/api/auth/firebase-login', { token: firebaseIdToken })
    authStore.setUser(response.data)

    successMessage.value = '¡Acceso correcto! Iniciando sesión...'
    
    setTimeout(() => {
      if (response.data?.isAdmin) {
        router.push('/admin')
      } else {
        router.push('/tienda')
      }
    }, 800)

  } catch (error) {
    const errorCode = error.code
    if (errorCode === 'auth/user-not-found') {
      errorGlobal.value = 'No existe una cuenta con este email'
    } else if (errorCode === 'auth/wrong-password') {
      errorGlobal.value = 'Contraseña incorrecta'
    } else if (errorCode === 'auth/invalid-email') {
      errorGlobal.value = 'Email inválido'
    } else if (errorCode === 'auth/too-many-requests') {
      errorGlobal.value = 'Demasiados intentos. Intenta más tarde'
    } else {
      errorGlobal.value = error.response?.data || error.message || 'Error en el login'
    }
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
    errorGlobal.value = 'La contraseña debe tener al menos 6 caracteres'
    return
  }

  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    errorGlobal.value = 'Las contraseñas no coinciden'
    return
  }

  if (!registerForm.value.acceptTerms) {
    errorGlobal.value = 'Debes aceptar los términos y condiciones'
    return
  }

  loadingRegister.value = true

  try {
    const userCredential = await createUserWithEmailAndPassword(
      auth,
      registerForm.value.email,
      registerForm.value.password
    )

    await updateProfile(userCredential.user, {
      displayName: `${registerForm.value.nombre} ${registerForm.value.apellido}`
    })

    // Sincronizar con el backend
    const firebaseIdToken = await userCredential.user.getIdToken()
    const response = await apiClient.post('/api/auth/firebase-login', { token: firebaseIdToken })
    authStore.setUser(response.data)

    successMessage.value = '¡Bienvenido a IBERNOVIA!'
    
    setTimeout(() => {
      router.push('/tienda')
    }, 800)

  } catch (error) {
    const errorCode = error.code
    if (errorCode === 'auth/email-already-in-use') {
      errorGlobal.value = 'Ya existe una cuenta con este email'
    } else if (errorCode === 'auth/weak-password') {
      errorGlobal.value = 'La contraseña es demasiado débil'
    } else if (errorCode === 'auth/invalid-email') {
      errorGlobal.value = 'El email no es válido'
    } else {
      errorGlobal.value = error.response?.data || error.message || 'Error en el registro'
    }
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
    const provider = new GoogleAuthProvider()
    provider.addScope('profile email')
    
    const result = await signInWithPopup(auth, provider)
    const user = result.user

    // Actualizar perfil si no tiene displayName
    if (!user.displayName) {
      await updateProfile(user, {
        displayName: user.email.split('@')[0]
      })
    }

    // Sincronizar con el backend
    const firebaseIdToken = await user.getIdToken()
    const response = await apiClient.post('/api/auth/firebase-login', { token: firebaseIdToken })
    authStore.setUser(response.data)

    successMessage.value = '¡Acceso correcto! Iniciando sesión...'
    
    setTimeout(() => {
      if (response.data?.isAdmin) {
        router.push('/admin')
      } else {
        router.push('/tienda')
      }
    }, 800)

  } catch (error) {
    const errorCode = error.code
    if (errorCode === 'auth/popup-closed-by-user') {
      return
    } else if (errorCode === 'auth/cancelled-popup-request') {
      return
    } else {
      errorGlobal.value = error.response?.data || error.message || 'Error al conectar con Google'
      console.error('Error Google Auth:', error)
    }
  } finally {
    loadingGoogle.value = false
  }
}
</script>

<style scoped>
.animate-fade-in-up {
  animation: fadeInUp 1.2s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.animate-subtle-zoom {
  animation: subtleZoom 25s ease-out infinite alternate;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(25px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes subtleZoom {
  from {
    transform: scale(1.02);
  }
  to {
    transform: scale(1.1);
  }
}
</style>
