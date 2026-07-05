<template>
  <div class="chat-shell">
    <Transition name="chat-trigger">
      <button
        v-if="!isOpen"
        @click="toggleChat"
        class="chat-trigger"
        aria-label="Abrir concierge de Ibernovia"
      >
        <span class="chat-trigger-glow"></span>
        <span class="chat-trigger-ring"></span>
        <span class="chat-trigger-core">
          <img :src="logoSrc" alt="Ibernovia" class="chat-trigger-logo" />
        </span>
      </button>
    </Transition>

    <Transition name="chat-panel">
      <section v-if="isOpen" class="chat-panel" aria-label="Chat de Ibernovia">
        <header class="chat-header">
          <div class="chat-brand">
            <div class="chat-brand-logo-wrap">
              <img :src="logoSrc" alt="Logo Ibernovia" class="chat-brand-logo" />
              <span class="chat-brand-status"></span>
            </div>

            <div class="chat-brand-copy">
              <p class="chat-eyebrow">IBERNOVIA</p>
              <h3 class="chat-title">Asistente</h3>
              <p class="chat-subtitle">Productos, contacto y acceso profesional.</p>
            </div>
          </div>

          <div class="chat-header-actions">
            <button
              v-if="canResetConversation"
              @click="resetConversation"
              class="chat-header-action"
              type="button"
              aria-label="Reiniciar conversación"
            >
              Reiniciar
            </button>
            <button @click="isOpen = false" class="chat-close" aria-label="Cerrar chat">
              <svg class="chat-close-icon" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
        </header>

        <div ref="messagesContainer" class="chat-messages">
          <div v-if="showSuggestions" class="chat-suggestions-block">
            <p class="chat-suggestions-label">Consultas frecuentes</p>
            <div class="chat-suggestions-list">
              <button
                v-for="suggestion in activeSuggestions"
                :key="suggestion"
                type="button"
                class="chat-suggestion-chip"
                @click="sendSuggestion(suggestion)"
              >
                {{ suggestion }}
              </button>
            </div>
          </div>

          <article
            v-for="(msg, idx) in messages"
            :key="idx"
            class="chat-message"
            :class="msg.role === 'user' ? 'chat-message-user' : 'chat-message-model'"
          >
            <div v-if="msg.role === 'model'" class="chat-avatar">
              <img :src="logoSrc" alt="Ibernovia" class="chat-avatar-logo" />
            </div>

            <div class="chat-bubble-wrap">
              <p class="chat-message-label">
                {{ msg.role === 'user' ? 'Tu consulta' : 'Concierge Ibernovia' }}
              </p>
              <div class="chat-bubble" :class="msg.role === 'user' ? 'chat-bubble-user' : 'chat-bubble-model'">
                <p class="chat-bubble-text">{{ msg.text }}</p>
              </div>

              <div v-if="msg.products?.length" class="chat-product-grid">
                <router-link
                  v-for="product in msg.products"
                  :key="product.id"
                  :to="`/producto/${product.id}`"
                  class="chat-product-card"
                  @click="isOpen = false"
                >
                  <div class="chat-product-media">
                    <img :src="getImageUrl(product.imagen)" :alt="product.nombre" class="chat-product-image" />
                  </div>
                  <div class="chat-product-content">
                    <p class="chat-product-family">{{ product.familia || product.categoria }}</p>
                    <h4 class="chat-product-name">{{ product.nombre }}</h4>
                    <p v-if="product.descripcion" class="chat-product-description">{{ product.descripcion }}</p>
                    <p v-if="showPrice(product)" class="chat-product-price">{{ product.precio }}€</p>
                  </div>
                </router-link>
              </div>
            </div>
          </article>

          <div v-if="loading" class="chat-message chat-message-model">
            <div class="chat-avatar">
              <img :src="logoSrc" alt="Ibernovia" class="chat-avatar-logo" />
            </div>
            <div class="chat-bubble-wrap">
              <p class="chat-message-label">Concierge Ibernovia</p>
              <div class="chat-bubble chat-bubble-model chat-bubble-loading">
                <span class="chat-dot"></span>
                <span class="chat-dot chat-dot-delay-1"></span>
                <span class="chat-dot chat-dot-delay-2"></span>
              </div>
            </div>
          </div>
        </div>

        <form @submit.prevent="sendMessage" class="chat-form">
          <div class="chat-input-wrap">
            <textarea
              ref="textareaRef"
              v-model="newMessage"
              rows="1"
              :disabled="loading"
              placeholder="Preguntame por vestidos, complementos, horarios o acceso profesional"
              class="chat-input"
              @input="autoResize"
              @keydown.enter.exact.prevent="sendMessage"
            ></textarea>
          </div>

          <div class="chat-form-footer">
            <p class="chat-form-note">Atencion guiada de producto y boutique</p>
            <button
              type="submit"
              :disabled="loading || !newMessage.trim()"
              class="chat-send"
            >
              Enviar
            </button>
          </div>
        </form>
      </section>
    </Transition>
  </div>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { useAuthStore } from '../stores/auth'
