package org.apache.ftpserver.impl;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.ftpserver.ConnectionConfig;
import org.apache.ftpserver.command.CommandFactory;
import org.apache.ftpserver.ftplet.FtpletContext;
import org.apache.ftpserver.ftpletcontainer.FtpletContainer;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.message.MessageResource;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * This is basically <code>org.apache.ftpserver.ftplet.FtpletContext</code> with
 * added connection manager, message resource functionalities.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface FtpServerContext extends FtpletContext {

    ConnectionConfig getConnectionConfig();

    /**
     * Get message resource.
     */
    MessageResource getMessageResource();

    /**
     * Get ftplet container.
     */
    FtpletContainer getFtpletContainer();

    Listener getListener(String name);

    Map<String, Listener> getListeners();

    /**
     * Get the command factory.
     */
    CommandFactory getCommandFactory();

    /**
     * Release all components.
     */
    void dispose();

    /**
     * Returns the thread pool executor for this context.  
     * @return the thread pool executor for this context.
     */
    ThreadPoolExecutor getThreadPoolExecutor();
}
