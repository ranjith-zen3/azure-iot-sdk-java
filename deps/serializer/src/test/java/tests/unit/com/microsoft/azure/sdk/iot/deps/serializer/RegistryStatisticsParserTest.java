// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.RegistryStatisticsParser;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Code coverage:
 * Methods: 100%
 * Lines: 100%
 */
public class RegistryStatisticsParserTest
{
    //Tests_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_001: [This method shall return a json representation of this.]
    //Tests_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_002: [This constructor shall create and return an instance of a RegistryStatisticsParser object based off the provided json.]
    @Test
    public void testBasicFunctionality()
    {
        // arrange
        RegistryStatisticsParser parser = new RegistryStatisticsParser();
        parser.setTotalDeviceCount(2);
        parser.setEnabledDeviceCount(2);
        parser.setDisabledDeviceCount(0);

        // act
        RegistryStatisticsParser processedParser = new RegistryStatisticsParser(parser.toJson());

        // assert
        assertEquals(parser.getTotalDeviceCount(), processedParser.getTotalDeviceCount());
        assertEquals(parser.getEnabledDeviceCount(), processedParser.getEnabledDeviceCount());
        assertEquals(parser.getDisabledDeviceCount(), processedParser.getDisabledDeviceCount());
    }

    //Tests_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_003: [If the provided json is null, empty, or cannot be parsed into a RegistryStatisticsParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void nullJsonForConstructorThrows()
    {
        new RegistryStatisticsParser(null);
    }

    //Tests_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_003: [If the provided json is null, empty, or cannot be parsed into a RegistryStatisticsParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void emptyJsonForConstructorThrows()
    {
        new RegistryStatisticsParser("");
    }

    //Tests_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_003: [If the provided json is null, empty, or cannot be parsed into a RegistryStatisticsParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void invalidJsonForConstructorThrows()
    {
        new RegistryStatisticsParser("}");
    }
}
