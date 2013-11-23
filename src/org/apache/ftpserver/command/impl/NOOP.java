package org.apache.ftpserver.command.impl;

import java.io.IOException;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>NOOP &lt;CRLF&gt;</code><br>
 * 
 * This command does not affect any parameters or previously entered commands.
 * It specifies no action other than that the server send an OK reply.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class NOOP extends AbstractCommand {

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_200_COMMAND_OKAY, "NOOP", null));
    }
}
