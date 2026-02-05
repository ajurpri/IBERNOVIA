<template>
  <div class="min-h-screen bg-gray-50 py-12 px-4">
    <div class="max-w-3xl mx-auto bg-white rounded-lg shadow-md p-8">
      <h1 class="font-serif text-3xl font-bold text-luxury-black mb-8">Mi Cuenta</h1>
      <div v-if="authStore.user" class="space-y-6">
        <div class="grid grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-bold text-gray-700">Nombre</label>
            <p class="text-lg text-gray-900">{{ authStore.user.nombre }}</p>
          </div>
          <div>
            <label class="block text-sm font-bold text-gray-700">Apellido</label>
            <p class="text-lg text-gray-900">{{ authStore.user.apellido }}</p>
          </div>
        </div>
        <div>
          <label class="block text-sm font-bold text-gray-700">Email</label>
          <p class="text-lg text-gray-900">{{ authStore.user.email }}</p>
        </div>
        <button @click="logout" class="bg-red-600 text-white px-6 py-2 rounded hover:bg-red-700">
          Cerrar Sesión
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const logout = () => {
  authStore.logout()
  router.push('/')
}
</script>
