<template>
  <footer class="footer-premium">
    <div class="max-w-7xl mx-auto px-6 py-14 lg:py-16 relative z-10">
      <div class="grid grid-cols-1 md:grid-cols-[1.15fr_0.85fr_0.9fr] gap-10 lg:gap-14 items-start">
        <section>
          <div class="mb-6">
            <span class="brand-title">IBERNOVIA</span>
            <span class="brand-subtitle">ATELIER</span>
          </div>

          <p class="footer-copy max-w-xl">
            Complementos de ceremonia para tiendas y profesionales: novia, novio, fiesta, comunión y arras.
          </p>

          <!-- Social profiles hidden until official URLs are confirmed. -->
        </section>

        <section>
          <h3 class="footer-title">Enlaces</h3>
          <nav class="footer-links">
            <router-link to="/">Inicio</router-link>
            <router-link to="/tienda">Catálogo</router-link>
            <router-link to="/contacto">Contacto</router-link>
            <router-link to="/auth">Acceso profesional</router-link>
            <router-link to="/acceso-empresarial">Solicitar alta</router-link>
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
              <span class="contact-label">WhatsApp</span>
              <a href="https://wa.me/34619625679?text=Hola%20Ibernovia%2C%20queria%20hacer%20una%20consulta%20sobre%20vuestros%20productos." target="_blank" rel="noopener">619 62 56 79</a>
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
        <nav aria-label="Información legal" class="flex flex-wrap gap-x-5 gap-y-3 mt-4">
          <router-link to="/privacidad">Privacidad</router-link>
          <router-link to="/legal">Aviso legal</router-link>
          <router-link to="/cookies">Cookies</router-link>
          <button type="button" @click="openCookiePreferences">Preferencias de cookies</button>
        </nav>
        <p class="site-credit">
          Web realizada por <a href="https://alvaroo.dev" target="_blank" rel="noopener noreferrer">alvaroo.dev</a>
          <span aria-hidden="true"> · </span>
          <a href="https://www.instagram.com/alvaroo.dev/" target="_blank" rel="noopener noreferrer" aria-label="Instagram de alvaroo.dev">Instagram</a>
        </p>
      </div>
    </div>
  </footer>
</template>

<script setup>
const openCookiePreferences = () => window.dispatchEvent(new Event('ibernovia:cookie-preferences'))
</script>

<style scoped>
.footer-premium {
  position: relative;
  overflow: hidden;
  background: #24211d;
  color: #f5f2ec;
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
  text-transform: none;
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
  text-transform: none;
  letter-spacing: 0.02em;
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
  transition: color 0.2s ease;
}

.footer-links a:hover {
  color: #a87547;
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
  text-transform: none;
  letter-spacing: 0.02em;
  color: rgb(255 255 255 / 0.68);
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

.footer-bottom {
  border-top: 1px solid rgb(255 255 255 / 0.08);
  color: rgb(255 255 255 / 0.68);
  font-size: 0.78rem;
  letter-spacing: normal;
  text-transform: none;
}

.site-credit {margin-top:1.25rem;font-size:.75rem;line-height:1.6;color:#c8c1b8;}
.site-credit a {text-decoration:underline;text-underline-offset:3px;}
.site-credit a:hover {color:#fff;}

@media (max-width: 640px) {
  .footer-premium .max-w-7xl {
    padding-left: 1rem;
    padding-right: 1rem;
    padding-bottom: 2rem;
  }

  .brand-title {
    font-size: 1.55rem;
    letter-spacing: 0.16em;
  }

  .brand-subtitle {
    letter-spacing: 0.18em;
  }
}
</style>
