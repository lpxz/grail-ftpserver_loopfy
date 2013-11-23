package org.apache.ftpserver.usermanager;

import org.apache.ftpserver.ftplet.UserManager;

/**
 * Interface for user manager factories
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface UserManagerFactory {

    /**
     * Create an {@link UserManager} instance based on the configuration on the factory
     * @return The {@link UserManager}
     */
    UserManager createUserManager();
}
