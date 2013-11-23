package org.apache.ftpserver.usermanager;

/**
 * Password encryptor that does no encryption, that is, keps the
 * password in clear text
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ClearTextPasswordEncryptor implements PasswordEncryptor {

    /**
     * Returns the clear text password
     */
    public String encrypt(String password) {
        return password;
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
        return passwordToCheck.equals(storedPassword);
    }
}
