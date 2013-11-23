package org.apache.ftpserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * String encryption utility methods.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class EncryptUtils {

    /**
     * Encrypt byte array.
     */
    public static final byte[] encrypt(byte[] source, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.reset();
        md.update(source);
        return md.digest();
    }

    /**
     * Encrypt string
     */
    public static final String encrypt(String source, String algorithm) throws NoSuchAlgorithmException {
        byte[] resByteArray = encrypt(source.getBytes(), algorithm);
        return StringUtils.toHexString(resByteArray);
    }

    /**
     * Encrypt string using MD5 algorithm
     */
    public static final String encryptMD5(String source) {
        if (source == null) {
            source = "";
        }
        String result = "";
        try {
            result = encrypt(source, "MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    /**
     * Encrypt string using SHA algorithm
     */
    public static final String encryptSHA(String source) {
        if (source == null) {
            source = "";
        }
        String result = "";
        try {
            result = encrypt(source, "SHA");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}
