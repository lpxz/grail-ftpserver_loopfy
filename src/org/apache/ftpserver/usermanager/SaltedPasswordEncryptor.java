package org.apache.ftpserver.usermanager;

import java.security.SecureRandom;
import org.apache.ftpserver.util.EncryptUtils;

/**
 * Password encryptor that hashes a salt together with the password using MD5. 
 * Using a salt protects against birthday attacks. 
 * The hashing is also made in iterations, making lookup attacks much harder.
 *
 * The algorithm is based on the principles described in
 * http://www.jasypt.org/howtoencryptuserpasswords.html
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class SaltedPasswordEncryptor implements PasswordEncryptor {

    private SecureRandom rnd = new SecureRandom();

    private static final int MAX_SEED = 99999999;

    private static final int HASH_ITERATIONS = 1000;

    private String encrypt(String password, String salt) {
        String hash = salt + password;
        edu.hkust.clap.monitor.Monitor.loopBegin(50);
for (int i = 0; i < HASH_ITERATIONS; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(50);
{
            hash = EncryptUtils.encryptMD5(hash);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(50);

        return salt + ":" + hash;
    }

    /**
     * Encrypts the password using a salt concatenated with the password 
     * and a series of MD5 steps.
     */
    public String encrypt(String password) {
        String seed = Integer.toString(rnd.nextInt(MAX_SEED));
        return encrypt(password, seed);
    }

    /**
     * {@inheritDoc}
     */
    public boolean matches(String passwordToCheck, String storedPassword) {
        if (storedPassword == null) {
            throw new NullPointerException("storedPassword can not be null");
        }
        if (passwordToCheck == null) {
            throw new NullPointerException("passwordToCheck can not be null");
        }
        int divider = storedPassword.indexOf(':');
        if (divider < 1) {
            throw new IllegalArgumentException("stored password does not contain salt");
        }
        String storedSalt = storedPassword.substring(0, divider);
        return encrypt(passwordToCheck, storedSalt).equalsIgnoreCase(storedPassword);
    }
}
