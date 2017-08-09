# RegistryStatisticsParser Requirements

## Overview

Representation of a RegistryStatistics object with a Json deserializer and serializer.

## References


## Exposed API

```java
public class RegistryStatisticsParser
{
    public RegistryStatisticsParser(String json);
    public String toJson();
}
```

### toJson
```java
public String toJson();
```
**SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_001: [**This method shall return a json representation of this.**]**


### fromJson
```java
public RegistryStatisticsParser(String json);
```
**SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_002: [**This constructor shall create and return an instance of a RegistryStatisticsParser object based off the provided json.**]**

**SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_003: [**If the provided json is null, empty, or cannot be parsed into a RegistryStatisticsParser object, an IllegalArgumentException shall be thrown.**]**