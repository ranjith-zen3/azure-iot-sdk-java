/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.auth.Authentication;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;

import java.util.Random;

public class ExportImportDevice
{
    // CODES_SRS_SERVICE_SDK_JAVA_IMPORT_EXPORT_DEVICE_15_001: [The ExportImportDevice class has the following properties: Id,
    // Etag, ImportMode, Status, StatusReason, Authentication]

    private String Id;
    private String ETag;
    private ImportMode ImportMode;
    private DeviceStatus Status;
    private String StatusReason;
    private Authentication Authentication;

    private final int RANDOM_DEVICE_ID_NUMBER_UPPER_LIMIT = 2000000000;
    private final int RANDOM_DEVICE_ID_NUMBER_LOWER_LIMIT = 1000000000;

    /**
     * Default constructor for an ExportImportDevice object. Randomly generates a device ID and uses a randomly generated shared access signature for authentication
     */
    public ExportImportDevice()
    {
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_050: [This constructor will automatically set the authentication type of this object to be sas, and will generate a deviceId and symmetric key.]
        this.Authentication = new Authentication(AuthenticationType.sas);

        Random rand = new Random(System.currentTimeMillis());
        int randomDeviceIdNumber = rand.nextInt(RANDOM_DEVICE_ID_NUMBER_UPPER_LIMIT) + RANDOM_DEVICE_ID_NUMBER_LOWER_LIMIT;
        this.Id = "exportImportDevice_" + randomDeviceIdNumber;
    }

    /**
     * Constructor for an ExportImportDevice object.
     * @param deviceId the id of the new device
     * @param authenticationType the type of authentication to be used. For shared access signature and self signed x.509, all keys will be generated automatically.
     */
    public ExportImportDevice(String deviceId, AuthenticationType authenticationType)
    {
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_051: [This constructor will save the provided deviceId and authenticationType to itself.]
        this.Authentication = new Authentication(authenticationType);
        this.Id = deviceId;
    }

    /**
     * Setter for device id.
     * @param id The device id.
     * @throws IllegalArgumentException if the provided id is null
     */
    public void setId(String id) throws IllegalArgumentException
    {
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_055: [If the provided id is null, an IllegalArgumentException shall be thrown.]
        if (id == null)
        {
            throw new IllegalArgumentException("The provided id may not be null");
        }

        Id = id;
    }

    /**
     * Getter for device id.
     * @return The device id.
     */
    public String getId()
    {
        return this.Id;
    }

    /**
     * Getter for device eTag.
     * @return The device eTag.
     */
    public String getETag()
    {
        return ETag;
    }

    /**
     * Setter for device eTag.
     * @param ETag The device eTag.
     */
    public void setETag(String ETag)
    {
        this.ETag = ETag;
    }

    /**
     * Getter for device import mode.
     * @return The device import mode.
     */
    public ImportMode getImportMode()
    {
        return ImportMode;
    }

    /**
     * Setter for device import mode.
     * @param importMode The device import mode.
     */
    public void setImportMode(ImportMode importMode)
    {
        ImportMode = importMode;
    }

    /**
     * Getter for device status.
     * @return The device status.
     */
    public DeviceStatus getStatus()
    {
        return Status;
    }

    /**
     * Setter for device status.
     * @param status The device status.
     */
    public void setStatus(DeviceStatus status)
    {
        Status = status;
    }

    /**
     * Getter for device status reason.
     * @return The device status reason.
     */
    public String getStatusReason()
    {
        return StatusReason;
    }

    /**
     * Setter for device status reason.
     * @param statusReason The device status reason.
     */
    public void setStatusReason(String statusReason)
    {
        StatusReason = statusReason;
    }

    /**
     * Getter for device authentication mechanism.
     * @return The device authentication mechanism.
     */
    public Authentication getAuthentication()
    {
        return Authentication;
    }

