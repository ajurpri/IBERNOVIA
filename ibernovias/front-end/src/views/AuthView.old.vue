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
            Admin (Dev)
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

            <!-- Info -->
            <p class="text-center text-xs text-gray-500 mt-4">
              ¿Ya tienes cuenta? Inicia sesión arriba ←
            </p>
          </form>

          <!-- PESTAÑA: ADMIN (DEV) -->
          <div v-if="activeTab === 'admin'" class="space-y-6">
            <div class="bg-red-50 border border-red-200 rounded-lg p-4">
              <p class="text-xs text-red-600 font-bold uppercase tracking-widest mb-2">⚠️ Acceso de Desarrollador</p>
              <p class="text-xs text-red-600">Esta opción solo está disponible en desarrollo. Usa las credenciales por defecto para acceder al panel de administración.</p>
            </div>

            <div class="bg-gray-50 border border-gray-200 rounded-lg p-4">
              <p class="text-xs text-gray-700 mb-3 font-semibold">Credenciales de Desarrollo:</p>
              <div class="space-y-2">
                <div class="flex items-center justify-between bg-white p-2 rounded border border-gray-200">
                  <span class="text-xs font-mono text-gray-500">Email:</span>
                  <code class="text-xs font-bold text-luxury-gold bg-gray-100 px-2 py-1 rounded">admin@ibernovia.com</code>
                </div>
                <div class="flex items-center justify-between bg-white p-2 rounded border border-gray-200">
                  <span class="text-xs font-mono text-gray-500">Password:</span>
                  <code class="text-xs font-bold text-luxury-gold bg-gray-100 px-2 py-1 rounded">admin123</code>
                </div>
              </div>
            </div>

            <button 
              @click="loginAsAdmin"
              type="button"
              class="w-full bg-red-500 text-white py-3 font-bold uppercase tracking-widest hover:bg-red-600 transition rounded">
              Entrar como Admin
            </button>

            <p class="text-center text-xs text-gray-500">
              O usa las credenciales de arriba en la sección "Iniciar Sesión"
            </p>
          </div>
        </div>
      </div>

      <!-- Info Seguridad -->
      <div class="mt-8 text-center text-xs text-gray-400 space-y-2">
        <p>🔒 Tu información está completamente segura</p>
        <p>✓ Pago seguro con SSL • Datos encriptados</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { apiClient } from '../lib/api'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const activeTab = ref('login')
const errorGlobal = ref('')
const successMessage = ref('')
const loadingLogin = ref(false)
const loadingRegister = ref(false)

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
  
  if (!loginForm.value.email || !loginForm.value.password) {
    errorGlobal.value = 'Por favor completa todos los campos'
    return
  }

  loadingLogin.value = true

  try {
    // Credenciales de desarrollador para admin
    if (loginForm.value.email === 'admin@ibernovia.com' && loginForm.value.password === 'admin123') {
      authStore.setUser({
        userId: 'admin',
        email: 'admin@ibernovia.com',
        nombre: 'Administrador',
        apellido: 'IBERNOVIA',
        isAdmin: true,
        token: 'dev-token-admin-' + Date.now()
      })
      localStorage.setItem('token', 'dev-token-admin-' + Date.now())
      localStorage.setItem('user', JSON.stringify({
        userId: 'admin',
        email: 'admin@ibernovia.com',
        nombre: 'Administrador',
        apellido: 'IBERNOVIA',
        isAdmin: true
      }))
      
      successMessage.value = '¡Bienvenido Admin!'
      setTimeout(() => {
        router.push('/admin')
      }, 500)
      return
    }

    const response = await apiClient.post('/api/auth/login', {
      email: loginForm.value.email,
      password: loginForm.value.password
    })

    // Guardar datos en store y localStorage
    authStore.setUser(response.data)
    localStorage.setItem('token', response.data.token)
    localStorage.setItem('user', JSON.stringify(response.data))

    successMessage.value = '¡Bienvenido de vuelta!'
    
    setTimeout(() => {
      router.push('/carrito')
    }, 500)

  } catch (error) {
    errorGlobal.value = error.response?.data || 'Error en el login'
  } finally {
    loadingLogin.value = false
  }
}

const handleRegister = async () => {
  errorGlobal.value = ''

  // Validaciones
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
    const response = await apiClient.post('/api/auth/register', {
      email: registerForm.value.email,
      nombre: registerForm.value.nombre,
      apellido: registerForm.value.apellido,
      password: registerForm.value.password,
      confirmPassword: registerForm.value.confirmPassword
    })

    // Guardar datos en store y localStorage
    authStore.setUser(response.data)
    localStorage.setItem('token', response.data.token)
    localStorage.setItem('user', JSON.stringify(response.data))

    successMessage.value = '¡Bienvenido a IBERNOVIA!'
    
    setTimeout(() => {
      router.push('/carrito')
    }, 500)

  } catch (error) {
    errorGlobal.value = error.response?.data || 'Error en el registro'
  } finally {
    loadingRegister.value = false
  }
}

const loginAsAdmin = () => {
  authStore.setUser({
    userId: 'admin',
    email: 'admin@ibernovia.com',
    nombre: 'Administrador',
    apellido: 'IBERNOVIA',
    isAdmin: true,
    token: 'dev-token-admin-' + Date.now()
  })
  localStorage.setItem('token', 'dev-token-admin-' + Date.now())
  localStorage.setItem('user', JSON.stringify({
    userId: 'admin',
    email: 'admin@ibernovia.com',
    nombre: 'Administrador',
    apellido: 'IBERNOVIA',
    isAdmin: true
  }))
  
  successMessage.value = '¡Bienvenido Admin!'
  setTimeout(() => {
    router.push('/admin')
  }, 500)
}
</script>
