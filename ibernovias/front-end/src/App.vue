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
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue'
import FooterSection from './components/FooterSection.vue'
import CookieBanner from './components/CookieBanner.vue'
import BackToTop from './components/BackToTop.vue'
</script>

<style>
/* IMPORTANTE: Estilos Globales de Lujo */
:root {
  --luxury-gold: #c4a06a;
  --luxury-gold-rgb: 196 160 106;
  --luxury-black: #1f1c17;
  --luxury-black-rgb: 31 28 23;
  --luxury-gray: #f6f2ed;
  --luxury-cream: #fbf8f2;
  --luxury-ink: #2a2621;
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

.signature-shell::before {
  content: '';
  position: fixed;
  inset: 0;
  pointer-events: none;
  z-index: 0;
  opacity: 0.7;
  background-image:
    linear-gradient(135deg, rgb(var(--luxury-gold-rgb) / 0.05) 0 1px, transparent 1px 100%),
    linear-gradient(45deg, rgb(var(--luxury-gold-rgb) / 0.03) 0 1px, transparent 1px 100%),
    radial-gradient(circle at 18% 12%, rgb(var(--luxury-gold-rgb) / 0.08), transparent 35%),
    radial-gradient(circle at 82% 10%, rgb(var(--luxury-gold-rgb) / 0.05), transparent 35%),
    radial-gradient(circle at 20% 88%, rgb(var(--luxury-gold-rgb) / 0.06), transparent 38%),
    radial-gradient(circle at 80% 85%, rgb(var(--luxury-gold-rgb) / 0.04), transparent 38%);
  background-size: 56px 56px, 72px 72px, auto, auto, auto, auto;
  background-position: 0 0, 14px 14px, center, center, center, center;
}

.signature-shell::after {
  content: '';
  position: fixed;
  inset: 10px;
  pointer-events: none;
  z-index: 0;
  border-radius: 24px;
  border: 1px solid rgb(var(--luxury-gold-rgb) / 0.18);
  background:
    radial-gradient(circle at 0% 0%, rgb(var(--luxury-gold-rgb) / 0.12), transparent 35%),
    radial-gradient(circle at 100% 0%, rgb(var(--luxury-gold-rgb) / 0.1), transparent 35%),
    radial-gradient(circle at 0% 100%, rgb(var(--luxury-gold-rgb) / 0.08), transparent 35%),
    radial-gradient(circle at 100% 100%, rgb(var(--luxury-gold-rgb) / 0.08), transparent 35%);
  box-shadow:
    0 0 0 1px rgb(var(--luxury-black-rgb) / 0.05) inset,
    0 24px 70px rgb(var(--luxury-black-rgb) / 0.08);
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
  background-image:
    radial-gradient(circle at 15% 10%, rgb(var(--luxury-gold-rgb) / 0.12), transparent 40%),
    radial-gradient(circle at 80% 5%, rgb(var(--luxury-gold-rgb) / 0.08), transparent 40%),
    linear-gradient(180deg, rgba(255, 255, 255, 0.9), rgba(246, 242, 237, 0.9));
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
  background: #c9a02c;
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