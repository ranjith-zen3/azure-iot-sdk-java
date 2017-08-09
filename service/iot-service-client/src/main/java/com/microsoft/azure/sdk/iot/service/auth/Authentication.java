/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service.auth;

import com.microsoft.azure.sdk.iot.service.Tools;

/**
 * Authentication mechanism used to store the device symmetric key or the X509 Thumbprint.
 */
public class Authentication
{
    private SymmetricKey symmetricKey;
    private X509Thumbprint thumbprint;
    private AuthenticationType type;

    private static final String ILLEGAL_SYMMETRIC_KEY_STRING = "The provided symmetric key cannot be null";
    private static final String ILLEGAL_AUTHENTICATION_TYPE = "The provided authentication type cannot be null";

    /**
     * Constructor that saves a symmetric key used for SAS authentication
     * @throws IllegalArgumentException if the provided symmetricKey is null
     */
    public Authentication(SymmetricKey symmetricKey) throws IllegalArgumentException
    {
        //Codes_SRS_AUTHENTICATION_34_012: [This constructor shall throw an IllegalArgumentException if the provided symmetricKey is null.]
        if (symmetricKey == null)
        {
            throw new IllegalArgumentException(ILLEGAL_SYMMETRIC_KEY_STRING);
        }

        //Codes_SRS_AUTHENTICATION_34_003: [This constructor shall save the provided symmetricKey to the returned instance.]
        this.symmetricKey = symmetricKey;
        this.type = AuthenticationType.sas;
    }

    /**
     * Constructor that saves a thumbprint used for self signed authentication
     */
    public Authentication(String primaryThumbprint, String secondaryThumbprint)
    {
        //Codes_SRS_AUTHENTICATION_34_004: [This constructor shall save the provided thumbprint to the returned instance.]
        this.thumbprint = new X509Thumbprint(primaryThumbprint, secondaryThumbprint);
        this.type = AuthenticationType.selfSigned;
    }

    /**
     * Constructor that is used for certificate authority authentication. Necessary keys will be generated automatically, and can be overwritten later as well.
     * @param authenticationType the type of authentication for this to use.
     */
    public Authentication(AuthenticationType authenticationType)
    {
        this.type = authenticationType;

        if (this.type == AuthenticationType.certificateAuthority)
        {
            //do nothing
        }
        else if (this.type == AuthenticationType.selfSigned)
        {
            this.thumbprint = new X509Thumbprint();
        }
        else if (this.type == AuthenticationType.sas)
        {
            this.symmetricKey = new SymmetricKey();
        }
    }

    /**
     * Getter for symmetric key.
     * @return The symmetric key.
     */
    public SymmetricKey getSymmetricKey()
    {
        //Codes_SRS_AUTHENTICATION_34_005: [This function shall return this object's symmetric key.]
        return this.symmetricKey;
    }

    public String getPrimaryThumbprint()
    {
        if (this.thumbprint == null)
        {
            return null;
        }

        return this.thumbprint.getPrimaryThumbprint();
    }

    public String getSecondaryThumbprint()
    {
        if (this.thumbprint == null)
        {
            return null;
        }

        return this.thumbprint.getSecondaryThumbprint();
    }

    /**
     * Setter for symmetric key.
     * @throws IllegalArgumentException if the provided symmetricKey is null
     */
    public void setSymmetricKey(SymmetricKey symmetricKey) throws IllegalArgumentException
    {
        //Codes_SRS_AUTHENTICATION_34_013: [If the provided symmetricKey is null, this function shall throw an IllegalArgumentException.]
        if (symmetricKey == null)
        {
            throw new IllegalArgumentException(ILLEGAL_SYMMETRIC_KEY_STRING);
        }

        //Codes_SRS_AUTHENTICATION_34_007: [This function shall set this object's symmetric key to the provided value.]
        this.symmetricKey = symmetricKey;

        //Codes_SRS_AUTHENTICATION_34_019: [This function shall set this object's authentication type to sas.]
        this.type = AuthenticationType.sas;
    }

    public void setPrimaryThumbprint(String primaryThumbprint)
    {
        if (this.thumbprint == null)
        {
            this.thumbprint = new X509Thumbprint();
        }

        //Codes_SRS_AUTHENTICATION_34_015: [This function shall set this object's primary thumbprint to the provided value.]
        //Codes_SRS_AUTHENTICATION_34_017: [This function shall set this object's authentication type to SelfSigned.]
        this.thumbprint.setPrimaryThumbprint(primaryThumbprint);
        this.type = AuthenticationType.selfSigned;
    }

    public void setSecondaryThumbprint(String secondaryThumbprint)
    {
        if (this.thumbprint == null)
        {
            this.thumbprint = new X509Thumbprint();
        }

        //Codes_SRS_AUTHENTICATION_34_016: [This function shall set this object's secondary thumbprint to the provided value.]
        //Codes_SRS_AUTHENTICATION_34_018: [This function shall set this object's authentication type to SelfSigned.]
        this.thumbprint.setSecondaryThumbprint(secondaryThumbprint);
        this.type = AuthenticationType.selfSigned;
    }

    /**
     * Getter for authentication type.
     * @return The authentication type.
     */
    public AuthenticationType getAuthenticationType()
    {
        //Codes_SRS_AUTHENTICATION_34_009: [This function shall return the AuthenticationType of this object.]
        return this.type;
    }

    /**
     * Setter for the authentication type of this object
     * @param type the type of authentication to set
     * @throws IllegalArgumentException if the provided type is null
     */
    public void setAuthenticationType(AuthenticationType type) throws IllegalArgumentException
    {
        //Codes_SRS_AUTHENTICATION_34_014: [If the provided type is null, this function shall throw an IllegalArgumentException.]
        if (type == null)
        {
            throw new IllegalArgumentException(ILLEGAL_AUTHENTICATION_TYPE);
        }

        //Codes_SRS_AUTHENTICATION_34_011: [This function shall set this object's authentication type to the provided value.]
        this.type = type;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Authentication)
        {
            Authentication otherAuthentication = (Authentication) other;
            if (this.type != otherAuthentication.type)
            {
                return false;
            }
            else if (!Tools.areEqual(this.symmetricKey, otherAuthentication.symmetricKey))
            {
                return false;
            }
            else if (!Tools.areEqual(this.thumbprint, otherAuthentication.thumbprint))
            {
                return false;
            }

            return true;
        }

        return false;
    }
}
