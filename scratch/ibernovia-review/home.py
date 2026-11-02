from pathlib import Path
import re
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8')
a=s.index('    <!-- Hero Section');b=s.index('    <section class="home-families"',a)
s=s[:a]+'''    <section class="home-hero" aria-label="Portada">
      <div class="home-hero-copy">
        <p class="home-eyebrow">Complementos de ceremonia</p>
        <h1 class="home-wordmark">IBERNOVIA</h1>
        <p class="home-hero-description">Complementos de novia, novio, fiesta, comunión y arras para tiendas y profesionales.</p>
        <div class="home-hero-actions">
          <router-link to="/tienda" class="home-primary">Explorar catálogo</router-link>
          <router-link to="/auth" class="home-secondary">Acceso profesional</router-link>
        </div>
      </div>
      <picture class="home-hero-photo">
        <source media="(max-width: 640px)" srcset="/images/portada/velo-640.webp 640w, /images/portada/velo-960.webp 960w" sizes="100vw">
        <img src="/images/catalogo/novia/velos/17815-lentejuelas.webp" alt="Detalle del velo de novia del catálogo Ibernovia" width="1200" height="797" loading="eager" fetchpriority="high" decoding="async">
      </picture>
    </section>

'''+s[b:]
a=s.index('      <!-- Marquee container -->');b=s.index('      <div v-else class=',a)
s=s[:a]+'''      <p v-if="loading" class="text-center py-8 text-sm" role="status">Cargando selección…</p>
      <div v-else-if="products.length > 0" class="featured-products max-w-7xl mx-auto px-4 sm:px-6">
        <router-link v-for="prod in products" :key="prod.id" :to="`/producto/${prod.id}`" class="featured-product">
          <img :src="getProductImage(prod)" :alt="productName(prod)" width="400" height="533" loading="lazy" fetchpriority="low" decoding="async">
          <h3>{{ productName(prod) }}</h3>
          <p>Referencia: {{ productReference(prod) }}</p>
        </router-link>
      </div>

'''+s[b:]
s=s.replace('ref, onMounted, onBeforeUnmount, inject','ref, onMounted, inject').replace("import { useRouter } from 'vue-router'\n",'').replace('const router = useRouter()\n','')
s=s.replace("import { apiClient, fetchCachedProducts, getImageUrl } from '../lib/api'","import { apiClient, fetchCachedProducts, getImageUrl } from '../lib/api'\nimport { productName, productReference } from '../lib/productPresentation'")
a=s.index('// Slideshow state');b=s.index('const getProductImage',a);s=s[:a]+s[b:]
s=re.sub(r'  // Slideshow rotation every 5 seconds.*?\n  try \{','  try {',s,flags=re.S)
s=re.sub(r'\nonBeforeUnmount\(\(\) => \{.*?\n\}\)\n','\n',s,flags=re.S)
s=s.replace('.slice(0, 15)','.slice(0, 6)').replace('https://placehold.co/400x400/e5e5e5/3a3a3a?text=Ibernovia','/logo/logo.jpg')
s=s.replace('Explorar catalogo','Explorar catálogo').replace("name: 'Comunion'","name: 'Comunión'").replace('para celebracion','para celebración').replace('Diadema de comunion','Diadema de comunión').replace('Cojin de arras','Cojín de arras').replace('Avance de catálogo • Diseños exclusivos para profesionales','Complementos para tiendas y profesionales')
s=s.replace('class="home-family-card"','class="home-family-card"').replace('loading="lazy" fetchpriority="low" decoding="async">','width="400" height="533" loading="lazy" fetchpriority="low" decoding="async">')
# Remove unused animation rules, retain family and promo layouts.
a=s.index('/* Transiciones suaves');b=s.index('.home-families {',a);s=s[:a]+s[b:]
s=re.sub(r'@keyframes scrollMarquee \{.*?\n\}\n','',s,flags=re.S)
a=s.index('  .home-hero-section {');b=s.index('  .home-featured,',a);s=s[:a]+s[b:]
s=s.replace('font-size: 9.5px;','font-size: 12px;').replace('font-size: 10px;','font-size: 12px;').replace('font-size: 11px;','font-size: 13px;')
s=s.replace('color: rgb(255 255 255 / 0.78);','color: #fff;')
s=s.replace('</style>','''
.home-hero {display:grid;grid-template-columns:1fr 1fr;min-height:660px;background:#fcfaf6;align-items:center;}
.home-hero-copy {padding:5rem clamp(1.5rem,5vw,6rem);}
.home-eyebrow {font-size:.8rem;letter-spacing:.16em;text-transform:uppercase;color:#795333;margin-bottom:1.8rem;}
.home-wordmark {font-family:'Fraunces',Georgia,serif;font-size:clamp(2.7rem,5.2vw,5.8rem);font-weight:300;letter-spacing:.045em;line-height:1.1;color:#2d2924;}
.home-hero-description {font-size:1rem;line-height:1.9;color:#575047;max-width:36ch;margin:2rem 0;}
.home-hero-actions {display:flex;flex-wrap:wrap;gap:1rem;}
.home-hero-actions a {display:inline-flex;align-items:center;justify-content:center;min-height:48px;padding:.85rem 1.25rem;font-size:.85rem;border:1px solid #2d2924;}
.home-primary {background:#2d2924;color:#fff;}
.home-secondary {color:#2d2924;}
.home-hero-photo {display:block;height:100%;min-height:660px;overflow:hidden;}
.home-hero-photo img {width:100%;height:100%;object-fit:cover;object-position:52% center;}
.featured-products {display:grid;grid-template-columns:repeat(6,minmax(0,1fr));gap:1.5rem;}
.featured-product img {width:100%;aspect-ratio:3/4;object-fit:contain;padding:.75rem;background:#f7f4ef;border:1px solid #e9e3da;}
.featured-product h3 {font-family:'Fraunces',Georgia,serif;font-size:1rem;line-height:1.5;margin-top:1rem;}
.featured-product p {font-size:.8rem;color:#655e55;margin-top:.4rem;}
@media(max-width:900px){.featured-products{grid-template-columns:repeat(3,minmax(0,1fr));}}
@media(max-width:640px){
 .home-hero{grid-template-columns:1fr;min-height:0;}
 .home-hero-copy{padding:3rem 1.25rem 2.5rem;}
 .home-wordmark{font-size:clamp(2.4rem,11vw,3.6rem);}
 .home-hero-description{margin:1.5rem 0;}
 .home-hero-actions{gap:.65rem;}
 .home-hero-actions a{flex:1 1 auto;padding:.85rem .8rem;}
 .home-hero-photo{height:auto;min-height:0;aspect-ratio:1200/797;}
 .featured-products{grid-template-columns:repeat(2,minmax(0,1fr));gap:1.25rem;}
}
</style>''')
p.write_text(s,encoding='utf-8')
# Optimization only: resize the original photograph, no synthesis or retouching.
from PIL import Image
folder=Path('ibernovias/front-end/public/images/portada');folder.mkdir(exist_ok=True)
im=Image.open('ibernovias/front-end/public/images/catalogo/novia/velos/17815-lentejuelas.webp')
for w in (640,960):
 im.resize((w,round(im.height*w/im.width)),Image.Resampling.LANCZOS).save(folder/f'velo-{w}.webp',quality=88,method=6)
p=Path('ibernovias/front-end/index.html');s=p.read_text(encoding='utf-8');s=re.sub(r'  <link rel="preload" as="image"[^>]+>\n','',s);s=s.replace('Ibernovia Atelier | Complementos de Novia de Alta Costura','Ibernovia | Complementos de ceremonia para profesionales').replace('Descubre Ibernovia Atelier en Andújar (Jaén): tocados, velos, mantillas y complementos de novia y novio de lujo con diseños exclusivos y asesoramiento personalizado de alta costura.','Complementos de ceremonia para tiendas y profesionales. Explora el catálogo público de novia, novio, fiesta, comunión y arras.').replace('Tocados, velos, mantillas y complementos nupciales de lujo en Andújar. Visítanos en Calle Forjadores, 1.','Complementos de ceremonia para tiendas y profesionales en Andújar. Catálogo público y tarifas tras validación profesional.');p.write_text(s,encoding='utf-8')
