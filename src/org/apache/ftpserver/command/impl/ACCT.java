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
 * <code>ACCT &lt;CRLF&gt;</code><br>
 * 
 * Acknowledges the ACCT (account) command with a 202 reply. The command however
 * is irrelevant to any workings.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class ACCT extends AbstractCommand {

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_202_COMMAND_NOT_IMPLEMENTED, "ACCT", null));
    }
}
