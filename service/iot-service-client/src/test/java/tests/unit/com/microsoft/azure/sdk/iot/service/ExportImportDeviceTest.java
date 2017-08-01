package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.*;
import mockit.Deencapsulation;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Code coverage:
 * 93% Methods, 88% lines
 */
public class ExportImportDeviceTest
{
    // Tests_SRS_SERVICE_SDK_JAVA_IMPORT_EXPORT_DEVICE_15_001: [The ExportImportDevice class has the following properties: Id, Etag, ImportMode, Status, StatusReason, Authentication]
    @Test
    public void gettersAndSettersWork()
    {
        // arrange
        ExportImportDevice device = Deencapsulation.newInstance(ExportImportDevice.class, new Class[]{});
        Authentication expectedAuthentication = new Authentication(AuthenticationType.certificateAuthority);
        String expectedETag = "etag";
        String expectedId = "id";
        ImportMode expectedImportMode = ImportMode.Create;
        DeviceStatus expectedStatus = DeviceStatus.Disabled;
        String expectedStatusReason = "test";

        // act
        device.setAuthentication(expectedAuthentication);
        device.setETag(expectedETag);
        device.setId(expectedId);
        device.setImportMode(expectedImportMode);
        device.setStatus(expectedStatus);
        device.setStatusReason(expectedStatusReason);

        // assert
        assertEquals(expectedAuthentication, device.getAuthentication());
        assertEquals(expectedETag, device.getETag());
        assertEquals(expectedId, device.getId());
        assertEquals(expectedImportMode, device.getImportMode());
        assertEquals(expectedStatus, device.getStatus());
        assertEquals(expectedStatusReason, device.getStatusReason());
    }

    @Test
    public void equalsWorks()
    {
        ExportImportDevice device1 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.Create, DeviceStatus.Disabled);
        ExportImportDevice device2 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.Create, DeviceStatus.Disabled);
        ExportImportDevice device3 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.CreateOrUpdate, DeviceStatus.Disabled);
        ExportImportDevice device4 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.Create, DeviceStatus.Enabled);

        assertEquals(device1, device2);
        assertNotEquals(device1, device3);
        assertNotEquals(device1, device4);
        assertNotEquals(device1, 1);
    }

    private ExportImportDevice createTestDevice(Authentication authentication, ImportMode importMode, DeviceStatus status)
    {
        ExportImportDevice device = Deencapsulation.newInstance(ExportImportDevice.class, new Class[]{});
        device.setAuthentication(authentication);
        device.setImportMode(importMode);
        device.setStatus(status);

        return device;
    }

    @Test
    public void conversionToDeviceParser()
    {
        // arrange
        ExportImportDevice deviceCA = new ExportImportDevice();
        deviceCA.setId("deviceCA");
        deviceCA.setAuthentication(new Authentication(AuthenticationType.certificateAuthority));

        ExportImportDevice deviceSelf = new ExportImportDevice();
        deviceSelf.setId("deviceSelf");
        deviceSelf.setAuthentication(new Authentication(AuthenticationType.selfSigned));

        ExportImportDevice deviceSAS = new ExportImportDevice();
        deviceSAS.setId("deviceSAS");
        deviceSAS.setAuthentication(new Authentication(AuthenticationType.sas));

        // act
        ExportImportDeviceParser parserCA = ExportImportDevice.toExportImportDeviceParser(deviceCA);
        ExportImportDeviceParser parserSelf = ExportImportDevice.toExportImportDeviceParser(deviceSelf);
        ExportImportDeviceParser parserSAS = ExportImportDevice.toExportImportDeviceParser(deviceSAS);

        // assert
        assertEquals(AuthenticationTypeParser.certificateAuthority, parserCA.Authentication.type);
        assertEquals(AuthenticationTypeParser.selfSigned, parserSelf.Authentication.type);
        assertEquals(AuthenticationTypeParser.sas, parserSAS.Authentication.type);
    }

    @Test
    public void conversionFromDeviceParser()
    {
        // arrange
        ExportImportDeviceParser parserCA = new ExportImportDeviceParser();
        parserCA.Authentication = new AuthenticationParser();
        parserCA.Authentication.type = AuthenticationTypeParser.certificateAuthority;
        parserCA.Id = "deviceCA";

        ExportImportDeviceParser parserSelf = new ExportImportDeviceParser();
        parserSelf.Authentication = new AuthenticationParser();
        parserSelf.Authentication.type = AuthenticationTypeParser.selfSigned;
        parserSelf.Authentication.thumbprint = new X509ThumbprintParser("0000000000000000000000000000000000000000","0000000000000000000000000000000000000000");
        parserSelf.Id = "deviceSelf";

        ExportImportDeviceParser parserSAS = new ExportImportDeviceParser();
        parserSAS.Authentication = new AuthenticationParser();
        parserSAS.Authentication.type = AuthenticationTypeParser.sas;
        parserSAS.Authentication.symmetricKey = new SymmetricKeyParser("000000000000000000000000","000000000000000000000000");
        parserSAS.Id = "deviceSAS";

        // act
        ExportImportDevice deviceCA = ExportImportDevice.fromExportImportDeviceParser(parserCA);
        ExportImportDevice deviceSelf = ExportImportDevice.fromExportImportDeviceParser(parserSelf);
        ExportImportDevice deviceSAS = ExportImportDevice.fromExportImportDeviceParser(parserSAS);

        // assert
        assertEquals(AuthenticationType.certificateAuthority, deviceCA.getAuthentication().getAuthenticationType());
        assertEquals(AuthenticationType.selfSigned, deviceSelf.getAuthentication().getAuthenticationType());
        assertEquals(AuthenticationType.sas, deviceSAS.getAuthentication().getAuthenticationType());
    }
}
