# DeviceParser Requirements

## Overview

Representation of a Device with a Json deserializer and serializer.

## References


## Exposed API

```java
public class DeviceParser
{
    public static DeviceParser fromJson(String json);
    public static String toJson(DeviceParser device);
}
```

### toJson
```java
public static String toJson(DeviceParser device);
```
**SRS_DEVICE_PARSER_34_001: [**The parser will save the DeviceParser's authentication to the returned json representation**]**


### fromJson
```java
public static DeviceParser fromJson(String json);
```
**SRS_DEVICE_PARSER_34_002: [**The parser will look for the authentication of the serialized device and save it to the returned DeviceParser instance**]**