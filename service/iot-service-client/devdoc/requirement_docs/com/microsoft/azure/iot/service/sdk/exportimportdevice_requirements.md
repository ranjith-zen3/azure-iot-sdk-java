# ImportExportDevice Requirements

## Overview

The ImportExportDevice class stores device parameters and it is used by the bulk import/export operations.

## References

## Exposed API

```java
public class ExportImportDevice
{
    public ExportImportDevice()
    public ExportImportDevice(String deviceId, AuthenticationType authenticationType)

    public void setId(String id)
    public String getId()
    public String getETag()
    public void setETag(String ETag)
    public ImportMode getImportMode()
    public void setImportMode(ImportMode importMode)
    public DeviceStatus getStatus()
    public void setStatus(DeviceStatus status)
    public String getStatusReason()
    public void setStatusReason(String statusReason)
    public Authentication getAuthentication()
    public void setAuthentication(Authentication authentication)

    public ExportImportDevice(ExportImportDeviceParser parser)
    public ExportImportDeviceParser toExportImportDeviceParser()
}
```


**SRS_SERVICE_SDK_JAVA_IMPORT_EXPORT_DEVICE_15_001: [** The ExportImportDevice class has the following properties: Id,
Etag, ImportMode, Status, StatusReason, Authentication **]**

### ExportImportDevice
```java
public ExportImportDevice()
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_050: [**This constructor will automatically set the authentication type of this object to be sas, and will generate a deviceId and symmetric key.**]**


```java
public ExportImportDevice(String deviceId, AuthenticationType authenticationType)
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_051: [**This constructor will save the provided deviceId and authenticationType to itself.**]**


```java
public ExportImportDevice(ExportImportDeviceParser parser)
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_052: [**This constructor will use the properties of the provided parser object to set the new ExportImportDevice's properties.**]**
**SRS_SERVICE_SDK_JAVA_DEVICE_34_053: [**If the provided parser does not have values for the properties deviceId or authentication, an IllegalArgumentException shall be thrown.**]**


### toExportImportDeviceParser
```java
public ExportImportDeviceParser toExportImportDeviceParser()
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_054: [**This method shall convert this into an ExportImportDeviceParser object and return it.**]**


```java
public void setId(String id)
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_055: [**If the provided id is null, an IllegalArgumentException shall be thrown.**]**


```java
public void setAuthentication(Authentication authentication)
```
**SRS_SERVICE_SDK_JAVA_DEVICE_34_056: [**If the provided authentication is null, an IllegalArgumentException shall be thrown.**]**