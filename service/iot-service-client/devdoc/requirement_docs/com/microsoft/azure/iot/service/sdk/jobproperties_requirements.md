# JobProperties Requirements

## Overview

The JobProperties class stores job properties and it is used by the bulk import/export operations.

## References

## Exposed API
public class JobProperties()
{
    public static JobPropertiesParser toJobPropertiesParser(JobProperties jobProperties)
    public static JobProperties fromJobPropertiesParser(JobPropertiesParser parser)
}


```java
public class JobProperties
```

**SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_15_001: [** The JobProperties class has the following properties: JobId,
StartTimeUtc, EndTimeUtc, JobType, JobStatus, Progress, InputBlobContainerUri, OutputBlobContainerUri,
ExcludeKeysInExport, FailureReason **]**


```java
public static JobPropertiesParser toJobPropertiesParser(JobProperties jobProperties)
```

**SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_002: [**This method shall convert the provided jobProperties into a JobPropertiesParser object and return it**]**


```java
public static JobProperties fromJobPropertiesParser(JobPropertiesParser parser)
```

**SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_15_001: [**This method shall convert the provided parser into a JobProperty object and return it**]**
