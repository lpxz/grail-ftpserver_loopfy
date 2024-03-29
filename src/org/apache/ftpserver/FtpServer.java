package org.apache.ftpserver;

import org.apache.ftpserver.ftplet.FtpException;

/**
 * This is the starting point of all the servers. It invokes a new listener
 * thread. <code>Server</code> implementation is used to create the server
 * socket and handle client connection.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public interface FtpServer {

    /**
     * Start the server. Open a new listener thread.
     * @throws FtpException 
     */
    void start() throws FtpException;

    /**
     * Stop the server. Stop the listener thread.
     */
    void stop();

    /**
     * Get the server status.
     * @return true if the server is stopped
     */
    boolean isStopped();

    /**
     * Suspend further requests
     */
    void suspend();

    /**
     * Resume the server handler
     */
    void resume();

    /**
     * Is the server suspended
     * @return true if the server is suspended
     */
    boolean isSuspended();
}
