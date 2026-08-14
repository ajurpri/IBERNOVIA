import urllib.request
import json

try:
    url = "https://ipinfo.io/89.248.107.58/json"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    with urllib.request.urlopen(req) as response:
        data = json.loads(response.read().decode())
        print("IP Info:")
        for k, v in data.items():
            print(f"  {k}: {v}")
except Exception as e:
    print("Error:", e)
