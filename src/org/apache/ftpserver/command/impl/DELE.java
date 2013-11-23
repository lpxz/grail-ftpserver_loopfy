package org.apache.ftpserver.command.impl;

import java.io.IOException;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.apache.ftpserver.impl.ServerFtpStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>DELE &lt;SP&gt; &lt;pathname&gt; &lt;CRLF&gt;</code><br>
 * 
 * This command causes the file specified in the pathname to be deleted at the
 * server site.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class DELE extends AbstractCommand {

    private final Logger LOG = LoggerFactory.getLogger(DELE.class);

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        String fileName = request.getArgument();
        if (fileName == null) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "DELE", null));
            return;
        }
        FtpFile file = null;
        try {
            file = session.getFileSystemView().getFile(fileName);
        } catch (Exception ex) {
            LOG.debug("Could not get file " + fileName, ex);
        }
        if (file == null) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "DELE.invalid", fileName));
            return;
        }
        fileName = file.getAbsolutePath();
        if (file.isDirectory()) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "DELE.invalid", fileName));
            return;
        }
        if (!file.isRemovable()) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "DELE.permission", fileName));
            return;
        }
        if (file.delete()) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_250_REQUESTED_FILE_ACTION_OKAY, "DELE", fileName));
            String userName = session.getUser().getName();
            LOG.info("File delete : " + userName + " - " + fileName);
            ServerFtpStatistics ftpStat = (ServerFtpStatistics) context.getFtpStatistics();
            ftpStat.setDelete(session, file);
        } else {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "DELE", fileName));
        }
    }
}
