import json
from pathlib import Path

workspace = Path(r"c:\Users\alvar\Videos\IBERNOVIA")
backups = list(workspace.glob("productos_backup_*.json"))

for b in backups:
    print(f"Backup: {b.name}")
    try:
        data = json.loads(b.read_text(encoding='utf-8'))
        print(f"  Count: {len(data)}")
        # Check if 1239-AB or similar exists
        matches = [x for x in data if '1239' in str(x.get('nombre', '')) or '1239' in str(x.get('imagen', ''))]
        if matches:
            print(f"  Found 1239 matches in {b.name}:")
            for m in matches[:3]:
                print(f"    {m}")
        else:
            print("  No 1239 matches")
            
        # Families and categories
        fams = {}
        for x in data:
            f = x.get('familia') or x.get('categoria') # depends on schema
            fams[f] = fams.get(f, 0) + 1
        print(f"  Families/Categories: {fams}")
    except Exception as e:
        print(f"  Error reading: {e}")
