// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Code Coverage:
 * Methods: 100%
 * Lines: 100%
 */
public class ExportImportDeviceParserTest
{
    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser shall look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
    @Test
    public void fromJsonWithCASignedAuthentication()
    {
        //arrange
        String json = "{\n" +
                "  \"id\": \"test\",\n" +
                "  \"eTag\": \"MA==\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"authentication\": {\n" +
                "    \"symmetricKey\": {\n" +
                "      \"primaryKey\": \"null\",\n" +
                "      \"secondaryKey\": \"null\"\n" +
                "    },\n" +
                "    \"x509Thumbprint\": {\n" +
                "      \"primaryThumbprint\":null,\n" +
                "      \"secondaryThumbprint\": null\n" +
                "    },\n" +
                "    \"type\": \"" + AuthenticationTypeParser.certificateAuthority + "\"\n" +
                "  },\n" +
                "  \"twinETag\": \"AAAAAAAAAAE=\",\n" +
                "  \"tags\": {},\n" +
                "  \"properties\": {\n" +
                "    \"desired\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"0001-01-01T00:00:00Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    },\n" +
                "    \"reported\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"2017-07-26T21:44:15.80668Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //act
        ExportImportDeviceParser parser = new ExportImportDeviceParser(json);

        //assert
        assertNull(parser.getAuthentication().getThumbprint());
        assertNull(parser.getAuthentication().getSymmetricKey());
        assertEquals(AuthenticationTypeParser.certificateAuthority, parser.getAuthentication().getType());
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser shall look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
    @Test
    public void fromJsonWithSelfSignedAuthentication()
    {
        //arrange
        String expectedPrimaryThumbprint = "0000000000000000000000000000000000000000";
        String expectedSecondaryThumbprint = "1111111111111111111111111111111111111111";
        String json = "{\n" +
                "  \"id\": \"test\",\n" +
                "  \"eTag\": \"MA==\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"authentication\": {\n" +
                "    \"symmetricKey\": {\n" +
                "      \"primaryKey\": \"null\",\n" +
                "      \"secondaryKey\": \"null\"\n" +
                "    },\n" +
                "    \"x509Thumbprint\": {\n" +
                "      \"primaryThumbprint\":" + expectedPrimaryThumbprint + ",\n" +
                "      \"secondaryThumbprint\": " + expectedSecondaryThumbprint + "\n" +
                "    },\n" +
                "    \"type\": \"" + AuthenticationTypeParser.selfSigned + "\"\n" +
                "  },\n" +
                "  \"twinETag\": \"AAAAAAAAAAE=\",\n" +
                "  \"tags\": {},\n" +
                "  \"properties\": {\n" +
                "    \"desired\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"0001-01-01T00:00:00Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    },\n" +
                "    \"reported\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"2017-07-26T21:44:15.80668Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //act
        ExportImportDeviceParser parser = new ExportImportDeviceParser(json);

        //assert
        assertEquals(expectedPrimaryThumbprint, parser.getAuthentication().getThumbprint().getPrimaryThumbprint());
        assertEquals(expectedSecondaryThumbprint, parser.getAuthentication().getThumbprint().getSecondaryThumbprint());
        assertEquals(AuthenticationTypeParser.selfSigned, parser.getAuthentication().getType());
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser shall look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
    @Test
    public void fromJsonWithSASAuthentication()
    {
        //arrange
        String expectedPrimaryKey = "000000000000000000000000";
        String expectedSecondaryKey = "000000000000000000000000";
        String json = "{\n" +
                "  \"id\": \"test\",\n" +
                "  \"eTag\": \"MA==\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"authentication\": {\n" +
                "    \"symmetricKey\": {\n" +
                "      \"primaryKey\": \"" + expectedPrimaryKey + "\",\n" +
                "      \"secondaryKey\": \"" + expectedSecondaryKey + "\"\n" +
                "    },\n" +
                "    \"x509Thumbprint\": {\n" +
                "      \"primaryThumbprint\": null,\n" +
                "      \"secondaryThumbprint\": null\n" +
                "    },\n" +
                "    \"type\": \"" + AuthenticationTypeParser.sas + "\"\n" +
                "  },\n" +
                "  \"twinETag\": \"AAAAAAAAAAE=\",\n" +
                "  \"tags\": {},\n" +
                "  \"properties\": {\n" +
                "    \"desired\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"0001-01-01T00:00:00Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    },\n" +
                "    \"reported\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"2017-07-26T21:44:15.80668Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //act
        ExportImportDeviceParser parser = new ExportImportDeviceParser(json);

        //assert
        assertEquals(expectedPrimaryKey, parser.getAuthentication().getSymmetricKey().getPrimaryKey());
        assertEquals(expectedSecondaryKey, parser.getAuthentication().getSymmetricKey().getSecondaryKey());
        assertEquals(AuthenticationTypeParser.sas, parser.getAuthentication().getType());
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser shall save the ExportImportDeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForCASignedDevice()
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.setAuthentication(new AuthenticationParser());
        parser.getAuthentication().setType(AuthenticationTypeParser.certificateAuthority);

        String certificateAuthoritySignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.certificateAuthority + "\"";

        // act
        String serializedDevice = parser.toJson();

        // assert
        assertTrue(serializedDevice.contains(certificateAuthoritySignedDeviceAuthenticationJson));
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser shall save the ExportImportDeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForSelfSignedDevice()
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.setAuthentication(new AuthenticationParser());
        parser.getAuthentication().setType(AuthenticationTypeParser.selfSigned);
        parser.getAuthentication().setThumbprint(new X509ThumbprintParser("", ""));

        String selfSignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.selfSigned + "\"";

        // act
        String serializedDevice = parser.toJson();

