package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.AuthorizationRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Class representing a write permission
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class WritePermission implements Authority {

    private String permissionRoot;

    /**
     * Construct a write permission for the user home directory (/)
     */
    public WritePermission() {
        this.permissionRoot = "/";
    }

    /**
     * Construct a write permission for a file or directory relative to the user
     * home directory
     * 
     * @param permissionRoot
     *            The file or directory
     */
    public WritePermission(final String permissionRoot) {
        this.permissionRoot = permissionRoot;
    }

    /**
     * @see Authority#authorize(AuthorizationRequest)
     */
    public AuthorizationRequest authorize(final AuthorizationRequest request) {
        if (request instanceof WriteRequest) {
            WriteRequest writeRequest = (WriteRequest) request;
            String requestFile = writeRequest.getFile();
            if (requestFile.startsWith(permissionRoot)) {
                return writeRequest;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @see Authority#canAuthorize(AuthorizationRequest)
     */
    public boolean canAuthorize(final AuthorizationRequest request) {
        return request instanceof WriteRequest;
    }
}
