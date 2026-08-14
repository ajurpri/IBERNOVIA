import sys
import subprocess
from pycaw.pycaw import AudioUtilities

def change_to_device(name_query):
    # Get all audio devices (active or not)
    all_devices = AudioUtilities.GetAllDevices()
    target_id = None
    target_name = None
    
    for d in all_devices:
        if d.FriendlyName and name_query.lower() in d.FriendlyName.lower():
            target_id = d.id
            target_name = d.FriendlyName
            break
            
    if not target_id:
        print(f"Could not find any device matching: {name_query}")
        print("Available devices are:")
        for d in all_devices:
            print(f" - {d.FriendlyName} ({d.id})")
        return False
        
    print(f"Found target device: '{target_name}' with ID: {target_id}")
    
    # Run the compiled helper to set this device as default
    exe_path = r"scratch\SetDefaultAudioDevice.exe"
    try:
        res = subprocess.run([exe_path, target_id], capture_output=True, text=True, check=True)
        print("stdout:", res.stdout)
        print("stderr:", res.stderr)
        return True
    except subprocess.CalledProcessError as e:
        print("Failed to run helper:", e)
        print("stdout:", e.stdout)
        print("stderr:", e.stderr)
        return False

if __name__ == '__main__':
    # Test setting to "Speaker"
    target = sys.argv[1] if len(sys.argv) > 1 else "Speaker"
    change_to_device(target)
