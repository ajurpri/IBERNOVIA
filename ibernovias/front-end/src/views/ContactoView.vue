<template>
  <div class="min-h-screen bg-gray-50 py-12 px-4">
    <div class="max-w-2xl mx-auto">
      <div class="text-center mb-12">
        <h1 class="font-serif text-4xl font-bold text-luxury-black mb-4">Contacto</h1>
        <p class="text-gray-600">Nos encantaría saber de ti. Envíanos un mensaje.</p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-12 mb-12">
        <div>
          <h2 class="font-serif text-2xl font-bold text-luxury-black mb-6">Información de Contacto</h2>
          <div class="space-y-4">
            <div>
              <p class="font-bold text-luxury-black">Correo Electrónico</p>
              <p class="text-gray-600">info@ibernovia.com</p>
            </div>
            <div>
              <p class="font-bold text-luxury-black">Teléfono</p>
              <p class="text-gray-600">+34 123 456 789</p>
            </div>
            <div>
              <p class="font-bold text-luxury-black">Dirección</p>
              <p class="text-gray-600">Calle Principal 123<br>28001 Madrid, España</p>
            </div>
            <div>
              <p class="font-bold text-luxury-black">Horario</p>
              <p class="text-gray-600">Lunes - Viernes: 10:00 - 18:00<br>Sábado: 11:00 - 17:00</p>
            </div>
          </div>
        </div>

        <form @submit.prevent="submitForm" class="bg-white rounded-lg shadow-md p-8">
          <div class="mb-4">
            <label class="block text-sm font-bold text-gray-700 mb-2">Nombre</label>
            <input 
              v-model="form.nombre"
              type="text"
              required
              class="w-full px-4 py-2 border-2 border-gray-300 rounded focus:outline-none focus:border-luxury-gold"
            >
          </div>

          <div class="mb-4">
            <label class="block text-sm font-bold text-gray-700 mb-2">Email</label>
            <input 
              v-model="form.email"
              type="email"
              required
              class="w-full px-4 py-2 border-2 border-gray-300 rounded focus:outline-none focus:border-luxury-gold"
            >
          </div>

          <div class="mb-4">
            <label class="block text-sm font-bold text-gray-700 mb-2">Asunto</label>
            <input 
              v-model="form.asunto"
              type="text"
              required
              class="w-full px-4 py-2 border-2 border-gray-300 rounded focus:outline-none focus:border-luxury-gold"
            >
          </div>

          <div class="mb-6">
            <label class="block text-sm font-bold text-gray-700 mb-2">Mensaje</label>
            <textarea 
              v-model="form.mensaje"
              required
              rows="5"
              class="w-full px-4 py-2 border-2 border-gray-300 rounded focus:outline-none focus:border-luxury-gold"
            ></textarea>
          </div>

          <button 
            type="submit"
            :disabled="enviando"
            class="w-full bg-luxury-black text-white py-3 font-bold uppercase tracking-widest hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 rounded"
          >
            {{ enviando ? 'Enviando...' : 'Enviar Mensaje' }}
          </button>

          <p v-if="mensaje" :class="{ 'text-green-600': exito, 'text-red-600': !exito }" class="mt-4 text-center text-sm">
            {{ mensaje }}
          </p>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const form = ref({
  nombre: '',
  email: '',
  asunto: '',
  mensaje: ''
})

const enviando = ref(false)
const mensaje = ref('')
const exito = ref(false)

const submitForm = async () => {
  enviando.value = true
  try {
    // Aquí iría la llamada al backend para enviar el email
    setTimeout(() => {
      mensaje.value = '¡Gracias por tu mensaje! Nos pondremos en contacto pronto.'
      exito.value = true
      form.value = { nombre: '', email: '', asunto: '', mensaje: '' }
      enviando.value = false
    }, 1000)
  } catch (error) {
    mensaje.value = 'Error al enviar el mensaje. Intenta de nuevo.'
    exito.value = false
    enviando.value = false
  }
}
</script>
