<template>
  <div class="min-h-screen bg-gradient-to-b from-white via-gray-50 to-white py-12 px-4">
    <div class="max-w-6xl mx-auto">
      <div class="text-center mb-12">
        <div class="inline-flex items-center gap-3 px-5 py-2 rounded-full bg-white shadow-sm border border-gray-100 mb-6">
          <img :src="logoSrc" alt="IBERNOVIA" class="h-8 w-auto" />
          <span class="text-xs uppercase tracking-[0.3em] text-gray-500">Atención personalizada</span>
        </div>
        <h1 class="font-serif text-4xl md:text-5xl font-bold text-luxury-black mb-3">Contacto</h1>
        <p class="text-gray-600">Nos encantaría saber de ti. Envíanos un mensaje y te respondemos lo antes posible.</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-10 mb-12">
        <div class="bg-white rounded-2xl shadow-md border border-gray-100 p-8">
          <div class="flex items-center justify-between gap-3 mb-6">
            <h2 class="font-serif text-2xl font-bold text-luxury-black">Información de Contacto</h2>
            <span class="text-xs px-3 py-1 rounded-full bg-luxury-gold/10 text-luxury-gold uppercase tracking-widest">Google</span>
          </div>
          <div class="space-y-6">
            <div class="flex items-start gap-3">
              <div class="w-10 h-10 rounded-full bg-luxury-gold/10 text-luxury-gold flex items-center justify-center">📍</div>
              <div>
                <p class="font-bold text-luxury-black">Horario</p>
                <p class="text-gray-700">Plaza de Abastos</p>
                <p class="text-gray-600 mt-2">Dirección</p>
                <p class="text-gray-700">C. Ollerías, 48, 23740 Andújar, Jaén</p>
                <p class="text-gray-500 text-sm mt-1">Provincia: Jaén</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-10 h-10 rounded-full bg-luxury-gold/10 text-luxury-gold flex items-center justify-center">☎</div>
              <div>
                <p class="font-bold text-luxury-black">Teléfono</p>
                <p class="text-gray-700">953 51 50 70</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-10 h-10 rounded-full bg-luxury-gold/10 text-luxury-gold flex items-center justify-center">✉</div>
              <div>
                <p class="font-bold text-luxury-black">Correo Electrónico</p>
                <p class="text-gray-700">info@ibernovia.es</p>
              </div>
            </div>
            <div class="flex items-start gap-3">
              <div class="w-10 h-10 rounded-full bg-luxury-gold/10 text-luxury-gold flex items-center justify-center">⏰</div>
              <div>
                <p class="font-bold text-luxury-black">Horario</p>
                <p class="text-gray-700">De lunes a viernes: 9:00 - 14:00 y 17:00 - 20:00</p>
                <p class="text-gray-700">Sábado y domingo: Cerrado</p>
              </div>
            </div>
          </div>

          <div class="mt-8 rounded-xl bg-gradient-to-r from-luxury-black to-gray-800 text-white p-6">
            <p class="text-sm uppercase tracking-widest text-white/70">Visítanos</p>
            <p class="mt-2 text-lg font-semibold">Te esperamos en Plaza de Abastos</p>
            <p class="text-white/70 text-sm mt-1">Atención personalizada y asesoramiento en tienda.</p>
          </div>
        </div>

        <form @submit.prevent="submitForm" class="bg-white rounded-2xl shadow-md border border-gray-100 p-8">
          <div class="mb-4">
            <label class="block text-sm font-bold text-gray-700 mb-2">Nombre</label>
            <input 
              v-model="form.nombre"
              type="text"
              required
              class="w-full px-4 py-3 border-2 border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold"
            >
          </div>

          <div class="mb-4">
            <label class="block text-sm font-bold text-gray-700 mb-2">Email</label>
            <input 
              v-model="form.email"
              type="email"
              required
              class="w-full px-4 py-3 border-2 border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold"
            >
          </div>

          <div class="mb-4">
            <label class="block text-sm font-bold text-gray-700 mb-2">Asunto</label>
            <input 
              v-model="form.asunto"
              type="text"
              required
              class="w-full px-4 py-3 border-2 border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold"
            >
          </div>

          <div class="mb-6">
            <label class="block text-sm font-bold text-gray-700 mb-2">Mensaje</label>
            <textarea 
              v-model="form.mensaje"
              required
              rows="5"
              class="w-full px-4 py-3 border-2 border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold"
            ></textarea>
          </div>

          <button 
            type="submit"
            :disabled="enviando"
            class="w-full bg-luxury-black text-white py-3 font-bold uppercase tracking-widest hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 rounded-lg"
          >
            {{ enviando ? 'Enviando...' : 'Enviar Mensaje' }}
          </button>

          <p v-if="mensaje" :class="{ 'text-luxury-gold': exito, 'text-red-600': !exito }" class="mt-4 text-center text-sm">
            {{ mensaje }}
          </p>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const logoSrc = import.meta.env.BASE_URL + 'logo/logo.jpg'

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
  mensaje.value = ''
  try {
    const response = await fetch('http://localhost:8080/api/contacto', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(form.value)
    })

    if (!response.ok) {
      throw new Error('Error en el envío')
    }

    const data = await response.json().catch(() => null)
    mensaje.value = data?.message || '¡Gracias por tu mensaje! Nos pondremos en contacto pronto.'
    exito.value = true
    form.value = { nombre: '', email: '', asunto: '', mensaje: '' }
  } catch (error) {
    mensaje.value = 'Error al enviar el mensaje. Intenta de nuevo.'
    exito.value = false
  } finally {
    enviando.value = false
  }
}
</script>
