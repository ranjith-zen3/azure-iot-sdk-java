/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.DeviceConnectionState;
import com.microsoft.azure.sdk.iot.service.DeviceStatus;
import com.microsoft.azure.sdk.iot.service.auth.Authentication;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.NonStrictExpectations;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Code coverage:
 * 100% Methods
 * 87% lines
 */
public class DeviceTest
{
    private static final String SAMPLE_THUMBPRINT = "0000000000000000000000000000000000000000";
    private static final String SAMPLE_KEY = "000000000000000000000000";

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
        Authentication expectedAuthentication = new Authentication(expectedPrimaryThumbprint, expectedSecondaryThumbprint);

        // Act
        Device device = Device.createFromId(deviceId, null, null);

        device.setSymmetricKey(expectedSymmetricKey);
        assertEquals(expectedSymmetricKey, device.getSymmetricKey());

        device.setThumbprint(expectedPrimaryThumbprint, expectedSecondaryThumbprint);
        assertEquals(expectedPrimaryThumbprint, device.getPrimaryThumbprint());
        assertEquals(expectedSecondaryThumbprint, device.getSecondaryThumbprint());

        device.setStatus(DeviceStatus.Enabled);
        assertEquals(DeviceStatus.Enabled, device.getStatus());

        device.getPrimaryThumbprint();
        device.getSecondaryThumbprint();
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

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createDeviceThrowsIllegalArgumentExceptionWhenGivenNullDeviceId()
    {
        // Arrange
        String deviceId = null;
        // Act
        Device.createDevice(deviceId, AuthenticationType.certificateAuthority);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createDeviceThrowsIllegalArgumentExceptionWhenGivenNullAuthenticationType()
    {
        // Act
        Device.createDevice("someDevice", null);
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
        String utcTimeDefault = "0001-01-01T00:00:00.0000Z";
        String offsetTimeDefault = "0001-01-01T00:00:00-00:00";

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
        assertEquals(offsetTimeDefault, device.getLastActivityTime());
        assertEquals(0, device.getCloudToDeviceMessageCount());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_016: [This method shall return a new instance of a DeviceParser object that is populated using the properties of this.]
    @Test
    public void conversionToDeviceParser()
    {
        // arrange
        Device deviceCA = Device.createDevice("deviceCA", AuthenticationType.certificateAuthority);
        Device deviceSelf = Device.createDevice("deviceSelf", AuthenticationType.selfSigned);
        Device deviceSAS = Device.createDevice("deviceSAS", AuthenticationType.sas);

        // act
        DeviceParser parserCA = deviceCA.toDeviceParser();
        DeviceParser parserSelf = deviceSelf.toDeviceParser();
        DeviceParser parserSAS = deviceSAS.toDeviceParser();

        // assert
        assertEquals(AuthenticationTypeParser.certificateAuthority, parserCA.getAuthenticationParser().getType());
        assertEquals(AuthenticationTypeParser.selfSigned, parserSelf.getAuthenticationParser().getType());
        assertEquals(AuthenticationTypeParser.sas, parserSAS.getAuthenticationParser().getType());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_014: [This constructor will create a new Device object using the values within the provided parser.]
    @Test
    public void conversionFromDeviceParser()
    {
        // arrange
        DeviceParser parserCA = new DeviceParser();
        parserCA.setAuthenticationParser(new AuthenticationParser());
        parserCA.getAuthenticationParser().setType(AuthenticationTypeParser.certificateAuthority);
        parserCA.setDeviceId("deviceCA");

        DeviceParser parserSelf = new DeviceParser();
        parserSelf.setAuthenticationParser(new AuthenticationParser());
        parserSelf.getAuthenticationParser().setType(AuthenticationTypeParser.selfSigned);
        parserSelf.getAuthenticationParser().setThumbprint(new X509ThumbprintParser(SAMPLE_THUMBPRINT, SAMPLE_THUMBPRINT));
        parserSelf.setDeviceId("deviceSelf");

        DeviceParser parserSAS = new DeviceParser();
        parserSAS.setAuthenticationParser(new AuthenticationParser());
        parserSAS.getAuthenticationParser().setType(AuthenticationTypeParser.sas);
        parserSAS.getAuthenticationParser().setSymmetricKey(new SymmetricKeyParser(SAMPLE_KEY, SAMPLE_KEY));
        parserSAS.setDeviceId("deviceSAS");

        // act
        Device deviceCA = new Device(parserCA);
        Device deviceSelf = new Device(parserSelf);
        Device deviceSAS = new Device(parserSAS);

        // assert
        assertEquals(AuthenticationType.certificateAuthority, deviceCA.getAuthenticationType());
        assertEquals(AuthenticationType.selfSigned, deviceSelf.getAuthenticationType());
        assertEquals(AuthenticationType.sas, deviceSAS.getAuthenticationType());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_015: [If the provided parser is missing a value for its authentication or its device Id, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void conversionFromDeviceWithoutDeviceIdThrowsIllegalArgumentException()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.setAuthenticationParser(new AuthenticationParser());
        parser.getAuthenticationParser().setType(AuthenticationTypeParser.certificateAuthority);

        // act
        new Device(parser);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_015: [If the provided parser is missing a value for its authentication or its device Id, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void conversionFromDeviceWithoutAuthenticationTypeThrowsIllegalArgumentException()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.setDeviceId("someDevice");

        // act
        new Device(parser);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_011: [If the provided authenticationType is certificate authority, no symmetric key shall be generated and no thumbprint shall be generated]
    @Test
    public void deviceConstructorWithCertificateAuthorityGeneratesKeysCorrectly()
    {
        // act
        Device device = Deencapsulation.newInstance(Device.class, new Class[] { String.class, AuthenticationType.class }, "someDevice", AuthenticationType.certificateAuthority);

        // assert
        assertNull(device.getPrimaryThumbprint());
        assertNull(device.getSecondaryThumbprint());
        assertNull(device.getSymmetricKey());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_012: [If the provided authenticationType is sas, a symmetric key shall be generated but no thumbprint shall be generated]
    @Test
    public void deviceConstructorWithSharedAccessSignatureGeneratesKeysCorrectly()
    {
        // act
        Device device = Deencapsulation.newInstance(Device.class, new Class[] { String.class, AuthenticationType.class }, "someDevice", AuthenticationType.sas);

        // assert
        assertNull(device.getPrimaryThumbprint());
        assertNull(device.getSecondaryThumbprint());
        assertNotNull(device.getSymmetricKey());
        assertNotNull(device.getPrimaryKey());
        assertNotNull(device.getSecondaryKey());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_013: [If the provided authenticationType is self signed, a thumbprint shall be generated but no symmetric key shall be generated]
    @Test
    public void deviceConstructorWithSelfSignedGeneratesKeysCorrectly()
    {
        // act
        Device device = Deencapsulation.newInstance(Device.class, new Class[] { String.class, AuthenticationType.class }, "someDevice", AuthenticationType.selfSigned);

        // assert
        assertNotNull(device.getPrimaryThumbprint());
        assertNotNull(device.getSecondaryThumbprint());
        assertNull(device.getSymmetricKey());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
    @Test (expected = IllegalArgumentException.class)
    public void createDeviceThrowsIllegalArgumentExceptionForNullDeviceId()
    {
        Device.createDevice(null, AuthenticationType.certificateAuthority);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
    @Test (expected = IllegalArgumentException.class)
    public void createDeviceThrowsIllegalArgumentExceptionForEmptyDeviceId()
    {
        Device.createDevice("", AuthenticationType.certificateAuthority);
    }

    //Tests_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
    @Test (expected = IllegalArgumentException.class)
    public void createDeviceThrowsIllegalArgumentExceptionForNullAuthenticationType()
    {
        Device.createDevice("someDevice", null);
    }
}