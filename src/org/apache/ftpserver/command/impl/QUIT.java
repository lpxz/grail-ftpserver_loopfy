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
 * <code>QUIT &lt;CRLF&gt;</code><br>
 * 
 * This command terminates a USER and if file transfer is not in progress, the
 * server closes the control connection.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class QUIT extends AbstractCommand {

    private final Logger LOG = LoggerFactory.getLogger(QUIT.class);

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_221_CLOSING_CONTROL_CONNECTION, "QUIT", null));
        LOG.debug("QUIT received, closing session");
        session.close(false).awaitUninterruptibly(10000);
        session.getDataConnection().closeDataConnection();
    }
}
