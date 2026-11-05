from pathlib import Path
import re
root=Path('ibernovias/front-end')
p=root/'src/views/HomeView.vue'
s=p.read_text(encoding='utf-8')
s=re.sub(r'    <section class="max-w-7xl mx-auto w-full.*?</section>', '''    <aside class="retail-note" aria-label="Compra en tienda">
      <div>
        <p>¿No eres profesional? Consúltanos dónde comprar nuestros complementos cerca de ti.</p>
        <p class="retail-note-detail">En Andújar, <a href="/complementos-novia-andujar.html">Paola Novias</a> es nuestra tienda oficial.</p>
      </div>
      <router-link to="/contacto" class="retail-note-link">Consultar tienda <span aria-hidden="true">→</span></router-link>
    </aside>''',s,count=1,flags=re.S)
s=s.replace('<style scoped>','''<style scoped>
.retail-note {
  width: calc(100% - 48px);
  max-width: 1200px;
  margin: 0 auto 48px;
  padding: 20px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px 32px;
  background: #f5f2ec;
  color: #49423b;
  font-size: 14px;
  line-height: 1.7;
}
.retail-note p { margin: 0; }
.retail-note .retail-note-detail { margin-top: 3px; color: #6b6258; font-size: 13px; }
.retail-note a { text-underline-offset: 4px; }
.retail-note-detail a { text-decoration: underline; }
.retail-note-link { white-space: nowrap; text-decoration: underline; }
@media (max-width: 640px) {
  .retail-note { width: calc(100% - 32px); padding: 18px 20px; flex-direction: column; align-items: flex-start; gap: 10px; }
}
''',1)
p.write_text(s,encoding='utf-8')
p=root/'src/views/ContactoView.vue';s=p.read_text(encoding='utf-8')
s=re.sub(r'          <aside.*?</aside>', '''          <p class="text-sm text-gray-600 leading-relaxed">Si compras para ti, escríbenos y te indicamos tu tienda más cercana. En Andújar, nuestra tienda oficial es <a href="/complementos-novia-andujar.html" class="underline underline-offset-4">Paola Novias</a>.</p>''',s,count=1,flags=re.S)
p.write_text(s,encoding='utf-8')
p=root/'src/components/FooterSection.vue';s=p.read_text(encoding='utf-8').replace('En Andújar: Paola Novias','Dónde comprar');p.write_text(s,encoding='utf-8')
p=root/'public/complementos-novia-andujar.html';s=p.read_text(encoding='utf-8')
a=s.index('    <section class="hero">');b=s.index('  </main>',a)
s=s[:a]+'''    <section class="hero">
      <div>
        <p class="eyebrow">Dónde comprar</p>
        <h1>Encuentra tu tienda</h1>
        <p>¿No eres profesional? Consúltanos dónde puedes comprar nuestros complementos cerca de ti.</p>
        <p>En Andújar, <strong>Paola Novias es la tienda oficial de Ibernovia</strong>.</p>
        <a class="button" href="/#/contacto">Consultar mi tienda más cercana</a>
        <p><a href="/#/tienda">Ver el catálogo</a></p>
      </div>
      <img src="/images/catalogo/novia/velos/17815-lentejuelas.webp" width="1200" height="797" alt="Detalle de un velo de novia Ibernovia" fetchpriority="high">
    </section>
''' +s[b:]
s=s.replace('Información sobre los complementos Ibernovia disponibles a través de Paola Novias en Andújar.','Consulta tu tienda más cercana. Paola Novias es la tienda oficial de Ibernovia en Andújar.')
p.write_text(s,encoding='utf-8')
p=root/'index.html';s=p.read_text(encoding='utf-8');s=re.sub(r'    <main style=.*?</main>', '''    <main style="max-width:960px;margin:4rem auto;padding:0 1.5rem;font-family:Georgia,serif;line-height:1.7;color:#2d2924">
      <h1>Ibernovia</h1>
      <p>Complementos de novia y ceremonia para tiendas y profesionales.</p>
      <p>¿No eres profesional? Consúltanos dónde comprar nuestros complementos cerca de ti. En Andújar, Paola Novias es nuestra tienda oficial.</p>
      <p><a href="/#/tienda">Ver catálogo</a> · <a href="/#/contacto">Consultar tienda</a> · <a href="/complementos-novia-andujar.html">Dónde comprar</a></p>
    </main>''',s,count=1,flags=re.S);p.write_text(s,encoding='utf-8')
