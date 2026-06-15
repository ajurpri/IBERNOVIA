<template>
  <div class="min-h-screen bg-gradient-to-br from-luxury-black to-gray-900 flex items-center justify-center py-12 px-4">
    <div class="w-full max-w-md">
      <!-- Logo -->
      <div class="text-center mb-8">
        <h1 class="font-serif text-4xl text-luxury-gold mb-2">IBERNOVIA</h1>
        <p class="text-gray-400 text-sm uppercase tracking-widest">Complementos Nupciales Exclusivos</p>
      </div>

      <!-- Tarjeta de Autenticación -->
      <div class="bg-white rounded-lg shadow-2xl overflow-hidden">
        <!-- Selector de Tabs -->
        <div class="grid grid-cols-3 border-b border-gray-200">
          <button 
            @click="activeTab = 'login'"
            :class="[
              'py-4 font-bold uppercase tracking-widest text-sm transition',
              activeTab === 'login' 
                ? 'bg-luxury-gold text-luxury-black' 
                : 'bg-gray-50 text-gray-600 hover:bg-gray-100'
            ]">
            Iniciar Sesión
          </button>
          <button 
            @click="activeTab = 'register'"
            :class="[
              'py-4 font-bold uppercase tracking-widest text-sm transition',
              activeTab === 'register' 
                ? 'bg-luxury-gold text-luxury-black' 
                : 'bg-gray-50 text-gray-600 hover:bg-gray-100'
            ]">
            Registrarse
          </button>
          <button 
            @click="activeTab = 'admin'"
            :class="[
              'py-4 font-bold uppercase tracking-widest text-xs transition',
              activeTab === 'admin' 
                ? 'bg-red-500 text-white' 
                : 'bg-gray-50 text-gray-600 hover:bg-gray-100'
            ]">
            Admin
          </button>
        </div>

        <!-- Contenido -->
        <div class="p-8">
          <!-- Mensaje de Error Global -->
          <div v-if="errorGlobal" class="mb-6 p-4 bg-red-50 border border-red-200 rounded text-red-600 text-sm">
            {{ errorGlobal }}
          </div>

          <!-- Mensaje de Éxito -->
          <div v-if="successMessage" class="mb-6 p-4 bg-luxury-gold/10 border border-luxury-gold/30 rounded text-luxury-black text-sm">
            {{ successMessage }}
          </div>

          <!-- PESTAÑA: LOGIN -->
          <form v-if="activeTab === 'login'" @submit.prevent="handleLogin" class="space-y-4">
            <!-- Email -->
            <div>
              <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">Email</label>
              <input 
                v-model="loginForm.email"
                type="email" 
                required
                autocomplete="email"
                class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
                placeholder="tu@email.com"
              >
            </div>

            <!-- Contraseña -->
            <div>
              <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">Contraseña</label>
              <input 
                v-model="loginForm.password"
                type="password" 
                required
                autocomplete="current-password"
                class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
                placeholder="••••••••"
              >
            </div>

            <!-- Link Recuperar Contraseña -->
            <div class="text-right">
              <a href="#" class="text-xs text-luxury-gold hover:text-luxury-black transition font-semibold">
                ¿Olvidaste tu contraseña?
              </a>
            </div>

            <!-- Botón Login -->
            <button 
              type="submit"
              :disabled="loadingLogin"
              class="w-full bg-luxury-black text-white py-3 font-bold uppercase tracking-widest hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 rounded mt-6">
              {{ loadingLogin ? 'Iniciando...' : 'Iniciar Sesión' }}
            </button>

            <!-- Separador -->
            <div class="flex items-center gap-3 my-6">
              <div class="flex-1 h-px bg-gray-300"></div>
              <span class="text-xs text-gray-500 font-semibold">O CON</span>
              <div class="flex-1 h-px bg-gray-300"></div>
            </div>

            <!-- Botón Google -->
            <button 
              type="button"
              @click="handleGoogleLogin"
              :disabled="loadingGoogle"
              class="w-full bg-white border-2 border-gray-300 text-gray-700 py-3 font-bold uppercase tracking-widest hover:bg-gray-50 hover:border-blue-500 transition disabled:opacity-50 rounded flex items-center justify-center gap-3">
              <svg class="w-5 h-5" viewBox="0 0 24 24">
                <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
              </svg>
              <span>{{ loadingGoogle ? 'Conectando...' : 'Google' }}</span>
            </button>

            <!-- Info -->
            <p class="text-center text-xs text-gray-500 mt-4">
              ¿Primera vez? Crea una cuenta gratis arriba →
            </p>
          </form>

          <!-- PESTAÑA: REGISTRO -->
          <form v-if="activeTab === 'register'" @submit.prevent="handleRegister" class="space-y-4">
            <!-- Nombre -->
            <div>
              <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">Nombre</label>
              <input 
                v-model="registerForm.nombre"
                type="text" 
                required
                autocomplete="given-name"
                class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
                placeholder="Tu nombre"
              >
            </div>

            <!-- Apellido -->
            <div>
              <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">Apellido</label>
              <input 
                v-model="registerForm.apellido"
                type="text" 
                required
                autocomplete="family-name"
                class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
                placeholder="Tu apellido"
              >
            </div>

            <!-- Email -->
            <div>
              <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">Email</label>
              <input 
                v-model="registerForm.email"
                type="email" 
                required
                autocomplete="email"
                class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
                placeholder="tu@email.com"
              >
            </div>

            <!-- Contraseña -->
            <div>
              <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">Contraseña</label>
              <input 
                v-model="registerForm.password"
                type="password" 
                required
                minlength="6"
                autocomplete="new-password"
                class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
                placeholder="Mínimo 6 caracteres"
              >
              <p class="text-xs text-gray-500 mt-1">Mínimo 6 caracteres</p>
            </div>

            <!-- Confirmar Contraseña -->
            <div>
              <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">Confirmar Contraseña</label>
              <input 
                v-model="registerForm.confirmPassword"
                type="password" 
                required
                autocomplete="new-password"
                class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold transition"
                placeholder="Repite tu contraseña"
              >
            </div>

            <!-- Checkbox Términos -->
            <div class="flex items-start gap-2">
              <input 
                v-model="registerForm.acceptTerms"
                type="checkbox" 
                id="terms"
                class="mt-1 rounded border-gray-300"
              >
              <label for="terms" class="text-xs text-gray-600">
                Acepto los términos y condiciones y la política de privacidad de IBERNOVIA
              </label>
            </div>

            <!-- Botón Registro -->
            <button 
              type="submit"
              :disabled="loadingRegister"
              class="w-full bg-luxury-black text-white py-3 font-bold uppercase tracking-widest hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 rounded mt-6">
              {{ loadingRegister ? 'Registrando...' : 'Crear Cuenta' }}
            </button>

            <!-- Separador -->
            <div class="flex items-center gap-3 my-6">
              <div class="flex-1 h-px bg-gray-300"></div>
              <span class="text-xs text-gray-500 font-semibold">O CON</span>
              <div class="flex-1 h-px bg-gray-300"></div>
            </div>

            <!-- Botón Google -->
            <button 
              type="button"
              @click="handleGoogleLogin"
              :disabled="loadingGoogle"
              class="w-full bg-white border-2 border-gray-300 text-gray-700 py-3 font-bold uppercase tracking-widest hover:bg-gray-50 hover:border-blue-500 transition disabled:opacity-50 rounded flex items-center justify-center gap-3">
              <svg class="w-5 h-5" viewBox="0 0 24 24">
                <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
              </svg>
              <span>{{ loadingGoogle ? 'Conectando...' : 'Google' }}</span>
            </button>

            <!-- Info -->
            <p class="text-center text-xs text-gray-500 mt-4">
              ¿Ya tienes cuenta? Inicia sesión arriba ←
            </p>
          </form>

          <!-- PESTAÑA: ADMIN -->
          <div v-if="activeTab === 'admin'" class="space-y-6">
            <div class="bg-blue-50 border border-blue-200 rounded-lg p-4">
              <p class="text-xs text-blue-600 font-bold uppercase tracking-widest mb-2">🔐 Autenticación Firebase</p>
              <p class="text-xs text-blue-600">Sistema seguro con protección completa. Crea o inicia sesión con tus credenciales.</p>
            </div>

            <div class="bg-gray-50 border border-gray-200 rounded-lg p-4">
              <p class="text-xs text-gray-700 mb-3 font-semibold">Acceso administrativo:</p>
              <p class="text-xs text-gray-600 mb-3">Crea una cuenta con email <code class="bg-white px-2 py-1 rounded border font-mono">admin</code> o contacta al administrador.</p>
              <button 
                @click="showAdminForm = !showAdminForm"
                type="button"
                class="w-full bg-blue-500 text-white py-2 font-bold uppercase text-xs hover:bg-blue-600 transition rounded">
                {{ showAdminForm ? 'Ocultar' : 'Crear cuenta admin' }}
              </button>
            </div>

            <!-- Formulario Admin -->
            <form v-if="showAdminForm" @submit.prevent="handleAdminRegister" class="space-y-4 bg-gray-50 p-4 rounded-lg border border-gray-200">
              <div>
                <label class="block text-xs font-bold text-gray-700 mb-2 uppercase">Email</label>
                <input 
                  v-model="adminForm.email"
                  type="email"
                  required
                  class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
                  placeholder="admin@tudominio.com"
                >
              </div>
              <div>
                <label class="block text-xs font-bold text-gray-700 mb-2 uppercase">Contraseña</label>
                <input 
                  v-model="adminForm.password"
                  type="password"
                  required
                  minlength="6"
                  class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
                  placeholder="Mínimo 6 caracteres"
                >
              </div>
              <button 
                type="submit"
                :disabled="loadingAdmin"
                class="w-full bg-blue-600 text-white py-2 font-bold uppercase text-xs hover:bg-blue-700 transition disabled:opacity-50 rounded">
                {{ loadingAdmin ? 'Creando...' : 'Crear Cuenta Admin' }}
              </button>
            </form>
          </div>
        </div>
      </div>

      <!-- Info Seguridad -->
      <div class="mt-8 text-center text-xs text-gray-400 space-y-2">
        <p>🔒 Autenticación segura con Firebase</p>
        <p>✓ Contraseñas encriptadas • Datos protegidos • HTTPS seguro</p>
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
const showAdminForm = ref(false)
const errorGlobal = ref('')
const successMessage = ref('')
const loadingLogin = ref(false)
const loadingRegister = ref(false)
const loadingAdmin = ref(false)
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

