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
 * <code>ABOR &lt;CRLF&gt;</code><br>
 * 
 * This command tells the server to abort the previous FTP service command and
 * any associated transfer of data. No action is to be taken if the previous
 * command has been completed (including data transfer). The control connection
 * is not to be closed by the server, but the data connection must be closed.
 * Current implementation does not do anything. As here data transfers are not
 * multi-threaded.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class ABOR extends AbstractCommand {

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        session.getDataConnection().closeDataConnection();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_226_CLOSING_DATA_CONNECTION, "ABOR", null));
    }
}
