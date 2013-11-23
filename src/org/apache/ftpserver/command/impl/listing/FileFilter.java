package org.apache.ftpserver.command.impl.listing;

import org.apache.ftpserver.ftplet.FtpFile;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Interface for selecting files based on some critera.
 * 
 * @see java.io.FileFilter
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface FileFilter {

    /**
     * Decide if the {@link FtpFile} should be selected
     * 
     * @param file
     *            The {@link FtpFile}
     * @return true if the {@link FtpFile} was selected
     */
    boolean accept(FtpFile file);
}
