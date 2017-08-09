/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.RegistryStatisticsParser;

public class RegistryStatistics
{
    private long totalDeviceCount;
    private long enabledDeviceCount;
    private long disabledDeviceCount;

    private RegistryStatistics()
    {
        this.totalDeviceCount = 0;
        this.enabledDeviceCount = 0;
        this.disabledDeviceCount = 0;
    }

    public long getDisabledDeviceCount()
    {
        return disabledDeviceCount;
    }

    public long getEnabledDeviceCount()
    {
        return enabledDeviceCount;
    }

    public long getTotalDeviceCount()
    {
        return totalDeviceCount;
    }

    /**
     * Consructs a RegistryStatisics object based off of a RegistryStatisticsParser object
     * @param registryStatisticsParser the object to base the constructed object on
     */
    public RegistryStatistics(RegistryStatisticsParser registryStatisticsParser)
    {
        //Codes_SRS_SERVICE_SDK_JAVA_REGISTRY_STATISTICS_34_001: [This method shall convert the provided parser into a RegistryStatistics object and return it.]
        this.totalDeviceCount = registryStatisticsParser.getTotalDeviceCount();
        this.enabledDeviceCount = registryStatisticsParser.getEnabledDeviceCount();
        this.disabledDeviceCount = registryStatisticsParser.getDisabledDeviceCount();
    }

    /**
     * Converts this into a RegistryStatisticsParser object that can be used for serialization and deserialization
     * @return the created RegistryStatisticsParser object
     */
    public RegistryStatisticsParser toRegistryStatisticsParser()
    {
        //Codes_SRS_SERVICE_SDK_JAVA_REGISTRY_STATISTICS_34_002: [This method shall convert this into a RegistryStatisticsParser object and return it.]
        RegistryStatisticsParser parser = new RegistryStatisticsParser();
        parser.setTotalDeviceCount(this.totalDeviceCount);
        parser.setEnabledDeviceCount(this.enabledDeviceCount);
        parser.setDisabledDeviceCount(this.disabledDeviceCount);

        return parser;
    }
}
