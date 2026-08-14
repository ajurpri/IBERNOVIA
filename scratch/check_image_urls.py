import urllib.request
import json
from pathlib import Path

# Let's check some URLs
urls = [
    "https://ibernovia.onrender.com/images/productos/producto_1786534303146.jpg",
    "https://ibernovia.onrender.com/images/productos/producto_1786035139448.jpg",
    "https://ibernovia.onrender.com/images/productos/producto_1786034940643.jpg",
    "https://ibernovia.onrender.com/images/productos/producto_1786034982248.jpg"
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
