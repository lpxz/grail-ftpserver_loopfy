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
 * <code>CDUP &lt;CRLF&gt;</code><br>
 * 
 * This command is a special case of CWD, and is included to simplify the
 * implementation of programs for transferring directory trees between operating
 * systems having different syntaxes for naming the parent directory. The reply
 * codes shall be identical to the reply codes of CWD.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class CDUP extends AbstractCommand {

    private final Logger LOG = LoggerFactory.getLogger(CDUP.class);

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        FileSystemView fsview = session.getFileSystemView();
        boolean success = false;
        try {
            success = fsview.changeWorkingDirectory("..");
        } catch (Exception ex) {
            LOG.debug("Failed to change directory in file system", ex);
        }
        if (success) {
            String dirName = fsview.getWorkingDirectory().getAbsolutePath();
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_250_REQUESTED_FILE_ACTION_OKAY, "CDUP", dirName));
        } else {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "CDUP", null));
        }
    }
}
