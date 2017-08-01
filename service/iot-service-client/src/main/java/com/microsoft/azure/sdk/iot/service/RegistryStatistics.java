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

    public static RegistryStatistics fromRegistryStatisticsParser(RegistryStatisticsParser registryStatisticsParser)
    {
        //Codes_SRS_SERVICE_SDK_JAVA_REGISTRY_STATISTICS_34_001: [This method shall convert the provided parser into a RegistryStatistics object and return it]
        RegistryStatistics registryStatistics = new RegistryStatistics();
        registryStatistics.totalDeviceCount = registryStatisticsParser.totalDeviceCount;
        registryStatistics.enabledDeviceCount = registryStatisticsParser.enabledDeviceCount;
        registryStatistics.disabledDeviceCount = registryStatisticsParser.disabledDeviceCount;

        return registryStatistics;
    }
}
