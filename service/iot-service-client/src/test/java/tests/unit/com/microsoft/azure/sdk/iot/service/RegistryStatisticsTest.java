package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.RegistryStatisticsParser;
import com.microsoft.azure.sdk.iot.service.RegistryStatistics;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Code Coverage
 * Methods: 100%
 * Lines: 100%
 */
public class RegistryStatisticsTest
{
    //Tests_SRS_SERVICE_SDK_JAVA_REGISTRY_STATISTICS_34_001: [This method shall convert the provided parser into a RegistryStatistics object and return it]
    @Test
    public void fromRegistryStatisticsParser()
    {
        // arrange
        RegistryStatisticsParser parser = new RegistryStatisticsParser();
        parser.totalDeviceCount = 20;
        parser.enabledDeviceCount = 15;
        parser.disabledDeviceCount = 5;

        // act
        RegistryStatistics registryStatistics = RegistryStatistics.fromRegistryStatisticsParser(parser);

        // assert
        assertEquals(parser.totalDeviceCount, registryStatistics.getTotalDeviceCount());
        assertEquals(parser.enabledDeviceCount, registryStatistics.getEnabledDeviceCount());
        assertEquals(parser.disabledDeviceCount, registryStatistics.getDisabledDeviceCount());
    }
}
