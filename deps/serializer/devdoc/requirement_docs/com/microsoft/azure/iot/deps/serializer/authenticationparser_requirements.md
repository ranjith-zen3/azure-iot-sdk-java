# AuthenticationParser Requirements

## Overview

Representation of an Authentication object used for Json deserialization and serialization.

## References


## Exposed API

```java
public class AuthenticationParser
{
    public AuthenticationParser();

    public SymmetricKeyParser getSymmetricKey()
    public void setSymmetricKey(SymmetricKeyParser symmetricKey)
    public X509ThumbprintParser getThumbprint()
    public void setThumbprint(X509ThumbprintParser thumbprint)
    public AuthenticationTypeParser getType()
    public void setType(AuthenticationTypeParser type) throws IllegalArgumentException
}
```


### AuthenticationParser
```java
public AuthenticationParser();
```
**SRS_AUTHENTICATION_PARSER_34_001: [**This constructor creates a new AuthenticationParser object with an authentication type of certificate authority.**]**


### Properties
**SRS_AUTHENTICATION_PARSER_34_002: [**This class has properties for its SymmetricKeyParser, X509ThumbprintParser, and AuthenticationTypeParser.**]**


### setType
```java
public void setType(AuthenticationTypeParser type)
````
**SRS_AUTHENTICATION_PARSER_34_003: [**If the provided type is null, an IllegalArgumentException shall be thrown.**]**
