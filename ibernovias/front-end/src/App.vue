<template>
  <div class="flex flex-col w-screen min-h-screen overflow-x-hidden">
    <NavBar /> 
    
    <main class="flex-1 w-full">
      <router-view />
    </main>

    <FooterSection />
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue'
import FooterSection from './components/FooterSection.vue'
</script>

<style>
/* IMPORTANTE: Estilos Globales de Lujo */
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,700;1,400&family=Lato:wght@300;400;700&display=swap');

:root {
  --luxury-gold: #d4af37;
  --luxury-black: #1a1a1a;
  --luxury-gray: #f9f9f9;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body, #app {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Lato', sans-serif;
  color: var(--luxury-black);
  background-color: #ffffff;
  margin: 0;
  overflow-x: hidden;
}

.font-serif {
  font-family: 'Playfair Display', serif !important;
}

.text-luxury-gold { color: var(--luxury-gold); }
.bg-luxury-gold { background-color: var(--luxury-gold); }
.border-luxury-gold { border-color: var(--luxury-gold); }

.text-luxury-black { color: var(--luxury-black); }
.bg-luxury-black { background-color: var(--luxury-black); }

.hover\:text-luxury-gold:hover { color: var(--luxury-gold) !important; }
.hover\:bg-luxury-black:hover { background-color: var(--luxury-black) !important; }
</style>