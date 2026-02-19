<template>
  <div v-if="showModal" class="fixed inset-0 bg-black/50 z-[9999] flex items-center justify-center p-4">
    <div class="bg-white rounded-lg shadow-2xl max-w-md w-full p-8 animate-in">
      <!-- Header -->
      <div class="text-center mb-6">
        <div class="text-4xl mb-3">🏢</div>
        <h2 class="font-serif text-2xl text-luxury-black mb-2">Acceso Empresarial</h2>
        <p class="text-sm text-gray-600">Ingresa tu código para ver precios y realizar pedidos</p>
      </div>

      <!-- Form -->
      <form @submit.prevent="handleSubmit" class="space-y-4">
        <!-- Code Input -->
        <div>
          <label class="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-widest">
            Código de acceso
          </label>
          <input 
            v-model="businessCode"
            type="text"
            placeholder="Ej: EMPRESA2025"
            class="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold transition uppercase"
            autocomplete="off"
          >
        </div>

        <!-- Error Message -->
        <div v-if="error" class="p-3 bg-red-50 border border-red-200 rounded text-red-600 text-sm">
          ❌ {{ error }}
        </div>

        <!-- Buttons -->
        <div class="flex gap-3 pt-2">
          <button
            type="button"
            @click="closeModal"
            class="flex-1 px-4 py-2 border border-gray-300 rounded text-gray-600 font-semibold hover:bg-gray-50 transition">
            Cancelar
          </button>
          <button
            type="submit"
            :disabled="loading || !businessCode"
            class="flex-1 px-4 py-2 bg-luxury-gold text-white rounded font-bold hover:bg-opacity-90 transition disabled:opacity-50">
            {{ loading ? 'Verificando...' : 'Activar' }}
          </button>
        </div>
      </form>

      <!-- Info -->
      <div class="mt-6 p-4 bg-blue-50 border border-blue-200 rounded-lg">
        <p class="text-xs text-blue-700 font-semibold mb-2">💡 ¿No tienes código?</p>
        <p class="text-xs text-blue-600">Contacta a nuestro equipo para obtener acceso empresarial.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()

const businessCode = ref('')
const error = ref('')
const loading = ref(false)
const showModal = ref(false)

const openModal = () => {
  showModal.value = true
  businessCode.value = ''
  error.value = ''
}

const closeModal = () => {
  showModal.value = false
  businessCode.value = ''
  error.value = ''
}

const handleSubmit = () => {
  error.value = ''
  loading.value = true

  setTimeout(() => {
    const success = authStore.activateBusinessAccess(businessCode.value.toUpperCase())
    loading.value = false

    if (success) {
      showModal.value = false
      businessCode.value = ''
    } else {
      error.value = 'Código inválido o expirado. Intenta nuevamente.'
    }
  }, 500)
}

// Exponer funciones públicas
defineExpose({ openModal, closeModal })
</script>
