package org.apache.ftpserver.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.apache.ftpserver.DataConnectionException;
import org.apache.ftpserver.ftplet.DataConnectionFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> *
 */
public interface ServerDataConnectionFactory extends DataConnectionFactory {

    /**
     * Port command.
     */
    void initActiveDataConnection(InetSocketAddress address);

    /**
     * Initiate the passive data connection.
     * 
     * @return The {@link InetSocketAddress} on which the data connection if
     *         bound.
     */
    InetSocketAddress initPassiveDataConnection() throws DataConnectionException;

    /**
     * Set the security protocol.
     */
    void setSecure(boolean secure);

    /**
     * Sets the server's control address.
     */
    void setServerControlAddress(InetAddress serverControlAddress);

    void setZipMode(boolean zip);

    /**
     * Check the data connection idle status.
     */
    boolean isTimeout(long currTime);

    /**
     * Dispose data connection - close all the sockets.
     */
    void dispose();

    /**
     * Is secure?
     */
    boolean isSecure();

    /**
     * Is zip mode?
     */
    boolean isZipMode();

    /**
     * Get client address.
     */
    InetAddress getInetAddress();

    /**
     * Get port number.
     */
    int getPort();
}
