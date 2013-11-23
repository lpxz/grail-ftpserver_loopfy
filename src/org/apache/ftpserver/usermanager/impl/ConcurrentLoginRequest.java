package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.ftplet.AuthorizationRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Class representing a request to log in a number of concurrent times
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ConcurrentLoginRequest implements AuthorizationRequest {

    private int concurrentLogins;

    private int concurrentLoginsFromThisIP;

    private int maxConcurrentLogins = 0;

    private int maxConcurrentLoginsPerIP = 0;

    /**
     * @param concurrentLogins
     * @param concurrentLoginsFromThisIP
     */
    public ConcurrentLoginRequest(int concurrentLogins, int concurrentLoginsFromThisIP) {
        super();
        this.concurrentLogins = concurrentLogins;
        this.concurrentLoginsFromThisIP = concurrentLoginsFromThisIP;
    }

    /**
     * The number of concurrent logins requested
     * 
     * @return the concurrentLogins The number of current concurrent logins
     */
    public int getConcurrentLogins() {
        return concurrentLogins;
    }

    /**
     * The number of concurrent logins from this IP requested
     * 
     * @return the concurrentLoginsFromThisIP The number of current concurrent
     *         logins from this IP
     */
    public int getConcurrentLoginsFromThisIP() {
        return concurrentLoginsFromThisIP;
    }

    /**
     * The maximum allowed concurrent logins for this user, or 0 if no limit is
     * set. This is normally populated by {@link ConcurrentLoginPermission}
     * 
     * @return The maximum allowed concurrent logins
     */
    public int getMaxConcurrentLogins() {
        return maxConcurrentLogins;
    }

    /**
     * Set the maximum allowed concurrent logins for this user
     * 
     * @param maxConcurrentLogins
     *            Set max allowed concurrent connections
     */
    void setMaxConcurrentLogins(int maxConcurrentLogins) {
        this.maxConcurrentLogins = maxConcurrentLogins;
    }

    /**
     * The maximum allowed concurrent logins per IP for this user, or 0 if no
     * limit is set. This is normally populated by
     * {@link ConcurrentLoginPermission}
     * 
     * @return The maximum allowed concurrent logins per IP
     */
    public int getMaxConcurrentLoginsPerIP() {
        return maxConcurrentLoginsPerIP;
    }

    /**
     * Set the maximum allowed concurrent logins per IP for this user
     * 
     * @param maxConcurrentLoginsPerIP
     *            Set max allowed concurrent connections per IP
     */
    void setMaxConcurrentLoginsPerIP(int maxConcurrentLoginsPerIP) {
        this.maxConcurrentLoginsPerIP = maxConcurrentLoginsPerIP;
    }
}
