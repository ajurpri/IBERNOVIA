<template>
  <div class="min-h-screen bg-gradient-to-br from-luxury-black via-gray-900 to-luxury-black flex items-center justify-center p-4">
    <!-- Background decorative element -->
    <div class="absolute inset-0 opacity-10">
      <div class="absolute top-20 right-20 w-72 h-72 bg-luxury-gold rounded-full blur-3xl"></div>
      <div class="absolute bottom-20 left-20 w-72 h-72 bg-blue-500 rounded-full blur-3xl"></div>
    </div>

    <div class="relative max-w-md w-full">
      <div class="bg-white/95 backdrop-blur rounded-3xl shadow-2xl border border-white/20 p-8 sm:p-10">
        <!-- Logo -->
        <div class="text-center mb-8">
          <h1 class="font-serif text-4xl text-luxury-black mb-2">IBERNOVIA</h1>
          <p class="text-xs uppercase tracking-[0.3em] text-gray-500">Setup Administrativo</p>
        </div>

        <div class="mb-8">
          <h2 class="font-serif text-2xl text-luxury-black mb-3">Crear Administrador</h2>
          <p class="text-sm text-gray-600">Configura tu primer usuario administrador para acceder al panel de control.</p>
        </div>

        <form @submit.prevent="submitForm" class="space-y-6">
          <!-- Email -->
          <div>
            <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
              Email Admin
              <span class="text-red-500">*</span>
            </label>
            <input
              v-model="form.email"
              type="email"
              required
              placeholder="admin@ibernovia.es"
              class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
            />
            <p class="text-xs text-gray-500 mt-1">Usa tu correo profesional</p>
          </div>

          <!-- Nombre -->
          <div>
            <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
              Nombre Completo
              <span class="text-red-500">*</span>
            </label>
            <input
              v-model="form.nombre"
              type="text"
              required
              placeholder="Ej: Juan Pérez"
              class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
            />
          </div>

          <!-- Password -->
          <div>
            <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
              Contraseña
              <span class="text-red-500">*</span>
            </label>
            <input
              v-model="form.password"
              :type="showPassword ? 'text' : 'password'"
              required
              placeholder="Mínimo 8 caracteres"
              class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
            />
            <button
              type="button"
              @click="showPassword = !showPassword"
              class="text-xs text-luxury-gold hover:text-luxury-gold/80 mt-2"
            >
              {{ showPassword ? '👁 Ocultar' : '👁 Mostrar' }}
            </button>
          </div>

          <!-- Confirm Password -->
          <div>
            <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
              Confirmar Contraseña
              <span class="text-red-500">*</span>
            </label>
            <input
              v-model="form.confirmPassword"
              :type="showPassword ? 'text' : 'password'"
              required
              placeholder="Repite la contraseña"
              class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
            />
          </div>

          <!-- Validaciones -->
          <div v-if="validationErrors.length > 0" class="p-4 bg-red-50 border border-red-200 rounded-lg">
            <p class="text-xs font-semibold text-red-700 mb-2">⚠️ Errores:</p>
            <ul class="space-y-1">
              <li v-for="(error, i) in validationErrors" :key="i" class="text-xs text-red-600">
                • {{ error }}
              </li>
            </ul>
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            :disabled="loading || validationErrors.length > 0"
            class="w-full bg-luxury-black text-white py-3 font-bold uppercase tracking-widest text-sm rounded-lg hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 disabled:cursor-not-allowed duration-300"
          >
            <span v-if="loading" class="flex items-center justify-center gap-2">
              <svg class="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Creando admin...
            </span>
            <span v-else>✓ Crear Administrador</span>
          </button>

          <!-- Success Message -->
          <Transition name="fade">
            <div v-if="success" class="p-4 bg-green-50 border border-green-200 rounded-lg">
              <p class="text-sm text-green-800 font-semibold">✓ {{ success }}</p>
              <p class="text-xs text-green-700 mt-2">Redirigiendo al login en 3 segundos...</p>
            </div>
          </Transition>

          <!-- Error Message -->
          <Transition name="fade">
            <div v-if="error" class="p-4 bg-red-50 border border-red-200 rounded-lg">
              <p class="text-sm text-red-800 font-semibold">✗ {{ error }}</p>
            </div>
          </Transition>
        </form>

        <!-- Info Box -->
        <div class="mt-8 pt-8 border-t border-gray-200">
          <p class="text-xs text-gray-600 leading-relaxed">
            <span class="font-semibold text-gray-700">🔒 Seguridad:</span> Esta es la única vez que podrás crear un administrador. Guarda tus credenciales en un lugar seguro.
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const form = ref({
  email: '',
  nombre: '',
  password: '',
  confirmPassword: ''
})

const showPassword = ref(false)
const loading = ref(false)
const success = ref('')
const error = ref('')

const validationErrors = computed(() => {
  const errors = []
  
  if (form.value.email && !form.value.email.includes('@')) {
    errors.push('Email inválido')
  }
  if (form.value.password && form.value.password.length < 8) {
    errors.push('Contraseña debe tener al menos 8 caracteres')
  }
  if (form.value.password && form.value.confirmPassword && form.value.password !== form.value.confirmPassword) {
    errors.push('Las contraseñas no coinciden')
  }
  
  return errors
})

const submitForm = async () => {
  if (validationErrors.value.length > 0) return

  loading.value = true
  error.value = ''

  try {
    const response = await fetch('/api/admin/setup/create-admin', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        email: form.value.email,
        nombre: form.value.nombre,
        password: form.value.password
      })
    })

    const data = await response.json()

    if (data.success) {
      success.value = `✓ Admin creado correctamente. Email: ${data.admin.email}`
      setTimeout(() => {
        router.push('/auth')
      }, 3000)
    } else {
      error.value = data.message || 'Error al crear administrador'
    }
  } catch (e) {
    error.value = 'Error de conexión. Verifica que el servidor esté activo.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
