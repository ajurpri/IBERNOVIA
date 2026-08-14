import urllib.request
import json
from pathlib import Path

# Let's check some URLs
urls = [
    "https://ibernovia.onrender.com/images/productos/producto_1785950735452.jpg",
    "https://ibernovia.onrender.com/images/productos/producto_1786037325247.jpg",
    "https://ibernovia.onrender.com/images/productos/producto_1785952512145.jpg",
    "https://ibernovia.onrender.com/images/productos/producto_1785952564011.jpg"
]

for url in urls:
    try:
        req = urllib.request.Request(
            url, 
            method='HEAD',
            headers={'User-Agent': 'Mozilla/5.0'}
        )
        with urllib.request.urlopen(req) as resp:
            print(f"{url} -> Status: {resp.status}")
    except Exception as e:
        print(f"{url} -> Error: {e}")
