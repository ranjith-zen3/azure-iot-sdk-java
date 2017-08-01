/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.NoSuchAlgorithmException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Code coverage:
 * 100% Methods, 100% lines
 */
@RunWith(JMockit.class)
public class DeviceParserTest
{
    //Tests_SRS_DEVICE_PARSER_34_002: [The parser will look for the authentication of the serialized device and save it to the returned DeviceParser instance]
    @Test
    public void fromJsonWithSelfSignedAuthentication()
    {
        //arrange
        String expectedPrimaryThumbprint = "0000000000000000000000000000000000000000";
        String expectedSecondaryThumbprint = "1111111111111111111111111111111111111111";

        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"utcTimeDefault\": \"0001-01-01T00:00:00\",\n" +
                "  \"deviceId\": \"deviceId1234\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"lastActivityTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"cloudToDeviceMessageCount\": 0,\n" +
                "  \"forceUpdate\": false,\n" +
                "  \"authentication\": {\n" +
                "    \"type\": \"" + AuthenticationTypeParser.selfSigned + "\",\n" +
                "    \"x509Thumbprint\": {\n" +
                "      \"primaryThumbprint\": \"" + expectedPrimaryThumbprint + "\",\n" +
                "      \"secondaryThumbprint\": \"" + expectedSecondaryThumbprint + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //act
        DeviceParser parser = DeviceParser.fromJson(json);

        //assert
        assertEquals(expectedPrimaryThumbprint, parser.authenticationParser.thumbprint.primaryThumbprint);
        assertEquals(expectedSecondaryThumbprint, parser.authenticationParser.thumbprint.secondaryThumbprint);
        assertNull(parser.authenticationParser.symmetricKey);
        assertEquals(AuthenticationTypeParser.selfSigned, parser.authenticationParser.type);
    }

    //Tests_SRS_DEVICE_PARSER_34_002: [The parser will look for the authentication of the serialized device and save it to the returned DeviceParser instance]
    @Test
    public void fromJsonWithSASAuthentication()
    {
        //arrange
        String expectedPrimaryKey = "000000000000000000000000";
        String expectedSecondaryKey = "000000000000000000000000";

        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"utcTimeDefault\": \"0001-01-01T00:00:00\",\n" +
                "  \"deviceId\": \"deviceId1234\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"lastActivityTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"cloudToDeviceMessageCount\": 0,\n" +
                "  \"forceUpdate\": false,\n" +
                "  \"authentication\": {\n" +
                "    \"type\": \"" + AuthenticationTypeParser.sas + "\",\n" +
                "    \"symmetricKey\": {\n" +
                "      \"primaryKey\": \"" + expectedPrimaryKey + "\",\n" +
                "      \"secondaryKey\": \"" + expectedSecondaryKey + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //act
        DeviceParser parser = DeviceParser.fromJson(json);

        //assert
        assertEquals(expectedPrimaryKey, parser.authenticationParser.symmetricKey.primaryKey);
        assertEquals(expectedSecondaryKey, parser.authenticationParser.symmetricKey.secondaryKey);
        assertNull(parser.authenticationParser.thumbprint);
        assertEquals(AuthenticationTypeParser.sas, parser.authenticationParser.type);
    }

    //Tests_SRS_DEVICE_PARSER_34_002: [The parser will look for the authentication of the serialized device and save it to the returned DeviceParser instance]
    @Test
    public void fromJsonWithCAAuthentication()
    {
        //arrange
        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"utcTimeDefault\": \"0001-01-01T00:00:00\",\n" +
                "  \"deviceId\": \"deviceId1234\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"lastActivityTime\": \"0001-01-01T00:00:00\",\n" +
                "  \"cloudToDeviceMessageCount\": 0,\n" +
                "  \"forceUpdate\": false,\n" +
                "  \"authentication\": {\n" +
                "    \"type\": \"" + AuthenticationTypeParser.certificateAuthority + "\"\n" +
                "  }\n" +
                "}";

        //act
        DeviceParser parser = DeviceParser.fromJson(json);

        //assert
        assertNull(parser.authenticationParser.thumbprint);
        assertNull(parser.authenticationParser.symmetricKey);
        assertEquals(AuthenticationTypeParser.certificateAuthority, parser.authenticationParser.type);
    }

    //Tests_SRS_DEVICE_PARSER_34_001: [The parser will save the DeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForCASignedDevice()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.authenticationParser = new AuthenticationParser();
        parser.authenticationParser.type = AuthenticationTypeParser.certificateAuthority;

        String certificateAuthoritySignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.certificateAuthority + "\"";

        // act
        String serializedDevice = DeviceParser.toJson(parser);

        // assert
        assertTrue(serializedDevice.contains(certificateAuthoritySignedDeviceAuthenticationJson));
    }

    //Tests_SRS_DEVICE_PARSER_34_001: [The parser will save the DeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForSelfSignedDevice()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.authenticationParser = new AuthenticationParser();
        parser.authenticationParser.type = AuthenticationTypeParser.selfSigned;
        parser.authenticationParser.thumbprint = new X509ThumbprintParser("", "");

        String selfSignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.selfSigned + "\"";

        // act
        String serializedDevice = DeviceParser.toJson(parser);

        // assert
        assertTrue(serializedDevice.contains(selfSignedDeviceAuthenticationJson));
    }

    //Tests_SRS_DEVICE_PARSER_34_001: [The parser will save the DeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForSymmetricKeySecuredDevice() throws NoSuchAlgorithmException
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.authenticationParser = new AuthenticationParser();
        parser.authenticationParser.type = AuthenticationTypeParser.sas;
        parser.authenticationParser.symmetricKey = new SymmetricKeyParser("", "");

        String symmetricKeySecuredDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.sas + "\"";

        // act
        String serializedDevice = DeviceParser.toJson(parser);

        // assert
        assertTrue(serializedDevice.contains(symmetricKeySecuredDeviceAuthenticationJson));
    }
}
