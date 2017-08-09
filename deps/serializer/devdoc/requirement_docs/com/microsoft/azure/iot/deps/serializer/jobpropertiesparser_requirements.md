# JobPropertiesParser Requirements

## Overview

Representation of a JobProperties object with a Json deserializer and serializer.

## References


## Exposed API

```java
public class JobPropertiesParser
{
    public JobPropertiesParser(String json)
    public String toJson()

    public String getType()
    public void setType(String type)
    public String getInputBlobContainerUri()
    public void setInputBlobContainerUri(String inputBlobContainerUri)
    public String getOutputBlobContainerUri()
    public void setOutputBlobContainerUri(String outputBlobContainerUri)
    public String getJobId()
    public void setJobId(String jobId)
    public Date getStartTimeUtc()
    public void setStartTimeUtc(Date startTimeUtc)
    public Date getEndTimeUtc()
    public void setEndTimeUtc(Date endTimeUtc)
    public String getStatus()
    public void setStatus(String status)
    public int getProgress()
    public void setProgress(int progress)
    public boolean isExcludeKeysInExport()
    public void setExcludeKeysInExport(boolean excludeKeysInExport)
    public String getFailureReason()
    public void setFailureReason(String failureReason)
}
```

### JobPropertiesParser
```java
public JobPropertiesParser(String json)
```
**SRS_JOB_PROPERTIES_PARSER_34_001: [**The constructor shall create and return an instance of a JobPropertiesParser object based off the provided json.**]**

**SRS_JOB_PROPERTIES_PARSER_34_007: [**If the provided json is null or empty, an IllegalArgumentException shall be thrown.**]**

**SRS_JOB_PROPERTIES_PARSER_34_008: [**If the provided json cannot be parsed into a JobPropertiesParser object, an IllegalArgumentException shall be thrown.**]**

**SRS_JOB_PROPERTIES_PARSER_34_009: [**If the provided json is missing the field for jobId, or if its value is null or empty, an IllegalArgumentException shall be thrown.**]**


### toJson
```java
public String toJson()
```
**SRS_JOB_PROPERTIES_PARSER_34_002: [**This method shall return a json representation of this.**]**


### Properties

**SRS_JOB_PROPERTIES_PARSER_34_003: [**JobPropertiesParser contains properties for InputBlobContainerUri, Type, Status,
    JobId, ExcludeKeysInExport, Progress, OutputBlobContainerUri, FailureReason, EndTimeUtc, and StartTimeUtc.**]**


### setOutputBlobContainerUri

```java
public void setType(String type)
```
**SRS_JOB_PROPERTIES_PARSER_34_004: [**If the provided OutputBlobContainerUri is null or empty, an IllegalArgumentException shall be thrown.**]**


### setJobId

```java
public void setJobId(String jobId)
```
**SRS_JOB_PROPERTIES_PARSER_34_005: [**If the provided jobId is null, an IllegalArgumentException shall be thrown.**]**