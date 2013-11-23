package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.ftplet.AuthorizationRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Class representing a write request
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class WriteRequest implements AuthorizationRequest {

    private String file;

    /**
     * Request write access to the user home directory (/)
     * 
     */
    public WriteRequest() {
        this("/");
    }

    /**
     * Request write access to a file or directory relative to the user home
     * directory
     * 
     * @param file
     */
    public WriteRequest(final String file) {
        this.file = file;
    }

    /**
     * Get the file or directory to which write access is requested
     * 
     * @return the file The file or directory
     */
    public String getFile() {
        return file;
    }
}