    /**
     * Setter for device authentication mechanism.
     * @param authentication The device authentication mechanism.
     * @throws IllegalArgumentException if the provided authentication is null
     */
    public void setAuthentication(Authentication authentication) throws IllegalArgumentException
    {
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_056: [If the provided authentication is null, an IllegalArgumentException shall be thrown.]
        if (authentication == null)
        {
            throw new IllegalArgumentException("The provided authentication object may not be null");
        }

        Authentication = authentication;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof ExportImportDevice)
        {
            ExportImportDevice otherExportImportDevice = (ExportImportDevice) other;

            if (!Tools.areEqual(this.getAuthentication(), otherExportImportDevice.getAuthentication()))
            {
                return false;
            }
            else if (!Tools.areEqual(this.getStatus(), otherExportImportDevice.getStatus()))
            {
                return false;
            }
            else if (!Tools.areEqual(this.getImportMode(), otherExportImportDevice.getImportMode()))
            {
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        int result = Id.hashCode();
        result = 31 * result + Authentication.hashCode();
        return result;
    }

    /**
     * Retrieves information from the provided parser and saves it to this. All information on this will be overwritten.
     * @param parser the parser to read from
     */
    public ExportImportDevice(ExportImportDeviceParser parser)
    {
        if (parser.getId() == null)
        {
            //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_053: [If the provided parser does not have values for the properties deviceId or authentication, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException("The Id property of the parser object may not be null");
        }

        if (parser.getAuthentication() == null)
        {
            //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_053: [If the provided parser does not have values for the properties deviceId or authentication, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException("The Authentication property of the parser object may not be null");
        }

        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_052: [This constructor will use the properties of the provided parser object to set the new ExportImportDevice's properties.]
        this.ETag = parser.getETag();
        this.Id = parser.getId();
        this.StatusReason = parser.getStatusReason();

        if (parser.getImportMode() != null)
        {
            this.ImportMode = com.microsoft.azure.sdk.iot.service.ImportMode.valueOf(parser.getImportMode());
        }

        if (parser.getStatus() != null)
        {
            this.Status = DeviceStatus.valueOf(parser.getStatus());
        }

        this.Authentication = new Authentication(AuthenticationType.valueOf(parser.getAuthentication().getType().toString()));
        if (this.Authentication.getAuthenticationType() == AuthenticationType.certificateAuthority)
        {
            //do nothing
        }
        else if (this.Authentication.getAuthenticationType() == AuthenticationType.selfSigned)
        {
            this.Authentication = new Authentication(
                    parser.getAuthentication().getThumbprint().getPrimaryThumbprint(),
                    parser.getAuthentication().getThumbprint().getSecondaryThumbprint());
        }
        else if (this.Authentication.getAuthenticationType() == AuthenticationType.sas)
        {
            this.Authentication.getSymmetricKey().setPrimaryKey(parser.getAuthentication().getSymmetricKey().getPrimaryKey());
            this.Authentication.getSymmetricKey().setSecondaryKey(parser.getAuthentication().getSymmetricKey().getSecondaryKey());
        }
    }

    /**
     * Converts this into a ExportImportDeviceParser object. To serialize a ExportImportDevice object, it must first be converted to a ExportImportDeviceParser object.
     * @return the ExportImportDeviceParser object that can be serialized.
     */
    public ExportImportDeviceParser toExportImportDeviceParser()
    {
        //Codes_SRS_SERVICE_SDK_JAVA_DEVICE_34_054: [This method shall convert this into an ExportImportDeviceParser object and return it.]
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.setETag(this.ETag);
        parser.setId(this.Id);
        parser.setStatusReason(this.StatusReason);

        if (this.ImportMode != null)
        {
            parser.setImportMode(this.ImportMode.toString());
        }

        if (this.Status != null)
        {
            parser.setStatus(this.Status.toString());
        }

        if (this.Authentication != null)
        {
            parser.setAuthentication(new AuthenticationParser());
            if (this.getAuthentication().getAuthenticationType() != null)
            {
                parser.getAuthentication().setType(AuthenticationTypeParser.valueOf(this.Authentication.getAuthenticationType().toString()));
                if (this.getAuthentication().getAuthenticationType() == AuthenticationType.certificateAuthority)
                {
                    //do nothing
                }
                else if (this.getAuthentication().getAuthenticationType() == AuthenticationType.selfSigned)
                {
                    if (this.Authentication.getPrimaryThumbprint() != null && this.Authentication.getSecondaryThumbprint() != null)
                    {
                        parser.getAuthentication().setThumbprint(new X509ThumbprintParser(
                                this.Authentication.getPrimaryThumbprint(),
                                this.Authentication.getSecondaryThumbprint()));
                    }
                }
                else if (this.getAuthentication().getAuthenticationType() == AuthenticationType.sas)
                {
                    if (this.Authentication.getSymmetricKey() != null)
                    {
                        parser.getAuthentication().setSymmetricKey(new SymmetricKeyParser(
                                this.Authentication.getSymmetricKey().getPrimaryKey(),
                                this.Authentication.getSymmetricKey().getSecondaryKey()));
                    }
                }
            }
        }

        return parser;
    }
}