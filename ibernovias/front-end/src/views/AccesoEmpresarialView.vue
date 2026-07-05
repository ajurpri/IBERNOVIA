<template>
  <div class="b2b-page">
    <section class="b2b-header">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12 sm:py-14">
        <p class="b2b-eyebrow">Acceso profesional</p>
        <div class="b2b-header-grid">
          <div>
            <h1 class="b2b-title">Solicitud de alta para empresa</h1>
            <p class="b2b-intro">
              Esta area esta dirigida a tiendas y profesionales del sector que quieran solicitar acceso comercial a Ibernovia.
            </p>
          </div>

          <div class="b2b-summary">
            <p class="b2b-summary-title">Revision manual</p>
            <p class="b2b-summary-copy">
              Revisamos cada solicitud antes de activar el acceso profesional.
            </p>
            <dl class="b2b-summary-list">
              <div>
                <dt>Plazo habitual</dt>
                <dd>24/48h laborables</dd>
              </div>
              <div>
                <dt>Contacto</dt>
                <dd>info@ibernovia.es</dd>
              </div>
              <div>
                <dt>Telefono</dt>
                <dd>953 51 50 70</dd>
              </div>
            </dl>
          </div>
        </div>
      </div>
    </section>

    <section class="b2b-content">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 pb-16 sm:pb-20">
        <div class="b2b-content-grid">
          <div class="b2b-form-card">
            <div class="b2b-form-head">
              <div>
                <p class="b2b-section-label">Formulario</p>
                <h2 class="b2b-form-title">Datos de la empresa</h2>
              </div>
              <p class="b2b-form-note">Los campos marcados con * son obligatorios.</p>
            </div>

            <form @submit.prevent="submitForm" class="space-y-7">
              <fieldset class="space-y-5">
                <legend class="b2b-fieldset-title">Empresa</legend>

                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <label class="b2b-input-group">
                    <span class="b2b-input-label">Nombre comercial *</span>
                    <input
                      v-model="form.empresa"
                      required
                      type="text"
                      placeholder="Ej: Boutique Nupcial"
                      class="b2b-input"
                    >
                  </label>

                  <label class="b2b-input-group">
                    <span class="b2b-input-label">CIF / NIF *</span>
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

              <fieldset class="space-y-5">
                <legend class="b2b-fieldset-title">Contacto</legend>

                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <label class="b2b-input-group">
                    <span class="b2b-input-label">Persona de contacto *</span>
                    <input
                      v-model="form.contacto"
                      required
                      type="text"
                      placeholder="Ej: Carmen Gomez"
                      class="b2b-input"
                    >
                  </label>

                  <label class="b2b-input-group">
                    <span class="b2b-input-label">Telefono *</span>
                    <input
                      v-model="form.telefono"
                      required
                      type="tel"
                      placeholder="Ej: +34 600 123 456"
                      class="b2b-input"
                    >
                  </label>

                  <label class="b2b-input-group sm:col-span-2">
                    <span class="b2b-input-label">Email profesional *</span>
                    <input
                      v-model="form.email"
                      required
                      type="email"
                      placeholder="Ej: comercial@empresa.com"
                      class="b2b-input"
                    >
                  </label>

                  <label class="b2b-input-group">
                    <span class="b2b-input-label">Provincia *</span>
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

              <fieldset class="space-y-5">
                <legend class="b2b-fieldset-title">Actividad</legend>

                <label class="b2b-input-group">
                  <span class="b2b-input-label">Informacion adicional</span>
                  <textarea
                    v-model="form.mensaje"
                    rows="5"
                    placeholder="Describe brevemente tu actividad o el tipo de tienda."
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
                  Confirmo la veracidad de los datos aportados y acepto su tratamiento conforme a la
                  <router-link to="/privacidad" class="b2b-inline-link">Politica de Privacidad</router-link>.
                </label>
              </div>

              <button
                type="submit"
                :disabled="sending || !form.aceptaTerminos"
                class="b2b-submit-button"
              >
                <span v-if="sending">Enviando solicitud...</span>
                <span v-else>Enviar solicitud</span>
              </button>

              <Transition name="fade">
                <div
                  v-if="feedback"
                  class="b2b-feedback"
                  :class="feedbackOk ? 'b2b-feedback-ok' : 'b2b-feedback-error'"
                >
                  <p>{{ feedback }}</p>
                </div>
              </Transition>
            </form>
          </div>

          <aside class="space-y-6">
            <div class="b2b-info-card">
              <p class="b2b-section-label">Proceso</p>
              <h3 class="b2b-side-title">Como gestionamos el alta</h3>
              <ol class="b2b-steps">
                <li>
                  <strong>1. Recepcion</strong>
                  <p>Recibimos los datos de empresa y contacto.</p>
                </li>
                <li>
                  <strong>2. Revision</strong>
                  <p>Comprobamos la informacion antes de activar la cuenta.</p>
                </li>
                <li>
                  <strong>3. Respuesta</strong>
                  <p>Si procede, te avisamos por correo con los siguientes pasos.</p>
                </li>
              </ol>
            </div>

            <div class="b2b-info-card">
              <p class="b2b-section-label">Contacto</p>
              <h3 class="b2b-side-title">Si prefieres consultarlo antes</h3>
              <div class="b2b-contact-list">
                <p><strong>Telefono:</strong> 953 51 50 70</p>
                <p><strong>Email:</strong> info@ibernovia.es</p>
                <p><strong>Horario:</strong> 09:00 a 14:00 y 17:00 a 20:00</p>
              </div>
              <router-link to="/contacto" class="b2b-secondary-link">Ir a contacto</router-link>
            </div>
          </aside>
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
      asunto: `Alta empresarial - ${form.value.empresa}`,
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
    feedback.value = 'Solicitud enviada correctamente. Revisaremos los datos y te responderemos por correo.'

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
.b2b-page {
  background: #fffdf9;
  color: var(--luxury-black);
}

