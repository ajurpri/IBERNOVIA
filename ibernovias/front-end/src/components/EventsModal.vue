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
          <article
            v-for="eventItem in events"
            :key="eventItem.title"
            class="rounded-xl border border-gray-200 bg-white px-4 py-4"
          >
            <p class="text-[11px] uppercase tracking-[0.2em] text-luxury-gold font-semibold">{{ eventItem.displayDate }}</p>
            <h4 class="mt-2 text-base font-semibold text-luxury-black">{{ eventItem.title }}</h4>
            <p class="mt-1 text-sm text-gray-600">{{ eventItem.description }}</p>
            <p class="mt-2 text-xs uppercase tracking-[0.12em] text-gray-500">{{ eventItem.place }}</p>
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
import { ref } from 'vue'

const showModal = ref(false)
const events = [
  {
    displayDate: '14 MAR 2026 · 18:00',
    title: 'Presentación Colección Atelier',
    description: 'Novedades de catálogo y planificación de campaña para clientes profesionales.',
    place: 'Atelier Ibernovia · Andújar'
  },
  {
    displayDate: '28 MAR 2026 · 11:30',
    title: 'Pruebas Privadas de Tocados',
    description: 'Sesiones para selección de producto y combinación de referencias.',
    place: 'Plaza de Abastos · Andújar'
  },
  {
    displayDate: '11 ABR 2026 · 17:30',
    title: 'Jornada Novia Invitada',
    description: 'Visión comercial de tendencias y producto para temporada.',
    place: 'Espacio Ibernovia'
  }
]

const openModal = () => {
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
}

defineExpose({ openModal, closeModal })
</script>