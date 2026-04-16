<template>
  <div class="min-h-screen bg-gradient-to-b from-luxury-gray via-white to-luxury-gray">
    <!-- Hero Section -->
    <section class="bg-luxury-black text-white py-16 sm:py-20 lg:py-24">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="space-y-4 max-w-3xl">
          <p class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase">Venta Mayorista</p>
          <h1 class="font-serif text-4xl sm:text-5xl lg:text-6xl text-white leading-tight">
            Acceso a catálogo de profesionales
          </h1>
          <p class="text-lg text-gray-300 leading-relaxed">
            Amplía tu oferta de producto con las colecciones de Ibernovia. Acceso a tarifas mayoristas, solicitud de presupuestos y soporte comercial personalizado.
          </p>
        </div>
      </div>
    </section>

    <!-- Main Content -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12 lg:py-16">
      <div class="grid grid-cols-1 lg:grid-cols-[1.1fr_0.9fr] gap-8 lg:gap-10">
        <!-- Form Section -->
        <div>
          <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-8 sm:p-10">
            <div class="mb-8">
              <h2 class="font-serif text-3xl text-luxury-black mb-2">Solicitar acceso</h2>
              <p class="text-gray-600 text-sm">Completa el formulario y nos pondremos en contacto en 24/48h laborales para validar tu empresa.</p>
            </div>

            <form @submit.prevent="submitForm" class="space-y-6">
              <!-- Datos empresa -->
              <fieldset class="pb-6 border-b border-gray-100">
                <legend class="text-xs font-bold uppercase tracking-widest text-gray-500 mb-5 block">Datos de empresa</legend>
                <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
                  <div class="group">
                    <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
                      Nombre empresa
                      <span class="text-red-500">*</span>
                    </label>
                    <input
                      v-model="form.empresa"
                      required
                      type="text"
                      placeholder="Ej: Boutique Luna"
                      class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
                    />
                  </div>
                  <div class="group">
                    <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
                      CIF/NIF
                      <span class="text-red-500">*</span>
                    </label>
                    <input
                      v-model="form.cif"
                      required
                      type="text"
                      placeholder="Ej: A12345678"
                      class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
                    />
                  </div>
                </div>
              </fieldset>

              <!-- Datos de contacto -->
              <fieldset class="pb-6 border-b border-gray-100">
                <legend class="text-xs font-bold uppercase tracking-widest text-gray-500 mb-5 block">Información de contacto</legend>
                <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
                  <div class="group">
                    <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
                      Nombre de contacto
                      <span class="text-red-500">*</span>
                    </label>
                    <input
                      v-model="form.contacto"
                      required
                      type="text"
                      placeholder="Ej: María García"
                      class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
                    />
                  </div>
                  <div class="group">
                    <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
                      Teléfono
                      <span class="text-red-500">*</span>
                    </label>
                    <input
                      v-model="form.telefono"
                      required
                      type="tel"
                      placeholder="Ej: +34 600 123 456"
                      class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
                    />
                  </div>
                  <div class="group sm:col-span-2">
                    <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
                      Email profesional
                      <span class="text-red-500">*</span>
                    </label>
                    <input
                      v-model="form.email"
                      required
                      type="email"
                      placeholder="Ej: contacto@boutique.es"
                      class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
                    />
                  </div>
                  <div class="group">
                    <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
                      Provincia
                      <span class="text-red-500">*</span>
                    </label>
                    <input
                      v-model="form.provincia"
                      required
                      type="text"
                      placeholder="Ej: Sevilla"
                      class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition"
                    />
                  </div>
                </div>
              </fieldset>

              <!-- Mensaje adicional -->
              <fieldset>
                <legend class="text-xs font-bold uppercase tracking-widest text-gray-500 mb-5 block">Información adicional</legend>
                <div>
                  <label class="block text-xs uppercase tracking-widest text-gray-600 font-semibold mb-2">
                    ¿Algo más que nos gustaría saber?
                  </label>
                  <textarea
                    v-model="form.mensaje"
                    rows="4"
                    placeholder="Cuéntanos sobre tu negocio, especializaciones, volumen de compra estimado..."
                    class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:border-luxury-gold focus:ring-2 focus:ring-luxury-gold/20 transition resize-none"
                  ></textarea>
                </div>
              </fieldset>

              <!-- Términos -->
              <label class="flex items-start gap-3 p-4 bg-amber-50 rounded-lg border border-amber-200">
                <input
                  v-model="form.aceptaTerminos"
                  type="checkbox"
                  class="w-5 h-5 mt-0.5 accent-luxury-gold rounded"
                />
                <span class="text-xs text-amber-900">
                  Acepto que mis datos se usen para validar mi empresa y recibir información comercial.
                  <router-link to="/privacidad" class="underline text-luxury-gold hover:text-luxury-gold/80">Política de privacidad</router-link>
                </span>
              </label>

              <!-- Submit Button -->
              <button
                type="submit"
                :disabled="sending || !form.aceptaTerminos"
                class="w-full bg-luxury-black text-white py-4 font-bold uppercase tracking-widest text-sm rounded-lg hover:bg-luxury-gold hover:text-luxury-black transition disabled:opacity-50 disabled:cursor-not-allowed duration-300"
              >
                <span v-if="sending" class="flex items-center justify-center gap-2">
                  <svg class="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Procesando solicitud...
                </span>
                <span v-else>✓ Solicitar acceso empresarial</span>
              </button>

              <!-- Feedback -->
              <Transition name="fade">
                <div
                  v-if="feedback"
                  :class="feedbackOk ? 'bg-green-50 border-green-200 text-green-800' : 'bg-red-50 border-red-200 text-red-800'"
                  class="p-4 rounded-lg border text-sm flex gap-3"
                >
                  <span class="text-lg">{{ feedbackOk ? '✓' : '✗' }}</span>
                  <span>{{ feedback }}</span>
                </div>
              </Transition>
            </form>
          </div>
        </div>

        <!-- Side Info -->
        <div class="space-y-6">
          <!-- Process -->
          <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-8">
            <h3 class="font-serif text-2xl text-luxury-black mb-6">Proceso de validación</h3>
            <ol class="space-y-4">
              <li class="flex gap-4">
                <div class="flex-shrink-0 w-8 h-8 rounded-full bg-luxury-gold/10 flex items-center justify-center text-luxury-gold font-bold text-sm">1</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">Envía tu solicitud</p>
                  <p class="text-xs text-gray-600 mt-1">Completa el formulario con datos de tu empresa</p>
                </div>
              </li>
              <li class="flex gap-4">
                <div class="flex-shrink-0 w-8 h-8 rounded-full bg-luxury-gold/10 flex items-center justify-center text-luxury-gold font-bold text-sm">2</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">Validemos tu perfil</p>
                  <p class="text-xs text-gray-600 mt-1">24/48 horas laborales de análisis</p>
                </div>
              </li>
              <li class="flex gap-4">
                <div class="flex-shrink-0 w-8 h-8 rounded-full bg-luxury-gold/10 flex items-center justify-center text-luxury-gold font-bold text-sm">3</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">Recibe tu código de acceso</p>
                  <p class="text-xs text-gray-600 mt-1">Vía email con instrucciones</p>
                </div>
              </li>
              <li class="flex gap-4">
                <div class="flex-shrink-0 w-8 h-8 rounded-full bg-luxury-gold/10 flex items-center justify-center text-luxury-gold font-bold text-sm">4</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">¡Acceso completo!</p>
                  <p class="text-xs text-gray-600 mt-1">Disfruta tarifas, stock y presupuestos</p>
                </div>
              </li>
            </ol>
          </div>

          <!-- Conditions -->
          <div class="bg-white rounded-2xl shadow-lg border border-gray-100 p-8">
            <h3 class="font-serif text-xl text-luxury-black mb-5">Condiciones</h3>
            <ul class="space-y-3 text-sm text-gray-700">
              <li class="flex gap-3">
                <span class="text-luxury-gold font-bold">✓</span>
                <span>Venta exclusiva para comercios profesionales</span>
              </li>
              <li class="flex gap-3">
                <span class="text-luxury-gold font-bold">✓</span>
                <span>Tarifas mayoristas competitivas</span>
              </li>
              <li class="flex gap-3">
                <span class="text-luxury-gold font-bold">✓</span>
                <span>Soporte comercial dedicado</span>
              </li>
              <li class="flex gap-3">
                <span class="text-luxury-gold font-bold">✓</span>
                <span>Disponibilidad según campaña</span>
              </li>
              <li class="flex gap-3">
                <span class="text-luxury-gold font-bold">✓</span>
                <span>Condiciones de pago negociables</span>
              </li>
            </ul>
          </div>

          <!-- Trust -->
          <div class="bg-gradient-to-br from-luxury-gold/5 to-luxury-gold/10 rounded-2xl border border-luxury-gold/20 p-8">
            <h3 class="font-serif text-xl text-luxury-black mb-4">Confían en Ibernovia</h3>
            <p class="text-sm text-gray-700 mb-4">Trabajamos con más de 200 comercios profesionales en:</p>
            <div class="flex flex-wrap gap-2">
              <span class="px-3 py-2 text-xs font-semibold uppercase tracking-widest bg-white border border-gray-200 rounded-full text-gray-700">Andalucía</span>
              <span class="px-3 py-2 text-xs font-semibold uppercase tracking-widest bg-white border border-gray-200 rounded-full text-gray-700">Madrid</span>
              <span class="px-3 py-2 text-xs font-semibold uppercase tracking-widest bg-white border border-gray-200 rounded-full text-gray-700">Castilla-La Mancha</span>
              <span class="px-3 py-2 text-xs font-semibold uppercase tracking-widest bg-white border border-gray-200 rounded-full text-gray-700">Valencia</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- CTA Section -->
    <section class="bg-luxury-black text-white py-12 mt-12 lg:mt-16">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h2 class="font-serif text-3xl text-white mb-3">¿Dudas sobre el proceso?</h2>
        <p class="text-gray-300 mb-6">Contacta con nuestro equipo comercial para más información</p>
        <router-link to="/contacto" class="inline-block px-8 py-3 bg-luxury-gold text-luxury-black font-bold uppercase tracking-widest text-sm rounded-lg hover:bg-white transition">
          Enviar consulta
        </router-link>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { apiFetch } from '../lib/api'

