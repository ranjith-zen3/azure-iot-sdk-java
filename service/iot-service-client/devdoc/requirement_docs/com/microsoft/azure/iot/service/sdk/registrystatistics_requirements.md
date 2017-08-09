# RegistryStatistics Requirements

## Overview

The RegistryStatistics class stores statistics regarding the device registry, such as device count.

## References

## Exposed API
public class JobProperties()
{
    public RegistryStatistics(RegistryStatisticsParser parser)
    public RegistryStatisticsParser toRegistryStatisticsParser()
}


### RegistryStatistics
```java
public RegistryStatistics(RegistryStatisticsParser parser)
```

**SRS_SERVICE_SDK_JAVA_REGISTRY_STATISTICS_34_001: [**This method shall convert the provided parser into a RegistryStatistics object and return it.**]**


### toRegistryStatisticsParser
```java
public RegistryStatisticsParser toRegistryStatisticsParser()
```

**SRS_SERVICE_SDK_JAVA_REGISTRY_STATISTICS_34_002: [**This method shall convert this into a RegistryStatisticsParser object and return it.**]**