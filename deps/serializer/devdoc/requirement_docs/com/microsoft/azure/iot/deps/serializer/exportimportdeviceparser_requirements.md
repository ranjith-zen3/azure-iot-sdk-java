# ExportImportDeviceParser Requirements

## Overview

Representation of an ExportImportDevice with a Json deserializer and serializer.

## References


## Exposed API

```java
public class ExportImportDeviceParser
{
    public static ExportImportDeviceParser fromJson(String json);
    public static String toJson(ExportImportDeviceParser device);
}
```

### toJson
```java
public static String toJson(DeviceParser device);
```
**SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [**The parser will save the ExportImportDeviceParser's authentication to the returned json representation**]**


### fromJson
```java
public static DeviceParser fromJson(String json);
```
**SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [**The parser will look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance**]**