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
    //Tests_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_001: [The parser will return a json representation of the provided RegistryStatisticsParser]
    //Tests_SRS_REGISTRY_STATISTICS_PROPERTIES_PARSER_34_002: [The parser will create and return an instance of a RegistryStatisticsParser object based off the provided json]
    @Test
    public void testBasicFunctionality()
    {
        // arrange
        RegistryStatisticsParser parser = new RegistryStatisticsParser();
        parser.totalDeviceCount = 2;
        parser.enabledDeviceCount = 2;
        parser.disabledDeviceCount = 0;

        // act
        RegistryStatisticsParser processedParser = RegistryStatisticsParser.fromJson(RegistryStatisticsParser.toJson(parser));

        // assert
        assertEquals(parser.totalDeviceCount, processedParser.totalDeviceCount);
        assertEquals(parser.enabledDeviceCount, processedParser.enabledDeviceCount);
        assertEquals(parser.disabledDeviceCount, processedParser.disabledDeviceCount);
    }
}
