/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.auth.Authentication;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;

import java.security.NoSuchAlgorithmException;

/**
 * The Device class extends the Device class
 * implementing constructors and serialization functionality.
 */
public class Device
{
    protected final String utcTimeDefault = "0001-01-01T00:00:00.0000Z";
    protected final String offsetTimeDefault = "0001-01-01T00:00:00-00:00";

    private static final String ILLEGAL_DEVICE_ID_ARGUMENT_ERROR_MESSAGE = "The provided device Id must not be null or empty";
    private static final String ILLEGAL_AUTHENTICATION_TYPE_ARGUMENT_ERROR_MESSAGE = "The provided authentication type must not be null";
    private static final String ILLEGAL_THUMBPRINT_ERROR_MESSAGE = "Thumbprint may not be null or empty";

    /**
     * Static create function
     * Creates device object using the given name.
     * If input device status and symmetric key are null then they will be auto generated.
     *
     * @param deviceId - String containing the device name
     * @param status - Device status. If parameter is null, then the status will be set to Enabled.
     * @param symmetricKey - Device key. If parameter is null, then the key will be auto generated.
     * @return Device object
     * @throws IllegalArgumentException This exception is thrown if {@code deviceId} is {@code null} or empty.
     * @throws NoSuchAlgorithmException This exception is thrown if the encryption method is not supported by the keyGenerator
     */
    public static Device createFromId(String deviceId, DeviceStatus status, SymmetricKey symmetricKey)
            throws IllegalArgumentException, NoSuchAlgorithmException
    {
        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_12_002: [The function shall throw IllegalArgumentException if the input string is empty or null]
        if (Tools.isNullOrEmpty(deviceId))
        {
            throw new IllegalArgumentException(deviceId);
        }

        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_12_003: [The function shall create a new instance
        // of Device using the given deviceId and return it]
        return new Device(deviceId, status, symmetricKey);
    }

    /**
     * Static create function
     * Creates device object using the given name that will use a Certificate Authority signed certificate for authentication.
     * If input device status is null then it will be auto generated.
     *
     * @param deviceId - String containing the device name
     * @param authenticationType - The type of authentication used by this device.
     * @return Device object
     * @throws IllegalArgumentException This exception is thrown if {@code deviceId} is {@code null} or empty.
     */
    public static Device createDevice(String deviceId, AuthenticationType authenticationType)
    {
        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
        if (Tools.isNullOrEmpty(deviceId))
        {
            throw new IllegalArgumentException(ILLEGAL_DEVICE_ID_ARGUMENT_ERROR_MESSAGE);
        }

        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
        if (authenticationType == null)
        {
            throw new IllegalArgumentException(ILLEGAL_AUTHENTICATION_TYPE_ARGUMENT_ERROR_MESSAGE);
        }

        return new Device(deviceId, authenticationType);
    }

    /**
     * Create an Device instance using the given device name
     *
     * @param deviceId Name of the device (used as device id)
     * @param status - Device status. If parameter is null, then the status will be set to Enabled.
     * @param symmetricKey - Device key. If parameter is null, then the key will be auto generated.
     * @throws NoSuchAlgorithmException This exception is thrown if the encryption method is not supported by the keyGenerator
     */
    protected Device(String deviceId, DeviceStatus status, SymmetricKey symmetricKey)
            throws NoSuchAlgorithmException, IllegalArgumentException
    {
        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_12_004: [The constructor shall throw IllegalArgumentException
        // if the input string is empty or null]
        if (Tools.isNullOrEmpty(deviceId))
        {
            throw new IllegalArgumentException();
        }

        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_15_007: [The constructor shall store
        // the input device status and symmetric key into a member variable]
        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_12_005: [If the input symmetric key is empty, the constructor shall create
        // a new SymmetricKey instance using AES encryption and store it into a member variable]
        if (symmetricKey == null)
        {
            this.authentication = new Authentication(AuthenticationType.sas);
        }
        else
        {
            this.authentication = new Authentication(symmetricKey);
        }

        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_12_006: [The constructor shall initialize all properties to default values]
        this.setPropertiesToDefaultValues();
        this.deviceId = deviceId;
        this.status = status != null ? status : DeviceStatus.Enabled;
    }

    /**
     * Create an Device instance using the given device name that uses a Certificate Authority signed certificate
     *
     * @param deviceId Name of the device (used as device id)
     * @param authenticationType - The type of authentication used by this device.
     */
    private Device(String deviceId, AuthenticationType authenticationType)
    {
        if (Tools.isNullOrEmpty(deviceId))
        {
            //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
            throw new IllegalArgumentException(ILLEGAL_DEVICE_ID_ARGUMENT_ERROR_MESSAGE);
        }

        if (authenticationType == null)
        {
            //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall throw IllegalArgumentException if the provided deviceId or authenticationType is empty or null.]
            throw new IllegalArgumentException(ILLEGAL_AUTHENTICATION_TYPE_ARGUMENT_ERROR_MESSAGE);
        }

        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_011: [If the provided authenticationType is certificate authority, no symmetric key shall be generated and no thumbprint shall be generated]
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_012: [If the provided authenticationType is sas, a symmetric key shall be generated but no thumbprint shall be generated]
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_013: [If the provided authenticationType is self signed, a thumbprint shall be generated but no symmetric key shall be generated]
        this.authentication = new Authentication(authenticationType);
        this.setPropertiesToDefaultValues();
        this.deviceId = deviceId;
    }

    // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_12_001: [The Device class has the following properties: Id, Etag,
    // SymmetricKey, State, StateReason, StateUpdatedTime,
    // ConnectionState, ConnectionStateUpdatedTime, LastActivityTime, symmetricKey, thumbprint, status, authentication]

    /**
     * Device name
     * A case-sensitive string (up to 128 char long)
     * of ASCII 7-bit alphanumeric chars
     * + {'-', ':', '.', '+', '%', '_', '#', '*', '?', '!', '(', ')', ',', '=', '@', ';', '$', '''}.
     */
    protected String deviceId;

    /**
     * Getter for device name
     *
     * @return The deviceId string
     */
    public String getDeviceId()
    {
        return deviceId;
    }

    /**
     * Device generation Id
     */
    protected String generationId;

    /**
     * Getter for GenerationId
     * @return The generationId string
     */
    public String getGenerationId()
    {
        return generationId;
    }

    /**
     * Getter for SymmetricKey object
     * @return The symmetricKey object
     */
    public SymmetricKey getSymmetricKey()
    {
        if (this.authentication == null)
        {
            return null;
        }

        return this.authentication.getSymmetricKey();
    }

    /**
     * Setter for SymmetricKey object
     *
     * @param symmetricKey
     * @throws IllegalArgumentException if the provided symmetric key is null
     */
    public void setSymmetricKey(SymmetricKey symmetricKey) throws  IllegalArgumentException
    {
        if (symmetricKey == null)
        {
            throw new IllegalArgumentException("symmetricKey");
        }

        if (this.authentication == null)
        {
            this.authentication = new Authentication(symmetricKey);
        }
        else
        {
            this.authentication.setSymmetricKey(symmetricKey);
        }
    }

    /**
     * Getter for PrimaryKey part of the SymmetricKey
     *
     * @return The primaryKey string
     */
    public String getPrimaryKey()
    {
        if (getSymmetricKey() == null)
        {
            return null;
        }

        return getSymmetricKey().getPrimaryKey();
    }

    /**
     * Getter for SecondaryKey part of the SymmetricKey
     *
     * @return The secondaryKey string
     */
    public String getSecondaryKey()
    {
        if (getSymmetricKey() == null)
        {
            return null;
        }

        return getSymmetricKey().getSecondaryKey();
    }

    /**
     * Setter for X509 thumbprint
     * @param primaryThumbprint the primary thumbprint to set
     * @param secondaryThumbprint the secondary thumbprint to set
     * @throws IllegalArgumentException if primaryThumbprint or secondaryThumbprint is null or empty
     */
    public void setThumbprint(String primaryThumbprint, String secondaryThumbprint)
    {
        if (Tools.isNullOrEmpty(primaryThumbprint) || Tools.isNullOrEmpty(secondaryThumbprint))
        {
            throw new IllegalArgumentException(ILLEGAL_THUMBPRINT_ERROR_MESSAGE);
        }

        if (this.authentication == null)
        {
            this.authentication = new Authentication(AuthenticationType.selfSigned);
        }

        this.authentication.setPrimaryThumbprint(primaryThumbprint);
        this.authentication.setSecondaryThumbprint(secondaryThumbprint);
    }

    /**
     * Getter for primary thumbprint part of the whole thumbprint
     *
     * @return The primary thumbprint string
     */
    public String getPrimaryThumbprint()
    {
        if (this.authentication == null)
        {
            return null;
        }

        return authentication.getPrimaryThumbprint();
    }

    /**
     * Getter for secondary thumbprint part of the whole thumbprint
     *
     * @return The secondary thumbprint string
     */
    public String getSecondaryThumbprint()
    {
        if (this.authentication == null)
        {
            return null;
        }

        return authentication.getSecondaryThumbprint();
    }

    /**
     * A string representing a weak ETAG version
     * of this JSON description. This is a hash.
     */
    protected String eTag;

    /**
     * Getter for eTag
     *
     * @return The eTag string
     */
    public String geteTag()
    {
        return eTag;
    }

    /**
     * "Enabled", "Disabled".
     * If "Enabled", this device is authorized to connect.
     * If "Disabled" this device cannot receive or send messages, and statusReason must be set.
     */
    protected DeviceStatus status;

    /**
     * Getter for DeviceStatus object
     * @return The deviceStatus object
     */
    public DeviceStatus getStatus()
    {
        return status;
    }

    /**
     * Setter for DeviceStatus object
     *
     * @param status
     */
    public void setStatus(DeviceStatus status)
    {
        this.status = status;
    }

    /**
     * A 128 char long string storing the reason of suspension.
     * (all UTF-8 chars allowed).
     */
    protected String statusReason;

    /**
     * Getter for status reason
     *
     * @return The statusReason string
     */
    public String getStatusReason()
    {
        return statusReason;
    }

    /**
     * Datetime of last time the state was updated.
     */
    protected String statusUpdatedTime;

    /**
     * Getter for status updated time string
     *
     * @return The string containing the time when the statusUpdated parameter was updated
     */
    public String getStatusUpdatedTime()
    {
        return statusUpdatedTime;
    }

    /**
     * Status of the device:
     * {"connected" | "disconnected"}
     */
    protected DeviceConnectionState connectionState;

    /**
     * Getter for connection state
     *
     * @return The connectionState string
     */
    public DeviceConnectionState getConnectionState()
    {
        return connectionState;
    }

    /**
     * Datetime of last time the connection state was updated.
     */
    protected String connectionStateUpdatedTime;

    /**
     * Getter for connection state updated time
     *
     * @return The string containing the time when the connectionState parameter was updated
     */
    public String getConnectionStateUpdatedTime()
    {
        return connectionStateUpdatedTime;
    }

    /**
     * Datetime of last time the device authenticated, received, or sent a message.
     */
    protected String lastActivityTime;

    /**
     * Getter for last activity time
     *
     * @return The string containing the time when the lastActivity parameter was updated
     */
    public String getLastActivityTime()
    {
        return lastActivityTime;
    }

    /**
     * Number of messages received by the device
     */
    protected long cloudToDeviceMessageCount;

    /**
     * Getter for cloud to device message count
     *
     * @return The string containing the time when the cloudToDeviceMessageCount parameter was updated
     */
    public long getCloudToDeviceMessageCount()
    {
        return cloudToDeviceMessageCount;
    }

    /**
     * Flip-flop helper for sending a forced update
     */
    private Boolean forceUpdate;

    /**
     * Setter for force update boolean
     *
     * @param forceUpdate - Boolean controlling if the update should be forced or not
     */
    public void setForceUpdate(Boolean forceUpdate)
    {
        if (forceUpdate == null)
        {
            throw new IllegalArgumentException();
        }

        this.forceUpdate = forceUpdate;
    }

    /*
    * Specifies whether this device uses a key for authentication, an X509 certificate, or something else
    */
    protected Authentication authentication;

    public AuthenticationType getAuthenticationType()
    {
        if (this.authentication == null)
        {
            return null;
        }

        return this.authentication.getAuthenticationType();
    }

    /**
     * Converts this into a DeviceParser object. To serialize a Device object, it must first be converted to a DeviceParser object.
     * @return the DeviceParser object that can be serialized.
     */
    public DeviceParser toDeviceParser()
    {
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_016: [This method shall return a new instance of a DeviceParser object that is populated using the properties of this.]
        DeviceParser deviceParser = new DeviceParser();
        deviceParser.setCloudToDeviceMessageCount(this.cloudToDeviceMessageCount);
        deviceParser.setConnectionState(this.connectionState.toString());
        deviceParser.setConnectionStateUpdatedTime(ParserUtility.getSimpleDateTime(this.connectionStateUpdatedTime));
        deviceParser.setDeviceId(this.deviceId);
        deviceParser.seteTag(this.eTag);
        deviceParser.setLastActivityTime(ParserUtility.getSimpleDateTime(this.lastActivityTime));
        deviceParser.setGenerationId(this.generationId);
        deviceParser.setStatus(this.status.toString());
        deviceParser.setStatusReason(this.statusReason);
        deviceParser.setStatusUpdatedTime(ParserUtility.getSimpleDateTime(this.statusUpdatedTime));

        deviceParser.setAuthenticationParser(new AuthenticationParser());
        deviceParser.getAuthenticationParser().setType(AuthenticationTypeParser.valueOf(this.authentication.getAuthenticationType().toString()));

        if (this.authentication.getAuthenticationType() == AuthenticationType.certificateAuthority)
        {
            // do nothing else
        }
        else if (this.authentication.getAuthenticationType() == AuthenticationType.selfSigned)
        {
            deviceParser.getAuthenticationParser().setThumbprint(new X509ThumbprintParser(this.getPrimaryThumbprint(), this.getSecondaryThumbprint()));
        }
        else if (this.authentication.getAuthenticationType() == AuthenticationType.sas)
        {
            deviceParser.getAuthenticationParser().setSymmetricKey(new SymmetricKeyParser(this.getPrimaryKey(), this.getSecondaryKey()));
        }

        return  deviceParser;
    }

    /**
     * Retrieves information from the provided parser and saves it to this. All information on this will be overwritten.
     * @param parser the parser to read from
     */
    public Device(DeviceParser parser) throws IllegalArgumentException
    {
        if (parser.getAuthenticationParser() == null || parser.getAuthenticationParser().getType() == null)
        {
            //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_015: [If the provided parser is missing a value for its authentication or its device Id, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException("deviceParser must have an authentication type assigned");
        }

        if (parser.getDeviceId() == null)
        {
            //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_015: [If the provided parser is missing a value for its authentication or its device Id, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException("deviceParser must have a deviceId assigned");
        }

        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_014: [This constructor will create a new Device object using the values within the provided parser.]
        AuthenticationType authenticationType = AuthenticationType.valueOf(parser.getAuthenticationParser().getType().toString());

        this.deviceId = parser.getDeviceId();
        this.authentication = new Authentication(authenticationType);

        this.cloudToDeviceMessageCount = parser.getCloudToDeviceMessageCount();
        this.deviceId = parser.getDeviceId();
        this.eTag = parser.geteTag();
        this.generationId = parser.getGenerationId();
        this.statusReason = parser.getStatusReason();

        if (parser.getConnectionStateUpdatedTime() != null)
        {
            this.connectionStateUpdatedTime = ParserUtility.getSimpleDateStringFromDate(parser.getConnectionStateUpdatedTime());
        }

        if (parser.getStatusUpdatedTime() != null)
        {
            this.statusUpdatedTime = ParserUtility.getSimpleDateStringFromDate(parser.getStatusUpdatedTime());
        }

        if (parser.getLastActivityTime() != null)
        {
            this.lastActivityTime = ParserUtility.getSimpleDateStringFromDate(parser.getLastActivityTime());
        }

        if (parser.getStatus() != null)
        {
            this.status = DeviceStatus.valueOf(parser.getStatus());
        }

        if (parser.getConnectionState() != null)
        {
            this.connectionState = DeviceConnectionState.valueOf(parser.getConnectionState());
        }

        this.authentication = new Authentication(authenticationType);
        if (authenticationType == AuthenticationType.certificateAuthority)
        {
            //do nothing
        }
        else if (authenticationType == AuthenticationType.selfSigned)
        {
            if (parser.getAuthenticationParser().getThumbprint() != null
                    && parser.getAuthenticationParser().getThumbprint().getPrimaryThumbprint() != null
                    && parser.getAuthenticationParser().getThumbprint().getSecondaryThumbprint() != null)
            {
                this.setThumbprint(parser.getAuthenticationParser().getThumbprint().getPrimaryThumbprint(), parser.getAuthenticationParser().getThumbprint().getSecondaryThumbprint());
            }
        }
        else if (authenticationType == AuthenticationType.sas)
        {
            if (parser.getAuthenticationParser().getSymmetricKey().getPrimaryKey() != null
                    && parser.getAuthenticationParser().getSymmetricKey().getSecondaryKey() != null)
            {
                this.getSymmetricKey().setPrimaryKey(parser.getAuthenticationParser().getSymmetricKey().getPrimaryKey());
                this.getSymmetricKey().setSecondaryKey(parser.getAuthenticationParser().getSymmetricKey().getSecondaryKey());
            }
        }
    }

    /*
     * Set default properties for a device
     */
    private void setPropertiesToDefaultValues()
    {
        this.status = DeviceStatus.Enabled;
        this.generationId = "";
        this.eTag = "";
        this.statusReason = "";
        this.statusUpdatedTime = utcTimeDefault;
        this.connectionState = DeviceConnectionState.Disconnected;
        this.connectionStateUpdatedTime = utcTimeDefault;
        this.lastActivityTime = offsetTimeDefault;
        this.cloudToDeviceMessageCount = 0;
        this.setForceUpdate(false);
    }
}
