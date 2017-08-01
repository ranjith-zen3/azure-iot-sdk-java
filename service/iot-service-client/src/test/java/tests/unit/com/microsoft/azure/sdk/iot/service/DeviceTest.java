/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.*;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;
import com.microsoft.azure.sdk.iot.service.auth.X509Thumbprint;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.NonStrictExpectations;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Code coverage:
 * 96% Methods, 92% lines
 */
public class DeviceTest
{
    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_001: [The Device class has the following properties: Id, Etag, Authentication.SymmetricKey, State, StateReason, StateUpdatedTime, ConnectionState, ConnectionStateUpdatedTime, LastActivityTime, symmetricKey, thumbprint, status, authentication]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void device_get_all_properties() throws NoSuchAlgorithmException
    {
        // Arrange
        String deviceId = "xxx-device";
        SymmetricKey expectedSymmetricKey = new SymmetricKey();
        String expectedPrimaryThumbprint = "0000000000000000000000000000000000000000";
        String expectedSecondaryThumbprint = "1111111111111111111111111111111111111111";
        Authentication expectedAuthentication = new Authentication(new X509Thumbprint(expectedPrimaryThumbprint, expectedSecondaryThumbprint));

        // Act
        Device device = Device.createFromId(deviceId, null, null);

        device.setSymmetricKey(expectedSymmetricKey);
        assertEquals(expectedSymmetricKey, device.getSymmetricKey());

        device.setThumbprint(new X509Thumbprint(expectedPrimaryThumbprint, expectedSecondaryThumbprint));
        assertEquals(expectedPrimaryThumbprint, device.getPrimaryThumbprint());
        assertEquals(expectedSecondaryThumbprint, device.getSecondaryThumbprint());

        device.setStatus(DeviceStatus.Enabled);
        assertEquals(DeviceStatus.Enabled, device.getStatus());

        device.getThumbprint();
        device.getDeviceId();
        device.getGenerationId();
        device.getPrimaryKey();
        device.getSecondaryKey();
        device.geteTag();
        device.getStatus();
        device.getStatusReason();
        device.getStatusUpdatedTime();
        device.getConnectionState();
        device.getConnectionStateUpdatedTime();
        device.getLastActivityTime();
        device.getCloudToDeviceMessageCount();

        device.setAuthentication(expectedAuthentication);
        assertEquals(expectedAuthentication, device.getAuthentication());

        device.setForceUpdate(true);
        device.setForceUpdate(null);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_002: [The constructor shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createFromId_input_null() throws NoSuchAlgorithmException
    {
        // Arrange
        String deviceId = null;
        // Act
        Device.createFromId(deviceId, null, null);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_008: [The function shall throw IllegalArgumentException if the device Id is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createCertificateSecuredDeviceThrowsIllegalArgumentExceptionWhenGivenNullDeviceId()
    {
        // Arrange
        String deviceId = null;
        // Act
        Device.createDevice(deviceId, AuthenticationType.certificateAuthority);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall set the authentication to use Certificate Authority signed certificates]
    @Test
    public void createCertificateSecuredDeviceSetsAuthenticationToUseCASignedCerts()
    {
        // Act
        Device device = Device.createDevice("someDevice", AuthenticationType.certificateAuthority);

        // Assert
        assertEquals(AuthenticationType.certificateAuthority, device.getAuthenticationType());
    }


    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_002: [The constructor shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createFromId_input_empty() throws NoSuchAlgorithmException
    {
        // Arrange
        String deviceId = "";
        // Act
        Device.createFromId(deviceId, null, null);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_003: [The constructor shall create a new instance of Device using the given deviceId and return with it]
    @Test
    public void createFromId_good_case() throws NoSuchAlgorithmException
    {
        // Arrange
        String deviceId = "xxx-device";
        new Expectations()
        {
            {
                Deencapsulation.newInstance(Device.class, deviceId, DeviceStatus.class, SymmetricKey.class);
            }
        };
        // Act
        Device device = Device.createFromId(deviceId, null, null);
        // Assert
        assertNotEquals(device, null);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_004: [The constructor shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void constructor_string_null() throws NoSuchAlgorithmException
    {
        // Arrange
        String deviceId = null;
        // Act
        Device device = Deencapsulation.newInstance(Device.class, deviceId, null, null);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_004: [The constructor shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void constructor_string_empty() throws NoSuchAlgorithmException
    {
        // Arrange
        String deviceId = "";
        // Act
        Device device = Deencapsulation.newInstance(Device.class, deviceId, null, null);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_005: [If the input symmetric key is empty, the constructor shall create
    // a new SymmetricKey instance using AES encryption and store it into a member variable]
    @Test
    public void constructor_create_symmetrickey() throws NoSuchAlgorithmException
    {
        // Arrange
        String encryptionMethod = "AES";
        String deviceId = "xxx-device";
        new NonStrictExpectations()
        {
            {
                SymmetricKey symmetricKey = new SymmetricKey();
                KeyGenerator.getInstance(encryptionMethod);
            }
        };
        // Act
        Device device = Deencapsulation.newInstance(Device.class, deviceId, DeviceStatus.class, SymmetricKey.class);
        // Assert
        assertNotEquals(device.getSymmetricKey(), null);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_15_007: [The constructor shall store
    // the input device status and symmetric key into a member variable]
    @Test
    public void constructor_sets_status_and_symmetrickey() throws NoSuchAlgorithmException
    {
        // Arrange
        String deviceId = "xxx-device";

        DeviceStatus expectedDeviceStatus = DeviceStatus.Disabled;
        SymmetricKey expectedSymmetricKey = new SymmetricKey();
        // Act
        Device device = Deencapsulation.newInstance(Device.class, deviceId, expectedDeviceStatus, expectedSymmetricKey); 
        
        // Assert
        assertEquals(expectedDeviceStatus, device.getStatus());
        assertEquals(expectedSymmetricKey, device.getSymmetricKey());
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_12_006: [The constructor shall initialize all properties to default value]
    @Test
    public void constructor_initialize_properties() throws NoSuchAlgorithmException
    {
        // Arrange
        String utcTimeDefault = "0001-01-01T00:00:00";
        String deviceId = "xxx-device";
        // Act
        Device device = Deencapsulation.newInstance(Device.class, deviceId, DeviceStatus.class, SymmetricKey.class);
        // Assert
        assertNotEquals(null, device);
        assertNotEquals(device.getSymmetricKey(), null);

        assertEquals("xxx-device", device.getDeviceId());
        assertEquals("", device.getGenerationId());
        assertEquals("", device.geteTag());
        assertEquals(DeviceStatus.Enabled, device.getStatus());
        assertEquals("", device.getStatusReason());
        assertEquals(utcTimeDefault, device.getStatusUpdatedTime());
        assertEquals(DeviceConnectionState.Disconnected, device.getConnectionState());
        assertEquals(utcTimeDefault, device.getStatusUpdatedTime());
        assertEquals(utcTimeDefault, device.getConnectionStateUpdatedTime());
        assertEquals(utcTimeDefault, device.getLastActivityTime());
        assertEquals(0, device.getCloudToDeviceMessageCount());
    }

    @Test
    public void conversionToDeviceParser()
    {
        // arrange
        Device deviceCA = new Device("deviceCA", AuthenticationType.certificateAuthority);
        Device deviceSelf = new Device("deviceSelf", AuthenticationType.selfSigned);
        Device deviceSAS = new Device("deviceSAS", AuthenticationType.sas);

        // act
        DeviceParser parserCA = Device.toDeviceParser(deviceCA);
        DeviceParser parserSelf = Device.toDeviceParser(deviceSelf);
        DeviceParser parserSAS = Device.toDeviceParser(deviceSAS);

        // assert
        assertEquals(AuthenticationTypeParser.certificateAuthority, parserCA.authenticationParser.type);
        assertEquals(AuthenticationTypeParser.selfSigned, parserSelf.authenticationParser.type);
        assertEquals(AuthenticationTypeParser.sas, parserSAS.authenticationParser.type);
    }

    @Test
    public void conversionFromDeviceParser()
    {
        // arrange
        DeviceParser parserCA = new DeviceParser();
        parserCA.authenticationParser = new AuthenticationParser();
        parserCA.authenticationParser.type = AuthenticationTypeParser.certificateAuthority;
        parserCA.deviceId = "deviceCA";

        DeviceParser parserSelf = new DeviceParser();
        parserSelf.authenticationParser = new AuthenticationParser();
        parserSelf.authenticationParser.type = AuthenticationTypeParser.selfSigned;
        parserSelf.authenticationParser.thumbprint = new X509ThumbprintParser("0000000000000000000000000000000000000000","0000000000000000000000000000000000000000");
        parserSelf.deviceId = "deviceSelf";

        DeviceParser parserSAS = new DeviceParser();
        parserSAS.authenticationParser = new AuthenticationParser();
        parserSAS.authenticationParser.type = AuthenticationTypeParser.sas;
        parserSAS.authenticationParser.symmetricKey = new SymmetricKeyParser("000000000000000000000000","000000000000000000000000");
        parserSAS.deviceId = "deviceSAS";

        // act
        Device deviceCA = Device.fromDeviceParser(parserCA);
        Device deviceSelf = Device.fromDeviceParser(parserSelf);
        Device deviceSAS = Device.fromDeviceParser(parserSAS);

        // assert
        assertEquals(AuthenticationType.certificateAuthority, deviceCA.getAuthentication().getAuthenticationType());
        assertEquals(AuthenticationType.selfSigned, deviceSelf.getAuthentication().getAuthenticationType());
        assertEquals(AuthenticationType.sas, deviceSAS.getAuthentication().getAuthenticationType());
    }

    @Test (expected = IllegalArgumentException.class)
    public void conversionFromDeviceWithoutDeviceIdThrowsIllegalArgumentException()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.authenticationParser = new AuthenticationParser();
        parser.authenticationParser.type = AuthenticationTypeParser.certificateAuthority;

        // act
        Device.fromDeviceParser(parser);
    }

    @Test (expected = IllegalArgumentException.class)
    public void conversionFromDeviceWithoutAuthenticationTypeThrowsIllegalArgumentException()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.deviceId = "someDevice";

        // act
        Device.fromDeviceParser(parser);
    }
}