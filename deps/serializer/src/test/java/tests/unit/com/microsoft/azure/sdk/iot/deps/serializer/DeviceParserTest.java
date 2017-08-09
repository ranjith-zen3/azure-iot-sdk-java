/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Code coverage:
 * 100% Methods
 * 100% lines
 */
@RunWith(JMockit.class)
public class DeviceParserTest
{
    private static Date validDate;
    private static String validDateString;
    private static final String SIMPLEDATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private static final String expectedPrimaryThumbprint = "0000000000000000000000000000000000000000";
    private static final String expectedSecondaryThumbprint = "1111111111111111111111111111111111111111";

    @Before
    public void setUp() throws ParseException
    {
        validDate = new Date();
        validDateString =  new SimpleDateFormat(SIMPLEDATEFORMAT).format(validDate);
    }


    //Tests_SRS_DEVICE_PARSER_34_002: [This constructor will create a DeviceParser object based off of the provided json.]
    @Test
    public void fromJsonWithSelfSignedAuthentication()
    {
        //arrange
        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"deviceId\": \"deviceId1234\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"lastActivityTime\": \"" + validDateString + "\",\n" +
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
        DeviceParser parser = new DeviceParser(json);

        //assert
        assertEquals(expectedPrimaryThumbprint, parser.getAuthenticationParser().getThumbprint().getPrimaryThumbprint());
        assertEquals(expectedSecondaryThumbprint, parser.getAuthenticationParser().getThumbprint().getSecondaryThumbprint());
        assertNull(parser.getAuthenticationParser().getSymmetricKey());
        assertEquals(AuthenticationTypeParser.selfSigned, parser.getAuthenticationParser().getType());
    }

    //Tests_SRS_DEVICE_PARSER_34_002: [This constructor will create a DeviceParser object based off of the provided json.]
    @Test
    public void fromJsonWithSASAuthentication()
    {
        //arrange
        String expectedPrimaryKey = "000000000000000000000000";
        String expectedSecondaryKey = "000000000000000000000000";

        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"deviceId\": \"deviceId1234\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"lastActivityTime\": \"" + validDateString + "\",\n" +
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
        DeviceParser parser = new DeviceParser(json);

        //assert
        assertEquals(expectedPrimaryKey, parser.getAuthenticationParser().getSymmetricKey().getPrimaryKey());
        assertEquals(expectedSecondaryKey, parser.getAuthenticationParser().getSymmetricKey().getSecondaryKey());
        assertNull(parser.getAuthenticationParser().getThumbprint());
        assertEquals(AuthenticationTypeParser.sas, parser.getAuthenticationParser().getType());
    }

    //Tests_SRS_DEVICE_PARSER_34_002: [This constructor will create a DeviceParser object based off of the provided json.]
    @Test
    public void fromJsonWithCAAuthentication() throws ParseException
    {
        //arrange
        String deviceId = "deviceId";
        String generationId = "1234";
        String ETag = "5678";
        String status = "Enabled";
        String statusReason = "no reason";
        String statusUpdatedTime = validDateString;
        String connectionState = "Disconnected";
        String connectionStateUpdatedTime = validDateString;
        String lastActivityTime = ParserUtility.getSimpleDateStringFromDate(new Date());
        int cloudToDeviceMessageCount = 20;

        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"deviceId\": \"" + deviceId + "\",\n" +
                "  \"generationId\": \"" + generationId + "\",\n" +
                "  \"etag\": \"" + ETag + "\",\n" +
                "  \"status\": \"" + status + "\",\n" +
                "  \"statusReason\": \"" + statusReason + "\",\n" +
                "  \"statusUpdatedTime\": \"" + statusUpdatedTime + "\",\n" +
                "  \"connectionState\": \"" + connectionState + "\",\n" +
                "  \"connectionStateUpdatedTime\": \"" + connectionStateUpdatedTime + "\",\n" +
                "  \"lastActivityTime\": \"" + lastActivityTime + "\",\n" +
                "  \"cloudToDeviceMessageCount\": \"" + cloudToDeviceMessageCount + "\",\n" +
                "  \"forceUpdate\": \"false\",\n" +
                "  \"authentication\": {\n" +
                "    \"type\": \"" + AuthenticationTypeParser.certificateAuthority + "\"\n" +
                "  }\n" +
                "}";

        //act
        DeviceParser parser = new DeviceParser(json);

        //assert
        assertNull(parser.getAuthenticationParser().getThumbprint());
        assertNull(parser.getAuthenticationParser().getSymmetricKey());
        assertEquals(AuthenticationTypeParser.certificateAuthority, parser.getAuthenticationParser().getType());
        assertEquals(deviceId, parser.getDeviceId());
        assertEquals(generationId, parser.getGenerationId());
        assertEquals(ETag, parser.geteTag());
        assertEquals(status, parser.getStatus());
        assertEquals(statusReason, parser.getStatusReason());
        assertEquals(new SimpleDateFormat(SIMPLEDATEFORMAT).parse(statusUpdatedTime), parser.getStatusUpdatedTime());
        assertEquals(connectionState, parser.getConnectionState());
        assertEquals(new SimpleDateFormat(SIMPLEDATEFORMAT).parse(connectionStateUpdatedTime), parser.getConnectionStateUpdatedTime());
        assertEquals(new SimpleDateFormat(SIMPLEDATEFORMAT).parse(lastActivityTime), parser.getLastActivityTime());
        assertEquals(cloudToDeviceMessageCount, parser.getCloudToDeviceMessageCount());
    }

