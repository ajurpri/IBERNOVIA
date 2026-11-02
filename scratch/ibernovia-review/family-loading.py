from pathlib import Path
p=Path('ibernovias/front-end/src/views/HomeView.vue');s=p.read_text(encoding='utf-8').replace('<section class="home-families"','<section ref="familiesSection" class="home-families"').replace('<img :src="family.image" :alt="family.alt" width="400" height="533" loading="lazy" fetchpriority="low" decoding="async">','<img v-if="familiesReady" :src="family.image" :alt="family.alt" width="600" height="400" loading="eager" fetchpriority="low" decoding="async">\n            <span v-else class="home-family-image-space" aria-hidden="true"></span>')
s=s.replace('const featuredSection = ref(null)','const familiesSection = ref(null)\nconst familiesReady = ref(false)\nconst featuredSection = ref(null)')
a=s.index('    selectionObserver = new IntersectionObserver(');b=s.index('\n  try {',a)
s=s[:a]+'''    selectionObserver = new IntersectionObserver((entries) => {
      for (const entry of entries) {
        if (!entry.isIntersecting) continue
        if (entry.target === featuredSection.value) selectionReady.value = true
        if (entry.target === familiesSection.value) familiesReady.value = true
        selectionObserver.unobserve(entry.target)
      }
    }, { rootMargin: '400px' })
    if (featuredSection.value) selectionObserver.observe(featuredSection.value)
    if (familiesSection.value) selectionObserver.observe(familiesSection.value)
  } else {
    selectionReady.value = true
    familiesReady.value = true
  }
'''+s[b:]
s=s.replace('.home-family-card img {display:block;', '.home-family-card img,.home-family-image-space {display:block;');p.write_text(s,encoding='utf-8')
