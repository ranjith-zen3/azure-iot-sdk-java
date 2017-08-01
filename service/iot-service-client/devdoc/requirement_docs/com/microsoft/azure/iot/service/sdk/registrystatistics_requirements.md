# RegistryStatistics Requirements

## Overview

The RegistryStatistics class stores statistics regarding the device registry, such as device count.

## References

## Exposed API
public class JobProperties()
{
    public static RegistryStatistics fromRegistryStatisticsParser(RegistryStatisticsParser parser)
}


```java
public static RegistryStatistics fromRegistryStatisticsParser(RegistryStatisticsParser parser)
```

**SRS_SERVICE_SDK_JAVA_REGISTRY_STATISTICS_34_001: [**This method shall convert the provided parser into a RegistryStatistics object and return it**]**
