<template>
  <Teleport to="body">
    <transition name="modal-fade">
      <div
        v-if="showModal"
        class="fixed inset-0 z-[9999] bg-black/50 backdrop-blur-sm p-4 flex items-center justify-center"
        @click.self="closeModal"
      >
        <!-- Modal Card Container -->
        <div class="w-full max-w-2xl max-h-[85dvh] rounded-2xl border border-black/5 bg-[#fdfdfc] shadow-2xl overflow-hidden flex flex-col animate-modal-slide">
          
          <!-- Header -->
          <div class="px-6 py-5 border-b border-black/5 flex items-start justify-between gap-4">
            <div>
              <span class="text-[9px] uppercase tracking-[0.25em] text-gray-400 font-bold block mb-1">AGENDA IBERNOVIA</span>
              <h2 class="font-serif text-2xl font-light text-luxury-black tracking-wide">Eventos Profesionales</h2>
            </div>
            <button
              type="button"
              class="w-10 h-10 rounded-full border border-gray-100 flex items-center justify-center text-gray-400 hover:text-luxury-black hover:border-luxury-gold transition-all duration-300 focus:outline-none"
              aria-label="Cerrar ventana de eventos"
              @click="closeModal"
            >
              <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
              </svg>
            </button>
          </div>

          <!-- Body / List (Borderless editorial list) -->
          <div class="px-8 py-4 overflow-y-auto flex-1 divide-y divide-black/5">
            <div v-if="loading" class="py-12 text-center text-sm text-gray-400 font-light font-serif italic">
              Cargando agenda de eventos...
            </div>
            
            <div v-else-if="events.length === 0" class="py-12 text-center text-sm text-gray-400 font-light font-serif italic">
              No hay eventos próximos programados en este momento.
            </div>
            
            <article
              v-else
              v-for="eventItem in events"
              :key="eventItem.id"
              class="py-6 first:pt-2 last:pb-2"
            >
              <p class="text-[9px] uppercase tracking-[0.25em] text-luxury-gold font-bold mb-1.5">
                {{ formatEventDate(eventItem.fecha) }}
              </p>
              <h4 class="font-serif text-xl text-luxury-black font-light leading-snug mb-2">
                {{ eventItem.titulo }}
              </h4>
              <p class="text-xs text-gray-500 leading-relaxed text-balance mb-3">
                {{ eventItem.descripcion }}
              </p>
              <div class="flex items-center gap-2 text-[9px] uppercase tracking-widest text-gray-400 font-bold">
                <span class="w-1.5 h-1.5 rounded-full bg-luxury-gold"></span>
                <span>{{ eventItem.lugar }}</span>
              </div>
            </article>
          </div>

          <!-- Footer -->
          <div class="px-6 py-5 border-t border-black/5 bg-transparent flex flex-col sm:flex-row items-center justify-between gap-4">
            <p class="text-xs text-gray-400 text-center sm:text-left max-w-sm leading-relaxed text-balance">
              ¿Deseas asistir a alguno de nuestros desfiles o presentaciones? Solicita tu acreditación profesional.
            </p>
            <router-link
              to="/acceso-empresarial"
              class="inline-flex justify-center items-center px-6 py-3 bg-luxury-black text-white hover:bg-luxury-gold hover:text-white text-[10px] font-bold uppercase tracking-[0.2em] transition-all duration-300 rounded-full shadow-md hover:shadow-lg hover:-translate-y-0.5"
              @click="closeModal"
            >
              Solicitar acceso
            </router-link>
          </div>
          
        </div>
      </div>
    </transition>
  </Teleport>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { apiClient } from '../lib/api'

const showModal = ref(false)
const events = ref([])
const loading = ref(false)

const formatEventDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', { 
    day: '2-digit', 
    month: 'short', 
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  }).toUpperCase()
}

const loadEvents = async () => {
  try {
    loading.value = true
    const res = await apiClient.get('/api/eventos')
    events.value = res.data || []
  } catch (e) {
    console.error('Error cargando eventos:', e)
    events.value = []
  } finally {
    loading.value = false
  }
}

const openModal = () => {
  showModal.value = true
  if (events.value.length === 0) {
    loadEvents()
  }
}

const closeModal = () => {
  showModal.value = false
}

onMounted(() => {
  loadEvents()
})

defineExpose({ openModal, closeModal })
</script>

<style scoped>
/* Modal transition */
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.35s cubic-bezier(0.16, 1, 0.3, 1);
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-fade-enter-active .animate-modal-slide,
.modal-fade-leave-active .animate-modal-slide {
  transition: transform 0.35s cubic-bezier(0.16, 1, 0.3, 1), opacity 0.35s cubic-bezier(0.16, 1, 0.3, 1);
}

.modal-fade-enter-from .animate-modal-slide,
.modal-fade-leave-to .animate-modal-slide {
  transform: translateY(16px) scale(0.97);
  opacity: 0;
}
</style>