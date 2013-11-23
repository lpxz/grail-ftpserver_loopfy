package org.apache.ftpserver.impl;

import java.net.InetAddress;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * FTP statistics observer interface.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface StatisticsObserver {

    /**
     * User file upload notification.
     */
    void notifyUpload();

    /**
     * User file download notification.
     */
    void notifyDownload();

    /**
     * User file delete notification.
     */
    void notifyDelete();

    /**
     * User make directory notification.
     */
    void notifyMkdir();

    /**
     * User remove directory notification.
     */
    void notifyRmdir();

    /**
     * New user login notification.
     */
    void notifyLogin(boolean anonymous);

    /**
     * Failed user login notification.
     * 
     * @param address
     *            Remote address that the failure came from
     */
    void notifyLoginFail(InetAddress address);

    /**
     * User logout notification.
     */
    void notifyLogout(boolean anonymous);

    /**
     * Connection open notification
     */
    void notifyOpenConnection();

    /**
     * Connection close notification
     */
    void notifyCloseConnection();
}
