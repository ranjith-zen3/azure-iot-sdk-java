/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.service.auth;

import com.microsoft.azure.sdk.iot.service.auth.Authentication;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;
import mockit.Deencapsulation;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Code coverage:
 * 100% Methods
 * 98% lines
 */
@RunWith(JMockit.class)
public class AuthenticationTest
{
    String expectedPrimaryThumbprint;
    String expectedSecondaryThumbprint;
    private SymmetricKey expectedSymmetricKey;

    @Before
    public void setUp()
    {
        expectedPrimaryThumbprint = "0000000000000000000000000000000000000000";
        expectedSecondaryThumbprint = "1111111111111111111111111111111111111111";
        expectedSymmetricKey = Deencapsulation.newInstance(SymmetricKey.class, new Class[]{});
        Deencapsulation.setField(expectedSymmetricKey, "primaryKey", "1234");
        Deencapsulation.setField(expectedSymmetricKey, "secondaryKey", "5678");
    }

    //Tests_SRS_AUTHENTICATION_34_003: [This constructor shall save the provided symmetricKey to the returned instance.]
    @Test
    public void testSymmetricKeyConstructor()
    {
        Authentication authenticationWithSymmetricKey = new Authentication(expectedSymmetricKey);
        assertEquals(expectedSymmetricKey, authenticationWithSymmetricKey.getSymmetricKey());
    }

    //Tests_SRS_AUTHENTICATION_34_004: [This constructor shall save the provided thumbprint to the returned instance.]
    @Test
    public void testThumbprintConstructor()
    {
        Authentication authenticationSelfSigned = new Authentication(expectedPrimaryThumbprint, expectedSecondaryThumbprint);
        assertEquals(expectedPrimaryThumbprint, authenticationSelfSigned.getPrimaryThumbprint());
        assertEquals(expectedSecondaryThumbprint, authenticationSelfSigned.getSecondaryThumbprint());
    }

    //Tests_SRS_AUTHENTICATION_34_010: [This constructor shall not save a thumbprint or a symmetric key.]
    @Test
    public void testCertificateAuthoritySignedConstructor()
    {
        Authentication authenticationCASigned = new Authentication(AuthenticationType.certificateAuthority);
        assertNull(authenticationCASigned.getSymmetricKey());
        assertNull(authenticationCASigned.getPrimaryThumbprint());
        assertNull(authenticationCASigned.getSecondaryThumbprint());
        assertEquals(AuthenticationType.certificateAuthority, authenticationCASigned.getAuthenticationType());
    }

    //Tests_SRS_AUTHENTICATION_34_005: [This function shall return this object's symmetric key.]
    //Tests_SRS_AUTHENTICATION_34_006: [This function shall return this object's thumbprint.]
    //Tests_SRS_AUTHENTICATION_34_007: [This function shall set this object's symmetric key to the provided value.]
    //Tests_SRS_AUTHENTICATION_34_019: [This function shall set this object's authentication type to sas.]
    //Tests_SRS_AUTHENTICATION_34_015: [This function shall set this object's primary thumbprint to the provided value.]
    //Tests_SRS_AUTHENTICATION_34_017: [This function shall set this object's authentication type to SelfSigned.]
    //Tests_SRS_AUTHENTICATION_34_016: [This function shall set this object's secondary thumbprint to the provided value.]
    //Tests_SRS_AUTHENTICATION_34_018: [This function shall set this object's authentication type to SelfSigned.]
    //Tests_SRS_AUTHENTICATION_34_009: [This function shall return the AuthenticationType of this object.]
    //Tests_SRS_AUTHENTICATION_34_011: [This function shall set this object's authentication type to the provided value.]
    @Test
    public void settersAndGettersWork()
    {
        Authentication actualAuthentication = new Authentication(AuthenticationType.certificateAuthority);

        actualAuthentication.setSymmetricKey(expectedSymmetricKey);
        assertEquals(expectedSymmetricKey, actualAuthentication.getSymmetricKey());
        assertEquals(AuthenticationType.sas, actualAuthentication.getAuthenticationType());

        actualAuthentication.setPrimaryThumbprint(expectedPrimaryThumbprint);
        assertEquals(expectedPrimaryThumbprint, actualAuthentication.getPrimaryThumbprint());
        assertEquals(AuthenticationType.selfSigned, actualAuthentication.getAuthenticationType());

        actualAuthentication.setAuthenticationType(AuthenticationType.certificateAuthority);
        assertEquals(AuthenticationType.certificateAuthority, actualAuthentication.getAuthenticationType());

        actualAuthentication.setSecondaryThumbprint(expectedSecondaryThumbprint);
        assertEquals(expectedSecondaryThumbprint, actualAuthentication.getSecondaryThumbprint());
        assertEquals(AuthenticationType.selfSigned, actualAuthentication.getAuthenticationType());
    }

