from pathlib import Path
p=Path('scratch/ibernovia-review/preview.py');s=p.read_text(encoding='utf-8').replace('import json, mimetypes, sys','import json, mimetypes, sys\n\nmimetypes.add_type("image/webp", ".webp")\nmimetypes.add_type("text/javascript", ".js")');p.write_text(s,encoding='utf-8')