import { apiClient, getImageUrl } from '../lib/api'

const CHAT_STORAGE_KEY = 'ibernovia_concierge_history_v3'
const logoSrc = `${import.meta.env.BASE_URL}logo/optimized/logo-80.jpg`
const authStore = useAuthStore()

const initialMessage = {
  role: 'model',
  text: 'Hola. Puedo ayudarte con productos, horarios, contacto o acceso profesional.',
  products: []
}

const baseSuggestions = [
  'Busco algo para novia',
  'Que horario teneis',
  'Como accedo a precios profesionales',
  'Busco algo para fiesta'
]

const isOpen = ref(false)
const loading = ref(false)
const newMessage = ref('')
const messagesContainer = ref(null)
const textareaRef = ref(null)
const messages = ref(loadStoredMessages())

const showSuggestions = computed(() => !loading.value && messages.value.length < 4)
const canResetConversation = computed(() => messages.value.length > 1)

const profileSummary = computed(() => {
  const normalized = normalize(messages.value
    .filter((msg) => msg.role === 'user')
    .map((msg) => msg.text)
    .join(' '))

  const tags = []
  if (containsAny(normalized, 'novia', 'velo', 'tocado', 'tiara', 'liga')) tags.push('Interes principal en novia')
  if (containsAny(normalized, 'fiesta', 'mantilla', 'pamela', 'bolso')) tags.push('Busca opciones de fiesta')
  if (containsAny(normalized, 'comunion', 'corona', 'rosario', 'diadema')) tags.push('Valora referencias de comunion')
  if (containsAny(normalized, 'novio', 'gemelo')) tags.push('Tambien contempla complementos de novio')
  if (containsAny(normalized, 'elegante', 'clasico', 'refinado')) tags.push('Prefiere un estilo elegante y refinado')
  if (containsAny(normalized, 'llamativo', 'especial', 'diferente')) tags.push('Quiere una propuesta con mas personalidad')
  if (containsAny(normalized, 'precio', 'profesional', 'mayorista', 'empresa')) tags.push('Hay interes por condiciones profesionales')
  if (containsAny(normalized, 'cita', 'horario', 'contacto', 'visita')) tags.push('Tambien necesita informacion de visita')

  return tags.slice(0, 3).join(' · ')
})

const activeSuggestions = computed(() => {
  const normalized = normalize(messages.value
    .filter((msg) => msg.role === 'user')
    .map((msg) => msg.text)
    .join(' '))

  if (containsAny(normalized, 'novia', 'velo', 'liga', 'tocado', 'tiara')) {
    return [
      'Busco algo delicado para novia',
      'Recomiendame velos o tocados elegantes',
      'Quiero un look clasico pero especial',
      'Como pido cita para ver opciones'
    ]
  }

  if (containsAny(normalized, 'fiesta', 'mantilla', 'pendiente', 'bolso', 'pamela')) {
    return [
      'Quiero complementos de fiesta elegantes',
      'Recomiendame pendientes o bolsos',
      'Busco algo para mantilla o invitada',
      'Quiero ver opciones con mas presencia'
    ]
  }

  if (containsAny(normalized, 'comunion', 'rosario', 'corona', 'diadema')) {
    return [
      'Busco complementos de comunion finos',
      'Recomiendame coronas o diademas',
      'Quiero opciones delicadas y clasicas',
      'Que articulos teneis para comunion'
    ]
  }

  if (containsAny(normalized, 'profesional', 'empresa', 'precio', 'mayorista')) {
    return [
      'Como solicito acceso profesional',
      'Que necesito para ver precios',
      'Quiero informacion para empresa',
      'Como funciona el alta B2B'
    ]
  }

  return baseSuggestions
})

