# Authentication Requirements

## Overview

Holds information used by a device for authentication when it is created by the registry manager

## References

## Exposed API

```java
public class X509Thumbprint
{
    public Authentication(AuthenticationType authType);
    public Authentication(SymmetricKey symmetricKey);
    public Authentication(String primaryThumbprint, String secondaryThumbprint)

    public SymmetricKey getSymmetricKey();
    public X509Thumbprint getThumbprint();
    public void setSymmetricKey(SymmetricKey symmetricKey);
    public void setPrimaryThumbprint(String primaryThumbprint);
    public void setSecondaryThumbprint(String secondaryThumbprint);

    public AuthenticationType getAuthenticationType();
    public void setAuthenticationType(AuthenticationType type);
}
```

### Authentication
```java
public Authentication(SymmetricKey symmetricKey);
```
**SRS_AUTHENTICATION_34_003: [**This constructor shall save the provided symmetricKey to the returned instance.**]**
**SRS_AUTHENTICATION_34_012: [**This constructor shall throw an IllegalArgumentException if the provided symmetricKey is null.**]**


```java
public Authentication(String primaryThumbprint, String secondaryThumbprint)
```
**SRS_AUTHENTICATION_34_004: [**This constructor shall save the provided thumbprint to the returned instance.**]**

```java
public Authentication();
```
**SRS_AUTHENTICATION_34_010: [**This constructor shall not save a thumbprint or a symmetric key.**]**


### getSymmetricKey
```java
public SymmetricKey getSymmetricKey();
```
**SRS_AUTHENTICATION_34_005: [**This function shall return this object's symmetric key.**]**


### getThumbprint
```java
public X509Thumbprint getThumbprint();
```
**SRS_AUTHENTICATION_34_006: [**This function shall return this object's thumbprint.**]**


### setSymmetricKey
```java
public void setSymmetricKey(SymmetricKey symmetricKey);
```
**SRS_AUTHENTICATION_34_007: [**This function shall set this object's symmetric key to the provided value.**]**
**SRS_AUTHENTICATION_34_013: [**If the provided symmetricKey is null, this function shall throw an IllegalArgumentException.**]**
**SRS_AUTHENTICATION_34_019: [**This function shall set this object's authentication type to sas.**]**


### setPrimaryThumbprint
```java
public void setPrimaryThumbprint(String primaryThumbprint);
```
**SRS_AUTHENTICATION_34_015: [**This function shall set this object's primary thumbprint to the provided value.**]**
**SRS_AUTHENTICATION_34_017: [**This function shall set this object's authentication type to SelfSigned.**]**


### setSecondaryThumbprint
```java
public void setSecondaryThumbprint(String secondaryThumbprint);
```
**SRS_AUTHENTICATION_34_016: [**This function shall set this object's secondary thumbprint to the provided value.**]**
**SRS_AUTHENTICATION_34_018: [**This function shall set this object's authentication type to SelfSigned.**]**


### getAuthenticationType
```java
public AuthenticationType getAuthenticationType();
```
**SRS_AUTHENTICATION_34_009: [**This function shall return the AuthenticationType of this object.**]**


### setAuthenticationType
```java
public void setAuthenticationType(AuthenticationType type);
```
**SRS_AUTHENTICATION_34_011: [**This function shall set this object's authentication type to the provided value.**]**
**SRS_AUTHENTICATION_34_014: [**If the provided type is null, this function shall throw an IllegalArgumentException.**]**