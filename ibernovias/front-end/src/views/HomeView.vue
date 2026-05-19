<template>
  <div class="min-h-screen bg-luxury-gray">
    <section class="atelier-hero" aria-label="Portada">
      <div class="atelier-overlay"></div>
      <div class="atelier-frame"></div>

      <div class="relative z-10 max-w-7xl mx-auto px-4 sm:px-6 h-full flex items-center justify-center">
        <div class="atelier-content text-center">
          <p class="atelier-kicker">Coleccion 2026</p>
          <h1 class="atelier-title">IBERNOVIA</h1>
          <p class="atelier-subtitle">ATELIER</p>
          <p class="atelier-copy">Diseño nupcial y complementos profesionales seleccionados para ceremonia, fiesta y comunión.</p>
          <router-link
            to="/acceso-empresarial"
            class="atelier-cta mt-7 inline-flex items-center px-6 py-3 text-xs sm:text-sm uppercase tracking-[0.24em] transition"
          >
            Acceso empresarial
          </router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
</script>

<style scoped>
.atelier-hero {
  position: relative;
  min-height: 78vh;
  margin: 0 auto;
  background-image: url('/images/catalogo/novia/velos/17815-lentejuelas_3.webp');
  background-size: cover;
  background-position: center;
}

.atelier-overlay {
  position: absolute;
  inset: 0;
  background:
    linear-gradient(180deg, rgb(255 255 255 / 0.58) 0%, rgb(255 255 255 / 0.5) 45%, rgb(255 255 255 / 0.66) 100%),
    radial-gradient(circle at 50% 16%, rgb(255 255 255 / 0.22), transparent 40%);
}

.atelier-frame {
  position: absolute;
  inset: 20px;
  border: 1px solid rgb(45 41 36 / 0.22);
  pointer-events: none;
}

.atelier-content {
  max-width: 760px;
  padding: clamp(1.2rem, 2.5vw, 2.2rem) clamp(1rem, 3vw, 2.6rem);
  border: 1px solid rgb(255 255 255 / 0.7);
  background: linear-gradient(180deg, rgb(255 255 255 / 0.58), rgb(255 255 255 / 0.45));
  backdrop-filter: blur(2px);
}

.atelier-kicker {
  font-size: 0.72rem;
  letter-spacing: 0.44em;
  text-transform: uppercase;
  color: rgb(58 52 45 / 0.88);
}

.atelier-title {
  margin-top: 0.65rem;
  font-family: 'Cormorant Garamond', 'Times New Roman', serif;
  font-size: clamp(2.7rem, 9vw, 6.5rem);
  font-weight: 500;
  letter-spacing: 0.16em;
  line-height: 0.95;
  color: rgb(45 41 36 / 0.92);
}

.atelier-subtitle {
  margin-top: 0.7rem;
  font-family: 'Cormorant Garamond', 'Times New Roman', serif;
  font-size: clamp(1.2rem, 3.1vw, 2.4rem);
  letter-spacing: 0.42em;
  text-transform: uppercase;
  color: rgb(58 52 45 / 0.82);
}

.atelier-copy {
  margin-top: 1.25rem;
  max-width: 700px;
  font-size: clamp(0.95rem, 2.2vw, 1.15rem);
  line-height: 1.7;
  text-wrap: balance;
  color: rgb(58 52 45 / 0.84);
}

.atelier-cta {
  border: 1px solid rgb(45 41 36 / 0.6);
  background: rgb(45 41 36 / 0.92);
  color: rgb(255 255 255 / 0.94);
}

.atelier-cta:hover {
  background: rgb(168 117 71 / 0.95);
  border-color: rgb(168 117 71 / 0.95);
}

@media (max-width: 640px) {
  .atelier-hero {
    min-height: 68vh;
  }

  .atelier-frame {
    inset: 10px;
  }

  .atelier-subtitle {
    letter-spacing: 0.22em;
  }

  .atelier-kicker {
    letter-spacing: 0.28em;
  }
}
</style>
