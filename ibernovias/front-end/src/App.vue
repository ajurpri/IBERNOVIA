<template>
  <div class="signature-shell flex flex-col w-screen min-h-screen overflow-x-hidden">
    <a href="#main-content" class="skip-link">Saltar al contenido</a>
    <NavBar /> 
    
    <main id="main-content" tabindex="-1" class="flex-1 w-full">
      <router-view />
    </main>

    <BackToTop />
    <FooterSection />
    <CookieBanner />
    <Toast ref="toastRef" />
    <FloatingChat />
  </div>
</template>

<script setup>
import { ref, provide } from 'vue'
import NavBar from './components/NavBar.vue'
import FooterSection from './components/FooterSection.vue'
import CookieBanner from './components/CookieBanner.vue'
import BackToTop from './components/BackToTop.vue'
import Toast from './components/Toast.vue'
import FloatingChat from './components/FloatingChat.vue'
import { useAuthStore } from './stores/auth'

const authStore = useAuthStore()
const toastRef = ref(null)

// Proporcionar el toast globalmente
const showToast = (message, type = 'info', duration = 3000) => {
  toastRef.value?.show(message, type, duration)
}

provide('toast', { show: showToast })
</script>

<style>
/* IMPORTANTE: Estilos Globales de Lujo */
:root {
  --luxury-gold: #a87547;
  --luxury-gold-rgb: 168 117 71;
  --luxury-black: #2d2924;
  --luxury-black-rgb: 45 41 36;
  --luxury-gray: #d9d9d9;
  --luxury-cream: #fcfaf6;
  --luxury-ink: #3a342d;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* Global signature: subtle jewelry lattice + gold frame */
.signature-shell {
  position: relative;
  isolation: isolate;
}

.signature-shell > * {
  position: relative;
  z-index: 1;
}

.signature-shell::before,
.signature-shell::after {
  content: none;
}

.skip-link {
  position: absolute;
  top: 0;
  left: 0;
  transform: translateY(-120%);
  background: var(--luxury-black);
  color: white;
  padding: 0.75rem 1rem;
  z-index: 1000;
  border-radius: 0 0 8px 0;
  transition: transform 0.2s ease;
}

.skip-link:focus {
  transform: translateY(0);
  outline: 2px solid var(--luxury-gold);
  outline-offset: 2px;
}

@media (max-width: 640px) {
  .signature-shell::after {
    inset: 0;
    border-radius: 0;
    border-left: 0;
    border-right: 0;
  }
}

html, body, #app {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Manrope', 'Segoe UI', sans-serif;
  color: var(--luxury-ink);
  background-color: var(--luxury-gray);
  background-image: none;
  background-attachment: fixed;
  margin: 0;
  overflow-x: hidden;
}

/* Animaciones Premium */
@keyframes fade-in-up {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes subtle-zoom {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

.animate-fade-in-up {
  animation: fade-in-up 1s ease-out;
}

.animate-subtle-zoom {
  animation: subtle-zoom 20s ease-in-out infinite;
}

/* Scrollbar personalizada */
::-webkit-scrollbar {
  width: 10px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: var(--luxury-gold);
  border-radius: 5px;
}

::-webkit-scrollbar-thumb:hover {
  background: #95633d;
}

/* Hover effects premium */
.hover-lift {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.hover-lift:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.text-luxury-gold { color: var(--luxury-gold); }
.bg-luxury-gold { background-color: var(--luxury-gold); }
.border-luxury-gold { border-color: var(--luxury-gold); }

.text-luxury-black { color: var(--luxury-black); }
.bg-luxury-black { background-color: var(--luxury-black); }

.hover\:text-luxury-gold:hover { color: var(--luxury-gold) !important; }
.hover\:bg-luxury-black:hover { background-color: var(--luxury-black) !important; }
</style>