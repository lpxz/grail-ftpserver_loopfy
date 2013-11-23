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
 * Show SITE help message.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class SITE_HELP extends AbstractCommand {

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_200_COMMAND_OKAY, "SITE.HELP", null));
    }
}
