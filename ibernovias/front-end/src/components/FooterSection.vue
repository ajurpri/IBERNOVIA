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

@media (max-width: 640px) {
  .footer-premium .max-w-7xl {
    padding-left: 1rem;
    padding-right: 1rem;
    padding-bottom: 6.5rem;
  }

  .brand-title {
    font-size: 1.55rem;
    letter-spacing: 0.16em;
  }

  .brand-subtitle,
  .footer-bottom {
    letter-spacing: 0.18em;
  }
}
</style>
