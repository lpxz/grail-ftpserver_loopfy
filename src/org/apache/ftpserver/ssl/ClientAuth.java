package org.apache.ftpserver.ssl;

/**
 * Enumeration of possible levels of client authentication during an SSL
 * session.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public enum ClientAuth {

    /**
     * Client authentication is required
     */
    NEED, /**
     * Client authentication is requested but not required
     */
    WANT, /**
     * Client authentication is not performed
     */
    NONE
}