        // assert
        assertTrue(serializedDevice.contains(selfSignedDeviceAuthenticationJson));
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser shall save the ExportImportDeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForSymmetricKeySecuredDevice() throws NoSuchAlgorithmException
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.setAuthentication(new AuthenticationParser());
        parser.getAuthentication().setType(AuthenticationTypeParser.sas);
        parser.getAuthentication().setSymmetricKey(new SymmetricKeyParser("", ""));

        String expectedJson = "{\"authentication\":{\"symmetricKey\":{\"primaryKey\":\"\",\"secondaryKey\":\"\"},\"type\":\"sas\"}}";

        // act
        String serializedDevice = parser.toJson();

        // assert
        assertEquals(expectedJson, serializedDevice);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_003: [This parser has properties for eTag, Id, status, statusReason, importMode and authenticationParser.]
    @Test
    public void testGettersAndSetters()
    {
        String deviceId = "someDevice";
        String eTag = "etag";
        String status = "Enabled";
        String statusReason = "no reason";
        String importMode = "import";
        ExportImportDeviceParser parser = new ExportImportDeviceParser();

        assertNull(parser.getAuthentication());
        assertNull(parser.getETag());
        assertNull(parser.getId());
        assertNull(parser.getImportMode());
        assertNull(parser.getStatus());
        assertNull(parser.getStatusReason());

        parser.setAuthentication(new AuthenticationParser());
        parser.setId(deviceId);
        parser.setETag(eTag);
        parser.setStatus(status);
        parser.setStatusReason(statusReason);
        parser.setImportMode(importMode);

        assertEquals(new AuthenticationParser().getType(), parser.getAuthentication().getType());
        assertEquals(new AuthenticationParser().getSymmetricKey(), parser.getAuthentication().getSymmetricKey());
        assertEquals(new AuthenticationParser().getThumbprint(), parser.getAuthentication().getThumbprint());
        assertEquals(deviceId, parser.getId());
        assertEquals(eTag, parser.getETag());
        assertEquals(status, parser.getStatus());
        assertEquals(statusReason, parser.getStatusReason());
        assertEquals(importMode, parser.getImportMode());
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_005: [This constructor will take the provided json and convert it into a new ExportImportDeviceParser and return it.]
    public void testJsonConstructor()
    {
        String primaryKey = "123451234512345123451234";
        String secondaryKey = "123451234512345123451234";
        AuthenticationTypeParser authType = AuthenticationTypeParser.sas;

        String json = "{\"authentication\":{\"symmetricKey\":{\"primaryKey\":\"" + primaryKey + "\",\"secondaryKey\":\"" + secondaryKey +"\"},\"type\":\"" + authType + "\"}}";
        ExportImportDeviceParser parser = new ExportImportDeviceParser(json);

        assertEquals(primaryKey, parser.getAuthentication().getSymmetricKey().getPrimaryKey());
        assertEquals(secondaryKey, parser.getAuthentication().getSymmetricKey().getSecondaryKey());
        assertEquals(authType, parser.getAuthentication().getType());
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_007: [If the provided id is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void idSetterCannotTakeNullArgument()
    {
        new ExportImportDeviceParser().setId(null);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_006: [If the provided authentication is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void authenticationSetterCannotTakeNullArgument()
    {
        new ExportImportDeviceParser().setAuthentication(null);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_009: [If the provided json is missing the Authentication field, or its value is empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForMissingAuthentication()
    {
        String json = "{\n" +
                "  \"id\": \"test\",\n" +
                "  \"eTag\": \"MA==\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"twinETag\": \"AAAAAAAAAAE=\",\n" +
                "  \"tags\": {},\n" +
                "  \"properties\": {\n" +
                "    \"desired\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"0001-01-01T00:00:00Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    },\n" +
                "    \"reported\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"2017-07-26T21:44:15.80668Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";

        new ExportImportDeviceParser(json);
    }


    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_008: [If the provided json is missing the Id field, or its value is empty, an IllegalArgumentException shall be thrown]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForMissingId()
    {
        String json = "{\n" +
                "  \"eTag\": \"MA==\",\n" +
                "  \"status\": \"enabled\",\n" +
                "  \"authentication\": {\n" +
                "    \"symmetricKey\": {\n" +
                "      \"primaryKey\": \"null\",\n" +
                "      \"secondaryKey\": \"null\"\n" +
                "    },\n" +
                "    \"x509Thumbprint\": {\n" +
                "      \"primaryThumbprint\":null,\n" +
                "      \"secondaryThumbprint\": null\n" +
                "    },\n" +
                "    \"type\": \"" + AuthenticationTypeParser.certificateAuthority + "\"\n" +
                "  },\n" +
                "  \"twinETag\": \"AAAAAAAAAAE=\",\n" +
                "  \"tags\": {},\n" +
                "  \"properties\": {\n" +
                "    \"desired\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"0001-01-01T00:00:00Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    },\n" +
                "    \"reported\": {\n" +
                "      \"$metadata\": {\n" +
                "        \"$lastUpdated\": \"2017-07-26T21:44:15.80668Z\"\n" +
                "      },\n" +
                "      \"$version\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}";

        new ExportImportDeviceParser(json);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_011: [If the provided json is null, empty, or cannot be parsed into an ExportImportDeviceParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForInvalidJson()
    {
        String json = "}";
        new ExportImportDeviceParser(json);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_011: [If the provided json is null, empty, or cannot be parsed into an ExportImportDeviceParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForEmptyJson()
    {
        String json = "";
        new ExportImportDeviceParser(json);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_011: [If the provided json is null, empty, or cannot be parsed into an ExportImportDeviceParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForNullJson()
    {
        String json = null;
        new ExportImportDeviceParser(json);
    }
}
