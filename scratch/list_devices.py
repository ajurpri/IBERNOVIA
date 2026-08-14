import sys
import subprocess

# Ensure comtypes and pycaw are installed
for pkg in ["comtypes", "pycaw"]:
    try:
        __import__(pkg)
    except ImportError:
        subprocess.check_call([sys.executable, "-m", "pip", "install", pkg])

import ctypes
from comtypes import GUID, COMMETHOD, HRESULT, CLSCTX_ALL
from comtypes.automation import LPWSTR
from pycaw.pycaw import AudioUtilities, IMMDeviceEnumerator, EDataFlow, ERole

def list_devices():
    device_enumerator = AudioUtilities.GetDeviceEnumerator()
    devices = device_enumerator.EnumAudioEndpoints(EDataFlow.eRender.value, 0x1) # 0x1 is DEVICE_STATE_ACTIVE
    count = devices.GetCount()
    print(f"Found {count} active rendering devices:")
    for i in range(count):
        device = devices.Item(i)
        # In pycaw, we can use the friendly name. Let's just print the ID.
        dev_id = device.GetId()
        # We can also get properties.
        # Let's get the friendly name using the property store.
        # PROPERTYKEY: {a45c254e-df1c-4efd-8020-67d146a850e0}, 14
        # We can also query using AudioUtilities
        print(f"Index: {i}, ID: {dev_id}")

list_devices()
