from pathlib import Path
p=Path('REVISION_IBERNOVIA_2026-10-30.md');s=p.read_text(encoding='utf-8').replace('| CSS global | 71,88 | 59,39 |','| CSS global | 71,88 | 59,60 |').replace('| JavaScript de inicio | 12,76 | 8,9 |','| JavaScript de inicio | 12,76 | 8,81 |');p.write_text(s,encoding='utf-8')
