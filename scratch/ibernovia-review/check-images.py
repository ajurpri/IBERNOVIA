from concurrent.futures import ThreadPoolExecutor
from urllib.request import Request, urlopen
from pathlib import Path
import json

root=Path(__file__).resolve().parent
public=root.parents[1]/'ibernovias/front-end/public'
products=json.loads((root/'public-products.json').read_text(encoding='utf-8'))
paths=sorted({p[key] for p in products for key in ['imagen','imagen2'] if p.get(key)})
def check(path):
    if (public/path.lstrip('/')).is_file(): return {'path':path,'status':'local'}
    url=path if path.startswith('https://') else 'https://ibernovia.es'+path
    try:
        with urlopen(Request(url,method='HEAD'),timeout=15) as response:
            return {'path':path,'status':response.status,'type':response.headers.get('Content-Type')}
    except Exception as error: return {'path':path,'error':str(error)}
with ThreadPoolExecutor(max_workers=6) as pool:
    rows=list(pool.map(check,paths))
(root/'image-audit.json').write_text(json.dumps(rows,indent=2),encoding='utf-8')
bad=[r for r in rows if 'error' in r or (r['status']!='local' and not r.get('type','').startswith('image/'))]
print(json.dumps({'checked':len(rows),'local':sum(r.get('status')=='local' for r in rows),'failed':len(bad),'examples':bad[:5]},indent=2))
