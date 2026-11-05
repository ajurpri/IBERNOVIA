import ftplib,sys
from pathlib import Path
f=ftplib.FTP_TLS(timeout=30)
f.connect('ftp.ibernovia.es',21)
f.login('ibernovia.es_u7avg8028',sys.stdin.readline().strip())
f.prot_p()
p=Path('scratch/hosting-review/remote-backup/upload.php')
with p.open('wb') as out:f.retrbinary('RETR /httpdocs/upload.php',out.write)
f.quit()
print('Upload handler downloaded for inspection')
