// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistryStatisticsParser
{
    private static Gson gson = new Gson();

    private static final String TOTAL_DEVICE_COUNT_NAME = "totalDeviceCount";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(TOTAL_DEVICE_COUNT_NAME)
    private long totalDeviceCount;

    private static final String ENABLED_DEVICE_COUNT_NAME = "enableDeviceCount";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(ENABLED_DEVICE_COUNT_NAME)
    private long enabledDeviceCount;

    private static final String DISABLED_DEVICE_COUNT_NAME = "disabledDeviceCount";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(DISABLED_DEVICE_COUNT_NAME)
    private long disabledDeviceCount;

    private static final String NULL_OR_EMPTY_JSON_ERROR_MESSAGE = "The provided json cannot be null or empty";
    private static final String INVALID_JSON_ERROR_MESSAGE = "The provided json could not be parsed";


    /**
     * Empty constructor: Used only to keep GSON happy.
     */
    @SuppressWarnings("unused")
    public RegistryStatisticsParser()
    {
        this.totalDeviceCount = 0;
        this.enabledDeviceCount = 0;
        this.disabledDeviceCount = 0;
    }

    /**
     * Constructor for a RegistryStatisticsParser that is constructed from Json.
     * @param json the json to build from.
     */
    public RegistryStatisticsParser(String json)
    {
        if (json == null || json.isEmpty())
        {
            //Codes_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_003: [If the provided json is null, empty, or cannot be parsed into a RegistryStatisticsParser object, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(NULL_OR_EMPTY_JSON_ERROR_MESSAGE);
        }

        RegistryStatisticsParser parser;
        try
        {
            //Codes_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_002: [This constructor shall create and return an instance of a RegistryStatisticsParser object based off the provided json.]
            parser = gson.fromJson(json, RegistryStatisticsParser.class);
        }
        catch (Exception e)
        {
            //Codes_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_003: [If the provided json is null, empty, or cannot be parsed into a RegistryStatisticsParser object, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(INVALID_JSON_ERROR_MESSAGE);
        }

        this.totalDeviceCount = parser.totalDeviceCount;
        this.enabledDeviceCount = parser.enabledDeviceCount;
        this.disabledDeviceCount = parser.disabledDeviceCount;
    }

    /**
     * Converts this into a json string.
     * @return the json representation of this.
     */
    public String toJson()
    {
        //Codes_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_001: [This method shall return a json representation of this.]
        return gson.toJson(this);
    }

    /**
     * Getter for TotalDeviceCount
     */
    public long getTotalDeviceCount()
    {
        return totalDeviceCount;
    }

    /**
     * Setter for TotalDeviceCount
     */
    public void setTotalDeviceCount(long totalDeviceCount)
    {
        this.totalDeviceCount = totalDeviceCount;
    }

    /**
     * Getter for EnabledDeviceCount
     */
    public long getEnabledDeviceCount()
    {
        return enabledDeviceCount;
    }

    /**
     * Setter for EnabledDeviceCount
     */
    public void setEnabledDeviceCount(long enabledDeviceCount)
    {
        this.enabledDeviceCount = enabledDeviceCount;
    }

    /**
     * Getter for DisabledDeviceCount
     */
    public long getDisabledDeviceCount()
    {
        return disabledDeviceCount;
    }

    /**
     * Setter for DisabledDeviceCount
     */
    public void setDisabledDeviceCount(long disabledDeviceCount)
    {
        this.disabledDeviceCount = disabledDeviceCount;
    }
}
