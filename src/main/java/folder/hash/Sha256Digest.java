/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folder.hash;

import static folder.Constants.APP_SECRET;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author mcaikovs
 */
public class Sha256Digest {

    Mac mac;
    private static Sha256Digest i;

    Sha256Digest() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        this(APP_SECRET);
    }

    Sha256Digest(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec sk = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8.toString()), "HmacSHA256");
        mac = Mac.getInstance("HmacSHA256");
        mac.init(sk);
    }

    String hash(String msg) throws UnsupportedEncodingException {
        return HexConverter.bytesToHex(mac.doFinal(msg.getBytes(StandardCharsets.UTF_8.toString())));
    }

    public static String hashToken(String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        if (i == null) {
            i = new Sha256Digest();
        }
        return i.hash(msg);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new Sha256Digest().hash("Test"));
    }
}
