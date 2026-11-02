from pathlib import Path
import re
p=Path('ibernovias/front-end/src/components/ProductCard.vue');s=p.read_text(encoding='utf-8');s=s.replace('      :aria-busy="!imageLoaded && !imageError"\n','').replace('        @load="imageLoaded = true"\n','');s=re.sub(r'      <div\n        v-if="!imageLoaded && !imageError".*?</div>\n','',s,flags=re.S).replace('const imageLoaded = ref(false)\n','').replace('  imageLoaded.value = false\n','').replace('.image-placeholder {background:#f7f6f3;pointer-events:none;}\n','');p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8');a=s.index('<style scoped>');s=s[:a]+'''<style scoped>
.home-hero {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 550px;
  height: 85vh;
  overflow: hidden;
  background: #fcfaf6;
}
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

.home-families {padding:3.2rem 0;background:#fdfdfc;}
.home-families-head {display:flex;justify-content:space-between;align-items:end;gap:1.5rem;margin-bottom:1.8rem;}
.home-families-kicker {font-size:.75rem;letter-spacing:.12em;text-transform:uppercase;color:#795333;}
.home-families-title {margin-top:.4rem;font-family:'Fraunces',Georgia,serif;font-size:clamp(1.8rem,3vw,2.5rem);font-weight:300;line-height:1.2;}
.home-families-link {font-size:.85rem;text-decoration:underline;text-underline-offset:4px;}
.home-family-grid {display:grid;grid-template-columns:repeat(5,minmax(0,1fr));gap:1.25rem;}
.home-family-card {display:block;color:#302a24;}
.home-family-card img {display:block;width:100%;height:auto;aspect-ratio:3/2;object-fit:contain;}
.home-family-copy {display:block;margin-top:.85rem;}
.home-family-copy strong {font-family:'Manrope','Segoe UI',sans-serif;font-size:.95rem;font-weight:500;}

.home-featured {border:0;}
.featured-window {overflow:hidden;}
.featured-track {display:flex;width:max-content;animation:selection-scroll 45s linear infinite;}
.featured-group {display:flex;gap:2rem;padding-right:2rem;flex-shrink:0;}
.featured-product {display:block;width:300px;flex-shrink:0;}
.featured-product img,.featured-image-space {display:block;width:100%;height:220px;object-fit:contain;}
.featured-product h3 {font-family:'Manrope','Segoe UI',sans-serif;font-size:.9rem;font-weight:500;line-height:1.5;margin-top:.85rem;}
.featured-product p {font-size:.8rem;color:#655e55;margin-top:.35rem;}
.featured-window:hover .featured-track,.featured-window:focus-within .featured-track {animation-play-state:paused;}
@keyframes selection-scroll {to {transform:translateX(-50%);}}

@media(max-width:640px){
  .home-hero{min-height:520px;height:78vh;}
  .home-hero-copy{padding:2rem 1rem;}
  .home-wordmark{font-size:clamp(2.25rem,11vw,3.2rem);letter-spacing:.07em;}
  .home-subline{font-size:.9rem;letter-spacing:.12em;}
  .home-hero-actions{gap:.65rem;}
  .home-hero-actions a{padding:.85rem 1rem;}
  .home-families,.home-featured,.home-promos{padding-top:2.5rem;padding-bottom:2.5rem;}
  .home-families-head{align-items:start;gap:1rem;}
  .home-family-grid{grid-auto-flow:column;grid-auto-columns:minmax(180px,55vw);grid-template-columns:none;gap:1rem;overflow-x:auto;padding-bottom:.75rem;scroll-snap-type:x proximity;}
  .home-family-card{scroll-snap-align:start;}
  .home-family-copy strong{font-size:.9rem;}
  .featured-product{width:240px;}
  .featured-product img,.featured-image-space{height:180px;}
  .home-promos .mb-16{margin-bottom:2.25rem;}
}
@media(prefers-reduced-motion:reduce){
  .featured-track{animation:none;transform:none;width:auto;}
  .featured-window{overflow-x:auto;}
  .featured-group[aria-hidden="true"]{display:none;}
}
</style>
''';p.write_text(s,encoding='utf-8')
