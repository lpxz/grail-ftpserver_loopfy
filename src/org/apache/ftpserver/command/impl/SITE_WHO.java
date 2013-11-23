package org.apache.ftpserver.command.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.apache.ftpserver.util.DateUtils;
import org.apache.ftpserver.util.StringUtils;
import org.apache.mina.core.session.IoSession;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Sends the list of all the connected users.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class SITE_WHO extends AbstractCommand {

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        UserManager userManager = context.getUserManager();
        boolean isAdmin = userManager.isAdmin(session.getUser().getName());
        if (!isAdmin) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_530_NOT_LOGGED_IN, "SITE", null));
            return;
        }
        StringBuilder sb = new StringBuilder();
        Map<Long, IoSession> sessions = session.getService().getManagedSessions();
        sb.append('\n');
        Iterator<IoSession> sessionIterator = sessions.values().iterator();
        edu.hkust.clap.monitor.Monitor.loopBegin(16);
while (sessionIterator.hasNext()) { 
edu.hkust.clap.monitor.Monitor.loopInc(16);
{
            FtpIoSession managedSession = new FtpIoSession(sessionIterator.next(), context);
            if (!managedSession.isLoggedIn()) {
                continue;
            }
            User tmpUsr = managedSession.getUser();
            sb.append(StringUtils.pad(tmpUsr.getName(), ' ', true, 16));
            if (managedSession.getRemoteAddress() instanceof InetSocketAddress) {
                sb.append(StringUtils.pad(((InetSocketAddress) managedSession.getRemoteAddress()).getAddress().getHostAddress(), ' ', true, 16));
            }
            sb.append(StringUtils.pad(DateUtils.getISO8601Date(managedSession.getLoginTime().getTime()), ' ', true, 20));
            sb.append(StringUtils.pad(DateUtils.getISO8601Date(managedSession.getLastAccessTime().getTime()), ' ', true, 20));
            sb.append('\n');
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(16);

        sb.append('\n');
        session.write(new DefaultFtpReply(FtpReply.REPLY_200_COMMAND_OKAY, sb.toString()));
    }
}
