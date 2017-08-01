/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;
import com.microsoft.azure.sdk.iot.service.auth.X509Thumbprint;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * The Device class extends the Device class
 * implementing constructors and serialization functionality.
 */
public class Device
{
    protected final String utcTimeDefault = "0001-01-01T00:00:00";

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
        // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_008: [The function shall throw IllegalArgumentException if the device Id is empty or null]
        if (Tools.isNullOrEmpty(deviceId))
        {
            throw new IllegalArgumentException(deviceId);
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
        setPropertiesToDefaultValues();
        this.deviceId = deviceId;
        this.status = status != null ? status : DeviceStatus.Enabled;
    }

    /**
     * Create an Device instance using the given device name that uses a Certificate Authority signed certificate
     *
     * @param deviceId Name of the device (used as device id)
     * @param authenticationType - The type of authentication used by this device.
     */
    public Device(String deviceId, AuthenticationType authenticationType)
    {
        if (Tools.isNullOrEmpty(deviceId))
        {
            throw new IllegalArgumentException(deviceId);
        }

        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_009: [The function shall set the authentication to use Certificate Authority signed certificates]
        this.setAuthentication(new Authentication(authenticationType));
        setPropertiesToDefaultValues();
        this.deviceId = deviceId;
        this.status = status != null ? status : DeviceStatus.Enabled;
    }

    // Codes_SRS_SERVICE_SDK_JAVA_DEVICE_12_001: [The Device class has the following properties: Id, Etag,
    // Authentication.SymmetricKey, State, StateReason, StateUpdatedTime,
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
     *
     * @return The generationId string
     */
    public String getGenerationId()
    {
        return generationId;
    }

    /**
     * Getter for SymmetricKey object
     *
     * @return The symmetricKey object
     */
    public SymmetricKey getSymmetricKey()
    {
        return this.authentication.getSymmetricKey();
    }

    /**
     * Setter for SymmetricKey object
     *
     * @param symmetricKey
     */
    public void setSymmetricKey(SymmetricKey symmetricKey)
    {
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
        return getSymmetricKey().getPrimaryKey();
    }

    /**
     * Getter for SecondaryKey part of the SymmetricKey
     *
     * @return The secondaryKey string
     */
    public String getSecondaryKey()
    {
        return getSymmetricKey().getSecondaryKey();
    }

    /**
     * Getter for the whole X509 thumbprint
     *
     * @return The X509Thumbprint
     */
    public X509Thumbprint getThumbprint()
    {
        return authentication.getThumbprint();
    }

    /**
     * Setter for X509 thumbprint
     *
     * @param x509Thumbprint the thumbprint to set
     */
    public void setThumbprint(X509Thumbprint x509Thumbprint)
    {
        if (this.authentication == null)
        {
            this.authentication = new Authentication(x509Thumbprint);
        }
        else
        {
            this.authentication.setThumbprint(x509Thumbprint);
        }
    }

    /**
     * Getter for primary thumbprint part of the whole thumbprint
     *
     * @return The primary thumbprint string
     */
    public String getPrimaryThumbprint()
    {
        return getThumbprint().getPrimaryThumbprint();
    }

    /**
     * Getter for secondary thumbprint part of the whole thumbprint
     *
     * @return The secondary thumbprint string
     */
    public String getSecondaryThumbprint()
    {
        return getThumbprint().getSecondaryThumbprint();
    }

    /**
     * A string representing a weak ETAG version
     * of this JSON description. This is a hash.
     */
    @SerializedName("etag")
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
    @SerializedName("authentication")
    private Authentication authentication;

    /**
     * Setter for Authentication
     *
     * @param authentication - the type of authentication the device uses
     */
    public void setAuthentication(Authentication authentication)
    {
        this.authentication = authentication;
    }

    /**
     * Getter for Authentication
     *
     * @return the type of authentication that this device uses
     */
    public Authentication getAuthentication()
    {
        return this.authentication;
    }

    public AuthenticationType getAuthenticationType()
    {
        return this.authentication.getAuthenticationType();
    }

    public void setAuthenticationType(AuthenticationType type)
    {
        this.authentication.setAuthenticationType(type);
    }

