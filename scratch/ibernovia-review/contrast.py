from pathlib import Path
for filename,first,replacement in [('views/ProductoView.vue','class="min-h-screen bg-white','class="public-product-page min-h-screen bg-white'),('views/AuthView.vue','class="min-h-screen bg-[#fdfdfc]','class="auth-page min-h-screen bg-[#fdfdfc]'),('views/ContactoView.vue','class="min-h-screen','class="contact-page min-h-screen')]:
 p=Path('ibernovias/front-end/src')/filename;s=p.read_text(encoding='utf-8').replace(first,replacement,1);p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/assets/main.css');s=p.read_text(encoding='utf-8').replace(':where(.home-page,.tienda-page,main)',':where(.home-page,.tienda-page,.public-product-page,.auth-page,.contact-page,.b2b-page)');s+='''
:where(.auth-page,.contact-page) label {font-size:.8rem;letter-spacing:.08em;}
.business-strip a {color:#dfbd91;}
''';p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8').replace('rgb(0 0 0 / 0.5));','rgb(0 0 0 / 0.7));');p.write_text(s,encoding='utf-8')
