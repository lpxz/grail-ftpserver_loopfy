package org.apache.ftpserver.command.impl;

import java.io.IOException;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>MODE &lt;SP&gt; <mode-code> &lt;CRLF&gt;</code><br>
 * 
 * The argument is a single Telnet character code specifying the data transfer
 * modes described in the Section on Transmission Modes.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class MODE extends AbstractCommand {

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        if (!request.hasArgument()) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "MODE", null));
            return;
        }
        char md = request.getArgument().charAt(0);
        md = Character.toUpperCase(md);
        if (md == 'S') {
            session.getDataConnection().setZipMode(false);
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_200_COMMAND_OKAY, "MODE", "S"));
        } else if (md == 'Z') {
            session.getDataConnection().setZipMode(true);
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_200_COMMAND_OKAY, "MODE", "Z"));
        } else {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_504_COMMAND_NOT_IMPLEMENTED_FOR_THAT_PARAMETER, "MODE", null));
        }
    }
}
