package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

public class ExportImportDeviceParser
{
    @SerializedName("id")
    public String Id;

    @SerializedName("eTag")
    public String ETag;

    @SerializedName("importMode")
    public String ImportMode;

    @SerializedName("status")
    public String Status;

    @SerializedName("statusReason")
    public String StatusReason;

    @SerializedName("authentication")
    public AuthenticationParser Authentication;

    private static Gson gson = new Gson();

    protected final static String AUTHENTICATION_JSON_LABEL = "authentication";
    protected final static String AUTHENTICATION_TYPE_JSON_LABEL = "type";
    protected final static String SYMMETRIC_KEY_JSON_LABEL = "symmetricKey";
    protected final static String X509_THUMBPRINT_JSON_LABEL = "x509Thumbprint";

    public static String toJson(ExportImportDeviceParser device)
    {
        //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser will save the ExportImportDeviceParser's authentication type to the returned json representation]
        return gson.toJson(device);
    }

    public static ExportImportDeviceParser fromJson(String json)
    {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonRoot = jsonParser.parse(json);
        JsonElement deviceElement = jsonRoot.getAsJsonObject();
        ExportImportDeviceParser deviceParser = gson.fromJson(deviceElement, ExportImportDeviceParser.class);
        JsonElement authenticationElement = jsonRoot.getAsJsonObject().get(AUTHENTICATION_JSON_LABEL);
        JsonElement authenticationTypeElement = authenticationElement.getAsJsonObject().get(AUTHENTICATION_TYPE_JSON_LABEL);
        AuthenticationTypeParser authenticationTypeParser = gson.fromJson(authenticationTypeElement, AuthenticationTypeParser.class);

        //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser will look for the Authentication Type of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
        deviceParser.Authentication = new AuthenticationParser();
        deviceParser.Authentication.type = authenticationTypeParser;
        if (authenticationTypeParser == AuthenticationTypeParser.certificateAuthority)
        {
            // do nothing
        }
        else if (authenticationTypeParser == AuthenticationTypeParser.selfSigned)
        {
            JsonElement x509ThumbprintElement = authenticationElement.getAsJsonObject().get(X509_THUMBPRINT_JSON_LABEL);
            X509ThumbprintParser x509Thumbprint = gson.fromJson(x509ThumbprintElement, X509ThumbprintParser.class);
            deviceParser.Authentication.thumbprint = x509Thumbprint;
        }
        else if (authenticationTypeParser == AuthenticationTypeParser.sas)
        {
            JsonElement symmetricKeyElement = authenticationElement.getAsJsonObject().get(SYMMETRIC_KEY_JSON_LABEL);
            SymmetricKeyParser symmetricKeyParser = gson.fromJson(symmetricKeyElement, SymmetricKeyParser.class);
            deviceParser.Authentication.symmetricKey = symmetricKeyParser;
        }

        //status needs to begin with a capital letter for enum conversion
        String status = deviceParser.Status;
        if (status != null && !status.equals(""))
        {
            deviceParser.Status = status.substring(0,1).toUpperCase() + status.substring(1);
        }

        return deviceParser;
    }
}
