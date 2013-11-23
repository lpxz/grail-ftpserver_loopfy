package org.apache.ftpserver.command;

import java.io.IOException;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;

/**
 * This interface encapsulates all the FTP commands.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public interface Command {

    /**
     * Execute command.
     * 
     * @param session
     *            The current {@link FtpIoSession}
     * @param context
     *            The current {@link FtpServerContext}
     * @param request The current {@link FtpRequest}
     * @throws IOException 
     * @throws FtpException 
     */
    void execute(FtpIoSession session, FtpServerContext context, FtpRequest request) throws IOException, FtpException;
}
