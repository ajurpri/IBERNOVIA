using System;
using System.Runtime.InteropServices;

namespace SetDefaultAudioDevice
{
    [ComImport]
    [Guid("29415738-c096-41cd-9577-40c6d595b9d3")]
    class PolicyConfigClient
    {
    }

    [ComImport]
    [Guid("870af99c-171d-4f9e-af0d-e63df40c2bc9")]
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    interface IPolicyConfig
    {
        int GetShareMode(string wszDeviceId, ref uint pShareMode);
        int SetShareMode(string wszDeviceId, uint shareMode);
        int GetPropertyValue(string wszDeviceId, ref uint pKey, ref uint pPropVariant);
        int SetPropertyValue(string wszDeviceId, ref uint pKey, ref uint pPropVariant);
        int SetDefaultEndpoint(string wszDeviceId, int role);
        int SetEndpointVisibility(string wszDeviceId, int fVisible);
    }

    [ComImport]
    [Guid("f8679f50-850a-41cf-9c74-d83a90da350a")]
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    interface IPolicyConfigVista
    {
        int GetShareMode(string wszDeviceId, ref uint pShareMode);
        int SetShareMode(string wszDeviceId, uint shareMode);
        int GetPropertyValue(string wszDeviceId, ref uint pKey, ref uint pPropVariant);
        int SetPropertyValue(string wszDeviceId, ref uint pKey, ref uint pPropVariant);
        int SetDefaultEndpoint(string wszDeviceId, int role);
        int SetEndpointVisibility(string wszDeviceId, int fVisible);
    }

    class Program
    {
        static int Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("Usage: SetDefaultAudioDevice <DeviceID>");
                return 1;
            }
            string deviceId = args[0];
            try
            {
                PolicyConfigClient client = new PolicyConfigClient();
                try
                {
                    IPolicyConfig policyConfig = (IPolicyConfig)client;
                    policyConfig.SetDefaultEndpoint(deviceId, 0); // eConsole
                    policyConfig.SetDefaultEndpoint(deviceId, 1); // eMultimedia
                    policyConfig.SetDefaultEndpoint(deviceId, 2); // eCommunications
                    Console.WriteLine("Success: Set default device (Win10+)");
                    return 0;
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Failed with IPolicyConfig: " + ex.Message + ". Trying IPolicyConfigVista...");
                    IPolicyConfigVista policyConfigVista = (IPolicyConfigVista)client;
                    policyConfigVista.SetDefaultEndpoint(deviceId, 0);
                    policyConfigVista.SetDefaultEndpoint(deviceId, 1);
                    policyConfigVista.SetDefaultEndpoint(deviceId, 2);
                    Console.WriteLine("Success: Set default device (Vista/Win7/Win8)");
                    return 0;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: " + ex.Message);
                return 2;
            }
        }
    }
}
