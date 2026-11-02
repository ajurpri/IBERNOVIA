from pathlib import Path
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8')
a=s.index('    <section class="home-hero"');b=s.index('    <section class="home-families"',a)
s=s[:a]+'''    <section class="home-hero" aria-label="Portada">
      <picture class="home-hero-photo">
        <source media="(max-width: 640px)" srcset="/images/portada/velo-640.webp 640w, /images/portada/velo-960.webp 960w" sizes="100vw">
        <img src="/images/catalogo/novia/velos/17815-lentejuelas.webp" alt="Detalle de un velo del catálogo Ibernovia" width="1200" height="797" loading="eager" fetchpriority="high" decoding="async">
      </picture>
      <div class="home-hero-copy">
        <p class="home-eyebrow">Colección de ceremonia</p>
        <h1 class="home-wordmark">IBERNOVIA</h1>
        <p class="home-subline">Complementos de ceremonia</p>
        <div class="home-rule" aria-hidden="true"></div>
        <p class="home-hero-description">Novia, novio, fiesta, comunión y arras.<br>Catálogo para tiendas y profesionales.</p>
        <div class="home-hero-actions">
          <router-link to="/tienda" class="home-primary">Explorar catálogo</router-link>
          <router-link to="/auth" class="home-secondary">Acceso profesional</router-link>
        </div>
      </div>
    </section>

'''+s[b:]
s=s.replace('<!-- Static selection; no duplicated products or automatic movement. -->','<!-- Restore the original moving selection with an explicit pause control. -->')
a=s.index('      <div v-else-if="products.length > 0" class="featured-products');b=s.index('      <div v-else class=',a)
s=s[:a]+'''      <div v-else-if="products.length > 0" class="featured-window" :class="{ 'is-paused': selectionPaused }">
        <div class="featured-toolbar max-w-7xl mx-auto px-4 sm:px-6">
          <button type="button" @click="selectionPaused = !selectionPaused" :aria-pressed="selectionPaused" class="selection-toggle">{{ selectionPaused ? 'Reanudar movimiento' : 'Pausar movimiento' }}</button>
        </div>
        <div class="featured-track">
          <div v-for="copy in 2" :key="copy" class="featured-group" :aria-hidden="copy === 2 ? 'true' : undefined" :inert="copy === 2 ? true : undefined">
            <router-link v-for="prod in products" :key="prod.id" :to="`/producto/${prod.id}`" class="featured-product" :tabindex="copy === 2 ? -1 : undefined">
              <img :src="getProductImage(prod)" :alt="productName(prod)" width="400" height="533" loading="lazy" fetchpriority="low" decoding="async">
              <h3>{{ productName(prod) }}</h3>
              <p>Referencia: {{ productReference(prod) }}</p>
            </router-link>
          </div>
        </div>
      </div>

'''+s[b:]
s=s.replace('const products = ref([])','const products = ref([])\nconst selectionPaused = ref(false)').replace('.slice(0, 6)','.slice(0, 15)')
a=s.index('.home-hero {display:grid;');s=s[:a]+'''
.home-hero {position:relative;display:flex;align-items:center;justify-content:center;min-height:550px;height:85vh;overflow:hidden;background:#fcfaf6;}
.home-hero-photo {position:absolute;inset:0;}
.home-hero-photo img {width:100%;height:100%;object-fit:cover;object-position:center;}
.home-hero::after {content:'';position:absolute;inset:0;background:linear-gradient(180deg,rgba(253,253,252,.78),rgba(253,253,252,.68) 55%,rgba(253,253,252,.24));}
.home-hero-copy {position:relative;z-index:1;text-align:center;max-width:900px;padding:3rem 1.5rem;}
.home-eyebrow {font-size:.8rem;letter-spacing:.2em;text-transform:uppercase;color:#715035;margin-bottom:1.5rem;}
.home-wordmark {font-family:'Fraunces',Georgia,serif;font-size:clamp(3rem,7vw,6rem);font-weight:300;letter-spacing:.15em;line-height:1.1;color:#2d2924;}
.home-subline {font-family:'Fraunces',Georgia,serif;font-size:1.1rem;letter-spacing:.22em;margin-top:1.2rem;color:#3a342d;}
.home-rule {width:4rem;height:1px;background:#a87547;margin:1.8rem auto;}
.home-hero-description {font-size:.95rem;line-height:1.8;color:#3d3730;margin:0 auto 2rem;}
.home-hero-actions {display:flex;justify-content:center;flex-wrap:wrap;gap:1rem;}
.home-hero-actions a {display:inline-flex;align-items:center;justify-content:center;min-height:48px;padding:.85rem 1.5rem;font-size:.85rem;border:1px solid #2d2924;}
.home-primary {background:#2d2924;color:#fff;}
.home-secondary {color:#2d2924;background:rgba(253,253,252,.65);}
.featured-window {overflow:hidden;}
.featured-toolbar {text-align:right;margin-bottom:1rem;}
.selection-toggle {font-size:.8rem;text-decoration:underline;text-underline-offset:4px;min-height:44px;}
.featured-track {display:flex;width:max-content;animation:selection-scroll 45s linear infinite;}
.featured-group {display:flex;gap:1.5rem;padding-right:1.5rem;flex-shrink:0;}
.featured-product {display:block;width:190px;flex-shrink:0;}
.featured-product img {width:100%;aspect-ratio:3/4;object-fit:contain;padding:.75rem;background:#f7f4ef;border:1px solid #e9e3da;}
.featured-product h3 {font-family:'Fraunces',Georgia,serif;font-size:1rem;line-height:1.5;margin-top:1rem;max-width:24ch;}
.featured-product p {font-size:.8rem;color:#655e55;margin-top:.4rem;}
.featured-window:hover .featured-track,.featured-window:focus-within .featured-track,.featured-window.is-paused .featured-track {animation-play-state:paused;}
@keyframes selection-scroll {to {transform:translateX(-50%);}}
@media(max-width:640px){
 .home-hero{min-height:520px;height:78vh;}
 .home-hero-copy{padding:2rem 1rem;}
 .home-wordmark{font-size:clamp(2.25rem,11vw,3.2rem);letter-spacing:.07em;}
 .home-subline{font-size:.9rem;letter-spacing:.12em;}
 .home-hero-actions{gap:.65rem;}
 .home-hero-actions a{padding:.85rem 1rem;}
 .featured-product{width:150px;}
}
@media(prefers-reduced-motion:reduce){
 .featured-track{animation:none;transform:none;width:auto;}
 .featured-window{overflow-x:auto;}
 .featured-group[aria-hidden="true"],.selection-toggle{display:none;}
}
</style>
'''
p.write_text(s,encoding='utf-8')
# The sign-up CTA must retain its actual sign-up destination.
p=Path('ibernovias/front-end/src/views/TiendaView.vue');s=p.read_text(encoding='utf-8');s=s.replace('AREA PROFESIONAL','ÁREA PROFESIONAL').replace('la informacion comercial','la información comercial');import re
s=re.sub(r'to="/auth"(\s+class="[^"]*"\s*>\s*Solicitar alta)',r'to="/acceso-empresarial"\1',s);p.write_text(s,encoding='utf-8')
