/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.service.Authentication;
import com.microsoft.azure.sdk.iot.service.AuthenticationType;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;
import com.microsoft.azure.sdk.iot.service.auth.X509Thumbprint;
import mockit.Deencapsulation;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Code coverage:
 * 100% Methods, 87% lines
 */
@RunWith(JMockit.class)
public class AuthenticationTest
{
    private X509Thumbprint expectedThumbprint;
    private SymmetricKey expectedSymmetricKey;

    @Before
    public void setUp()
    {
        String expectedPrimaryThumbprint = "0000000000000000000000000000000000000000";
        String expectedSecondaryThumbprint = "1111111111111111111111111111111111111111";
        expectedThumbprint = Deencapsulation.newInstance(X509Thumbprint.class, new Class[]{String.class, String.class}, expectedPrimaryThumbprint, expectedSecondaryThumbprint);
        expectedSymmetricKey = Deencapsulation.newInstance(SymmetricKey.class, new Class[]{});
        Deencapsulation.setField(expectedSymmetricKey, "primaryKey", "1234");
        Deencapsulation.setField(expectedSymmetricKey, "secondaryKey", "5678");
    }

    //Tests_SRS_AUTHENTICATION_34_003: [This constructor will save the provided symmetricKey to the returned instance.]
    @Test
    public void testSymmetricKeyConstructor()
    {
        Authentication authenticationWithSymmetricKey = new Authentication(expectedSymmetricKey);
        assertEquals(expectedSymmetricKey, authenticationWithSymmetricKey.getSymmetricKey());
    }

    //Tests_SRS_AUTHENTICATION_34_004: [This constructor will save the provided thumbprint to the returned instance.]
    @Test
    public void testThumbprintConstructor()
    {
        Authentication authenticationSelfSigned = new Authentication(expectedThumbprint);
        assertEquals(expectedThumbprint, authenticationSelfSigned.getThumbprint());
    }

    //Tests_SRS_AUTHENTICATION_34_010: [This constructor will not save a thumbprint or a symmetric key.]
    @Test
    public void testCertificateAuthoritySignedConstructor()
    {
        Authentication authenticationCASigned = new Authentication(AuthenticationType.certificateAuthority);
        assertNull(authenticationCASigned.getSymmetricKey());
        assertNull(authenticationCASigned.getThumbprint());
        assertEquals(AuthenticationType.certificateAuthority, authenticationCASigned.getAuthenticationType());
    }

    //Tests_SRS_AUTHENTICATION_34_005: [This function shall return this object's symmetric key.]
    //Tests_SRS_AUTHENTICATION_34_006: [This function shall return this object's thumbprint.]
    //Tests_SRS_AUTHENTICATION_34_007: [This function shall set this object's symmetric key to the provided value.]
    //Tests_SRS_AUTHENTICATION_34_008: [This function shall set this object's thumbprint to the provided value.]
    //Tests_SRS_AUTHENTICATION_34_009: [This function shall return the AuthenticationType of this object.]
    //Tests_SRS_AUTHENTICATION_34_011: [This function shall set this object's authentication type to the provided value.]
    @Test
    public void settersAndGettersWork()
    {
        Authentication actualAuthentication = Deencapsulation.newInstance(Authentication.class, new Class[]{X509Thumbprint.class}, expectedThumbprint);

        actualAuthentication.setSymmetricKey(expectedSymmetricKey);
        assertEquals(expectedSymmetricKey, actualAuthentication.getSymmetricKey());

        actualAuthentication.setThumbprint(expectedThumbprint);
        assertEquals(expectedThumbprint, actualAuthentication.getThumbprint());

        actualAuthentication.setAuthenticationType(AuthenticationType.certificateAuthority);
        assertEquals(AuthenticationType.certificateAuthority, actualAuthentication.getAuthenticationType());
    }

    //Tests_SRS_AUTHENTICATION_34_009: [This function shall return the AuthenticationType of this object.]
    @Test
    public void testGetAuthenticationType()
    {
        Authentication authenticationWithSymmetricKey = new Authentication(expectedSymmetricKey);
        Authentication authenticationCASigned = new Authentication(AuthenticationType.certificateAuthority);
        Authentication authenticationSelfSigned = new Authentication(expectedThumbprint);

        assertEquals(AuthenticationType.sas, authenticationWithSymmetricKey.getAuthenticationType());
        assertEquals(AuthenticationType.certificateAuthority, authenticationCASigned.getAuthenticationType());
        assertEquals(AuthenticationType.selfSigned, authenticationSelfSigned.getAuthenticationType());
    }
}
