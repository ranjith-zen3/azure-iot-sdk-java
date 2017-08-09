// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticationParser
{
    private static final String SYMMETRIC_KEY_NAME = "symmetricKey";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(SYMMETRIC_KEY_NAME)
    private SymmetricKeyParser symmetricKey;

    private static final String X509_THUMBPRINT_NAME = "x509Thumbprint";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(X509_THUMBPRINT_NAME)
    private X509ThumbprintParser thumbprint;

    private static final String AUTHENTICATION_TYPE_NAME = "type";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(AUTHENTICATION_TYPE_NAME)
    private AuthenticationTypeParser type;

    /**
     * Empty constructor: Used only to keep GSON happy.
     */
    @SuppressWarnings("unused")
    public AuthenticationParser()
    {
        //Codes_SRS_AUTHENTICATION_PARSER_34_001: [This constructor creates a new AuthenticationParser object with an authentication type of certificate authority.]
        this.setType(AuthenticationTypeParser.certificateAuthority);
    }

    /**
     * Getter for SymmetricKey
     */
    public SymmetricKeyParser getSymmetricKey()
    {
        return symmetricKey;
    }

    /**
     * Setter for SymmetricKey
     */
    public void setSymmetricKey(SymmetricKeyParser symmetricKey)
    {
        this.symmetricKey = symmetricKey;
    }

    /**
     * Getter for Thumbprint
     */
    public X509ThumbprintParser getThumbprint()
    {
        return thumbprint;
    }

    /**
     * Setter for Thumbprint
     */
    public void setThumbprint(X509ThumbprintParser thumbprint)
    {
        this.thumbprint = thumbprint;
    }

    /**
     * Getter for Type
     */
    public AuthenticationTypeParser getType()
    {
        return type;
    }

    /**
     * Setter for Type
     * @throws IllegalArgumentException if the provided type is null
     */
    public void setType(AuthenticationTypeParser type) throws IllegalArgumentException
    {
        //Codes_SRS_AUTHENTICATION_PARSER_34_003: [If the provided type is null, an IllegalArgumentException shall be thrown.]
        if (type == null)
        {
            throw new IllegalArgumentException("Type may not be set to null");
        }

        this.type = type;
    }
}
