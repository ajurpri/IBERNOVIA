import os
import requests
import sys

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

try:
    response = requests.post(url, headers=headers, json=data)
    print(f"Status: {response.status_code}")
    print(f"Response: {response.json()}")
except Exception as e:
    print(f"Error: {e}")
