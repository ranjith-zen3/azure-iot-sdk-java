// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.AuthenticationParser;
import com.microsoft.azure.sdk.iot.deps.serializer.AuthenticationTypeParser;
import com.microsoft.azure.sdk.iot.deps.serializer.SymmetricKeyParser;
import com.microsoft.azure.sdk.iot.deps.serializer.X509ThumbprintParser;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Code Coverage:
 * Methods: 100%
 * Lines: 100%
 */
public class AuthenticationParserTest
{
    //Tests_SRS_AUTHENTICATION_PARSER_34_001: [This constructor creates a new AuthenticationParser object with an authentication type of certificate authority.]
    //Tests_SRS_AUTHENTICATION_PARSER_34_002: [This class has properties for its SymmetricKeyParser, X509ThumbprintParser, and AuthenticationTypeParser.]
    @Test
    public void gettersAndSettersWork()
    {
        AuthenticationParser parser = new AuthenticationParser();

        assertNotNull(parser.getType());
        assertNull(parser.getThumbprint());
        assertNull(parser.getSymmetricKey());

        AuthenticationTypeParser expectedType = AuthenticationTypeParser.selfSigned;
        SymmetricKeyParser expectedSymmetricKey = new SymmetricKeyParser("1", "2");
        X509ThumbprintParser expectedThumbprint = new X509ThumbprintParser("2","3");

        parser.setType(expectedType);
        parser.setSymmetricKey(expectedSymmetricKey);
        parser.setThumbprint(expectedThumbprint);

        assertEquals(expectedType, parser.getType());
        assertEquals(expectedSymmetricKey, parser.getSymmetricKey());
        assertEquals(expectedThumbprint, parser.getThumbprint());
    }

    //Tests_SRS_AUTHENTICATION_PARSER_34_003: [If the provided type is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void cannotSetTypeToNull()
    {
        new AuthenticationParser().setType(null);
    }
}
