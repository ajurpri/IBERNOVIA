import urllib.request
import json
import sys

url = "http://localhost:8080/api/admin/setup/create-admin"
headers = {
    "X-Setup-Key": "ibernovia-setup-2026",
    "Content-Type": "application/json"
}
data = {
    "email": "admin@ibernovia.com",
    "nombre": "Administrador",
    "password": "admin123456"
}

req = urllib.request.Request(url, data=json.dumps(data).encode(), headers=headers)

try:
    with urllib.request.urlopen(req) as response:
        print(f"Status: {response.getcode()}")
        print(f"Response: {response.read().decode()}")
except Exception as e:
    print(f"Error: {e}")
    if hasattr(e, 'read'):
        print(f"Detail: {e.read().decode()}")
