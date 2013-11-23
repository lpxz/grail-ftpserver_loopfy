package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.AuthorizationRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * The max upload rate permission
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ConcurrentLoginPermission implements Authority {

    private int maxConcurrentLogins;

    private int maxConcurrentLoginsPerIP;

    public ConcurrentLoginPermission(int maxConcurrentLogins, int maxConcurrentLoginsPerIP) {
        this.maxConcurrentLogins = maxConcurrentLogins;
        this.maxConcurrentLoginsPerIP = maxConcurrentLoginsPerIP;
    }

    /**
     * @see Authority#authorize(AuthorizationRequest)
     */
    public AuthorizationRequest authorize(AuthorizationRequest request) {
        if (request instanceof ConcurrentLoginRequest) {
            ConcurrentLoginRequest concurrentLoginRequest = (ConcurrentLoginRequest) request;
            if (maxConcurrentLogins != 0 && maxConcurrentLogins < concurrentLoginRequest.getConcurrentLogins()) {
                return null;
            } else if (maxConcurrentLoginsPerIP != 0 && maxConcurrentLoginsPerIP < concurrentLoginRequest.getConcurrentLoginsFromThisIP()) {
                return null;
            } else {
                concurrentLoginRequest.setMaxConcurrentLogins(maxConcurrentLogins);
                concurrentLoginRequest.setMaxConcurrentLoginsPerIP(maxConcurrentLoginsPerIP);
                return concurrentLoginRequest;
            }
        } else {
            return null;
        }
    }

    /**
     * @see Authority#canAuthorize(AuthorizationRequest)
     */
    public boolean canAuthorize(AuthorizationRequest request) {
        return request instanceof ConcurrentLoginRequest;
    }
}
