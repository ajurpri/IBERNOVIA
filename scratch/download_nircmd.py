import urllib.request
import zipfile
import io
import os

url = "https://www.nirsoft.net/utils/nircmd-x64.zip"
print(f"Downloading {url}...")
try:
    with urllib.request.urlopen(url) as response:
        zip_data = response.read()
    print("Downloaded successfully. Extracting...")
    with zipfile.ZipFile(io.BytesIO(zip_data)) as z:
        z.extractall("scratch")
    print("Extracted successfully to 'scratch' directory.")
    if os.path.exists("scratch/nircmd.exe"):
        print("nircmd.exe is ready!")
    else:
        print("nircmd.exe NOT found in extracted files.")
except Exception as e:
    print(f"Error: {e}")
