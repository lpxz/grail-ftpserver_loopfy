package org.apache.ftpserver.command.impl.listing;

import org.apache.ftpserver.ftplet.FtpFile;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Interface for formating output based on a {@link FtpFile}
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface FileFormater {

    /**
     * Format the file
     * 
     * @param file
     *            The {@link FtpFile}
     * @return The formated string based on the {@link FtpFile}
     */
    String format(FtpFile file);
}
