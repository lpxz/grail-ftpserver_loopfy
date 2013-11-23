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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>CWD  &lt;SP&gt; &lt;pathname&gt; &lt;CRLF&gt;</code><br>
 * 
 * This command allows the user to work with a different directory for file
 * storage or retrieval without altering his login or accounting information.
 * Transfer parameters are similarly unchanged. The argument is a pathname
 * specifying a directory.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class CWD extends AbstractCommand {

    private final Logger LOG = LoggerFactory.getLogger(CWD.class);

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        String dirName = "/";
        if (request.hasArgument()) {
            dirName = request.getArgument();
        }
        FileSystemView fsview = session.getFileSystemView();
        boolean success = false;
        try {
            success = fsview.changeWorkingDirectory(dirName);
        } catch (Exception ex) {
            LOG.debug("Failed to change directory in file system", ex);
        }
        if (success) {
            dirName = fsview.getWorkingDirectory().getAbsolutePath();
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_250_REQUESTED_FILE_ACTION_OKAY, "CWD", dirName));
        } else {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "CWD", null));
        }
    }
}
