package org.apache.ftpserver.command;

import java.io.IOException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;

/**
 * A command used primarily for overriding already installed commands when one
 * wants to disable the command.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class NotSupportedCommand extends AbstractCommand {

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_502_COMMAND_NOT_IMPLEMENTED, "Not supported", null));
    }
}