.b2b-header {
  border-bottom: 1px solid rgb(var(--luxury-gold-rgb) / 0.12);
  background: #f8f4ed;
}

.b2b-header-grid,
.b2b-content-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) minmax(280px, 0.8fr);
  gap: 2rem;
  align-items: start;
}

.b2b-eyebrow,
.b2b-section-label,
.b2b-fieldset-title {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.22em;
  text-transform: uppercase;
  color: var(--luxury-gold);
}

.b2b-title,
.b2b-form-title,
.b2b-side-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  color: var(--luxury-black);
}

.b2b-title {
  margin-top: 0.6rem;
  font-size: clamp(2.5rem, 5vw, 4.5rem);
  line-height: 0.96;
  max-width: 10ch;
}

.b2b-intro {
  margin-top: 1rem;
  max-width: 56ch;
  color: rgb(var(--luxury-black-rgb) / 0.68);
  line-height: 1.8;
}

.b2b-summary,
.b2b-form-card,
.b2b-info-card {
  background: white;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  border-radius: 18px;
}

.b2b-summary,
.b2b-info-card {
  padding: 1.5rem;
}

.b2b-summary-title {
  font-size: 1rem;
  font-weight: 700;
}

.b2b-summary-copy {
  margin-top: 0.5rem;
  color: rgb(var(--luxury-black-rgb) / 0.66);
  line-height: 1.7;
}

.b2b-summary-list {
  margin-top: 1rem;
  display: grid;
  gap: 0.9rem;
}

.b2b-summary-list dt {
  font-size: 11px;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  color: rgb(var(--luxury-black-rgb) / 0.48);
}

.b2b-summary-list dd {
  margin-top: 0.2rem;
  font-weight: 600;
}

.b2b-content {
  padding-top: 2rem;
}

.b2b-form-card {
  padding: 1.5rem;
}

.b2b-form-head {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: end;
  margin-bottom: 1.5rem;
}

.b2b-form-title {
  margin-top: 0.35rem;
  font-size: clamp(1.9rem, 3vw, 2.7rem);
}

.b2b-form-note {
  color: rgb(var(--luxury-black-rgb) / 0.56);
  font-size: 13px;
}

fieldset {
  margin: 0;
  padding: 0;
  border: none;
}

.b2b-input-group {
  display: block;
}

.b2b-input-label {
  display: block;
  margin-bottom: 0.5rem;
  font-size: 13px;
  color: rgb(var(--luxury-black-rgb) / 0.72);
}

.b2b-input {
  width: 100%;
  min-height: 50px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.18);
  border-radius: 10px;
  padding: 0.85rem 0.95rem;
  background: white;
  color: var(--luxury-black);
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.b2b-input:focus {
  border-color: rgb(var(--luxury-gold-rgb) / 0.5);
  box-shadow: 0 0 0 3px rgb(var(--luxury-gold-rgb) / 0.08);
}

.b2b-textarea {
  min-height: 130px;
  resize: vertical;
}

.b2b-terms-box {
  display: grid;
  grid-template-columns: 18px 1fr;
  gap: 0.75rem;
  align-items: start;
  padding: 1rem;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  border-radius: 12px;
  background: #faf8f4;
}

.b2b-terms-copy {
  font-size: 13px;
  line-height: 1.7;
  color: rgb(var(--luxury-black-rgb) / 0.7);
}

.b2b-inline-link {
  color: var(--luxury-black);
  text-decoration: underline;
  text-underline-offset: 3px;
}

.b2b-submit-button,
.b2b-secondary-link {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-height: 50px;
  padding: 0 1.4rem;
  border-radius: 999px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  text-decoration: none;
  transition: background 0.2s ease, color 0.2s ease, border-color 0.2s ease;
}

.b2b-submit-button {
  width: 100%;
  border: none;
  background: var(--luxury-black);
  color: white;
}

.b2b-submit-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.b2b-secondary-link {
  border: 1px solid rgb(var(--luxury-black-rgb) / 0.16);
  color: var(--luxury-black);
}

.b2b-feedback {
  padding: 1rem;
  border-radius: 12px;
  border: 1px solid transparent;
  font-size: 14px;
  line-height: 1.6;
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

.b2b-side-title {
  margin-top: 0.35rem;
  font-size: clamp(1.8rem, 2.5vw, 2.3rem);
  line-height: 1.05;
}

.b2b-steps {
  margin-top: 1rem;
  display: grid;
  gap: 1rem;
}

.b2b-steps li {
  border-top: 1px solid rgb(var(--luxury-gold-rgb) / 0.12);
  padding-top: 1rem;
}

.b2b-steps li:first-child {
  border-top: none;
  padding-top: 0;
}

.b2b-steps p,
.b2b-contact-list p {
  margin-top: 0.3rem;
  color: rgb(var(--luxury-black-rgb) / 0.68);
  line-height: 1.7;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(4px);
}

@media (max-width: 1024px) {
  .b2b-header-grid,
  .b2b-content-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .b2b-form-head {
    flex-direction: column;
    align-items: start;
  }

  .b2b-summary,
  .b2b-form-card,
  .b2b-info-card {
    border-radius: 14px;
  }
}
</style>
