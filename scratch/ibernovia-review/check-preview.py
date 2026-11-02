from pathlib import Path
from urllib.request import urlopen, Request
from urllib.error import HTTPError
import json, time, gzip

root=Path(__file__).resolve().parent
project=root.parents[1]/'ibernovias/front-end'
result={}
for label,port,build in [('before',4173,root/'before'),('after',4174,project/'dist')]:
    assets=build/'assets'
    sizes={}
    for pattern in ['index-*.js','index-*.css','HomeView-*.js','HomeView-*.css','productPresentation-*.js']:
        files=list(assets.glob(pattern))
        sizes[pattern]={'bytes':sum(p.stat().st_size for p in files),'gzipBytes':sum(len(gzip.compress(p.read_bytes())) for p in files)}
    samples=[]
    for _ in range(3):
        start=time.perf_counter()
        with urlopen(f'http://127.0.0.1:{port}/',timeout=15) as response:
            body=response.read()
            assert response.status==200
        samples.append(round((time.perf_counter()-start)*1000,2))
    result[label]={'assets':sizes,'htmlGetMs':samples}
for path in ['/','/tienda','/producto/639','/auth','/acceso-empresarial','/contacto']:
    with urlopen('http://127.0.0.1:4174'+path) as response:
        assert response.status==200
        assert response.headers['X-Robots-Tag']=='noindex, nofollow'
try:
    urlopen(Request('http://127.0.0.1:4174/api/contacto',data=b'{}',method='POST'))
    raise AssertionError('Preview unexpectedly accepted submission')
except HTTPError as error:
    assert error.code==503
result['previewRoutes']='6 routes return 200; local POST rejected with 503; noindex enabled'
result['heroVariants']={p.name:p.stat().st_size for p in (project/'public/images/portada').glob('*.webp')}
(root/'comparison.json').write_text(json.dumps(result,indent=2),encoding='utf-8')
print(json.dumps(result,indent=2))
