package com.microsoft.azure.sdk.iot.service;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationType
{
    @SerializedName("sas")
    sas,

    @SerializedName("selfSigned")
    selfSigned,

    @SerializedName("certificateAuthority")
    certificateAuthority;
}
