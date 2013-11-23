package org.apache.ftpserver.usermanager;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.usermanager.impl.UserMetadata;

/**
 * Class representing a normal authentication attempt using username and
 * password
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class UsernamePasswordAuthentication implements Authentication {

    private String username;

    private String password;

    private UserMetadata userMetadata;

    /**
     * Constructor with the minimal data for an authentication
     * 
     * @param username
     *            The user name
     * @param password
     *            The password, can be null
     */
    public UsernamePasswordAuthentication(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor with an additonal parameter for user metadata
     * 
     * @param username
     *            The user name
     * @param password
     *            The password, can be null
     * @param userMetadata
     *            The user metadata
     */
    public UsernamePasswordAuthentication(final String username, final String password, final UserMetadata userMetadata) {
        this(username, password);
        this.userMetadata = userMetadata;
    }

    /**
     * Retrive the password
     * 
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrive the user name
     * 
     * @return The user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrive the user metadata
     * 
     * @return The user metadata
     */
    public UserMetadata getUserMetadata() {
        return userMetadata;
    }
}
