package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.annotations.SerializedName;

public class AuthenticationParser
{
    @SerializedName("symmetricKey")
    public SymmetricKeyParser symmetricKey;

    @SerializedName("x509Thumbprint")
    public X509ThumbprintParser thumbprint;

    @SerializedName("type")
    public AuthenticationTypeParser type;
}
