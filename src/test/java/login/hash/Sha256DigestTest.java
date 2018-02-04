/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.hash;

import login.hash.Sha256Digest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcaikovs
 */
public class Sha256DigestTest {

    public Sha256DigestTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHash() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String key = "key";
        String msg = "The quick brown fox jumps over the lazy dog";
        Sha256Digest i = new Sha256Digest(key);
        String s = i.hash(msg);
        System.out.println(s);
        assertEquals(s, "f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8");
        s = i.hash(msg);
        s = i.hash(msg);
        s = i.hash(msg);
        assertEquals(s, "f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8");
    }

}
