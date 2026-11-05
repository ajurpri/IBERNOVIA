import base64
import ftplib
import re
import sys
from pathlib import Path
import json
from urllib.request import Request, urlopen

password = sys.stdin.readline().strip()
source = Path('ibernovias/front-end/src/views/AdminView.vue').read_text(encoding='utf-8')
token = re.search(r"const token = '([^']+)'", source[source.index('const uploadImageToVps'):]).group(1)
png = base64.b64decode('iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+a2ioAAAAASUVORK5CYII=')
boundary = 'IbernoviaRouteCheckBoundary'
body = (f'--{boundary}\r\nContent-Disposition: form-data; name="image"; filename="route-check.png"\r\nContent-Type: image/png\r\n\r\n'.encode() + png + f'\r\n--{boundary}--\r\n'.encode())
request = Request('https://www.ibernovia.es/upload.php', data=body, headers={'X-Security-Token': token, 'Content-Type': 'multipart/form-data; boundary=' + boundary})
with urlopen(request, timeout=60) as response:
    url = json.load(response)['url']
assert re.fullmatch(r'/images/productos/producto_\d+_[a-f0-9]+\.png', url), url
try:
    for origin in ['https://www.ibernovia.es', 'https://ibernovia.es']:
        with urlopen(origin + url, timeout=30) as image:
            assert image.status == 200
            assert image.read() == png
        print(origin, 'upload image readable, exact bytes verified', flush=True)
finally:
    ftp = ftplib.FTP_TLS(timeout=30)
    ftp.connect('ftp.ibernovia.es', 21)
    ftp.login('ibernovia.es_u7avg8028', password)
    ftp.prot_p()
    ftp.delete('/httpdocs' + url)
    ftp.quit()
    print('Only the temporary test image removed; no products changed', flush=True)