const form = ref({
  empresa: '',
  cif: '',
  contacto: '',
  telefono: '',
  email: '',
  provincia: '',
  mensaje: '',
  aceptaTerminos: false
})

const sending = ref(false)
const feedback = ref('')
const feedbackOk = ref(false)

// Validaciones
const validarEmail = (email) => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return regex.test(email)
}

const validarTelefono = (telefono) => {
  const regex = /^[\d\s\+\-()]{9,}$/
  return regex.test(telefono.replace(/\s/g, ''))
}

const validarFormulario = () => {
  const errores = []

  if (!form.value.empresa.trim())
    errores.push('El nombre de empresa es requerido')
  if (!form.value.cif.trim())
    errores.push('El CIF/NIF es requerido')
  if (!form.value.contacto.trim())
    errores.push('El nombre de contacto es requerido')
  if (!form.value.telefono.trim())
    errores.push('El teléfono es requerido')
  if (!validarTelefono(form.value.telefono))
    errores.push('El teléfono no tiene formato válido')
  if (!form.value.email.trim())
    errores.push('El email es requerido')
  if (!validarEmail(form.value.email))
    errores.push('El email no es válido')
  if (!form.value.provincia.trim())
    errores.push('La provincia es requerida')
  if (!form.value.aceptaTerminos)
    errores.push('Debes aceptar los términos')

  return errores
}

