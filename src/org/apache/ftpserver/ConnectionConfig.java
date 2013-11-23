package org.apache.ftpserver;

/**
 * Interface for providing the configuration for the control socket connections.
 * 
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface ConnectionConfig {

    /**
     * The maximum number of time an user can fail to login before getting disconnected
     * @return The maximum number of failure login attempts
     */
    int getMaxLoginFailures();

    /**
     * The delay in number of milliseconds between login failures. Important to 
     * make brute force attacks harder.
     * 
     * @return The delay time in milliseconds
     */
    int getLoginFailureDelay();

    /**
     * The maximum number of time an anonymous user can fail to login before getting disconnected
     * @return The maximum number of failer login attempts
     */
    int getMaxAnonymousLogins();

    /**
     * The maximum number of concurrently logged in users
     * @return The maximum number of users
     */
    int getMaxLogins();

    /**
     * Is anonymous logins allowed at the server?
     * @return true if anonymous logins are enabled
     */
    boolean isAnonymousLoginEnabled();

    /**
     * Returns the maximum number of threads the server is allowed to create for
     * processing client requests.
     * 
     * @return the maximum number of threads the server is allowed to create for
     *         processing client requests.
     */
    int getMaxThreads();
}
