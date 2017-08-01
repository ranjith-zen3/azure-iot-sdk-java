/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.service.auth;

import com.microsoft.azure.sdk.iot.service.auth.X509Thumbprint;
import mockit.Deencapsulation;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotEquals;

/**
 * Code coverage:
 * 100% Methods, 96% lines
 */
@RunWith(JMockit.class)
public class X509ThumbprintTest
{
    String expectedPrimaryThumbprint;
    String expectedSecondaryThumbprint;

    @Before
    public void setUp()
    {
        //These thumbprints need to be 40 characters long. Can be a mix of upper case letters and numbers
        expectedPrimaryThumbprint = "0000000000000000000000000000000000000000";
        expectedSecondaryThumbprint = "1111111111111111111111111111111111111111";
    }

    //Tests_SRS_X509THUMBPRINT_34_001: [The function shall return the primary thumbprint value of this.]
    //Tests_SRS_X509THUMBPRINT_34_002: [The function shall return the secondary thumbprint value of this.]
    //Tests_SRS_X509THUMBPRINT_34_003: [The function shall set the primary thumbprint to the given value.]
    //Tests_SRS_X509THUMBPRINT_34_004: [The function shall set the secondary thumbprint to the given value.]
    @Test
    public void gettersAndSettersWork()
    {
        X509Thumbprint thumbprint = Deencapsulation.newInstance(X509Thumbprint.class, new Class[]{String.class, String.class}, null, null);

        assertNull(thumbprint.getPrimaryThumbprint());
        assertNull(thumbprint.getSecondaryThumbprint());

        //act
        thumbprint.setPrimaryThumbprint(expectedPrimaryThumbprint);
        thumbprint.setSecondaryThumbprint(expectedSecondaryThumbprint);

        //assert
        assertEquals(expectedPrimaryThumbprint, thumbprint.getPrimaryThumbprint());
        assertEquals(expectedSecondaryThumbprint, thumbprint.getSecondaryThumbprint());
    }

    //Tests_SRS_X509THUMBPRINT_34_006: [This constructor will create an X509Thumbprint with the provided primary thumbprint and the provided secondary thumbprint.]
    @Test
    public void constructorSetsPrimaryAndSecondaryThumbmprints()
    {
        //act
        X509Thumbprint thumbprint = new X509Thumbprint(expectedPrimaryThumbprint, expectedSecondaryThumbprint);

        //assert
        assertEquals(expectedPrimaryThumbprint, thumbprint.getPrimaryThumbprint());
        assertEquals(expectedSecondaryThumbprint, thumbprint.getSecondaryThumbprint());
    }

    //Tests_SRS_X509THUMBPRINT_34_010: [This constructor will throw an IllegalArgumentException if the provided thumbprints are not a valid format.]
    @Test (expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionThrownByConstructorForInvalidPrimaryThumbprint()
    {
        new X509Thumbprint("",expectedSecondaryThumbprint);
    }

    //Tests_SRS_X509THUMBPRINT_34_010: [This constructor will throw an IllegalArgumentException if the provided thumbprints are not a valid format.]
    @Test (expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionThrownByConstructorForInvalidSecondaryThumbprint()
    {
        new X509Thumbprint(expectedPrimaryThumbprint,"");
    }

    //Codes_SRS_X509THUMBPRINT_34_007: [If the provided thumbprint string is not the proper format, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionThrownBySetPrimaryThumbprintForIllegalThumbprint()
    {
        X509Thumbprint thumbprint = Deencapsulation.newInstance(X509Thumbprint.class, new Class[]{String.class, String.class}, null, null);
        thumbprint.setPrimaryThumbprint("");
    }

    //Codes_SRS_X509THUMBPRINT_34_008: [If the provided thumbprint string is not the proper format, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionThrownBySetSecondaryThumbprintForIllegalThumbprint()
    {
        X509Thumbprint thumbprint = Deencapsulation.newInstance(X509Thumbprint.class, new Class[]{String.class, String.class}, null, null);
        thumbprint.setSecondaryThumbprint("");
    }

    @Test
    public void equalsWorks()
    {
        // Arrange
        X509Thumbprint s1 = createTestThumbprint(null, null);
        X509Thumbprint s2 = createTestThumbprint("", null);
        X509Thumbprint s3 = createTestThumbprint(null, "");
        X509Thumbprint s4 = createTestThumbprint("", "");
        X509Thumbprint s5 = createTestThumbprint("", "secondaryThumbprint");
        X509Thumbprint s6 = createTestThumbprint("primaryThumbprint", "secondaryThumbprint2");
        X509Thumbprint s7 = createTestThumbprint("primaryThumbprint2", "secondaryThumbprint");
        X509Thumbprint s8 = createTestThumbprint("primaryThumbprint", "secondaryThumbprint");
        X509Thumbprint s9 = createTestThumbprint("primaryThumbprint", "secondaryThumbprint");

        // Assert
        assertNotEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);
        assertNotEquals(s1, s5);
        assertNotEquals(s1, s6);
        assertNotEquals(s1, s7);
        assertNotEquals(s1, s8);

        assertNotEquals(s2, s3);
        assertNotEquals(s2, s4);
        assertNotEquals(s2, s5);
        assertNotEquals(s2, s6);
        assertNotEquals(s2, s7);
        assertNotEquals(s2, s8);

        assertNotEquals(s3, s4);
        assertNotEquals(s3, s5);
        assertNotEquals(s3, s6);
        assertNotEquals(s3, s7);
        assertNotEquals(s3, s8);

        assertNotEquals(s4, s5);
        assertNotEquals(s4, s6);
        assertNotEquals(s4, s7);
        assertNotEquals(s4, s8);

        assertNotEquals(s5, s6);
        assertNotEquals(s5, s7);
        assertNotEquals(s5, s8);

        assertNotEquals(s6, s7);
        assertNotEquals(s6, s8);

        assertNotEquals(s7, s8);

        assertEquals(s8, s9);
    }

    private X509Thumbprint createTestThumbprint(String primaryThumbprint, String secondaryThumbprint)
    {
        X509Thumbprint thumbprint = new X509Thumbprint(expectedPrimaryThumbprint, expectedSecondaryThumbprint);
        Deencapsulation.setField(thumbprint, "primaryThumbprint", primaryThumbprint);
        Deencapsulation.setField(thumbprint, "secondaryThumbprint", secondaryThumbprint);
        return thumbprint;
    }
}
