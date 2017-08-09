// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DeviceParser
{
    private static final String E_TAG_NAME = "etag";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(E_TAG_NAME)
    private String eTag;

    private static final String DEVICE_ID_NAME = "deviceId";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(DEVICE_ID_NAME)
    private String deviceId;

    private static final String GENERATION_ID_NAME = "generationId";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(GENERATION_ID_NAME)
    private String generationId;

    private static final String STATUS_NAME = "status";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(STATUS_NAME)
    private String status;

    private static final String STATUS_REASON = "statusReason";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(STATUS_REASON)
    private String statusReason;

    private static final String STATUS_UPDATED_TIME_NAME = "statusUpdatedTime";
    @Expose(serialize = true, deserialize = false)
    @SerializedName(STATUS_UPDATED_TIME_NAME)
    private String statusUpdatedTimeString;
    private transient Date statusUpdatedTime;

    private static final String CONNECTION_STATE_NAME = "connectionState";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(CONNECTION_STATE_NAME)
    private String connectionState;

    private static final String CONNECTION_STATE_UPDATED_TIME_NAME = "connectionStateUpdatedTime";
    @Expose(serialize = true, deserialize = false)
    @SerializedName(CONNECTION_STATE_UPDATED_TIME_NAME)
    private String connectionStateUpdatedTimeString;
    private transient Date connectionStateUpdatedTime;

    private static final String LAST_ACTIVITY_TIME_NAME = "lastActivityTime";
    @Expose(serialize = true, deserialize = false)
    @SerializedName(LAST_ACTIVITY_TIME_NAME)
    private String lastActivityTimeString;
    @Expose (serialize = false, deserialize = true)
    private transient Date lastActivityTime;

    private static final String CLOUD_TO_MESSAGE_COUNT_NAME = "cloudToDeviceMessageCount";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(CLOUD_TO_MESSAGE_COUNT_NAME)
    private long cloudToDeviceMessageCount;

    private static final String AUTHENTICATION_NAME = "authentication";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(AUTHENTICATION_NAME)
    private AuthenticationParser authenticationParser;

    private transient Gson gson = new Gson();

    private static final String DEVICE_ID_MISSING_ERROR_MESSAGE = "The provided json must contain the field for deviceId and its value may not be empty";
    private static final String AUTHENTICATION_MISSING_ERROR_MESSAGE = "The provided json must contain the field for authentication and its value may not be empty";
    private static final String DEVICE_ID_CANNOT_BE_NULL_ERROR_MESSAGE = "DeviceId cannot not be null";
    private static final String AUTHENTICATION_CANNOT_BE_NULL_ERROR_MESSAGE = "Authentication cannot not be null";
    private static final String NULL_OR_EMPTY_JSON_ERROR_MESSAGE = "The provided json cannot be null or empty";
    private static final String INVALID_JSON_ERROR_MESSAGE = "The provided json could not be parsed";

    /**
     * Converts this into json format and returns it
     * @return the json representation of this
     */
    public String toJson()
    {
        //Codes_SRS_DEVICE_PARSER_34_001: [This method shall return a json representation of this.]
        return gson.toJson(this);
    }

    /**
     * Empty constructor: Used only to keep GSON happy.
     */
    @SuppressWarnings("unused")
    public DeviceParser()
    {
    }

    /**
     * Constructor for a DeviceParser object that is built from the provided json.
     * @param json the json to build the object from
     * @throws IllegalArgumentException if the provided json is null, empty, or not the expected format
     */
    public DeviceParser(String json)
    {
        if (json == null || json.isEmpty())
        {
            //Codes_SRS_DEVICE_PARSER_34_005: [If the provided json is null or empty, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(NULL_OR_EMPTY_JSON_ERROR_MESSAGE);
        }

        DeviceParser deviceParser;
        try
        {
            deviceParser = gson.fromJson(json, DeviceParser.class);
        }
        catch (Exception e)
        {
            //Codes_SRS_DEVICE_PARSER_34_006: [If the provided json cannot be parsed into a DeviceParser object, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(INVALID_JSON_ERROR_MESSAGE);
        }

        //Codes_SRS_DEVICE_PARSER_34_011: [If the provided json is missing the DeviceId field or its value is empty, an IllegalArgumentException shall be thrown.]
        if (deviceParser.deviceId == null || deviceParser.deviceId.isEmpty())
        {
            throw new IllegalArgumentException(DEVICE_ID_MISSING_ERROR_MESSAGE);
        }

        //Codes_SRS_DEVICE_PARSER_34_012: [If the provided json is missing the authentication field or its value is empty, an IllegalArgumentException shall be thrown.]
        if (deviceParser.authenticationParser == null)
        {
            throw new IllegalArgumentException(AUTHENTICATION_MISSING_ERROR_MESSAGE);
        }

        //Codes_SRS_DEVICE_PARSER_34_002: [This constructor will create a DeviceParser object based off of the provided json.]
        this.authenticationParser = deviceParser.authenticationParser;
        this.connectionState = deviceParser.connectionState;
        this.deviceId = deviceParser.deviceId;
        this.statusReason = deviceParser.statusReason;
        this.cloudToDeviceMessageCount = deviceParser.cloudToDeviceMessageCount;
        this.connectionState = deviceParser.connectionState;
        this.generationId = deviceParser.generationId;
        this.eTag = deviceParser.eTag;

        //convert to date format
        this.lastActivityTime = ParserUtility.getSimpleDateTime(deviceParser.lastActivityTimeString);
        this.connectionStateUpdatedTime = ParserUtility.getSimpleDateTime(deviceParser.connectionStateUpdatedTimeString);
        this.statusUpdatedTime = ParserUtility.getSimpleDateTime(deviceParser.statusUpdatedTimeString);

        //status needs to begin with a capital letter for enum conversion
        String status = deviceParser.status;

        if (status != null && !status.isEmpty())
        {
            this.status = status.substring(0,1).toUpperCase() + status.substring(1);
        }

        if (this.getAuthenticationParser().getType() == AuthenticationTypeParser.certificateAuthority)
        {
            this.getAuthenticationParser().setThumbprint(null);
            this.getAuthenticationParser().setSymmetricKey(null);
        }
        else if (this.getAuthenticationParser().getType() == AuthenticationTypeParser.selfSigned)
        {
            this.getAuthenticationParser().setSymmetricKey(null);
        }
        else if (this.getAuthenticationParser().getType() == AuthenticationTypeParser.sas)
        {
            this.getAuthenticationParser().setThumbprint(null);
        }
    }

    /**
     * Getter for DeviceId
     *
     * @return The value of DeviceId
     */
    public String getDeviceId()
    {
        return deviceId;
    }

    /**
     * Setter for DeviceId
     *
     * @throws IllegalArgumentException if deviceId is null
     */
    public void setDeviceId(String deviceId) throws IllegalArgumentException
    {

        //Codes_SRS_DEVICE_PARSER_34_010: [If the provided deviceId value is null, an IllegalArgumentException shall be thrown.]
        if (deviceId == null || deviceId.isEmpty())
        {
            throw new IllegalArgumentException(DEVICE_ID_CANNOT_BE_NULL_ERROR_MESSAGE);
        }

        //Codes_SRS_DEVICE_PARSER_34_009: [This method shall set the value of deviceId to the provided value.]
        this.deviceId = deviceId;
    }

    /**
     * Getter for AuthenticationParser
     *
     * @return The value of AuthenticationParser
     */
    public AuthenticationParser getAuthenticationParser()
    {
        return authenticationParser;
    }

    /**
     * Setter for AuthenticationParser
     *
     * @throws IllegalArgumentException if authenticationParser is null
     */
    public void setAuthenticationParser(AuthenticationParser authenticationParser) throws IllegalArgumentException
    {
        //Codes_SRS_DEVICE_PARSER_34_008: [If the provided authenticationParser value is null, an IllegalArgumentException shall be thrown.]
        if (authenticationParser == null)
        {
            throw new IllegalArgumentException(AUTHENTICATION_CANNOT_BE_NULL_ERROR_MESSAGE);
        }

        //Codes_SRS_DEVICE_PARSER_34_007: [This method shall set the value of authenticationParser to the provided value.]
        this.authenticationParser = authenticationParser;
    }

    /**
     * Getter for eTag
     *
     * @return The value of eTag
     */
    public String geteTag()
    {
        return eTag;
    }

    /**
     * Setter for eTag
     */
    public void seteTag(String eTag)
    {
        this.eTag = eTag;
    }

    /**
     * Getter for GenerationId
     *
     * @return The value of GenerationId
     */
    public String getGenerationId()
    {
        return generationId;
    }

    /**
     * Setter for GenerationId
     */
    public void setGenerationId(String generationId)
    {
        this.generationId = generationId;
    }

    /**
     * Getter for Status
     *
     * @return The value of Status
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Setter for Status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * Getter for StatusReason
     *
     * @return The value of StatusReason
     */
    public String getStatusReason()
    {
        return statusReason;
    }

    /**
     * Setter for StatusReason
     */
    public void setStatusReason(String statusReason)
    {
        this.statusReason = statusReason;
    }

    /**
     * Getter for StatusUpdatedTime
     *
     * @return The value of StatusUpdatedTime
     */
    public Date getStatusUpdatedTime()
    {
        return statusUpdatedTime;
    }

    /**
     * Setter for StatusUpdatedTime
     */
    public void setStatusUpdatedTime(Date statusUpdatedTime)
    {
        this.statusUpdatedTime = statusUpdatedTime;
    }

    /**
     * Getter for ConnectionState
     *
     * @return The value of ConnectionState
     */
    public String getConnectionState()
    {
        return connectionState;
    }

    /**
     * Setter for ConnectionState
     */
    public void setConnectionState(String connectionState)
    {
        this.connectionState = connectionState;
    }

    /**
     * Getter for ConnectionStateUpdatedTime
     *
     * @return The value of ConnectionStateUpdatedTime
     */
    public Date getConnectionStateUpdatedTime()
    {
        return connectionStateUpdatedTime;
    }

    /**
     * Setter for ConnectionStateUpdatedTime
     */
    public void setConnectionStateUpdatedTime(Date connectionStateUpdatedTime)
    {
        this.connectionStateUpdatedTime = connectionStateUpdatedTime;
    }

    /**
     * Getter for LastActivityTime
     *
     * @return The value of LastActivityTime
     */
    public Date getLastActivityTime()
    {
        return lastActivityTime;
    }

    /**
     * Setter for LastActivityTime
     */
    public void setLastActivityTime(Date lastActivityTime)
    {
        this.lastActivityTime = lastActivityTime;
    }

    /**
     * Getter for CloudToDeviceMessageCount
     *
     * @return The value of CloudToDeviceMessageCount
     */
    public long getCloudToDeviceMessageCount()
    {
        return cloudToDeviceMessageCount;
    }

    /**
     * Setter for CloudToDeviceMessageCount
     */
    public void setCloudToDeviceMessageCount(long cloudToDeviceMessageCount)
    {
        this.cloudToDeviceMessageCount = cloudToDeviceMessageCount;
    }
}
