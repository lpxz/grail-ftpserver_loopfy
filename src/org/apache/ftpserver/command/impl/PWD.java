package org.apache.ftpserver.command.impl;

import java.io.IOException;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>PWD  &lt;CRLF&gt;</code><br>
 * 
 * This command causes the name of the current working directory to be returned
 * in the reply.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class PWD extends AbstractCommand {

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        FileSystemView fsview = session.getFileSystemView();
        String currDir = fsview.getWorkingDirectory().getAbsolutePath();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_257_PATHNAME_CREATED, "PWD", currDir));
    }
}
