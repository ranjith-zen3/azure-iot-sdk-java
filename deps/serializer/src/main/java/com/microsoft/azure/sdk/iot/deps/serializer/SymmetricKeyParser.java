/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.annotations.SerializedName;

/**
 * Store primary and secondary keys
 * Provide function for key length validation
 */
public class SymmetricKeyParser
{
    @SerializedName("primaryKey")
    public String primaryKey;

    @SerializedName("secondaryKey")
    public String secondaryKey;

    public SymmetricKeyParser(String primaryKey, String secondaryKey)
    {
        this.primaryKey = primaryKey;
        this.secondaryKey = secondaryKey;
    }
}
