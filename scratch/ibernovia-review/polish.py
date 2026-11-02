from pathlib import Path
p=Path('ibernovias/front-end/src/components/WhatsAppButton.vue');s=p.read_text(encoding='utf-8');s=s.replace('queria','quería');start=s.index('<style scoped>');s=s[:start]+'''<style scoped>
.whatsapp-float {position:fixed;left:1rem;bottom:1rem;z-index:60;display:inline-flex;align-items:center;gap:.5rem;padding:.6rem 1rem;min-height:48px;border-radius:5px;border:1px solid #c7d7cb;background:#fff;color:#21643c;text-decoration:none;box-shadow:0 2px 8px #0000000d;}
.whatsapp-float:hover {background:#eff6f1;}
.whatsapp-icon {width:26px;height:26px;display:inline-flex;}
.whatsapp-icon svg {width:26px;height:26px;}
.whatsapp-copy {font-size:14px;font-weight:600;}
/* In normal flow on mobile, so it cannot cover products or cookie controls. */
@media(max-width:640px){.whatsapp-float{position:relative;left:auto;bottom:auto;align-self:center;margin:1rem;}}
</style>
''';p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/App.vue');s=p.read_text(encoding='utf-8').replace('    <FooterSection />','    <WhatsAppButton />\n    <FooterSection />').replace('    <Toast ref="toastRef" />\n    <WhatsAppButton />','    <Toast ref="toastRef" />');p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/assets/main.css');s=p.read_text(encoding='utf-8');s+='''
/* Restrained public presentation; preserve the established font families. */
:where(a,button,input,textarea,select,summary):focus-visible {outline:2px solid #76502f !important;outline-offset:3px;}
:where(.home-page,.tienda-page,main) .text-gray-400 {color:#68635e;}
:where(.home-page,.tienda-page,main) .text-luxury-gold {color:#815733;}
.tienda-page [class*="text-[10px]"], .tienda-page [class*="text-[11px]"] {font-size:.8rem;letter-spacing:.08em;}
.tienda-page button {min-height:44px;}
.product-enquiry {display:block;text-align:center;padding:.85rem 1rem;border:1px solid #b8cbbd;color:#21643c;background:#f4f8f4;font-size:.9rem;}
body:has(.cookie-banner) .whatsapp-float {visibility:hidden;}
@media(max-width:640px){body:has(.cookie-banner) .whatsapp-float{visibility:visible;}}
.animate-fade-in-up {animation:none !important;opacity:1;transform:none;}
.animate-subtle-zoom {animation:none !important;}
.footer-premium .contact-label,.footer-premium .footer-bottom {color:#c8c1b8;}
''';p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/router/index.js');s=p.read_text(encoding='utf-8').replace('IBERNOVIA Atelier: diseño nupcial y complementos profesionales.','Ibernovia: complementos de ceremonia para tiendas y profesionales.').replace('IBERNOVIA: complementos nupciales de alta costura en Andújar, Jaén. Tienda con asesoramiento personalizado y diseños exclusivos.','Ibernovia: complementos de ceremonia para tiendas y profesionales en Andújar, Jaén. Catálogo público y tarifas tras validación profesional.').replace("behavior: 'smooth'","behavior: window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 'auto' : 'smooth'");p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/components/BackToTop.vue');s=p.read_text(encoding='utf-8').replace("behavior: 'smooth'","behavior: window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 'auto' : 'smooth'");p.write_text(s,encoding='utf-8')
