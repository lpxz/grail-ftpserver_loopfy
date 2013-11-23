package org.apache.ftpserver.clienttests;

import java.net.ServerSocket;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.test.TestUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>
*
*/
public class PasvPortUnavailableTest extends ClientTestTemplate {

    private int passivePort;

    @Override
    protected FtpServerFactory createServer() throws Exception {
        FtpServerFactory server = super.createServer();
        ListenerFactory listenerFactory = new ListenerFactory(server.getListener("default"));
        DataConnectionConfigurationFactory dccFactory = new DataConnectionConfigurationFactory();
        passivePort = TestUtil.findFreePort(12444);
        dccFactory.setPassivePorts(String.valueOf(passivePort));
        listenerFactory.setDataConnectionConfiguration(dccFactory.createDataConnectionConfiguration());
        server.addListener("default", listenerFactory.createListener());
        return server;
    }

    public void testPasvPortUnavailable() throws Exception {
        FTPClient[] clients = new FTPClient[3];
        edu.hkust.clap.monitor.Monitor.loopBegin(41);
for (int i = 0; i < 3; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(41);
{
            clients[i] = createFTPClient();
            clients[i].connect("localhost", getListenerPort());
            clients[i].login(ADMIN_USERNAME, ADMIN_PASSWORD);
            clients[i].pasv();
            if (i < 1) {
                assertTrue(clients[i].getReplyString(), clients[i].getReplyString().trim().startsWith("227"));
            } else {
                assertTrue(clients[i].getReplyString(), clients[i].getReplyString().trim().startsWith("425"));
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(41);

        edu.hkust.clap.monitor.Monitor.loopBegin(42);
for (int i = 0; i < 3; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(42);
{
            if (clients[i] != null) {
                clients[i].disconnect();
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(42);

    }
}
