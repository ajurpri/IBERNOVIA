import ftplib
import hashlib
import io
from pathlib import Path
import sys

root = Path(__file__).resolve().parents[2]
dist = root / 'ibernovias/front-end/dist'
backup = Path(__file__).parent / 'remote-backup'
backup.mkdir(exist_ok=True)
password = sys.stdin.readline().rstrip('\r\n')
ftp = ftplib.FTP_TLS(timeout=45)
ftp.connect('ftp.ibernovia.es', 21)
ftp.login('ibernovia.es_u7avg8028', password)
ftp.prot_p()
ftp.cwd('/httpdocs')
for name in ['index.html', 'sitemap.xml', '.htaccess']:
    data = io.BytesIO()
    ftp.retrbinary('RETR ' + name, data.write)
    target = backup / name
    if not target.exists():
        target.write_bytes(data.getvalue())
print('Remote configuration backed up', flush=True)
for path in sorted((dist / 'assets').iterdir()):
    with path.open('rb') as handle:
        ftp.storbinary('STOR assets/' + path.name, handle)
    received = io.BytesIO()
    ftp.retrbinary('RETR assets/' + path.name, received.write)
    assert hashlib.sha256(received.getvalue()).digest() == hashlib.sha256(path.read_bytes()).digest()
print('Assets uploaded and verified', flush=True)
for name in ['complementos-novia-andujar.html', 'sitemap.xml', 'index.html']:
    with (dist / name).open('rb') as handle:
        ftp.storbinary('STOR ' + name, handle)
    received = io.BytesIO()
    ftp.retrbinary('RETR ' + name, received.write)
    assert received.getvalue() == (dist / name).read_bytes()
ftp.quit()
print('Published and verified; previous assets retained')
