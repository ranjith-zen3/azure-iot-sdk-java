# JobPropertiesParser Requirements

## Overview

Representation of a JobProperties object with a Json deserializer and serializer.

## References


## Exposed API

```java
public class JobPropertiesParser
{
    public static JobPropertiesParser fromJson(String json);
    public static String toJson(JobPropertiesParser jobPropertiesParser);
}
```

### toJson
```java
public static String toJson(JobPropertiesParser jobPropertiesParser);
```
**SRS_JOB_PROPERTIES_PARSER_34_001: [**The parser will return a json representation of the provided jobPropertiesParser**]**


### fromJson
```java
public static JobPropertiesParser fromJson(String json);
```
**SRS_JOB_PROPERTIES_PARSER_34_002: [**The parser will create and return an instance of a JobPropertiesParser object based off the provided json**]**