    public static DeviceParser toDeviceParser(Device device)
    {
        DeviceParser deviceParser = new DeviceParser();
        deviceParser.cloudToDeviceMessageCount = device.cloudToDeviceMessageCount;
        deviceParser.connectionState = device.connectionState.toString();
        deviceParser.connectionStateUpdatedTime = device.connectionStateUpdatedTime;
        deviceParser.deviceId = device.deviceId;
        deviceParser.eTag = device.eTag;
        deviceParser.lastActivityTime = device.lastActivityTime;
        deviceParser.generationId = device.generationId;
        deviceParser.status = device.status.toString();
        deviceParser.statusReason = device.statusReason;
        deviceParser.statusUpdatedTime = device.statusUpdatedTime;

        deviceParser.authenticationParser = new AuthenticationParser();
        deviceParser.authenticationParser.type = AuthenticationTypeParser.valueOf(device.authentication.getAuthenticationType().toString());

        if (device.authentication.getAuthenticationType() == AuthenticationType.certificateAuthority)
        {
            // do nothing else
        }
        else if (device.authentication.getAuthenticationType() == AuthenticationType.selfSigned)
        {
            deviceParser.authenticationParser.thumbprint = new X509ThumbprintParser(device.getPrimaryThumbprint(), device.getSecondaryThumbprint());
        }
        else if (device.authentication.getAuthenticationType() == AuthenticationType.sas)
        {
            deviceParser.authenticationParser.symmetricKey = new SymmetricKeyParser(device.getPrimaryKey(), device.getSecondaryKey());
        }

        return  deviceParser;
    }

    public static Device fromDeviceParser(DeviceParser deviceParser) throws IllegalArgumentException
    {
        if (deviceParser.authenticationParser == null || deviceParser.authenticationParser.type == null)
        {
            throw new IllegalArgumentException("deviceParser must have an authentication type assigned");
        }

        if (deviceParser.deviceId == null)
        {
            throw new IllegalArgumentException("deviceParser must have a deviceId assigned");
        }

        AuthenticationType authenticationType = AuthenticationType.valueOf(deviceParser.authenticationParser.type.toString());
        Device device = new Device(deviceParser.deviceId, authenticationType);

        device.cloudToDeviceMessageCount = deviceParser.cloudToDeviceMessageCount;
        device.connectionStateUpdatedTime = deviceParser.connectionStateUpdatedTime;
        device.deviceId = deviceParser.deviceId;
        device.eTag = deviceParser.eTag;
        device.lastActivityTime = deviceParser.lastActivityTime;
        device.generationId = deviceParser.generationId;
        device.statusReason = deviceParser.statusReason;
        device.statusUpdatedTime = deviceParser.statusUpdatedTime;

        if (deviceParser.status != null)
        {
            device.status = DeviceStatus.valueOf(deviceParser.status);
        }

        if (deviceParser.connectionState != null)
        {
            device.connectionState = DeviceConnectionState.valueOf(deviceParser.connectionState);
        }

        if (authenticationType == AuthenticationType.certificateAuthority)
        {
            //do nothing
        }
        else if (authenticationType == AuthenticationType.selfSigned)
        {
            if (deviceParser.authenticationParser.thumbprint != null && deviceParser.authenticationParser.thumbprint.primaryThumbprint != null && deviceParser.authenticationParser.thumbprint.secondaryThumbprint != null)
            {
                device.setThumbprint(new X509Thumbprint(deviceParser.authenticationParser.thumbprint.primaryThumbprint, deviceParser.authenticationParser.thumbprint.secondaryThumbprint));
            }
            else
            {
                device.setThumbprint(new X509Thumbprint());
            }
        }
        else if (authenticationType == AuthenticationType.sas)
        {
            device.setSymmetricKey(new SymmetricKey());
            if (deviceParser.authenticationParser.symmetricKey != null && deviceParser.authenticationParser.symmetricKey.primaryKey != null)
            {
                device.getSymmetricKey().setPrimaryKey(deviceParser.authenticationParser.symmetricKey.primaryKey);
                device.getSymmetricKey().setSecondaryKey(deviceParser.authenticationParser.symmetricKey.secondaryKey);
            }
        }

        return device;
    }

    /*
     * Set default properties for a device
     */
    private void setPropertiesToDefaultValues()
    {
        this.generationId = "";
        this.eTag = "";
        this.statusReason = "";
        this.statusUpdatedTime = utcTimeDefault;
        this.connectionState = DeviceConnectionState.Disconnected;
        this.connectionStateUpdatedTime = utcTimeDefault;
        this.lastActivityTime = utcTimeDefault;
        this.cloudToDeviceMessageCount = 0;
        this.setForceUpdate(false);
    }
}
