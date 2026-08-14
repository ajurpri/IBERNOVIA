import os
from pathlib import Path

workspace = Path(r"c:\Users\alvar\Videos\IBERNOVIA")
filenames = [
    "producto_1786534303146.jpg",
    "producto_1786035139448.jpg",
    "producto_1786034940643.jpg",
    "producto_1786034982248.jpg"
]

print("Searching for files locally:")
for root, dirs, files in os.walk(workspace):
    for f in files:
        if f in filenames:
            print(f"  Found '{f}' at: {os.path.join(root, f)}")
