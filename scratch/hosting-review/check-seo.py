import functools
import json
import threading
from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from playwright.sync_api import sync_playwright

dist = Path('ibernovias/front-end/dist').resolve()
out = Path('scratch/hosting-review')
class QuietHandler(SimpleHTTPRequestHandler):
    def log_message(self, *args): pass
server = ThreadingHTTPServer(('127.0.0.1', 0), functools.partial(QuietHandler, directory=str(dist)))
threading.Thread(target=server.serve_forever, daemon=True).start()
base = f'http://127.0.0.1:{server.server_port}'
try:
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        for width in [390, 1440]:
            page = browser.new_page(viewport={'width':width,'height':1000})
            for route, label in [('/complementos-novia-andujar.html','local'),('/','home'),('/#/contacto','contact')]:
                page.goto(base+route, wait_until='domcontentloaded')
                page.get_by_text('Paola Novias', exact=False).first.wait_for()
                assert page.locator('h1').count() == 1
                assert page.evaluate('document.documentElement.scrollWidth <= innerWidth')
                page.screenshot(path=str(out / f'seo-{label}-{width}.png'), full_page=True)
                print(width,label,'Paola visible, one h1, no overflow')
            page.close()
        page = browser.new_page(java_script_enabled=False)
        for route in ['/', '/complementos-novia-andujar.html']:
            response = page.goto(base+route)
            assert response.status == 200
            assert 'Paola Novias' in page.locator('body').inner_text()
            assert page.locator('link[rel="canonical"]').count() == 1
            for script in page.locator('script[type="application/ld+json"]').all():
                assert json.loads(script.inner_text())['@context'] == 'https://schema.org'
            print(route, 'crawlable without JavaScript; structured data valid JSON')
        browser.close()
finally:
    server.shutdown()
