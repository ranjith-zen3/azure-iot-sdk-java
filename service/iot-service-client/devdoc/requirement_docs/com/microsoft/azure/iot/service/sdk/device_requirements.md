# Device Requirements

## Overview

The Device class is the main DTO for the RegistryManager class. It implements static constructors and stores device parameters.

## References

([IoT Hub SDK.doc](https://microsoft.sharepoint.com/teams/Azure_IoT/_layouts/15/WopiFrame.aspx?sourcedoc={9A552E4B-EC00-408F-AE9A-D8C2C37E904F}&file=IoT%20Hub%20SDK.docx&action=default))

## Exposed API

```java
public class Device
{
    public static Device createFromId(String deviceId, DeviceStatus status, SymmetricKey symmetricKey)
    public static Device createCertificateSecuredDevice(String deviceId, DeviceStatus status)
    public static Device createSelfSignedDevice(String deviceId, DeviceStatus status, String primaryThumbprint, String secondaryThumbprint)

    protected Device(String deviceId, DeviceStatus status, SymmetricKey symmetricKey)
}
```
**SRS_SERVICE_SDK_JAVA_DEVICE_12_001: [** The Device class has the following properties: Id, Etag, Authentication.SymmetricKey, State, StateReason, StateUpdatedTime, ConnectionState, ConnectionStateUpdatedTime, LastActivityTime, symmetricKey, thumbprint, status, authentication**]**

### createFromId

```java
public static Device createFromId(String deviceId, DeviceStatus status, SymmetricKey symmetricKey) throws IllegalArgumentException, NoSuchAlgorithmException;
```
**SRS_SERVICE_SDK_JAVA_DEVICE_12_002: [** The function shall throw IllegalArgumentException if the input string is empty or null **]**

**SRS_SERVICE_SDK_JAVA_DEVICE_12_003: [** The function shall create a new instance of Device using the given id and return with it **]**

### Device

```java
private Device(String deviceId, DeviceStatus status, SymmetricKey symmetricKey) throws NoSuchAlgorithmException, IllegalArgumentException;
```
**SRS_SERVICE_SDK_JAVA_DEVICE_12_004: [** The constructor shall throw IllegalArgumentException if the input string is empty or null **]**

**SRS_SERVICE_SDK_JAVA_DEVICE_12_005: [** If the input symmetric key is empty, the constructor shall create a new SymmetricKey instance using AES encryption and store it into a member variable **]**

**SRS_SERVICE_SDK_JAVA_DEVICE_15_007: [** The constructor shall store the input device status and symmetric key into a member variable **]**

**SRS_SERVICE_SDK_JAVA_DEVICE_12_006: [** The constructor shall initialize all properties to default values **]**


```java
public static Device createCertificateSecuredDevice(String deviceId, DeviceStatus status)
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_008: [**The function shall throw IllegalArgumentException if the device Id is empty or null**]**


```java
public static Device createSelfSignedDevice(String deviceId, DeviceStatus status, String primaryThumbprint, String secondaryThumbprint)
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_010: [**The function shall throw IllegalArgumentException if the device Id is empty or null**]**


```java
private Device(String deviceId, DeviceStatus status)
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [**The function shall set the authentication to use Certificate Authority signed certificates**]**