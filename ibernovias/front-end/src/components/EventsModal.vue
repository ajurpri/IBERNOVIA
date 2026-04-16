<template>
  <Teleport to="body">
    <div
      v-if="showModal"
      class="fixed inset-0 z-[9999] bg-black/60 p-3 sm:p-5 flex items-center justify-center"
      @click.self="closeModal"
    >
      <div class="w-full max-w-2xl max-h-[88dvh] rounded-2xl border border-luxury-gold/25 bg-white shadow-2xl overflow-hidden flex flex-col">
        <div class="px-6 py-5 border-b border-gray-100 flex items-start justify-between gap-4">
          <div>
            <p class="text-xs uppercase tracking-[0.28em] text-luxury-gold font-semibold">Agenda Ibernovia</p>
            <h2 class="mt-2 font-serif text-2xl sm:text-3xl text-luxury-black">Eventos profesionales</h2>
          </div>
          <button
            type="button"
            class="shrink-0 w-10 h-10 rounded-full border border-gray-200 text-gray-500 hover:text-luxury-black hover:border-luxury-gold transition"
            aria-label="Cerrar ventana de eventos"
            @click="closeModal"
          >
            ✕
          </button>
        </div>

        <div class="px-6 py-5 overflow-y-auto space-y-3">
          <div v-if="loading" class="py-10 text-center text-gray-500">Cargando eventos...</div>
          <div v-else-if="events.length === 0" class="py-10 text-center text-gray-500">No hay eventos próximos</div>
          <article
            v-for="eventItem in events"
            v-else
            :key="eventItem.id"
            class="rounded-xl border border-gray-200 bg-white px-4 py-4"
          >
            <p class="text-[11px] uppercase tracking-[0.2em] text-luxury-gold font-semibold">{{ formatEventDate(eventItem.fecha) }}</p>
            <h4 class="mt-2 text-base font-semibold text-luxury-black">{{ eventItem.titulo }}</h4>
            <p class="mt-1 text-sm text-gray-600">{{ eventItem.descripcion }}</p>
            <p class="mt-2 text-xs uppercase tracking-[0.12em] text-gray-500">{{ eventItem.lugar }}</p>
          </article>
        </div>

        <div class="px-6 py-5 border-t border-gray-100 bg-gray-50 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-3">
          <p class="text-sm text-gray-600">¿Quieres asistir? Solicita acceso y te informamos de próximas convocatorias.</p>
          <router-link
            to="/acceso-empresarial"
            class="inline-flex items-center px-5 py-2.5 rounded-full bg-luxury-black text-white text-xs font-semibold uppercase tracking-[0.2em] hover:bg-luxury-gold hover:text-luxury-black transition"
            @click="closeModal"
          >
            Solicitar acceso
          </router-link>
        </div>
      </div>
    </div>
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