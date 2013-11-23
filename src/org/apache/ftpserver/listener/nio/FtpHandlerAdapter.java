package org.apache.ftpserver.listener.nio;

import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.DefaultFtpRequest;
import org.apache.ftpserver.impl.FtpHandler;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.MdcInjectionFilter;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Adapter between MINA handler and the {@link FtpHandler} interface
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> *
 */
public class FtpHandlerAdapter implements IoHandler {

    private FtpServerContext context;

    private FtpHandler ftpHandler;

    public FtpHandlerAdapter(FtpServerContext context, FtpHandler ftpHandler) {
        this.context = context;
        this.ftpHandler = ftpHandler;
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        FtpIoSession ftpSession = new FtpIoSession(session, context);
        ftpHandler.exceptionCaught(ftpSession, cause);
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        FtpIoSession ftpSession = new FtpIoSession(session, context);
        FtpRequest request = new DefaultFtpRequest(message.toString());
        ftpHandler.messageReceived(ftpSession, request);
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        FtpIoSession ftpSession = new FtpIoSession(session, context);
        ftpHandler.messageSent(ftpSession, (FtpReply) message);
    }

    public void sessionClosed(IoSession session) throws Exception {
        FtpIoSession ftpSession = new FtpIoSession(session, context);
        ftpHandler.sessionClosed(ftpSession);
    }

    public void sessionCreated(IoSession session) throws Exception {
        FtpIoSession ftpSession = new FtpIoSession(session, context);
        MdcInjectionFilter.setProperty(session, "session", ftpSession.getSessionId().toString());
        ftpHandler.sessionCreated(ftpSession);
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        FtpIoSession ftpSession = new FtpIoSession(session, context);
        ftpHandler.sessionIdle(ftpSession, status);
    }

    public void sessionOpened(IoSession session) throws Exception {
        FtpIoSession ftpSession = new FtpIoSession(session, context);
        ftpHandler.sessionOpened(ftpSession);
    }

    public FtpHandler getFtpHandler() {
        return ftpHandler;
    }

    public void setFtpHandler(FtpHandler handler) {
        this.ftpHandler = handler;
    }
}
