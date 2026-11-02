from urllib.request import urlopen, Request
from urllib.parse import quote
from pathlib import Path
import json

root=Path(__file__).parent
results=[]
for path in ['/api/productos','/api/productos/639','/api/productos/familia/Novia','/api/productos/categoria/Abanicos','/api/promociones']:
    try:
        with urlopen('https://ibernovia.onrender.com'+quote(path),timeout=45) as response:
            data=json.load(response)
            rows=data if isinstance(data,list) else [data]
            prices=sum(any(row.get(key) is not None for key in ['precio','precioOferta','precioOriginal']) for row in rows)
            results.append(dict(path=path,status=response.status,rows=len(rows),rowsWithPrices=prices,cache=response.headers.get('Cache-Control')))
    except Exception as error:
        results.append(dict(path=path,error=str(error)))
(root/'public-api-audit.json').write_text(json.dumps(results,indent=2),encoding='utf-8')
print(json.dumps(results,indent=2))
