# RegistryStatisticsParser Requirements

## Overview

Representation of a RegistryStatistics object with a Json deserializer and serializer.

## References


## Exposed API

```java
public class JobPropertiesParser
{
    public static RegistryStatisticsParser fromJson(String json);
    public static String toJson(RegistryStatisticsParser jobPropertiesParser);
}
```

### toJson
```java
public static String toJson(RegistryStatisticsParser jobPropertiesParser);
```
**SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_001: [**The parser will return a json representation of the provided RegistryStatisticsParser**]**


### fromJson
```java
public static RegistryStatisticsParser fromJson(String json);
```
**SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_002: [**The parser will create and return an instance of a RegistryStatisticsParser object based off the provided json**]**