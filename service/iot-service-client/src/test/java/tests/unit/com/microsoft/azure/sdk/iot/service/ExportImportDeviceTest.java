// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.DeviceStatus;
import com.microsoft.azure.sdk.iot.service.ExportImportDevice;
import com.microsoft.azure.sdk.iot.service.ImportMode;
import com.microsoft.azure.sdk.iot.service.auth.Authentication;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import mockit.Deencapsulation;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Code coverage:
 * 94% Methods
 * 96% lines
 */
public class ExportImportDeviceTest
{
    private static final String SAMPLE_THUMBPRINT = "0000000000000000000000000000000000000000";

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

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_050: [This constructor will automatically set the authentication type of this object to be sas, and will generate a deviceId and symmetric key.]
    @Test
    public void emptyConstructorGeneratesDeviceIdSymmetricKeyAndAuthType()
    {
        ExportImportDevice device = new ExportImportDevice();

        assertNotNull(device.getAuthentication());
        assertNotNull(device.getAuthentication().getSymmetricKey());
        assertEquals(AuthenticationType.sas, device.getAuthentication().getAuthenticationType());
    }

    @Test
    public void equalsWorks()
    {
        ExportImportDevice device1 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.Create, DeviceStatus.Disabled);
        ExportImportDevice device2 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.Create, DeviceStatus.Disabled);
        ExportImportDevice device3 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.CreateOrUpdate, DeviceStatus.Disabled);
        ExportImportDevice device4 = createTestDevice(new Authentication(AuthenticationType.certificateAuthority), ImportMode.Create, DeviceStatus.Enabled);
        ExportImportDevice device5 = createTestDevice(new Authentication(AuthenticationType.selfSigned), ImportMode.Create, DeviceStatus.Enabled);

        assertEquals(device1, device2);
        assertNotEquals(device1, device3);
        assertNotEquals(device1, device4);
        assertNotEquals(device1, device5);
        assertNotEquals(device1, 1);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_054: [This method shall convert this into an ExportImportDeviceParser object and return it.]
    @Test
    public void conversionToExportImportDeviceParser()
    {
        // arrange
        ExportImportDevice deviceCA = new ExportImportDevice();
        deviceCA.setId("deviceCA");
        deviceCA.setAuthentication(new Authentication(AuthenticationType.certificateAuthority));
        deviceCA.setImportMode(ImportMode.CreateOrUpdate);
        deviceCA.setStatus(DeviceStatus.Enabled);

        ExportImportDevice deviceSelf = new ExportImportDevice();
        deviceSelf.setId("deviceSelf");
        deviceSelf.setAuthentication(new Authentication(AuthenticationType.selfSigned));

        ExportImportDevice deviceSAS = new ExportImportDevice();
        deviceSAS.setId("deviceSAS");
        deviceSAS.setAuthentication(new Authentication(AuthenticationType.sas));

        // act
        ExportImportDeviceParser parserCA = deviceCA.toExportImportDeviceParser();
        ExportImportDeviceParser parserSelf = deviceSelf.toExportImportDeviceParser();
        ExportImportDeviceParser parserSAS = deviceSAS.toExportImportDeviceParser();

        // assert
        assertEquals(AuthenticationTypeParser.certificateAuthority, parserCA.getAuthentication().getType());
        assertEquals(AuthenticationTypeParser.selfSigned, parserSelf.getAuthentication().getType());
        assertEquals(AuthenticationTypeParser.sas, parserSAS.getAuthentication().getType());

        assertEquals("CreateOrUpdate", parserCA.getImportMode());
        assertEquals("Enabled", parserCA.getStatus());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_052: [This constructor will use the properties of the provided parser object to set the new ExportImportDevice's properties.]
    @Test
    public void conversionFromDeviceParser()
    {
        // arrange
        ExportImportDeviceParser parserCA = new ExportImportDeviceParser();
        parserCA.setAuthentication(new AuthenticationParser());
        parserCA.getAuthentication().setType(AuthenticationTypeParser.certificateAuthority);
        parserCA.setStatus("Enabled");
        parserCA.setImportMode("Create");
        parserCA.setId("deviceCA");

        ExportImportDeviceParser parserSelf = new ExportImportDeviceParser();
        parserSelf.setAuthentication(new AuthenticationParser());
        parserSelf.getAuthentication().setType(AuthenticationTypeParser.selfSigned);
        parserSelf.getAuthentication().setThumbprint(new X509ThumbprintParser(SAMPLE_THUMBPRINT, SAMPLE_THUMBPRINT));
        parserSelf.setId("deviceSelf");

        ExportImportDeviceParser parserSAS = new ExportImportDeviceParser();
        parserSAS.setAuthentication(new AuthenticationParser());
        parserSAS.getAuthentication().setType(AuthenticationTypeParser.sas);
        parserSAS.getAuthentication().setSymmetricKey(new SymmetricKeyParser(SAMPLE_THUMBPRINT,SAMPLE_THUMBPRINT));
        parserSAS.setId("deviceSAS");

        // act
        ExportImportDevice deviceCA = new ExportImportDevice(parserCA);
        ExportImportDevice deviceSelf = new ExportImportDevice(parserSelf);
        ExportImportDevice deviceSAS = new ExportImportDevice(parserSAS);

        // assert
        assertEquals(AuthenticationType.certificateAuthority, deviceCA.getAuthentication().getAuthenticationType());
        assertEquals(AuthenticationType.selfSigned, deviceSelf.getAuthentication().getAuthenticationType());
        assertEquals(AuthenticationType.sas, deviceSAS.getAuthentication().getAuthenticationType());

        assertEquals(ImportMode.Create, deviceCA.getImportMode());
        assertEquals(DeviceStatus.Enabled, deviceCA.getStatus());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_051: [This constructor will save the provided deviceId and authenticationType to itself.]
    @Test
    public void constructorSavesDeviceIdAndAuthType()
    {
        String deviceId = "someDevice";
        ExportImportDevice device = new ExportImportDevice(deviceId, AuthenticationType.certificateAuthority);

        assertEquals(AuthenticationType.certificateAuthority, device.getAuthentication().getAuthenticationType());
        assertEquals(deviceId, device.getId());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_053: [If the provided parser does not have values for the properties deviceId or authentication, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void conversionFromDeviceParserMissingDeviceIdThrows()
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.setAuthentication(new AuthenticationParser());
        parser.getAuthentication().setType(AuthenticationTypeParser.certificateAuthority);
        Deencapsulation.setField(parser, "Id", null);

        // act
        new ExportImportDevice(parser);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_053: [If the provided parser does not have values for the properties deviceId or authentication, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void conversionFromDeviceParserMissingAuthenticationThrows()
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        Deencapsulation.setField(parser, "Authentication", null);
        parser.setId("deviceCA");

        // act
        new ExportImportDevice(parser);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_056: [If the provided authentication is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void cannotSetIdNull()
    {
        new ExportImportDevice().setId(null);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_055: [If the provided id is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void cannotSetAuthenticationNull()
    {
        new ExportImportDevice().setAuthentication(null);
    }

    private ExportImportDevice createTestDevice(Authentication authentication, ImportMode importMode, DeviceStatus status)
    {
        ExportImportDevice device = Deencapsulation.newInstance(ExportImportDevice.class, new Class[]{});
        device.setAuthentication(authentication);
        device.setImportMode(importMode);
        device.setStatus(status);

        return device;
    }
}