/*
 */
package folder.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageHash {

    static String DEFAULT_ALGORITHM = "SHA-1";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String input = "sequlator@gmail.com", input2 = "sequlatop@gmail.com";

        System.out.println(digest(input));
        System.out.println(digest(input2));
        System.out.println("==========================================================");
        String[] ALGORITHMS = {"MD2", "MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512"};
        for (String a : ALGORITHMS) {
            System.out.println("========================================================== " + a);
            System.out.println(digest(input, a));
            System.out.println(digest(input2, a));
        }
    }

    static String digest(String input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        return HexConverter.bytesToHex(md.digest(input.getBytes()));
    }

    public static String digest(String input) {
        try {
            return digest(input, DEFAULT_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("This is impossible");
        }
    }

    //27d339daee82263b40a2e397ccf223c2ac19c01a
}
