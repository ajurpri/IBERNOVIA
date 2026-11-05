import functools
import base64
import json
import threading
from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from playwright.sync_api import sync_playwright

dist = Path('ibernovias/front-end/dist').resolve()
class QuietHandler(SimpleHTTPRequestHandler):
    def log_message(self, *args): pass
server = ThreadingHTTPServer(('127.0.0.1', 0), functools.partial(QuietHandler, directory=str(dist)))
threading.Thread(target=server.serve_forever, daemon=True).start()
product = dict(id=999999, nombre='REF-PRUEBA-NUEVA', descripcion='', familia='Novia', categoria='Abanicos', activo=True, imagen='/images/productos/prueba-nueva.png', stock=10)
try:
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        page = browser.new_page()
        page.add_init_script("localStorage.setItem('ibernovia_products_public_v2', JSON.stringify({timestamp:Date.now(),data:[{id:999999,nombre:'REF-ANTIGUA',activo:true,imagen:'/images/productos/antigua.png'}]}))")
        page.route('**/api/productos', lambda route: route.fulfill(json=[product]))
        page.route('**/images/productos/*.png', lambda route: route.fulfill(content_type='image/png', body=base64.b64decode('iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+a2ioAAAAASUVORK5CYII=')))
        page.goto(f'http://127.0.0.1:{server.server_port}/#/tienda')
        page.get_by_text('REF-PRUEBA-NUEVA', exact=True).first.wait_for()
        assert page.locator('img[src$="/images/productos/prueba-nueva.png"]').count() > 0
        page.evaluate("location.hash = '#/contacto'")
        page.wait_for_function("document.title.startsWith('Contacto')")
        product['nombre'] = 'REF-ACTUALIZADA'
        product['imagen'] = '/images/productos/actualizada.png'
        page.evaluate("location.hash = '#/tienda'")
        page.get_by_text('REF-ACTUALIZADA', exact=True).first.wait_for()
        assert page.locator('img[src$="/images/productos/actualizada.png"]').count() > 0
        print('PASS: stale cache replaced; returning to catalog loads updated product and image URL')
        browser.close()
finally:
    server.shutdown()
