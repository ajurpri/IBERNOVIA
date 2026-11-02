"""Local, read-only preview. Public catalog snapshot only; no production writes."""
from http.server import ThreadingHTTPServer, BaseHTTPRequestHandler
from pathlib import Path
from urllib.parse import urlsplit, unquote, parse_qs
import json, mimetypes, sys

mimetypes.add_type("image/webp", ".webp")
mimetypes.add_type("text/javascript", ".js")

ROOT = Path(__file__).resolve().parent
PROJECT = ROOT.parents[1] / 'ibernovias/front-end'
BEFORE = '--before' in sys.argv
BUILD = ROOT / 'before' if BEFORE else PROJECT / 'dist'
PRODUCTS = json.loads((ROOT / 'public-products.json').read_text(encoding='utf-8'))
assert all(p.get('precio') is None and p.get('precioOferta') is None for p in PRODUCTS)
INSTRUMENT = '''<script>
localStorage.removeItem('ibernovia_products_public_v2');
const qa={errors:[],lcp:0,cls:0};
addEventListener('error',e=>qa.errors.push(e.message||'resource error'),true);
new PerformanceObserver(l=>{for(const e of l.getEntries())qa.lcp=e.startTime}).observe({type:'largest-contentful-paint',buffered:true});
new PerformanceObserver(l=>{for(const e of l.getEntries())if(!e.hadRecentInput)qa.cls+=e.value}).observe({type:'layout-shift',buffered:true});
setTimeout(()=>{qa.path=location.pathname;qa.viewport=[innerWidth,innerHeight];qa.resources=performance.getEntriesByType('resource').map(e=>({url:e.name.split('?')[0],bytes:e.transferSize}));qa.brokenImages=[...document.images].filter(i=>i.complete&&!i.naturalWidth).map(i=>i.getAttribute('src'));qa.overflow=document.documentElement.scrollWidth>innerWidth;fetch('/__qa/report',{method:'POST',body:JSON.stringify(qa)})},8000);
</script>'''

class Handler(BaseHTTPRequestHandler):
    def respond(self, data, content='application/json', status=200):
        self.send_response(status)
        self.send_header('Content-Type', content)
        self.send_header('Cache-Control', 'no-store')
        self.send_header('X-Robots-Tag', 'noindex, nofollow')
        self.send_header('Content-Security-Policy', "connect-src 'self'; form-action 'self'; frame-src 'self'")
        self.end_headers()
        self.wfile.write(data)

    def do_POST(self):
        if self.path == '/__qa/report':
            data=json.loads(self.rfile.read(int(self.headers.get('Content-Length','0'))))
            with (ROOT / ('before-metrics.jsonl' if BEFORE else 'after-metrics.jsonl')).open('a',encoding='utf-8') as out:
                out.write(json.dumps(data)+'\n')
            return self.respond(b'{}')
        self.respond(b'{"message":"Vista previa: los envios estan desactivados."}',status=503)

    def do_GET(self):
        path=unquote(urlsplit(self.path).path)
        if path == '/__qa/mobile':
            view=parse_qs(urlsplit(self.path).query).get('view',['home'])[0]
            target={'home':'/','catalog':'/tienda','product':'/producto/639'}.get(view,'/')
            html=f'<html lang="es"><meta charset="utf-8"><title>Revisión móvil Ibernovia</title><style>body{{margin:0;padding:16px;background:#e9e9e9;font:14px sans-serif}}nav{{text-align:center;margin-bottom:16px}}a{{margin:0 10px;color:#333}}iframe{{display:block;width:390px;height:844px;max-width:100%;border:0;margin:auto;background:white}}</style><nav>Vista de prueba 390 × 844 · <a href="?view=home">Inicio</a><a href="?view=catalog">Catálogo</a><a href="?view=product">Ficha</a></nav><iframe title="Vista móvil de Ibernovia" src="{target}"></iframe></html>'
            return self.respond(html.encode(),'text/html; charset=utf-8')
        if path.startswith('/api/'):
            result=[]
            if path=='/api/productos': result=PRODUCTS
            elif path.startswith('/api/productos/familia/'):
                result=[p for p in PRODUCTS if p.get('familia')==path.rsplit('/',1)[1]]
            elif path.startswith('/api/productos/categoria/'):
                result=[p for p in PRODUCTS if p.get('categoria')==path.rsplit('/',1)[1]]
            elif path.startswith('/api/productos/'):
                result=next((p for p in PRODUCTS if str(p['id'])==path.rsplit('/',1)[1]),None)
                if result is None: return self.respond(b'{}',status=404)
            return self.respond(json.dumps(result).encode())
        for root in [BUILD, PROJECT/'public']:
            file=(root/path.lstrip('/')).resolve()
            if file.is_relative_to(root.resolve()) and file.is_file():
                data=file.read_bytes()
                if file.suffix=='.js': data=data.replace(b'https://ibernovia.onrender.com', b'')
                if file.suffix=='.html': data=data.replace(b'<head>',b'<head>'+INSTRUMENT.encode())
                return self.respond(data,mimetypes.guess_type(file)[0] or 'application/octet-stream')
        if Path(path).suffix: return self.respond(b'Not found','text/plain',404)
        self.respond((BUILD/'index.html').read_bytes().replace(b'<head>',b'<head>'+INSTRUMENT.encode()),'text/html')

if __name__=='__main__':
    port=4173 if BEFORE else 4174
    print(f'http://127.0.0.1:{port} (public snapshot; submissions disabled)',flush=True)
    ThreadingHTTPServer(('127.0.0.1',port),Handler).serve_forever()
