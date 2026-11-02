from pathlib import Path
p=Path('scratch/ibernovia-review/audit-public.py');s=p.read_text();a=s.index('    with urlopen');b=s.index("(root/'public-api",a);s=s[:a]+'''    try:
        with urlopen('https://ibernovia.onrender.com'+quote(path),timeout=45) as response:
            data=json.load(response)
            rows=data if isinstance(data,list) else [data]
            prices=sum(any(row.get(key) is not None for key in ['precio','precioOferta','precioOriginal']) for row in rows)
            results.append(dict(path=path,status=response.status,rows=len(rows),rowsWithPrices=prices,cache=response.headers.get('Cache-Control')))
    except Exception as error:
        results.append(dict(path=path,error=str(error)))
'''+s[b:];p.write_text(s)
