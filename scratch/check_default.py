from pycaw.pycaw import AudioUtilities, EDataFlow, ERole
device_enumerator = AudioUtilities.GetDeviceEnumerator()
device = device_enumerator.GetDefaultAudioEndpoint(EDataFlow.eRender.value, ERole.eConsole.value)
# Get friendly name
for d in AudioUtilities.GetAllDevices():
    if d.id == device.GetId():
        print(f"Current Default Device: {d.FriendlyName} ({d.id})")
        break
