# JobProperties Requirements

## Overview

The JobProperties class stores job properties and it is used by the bulk import/export operations.

## References

## Exposed API
public class JobProperties()
{
    public JobProperties();

    public String getJobId();
    public void setJobId(String jobId) throws IllegalArgumentException;
    public Date getStartTimeUtc();
    public void setStartTimeUtc(Date startTimeUtc);
    public Date getEndTimeUtc();
    public void setEndTimeUtc(Date endTimeUtc);
    public JobType getType();
    public void setType(JobType type);
    public JobStatus getStatus();
    public void setStatus(JobStatus status);
    public int getProgress();
    public void setProgress(int progress);
    public String getInputBlobContainerUri();
    public void setInputBlobContainerUri(String inputBlobContainerUri);
    public String getOutputBlobContainerUri();
    public void setOutputBlobContainerUri(String outputBlobContainerUri);
    public boolean getExcludeKeysInExport();
    public void setExcludeKeysInExport(boolean excludeKeysInExport);
    public String getFailureReason();
    public void setFailureReason(String failureReason);

    public JobProperties(JobPropertiesParser parser);
    public JobPropertiesParser toJobPropertiesParser();
}


```java
public class JobProperties
```

**SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_15_001: [** The JobProperties class has the following properties: JobId,
StartTimeUtc, EndTimeUtc, JobType, JobStatus, Progress, InputBlobContainerUri, OutputBlobContainerUri,
ExcludeKeysInExport, FailureReason **]**


```java
public JobPropertiesParser toJobPropertiesParser();
```

**SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_002: [**This method shall convert this into a JobPropertiesParser object and return it.**]**


```java
public JobProperties(JobPropertiesParser parser);
```

**SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_003: [**This method shall convert the provided parser into a JobProperty object and return it.**]**

```java
public void setJobId(String jobId);
```
**SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_004: [**If the provided jobId is null, an IllegalArgumentException shall be thrown.**]**