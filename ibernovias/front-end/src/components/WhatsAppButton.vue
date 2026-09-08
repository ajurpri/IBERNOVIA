<template>
  <a
    class="whatsapp-float"
    :href="whatsappUrl"
    target="_blank"
    rel="noopener"
    aria-label="Escribir a Ibernovia por WhatsApp"
  >
    <span class="whatsapp-icon" aria-hidden="true">
      <svg viewBox="0 0 32 32" fill="currentColor">
        <path d="M16.02 4.2A11.62 11.62 0 0 0 6.1 21.9L4.8 27.7l5.95-1.42A11.62 11.62 0 1 0 16.02 4.2Zm0 2.1a9.52 9.52 0 0 1 8.05 14.62 9.52 9.52 0 0 1-12.88 3.12l-.36-.22-3.22.77.7-3.14-.24-.38A9.52 9.52 0 0 1 16.02 6.3Zm-3.25 4.75c-.2-.45-.42-.46-.62-.47h-.53c-.18 0-.47.07-.72.35-.25.27-.95.93-.95 2.27s.98 2.64 1.12 2.82c.14.18 1.9 3.05 4.7 4.15 2.33.92 2.8.74 3.3.69.5-.04 1.63-.66 1.86-1.3.23-.63.23-1.18.16-1.3-.07-.1-.25-.17-.53-.31-.27-.14-1.63-.8-1.88-.9-.25-.08-.44-.13-.62.14-.18.27-.72.9-.88 1.08-.16.18-.32.2-.6.07-.27-.14-1.15-.42-2.2-1.35-.81-.72-1.36-1.62-1.52-1.9-.16-.27-.02-.42.12-.56.13-.12.28-.32.42-.48.14-.16.18-.27.27-.45.09-.18.05-.34-.02-.48-.07-.14-.6-1.49-.84-2.03Z" />
      </svg>
    </span>
    <span class="whatsapp-copy">WhatsApp</span>
  </a>
</template>

<script setup>
const phone = '34619625679'
const message = 'Hola Ibernovia, queria hacer una consulta sobre vuestros productos.'
const whatsappUrl = `https://wa.me/${phone}?text=${encodeURIComponent(message)}`
</script>

<style scoped>
.whatsapp-float {
  position: fixed;
  left: 1rem;
  bottom: 1rem;
  z-index: 98;
  min-height: 54px;
  display: inline-flex;
  align-items: center;
  gap: 0.65rem;
  padding: 0.65rem 0.95rem 0.65rem 0.72rem;
  border-radius: 999px;
  background: #25d366;
  color: white;
  box-shadow: 0 16px 36px rgb(0 0 0 / 0.18);
  border: 1px solid rgb(255 255 255 / 0.42);
  text-decoration: none;
  transition: transform 0.2s ease, box-shadow 0.2s ease, background 0.2s ease;
}

.whatsapp-float:hover {
  transform: translateY(-2px);
  background: #1fbd5a;
  box-shadow: 0 18px 42px rgb(0 0 0 / 0.22);
}

.whatsapp-icon {
  width: 34px;
  height: 34px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: rgb(255 255 255 / 0.18);
  flex: 0 0 auto;
}

.whatsapp-icon svg {
  width: 26px;
  height: 26px;
}

.whatsapp-copy {
  font-size: 11px;
  font-weight: 800;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  white-space: nowrap;
}

@media (max-width: 640px) {
  .whatsapp-float {
    left: 0.75rem;
    bottom: 0.75rem;
    min-height: 48px;
    padding: 0.55rem;
  }

  .whatsapp-copy {
    position: absolute;
    width: 1px;
    height: 1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    white-space: nowrap;
  }

  .whatsapp-icon {
    width: 38px;
    height: 38px;
  }
}
</style>
