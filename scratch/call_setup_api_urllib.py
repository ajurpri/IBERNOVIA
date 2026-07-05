import json
import os
import sys
import urllib.request

url = "http://localhost:8080/api/admin/setup/create-admin"
headers = {
    "X-Setup-Key": os.getenv("APP_ADMIN_SETUP_KEY", ""),
    "Content-Type": "application/json"
}
data = {
    "email": os.getenv("ADMIN_EMAIL", "admin@ibernovia.com"),
    "nombre": os.getenv("ADMIN_NAME", "Administrador"),
    "password": os.getenv("ADMIN_PASSWORD", "")
}

if not headers["X-Setup-Key"] or not data["password"]:
    raise SystemExit("Define APP_ADMIN_SETUP_KEY y ADMIN_PASSWORD antes de ejecutar este script.")

req = urllib.request.Request(url, data=json.dumps(data).encode(), headers=headers)

try:
    with urllib.request.urlopen(req) as response:
        print(f"Status: {response.getcode()}")
        print(f"Response: {response.read().decode()}")
except Exception as e:
    print(f"Error: {e}")
    if hasattr(e, 'read'):
        print(f"Detail: {e.read().decode()}")
