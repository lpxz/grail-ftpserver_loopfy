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
 * The FEAT command (introduced in [RFC-2389]) allows servers with additional
 * features to advertise these to a client by responding to the FEAT command. If
 * a server supports the FEAT command then it MUST advertise supported AUTH,
 * PBSZ and PROT commands in the reply.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class FEAT extends AbstractCommand {

    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_211_SYSTEM_STATUS_REPLY, "FEAT", null));
    }
}
