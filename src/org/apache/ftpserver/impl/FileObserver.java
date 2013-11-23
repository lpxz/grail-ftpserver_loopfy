package org.apache.ftpserver.impl;

import org.apache.ftpserver.ftplet.FtpFile;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * This is the file related activity observer.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface FileObserver {

    /**
     * User file upload notification.
     */
    void notifyUpload(FtpIoSession session, FtpFile file, long size);

    /**
     * User file download notification.
     */
    void notifyDownload(FtpIoSession session, FtpFile file, long size);

    /**
     * User file delete notification.
     */
    void notifyDelete(FtpIoSession session, FtpFile file);

    /**
     * User make directory notification.
     */
    void notifyMkdir(FtpIoSession session, FtpFile file);

    /**
     * User remove directory notification.
     */
    void notifyRmdir(FtpIoSession session, FtpFile file);
}
