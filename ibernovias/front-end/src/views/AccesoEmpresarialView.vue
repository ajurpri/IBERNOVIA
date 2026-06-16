<template>
  <div class="min-h-screen bg-[#fdfdfc] flex flex-col justify-between">
    <!-- Header Section (Sophisticated Editorial Banner) -->
    <header class="relative py-16 lg:py-24 border-b border-black/5 overflow-hidden" aria-label="Cabecera">
      <div class="absolute inset-0 bg-[radial-gradient(circle_at_70%_20%,rgba(168,117,71,0.04),transparent_50%)] pointer-events-none"></div>
      
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10 text-center lg:text-left">
        <div class="space-y-4 max-w-3xl">
          <p class="text-luxury-gold text-xs font-bold tracking-[0.3em] uppercase animate-fade-in-up">
            Servicio Mayorista B2B
          </p>
          <h1 class="font-serif text-4xl sm:text-5xl lg:text-6xl font-light text-luxury-black tracking-wide leading-tight animate-fade-in-up delay-75">
            Acceso Profesional
          </h1>
          <p class="text-sm sm:text-base text-gray-600 leading-relaxed max-w-2xl animate-fade-in-up delay-150">
            Únete a nuestra red de comercios asociados. Habilita tu cuenta para consultar tarifas mayoristas exclusivas, stock en tiempo real y gestionar presupuestos directamente desde nuestro atelier digital.
          </p>
        </div>
      </div>
    </header>

    <!-- Main Content Area -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12 lg:py-20 flex-1">
      <div class="grid grid-cols-1 lg:grid-cols-12 gap-12 lg:gap-16 items-start">
        
        <!-- Form Column (Left 7 Columns) -->
        <div class="lg:col-span-7 bg-[#fbfbfa] border border-black/5 rounded-2xl p-8 sm:p-12 shadow-sm animate-fade-in-up delay-75">
          <div class="mb-10">
            <h2 class="font-serif text-2xl sm:text-3xl font-light text-luxury-black tracking-wide">Solicitud de Acceso</h2>
            <p class="text-gray-500 text-xs mt-2 uppercase tracking-wider">Validación en 24/48 horas laborables</p>
          </div>

          <form @submit.prevent="submitForm" class="space-y-8">
            <!-- Sección: Datos de la Empresa -->
            <fieldset class="space-y-6">
              <legend class="text-[10px] uppercase tracking-[0.3em] text-luxury-gold font-bold mb-4">Datos de la Empresa</legend>
              
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
                <!-- Nombre Empresa -->
                <div class="relative group">
                  <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                    Nombre comercial <span class="text-luxury-gold">*</span>
                  </label>
                  <input
                    v-model="form.empresa"
                    required
                    type="text"
                    placeholder="Ej: Boutique Nupcial"
                    class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                  />
                </div>
                <!-- CIF/NIF -->
                <div class="relative group">
                  <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                    CIF / NIF <span class="text-luxury-gold">*</span>
                  </label>
                  <input
                    v-model="form.cif"
                    required
                    type="text"
                    placeholder="Ej: B12345678"
                    class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                  />
                </div>
              </div>
            </fieldset>

            <!-- Sección: Información de Contacto -->
            <fieldset class="space-y-6 pt-4 border-t border-black/5">
              <legend class="text-[10px] uppercase tracking-[0.3em] text-luxury-gold font-bold mb-4">Información de Contacto</legend>
              
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
                <!-- Persona de Contacto -->
                <div class="relative group">
                  <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                    Persona de contacto <span class="text-luxury-gold">*</span>
                  </label>
                  <input
                    v-model="form.contacto"
                    required
                    type="text"
                    placeholder="Ej: Carmen Gómez"
                    class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                  />
                </div>
                <!-- Teléfono -->
                <div class="relative group">
                  <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                    Teléfono comercial <span class="text-luxury-gold">*</span>
                  </label>
                  <input
                    v-model="form.telefono"
                    required
                    type="tel"
                    placeholder="Ej: +34 600 123 456"
                    class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                  />
                </div>
                <!-- Email profesional -->
                <div class="relative group sm:col-span-2">
                  <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                    Email profesional <span class="text-luxury-gold">*</span>
                  </label>
                  <input
                    v-model="form.email"
                    required
                    type="email"
                    placeholder="Ej: comercial@empresa.com"
                    class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                  />
                </div>
                <!-- Provincia -->
                <div class="relative group">
                  <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-1 transition-colors group-focus-within:text-luxury-gold">
                    Provincia <span class="text-luxury-gold">*</span>
                  </label>
                  <input
                    v-model="form.provincia"
                    required
                    type="text"
                    placeholder="Ej: Jaén"
                    class="w-full pb-3 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30"
                  />
                </div>
              </div>
            </fieldset>

            <!-- Sección: Detalles Adicionales -->
            <fieldset class="space-y-6 pt-4 border-t border-black/5">
              <legend class="text-[10px] uppercase tracking-[0.3em] text-luxury-gold font-bold mb-4">Información Adicional</legend>
              <div class="relative group">
                <label class="block text-[10px] uppercase tracking-[0.25em] text-gray-400 font-bold mb-2 transition-colors group-focus-within:text-luxury-gold">
                  Cuéntanos sobre tu negocio
                </label>
                <textarea
                  v-model="form.mensaje"
                  rows="3"
                  placeholder="Detalla especializaciones, volumen estimado, marcas con las que trabajas..."
                  class="w-full pb-2 pt-1 border-b border-gray-300 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold transition-colors text-sm text-luxury-black placeholder-gray-400/30 resize-none"
                ></textarea>
              </div>
            </fieldset>

            <!-- Aceptación de Términos -->
            <div class="p-4 bg-white border border-black/5 rounded-xl flex items-start gap-4">
              <input
                v-model="form.aceptaTerminos"
                type="checkbox"
                id="terms"
                class="w-4 h-4 mt-0.5 accent-luxury-gold border-gray-300 rounded focus:ring-0"
              />
              <label for="terms" class="text-xs text-gray-600 leading-relaxed cursor-pointer select-none">
                Confirmo la veracidad de los datos aportados y acepto que sean tratados para validar mi perfil comercial de acuerdo con la 
                <router-link to="/privacidad" class="underline text-luxury-gold hover:text-luxury-gold/80 transition-colors font-semibold">Política de Privacidad</router-link>.
              </label>
            </div>

            <!-- Submit Button -->
            <button
              type="submit"
              :disabled="sending || !form.aceptaTerminos"
              class="w-full bg-luxury-black text-white hover:bg-luxury-gold hover:text-luxury-black transition-all duration-300 py-4 font-bold uppercase tracking-[0.2em] text-xs sm:text-sm rounded-full shadow-md disabled:opacity-40 disabled:cursor-not-allowed hover:-translate-y-0.5 active:translate-y-0"
            >
              <span v-if="sending" class="flex items-center justify-center gap-3">
                <svg class="animate-spin h-4 w-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Procesando solicitud...
              </span>
              <span v-else>✓ Enviar Solicitud Comercial</span>
            </button>

            <!-- Feedback Notifications -->
            <Transition name="fade">
              <div
                v-if="feedback"
                :class="feedbackOk ? 'bg-green-50 border-green-200 text-green-800' : 'bg-red-50 border-red-200 text-red-800'"
                class="p-4 rounded-xl border text-sm flex gap-3 animate-fade-in"
              >
                <span class="text-base font-bold">{{ feedbackOk ? '✓' : '✗' }}</span>
                <p class="leading-relaxed">{{ feedback }}</p>
              </div>
            </Transition>
          </form>
        </div>

        <!-- Info Column (Right 5 Columns) -->
        <div class="lg:col-span-5 space-y-8 animate-fade-in-up delay-150">
          
          <!-- Validation Process -->
          <div class="bg-[#fbfbfa] border border-black/5 rounded-2xl p-8 sm:p-10 shadow-sm">
            <h3 class="font-serif text-xl sm:text-2xl font-light text-luxury-black tracking-wide mb-8">El proceso B2B</h3>
            <ol class="space-y-6 relative border-l border-black/5 ml-3 pl-6">
              <li class="relative">
                <div class="absolute -left-[35px] top-0.5 w-6.5 h-6.5 rounded-full bg-[#fdfdfc] border-2 border-luxury-gold flex items-center justify-center text-[10px] font-bold text-luxury-black">1</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">Registro de datos</p>
                  <p class="text-xs text-gray-500 mt-1">Envías la solicitud con el CIF y contacto comercial.</p>
                </div>
              </li>
              <li class="relative">
                <div class="absolute -left-[35px] top-0.5 w-6.5 h-6.5 rounded-full bg-[#fdfdfc] border-2 border-luxury-gold flex items-center justify-center text-[10px] font-bold text-luxury-black">2</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">Validación del perfil</p>
                  <p class="text-xs text-gray-500 mt-1">Revisamos la actividad comercial de tu negocio (24/48h).</p>
                </div>
              </li>
              <li class="relative">
                <div class="absolute -left-[35px] top-0.5 w-6.5 h-6.5 rounded-full bg-[#fdfdfc] border-2 border-luxury-gold flex items-center justify-center text-[10px] font-bold text-luxury-black">3</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">Activación de la cuenta</p>
                  <p class="text-xs text-gray-500 mt-1">Te notificamos la activación y asignamos perfil de Empresa.</p>
                </div>
              </li>
              <li class="relative">
                <div class="absolute -left-[35px] top-0.5 w-6.5 h-6.5 rounded-full bg-[#fdfdfc] border-2 border-luxury-gold flex items-center justify-center text-[10px] font-bold text-luxury-black">4</div>
                <div>
                  <p class="font-semibold text-luxury-black text-sm">Acceso al Atelier</p>
                  <p class="text-xs text-gray-500 mt-1">Inicias sesión y accedes a precios, catálogo y presupuestos.</p>
                </div>
              </li>
            </ol>
          </div>

          <!-- Conditions List -->
          <div class="bg-[#fbfbfa] border border-black/5 rounded-2xl p-8 sm:p-10 shadow-sm">
            <h3 class="font-serif text-lg font-light text-luxury-black tracking-wide mb-6">Condiciones de Servicio</h3>
            <ul class="space-y-4 text-xs sm:text-sm text-gray-600">
              <li class="flex items-start gap-3">
                <span class="text-luxury-gold mt-0.5 font-bold">✓</span>
                <span>Distribución exclusiva para comercios físicos o portales del sector ceremonia.</span>
              </li>
              <li class="flex items-start gap-3">
                <span class="text-luxury-gold mt-0.5 font-bold">✓</span>
                <span>Tarifas comerciales mayoristas y disponibilidad de stock según campaña.</span>
              </li>
              <li class="flex items-start gap-3">
                <span class="text-luxury-gold mt-0.5 font-bold">✓</span>
                <span>Asistencia personalizada de nuestro equipo de concierge comercial.</span>
              </li>
              <li class="flex items-start gap-3">
                <span class="text-luxury-gold mt-0.5 font-bold">✓</span>
                <span>Pedido mínimo adaptado y condiciones de compra negociadas a largo plazo.</span>
              </li>
            </ul>
          </div>

          <!-- Trust & Location -->
          <div class="bg-gradient-to-br from-[#fbfbfa] to-[#f5f2eb] border border-black/5 rounded-2xl p-8 sm:p-10 shadow-sm">
            <h3 class="font-serif text-lg font-light text-luxury-black tracking-wide mb-3">Soporte Ibernovia</h3>
            <p class="text-xs text-gray-600 leading-relaxed mb-4">
              Atendemos y damos servicio a boutiques y ateliers especializados en toda España desde nuestra sede central en Andújar.
            </p>
            <div class="flex flex-wrap gap-1.5">
              <span class="px-3 py-1.5 text-[9px] font-bold uppercase tracking-wider bg-white/95 border border-black/5 rounded-full text-luxury-black">Andalucía</span>
              <span class="px-3 py-1.5 text-[9px] font-bold uppercase tracking-wider bg-white/95 border border-black/5 rounded-full text-luxury-black">Madrid</span>
              <span class="px-3 py-1.5 text-[9px] font-bold uppercase tracking-wider bg-white/95 border border-black/5 rounded-full text-luxury-black">Comunidad Valenciana</span>
              <span class="px-3 py-1.5 text-[9px] font-bold uppercase tracking-wider bg-white/95 border border-black/5 rounded-full text-luxury-black">Castilla-La Mancha</span>
            </div>
          </div>
        </div>

      </div>
    </main>

    <!-- Support CTA Footer Section -->
    <section class="bg-luxury-black text-white py-16 mt-16 border-t border-white/5">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center space-y-4">
        <h2 class="font-serif text-2xl sm:text-3xl font-light text-white tracking-wide">¿Tienes dudas sobre las condiciones?</h2>
        <p class="text-gray-400 text-sm max-w-lg mx-auto">Nuestro departamento de atención al profesional te atenderá de inmediato para facilitarte cualquier detalle.</p>
        <div class="pt-4">
          <router-link
            to="/contacto"
            class="inline-flex items-center px-8 py-3.5 bg-white text-luxury-black hover:bg-luxury-gold hover:text-luxury-black transition-all duration-300 rounded-full text-xs uppercase tracking-[0.2em] font-bold"
          >
            Contactar ahora
          </router-link>
        </div>
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
    feedback.value = `Hubo un error al procesar tu solicitud: ${error.message || 'Inténtalo de nuevo en unos minutos.'}`
  } finally {
    sending.value = false
  }
}
</script>

<style scoped>
/* Transiciones */
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

.animate-fade-in-up {
  animation: fadeInUp 1s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.delay-75 {
  animation-delay: 75ms;
}

.delay-150 {
  animation-delay: 150ms;
}

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
  animation: fadeInScale 0.35s ease-out;
}

.fade-leave-active {
  animation: fadeInScale 0.3s ease-out reverse;
}

fieldset {
  border: none;
  padding: 0;
  margin: 0;
}
</style>
