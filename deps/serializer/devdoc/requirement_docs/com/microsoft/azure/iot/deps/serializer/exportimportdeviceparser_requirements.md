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

    public String getId()
    public void setId(String id)
    public String getETag()
    public void setETag(String ETag)
    public String getImportMode()
    public void setImportMode(String importMode)
    public String getStatus()
    public void setStatus(String status)
    public String getStatusReason()
    public void setStatusReason(String statusReason)
    public AuthenticationParser getAuthentication()
    public void setAuthentication(AuthenticationParser authentication)
}
```

### toJson
```java
public static String toJson(ExportImportDeviceParser device);
```
**SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [**The parser shall save the ExportImportDeviceParser's authentication to the returned json representation**]**


### ExportImportDeviceParser
```java
public ExportImportDeviceParser(String json);
```
**SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [**The parser shall look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance**]**

**SRS_EXPORTIMPORTDEVICE_PARSER_34_005: [**This constructor will take the provided json and convert it into a new ExportImportDeviceParser and return it.**]**

**SRS_EXPORTIMPORTDEVICE_PARSER_34_008: [**If the provided json is missing the Id field, or its value is empty, an IllegalArgumentException shall be thrown**]**

**SRS_EXPORTIMPORTDEVICE_PARSER_34_009: [**If the provided json is missing the Authentication field, or its value is empty, an IllegalArgumentException shall be thrown.**]**

**SRS_EXPORTIMPORTDEVICE_PARSER_34_011: [**If the provided json is null, empty, or cannot be parsed into an ExportImportDeviceParser object, an IllegalArgumentException shall be thrown.**]**


### Properties

**SRS_EXPORTIMPORTDEVICE_PARSER_34_003: [**This parser has properties for eTag, Id, status, statusReason, importMode and authenticationParser.**]**


### setAuthentication
```java
public void setAuthentication(AuthenticationParser authentication)
```
**SRS_EXPORTIMPORTDEVICE_PARSER_34_006: [**If the provided authentication is null, an IllegalArgumentException shall be thrown.**]**

### setId
```java
public void setId(String id)
```
**SRS_EXPORTIMPORTDEVICE_PARSER_34_007: [**If the provided id is null, an IllegalArgumentException shall be thrown.**]**

