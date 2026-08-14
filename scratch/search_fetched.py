import json
from pathlib import Path

content_file = Path(r"C:\Users\alvar\.gemini\antigravity-ide\brain\3c4b1427-c647-46ad-a9fb-cac8c37ed7c9\.system_generated\steps\85\content.md")
lines = content_file.read_text(encoding='utf-8').splitlines()

# The first 4 lines might be header stuff:
# Source: https://ibernovia.onrender.com/api/productos
# 
# ---
# 
# The JSON starts at line 5
json_str = "".join(lines[4:])
data = json.loads(json_str)

print(f"Total products fetched: {len(data)}")

# Find 1239, 1242, 14126, 14136
refs = ['1239', '1242', '14126', '14136']
for ref in refs:
    matches = [x for x in data if ref in str(x.get('nombre', '')) or ref in str(x.get('imagen', ''))]
    print(f"\nMatches for '{ref}':")
    for m in matches:
        print(f"  ID: {m.get('id')} | Name: {m.get('nombre')} | Image: {m.get('imagen')} | Family: {m.get('familia')} | Cat: {m.get('categoria')}")
