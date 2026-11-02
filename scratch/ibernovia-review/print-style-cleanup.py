from pathlib import Path
p=Path('ibernovias/front-end/src/views/TiendaView.vue');s=p.read_text(encoding='utf-8');a=s.find('\n.catalog-heading');b=s.find('</script>')
if 0 <= a < b:
 end=s.index('</style>',a);s=s[:a]+'\n      '+s[end:]
s='\n'.join(line.rstrip() for line in s.splitlines())+'\n';p.write_text(s,encoding='utf-8')
