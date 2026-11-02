from pathlib import Path
p=Path('ibernovias/front-end/src/components/ProductCard.vue');s=p.read_text(encoding='utf-8');a=s.index('    <!-- Details below image -->');b=s.index('\n</template>',a)
s=s[:a]+'''    <div class="product-details pt-3 pb-2 flex-1 flex flex-col">
      <h3 class="product-name text-luxury-black">
        {{ productName(producto) !== productReference(producto) ? productName(producto) : producto.categoria || 'Producto' }}
      </h3>
      <div class="product-reference-row">
        <p>Referencia: {{ productReference(producto) }}</p>
        <button
          @click.stop.prevent="toggleFavorite"
          :aria-pressed="isFavorite ? 'true' : 'false'"
          class="favorite-control"
          :aria-label="`${isFavorite ? 'Quitar de favoritos' : 'Marcar favorito'}: ${productReference(producto)}`"
        >
          <svg :class="isFavorite ? 'text-red-500 fill-red-500' : ''" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" aria-hidden="true">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
          </svg>
        </button>
      </div>
      <div v-if="authStore.canSeePrices" class="mt-2 text-base font-sans font-medium flex items-center flex-wrap gap-x-2 gap-y-0.5">
        <span v-if="producto.enOferta" class="line-through text-gray-400 text-xs sm:text-sm font-normal">{{ producto.precio || '0' }} €</span>
        <span :class="producto.enOferta ? 'text-red-600 font-bold' : 'text-luxury-black'">{{ producto.enOferta ? producto.precioOferta : producto.precio }} €</span>
      </div>
    </div>
  </div>'''+s[b:]
s=s.replace(".professional-link {font-size:.8rem;color:#65513f;text-decoration:underline;text-underline-offset:3px;}\n",'')
s=s.replace('.favorite-control {background:transparent;border:0;color:#655e55;margin-top:-.5rem;margin-right:-.65rem;}','.product-reference-row {display:flex;align-items:center;justify-content:space-between;gap:.25rem;}\n.product-reference-row p {font-size:.8rem;color:#655e55;}\n.favorite-control {display:flex;align-items:center;justify-content:center;width:44px;height:44px;flex-shrink:0;background:transparent;border:0;color:#655e55;margin-right:-.65rem;}')
p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8');s=s.replace('            <p class="home-families-kicker">Explorar catálogo</p>\n','').replace('Colecciones principales','Colecciones').replace('Nuestra Selección','Nuestra selección').replace('          <p class="text-xs uppercase tracking-widest text-gray-500 mt-1">Complementos para tiendas y profesionales</p>\n','').replace('<p>Referencia: {{ productReference(prod) }}</p>','<p v-if="productName(prod) !== productReference(prod)">Referencia: {{ productReference(prod) }}</p>')
s=s.replace('.featured-product {display:block;width:300px;flex-shrink:0;}','.featured-product {display:block;width:340px;flex-shrink:0;}').replace('height:220px;object-fit:contain','height:240px;object-fit:contain').replace('.featured-product{width:240px;}','.featured-product{width:300px;}').replace('height:180px;}','height:210px;}')
s=s.replace('.home-family-card img,.home-family-image-space {display:block;width:100%;height:auto;aspect-ratio:3/2;object-fit:contain;}','.home-family-card img,.home-family-image-space {display:block;width:100%;height:auto;aspect-ratio:1;object-fit:contain;}')
s=s.replace('.home-families-link {font-size:', '.home-families-link {white-space:nowrap;font-size:')
s=s.replace('</style>','''
.home-family-grid {scrollbar-width:thin;scrollbar-color:#bfb7ad transparent;}
.home-family-grid::-webkit-scrollbar {height:3px;}
.home-family-grid::-webkit-scrollbar-track {background:transparent;}
.home-family-grid::-webkit-scrollbar-thumb {background:#bfb7ad;}
</style>''');p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/TiendaView.vue');s=p.read_text(encoding='utf-8');where=s.rfind('</style>');s=s[:where]+'''
.tienda-page .catalog-controls-shell :is(label,p,a,button,span) {letter-spacing:normal;text-transform:none;}
.catalog-product-grid {margin-top:-.5rem;}
'''+s[where:];p.write_text(s,encoding='utf-8')
