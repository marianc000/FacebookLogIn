/*
 */
package login;

import static login.OpenId.SEPARATOR;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author caikovsk
 */
public class OpenIdTest {

    public OpenIdTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetState() {
        String url = "http://localhost:8080/test/", nonce = "23456789",state=url+SEPARATOR+nonce;
        OpenId i = new OpenId() {
            @Override
            String getStateNonce() {
                return nonce; //To change body of generated methods, choose Tools | Templates.
            }
        };
        assertEquals(state,i.getState(url));
        assertEquals(url,i.extractUrlFromState(state));
 
    }

}
