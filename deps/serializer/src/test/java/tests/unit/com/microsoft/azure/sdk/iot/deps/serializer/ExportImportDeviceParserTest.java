package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Code coverage:
 * 100% Methods, 100% lines
 */
public class ExportImportDeviceParserTest
{
    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser will look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
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
        ExportImportDeviceParser parser = ExportImportDeviceParser.fromJson(json);

        //assert
        assertNull(parser.Authentication.thumbprint);
        assertNull(parser.Authentication.symmetricKey);
        assertEquals(AuthenticationTypeParser.certificateAuthority, parser.Authentication.type);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser will look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
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
        ExportImportDeviceParser parser = ExportImportDeviceParser.fromJson(json);

        //assert
        assertEquals(expectedPrimaryThumbprint, parser.Authentication.thumbprint.primaryThumbprint);
        assertEquals(expectedSecondaryThumbprint, parser.Authentication.thumbprint.secondaryThumbprint);
        assertEquals(AuthenticationTypeParser.selfSigned, parser.Authentication.type);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser will look for the authentication of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
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
        ExportImportDeviceParser parser = ExportImportDeviceParser.fromJson(json);

        //assert
        assertEquals(expectedPrimaryKey, parser.Authentication.symmetricKey.primaryKey);
        assertEquals(expectedSecondaryKey, parser.Authentication.symmetricKey.secondaryKey);
        assertEquals(AuthenticationTypeParser.sas, parser.Authentication.type);
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser will save the ExportImportDeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForCASignedDevice()
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.Authentication = new AuthenticationParser();
        parser.Authentication.type = AuthenticationTypeParser.certificateAuthority;

        String certificateAuthoritySignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.certificateAuthority + "\"";

        // act
        String serializedDevice = ExportImportDeviceParser.toJson(parser);

        // assert
        assertTrue(serializedDevice.contains(certificateAuthoritySignedDeviceAuthenticationJson));
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser will save the ExportImportDeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForSelfSignedDevice()
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.Authentication = new AuthenticationParser();
        parser.Authentication.type = AuthenticationTypeParser.selfSigned;
        parser.Authentication.thumbprint = new X509ThumbprintParser("", "");

        String selfSignedDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.selfSigned + "\"";

        // act
        String serializedDevice = ExportImportDeviceParser.toJson(parser);

        // assert
        assertTrue(serializedDevice.contains(selfSignedDeviceAuthenticationJson));
    }

    //Tests_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser will save the ExportImportDeviceParser's authentication to the returned json representation]
    @Test
    public void toJsonForSymmetricKeySecuredDevice() throws NoSuchAlgorithmException
    {
        // arrange
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.Authentication = new AuthenticationParser();
        parser.Authentication.type = AuthenticationTypeParser.sas;
        parser.Authentication.symmetricKey = new SymmetricKeyParser("", "");

        String symmetricKeySecuredDeviceAuthenticationJson = "\"type\":\"" + AuthenticationTypeParser.sas + "\"";

        // act
        String serializedDevice = ExportImportDeviceParser.toJson(parser);

        // assert
        assertTrue(serializedDevice.contains(symmetricKeySecuredDeviceAuthenticationJson));
    }
}