    //Tests_SRS_AUTHENTICATION_34_009: [This function shall return the AuthenticationType of this object.]
    @Test
    public void testGetAuthenticationType()
    {
        Authentication authenticationWithSymmetricKey = new Authentication(expectedSymmetricKey);
        Authentication authenticationCASigned = new Authentication(AuthenticationType.certificateAuthority);
        Authentication authenticationSelfSigned = new Authentication(expectedPrimaryThumbprint, expectedSecondaryThumbprint);

        assertEquals(AuthenticationType.sas, authenticationWithSymmetricKey.getAuthenticationType());
        assertEquals(AuthenticationType.certificateAuthority, authenticationCASigned.getAuthenticationType());
        assertEquals(AuthenticationType.selfSigned, authenticationSelfSigned.getAuthenticationType());
    }

    //Tests_SRS_AUTHENTICATION_34_012: [This constructor shall throw an IllegalArgumentException if the provided symmetricKey is null.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsIllegalArgumentExceptionForNullSymmetricKey()
    {
        SymmetricKey key = null;
        new Authentication(key);
    }

    //Tests_SRS_AUTHENTICATION_34_013: [If the provided symmetricKey is null, this function shall throw an IllegalArgumentException.]
    @Test (expected = IllegalArgumentException.class)
    public void setSymmetricKeyThrowsForNullSymmetricKey()
    {
        Authentication authentication = new Authentication(expectedSymmetricKey);
        authentication.setSymmetricKey(null);
    }

    //Tests_SRS_AUTHENTICATION_34_014: [If the provided type is null, this function shall throw an IllegalArgumentException.]
    @Test (expected = IllegalArgumentException.class)
    public void setAuthenticationTypeThrowsForNullType()
    {
        Authentication authentication = new Authentication(expectedSymmetricKey);
        authentication.setAuthenticationType(null);
    }

    @Test
    public void constructorWithTypeInitializesThumbprintAndOrSymmetricKeyCorrectly()
    {
        Authentication caAuth = new Authentication(AuthenticationType.certificateAuthority);
        Authentication selfSignedAuth = new Authentication(AuthenticationType.selfSigned);
        Authentication sasAuth = new Authentication(AuthenticationType.sas);

        assertNull(caAuth.getPrimaryThumbprint());
        assertNull(caAuth.getSecondaryThumbprint());
        assertNull(caAuth.getSymmetricKey());

        assertNotNull(selfSignedAuth.getPrimaryThumbprint());
        assertNotNull(selfSignedAuth.getSecondaryThumbprint());
        assertNull(selfSignedAuth.getSymmetricKey());

        assertNotNull(sasAuth.getSymmetricKey());
        assertNull(sasAuth.getPrimaryThumbprint());
        assertNull(sasAuth.getSecondaryThumbprint());
    }

    @Test
    public void equalsWorks()
    {
        Authentication auth1 = new Authentication(AuthenticationType.certificateAuthority);
        Authentication auth2 = new Authentication(AuthenticationType.selfSigned);
        Authentication auth3 = new Authentication(AuthenticationType.sas);
        Authentication auth4 = new Authentication("0000000000000000000000000000000000000000", "0000000000000000000000000000000000000000");
        Authentication auth5 = new Authentication(new SymmetricKey());
        Authentication auth6 = new Authentication(new SymmetricKey());
        Authentication auth7 = new Authentication(AuthenticationType.certificateAuthority);

        assertNotEquals(auth1, auth2);
        assertNotEquals(auth1, auth3);
        assertNotEquals(auth1, auth4);
        assertNotEquals(auth1, auth5);
        assertNotEquals(auth1, auth6);
        assertEquals(auth1, auth7);

        assertNotEquals(auth2, auth3);
        assertNotEquals(auth2, auth4);
        assertNotEquals(auth2, auth5);
        assertNotEquals(auth2, auth6);

        assertNotEquals(auth3, auth4);
        assertNotEquals(auth3, auth5);
        assertNotEquals(auth3, auth6);

        assertNotEquals(auth4, auth5);
        assertNotEquals(auth4, auth6);

        assertNotEquals(auth5, auth6);

        assertNotEquals(auth1, "not an authentication object");
    }
}