    //Tests_SRS_DEVICE_PARSER_34_001: [This method shall return a json representation of this.]
    @Test
    public void toJsonForCASignedDevice()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.setAuthenticationParser(new AuthenticationParser());
        parser.getAuthenticationParser().setType(AuthenticationTypeParser.certificateAuthority);

        String certificateAuthoritySignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.certificateAuthority + "\"";

        // act
        String serializedDevice = parser.toJson();

        // assert
        assertTrue(serializedDevice.contains(certificateAuthoritySignedDeviceAuthenticationJson));
    }

    //Tests_SRS_DEVICE_PARSER_34_001: [This method shall return a json representation of this.]
    @Test
    public void toJsonForSelfSignedDevice()
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.setAuthenticationParser(new AuthenticationParser());
        parser.getAuthenticationParser().setType(AuthenticationTypeParser.selfSigned);
        String sampleThumbprint = "0000000000000000000000000000000000000000";
        parser.getAuthenticationParser().setThumbprint(new X509ThumbprintParser(sampleThumbprint, sampleThumbprint));

        String selfSignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.selfSigned + "\"";

        // act
        String serializedDevice = parser.toJson();

        // assert
        assertTrue(serializedDevice.contains(selfSignedDeviceAuthenticationJson));
    }

    //Tests_SRS_DEVICE_PARSER_34_001: [This method shall return a json representation of this.]
    @Test
    public void toJsonForSymmetricKeySecuredDevice() throws NoSuchAlgorithmException
    {
        // arrange
        DeviceParser parser = new DeviceParser();
        parser.setAuthenticationParser(new AuthenticationParser());
        parser.getAuthenticationParser().setType(AuthenticationTypeParser.sas);
        String sampleKey = "000000000000000000000000";
        parser.getAuthenticationParser().setSymmetricKey(new SymmetricKeyParser(sampleKey, sampleKey));

        String symmetricKeySecuredDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.sas + "\"";

        // act
        String serializedDevice = parser.toJson();

        // assert
        assertTrue(serializedDevice.contains(symmetricKeySecuredDeviceAuthenticationJson));
    }

    //Tests_SRS_DEVICE_PARSER_34_003: [This parser has properties for eTag, deviceId, generationId, status, statusReason, statusUpdatedTime, connectionState, connectionStateUpdatedTime, lastActivityTim, cloudToDeviceMessageCount, and authenticationParser.]
    //Tests_SRS_DEVICE_PARSER_34_007: [This method shall set the value of authenticationParser to the provided value.]
    //Tests_SRS_DEVICE_PARSER_34_009: [This method shall set the value of deviceId to the provided value.]
    @Test
    public void testGettersAndSetters()
    {
        String connectionState = "connectionState";

        DeviceParser parser = new DeviceParser();
        String deviceId = "someDevice";
        String eTag = "etag";
        Date connectionStateUpdatedTime = new Date();
        Date lastActivityTime = new Date();
        Date statusUpdatedTime = new Date();
        String generationId = "1234";
        String status = "Enabled";
        String statusReason = "no reason";
        long cloudToDeviceMessageCount = 2;

        assertNull(parser.getAuthenticationParser());
        assertNull(parser.getConnectionState());
        assertNull(parser.getConnectionStateUpdatedTime());
        assertNull(parser.getDeviceId());
        assertNull(parser.geteTag());
        assertNull(parser.getGenerationId());
        assertNull(parser.getLastActivityTime());
        assertNull(parser.getStatus());
        assertNull(parser.getStatusReason());
        assertEquals(0, parser.getCloudToDeviceMessageCount());

        parser.setAuthenticationParser(new AuthenticationParser());
        parser.setConnectionState(connectionState);
        parser.setDeviceId(deviceId);
        parser.seteTag(eTag);
        parser.setConnectionStateUpdatedTime(connectionStateUpdatedTime);
        parser.setLastActivityTime(lastActivityTime);
        parser.setGenerationId(generationId);
        parser.setStatus(status);
        parser.setStatusReason(statusReason);
        parser.setCloudToDeviceMessageCount(cloudToDeviceMessageCount);
        parser.setStatusUpdatedTime(statusUpdatedTime);

        assertEquals(new AuthenticationParser().getType(), parser.getAuthenticationParser().getType());
        assertEquals(new AuthenticationParser().getSymmetricKey(), parser.getAuthenticationParser().getSymmetricKey());
        assertEquals(new AuthenticationParser().getThumbprint(), parser.getAuthenticationParser().getThumbprint());
        assertEquals(connectionState, parser.getConnectionState());
        assertEquals(connectionStateUpdatedTime, parser.getConnectionStateUpdatedTime());
        assertEquals(deviceId, parser.getDeviceId());
        assertEquals(eTag, parser.geteTag());
        assertEquals(generationId, parser.getGenerationId());
        assertEquals(lastActivityTime, parser.getLastActivityTime());
        assertEquals(status, parser.getStatus());
        assertEquals(statusReason, parser.getStatusReason());
        assertEquals(cloudToDeviceMessageCount, parser.getCloudToDeviceMessageCount());
        assertEquals(statusUpdatedTime, parser.getStatusUpdatedTime());
    }

    //Tests_SRS_DEVICE_PARSER_34_004: [For each of this parser's properties, if the setter is called with a null argument, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void cantSetAuthenticationParserNull()
    {
        new DeviceParser().setAuthenticationParser(null);
    }

    //Tests_SRS_DEVICE_PARSER_34_004: [For each of this parser's properties, if the setter is called with a null argument, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void cantSetDeviceIdNull()
    {
        new DeviceParser().setDeviceId(null);
    }

    //Tests_SRS_DEVICE_PARSER_34_005: [If the provided json is null or empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForNullJson()
    {
        new DeviceParser(null);
    }

    //Tests_SRS_DEVICE_PARSER_34_005: [If the provided json is null or empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForEmptyJson()
    {
        new DeviceParser("");
    }

    //Tests_SRS_DEVICE_PARSER_34_006: [If the provided json cannot be parsed into a DeviceParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForInvalidJson()
    {
        String json = "{";
        new DeviceParser(json);
    }

    //Tests_SRS_DEVICE_PARSER_34_012: [If the provided json is missing the authentication field or its value is empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForJsonMissingAuthenticationParser()
    {
        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"deviceId\": \"deviceId1234\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"lastActivityTime\": \"" + validDateString + "\",\n" +
                "  \"cloudToDeviceMessageCount\": 0,\n" +
                "  \"forceUpdate\": false\n" +
                "}";

        new DeviceParser(json);
    }

    //Tests_SRS_DEVICE_PARSER_34_011: [If the provided json is missing the DeviceId field or its value is empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForJsonMissingDeviceId()
    {
        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"lastActivityTime\": \"" + validDateString + "\",\n" +
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

        new DeviceParser(json);
    }

    //Tests_SRS_DEVICE_PARSER_34_011: [If the provided json is missing the DeviceId field or its value is empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForJsonEmptyDeviceId()
    {
        String json = "{\n" +
                "  \"encryptionMethod\": \"AES\",\n" +
                "  \"deviceId\": \"\",\n" +
                "  \"generationId\": \"\",\n" +
                "  \"etag\": \"\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"statusReason\": \"\",\n" +
                "  \"statusUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"connectionState\": \"Disconnected\",\n" +
                "  \"connectionStateUpdatedTime\": \"" + validDateString + "\",\n" +
                "  \"lastActivityTime\": \"" + validDateString + "\",\n" +
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

        new DeviceParser(json);
    }

    //Tests_SRS_DEVICE_PARSER_34_010: [If the provided deviceId value is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void setterForDeviceIdCalledWithNullThrowsIllegalArgumentException()
    {
        DeviceParser parser = new DeviceParser();
        parser.setDeviceId(null);
    }

    //Tests_SRS_DEVICE_PARSER_34_010: [If the provided deviceId value is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void setterForDeviceIdCalledWithEmptyStringThrowsIllegalArgumentException()
    {
        DeviceParser parser = new DeviceParser();
        parser.setDeviceId("");
    }

    //Tests_SRS_DEVICE_PARSER_34_008: [If the provided authenticationParser value is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void setterForAuthenticationCalledWithNullThrowsIllegalArgumentException()
    {
        DeviceParser parser = new DeviceParser();
        parser.setAuthenticationParser(null);
    }
}
