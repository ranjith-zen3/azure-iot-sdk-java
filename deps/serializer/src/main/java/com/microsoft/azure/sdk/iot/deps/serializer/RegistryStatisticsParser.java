package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;

public class RegistryStatisticsParser
{
    private static Gson gson = new Gson();

    public long totalDeviceCount;
    public long enabledDeviceCount;
    public long disabledDeviceCount;

    public static String toJson(RegistryStatisticsParser registryStatisticsParser)
    {
        //Codes_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_001: [The parser will return a json representation of the provided RegistryStatisticsParser]
        return gson.toJson(registryStatisticsParser);
    }

    public static RegistryStatisticsParser fromJson(String json)
    {
        //Codes_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_002: [The parser will create and return an instance of a RegistryStatisticsParser object based off the provided json]
        return gson.fromJson(json, RegistryStatisticsParser.class);
    }
}
