import requests
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

try:
    response = requests.post(url, headers=headers, json=data)
    print(f"Status: {response.status_code}")
    print(f"Response: {response.json()}")
except Exception as e:
    print(f"Error: {e}")
