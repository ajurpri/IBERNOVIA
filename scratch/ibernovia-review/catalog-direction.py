from pathlib import Path
import re
p=Path('ibernovias/front-end/src/views/TiendaView.vue');s=p.read_text(encoding='utf-8');a=s.index('    <section class="border-b');b=s.index('    <section id="catalogo"',a)
s=s[:a]+'''    <section class="catalog-heading">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1>Catálogo</h1>
        <p>Complementos de novia, novio, fiesta, comunión y arras.</p>
        <p class="catalog-access-note">Tarifas disponibles con <router-link to="/auth">acceso profesional</router-link>.</p>
      </div>
    </section>

'''+s[b:]
s=s.replace('placeholder="Ej. velo, tocado, novia..."','placeholder="Buscar por nombre o referencia"').replace('Area profesional','Área profesional').replace('No se pudo cargar el catalogo.','No se pudo cargar el catálogo.').replace('otra busqueda','otra búsqueda')
s=s.replace('Tarifas y condiciones reservadas para clientes validados','¿Tienes una tienda?').replace('Si eres empresa del sector, solicita acceso para consultar la información comercial disponible para perfiles profesionales.','Solicita el alta profesional para consultar tarifas y condiciones. Si ya tienes cuenta, entra desde Acceso profesional.')
s=s.replace(':class="selectedFamily === \'Todas\' ?',':aria-pressed="selectedFamily === \'Todas\'"\n              :class="selectedFamily === \'Todas\' ?').replace(':class="selectedFamily === fam ?',':aria-pressed="selectedFamily === fam"\n              :class="selectedFamily === fam ?').replace(':class="selectedCategory === \'Todos\' ?',':aria-pressed="selectedCategory === \'Todos\'"\n              :class="selectedCategory === \'Todos\' ?').replace(':class="selectedCategory === cat ?',':aria-pressed="selectedCategory === cat"\n              :class="selectedCategory === cat ?')
s=s.replace('</style>','''
.catalog-heading {padding:2.5rem 0 2rem;background:#fff;border-bottom:1px solid #e8e4de;}
.catalog-heading h1 {font-family:'Fraunces',Georgia,serif;font-size:2.6rem;font-weight:300;letter-spacing:-.02em;line-height:1.15;margin-bottom:.8rem;}
.catalog-heading p {font-size:.95rem;color:#514b44;line-height:1.7;}
.catalog-heading .catalog-access-note {margin-top:.35rem;font-size:.8rem;color:#6a625a;}
.catalog-access-note a {text-decoration:underline;text-underline-offset:3px;}
#catalogo {padding-top:1.5rem;}
.catalog-controls-shell {gap:1rem;padding-bottom:1.25rem;}
.catalog-controls-shell label,.catalog-controls-shell p,.catalog-controls-shell a,.catalog-controls-shell button,.catalog-controls-shell span {text-transform:none;letter-spacing:normal;}
.catalog-controls-shell input[type=search] {background:#fff;border-radius:3px;max-width:100%;font-size:.9rem;}
.catalog-controls-shell input::placeholder {color:#77716a;opacity:1;}
.catalog-filter-block button {padding:.35rem .2rem;border-bottom:1px solid transparent;}
.catalog-filter-block button[aria-pressed=true] {border-bottom-color:#6d5139;color:#302a24;font-weight:600;}
.catalog-filter-block.pt-4 {padding-top:.75rem;margin-top:0;}
.catalog-controls-shell .mt-2 {margin-top:0;}
.catalog-controls-shell .tracking-\\[0\\.25em\\] {font-size:.85rem;}
.catalog-product-grid {grid-template-columns:repeat(2,minmax(0,1fr));column-gap:1rem;row-gap:2rem;}
.tienda-page .rounded-full {border-radius:3px;}
.tienda-page .mt-14 h2 {font-size:1.8rem;font-weight:300;}
.tienda-page .mt-14 a {font-size:.85rem;letter-spacing:normal;text-transform:none;font-weight:500;}
@media(min-width:768px){.catalog-product-grid{grid-template-columns:repeat(3,minmax(0,1fr));column-gap:1.5rem;}}
@media(min-width:1200px){.catalog-product-grid{grid-template-columns:repeat(4,minmax(0,1fr));}}
@media(max-width:640px){
 .catalog-heading{padding:1.75rem 0 1.5rem;}
 .catalog-heading h1{font-size:2.1rem;}
 .catalog-heading p{font-size:.85rem;}
 .catalog-controls-shell{gap:.75rem;}
 .mobile-filter-toggle{font-size:.85rem;font-weight:500;letter-spacing:normal;text-transform:none;border-radius:3px;}
}
</style>''')
p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/components/ProductCard.vue');s=p.read_text(encoding='utf-8');s=s.replace('rounded-xl border border-black/5 bg-[#faf9f7] shadow-sm relative group-hover:shadow-md transition-shadow duration-500','border border-black/5 bg-[#faf9f7] relative').replace('aspect-[3/4]','aspect-[4/5]').replace('height="800"','height="750"').replace('rounded-full flex items-center justify-center shadow-md hover:scale-105 hover:bg-white transition-all duration-300','rounded-full flex items-center justify-center border border-black/5 hover:bg-white transition-colors duration-200')
s=s.replace('text-xs text-gray-600 uppercase tracking-[0.25em] mb-1.5','text-xs text-gray-600 mb-1.5').replace('font-serif text-base text-luxury-black font-light group-hover:text-luxury-gold transition-colors line-clamp-2 leading-relaxed','product-name text-sm text-luxury-black font-medium leading-relaxed').replace('text-base font-serif font-medium','text-base font-sans font-medium')
a=s.index('        <!-- Restricted Professional Access Prompt -->');b=s.index('      </div>',a)
# Replace the public price placeholder with one restrained link, no decorative dot.
s=s[:a]+'''        <router-link v-else to="/auth" class="professional-link">Consultar tarifas</router-link>
'''+s[b+len('      </div>'):]
# The previous slice removed the inner prompt div; retain the outer margin container.
s=s.replace('</style>','''
.product-name {font-family:'Manrope','Segoe UI',sans-serif;letter-spacing:0;min-height:2.9em;}
.professional-link {font-size:.8rem;color:#65513f;text-decoration:underline;text-underline-offset:3px;}
@media(max-width:640px){.product-name{font-size:.85rem;} .product-visual{border-radius:2px;} }
</style>''')
s=s.replace('    border-radius: 0.75rem;','    border-radius: 2px;');p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8').replace(' :inert="copy === 2 ? true : undefined"','').replace(".featured-product h3 {font-family:'Fraunces',Georgia,serif;font-size:1rem;", ".featured-product h3 {font-family:'Manrope','Segoe UI',sans-serif;font-size:.9rem;");p.write_text(s,encoding='utf-8')
