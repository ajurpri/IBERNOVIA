import json, re, subprocess, pathlib, concurrent.futures

root = pathlib.Path(__file__).parent
base = 'https://www.ibernovia.es'
def measure(label, url, index=0):
    stem = re.sub(r'[^a-zA-Z0-9-]', '-', label) + '-' + str(index)
    body, headers = root / (stem + '.body'), root / (stem + '.headers')
    p = subprocess.run(['curl.exe', '-L', '--compressed', '-sS', '--max-time', '45', '-D', str(headers), '-o', str(body), '-w', '%{json}', url], capture_output=True, text=True)
    if p.returncode: return {'label':label, 'error':p.stderr}
    stats = json.loads(p.stdout)
    h = headers.read_text().split('\n\n')[-2]
    result = {'label':label, 'url':url, 'status':stats['http_code'], 'ttfb_s':stats['time_starttransfer'], 'total_s':stats['time_total'], 'wire_bytes':stats['size_download'], 'decoded_bytes':body.stat().st_size}
    result['headers'] = {k.lower():v.strip() for k,v in (line.split(':',1) for line in h.splitlines() if ':' in line) if k.lower() in ['cache-control','content-encoding','expires','vary','content-type']}
    return result

results=[]
for i in range(3):
    for label,url in [('home',base+'/'),('api','https://ibernovia.onrender.com/api/productos')]:
        results.append(measure(label,url,i))
html=(root/'home-0.body').read_text(encoding='utf-8')
assets=list(dict.fromkeys(re.findall(r'(?:src|href)="(/assets/[^\"]+)"',html)))
assets+=['/images/catalogo/novia/velos/17815-lentejuelas.webp']
with concurrent.futures.ThreadPoolExecutor(max_workers=3) as pool:
    results+=list(pool.map(lambda path:measure(path,base+path),assets))
products=json.loads((root/'api-0.body').read_text(encoding='utf-8'))
print('Products:',len(products))
samples=list(dict.fromkeys(p.get('imagen') for p in products if p.get('imagen')))[:12]
with concurrent.futures.ThreadPoolExecutor(max_workers=3) as pool:
    results+=list(pool.map(lambda path:measure('product-'+path, path if path.startswith('http') else base+'/'+path.lstrip('/')),samples))
(root/'performance-results.json').write_text(json.dumps(results,indent=2),encoding='utf-8')
print(json.dumps(results,indent=2))
