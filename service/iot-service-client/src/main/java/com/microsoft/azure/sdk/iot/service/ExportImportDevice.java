/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.sdk.iot.deps.serializer.*;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;
import com.microsoft.azure.sdk.iot.service.auth.X509Thumbprint;

public class ExportImportDevice
{
    // CODES_SRS_SERVICE_SDK_JAVA_IMPORT_EXPORT_DEVICE_15_001: [The ExportImportDevice class has the following properties: Id,
    // Etag, ImportMode, Status, StatusReason, Authentication]

    @SerializedName("id")
    private String Id;

    @SerializedName("eTag")
    private String ETag;

    @SerializedName("importMode")
    private ImportMode ImportMode;

    @SerializedName("status")
    private DeviceStatus Status;

    @SerializedName("statusReason")
    private String StatusReason;

    @SerializedName("authentication")
    private Authentication Authentication;

    /**
     * Setter for device id.
     * @param id The device id.
     */
    public void setId(String id)
    {
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
    public com.microsoft.azure.sdk.iot.service.Authentication getAuthentication()
    {
        return Authentication;
    }

    /**
     * Setter for device authentication mechanism.
     * @param authentication The device authentication mechanism.
     */
    public void setAuthentication(Authentication authentication)
    {
        Authentication = authentication;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ExportImportDevice)
        {
            ExportImportDevice other = (ExportImportDevice) obj;

            if (!Tools.areEqual(this.getAuthentication(), other.getAuthentication()))
            {
                return false;
            }
            else if (!Tools.areEqual(this.getStatus(), other.getStatus()))
            {
                return false;
            }
            else if (!Tools.areEqual(this.getImportMode(), other.getImportMode()))
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

    public static ExportImportDevice fromExportImportDeviceParser(ExportImportDeviceParser parser)
    {
        ExportImportDevice device = new ExportImportDevice();
        device.ETag = parser.ETag;
        device.Id = parser.Id;
        device.StatusReason = parser.StatusReason;

        if (parser.ImportMode != null)
        {
            device.ImportMode = com.microsoft.azure.sdk.iot.service.ImportMode.valueOf(parser.ImportMode);
        }

        if (parser.Status != null)
        {
            device.Status = DeviceStatus.valueOf(parser.Status);
        }

        device.Authentication = new Authentication(AuthenticationType.valueOf(parser.Authentication.type.toString()));

        if (device.Authentication.getAuthenticationType() == AuthenticationType.certificateAuthority)
        {
            //do nothing
        }
        else if (device.Authentication.getAuthenticationType() == AuthenticationType.selfSigned)
        {
            device.Authentication.setThumbprint(new X509Thumbprint());
            device.Authentication.getThumbprint().setPrimaryThumbprint(parser.Authentication.thumbprint.primaryThumbprint);
            device.Authentication.getThumbprint().setSecondaryThumbprint(parser.Authentication.thumbprint.secondaryThumbprint);
        }
        else if (device.Authentication.getAuthenticationType() == AuthenticationType.sas)
        {
            device.Authentication.setSymmetricKey(new SymmetricKey());
            device.Authentication.getSymmetricKey().setPrimaryKey(parser.Authentication.symmetricKey.primaryKey);
            device.Authentication.getSymmetricKey().setSecondaryKey(parser.Authentication.symmetricKey.secondaryKey);
        }

        return device;
    }

    public static ExportImportDeviceParser toExportImportDeviceParser(ExportImportDevice device)
    {
        ExportImportDeviceParser parser = new ExportImportDeviceParser();
        parser.ETag = device.ETag;
        parser.Id = device.Id;
        parser.StatusReason = device.StatusReason;

        if (device.ImportMode != null)
        {
            parser.ImportMode = device.ImportMode.toString();
        }

        if (device.Status != null)
        {
            parser.Status = device.Status.toString();
        }

        if (device.Authentication != null)
        {
            parser.Authentication = new AuthenticationParser();
            if (device.getAuthentication().getAuthenticationType() != null)
            {
                parser.Authentication.type = AuthenticationTypeParser.valueOf(device.Authentication.getAuthenticationType().toString());
                if (device.getAuthentication().getAuthenticationType() == AuthenticationType.certificateAuthority)
                {
                    //do nothing
                }
                else if (device.getAuthentication().getAuthenticationType() == AuthenticationType.selfSigned)
                {
                    if (device.Authentication.getThumbprint() != null)
                    {
                        parser.Authentication.thumbprint = new X509ThumbprintParser(device.Authentication.getThumbprint().getPrimaryThumbprint(), device.Authentication.getThumbprint().getSecondaryThumbprint());
                    }
                }
                else if (device.getAuthentication().getAuthenticationType() == AuthenticationType.sas)
                {
                    if (device.Authentication.getSymmetricKey() != null)
                    {
                        parser.Authentication.symmetricKey = new SymmetricKeyParser(device.Authentication.getSymmetricKey().getPrimaryKey(), device.Authentication.getSymmetricKey().getSecondaryKey());
                    }
                }
            }
        }

        return parser;
    }
}