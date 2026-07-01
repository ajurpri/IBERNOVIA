<template>
  <footer class="footer-premium">
    <div class="footer-glow"></div>
    <div class="max-w-7xl mx-auto px-6 py-14 lg:py-16 relative z-10">
      <div class="grid grid-cols-1 lg:grid-cols-[1.15fr_0.85fr_0.9fr] gap-10 lg:gap-14 items-start">
        <section>
          <div class="mb-6">
            <span class="brand-title">IBERNOVIA</span>
            <span class="brand-subtitle">ATELIER</span>
          </div>

          <p class="footer-copy max-w-xl">
            Complementos y moda nupcial con una presentación elegante, cuidada y profesional para ceremonia, fiesta y comunión.
          </p>

          <div class="mt-7 flex items-center gap-4">
            <a href="https://www.instagram.com" target="_blank" rel="noopener" class="social-icon" aria-label="Instagram">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <rect x="3" y="3" width="18" height="18" rx="5" ry="5" stroke-width="1.8"></rect>
                <circle cx="12" cy="12" r="4.1" stroke-width="1.8"></circle>
                <circle cx="17.2" cy="6.8" r="1.1" fill="currentColor" stroke="none"></circle>
              </svg>
            </a>
            <a href="https://www.facebook.com" target="_blank" rel="noopener" class="social-icon" aria-label="Facebook">
              <svg viewBox="0 0 24 24" fill="currentColor">
                <path d="M14 8.5V7a1 1 0 0 1 1-1h1.5V3.5H15c-2.48 0-4 1.62-4 4.25v.75H9v3h2v9h3v-9h2.2l.4-3H14Z"/>
              </svg>
            </a>
          </div>
        </section>

        <section>
          <h3 class="footer-title">Enlaces</h3>
          <nav class="footer-links">
            <router-link to="/">Inicio</router-link>
            <router-link to="/tienda">Catálogo</router-link>
            <router-link to="/contacto">Contacto</router-link>
            <router-link to="/acceso-empresarial">Acceso empresarial</router-link>
            <router-link to="/privacidad">Privacidad</router-link>
          </nav>
        </section>

        <section>
          <h3 class="footer-title">Contacto</h3>
          <ul class="footer-contact">
            <li>
              <span class="contact-label">Teléfono</span>
              <a href="tel:+34953515070">953 51 50 70</a>
            </li>
            <li>
              <span class="contact-label">Correo</span>
              <a href="mailto:info@ibernovia.es">info@ibernovia.es</a>
            </li>
            <li>
              <span class="contact-label">Dirección</span>
              <span>Calle Forjadores, 1 · Andújar · Jaén</span>
            </li>
          </ul>
        </section>
      </div>

      <div class="footer-bottom mt-12 pt-6">
        <p>&copy; 2026 IBERNOVIA. Todos los derechos reservados.</p>
      </div>
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
.footer-premium {
  position: relative;
  overflow: hidden;
  background:
    radial-gradient(circle at top left, rgb(196 160 106 / 0.12), transparent 35%),
    radial-gradient(circle at top right, rgb(255 255 255 / 0.05), transparent 25%),
    #171513;
  color: #f5f2ec;
}

.footer-glow {
  position: absolute;
  inset: 0;
  pointer-events: none;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.02), transparent 20%, transparent 80%, rgb(168 117 71 / 0.04));
}

.brand-title {
  font-family: 'Fraunces', Georgia, serif;
  font-size: 1.85rem;
  font-weight: 300;
  letter-spacing: 0.22em;
  color: #ffffff;
  display: block;
  line-height: 1.2;
}

.brand-subtitle {
  margin-top: 0.25rem;
  font-size: 0.65rem;
  font-weight: 700;
  letter-spacing: 0.38em;
  text-transform: uppercase;
  color: #a87547;
  display: block;
  line-height: 1.2;
}

.footer-copy {
  color: rgb(255 255 255 / 0.72);
  line-height: 1.8;
  font-size: 0.95rem;
}

.footer-title {
  font-size: 0.78rem;
  text-transform: uppercase;
  letter-spacing: 0.24em;
  color: rgb(255 255 255 / 0.85);
  margin-bottom: 1.25rem;
}

.footer-links {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
}

.footer-links a {
  color: rgb(255 255 255 / 0.72);
  transition: color 0.25s ease, transform 0.25s ease;
}

.footer-links a:hover {
  color: #a87547;
  transform: translateX(3px);
}

.footer-contact {
  display: grid;
  gap: 1.1rem;
}

.footer-contact li {
  display: grid;
  gap: 0.35rem;
}

.contact-label {
  font-size: 0.72rem;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  color: rgb(255 255 255 / 0.48);
}

.footer-contact a {
  color: #ffffff;
  font-size: 0.98rem;
  transition: color 0.25s ease;
}

.footer-contact a:hover {
  color: #a87547;
}

.footer-contact span:last-child {
  color: #ffffff;
  font-size: 0.98rem;
}

.social-icon {
  width: 2.8rem;
  height: 2.8rem;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: 1px solid rgb(255 255 255 / 0.12);
  background: rgb(255 255 255 / 0.04);
  color: rgb(255 255 255 / 0.72);
  transition: transform 0.25s ease, background 0.25s ease, border-color 0.25s ease, color 0.25s ease;
}

.social-icon svg {
  width: 1.1rem;
  height: 1.1rem;
}

.social-icon:hover {
  transform: translateY(-2px);
  background: rgb(168 117 71 / 0.12);
  border-color: rgb(168 117 71 / 0.45);
  color: #a87547;
}

.footer-bottom {
  border-top: 1px solid rgb(255 255 255 / 0.08);
  color: rgb(255 255 255 / 0.45);
  font-size: 0.78rem;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}
</style>
