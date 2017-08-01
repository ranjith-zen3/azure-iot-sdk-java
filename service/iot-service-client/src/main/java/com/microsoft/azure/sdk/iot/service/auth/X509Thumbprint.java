package com.microsoft.azure.sdk.iot.service.auth;

import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.sdk.iot.service.Tools;

import java.util.Random;

public class X509Thumbprint
{
    @SerializedName("primaryThumbprint")
    private String primaryThumbprint;

    @SerializedName("secondaryThumbprint")
    private String secondaryThumbprint;

    private final static String THUMBPRINT_REGEX = "^([A-Fa-f0-9]{2}){20}$";
    private final static String INVALID_THUMBPRINT_MESSAGE = "Invalid format for primary/secondary thumbprint";

    /**
     * Constructor for an X509 Thumbprint that randomly generates the primary and secondary thumbprints
     */
    public X509Thumbprint()
    {
        this.primaryThumbprint = generateValidThumbprint();
        this.secondaryThumbprint = generateValidThumbprint();
    }

    /**
     * Constructor for an X509 Thumbprint with the provided primary and secondary thumbprints
     * @param primaryThumbprint the primary thumbprint
     * @param secondaryThumbprint the secondary thumbprint
     * @throws IllegalArgumentException if the provided thumbprint is an invalid format
     */
    public X509Thumbprint(String primaryThumbprint, String secondaryThumbprint)
    {
        //Codes_SRS_X509THUMBPRINT_34_010: [This constructor will throw an IllegalArgumentException if the provided thumbprints are not a valid format.]
        validateThumbprint(primaryThumbprint);
        validateThumbprint(secondaryThumbprint);

        //Codes_SRS_X509THUMBPRINT_34_006: [This constructor will create an X509Thumbprint with the provided primary thumbprint and the provided secondary thumbprint.]
        this.primaryThumbprint = primaryThumbprint;
        this.secondaryThumbprint = secondaryThumbprint;
    }

    public String getPrimaryThumbprint()
    {
        //Codes_SRS_X509THUMBPRINT_34_001: [The function shall return the primary thumbprint value of this.]
        return this.primaryThumbprint;
    }

    public String getSecondaryThumbprint()
    {
        //Codes_SRS_X509THUMBPRINT_34_002: [The function shall return the secondary thumbprint value of this.]
        return this.secondaryThumbprint;
    }

    /**
     * Setter for primary thumbprint
     * @param primaryThumbprint the thumbprint value to set
     * @throws IllegalArgumentException if the provided thumbprint is an invalid format
     */
    public void setPrimaryThumbprint(String primaryThumbprint) throws IllegalArgumentException
    {
        //Codes_SRS_X509THUMBPRINT_34_007: [If the provided thumbprint string is not the proper format, an IllegalArgumentException shall be thrown.]
        validateThumbprint(primaryThumbprint);

        //Codes_SRS_X509THUMBPRINT_34_003: [The function shall set the primary thumbprint to the given value.]
        this.primaryThumbprint = primaryThumbprint;
    }

    /**
     * Setter for secondary thumbprint
     * @param secondaryThumbprint the thumbprint value to set
     * @throws IllegalArgumentException if the provided thumbprint is an invalid format
     */
    public void setSecondaryThumbprint(String secondaryThumbprint) throws IllegalArgumentException
    {
        //Codes_SRS_X509THUMBPRINT_34_008: [If the provided thumbprint string is not the proper format, an IllegalArgumentException shall be thrown.]
        validateThumbprint(secondaryThumbprint);

        //Codes_SRS_X509THUMBPRINT_34_004: [The function shall set the secondary thumbprint to the given value.]
        this.secondaryThumbprint = secondaryThumbprint;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof X509Thumbprint)
        {
            X509Thumbprint obj = (X509Thumbprint) o;

            return (Tools.areEqual(this.getPrimaryThumbprint(), obj.getPrimaryThumbprint())
                && Tools.areEqual(this.getSecondaryThumbprint(), obj.getSecondaryThumbprint()));
        }

        return false;
    }

    /**
     * Validate the thumbprint
     * @param thumbprint The thumbprint to validate
     * @throws IllegalArgumentException if the provided thumbprint is the incorrect format
     */
    private void validateThumbprint(String thumbprint) throws IllegalArgumentException
    {
        if (thumbprint != null)
        {
            if (!thumbprint.matches(THUMBPRINT_REGEX))
            {
                throw new IllegalArgumentException(INVALID_THUMBPRINT_MESSAGE);
            }
        }
    }

    private String generateValidThumbprint()
    {
        String thumbprint = "";
        for (int i = 0; i < 40; i++)
        {
            Random rand = new Random(System.currentTimeMillis());
            thumbprint += Integer.toHexString(rand.nextInt(16));
        }

        return thumbprint;
    }
}
