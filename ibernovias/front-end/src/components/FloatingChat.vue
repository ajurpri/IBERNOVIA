<template>
  <div>
    <!-- Floating Trigger Button -->
    <button 
      @click="toggleChat" 
      class="fixed bottom-6 right-6 z-[99] w-14 h-14 rounded-full bg-luxury-black text-white shadow-2xl flex items-center justify-center hover:bg-luxury-gold hover:scale-105 transition-all duration-300 group focus:outline-none focus:ring-2 focus:ring-luxury-gold focus:ring-offset-2"
      aria-label="Abrir chat de asistencia"
    >
      <!-- Gold notification dot -->
      <span class="absolute top-0 right-0 w-3.5 h-3.5 bg-luxury-gold rounded-full border-2 border-white animate-pulse"></span>
      
      <!-- Close icon if open, Chat bubble if closed -->
      <svg v-if="isOpen" class="w-6 h-6" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
      </svg>
      <svg v-else class="w-6 h-6 transition-transform group-hover:rotate-12" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" d="M8.625 12a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H8.25m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H12m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 0 1-2.555-.337A5.972 5.972 0 0 1 5.41 20.97a.75.75 0 0 1-1.074-.765 5.99 5.99 0 0 1 1.402-2.983C3.524 15.655 3 13.896 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25Z" />
      </svg>
    </button>

    <!-- Chat Card Container -->
    <div 
      v-show="isOpen" 
      class="fixed bottom-24 right-6 z-[99] w-[340px] sm:w-[385px] h-[500px] bg-[#fdfdfc] rounded-2xl border border-black/10 shadow-2xl flex flex-col overflow-hidden animate-slide-up"
    >
      <!-- Chat Header -->
      <div class="bg-luxury-black text-white px-5 py-4 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <div class="relative w-8 h-8 rounded-full bg-luxury-gold/20 text-luxury-gold flex items-center justify-center border border-luxury-gold/30">
            <span class="text-sm font-serif">I</span>
            <!-- Active online status dot -->
            <span class="absolute bottom-0 right-0 w-2 h-2 bg-emerald-500 rounded-full border border-luxury-black"></span>
          </div>
          <div>
            <h3 class="font-serif text-sm tracking-wider">Concierge Ibernovia</h3>
            <span class="text-[9px] uppercase tracking-widest text-luxury-gold font-bold block mt-0.5">Asistente Virtual IA</span>
          </div>
        </div>
        <button @click="isOpen = false" class="text-white/60 hover:text-white transition-colors focus:outline-none" aria-label="Cerrar chat">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
          </svg>
        </button>
      </div>

      <!-- Messages View -->
      <div 
        ref="messagesContainer" 
        class="flex-1 overflow-y-auto p-4 space-y-4 bg-gradient-to-b from-[#fcfaf6] to-[#fdfdfc]"
      >
        <div 
          v-for="(msg, idx) in messages" 
          :key="idx" 
          class="flex flex-col" 
          :class="msg.role === 'user' ? 'items-end' : 'items-start'"
        >
          <!-- Bubble styling -->
          <div 
            class="max-w-[85%] rounded-2xl px-4 py-3 text-sm leading-relaxed"
            :class="msg.role === 'user' 
              ? 'bg-luxury-black text-white rounded-br-none font-sans' 
              : 'bg-white border border-black/5 text-luxury-black rounded-bl-none font-serif font-light shadow-sm'"
          >
            <p class="whitespace-pre-line">{{ msg.text }}</p>
          </div>
          <span class="text-[9px] text-gray-400 mt-1 uppercase tracking-widest px-1">
            {{ msg.role === 'user' ? 'Tú' : 'Concierge' }}
          </span>
        </div>

        <!-- Typing Indicator -->
        <div v-if="loading" class="flex flex-col items-start animate-fade-in">
          <div class="bg-white border border-black/5 text-luxury-black rounded-2xl rounded-bl-none px-4 py-3 shadow-sm flex items-center gap-1.5">
            <span class="w-1.5 h-1.5 bg-luxury-gold rounded-full animate-bounce"></span>
            <span class="w-1.5 h-1.5 bg-luxury-gold rounded-full animate-bounce [animation-delay:0.2s]"></span>
            <span class="w-1.5 h-1.5 bg-luxury-gold rounded-full animate-bounce [animation-delay:0.4s]"></span>
          </div>
          <span class="text-[9px] text-gray-400 mt-1 uppercase tracking-widest px-1">Escribiendo...</span>
        </div>
      </div>

      <!-- Input Field -->
      <form @submit.prevent="sendMessage" class="p-4 bg-white border-t border-black/5 flex items-center gap-2">
        <input 
          v-model="newMessage"
          type="text"
          placeholder="Haz una pregunta al concierge..."
          :disabled="loading"
          class="flex-1 pb-2 pt-1 border-b border-gray-200 bg-transparent rounded-none focus:outline-none focus:border-luxury-gold text-sm text-luxury-black placeholder-gray-400/40"
          autocomplete="off"
        />
        <button 
          type="submit" 
          :disabled="loading || !newMessage.trim()"
          class="w-9 h-9 rounded-full bg-luxury-black text-white hover:bg-luxury-gold hover:scale-105 transition-all flex items-center justify-center disabled:opacity-30 disabled:hover:bg-luxury-black disabled:scale-100 focus:outline-none"
        >
          <svg class="w-4 h-4 transform rotate-90" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 12 3.269 3.125A59.769 59.769 0 0 1 21.485 12 59.768 59.768 0 0 1 3.27 20.875L6 12Zm0 0h7.5" />
          </svg>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, watch } from 'vue'
import { apiClient } from '../lib/api'

const isOpen = ref(false)
const loading = ref(false)
const newMessage = ref('')
const messagesContainer = ref(null)

const messages = ref([
  {
    role: 'model',
    text: 'Bienvenido al Atelier Ibernovia. Soy tu asistente personal de concierge. ¿Deseas consultar nuestra colección 2026, conocer nuestros horarios o agendar una cita?'
  }
])

const toggleChat = () => {
  isOpen.value = !isOpen.value
  if (isOpen.value) {
    scrollToBottom()
  }
}

const scrollToBottom = async () => {
  await nextTick()
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

watch(messages, () => {
  scrollToBottom()
}, { deep: true })

const sendMessage = async () => {
  const text = newMessage.value.trim()
  if (!text || loading.value) return

  // Agregar mensaje de usuario
  messages.value.push({
    role: 'user',
    text
  })
  newMessage.value = ''
  loading.value = true
  
  // Esperar a que se renderice el mensaje del usuario
  await scrollToBottom()

  try {
    const response = await apiClient.post('/api/chat', {
      history: messages.value
    })
    
    messages.value.push({
      role: 'model',
      text: response.data.reply
    })
  } catch (error) {
    console.error('Error in chat request:', error)
    messages.value.push({
      role: 'model',
      text: 'Disculpa, ha ocurrido un error al conectar con el servicio concierge de Ibernovia. Por favor, inténtalo de nuevo.'
    })
  } finally {
    loading.value = false
    scrollToBottom()
  }
}
</script>

<style scoped>
.animate-slide-up {
  animation: slideUp 0.4s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.animate-fade-in {
  animation: fadeIn 0.3s ease-out both;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>
