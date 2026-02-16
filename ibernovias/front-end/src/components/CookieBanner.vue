<template>
  <div v-if="showBanner" class="cookie-banner" role="dialog" aria-live="polite" aria-label="Aviso de cookies">
    <div class="cookie-banner__content">
      <p class="cookie-banner__text">
        Usamos cookies propias para mejorar la experiencia. Puedes leer más en la
        <router-link to="/cookies" class="cookie-banner__link">política de cookies</router-link>.
      </p>
      <div class="cookie-banner__actions">
        <button class="cookie-banner__btn cookie-banner__btn--secondary" @click="reject">Rechazar</button>
        <button class="cookie-banner__btn" @click="accept">Aceptar</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const showBanner = ref(false)

const accept = () => {
  localStorage.setItem('cookie_consent', 'accepted')
  showBanner.value = false
}

const reject = () => {
  localStorage.setItem('cookie_consent', 'rejected')
  showBanner.value = false
}

onMounted(() => {
  const consent = localStorage.getItem('cookie_consent')
  showBanner.value = !consent
})
</script>

<style scoped>
.cookie-banner {
  position: fixed;
  inset: auto 16px 16px 16px;
  z-index: 1000;
  background: #1a1a1a;
  color: #fff;
  border: 1px solid rgba(212, 175, 55, 0.4);
  border-radius: 16px;
  padding: 16px 18px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.25);
}

.cookie-banner__content {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.cookie-banner__text {
  font-size: 0.9rem;
  line-height: 1.4;
}

.cookie-banner__link {
  color: #d4af37;
  text-decoration: underline;
}

.cookie-banner__actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.cookie-banner__btn {
  background: #d4af37;
  color: #1a1a1a;
  border: none;
  padding: 8px 14px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  font-size: 0.7rem;
  border-radius: 999px;
  cursor: pointer;
}

.cookie-banner__btn--secondary {
  background: transparent;
  color: #fff;
  border: 1px solid rgba(255, 255, 255, 0.4);
}

@media (min-width: 768px) {
  .cookie-banner {
    inset: auto 24px 24px auto;
    max-width: 520px;
  }
  .cookie-banner__content {
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    gap: 16px;
  }
  .cookie-banner__text {
    max-width: 60%;
  }
}
</style>