function loadStoredMessages() {
  if (typeof window === 'undefined') {
    return [initialMessage]
  }

  try {
    const raw = window.localStorage.getItem(CHAT_STORAGE_KEY)
    if (!raw) return [initialMessage]

    const parsed = JSON.parse(raw)
    if (!Array.isArray(parsed) || parsed.length === 0) return [initialMessage]

    return parsed
      .filter((item) => item && typeof item.text === 'string' && typeof item.role === 'string')
      .map((item) => ({
        role: item.role,
        text: item.text,
        products: Array.isArray(item.products) ? item.products : []
      }))
  } catch {
    return [initialMessage]
  }
}

const toggleChat = () => {
  isOpen.value = !isOpen.value
  if (isOpen.value) {
    scrollToBottom()
    autoResize()
  }
}

const scrollToBottom = async () => {
  await nextTick()
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

const autoResize = async () => {
  await nextTick()
  if (!textareaRef.value) return
  textareaRef.value.style.height = 'auto'
  textareaRef.value.style.height = `${Math.min(textareaRef.value.scrollHeight, 110)}px`
}

watch(messages, (value) => {
  scrollToBottom()
  if (typeof window !== 'undefined') {
    window.localStorage.setItem(CHAT_STORAGE_KEY, JSON.stringify(value.slice(-18)))
  }
}, { deep: true })

watch(isOpen, (open) => {
  if (open) {
    scrollToBottom()
    autoResize()
  }
})

watch(newMessage, () => {
  autoResize()
})

const resetConversation = () => {
  messages.value = [initialMessage]
  newMessage.value = ''
  if (typeof window !== 'undefined') {
    window.localStorage.removeItem(CHAT_STORAGE_KEY)
  }
}

const sendSuggestion = async (suggestion) => {
  newMessage.value = suggestion
  await sendMessage()
}

const showPrice = (product) => authStore.canSeePrices && product?.precio !== null && product?.precio !== undefined

const handleExternalOpen = async (event) => {
  isOpen.value = true

  const requestedMessage = event?.detail?.message?.trim()
  const autoSendRequested = Boolean(event?.detail?.autoSend)

  if (requestedMessage) {
    newMessage.value = requestedMessage
    await nextTick()
    await autoResize()

    if (autoSendRequested) {
      await sendMessage()
    }
  } else {
    await scrollToBottom()
    await autoResize()
  }
}

const sendMessage = async () => {
  const text = newMessage.value.trim()
  if (!text || loading.value) return

  messages.value.push({
    role: 'user',
    text,
    products: []
  })

  newMessage.value = ''
  loading.value = true
  await scrollToBottom()
  await autoResize()

  try {
    const response = await apiClient.post('/api/chat', {
      history: messages.value.map(({ role, text: content }) => ({ role, text: content }))
    })

    messages.value.push({
      role: 'model',
      text: response.data.reply,
      products: Array.isArray(response.data.suggestedProducts) ? response.data.suggestedProducts : []
    })
  } catch (error) {
    console.error('Error in chat request:', error)
    messages.value.push({
      role: 'model',
      text: buildFallbackReply(text),
      products: []
    })
  } finally {
    loading.value = false
    scrollToBottom()
    autoResize()
  }
}

function normalize(value) {
  return (value || '')
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .toLowerCase()
}

function containsAny(text, ...candidates) {
  return candidates.some((candidate) => text.includes(candidate))
}

function buildFallbackReply(text) {
  const normalized = normalize(text)

  if (containsAny(normalized, 'horario', 'abierto', 'cerrado')) {
    return 'Nuestro horario es de lunes a viernes de 9:00 a 14:00 y de 17:00 a 20:00.'
  }

  if (containsAny(normalized, 'contacto', 'telefono', 'email', 'correo')) {
    return 'Puedes escribir a info@ibernovia.es o llamar al 953 51 50 70.'
  }

  if (containsAny(normalized, 'precio', 'profesional', 'empresa', 'b2b')) {
    return 'Las tarifas estan reservadas a clientes profesionales validados. Puedes solicitar acceso desde la pagina de acceso profesional.'
  }

  if (containsAny(normalized, 'novia', 'fiesta', 'comunion', 'novio', 'producto', 'catalogo', 'velo', 'tocado')) {
    return 'Ahora mismo no puedo cargar la respuesta completa, pero puedes revisar el catalogo o probar de nuevo con una familia concreta.'
  }

  return 'Ahora mismo no puedo responder con normalidad. Si quieres, prueba otra vez en unos segundos o contacta con Ibernovia en info@ibernovia.es.'
}

onMounted(() => {
  window.addEventListener('ibernovia:open-chat', handleExternalOpen)
})

onBeforeUnmount(() => {
  window.removeEventListener('ibernovia:open-chat', handleExternalOpen)
})
</script>

<style scoped>
.chat-shell {
  position: fixed;
  right: 1rem;
  bottom: 1rem;
  z-index: 99;
}

.chat-trigger {
  position: relative;
  width: 74px;
  height: 74px;
  border: none;
  background: transparent;
  cursor: pointer;
  padding: 0;
}

.chat-trigger-glow,
.chat-trigger-ring,
.chat-trigger-core {
  position: absolute;
  inset: 0;
  border-radius: 999px;
}

.chat-trigger-glow {
  background:
    radial-gradient(circle at 30% 30%, rgb(var(--luxury-gold-rgb) / 0.7), transparent 58%),
    radial-gradient(circle at 70% 70%, rgb(var(--luxury-black-rgb) / 0.65), transparent 60%);
  filter: blur(10px);
  transform: scale(1.08);
}

.chat-trigger-ring {
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.45);
  background:
    linear-gradient(135deg, rgb(255 255 255 / 0.78), rgb(255 255 255 / 0.18)),
    rgb(253 250 246 / 0.85);
  box-shadow:
    0 24px 55px rgb(var(--luxury-black-rgb) / 0.26),
    inset 0 1px 0 rgb(255 255 255 / 0.8);
  backdrop-filter: blur(12px);
}

