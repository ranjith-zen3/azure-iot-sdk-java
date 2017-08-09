# DeviceParser Requirements

## Overview

Representation of a Device with a Json deserializer and serializer.

## References


## Exposed API

```java
public class DeviceParser
{
    public DeviceParser(String json);
    public String toJson();

    public String geteTag()
    public void seteTag(String eTag) throws IllegalArgumentException
    public String getDeviceId()
    public void setDeviceId(String deviceId) throws IllegalArgumentException
    public String getGenerationId()
    public void setGenerationId(String generationId) throws IllegalArgumentException
    public String getStatus()
    public void setStatus(String status) throws IllegalArgumentException
    public String getStatusReason()
    public void setStatusReason(String statusReason) throws IllegalArgumentException
    public Date getStatusUpdatedTime()
    public void setStatusUpdatedTime(Date statusUpdatedTime) throws IllegalArgumentException
    public String getConnectionState()
    public void setConnectionState(String connectionState) throws IllegalArgumentException
    public Date getConnectionStateUpdatedTime()
    public void setConnectionStateUpdatedTime(Date connectionStateUpdatedTime) throws IllegalArgumentException
    public Date getLastActivityTime()
    public void setLastActivityTime(Date lastActivityTime) throws IllegalArgumentException
    public long getCloudToDeviceMessageCount()
    public void setCloudToDeviceMessageCount(long cloudToDeviceMessageCount) throws IllegalArgumentException
    public AuthenticationParser getAuthenticationParser()
    public void setAuthenticationParser(AuthenticationParser authenticationParser) throws IllegalArgumentException
}
```

### toJson
```java
public String toJson();
```
**SRS_DEVICE_PARSER_34_001: [**This method shall return a json representation of this.**]**


### DeviceParser
```java
public DeviceParser(String json);
```
**SRS_DEVICE_PARSER_34_002: [**This constructor will create a DeviceParser object based off of the provided json.**]**

**SRS_DEVICE_PARSER_34_005: [**If the provided json is null or empty, an IllegalArgumentException shall be thrown.**]**

**SRS_DEVICE_PARSER_34_006: [**If the provided json cannot be parsed into a DeviceParser object, an IllegalArgumentException shall be thrown.**]**

**SRS_DEVICE_PARSER_34_011: [**If the provided json is missing the DeviceId field or its value is empty, an IllegalArgumentException shall be thrown.**]**

**SRS_DEVICE_PARSER_34_012: [**If the provided json is missing the authentication field or its value is empty, an IllegalArgumentException shall be thrown.**]**


### Properties
**SRS_DEVICE_PARSER_34_003: [**This parser has properties for eTag, deviceId, generationId, status, statusReason, statusUpdatedTime, connectionState,
                                connectionStateUpdatedTime, lastActivityTim, cloudToDeviceMessageCount, and authenticationParser.**]**

### setDeviceId
```java
public void setDeviceId(String deviceId)
````
**SRS_DEVICE_PARSER_34_009: [**This method shall set the value of deviceId to the provided value.**]**

**SRS_DEVICE_PARSER_34_010: [**If the provided deviceId value is null, an IllegalArgumentException shall be thrown.**]**


### setAuthenticationParser
```java
public void setAuthenticationParser(AuthenticationParser authenticationParser)
```
**SRS_DEVICE_PARSER_34_007: [**This method shall set the value of authenticationParser to the provided value.**]**

**SRS_DEVICE_PARSER_34_008: [**If the provided authenticationParser value is null, an IllegalArgumentException shall be thrown.**]**