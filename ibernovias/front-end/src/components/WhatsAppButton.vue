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
const message = 'Hola Ibernovia, quería hacer una consulta sobre vuestros productos.'
const whatsappUrl = `https://wa.me/${phone}?text=${encodeURIComponent(message)}`
</script>

<style scoped>
.whatsapp-float {position:fixed;left:1rem;bottom:1rem;z-index:60;display:inline-flex;align-items:center;gap:.5rem;padding:.6rem 1rem;min-height:48px;border-radius:5px;border:1px solid #c7d7cb;background:#fff;color:#21643c;text-decoration:none;box-shadow:0 2px 8px #0000000d;}
.whatsapp-float:hover {background:#eff6f1;}
.whatsapp-icon {width:26px;height:26px;display:inline-flex;}
.whatsapp-icon svg {width:26px;height:26px;}
.whatsapp-copy {font-size:14px;font-weight:600;}
/* In normal flow on mobile, so it cannot cover products or cookie controls. */
@media(max-width:640px){.whatsapp-float{position:relative;left:auto;bottom:auto;align-self:center;margin:1rem;}}
</style>
