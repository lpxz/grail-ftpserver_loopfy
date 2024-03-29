package org.apache.ftpserver.command.impl;

import java.io.IOException;
import org.apache.ftpserver.command.AbstractCommand;
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
 * <code>REST &lt;SP&gt; <marker> &lt;CRLF&gt;</code><br>
 * 
 * The argument field represents the server marker at which file transfer is to
 * be restarted. This command does not cause file transfer but skips over the
 * file to the specified data checkpoint. This command shall be immediately
 * followed by the appropriate FTP service command which shall cause file
 * transfer to resume.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class REST extends AbstractCommand {

    private final Logger LOG = LoggerFactory.getLogger(REST.class);

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        String argument = request.getArgument();
        if (argument == null) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "REST", null));
            return;
        }
        session.resetState();
        long skipLen = 0L;
        try {
            skipLen = Long.parseLong(argument);
            if (skipLen < 0L) {
                skipLen = 0L;
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "REST.negetive", null));
            } else {
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_350_REQUESTED_FILE_ACTION_PENDING_FURTHER_INFORMATION, "REST", null));
            }
        } catch (NumberFormatException ex) {
            LOG.debug("Invalid restart position: " + argument, ex);
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "REST.invalid", null));
        }
        session.setFileOffset(skipLen);
    }
}
