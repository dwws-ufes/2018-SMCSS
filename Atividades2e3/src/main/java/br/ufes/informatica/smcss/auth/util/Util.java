package br.ufes.informatica.smcss.auth.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Copiado de org.jboss.security.Util */
public class Util {

    private final static Logger log = Logger.getLogger(Util.class.getName());

    public static final String BASE64_ENCODING = "BASE64";
    public static final String BASE16_ENCODING = "HEX";

    /**
     * Calculate a password hash using a MessageDigest.
     *
     * @param hashAlgorithm - the MessageDigest algorithm name
     * @param hashEncoding  - either base64 or hex to specify the type of encoding
     *                      the MessageDigest as a string.
     * @param hashCharset   - the charset used to create the byte[] passed to the
     *                      MessageDigestfrom the password String. If null the
     *                      platform default is used.
     * @param username      - ignored in default version
     * @param password      - the password string to be hashed
     * @return the hashed string if successful, null if there is a digest exception
     */
    public static String createPasswordHash(String hashAlgorithm, String hashEncoding, String hashCharset,
            String... strList) {
        String passwordHash = null;
        boolean warned = false;
        // calculate the hash and apply the encoding.
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
            for (String str : strList) {
                byte[] passBytes;
                // convert to byte data
                try {
                    passBytes = (hashCharset == null) ? str.getBytes() : str.getBytes(hashCharset);
                } catch (UnsupportedEncodingException uee) {
                    if (!warned) {
                        log.warning("charset " + hashCharset + " not found. Using platform default.");
                        warned = true;
                    }
                    passBytes = str.getBytes();
                }
                md.update(passBytes);
            }
            byte[] hash = md.digest();
            if (hashEncoding.equalsIgnoreCase(BASE64_ENCODING)) {
                passwordHash = Util.encodeBase64(hash);
            } else if (hashEncoding.equalsIgnoreCase(BASE16_ENCODING)) {
                passwordHash = Util.encodeBase16(hash);
            } else {
                log.warning("Unsupported hash encoding format " + hashEncoding);
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "Password hash calculation failed ", e);
        }
        return passwordHash;
    }

    /**
     * Hex encoding of hashes, as used by Catalina. Each byte is converted to the
     * corresponding two hex characters.
     */
    public static String encodeBase16(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            // top 4 bits
            char c = (char) ((b >> 4) & 0xf);
            if (c > 9)
                c = (char) ((c - 10) + 'a');
            else
                c = (char) (c + '0');
            sb.append(c);
            // bottom 4 bits
            c = (char) (b & 0xf);
            if (c > 9)
                c = (char) ((c - 10) + 'a');
            else
                c = (char) (c + '0');
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * BASE64 encoder implementation. Provides encoding methods, using the BASE64
     * encoding rules, as defined in the MIME specification,
     * <a href="http://ietf.org/rfc/rfc1521.txt">rfc1521</a>.
     */
    public static String encodeBase64(byte[] bytes) {
        byte[] base64 = null;
        try {
            base64 = Base64.getEncoder().encode(bytes);
        } catch (Exception e) {
        }
        return new String(base64);
    }
}