const submitForm = async () => {
  // Validar
  const errores = validarFormulario()
  if (errores.length > 0) {
    feedbackOk.value = false
    feedback.value = `Errores: ${errores.join(', ')}`
    return
  }

  sending.value = true
  feedback.value = ''

  try {
    const payload = {
      nombre: form.value.contacto,
      email: form.value.email,
      asunto: `Alta empresarial • ${form.value.empresa}`,
      mensaje: `
SOLICITUD DE ACCESO EMPRESARIAL
═══════════════════════════════════

📍 Empresa: ${form.value.empresa}
🔢 CIF/NIF: ${form.value.cif}
👤 Contacto: ${form.value.contacto}
📞 Teléfono: ${form.value.telefono}
📧 Email: ${form.value.email}
📍 Provincia: ${form.value.provincia}

Mensaje adicional:
${form.value.mensaje || '(Sin mensaje adicional)'}
      `.trim()
    }

    const response = await apiFetch('/api/contacto', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })

    if (!response.ok) {
      const data = await response.json().catch(() => ({}))
      throw new Error(data.message || 'No se pudo enviar la solicitud')
    }

    feedbackOk.value = true
    feedback.value = '✓ Solicitud guardada correctamente. Te contactaremos en 24/48 horas laborales con tu código de acceso.'
    
    // Limpiar formulario
    form.value = {
      empresa: '',
      cif: '',
      contacto: '',
      telefono: '',
      email: '',
      provincia: '',
      mensaje: '',
      aceptaTerminos: false
    }
  } catch (error) {
    feedbackOk.value = false
    feedback.value = `✗ ${error.message || 'Hubo un error. Inténtalo de nuevo en unos minutos.'}`
  } finally {
    sending.value = false
  }
}
</script>

<style scoped>
@keyframes fadeInScale {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.fade-enter-active {
  animation: fadeInScale 0.3s ease-out;
}

.fade-leave-active {
  animation: fadeInScale 0.3s ease-out reverse;
}

fieldset {
  border: none;
  padding: 0;
  margin: 0;
}

legend {
  padding: 0;
  margin: 0;
}
</style>
