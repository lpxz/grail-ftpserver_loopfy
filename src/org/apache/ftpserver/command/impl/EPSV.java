package org.apache.ftpserver.command.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.ftpserver.DataConnectionException;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.apache.ftpserver.impl.ServerDataConnectionFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * The EPSV command requests that a server listen on a data port and wait for a
 * connection. The EPSV command takes an optional argument. The response to this
 * command includes only the TCP port number of the listening connection. The
 * format of the response, however, is similar to the argument of the EPRT
 * command. This allows the same parsing routines to be used for both commands.
 * In addition, the format leaves a place holder for the network protocol and/or
 * network address, which may be needed in the EPSV response in the future. The
 * response code for entering passive mode using an extended address MUST be
 * 229.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class EPSV extends AbstractCommand {

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        ServerDataConnectionFactory dataCon = session.getDataConnection();
        try {
            InetSocketAddress dataConAddress = dataCon.initPassiveDataConnection();
            int servPort = dataConAddress.getPort();
            String portStr = "|||" + servPort + '|';
            session.write(LocalizedFtpReply.translate(session, request, context, 229, "EPSV", portStr));
        } catch (DataConnectionException e) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_425_CANT_OPEN_DATA_CONNECTION, "EPSV", null));
            return;
        }
    }
}
