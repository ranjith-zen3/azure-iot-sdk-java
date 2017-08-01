package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationTypeParser
{
    @SerializedName("sas")
    sas,

    @SerializedName("selfSigned")
    selfSigned,

    @SerializedName("certificateAuthority")
    certificateAuthority;
}
