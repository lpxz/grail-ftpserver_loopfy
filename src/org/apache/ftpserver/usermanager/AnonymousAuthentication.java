package org.apache.ftpserver.usermanager;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.usermanager.impl.UserMetadata;

/**
 * Class representing an anonymous authentication attempt
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class AnonymousAuthentication implements Authentication {

    private UserMetadata userMetadata;

    /**
     * Default constructor
     */
    public AnonymousAuthentication() {
    }

    /**
     * Constructor with an additional user metadata parameter
     * 
     * @param userMetadata
     *            User metadata
     */
    public AnonymousAuthentication(UserMetadata userMetadata) {
        this.userMetadata = userMetadata;
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
