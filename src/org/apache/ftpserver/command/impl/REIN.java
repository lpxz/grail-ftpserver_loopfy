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
 * <code>REIN &lt;CRLF&gt;</code><br>
 * 
 * This command flushes a USER, without affecting transfers in progress. The
 * server state should otherwise be as when the user first connects.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class REIN extends AbstractCommand {

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.reinitialize();
        session.setLanguage(null);
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_220_SERVICE_READY, "REIN", null));
    }
}
