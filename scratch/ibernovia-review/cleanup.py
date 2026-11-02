from pathlib import Path
import re,subprocess
p=Path('ibernovias/front-end/src/components/ProductCard.vue');s=p.read_text(encoding='utf-8');s=re.sub(r'  button\[:aria-label=.*?\] \{','  button[aria-pressed] {',s);p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/views/ContactoView.vue');s=p.read_text(encoding='utf-8');s=re.sub(r'<label class="([^"]*)">(.*?)</label>\s*<(input|textarea)\s+v-model="([^"]+)"',lambda m:'<label for="contact-'+m[4].replace('.','-')+'" class="'+m[1]+'">'+m[2]+'</label>\n            <'+m[3]+' id="contact-'+m[4].replace('.','-')+'" v-model="'+m[4]+'"',s,flags=re.S).replace('confirmacion por correo','confirmación por correo');p.write_text(s,encoding='utf-8')
# Preserve meaningful line breaks, trim whitespace only in edited source files.
for name in subprocess.check_output(['git','diff','--name-only'],text=True).splitlines():
 p=Path(name)
 if p.suffix in ['.vue','.css','.js','.html']:
  s=p.read_text(encoding='utf-8');p.write_text('\n'.join(line.rstrip() for line in s.splitlines())+'\n',encoding='utf-8')
