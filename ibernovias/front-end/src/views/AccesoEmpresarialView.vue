<template>
  <div class="b2b-shell min-h-screen">
    <section class="b2b-hero">
      <div class="b2b-glow b2b-glow-left"></div>
      <div class="b2b-glow b2b-glow-right"></div>

      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-14 pb-12 sm:pt-16 sm:pb-14 lg:pt-20 lg:pb-16">
        <div class="grid grid-cols-1 lg:grid-cols-[1.08fr_0.92fr] gap-10 xl:gap-16 items-start">
          <div class="b2b-hero-copy animate-rise">
            <p class="b2b-kicker">Acceso comercial Ibernovia</p>
            <h1 class="b2b-title">Alta profesional para tiendas y boutiques del sector ceremonia</h1>
            <p class="b2b-intro">
              Esta area esta pensada para negocios que necesitan trabajar el catalogo de Ibernovia con una relacion comercial estable, tarifas reservadas y atencion mas directa.
            </p>

            <div class="b2b-hero-points">
              <div class="b2b-point-card">
                <span class="b2b-point-label">01</span>
                <p class="b2b-point-copy">Validacion manual del perfil comercial antes de habilitar el acceso.</p>
              </div>
              <div class="b2b-point-card">
                <span class="b2b-point-label">02</span>
                <p class="b2b-point-copy">Consulta de tarifas, stock y operativa desde una cuenta profesional autorizada.</p>
              </div>
              <div class="b2b-point-card">
                <span class="b2b-point-label">03</span>
                <p class="b2b-point-copy">Soporte cercano para campanas, reposicion y seguimiento de pedidos.</p>
              </div>
            </div>
          </div>

          <aside class="b2b-hero-panel animate-rise delay-100">
            <div class="b2b-panel-card">
              <p class="b2b-panel-kicker">Perfil que buscamos</p>
              <h2 class="b2b-panel-title">Negocio real, criterio comercial y continuidad de compra</h2>
              <p class="b2b-panel-copy">
                Priorizamos boutiques, ateliers, tiendas multimarca y proyectos especializados en novia, fiesta o ceremonia que quieran trabajar una seleccion cuidada y coherente.
              </p>

              <div class="b2b-panel-metrics">
                <div class="b2b-metric">
                  <span class="b2b-metric-value">24/48h</span>
                  <span class="b2b-metric-label">Revision habitual</span>
                </div>
                <div class="b2b-metric">
                  <span class="b2b-metric-value">B2B</span>
                  <span class="b2b-metric-label">Tarifa reservada</span>
                </div>
                <div class="b2b-metric">
                  <span class="b2b-metric-value">Directo</span>
                  <span class="b2b-metric-label">Soporte comercial</span>
                </div>
              </div>

              <div class="b2b-panel-note">
                <p class="b2b-panel-note-title">Revision personalizada</p>
                <p class="b2b-panel-note-copy">
                  Revisamos el tipo de negocio, contacto y encaje comercial antes de activar la cuenta.
                </p>
              </div>
            </div>
          </aside>
        </div>
      </div>
    </section>

    <section class="b2b-main-section">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pb-16 sm:pb-20">
        <div class="grid grid-cols-1 lg:grid-cols-[1.05fr_0.95fr] gap-10 xl:gap-14 items-start">
          <div class="space-y-8 animate-rise delay-150">
            <div class="b2b-form-card">
              <div class="b2b-form-head">
                <div>
                  <p class="b2b-section-label">Solicitud de alta</p>
                  <h2 class="b2b-form-title">Completa tu perfil comercial</h2>
                </div>
                <div class="b2b-form-chip">Respuesta habitual en 24/48h laborables</div>
              </div>

              <form @submit.prevent="submitForm" class="space-y-8">
                <fieldset class="space-y-6">
                  <legend class="b2b-fieldset-title">Datos de la empresa</legend>

                  <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
                    <label class="b2b-input-group">
                      <span class="b2b-input-label">Nombre comercial <span class="text-luxury-gold">*</span></span>
                      <input
                        v-model="form.empresa"
                        required
                        type="text"
                        placeholder="Ej: Boutique Nupcial"
                        class="b2b-input"
                      >
                    </label>

                    <label class="b2b-input-group">
                      <span class="b2b-input-label">CIF / NIF <span class="text-luxury-gold">*</span></span>
                      <input
                        v-model="form.cif"
                        required
                        type="text"
                        placeholder="Ej: B12345678"
                        class="b2b-input"
                      >
                    </label>
                  </div>
                </fieldset>

                <fieldset class="space-y-6">
                  <legend class="b2b-fieldset-title">Informacion de contacto</legend>

                  <div class="grid grid-cols-1 sm:grid-cols-2 gap-5">
                    <label class="b2b-input-group">
                      <span class="b2b-input-label">Persona de contacto <span class="text-luxury-gold">*</span></span>
                      <input
                        v-model="form.contacto"
                        required
                        type="text"
                        placeholder="Ej: Carmen Gomez"
                        class="b2b-input"
                      >
                    </label>

                    <label class="b2b-input-group">
                      <span class="b2b-input-label">Telefono comercial <span class="text-luxury-gold">*</span></span>
                      <input
                        v-model="form.telefono"
                        required
                        type="tel"
                        placeholder="Ej: +34 600 123 456"
                        class="b2b-input"
                      >
                    </label>

                    <label class="b2b-input-group sm:col-span-2">
                      <span class="b2b-input-label">Email profesional <span class="text-luxury-gold">*</span></span>
                      <input
                        v-model="form.email"
                        required
                        type="email"
                        placeholder="Ej: comercial@empresa.com"
                        class="b2b-input"
                      >
                    </label>

                    <label class="b2b-input-group">
                      <span class="b2b-input-label">Provincia <span class="text-luxury-gold">*</span></span>
                      <input
                        v-model="form.provincia"
                        required
                        type="text"
                        placeholder="Ej: Jaen"
                        class="b2b-input"
                      >
                    </label>
                  </div>
                </fieldset>

                <fieldset class="space-y-6">
                  <legend class="b2b-fieldset-title">Contexto del negocio</legend>

                  <label class="b2b-input-group">
                    <span class="b2b-input-label">Cuestanos brevemente tu actividad</span>
                    <textarea
                      v-model="form.mensaje"
                      rows="4"
                      placeholder="Describe tu tienda, especializacion, tipo de cliente o campanas que trabajas."
                      class="b2b-input b2b-textarea"
                    ></textarea>
                  </label>
                </fieldset>

                <div class="b2b-terms-box">
                  <input
                    v-model="form.aceptaTerminos"
                    type="checkbox"
                    id="terms"
                    class="mt-1 h-4 w-4 accent-luxury-gold"
                  >
                  <label for="terms" class="b2b-terms-copy">
                    Confirmo la veracidad de los datos aportados y acepto su tratamiento para revisar mi solicitud comercial conforme a la
                    <router-link to="/privacidad" class="b2b-inline-link">Politica de Privacidad</router-link>.
                  </label>
                </div>

                <button
                  type="submit"
                  :disabled="sending || !form.aceptaTerminos"
                  class="b2b-submit-button"
                >
                  <span v-if="sending">Procesando solicitud...</span>
                  <span v-else>Enviar solicitud comercial</span>
                </button>

                <Transition name="fade">
                  <div
                    v-if="feedback"
                    class="b2b-feedback"
                    :class="feedbackOk ? 'b2b-feedback-ok' : 'b2b-feedback-error'"
                  >
                    <span class="b2b-feedback-icon">{{ feedbackOk ? 'OK' : 'X' }}</span>
                    <p>{{ feedback }}</p>
                  </div>
                </Transition>
              </form>
            </div>
          </div>

          <div class="space-y-8 animate-rise delay-200">
            <div class="b2b-info-card">
              <p class="b2b-section-label">Como funciona</p>
              <h3 class="b2b-side-title">Un acceso pensado para trabajar mejor, no para abrir por abrir</h3>

              <ol class="b2b-steps">
                <li class="b2b-step">
                  <span class="b2b-step-number">1</span>
                  <div>
                    <p class="b2b-step-title">Recibimos tu solicitud</p>
                    <p class="b2b-step-copy">Recogemos los datos de empresa, contacto y el contexto comercial que nos facilitas.</p>
                  </div>
                </li>
                <li class="b2b-step">
                  <span class="b2b-step-number">2</span>
                  <div>
                    <p class="b2b-step-title">Revisamos encaje y actividad</p>
                    <p class="b2b-step-copy">Valoramos si el perfil corresponde a una tienda o proyecto profesional alineado con la marca.</p>
                  </div>
                </li>
                <li class="b2b-step">
                  <span class="b2b-step-number">3</span>
                  <div>
                    <p class="b2b-step-title">Activamos tu cuenta</p>
                    <p class="b2b-step-copy">Cuando el alta se aprueba, habilitamos el acceso profesional y te avisamos por correo.</p>
                  </div>
                </li>
              </ol>
            </div>

            <div class="b2b-info-card b2b-info-card-soft">
              <p class="b2b-section-label">Que incluye</p>
              <h3 class="b2b-side-title">Una relacion mas clara y mas util para el dia a dia</h3>

              <ul class="b2b-benefits">
                <li>Consulta de tarifas profesionales y disponibilidad segun campana.</li>
                <li>Acceso a una operativa mas ordenada para presupuestos y pedidos.</li>
                <li>Atencion comercial directa para seguimiento y reposicion.</li>
                <li>Contexto de coleccion mas trabajado para presentar mejor el producto.</li>
              </ul>
            </div>

            <div class="b2b-contact-card">
              <p class="b2b-section-label">Contacto directo</p>
              <h3 class="b2b-side-title">Si prefieres hablarlo antes, estamos disponibles</h3>
              <div class="b2b-contact-list">
                <div>
                  <span class="b2b-contact-label">Telefono</span>
                  <p class="b2b-contact-value">953 51 50 70</p>
                </div>
                <div>
                  <span class="b2b-contact-label">Email</span>
                  <p class="b2b-contact-value">info@ibernovia.es</p>
                </div>
                <div>
                  <span class="b2b-contact-label">Atencion</span>
                  <p class="b2b-contact-value">Lunes a viernes, 09:00 a 20:00</p>
                </div>
              </div>
              <router-link to="/contacto" class="b2b-secondary-link">Ir a contacto</router-link>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="b2b-footer-cta">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-14 sm:py-16">
        <div class="b2b-footer-card">
          <div>
            <p class="b2b-section-label b2b-section-label-light">Acceso con criterio</p>
            <h2 class="b2b-footer-title">Queremos que la relacion profesional con Ibernovia empiece bien desde el primer contacto</h2>
            <p class="b2b-footer-copy">
              Si tu negocio encaja con la linea de trabajo de la marca, estaremos encantados de revisar tu solicitud y abrir una conversacion comercial contigo.
            </p>
          </div>
          <div class="b2b-footer-actions">
            <a href="#top" class="b2b-footer-button b2b-footer-button-light">Volver arriba</a>
            <router-link to="/tienda" class="b2b-footer-button b2b-footer-button-outline">Ver catalogo</router-link>
          </div>
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

