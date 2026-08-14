import sys
import ctypes
from ctypes.wintypes import LPCWSTR
from comtypes import GUID, COMMETHOD, HRESULT, CLSCTX_ALL
import comtypes.client
from pycaw.pycaw import AudioUtilities, EDataFlow, ERole

# IID for IPolicyConfig in different Windows versions
# Windows 10/11:
IID_IPolicyConfig_Win10 = '{870af99c-171d-4f9e-af0d-e63df40c2bc9}'
# Windows Vista/7/8/early 10:
IID_IPolicyConfig_Vista = '{f8679f50-850a-41cf-9c74-d83a90da350a}'
# Windows 10 alternative:
IID_IPolicyConfig_Win10_Alt = '{568b9108-c322-4770-9f81-7730874e531e}'

# CoClass CLSID
CLSID_PolicyConfigClient = '{29415738-c096-41cd-9577-40c6d595b9d3}'

def create_policy_config_class(iid_str):
    class IPolicyConfig(comtypes.IUnknown):
        _iid_ = GUID(iid_str)
        _methods_ = [
            COMMETHOD([], HRESULT, 'GetShareMode',),
            COMMETHOD([], HRESULT, 'SetShareMode',),
            COMMETHOD([], HRESULT, 'GetPropertyValue',),
            COMMETHOD([], HRESULT, 'SetPropertyValue',),
            COMMETHOD([], HRESULT, 'GetDefaultEndpoint',),
            COMMETHOD([], HRESULT, 'SetDefaultEndpoint',
                      (['in'], LPCWSTR, 'wszDeviceId'),
                      (['in'], ctypes.c_int, 'role')),
        ]
    return IPolicyConfig

def set_default_device(device_name):
    # Find the device ID first using pycaw
    device_enumerator = AudioUtilities.GetDeviceEnumerator()
    devices = device_enumerator.EnumAudioEndpoints(EDataFlow.eRender.value, 15) # all states
    count = devices.GetCount()
    
    target_id = None
    target_name = None
    
    for i in range(count):
        device = devices.Item(i)
        dev_id = device.GetId()
        # Find friendly name using pycaw helper
        for d in AudioUtilities.GetAllDevices():
            if d.id == dev_id:
                if d.FriendlyName and device_name.lower() in d.FriendlyName.lower():
                    target_id = dev_id
                    target_name = d.FriendlyName
                    break
        if target_id:
            break
            
    if not target_id:
        print(f"Could not find device matching: {device_name}")
        return False
        
    print(f"Found target device: '{target_name}' with ID: {target_id}")
    
    # Try all known IIDs
    iids = [IID_IPolicyConfig_Win10, IID_IPolicyConfig_Vista, IID_IPolicyConfig_Win10_Alt]
    success = False
    for iid in iids:
        try:
            print(f"Trying IID: {iid}")
            policy_class = create_policy_config_class(iid)
            policy_config = comtypes.client.CreateObject(
                CLSID_PolicyConfigClient,
                interface=policy_class,
                clsctx=CLSCTX_ALL
            )
            # Set default for eConsole (0), eMultimedia (1), eCommunications (2)
            policy_config.SetDefaultEndpoint(target_id, 0)
            policy_config.SetDefaultEndpoint(target_id, 1)
            policy_config.SetDefaultEndpoint(target_id, 2)
            print("Successfully set as default!")
            success = True
            break
        except Exception as e:
            print(f"Failed with IID {iid}: {e}")
            
    return success

if __name__ == '__main__':
    # Test setting to "Speaker" or a user-provided argument
    name = sys.argv[1] if len(sys.argv) > 0 else "Speaker"
    set_default_device(name)
