/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;
import com.microsoft.azure.sdk.iot.service.auth.X509Thumbprint;

import java.security.NoSuchAlgorithmException;

/**
 * Authentication mechanism used to store the device symmetric key or the X509 Thumbprint.
 */
public class Authentication
{
    @SerializedName("symmetricKey")
    private SymmetricKey symmetricKey;

    @SerializedName("x509Thumbprint")
    private X509Thumbprint thumbprint;

    @SerializedName("type")
    private AuthenticationType type;

    /**
     * Constructor that saves a symmetric key used for SAS authentication
     */
    public Authentication(SymmetricKey symmetricKey)
    {
        //Codes_SRS_AUTHENTICATION_34_003: [This constructor will save the provided symmetricKey to the returned instance.]
        this.symmetricKey = symmetricKey;
        this.type = AuthenticationType.sas;
    }

    /**
     * Constructor that saves a thumbprint used for self signed authentication
     */
    public Authentication(X509Thumbprint thumbprint)
    {
        //Codes_SRS_AUTHENTICATION_34_004: [This constructor will save the provided thumbprint to the returned instance.]
        this.thumbprint = thumbprint;
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

    /**
     * Getter for x509 thumbprint.
     * @return The x509 thumbprint.
     */
    public X509Thumbprint getThumbprint()
    {
        //Codes_SRS_AUTHENTICATION_34_006: [This function shall return this object's thumbprint.]
        return this.thumbprint;
    }

    /**
     * Setter for symmetric key.
     */
    public void setSymmetricKey(SymmetricKey symmetricKey)
    {
        //Codes_SRS_AUTHENTICATION_34_007: [This function shall set this object's symmetric key to the provided value.]
        this.symmetricKey = symmetricKey;
        this.type = AuthenticationType.sas;
    }

    /**
     * Setter for x509 certificate.
     */
    public void setThumbprint(X509Thumbprint thumbprint)
    {
        //Codes_SRS_AUTHENTICATION_34_008: [This function shall set this object's thumbprint to the provided value.]
        this.thumbprint = thumbprint;
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
     */
    public void setAuthenticationType(AuthenticationType type)
    {
        //Codes_SRS_AUTHENTICATION_34_011: [This function shall set this object's authentication type to the provided value.]
        this.type = type;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof  Authentication)
        {
            Authentication other = (Authentication) o;
            if (this.type != other.type)
            {
                return false;
            }
            else if (!Tools.areEqual(this.symmetricKey, other.symmetricKey))
            {
                return false;
            }
            else if (!Tools.areEqual(this.thumbprint, other.thumbprint))
            {
                return false;
            }

            return true;
        }

        return false;
    }
}