.chat-trigger-core {
  inset: 7px;
  display: flex;
  align-items: center;
  justify-content: center;
  background:
    linear-gradient(145deg, rgb(255 255 255 / 0.95), rgb(252 250 246 / 0.76));
  overflow: hidden;
}

.chat-trigger-logo {
  width: 42px;
  height: 42px;
  border-radius: 999px;
  object-fit: cover;
  box-shadow: 0 10px 25px rgb(var(--luxury-black-rgb) / 0.18);
}

.chat-trigger-badge {
  position: absolute;
  right: -2px;
  bottom: -1px;
  min-width: 24px;
  height: 24px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0 7px;
  background: var(--luxury-black);
  color: #fff;
  font-size: 9px;
  font-weight: 700;
  letter-spacing: 0.22em;
  text-indent: 0.22em;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.45);
}

.chat-panel {
  width: min(420px, calc(100vw - 1.5rem));
  height: min(720px, calc(100vh - 2rem));
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border-radius: 18px;
  border: 1px solid rgb(var(--luxury-black-rgb) / 0.1);
  background: #fffdf9;
  box-shadow: 0 20px 48px rgb(var(--luxury-black-rgb) / 0.18);
}

.chat-header {
  position: relative;
  padding: 1.1rem 1.15rem 1rem;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  color: white;
  background: var(--luxury-black);
  border-bottom: 1px solid rgb(255 255 255 / 0.08);
}

.chat-brand {
  display: flex;
  gap: 0.9rem;
  min-width: 0;
}

.chat-brand-logo-wrap {
  position: relative;
  flex-shrink: 0;
}

.chat-brand-logo {
  width: 52px;
  height: 52px;
  border-radius: 12px;
  object-fit: cover;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.28);
}

.chat-brand-status {
  position: absolute;
  right: -2px;
  bottom: -1px;
  width: 13px;
  height: 13px;
  border-radius: 999px;
  background: #36c28b;
  border: 2px solid var(--luxury-black);
  box-shadow: 0 0 0 4px rgb(54 194 139 / 0.18);
}

