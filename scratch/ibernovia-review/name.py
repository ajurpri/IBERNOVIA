from pathlib import Path
p=Path('ibernovias/front-end/src/lib/productPresentation.js');s=p.read_text(encoding='utf-8').replace("return sentenceCase(firstLine.replace(/\\.$/, ''))","return sentenceCase(firstLine.replace(/\\.$/, '')).replace(/^Abanico novia\\b/, 'Abanico de novia')");p.write_text(s,encoding='utf-8')
p=Path('ibernovias/front-end/src/lib/productPresentation.test.mjs');s=p.read_text(encoding='utf-8').replace("'Abanico novia de encaje marfil'","'Abanico de novia de encaje marfil'");p.write_text(s,encoding='utf-8')
