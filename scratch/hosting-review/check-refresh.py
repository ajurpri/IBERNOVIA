from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    errors = []
    for route in ['tienda', 'contacto', 'producto/639']:
        page = browser.new_page()
        page.on('pageerror', lambda error: errors.append(str(error)))
        response = page.goto('https://www.ibernovia.es/#/' + route, wait_until='domcontentloaded')
        assert response.status == 200, (route, response.status)
        page.wait_for_function("document.querySelector('#app').textContent.trim().length > 30")
        expected = {'tienda': 'Catálogo profesional', 'contacto': 'Contacto', 'producto/639': 'Producto'}[route]
        page.wait_for_function('(prefix) => document.title.startsWith(prefix)', arg=expected)
        title = page.title()
        response = page.reload(wait_until='domcontentloaded')
        assert response.status == 200, (route, response.status)
        page.wait_for_function("document.querySelector('#app').textContent.trim().length > 30")
        page.wait_for_function('(title) => document.title === title', arg=title)
        assert '#/' + route in page.url
        print(route, 'direct access and refresh OK', title, flush=True)
        page.close()
    assert not errors, errors
    browser.close()