const validarEmail = (email) => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return regex.test(email)
}

const validarTelefono = (telefono) => {
  const regex = /^[\d\s+\-()]{9,}$/
  return regex.test(telefono.replace(/\s/g, ''))
}

const validarFormulario = () => {
  const errores = []

  if (!form.value.empresa.trim()) errores.push('El nombre de empresa es requerido')
  if (!form.value.cif.trim()) errores.push('El CIF/NIF es requerido')
  if (!form.value.contacto.trim()) errores.push('El nombre de contacto es requerido')
  if (!form.value.telefono.trim()) errores.push('El telefono es requerido')
  if (!validarTelefono(form.value.telefono)) errores.push('El telefono no tiene formato valido')
  if (!form.value.email.trim()) errores.push('El email es requerido')
  if (!validarEmail(form.value.email)) errores.push('El email no es valido')
  if (!form.value.provincia.trim()) errores.push('La provincia es requerida')
  if (!form.value.aceptaTerminos) errores.push('Debes aceptar los terminos')

  return errores
}

const submitForm = async () => {
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
      asunto: `Alta empresarial · ${form.value.empresa}`,
      mensaje: `
SOLICITUD DE ACCESO EMPRESARIAL
===================================

Empresa: ${form.value.empresa}
CIF/NIF: ${form.value.cif}
Contacto: ${form.value.contacto}
Telefono: ${form.value.telefono}
Email: ${form.value.email}
Provincia: ${form.value.provincia}

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
    feedback.value = 'Solicitud enviada correctamente. Revisaremos el perfil y te responderemos en 24/48 horas laborables.'

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
    feedback.value = `Hubo un error al procesar tu solicitud: ${error.message || 'Intentalo de nuevo en unos minutos.'}`
  } finally {
    sending.value = false
  }
}
</script>

<style scoped>
.b2b-shell {
  background:
    radial-gradient(circle at top left, rgb(var(--luxury-gold-rgb) / 0.12), transparent 24%),
    linear-gradient(180deg, #f7f2ea 0%, #fbf8f3 16%, #ffffff 54%, #f8f4ed 100%);
}

.b2b-hero {
  position: relative;
  overflow: hidden;
}

.b2b-glow {
  position: absolute;
  width: 28rem;
  height: 28rem;
  border-radius: 999px;
  filter: blur(72px);
  opacity: 0.22;
  pointer-events: none;
}

.b2b-glow-left {
  top: -9rem;
  left: -10rem;
  background: rgb(var(--luxury-gold-rgb) / 0.7);
}

.b2b-glow-right {
  top: 4rem;
  right: -12rem;
  background: rgb(var(--luxury-black-rgb) / 0.34);
}

.b2b-kicker,
.b2b-section-label,
.b2b-input-label,
.b2b-fieldset-title,
.b2b-panel-kicker {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.28em;
  text-transform: uppercase;
}

.b2b-kicker,
.b2b-section-label,
.b2b-panel-kicker,
.b2b-fieldset-title {
  color: rgb(var(--luxury-gold-rgb) / 0.96);
}

.b2b-title,
.b2b-panel-title,
.b2b-form-title,
.b2b-side-title,
.b2b-footer-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  color: var(--luxury-black);
}

.b2b-title {
  margin-top: 0.9rem;
  font-size: clamp(2.8rem, 5vw, 5.4rem);
  line-height: 0.95;
  max-width: 12ch;
}

.b2b-intro,
.b2b-panel-copy,
.b2b-point-copy,
.b2b-step-copy,
.b2b-footer-copy,
.b2b-panel-note-copy,
.b2b-terms-copy,
.b2b-contact-value,
.b2b-contact-label {
  color: rgb(var(--luxury-black-rgb) / 0.68);
}

.b2b-intro {
  margin-top: 1.15rem;
  max-width: 58ch;
  font-size: 15px;
  line-height: 1.85;
}

.b2b-hero-points {
  margin-top: 2rem;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.8rem;
}

.b2b-point-card,
.b2b-panel-card,
.b2b-form-card,
.b2b-info-card,
.b2b-contact-card {
  border-radius: 28px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  background: rgb(255 255 255 / 0.8);
  box-shadow:
    0 24px 50px rgb(var(--luxury-black-rgb) / 0.06),
    inset 0 1px 0 rgb(255 255 255 / 0.88);
  backdrop-filter: blur(14px);
}

.b2b-point-card {
  padding: 1rem;
  min-height: 144px;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.92), rgb(249 244 236 / 0.9));
}

.b2b-point-label {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 42px;
  height: 42px;
  border-radius: 14px;
  background: linear-gradient(145deg, rgb(38 31 26), rgb(78 64 50));
  color: white;
  font-size: 11px;
  letter-spacing: 0.22em;
}

.b2b-point-copy {
  margin-top: 0.9rem;
  font-size: 13px;
  line-height: 1.7;
}

.b2b-hero-panel {
  position: relative;
}

.b2b-panel-card {
  padding: 1.45rem;
  background:
    linear-gradient(135deg, rgb(31 26 22 / 0.98), rgb(63 50 39 / 0.95)),
    var(--luxury-black);
  color: white;
  box-shadow: 0 30px 55px rgb(var(--luxury-black-rgb) / 0.18);
}

.b2b-panel-card .b2b-panel-kicker,
.b2b-panel-card .b2b-panel-title,
.b2b-panel-card .b2b-panel-copy,
.b2b-panel-card .b2b-metric-value,
.b2b-panel-card .b2b-metric-label,
.b2b-panel-card .b2b-panel-note-title,
.b2b-panel-card .b2b-panel-note-copy {
  color: white;
}

.b2b-panel-title {
  margin-top: 0.7rem;
  font-size: clamp(2rem, 3vw, 3rem);
  line-height: 0.98;
}

.b2b-panel-copy {
  margin-top: 1rem;
  font-size: 14px;
  line-height: 1.8;
  opacity: 0.82;
}

.b2b-panel-metrics {
  margin-top: 1.3rem;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 0.7rem;
}

.b2b-metric {
  padding: 0.9rem 0.8rem;
  border-radius: 18px;
  background: rgb(255 255 255 / 0.08);
  border: 1px solid rgb(255 255 255 / 0.08);
}

.b2b-metric-value {
  display: block;
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 1.45rem;
}

.b2b-metric-label {
  display: block;
  margin-top: 0.2rem;
  font-size: 10px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  opacity: 0.75;
}

.b2b-panel-note {
  margin-top: 1.2rem;
  padding: 1rem;
  border-radius: 20px;
  background: rgb(255 255 255 / 0.08);
}

.b2b-panel-note-title {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.16em;
  text-transform: uppercase;
}

.b2b-panel-note-copy {
  margin-top: 0.35rem;
  font-size: 13px;
  line-height: 1.7;
  opacity: 0.76;
}

.b2b-main-section {
  position: relative;
  margin-top: -1.2rem;
}

.b2b-form-card,
.b2b-info-card,
.b2b-contact-card {
  padding: 1.45rem;
}

.b2b-form-head {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 1rem;
  align-items: flex-start;
  margin-bottom: 1.4rem;
}

.b2b-form-title {
  margin-top: 0.45rem;
  font-size: clamp(2rem, 3vw, 3rem);
  line-height: 1;
}

.b2b-form-chip {
  padding: 0.7rem 0.95rem;
  border-radius: 999px;
  background: rgb(var(--luxury-gold-rgb) / 0.1);
  color: var(--luxury-black);
  font-size: 10px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

fieldset {
  border: none;
  margin: 0;
  padding: 0;
}

.b2b-input-group {
  display: block;
}

.b2b-input-label {
  display: block;
  margin-bottom: 0.65rem;
  color: rgb(var(--luxury-black-rgb) / 0.5);
}

.b2b-input {
  width: 100%;
  min-height: 54px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.18);
  border-radius: 18px;
  padding: 0.95rem 1rem;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.96), rgb(249 245 238 / 0.9));
  color: var(--luxury-black);
  font-size: 14px;
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease, transform 0.2s ease;
}

.b2b-input:focus {
  border-color: rgb(var(--luxury-gold-rgb) / 0.56);
  box-shadow: 0 0 0 4px rgb(var(--luxury-gold-rgb) / 0.08);
}

.b2b-input::placeholder {
  color: rgb(var(--luxury-black-rgb) / 0.34);
}

.b2b-textarea {
  min-height: 140px;
  resize: vertical;
}

.b2b-terms-box {
  display: grid;
  grid-template-columns: 18px 1fr;
  gap: 0.8rem;
  align-items: start;
  padding: 1rem;
  border-radius: 18px;
  background: rgb(251 248 243 / 0.9);
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.16);
}

.b2b-terms-copy {
  font-size: 13px;
  line-height: 1.7;
}

.b2b-inline-link {
  color: var(--luxury-black);
  font-weight: 700;
  text-decoration: none;
  border-bottom: 1px solid rgb(var(--luxury-gold-rgb) / 0.4);
}

.b2b-inline-link:hover {
  color: var(--luxury-gold);
}

.b2b-submit-button,
.b2b-secondary-link,
.b2b-footer-button {
  min-height: 54px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0 1.5rem;
  text-decoration: none;
  text-transform: uppercase;
  letter-spacing: 0.18em;
  font-size: 11px;
  font-weight: 700;
  transition: transform 0.2s ease, box-shadow 0.2s ease, opacity 0.2s ease, background 0.2s ease, color 0.2s ease, border-color 0.2s ease;
}

.b2b-submit-button {
  width: 100%;
  border: none;
  background: linear-gradient(135deg, rgb(34 29 24), rgb(71 59 48));
  color: white;
  box-shadow: 0 18px 30px rgb(var(--luxury-black-rgb) / 0.14);
}

.b2b-submit-button:hover:not(:disabled),
.b2b-secondary-link:hover,
.b2b-footer-button:hover {
  transform: translateY(-1px);
}

.b2b-submit-button:disabled {
  opacity: 0.42;
  cursor: not-allowed;
  box-shadow: none;
}

.b2b-feedback {
  display: flex;
  gap: 0.8rem;
  align-items: flex-start;
  padding: 1rem;
  border-radius: 18px;
  border: 1px solid transparent;
  font-size: 14px;
  line-height: 1.65;
}

.b2b-feedback-ok {
  background: rgb(228 245 236);
  border-color: rgb(150 205 170);
  color: #21553a;
}

.b2b-feedback-error {
  background: rgb(254 239 239);
  border-color: rgb(230 185 185);
  color: #7a2f2f;
}

.b2b-feedback-icon {
  min-width: 30px;
  height: 30px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: rgb(255 255 255 / 0.56);
  font-size: 11px;
  font-weight: 700;
}

.b2b-side-title {
  margin-top: 0.5rem;
  font-size: clamp(1.8rem, 2.5vw, 2.5rem);
  line-height: 1.04;
}

.b2b-steps {
  margin-top: 1.4rem;
  display: grid;
  gap: 1rem;
}

.b2b-step {
  display: grid;
  grid-template-columns: 48px 1fr;
  gap: 0.9rem;
  align-items: start;
  padding: 0.9rem 0;
  border-top: 1px solid rgb(var(--luxury-gold-rgb) / 0.12);
}

.b2b-step:first-child {
  border-top: none;
  padding-top: 0;
}

.b2b-step-number {
  width: 48px;
  height: 48px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(145deg, rgb(37 31 26), rgb(82 67 51));
  color: white;
  font-size: 12px;
  letter-spacing: 0.18em;
}

.b2b-step-title {
  color: var(--luxury-black);
  font-size: 15px;
  font-weight: 700;
}

.b2b-step-copy {
  margin-top: 0.25rem;
  font-size: 13px;
  line-height: 1.7;
}

.b2b-info-card-soft {
  background: linear-gradient(180deg, rgb(255 255 255 / 0.94), rgb(250 245 238 / 0.92));
}

.b2b-benefits {
  margin-top: 1.4rem;
  display: grid;
  gap: 0.8rem;
  padding-left: 1rem;
  color: rgb(var(--luxury-black-rgb) / 0.68);
  font-size: 14px;
  line-height: 1.7;
}

.b2b-contact-card {
  background:
    linear-gradient(135deg, rgb(31 26 22 / 0.98), rgb(63 50 39 / 0.95)),
    var(--luxury-black);
}

.b2b-contact-card .b2b-section-label,
.b2b-contact-card .b2b-side-title,
.b2b-contact-card .b2b-contact-label,
.b2b-contact-card .b2b-contact-value {
  color: white;
}

.b2b-contact-list {
  margin-top: 1.4rem;
  display: grid;
  gap: 1rem;
}

.b2b-contact-label {
  display: block;
  font-size: 10px;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  opacity: 0.66;
}

.b2b-contact-value {
  display: block;
  margin-top: 0.3rem;
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 1.5rem;
  line-height: 1.1;
}

.b2b-secondary-link {
  margin-top: 1.4rem;
  border: 1px solid rgb(255 255 255 / 0.24);
  color: white;
  background: rgb(255 255 255 / 0.06);
}

.b2b-secondary-link:hover {
  background: rgb(255 255 255 / 0.1);
}

.b2b-footer-cta {
  margin-top: 0.5rem;
}

.b2b-footer-card {
  display: grid;
  grid-template-columns: minmax(0, 1.15fr) minmax(280px, 0.85fr);
  gap: 1.6rem;
  align-items: center;
  padding: 1.6rem;
  border-radius: 30px;
  background:
    linear-gradient(135deg, rgb(31 26 22 / 0.98), rgb(63 50 39 / 0.95)),
    var(--luxury-black);
  box-shadow: 0 28px 50px rgb(var(--luxury-black-rgb) / 0.18);
}

.b2b-section-label-light,
.b2b-footer-title,
.b2b-footer-copy {
  color: white;
}

.b2b-footer-title {
  margin-top: 0.5rem;
  font-size: clamp(2rem, 3vw, 3rem);
  line-height: 1;
}

.b2b-footer-copy {
  margin-top: 0.8rem;
  max-width: 54ch;
  font-size: 14px;
  line-height: 1.75;
  opacity: 0.78;
}

.b2b-footer-actions {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
}

.b2b-footer-button-light {
  background: white;
  color: var(--luxury-black);
}

.b2b-footer-button-outline {
  border: 1px solid rgb(255 255 255 / 0.24);
  color: white;
  background: transparent;
}

.animate-rise {
  animation: rise 0.9s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.delay-100 {
  animation-delay: 100ms;
}

.delay-150 {
  animation-delay: 150ms;
}

.delay-200 {
  animation-delay: 200ms;
}

.fade-enter-active {
  animation: fadeScale 0.28s ease-out;
}

.fade-leave-active {
  animation: fadeScale 0.24s ease-out reverse;
}

@keyframes rise {
  from {
    opacity: 0;
    transform: translateY(18px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeScale {
  from {
    opacity: 0;
    transform: translateY(8px) scale(0.98);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@media (max-width: 1024px) {
  .b2b-hero-points,
  .b2b-panel-metrics,
  .b2b-footer-card {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .b2b-form-card,
  .b2b-info-card,
  .b2b-contact-card,
  .b2b-footer-card,
  .b2b-panel-card,
  .b2b-point-card {
    border-radius: 24px;
  }

  .b2b-form-head {
    flex-direction: column;
  }

  .b2b-step {
    grid-template-columns: 40px 1fr;
  }

  .b2b-step-number {
    width: 40px;
    height: 40px;
    border-radius: 14px;
  }
}
</style>
