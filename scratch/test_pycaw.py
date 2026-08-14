from pycaw.pycaw import AudioUtilities, EDataFlow

# State mask: DEVICE_STATE_ACTIVE = 1, DEVICE_STATE_DISABLED = 2, DEVICE_STATE_NOTPRESENT = 4, DEVICE_STATE_UNPLUGGED = 8
# To get all states, we can pass 0xf (15)
device_enumerator = AudioUtilities.GetDeviceEnumerator()
devices = device_enumerator.EnumAudioEndpoints(EDataFlow.eRender.value, 15)
count = devices.GetCount()
print(f"Found {count} total rendering devices:")
for i in range(count):
    device = devices.Item(i)
    dev_id = device.GetId()
    # Let's get the name
    try:
        # We can use the device's properties or just its representation
        # Pycaw doesn't expose a direct name property easily without opening the property store
        # but let's query the name using ctypes
        from comtypes import COMError
        from pycaw.pycaw import STGM_READ
        props = device.OpenPropertyStore(STGM_READ)
        # PKEY_Device_FriendlyName: {a45c254e-df1c-4efd-8020-67d146a850e0}, 14
        from comtypes.gen import MMDeviceAPILib
        # Let's try to get property
        # Fortunately pycaw wraps this or we can construct property key
        # In pycaw:
        # PKEY_Device_FriendlyName = PropertyKey()
        # For simplicity, we can do:
        # print(device)
    except Exception as e:
        pass
    
    # Let's print the device string or search
    # Let's get the device name using another method
    # Actually, pycaw's AudioUtilities.GetAllDevices() gets them and populates .FriendlyName
    # Let's just print the FriendlyName for all:
    try:
        # Let's re-retrieve them using GetAllDevices which gets friendly names for all states
        pass
    except:
        pass

# Let's just use the built-in way
all_devices = AudioUtilities.GetAllDevices()
for d in all_devices:
    print(f"Name: {d.FriendlyName} | ID: {d.id} | State: {d.state}")
