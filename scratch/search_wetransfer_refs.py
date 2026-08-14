import os
from pathlib import Path

workspace = Path(r"c:\Users\alvar\Videos\IBERNOVIA")
wetransfer_dir = workspace / "wetransfer_fotos-y-arbol-web_2026-04-16_1721"

refs = ['14121', '1419', '1424', '1425']

print("Searching WeTransfer directory for references:")
if not wetransfer_dir.exists():
    print("WeTransfer directory does not exist.")
else:
    found_any = False
    for root, dirs, files in os.walk(wetransfer_dir):
        for f in files:
            for ref in refs:
                if ref in f:
                    print(f"  Found '{f}' at: {os.path.join(root, f)}")
                    found_any = True
    if not found_any:
        print("  No references found in WeTransfer directory.")
