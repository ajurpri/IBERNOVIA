<template>
  <footer class="footer-premium">
    <div class="footer-glow"></div>
    <div class="max-w-7xl mx-auto px-6 py-14 lg:py-16 relative z-10">
      <div class="grid grid-cols-1 lg:grid-cols-[1.15fr_0.85fr_0.9fr] gap-10 lg:gap-14 items-start">
        <section>
          <div class="inline-flex items-center gap-4 mb-6">
            <div class="logo-frame">
              <img :src="logoSrc" alt="IBERNOVIA" class="logo-image" />
            </div>
            <div>
              <p class="brand-kicker">IBERNOVIA</p>
              <p class="brand-subtitle">Wedding Fashion</p>
            </div>
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
              <span>Plaza de Abastos · Andújar · Jaén</span>
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
const logoBase = import.meta.env.BASE_URL + 'logo/optimized/'
const logoSrc = `${logoBase}logo-280.jpg`
</script>

<style scoped>
.footer-premium {
  position: relative;
  overflow: hidden;
  background:
    radial-gradient(circle at top left, rgb(196 160 106 / 0.14), transparent 30%),
    radial-gradient(circle at top right, rgb(255 255 255 / 0.06), transparent 22%),
    #050505;
  color: #f5f2ec;
}

.footer-glow {
  position: absolute;
  inset: 0;
  pointer-events: none;
  background: linear-gradient(180deg, rgb(255 255 255 / 0.03), transparent 18%, transparent 82%, rgb(196 160 106 / 0.06));
}

.logo-frame {
  background: #f0dfca;
  padding: 0.65rem;
  border-radius: 1.25rem;
  box-shadow: 0 18px 50px rgb(0 0 0 / 0.35);
}

.logo-image {
  display: block;
  height: 5.25rem;
  width: auto;
  object-fit: contain;
}

.brand-kicker {
  font-family: Georgia, 'Times New Roman', serif;
  font-size: 1.5rem;
  letter-spacing: 0.16em;
  color: #ffffff;
}

.brand-subtitle {
  margin-top: 0.2rem;
  font-size: 0.74rem;
  letter-spacing: 0.28em;
  text-transform: uppercase;
  color: rgb(255 255 255 / 0.66);
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
  color: rgb(255 255 255 / 0.8);
  margin-bottom: 1rem;
}

.footer-links {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
}

.footer-links a {
  color: rgb(255 255 255 / 0.72);
  transition: color 0.2s ease, transform 0.2s ease;
}

.footer-links a:hover {
  color: #ffffff;
  transform: translateX(2px);
}

.footer-contact {
  display: grid;
  gap: 1rem;
}

.footer-contact li {
  display: grid;
  gap: 0.3rem;
}

.contact-label {
  font-size: 0.72rem;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  color: rgb(255 255 255 / 0.48);
}

.footer-contact a,
.footer-contact span:last-child {
  color: #ffffff;
  font-size: 0.98rem;
}

.social-icon {
  width: 2.9rem;
  height: 2.9rem;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: 1px solid rgb(255 255 255 / 0.12);
  background: rgb(255 255 255 / 0.04);
  color: #ffffff;
  transition: transform 0.2s ease, background 0.2s ease, border-color 0.2s ease;
}

.social-icon svg {
  width: 1.15rem;
  height: 1.15rem;
}

.social-icon:hover {
  transform: translateY(-2px);
  background: rgb(196 160 106 / 0.16);
  border-color: rgb(196 160 106 / 0.35);
}

.footer-bottom {
  border-top: 1px solid rgb(255 255 255 / 0.1);
  color: rgb(255 255 255 / 0.45);
  font-size: 0.78rem;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}
</style>
