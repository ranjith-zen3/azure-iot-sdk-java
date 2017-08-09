// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExportImportDeviceParser
{
    private static final String ID_NAME = "id";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(ID_NAME)
    private String Id;

    private static final String E_TAG_NAME = "eTag";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(E_TAG_NAME)
    private String ETag;

    private static final String IMPORT_MODE_NAME = "importMode";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(IMPORT_MODE_NAME)
    private String ImportMode;

    private static final String STATUS_NAME = "status";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(STATUS_NAME)
    private String Status;

    private static final String STATUS_REASON_NAME = "statusReason";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(STATUS_REASON_NAME)
    private String StatusReason;

    private static final String AUTHENTICATION_NAME = "authentication";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(AUTHENTICATION_NAME)
    private AuthenticationParser Authentication;

    private static Gson gson = new Gson();

    private static final String NULL_OR_EMPTY_JSON_ERROR_MESSAGE = "The provided json cannot be null or empty";
    private static final String INVALID_JSON_ERROR_MESSAGE = "The provided json could not be parsed";
    private static final String MISSING_ID_FIELD = "The Id field must be present in the provided json";
    private static final String MISSING_AUTHENTICATION_FIELD = "The Authentication field must be present in the provided json";

    /**
     * Converts this into json and returns it
     * @return the json representation of this
     */
    public String toJson()
    {
        //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_001: [The parser shall save the ExportImportDeviceParser's authentication type to the returned json representation]
        return gson.toJson(this);
    }

    /**
     * Empty constructor: Used only to keep GSON happy.
     */
    @SuppressWarnings("unused")
    public ExportImportDeviceParser()
    {
    }

    /**
     * Constructor for an ExportImportDeviceParser that is built using the provided json
     * @param json the json string to build the ExportImportDeviceParser out of
     */
    public ExportImportDeviceParser(String json)
    {
        //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_011: [If the provided json is null, empty, or cannot be parsed into an ExportImportDeviceParser object, an IllegalArgumentException shall be thrown.]
        if (json == null || json.isEmpty())
        {
            throw new IllegalArgumentException(NULL_OR_EMPTY_JSON_ERROR_MESSAGE);
        }

        ExportImportDeviceParser deviceParser;
        try
        {
            //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_005: [This constructor will take the provided json and convert it into a new ExportImportDeviceParser and return it.]
            deviceParser = gson.fromJson(json, ExportImportDeviceParser.class);
        }
        catch (Exception e)
        {
            //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_011: [If the provided json is null, empty, or cannot be parsed into an ExportImportDeviceParser object, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(INVALID_JSON_ERROR_MESSAGE);
        }

        if (deviceParser.getId() == null)
        {
            //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_008: [If the provided json is missing the Id field, or its value is empty, an IllegalArgumentException shall be thrown]
            throw new IllegalArgumentException(MISSING_ID_FIELD);
        }

        if (deviceParser.getAuthentication() == null)
        {
            //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_009: [If the provided json is missing the Authentication field, or its value is empty, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(MISSING_AUTHENTICATION_FIELD);
        }

        //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_002: [The parser shall look for the Authentication Type of the serialized export import device and save it to the returned ExportImportDeviceParser instance]
        this.Authentication = deviceParser.Authentication;
        this.Id = deviceParser.Id;
        this.ImportMode = deviceParser.ImportMode;
        this.ETag = deviceParser.ETag;
        this.StatusReason = deviceParser.StatusReason;

        //status needs to begin with a capital letter for enum conversion
        String status = deviceParser.Status;
        if (status != null && !status.isEmpty())
        {
            this.Status = status.substring(0,1).toUpperCase() + status.substring(1);
        }

        if (this.getAuthentication().getType() == AuthenticationTypeParser.certificateAuthority)
        {
            this.getAuthentication().setThumbprint(null);
            this.getAuthentication().setSymmetricKey(null);
        }
        else if (this.getAuthentication().getType() == AuthenticationTypeParser.selfSigned)
        {
            this.getAuthentication().setSymmetricKey(null);
        }
        else if (this.getAuthentication().getType() == AuthenticationTypeParser.sas)
        {
            this.getAuthentication().setThumbprint(null);
        }
    }

    /**
     * Getter for Id
     *
     * @return The value of Id
     */
    public String getId()
    {
        return Id;
    }

    /**
     * Setter for Id
     *
     * @throws IllegalArgumentException if id is null
     */
    public void setId(String id) throws IllegalArgumentException
    {
        //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_007: [If the provided id is null, an IllegalArgumentException shall be thrown.]
        if (id == null)
        {
            throw new IllegalArgumentException("id");
        }

        Id = id;
    }

    /**
     * Getter for ETag
     *
     * @return The value of ETag
     */
    public String getETag()
    {
        return ETag;
    }

    /**
     * Setter for ETag
     */
    public void setETag(String ETag)
    {
        this.ETag = ETag;
    }

    /**
     * Getter for ImportMode
     *
     * @return The value of ImportMode
     */
    public String getImportMode()
    {
        return ImportMode;
    }

    /**
     * Setter for ImportMode
     */
    public void setImportMode(String importMode)
    {
        ImportMode = importMode;
    }

    /**
     * Getter for Status
     *
     * @return The value of Status
     */
    public String getStatus()
    {
        return Status;
    }

    /**
     * Setter for Status
     */
    public void setStatus(String status)
    {
        Status = status;
    }

    /**
     * Getter for StatusReason
     *
     * @return The value of StatusReason
     */
    public String getStatusReason()
    {
        return StatusReason;
    }

    /**
     * Setter for StatusReason
     */
    public void setStatusReason(String statusReason)
    {
        StatusReason = statusReason;
    }

    /**
     * Getter for Authentication
     *
     * @return The value of Authentication
     */
    public AuthenticationParser getAuthentication()
    {
        return Authentication;
    }

    /**
     * Setter for Authentication
     *
     * @throws IllegalArgumentException if authentication is null
     */
    public void setAuthentication(AuthenticationParser authentication) throws IllegalArgumentException
    {
        //Codes_SRS_EXPORTIMPORTDEVICE_PARSER_34_006: [If the provided authentication is null, an IllegalArgumentException shall be thrown.]
        if (authentication == null)
        {
            throw new IllegalArgumentException("authentication");
        }

        Authentication = authentication;
    }
}
