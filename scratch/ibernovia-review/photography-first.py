from pathlib import Path
import re
p=Path('ibernovias/front-end/src/components/ProductCard.vue');s=p.read_text(encoding='utf-8');s=s.replace('class="product-visual block overflow-hidden border border-black/5 bg-[#faf9f7] relative"','class="product-visual block relative"');s=s.replace('aspect-[4/5]','aspect-[1/1]').replace('height="750"','height="600"');s=s.replace('sizes="(max-width: 640px) calc(100vw - 2rem), (max-width: 1024px) 50vw, 25vw"','sizes="(max-width: 767px) 50vw, (max-width: 1199px) 33vw, 25vw"')
s=s.replace('        :class="[\n          \'w-full aspect-[1/1] object-contain p-4 transition-opacity duration-200\',\n          imageLoaded ? \'opacity-100\' : \'opacity-0\'\n        ]"','        class="product-photo w-full aspect-[1/1] object-contain"')
# Keep the photograph unobstructed: put favorites with the details.
a=s.index('      <!-- Favorite Button -->');b=s.index('      <!-- Badge de Oferta -->',a);favorite=s[a:b];s=s[:a]+s[b:];favorite=favorite.replace('class="absolute top-4 right-4 w-11 h-11 bg-white/95 rounded-full flex items-center justify-center border border-black/5 hover:bg-white transition-colors duration-200 focus:outline-none"','class="favorite-control w-11 h-11 flex items-center justify-center shrink-0"')
s=s.replace('    <div class="pt-4 pb-2 flex-1 flex flex-col justify-between">','    <div class="product-details pt-3 pb-2 flex-1 flex flex-col justify-between">').replace('      <div>\n        <p class="text-xs text-gray-600 mb-1.5">{{ producto.categoria || \'Sin categoría\' }}</p>','      <div class="flex items-start justify-between gap-2">\n        <div>')
s=s.replace('        <p class="mt-1 text-sm text-gray-600">Referencia: {{ productReference(producto) }}</p>\n      </div>','        <p class="mt-1 text-sm text-gray-600">Referencia: {{ productReference(producto) }}</p>\n        </div>\n'+favorite+'      </div>')
a=s.index('<style scoped>');s=s[:a]+'''<style scoped>
.product-visual {aspect-ratio:1;}
.product-photo {display:block;}
.image-placeholder {background:#f7f6f3;pointer-events:none;}
.product-name {font-family:'Manrope','Segoe UI',sans-serif;letter-spacing:0;font-size:.9rem;font-weight:500;line-height:1.55;}
.professional-link {font-size:.8rem;color:#65513f;text-decoration:underline;text-underline-offset:3px;}
.favorite-control {background:transparent;border:0;color:#655e55;margin-top:-.5rem;margin-right:-.65rem;}
.favorite-control:hover {color:#302a24;}
@media(max-width:640px){.product-name{font-size:.85rem;} .product-details p{font-size:.75rem;} }
</style>
''';p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8');s=s.replace('            <span class="home-family-shade"></span>\n','').replace('              <small>{{ family.caption }}</small>\n','');s=s.replace('<!-- Restore the original moving selection with an explicit pause control. -->','<!-- Moving product selection; photography stays unobstructed. -->').replace('<section class="home-featured','<section ref="featuredSection" class="home-featured')
s=s.replace(' :class="{ \'is-paused\': selectionPaused }"','');s=re.sub(r'        <div class="featured-toolbar.*?</div>\n','',s,flags=re.S)
s=s.replace('<img :src="getProductImage(prod)" :alt="productName(prod)" width="400" height="533" loading="lazy" fetchpriority="low" decoding="async">','<img v-if="selectionReady" :src="getProductImage(prod)" :alt="productName(prod)" width="600" height="400" loading="eager" fetchpriority="low" decoding="async">\n              <span v-else class="featured-image-space" aria-hidden="true"></span>')
s=s.replace("import { ref, onMounted, inject }", "import { ref, onMounted, onBeforeUnmount, inject }").replace('const selectionPaused = ref(false)','const featuredSection = ref(null)\nconst selectionReady = ref(false)\nlet selectionObserver')
s=s.replace('onMounted(async () => {','''onMounted(async () => {
  if ('IntersectionObserver' in window) {
    selectionObserver = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting) {
        selectionReady.value = true
        selectionObserver.disconnect()
      }
    }, { rootMargin: '400px' })
    if (featuredSection.value) selectionObserver.observe(featuredSection.value)
  } else {
    selectionReady.value = true
  }
''')
s=s.replace('</script>','onBeforeUnmount(() => selectionObserver?.disconnect())\n</script>')
s=re.sub(r'\.featured-toolbar \{[^}]*\}\n','',s);s=re.sub(r'\.selection-toggle \{[^}]*\}\n','',s)
s=s.replace('.featured-product {display:block;width:190px;flex-shrink:0;}','.featured-product {display:block;width:300px;flex-shrink:0;}')
s=s.replace('.featured-product img {width:100%;aspect-ratio:3/4;object-fit:contain;padding:.75rem;background:#f7f4ef;border:1px solid #e9e3da;}','.featured-product img,.featured-image-space {display:block;width:100%;height:220px;object-fit:contain;}')
s=s.replace(',.featured-window.is-paused .featured-track','').replace('.featured-product{width:150px;}','.featured-product{width:240px;}\n .featured-product img,.featured-image-space{height:180px;}').replace('.featured-group[aria-hidden="true"],.selection-toggle','.featured-group[aria-hidden="true"]')
s=s.replace('</style>','''
/* Family photographs, without frames, overlays or copy over the product. */
.home-families {background:#fdfdfc;}
.home-family-card {min-height:0;border:0;border-radius:0;background:transparent;color:#302a24;overflow:visible;}
.home-family-card img {width:100%;height:auto;min-height:0;aspect-ratio:3/2;object-fit:contain;transition:none;}
.home-family-card:hover img {transform:none;}
.home-family-copy {position:static;display:block;margin-top:.85rem;}
.home-family-copy strong {font-family:'Manrope','Segoe UI',sans-serif;font-size:.95rem;font-weight:500;}
.home-featured {border:0;}
@media(max-width:640px){.home-family-card,.home-family-card img{min-height:0;} .home-family-copy strong{font-size:.9rem;}}
</style>''');p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/TiendaView.vue');s=p.read_text(encoding='utf-8').replace('tienda-page min-h-screen bg-[#fcfaf6]','tienda-page min-h-screen bg-[#fdfdfc]');p.write_text(s,encoding='utf-8')