.chat-brand-copy {
  min-width: 0;
}

.chat-eyebrow {
  font-size: 10px;
  letter-spacing: 0.28em;
  color: rgb(var(--luxury-gold-rgb) / 0.9);
  font-weight: 700;
  margin-bottom: 0.18rem;
}

.chat-title {
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 1.5rem;
  font-weight: 500;
  line-height: 1;
}

.chat-subtitle {
  margin-top: 0.28rem;
  max-width: 25ch;
  font-size: 12px;
  line-height: 1.45;
  color: rgb(255 255 255 / 0.72);
}

.chat-header-actions {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.chat-header-action,
.chat-close {
  height: 38px;
  border-radius: 999px;
  border: 1px solid rgb(255 255 255 / 0.12);
  background: rgb(255 255 255 / 0.05);
  color: rgb(255 255 255 / 0.8);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: transform 0.25s ease, background 0.25s ease, color 0.25s ease;
}

.chat-header-action {
  padding: 0 0.9rem;
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.16em;
  text-transform: uppercase;
}

.chat-header-action:hover,
.chat-close:hover {
  background: rgb(var(--luxury-gold-rgb) / 0.14);
  color: white;
}

.chat-close {
  width: 38px;
}

.chat-close:hover {
  transform: rotate(90deg);
}

.chat-close-icon {
  width: 18px;
  height: 18px;
}

.chat-suggestions-label,
.chat-memory-label {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.22em;
  color: rgb(var(--luxury-black-rgb) / 0.58);
  margin-bottom: 0.6rem;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 0.85rem 1.05rem 1.1rem;
  display: flex;
  flex-direction: column;
  gap: 0.95rem;
  background: #fffdf9;
}

.chat-suggestions-block {
  padding: 0.15rem 0 0.1rem;
}

.chat-suggestions-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.chat-suggestion-chip {
  border: none;
  background: transparent;
  color: var(--luxury-black);
  border-radius: 0;
  padding: 0.3rem 0;
  font-size: 11px;
  line-height: 1.35;
  cursor: pointer;
  transition: color 0.2s ease;
  text-decoration: underline;
  text-underline-offset: 3px;
}

.chat-suggestion-chip:hover {
  color: var(--luxury-gold);
}

.chat-message {
  display: flex;
  gap: 0.7rem;
  align-items: flex-end;
}

.chat-message-user {
  justify-content: flex-end;
}

.chat-message-model {
  justify-content: flex-start;
}

.chat-avatar {
  width: 34px;
  height: 34px;
  border-radius: 999px;
  background: #f6f1e8;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.14);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.chat-avatar-logo {
  width: 24px;
  height: 24px;
  border-radius: 999px;
  object-fit: cover;
}

.chat-bubble-wrap {
  max-width: min(84%, 302px);
}

.chat-message-user .chat-bubble-wrap {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.chat-message-label {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  color: rgb(var(--luxury-black-rgb) / 0.48);
  margin-bottom: 0.3rem;
}

.chat-bubble {
  padding: 0.85rem 0.95rem;
  border-radius: 16px;
}

.chat-bubble-model {
  border-top-left-radius: 6px;
  background: #f7f4ee;
  border: 1px solid rgb(var(--luxury-black-rgb) / 0.08);
}

.chat-bubble-user {
  border-top-right-radius: 6px;
  background: var(--luxury-black);
  color: white;
}

.chat-bubble-text {
  white-space: pre-line;
  font-size: 13.5px;
  line-height: 1.62;
}

.chat-product-grid {
  display: grid;
  gap: 0.65rem;
  margin-top: 0.7rem;
}

.chat-product-card {
  display: grid;
  grid-template-columns: 74px 1fr;
  gap: 0.7rem;
  align-items: stretch;
  padding: 0.55rem 0 0.75rem;
  border-radius: 0;
  text-decoration: none;
  background: transparent;
  border-bottom: 1px solid rgb(var(--luxury-black-rgb) / 0.08);
  transition: border-color 0.2s ease;
}

.chat-product-card:hover {
  border-color: rgb(var(--luxury-gold-rgb) / 0.4);
}

.chat-product-media {
  overflow: hidden;
  border-radius: 10px;
  background: rgb(var(--luxury-gold-rgb) / 0.08);
}

.chat-product-image {
  width: 100%;
  height: 100%;
  min-height: 88px;
  object-fit: cover;
  display: block;
}

.chat-product-content {
  min-width: 0;
}

.chat-product-family {
  font-size: 9px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  color: var(--luxury-gold);
  margin-bottom: 0.25rem;
}

.chat-product-name {
  font-family: 'Cormorant Garamond', Georgia, serif;
  font-size: 1.05rem;
  line-height: 1.05;
  color: var(--luxury-black);
  margin-bottom: 0.25rem;
}

.chat-product-description {
  font-size: 11px;
  line-height: 1.35;
  color: rgb(var(--luxury-black-rgb) / 0.64);
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.chat-product-price {
  margin-top: 0.35rem;
  font-size: 12px;
  font-weight: 700;
  color: var(--luxury-black);
}

.chat-bubble-loading {
  display: inline-flex;
  align-items: center;
  gap: 0.32rem;
  min-width: 78px;
}

.chat-dot {
  width: 7px;
  height: 7px;
  border-radius: 999px;
  background: var(--luxury-gold);
  animation: chat-bounce 1s infinite ease-in-out;
}

.chat-dot-delay-1 {
  animation-delay: 0.15s;
}

.chat-dot-delay-2 {
  animation-delay: 0.3s;
}

.chat-form {
  padding: 0.95rem 1rem 1rem;
  border-top: 1px solid rgb(var(--luxury-black-rgb) / 0.08);
  background: white;
}

.chat-input-wrap {
  padding: 0.75rem 0;
  border-radius: 0;
  border: none;
  border-bottom: 1px solid rgb(var(--luxury-black-rgb) / 0.14);
  background: transparent;
  box-shadow: none;
}

.chat-input {
  width: 100%;
  min-height: 22px;
  max-height: 110px;
  border: none;
  resize: none;
  background: transparent;
  color: var(--luxury-black);
  font-size: 13.5px;
  line-height: 1.55;
  outline: none;
}

.chat-input::placeholder {
  color: rgb(var(--luxury-black-rgb) / 0.36);
}

.chat-form-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 0.8rem;
}

.chat-form-note {
  font-size: 10px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  color: rgb(var(--luxury-black-rgb) / 0.46);
}

.chat-send {
  min-width: 96px;
  border: none;
  border-radius: 999px;
  padding: 0.8rem 1.2rem;
  background: var(--luxury-black);
  color: white;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  cursor: pointer;
  transition: opacity 0.22s ease, background 0.22s ease;
}

.chat-send:hover:not(:disabled) {
  background: var(--luxury-gold);
}

.chat-send:disabled {
  opacity: 0.4;
  cursor: not-allowed;
  box-shadow: none;
}

.chat-panel-enter-active,
.chat-panel-leave-active,
.chat-trigger-enter-active,
.chat-trigger-leave-active {
  transition: opacity 0.28s ease, transform 0.28s ease;
}

.chat-panel-enter-from,
.chat-panel-leave-to,
.chat-trigger-enter-from,
.chat-trigger-leave-to {
  opacity: 0;
  transform: translateY(16px) scale(0.97);
}

@keyframes chat-bounce {
  0%, 80%, 100% {
    transform: translateY(0);
    opacity: 0.45;
  }
  40% {
    transform: translateY(-3px);
    opacity: 1;
  }
}

@media (max-width: 640px) {
  .chat-shell {
    left: 0.75rem;
    right: 0.75rem;
    bottom: 0.75rem;
  }

  .chat-panel {
    width: 100%;
    height: min(78vh, 700px);
    margin-left: auto;
  }

  .chat-bubble-wrap {
    max-width: calc(100% - 2.8rem);
  }

  .chat-form-footer {
    align-items: flex-end;
    flex-direction: column;
  }

  .chat-form-note {
    width: 100%;
  }

  .chat-send {
    width: 100%;
  }

  .chat-header-actions {
    flex-wrap: wrap;
  }

  .chat-product-card {
    grid-template-columns: 66px 1fr;
  }
}
</style>