const adminForm = ref({
  email: '',
  password: ''
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

    successMessage.value = '¡Bienvenido!'
    
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

const handleAdminRegister = async () => {
  errorGlobal.value = ''
  successMessage.value = ''

  if (!adminForm.value.email || !adminForm.value.password) {
    errorGlobal.value = 'Por favor completa todos los campos'
    return
  }

  if (adminForm.value.password.length < 6) {
    errorGlobal.value = 'La contraseña debe tener al menos 6 caracteres'
    return
  }

  loadingAdmin.value = true

  try {
    const userCredential = await createUserWithEmailAndPassword(
      auth,
      adminForm.value.email,
      adminForm.value.password
    )

    await updateProfile(userCredential.user, {
      displayName: 'Administrador'
    })

    // Sincronizar con el backend
    const firebaseIdToken = await userCredential.user.getIdToken()
    const response = await apiClient.post('/api/auth/firebase-login', { token: firebaseIdToken })
    authStore.setUser(response.data)

    successMessage.value = '¡Cuenta admin creada! Redirigiendo...'
    
    setTimeout(() => {
      router.push('/admin')
    }, 800)

  } catch (error) {
    const errorCode = error.code
    if (errorCode === 'auth/email-already-in-use') {
      errorGlobal.value = 'Esta cuenta ya existe'
    } else if (errorCode === 'auth/weak-password') {
      errorGlobal.value = 'Contraseña demasiado débil'
    } else {
      errorGlobal.value = error.response?.data || error.message || 'Error al crear cuenta'
    }
    console.error('Error Admin:', error)
  } finally {
    loadingAdmin.value = false
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

    successMessage.value = '¡Bienvenido!'
    
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
