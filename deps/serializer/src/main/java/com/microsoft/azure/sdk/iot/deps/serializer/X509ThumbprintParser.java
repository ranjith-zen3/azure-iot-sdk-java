package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.annotations.SerializedName;

public class X509ThumbprintParser
{
    @SerializedName("primaryThumbprint")
    public String primaryThumbprint;

    @SerializedName("secondaryThumbprint")
    public String secondaryThumbprint;

    public X509ThumbprintParser(String primaryThumbprint, String secondaryThumbprint)
    {
        this.primaryThumbprint = primaryThumbprint;
        this.secondaryThumbprint = secondaryThumbprint;
    }
}
