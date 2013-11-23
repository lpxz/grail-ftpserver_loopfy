package org.apache.ftpserver.command.impl.listing;

import org.apache.ftpserver.ftplet.FtpFile;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Formats files according to the NLST specification
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class NLSTFileFormater implements FileFormater {

    private static final char[] NEWLINE = { '\r', '\n' };

    /**
     * @see FileFormater#format(FtpFile)
     */
    public String format(FtpFile file) {
        StringBuilder sb = new StringBuilder();
        sb.append(file.getName());
        sb.append(NEWLINE);
        return sb.toString();
    }
}
