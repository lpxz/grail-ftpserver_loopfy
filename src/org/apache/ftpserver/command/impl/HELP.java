package org.apache.ftpserver.command.impl;

import java.io.IOException;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.apache.ftpserver.message.MessageResource;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>HELP [&lt;SP&gt; <string>] &lt;CRLF&gt;</code><br>
 * 
 * This command shall cause the server to send helpful information regarding its
 * implementation status over the control connection to the user. The command
 * may take an argument (e.g., any command name) and return more specific
 * information as a response.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class HELP extends AbstractCommand {

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        if (!request.hasArgument()) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_214_HELP_MESSAGE, null, null));
            return;
        }
        String ftpCmd = request.getArgument().toUpperCase();
        MessageResource resource = context.getMessageResource();
        if (resource.getMessage(FtpReply.REPLY_214_HELP_MESSAGE, ftpCmd, session.getLanguage()) == null) {
            ftpCmd = null;
        }
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_214_HELP_MESSAGE, ftpCmd, null));
    }
}
