package org.apache.ftpserver.clienttests;

import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.test.TestUtil;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class PasvTest extends ClientTestTemplate {

    protected boolean isConnectClient() {
        return false;
    }

    @Override
    protected FtpServerFactory createServer() throws Exception {
        FtpServerFactory server = super.createServer();
        ListenerFactory listenerFactory = new ListenerFactory(server.getListener("default"));
        DataConnectionConfigurationFactory dccFactory = new DataConnectionConfigurationFactory();
        int passivePort = TestUtil.findFreePort(12444);
        dccFactory.setPassivePorts(passivePort + "-" + passivePort);
        listenerFactory.setDataConnectionConfiguration(dccFactory.createDataConnectionConfiguration());
        server.addListener("default", listenerFactory.createListener());
        return server;
    }

    public void testMultiplePasv() throws Exception {
        edu.hkust.clap.monitor.Monitor.loopBegin(51);
for (int i = 0; i < 5; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(51);
{
            client.connect("localhost", getListenerPort());
            client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
            client.pasv();
            client.quit();
            client.disconnect();
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(51);

    }

    /**
     * This tests that the correct IP is returned, that is the IP that the
     * client has connected to.
     * 
     * Note that this test will only work if you got more than one NIC and the
     * server is allowed to listen an all NICs
     */
    public void testPasvIp() throws Exception {
        String[] ips = TestUtil.getHostAddresses();
        edu.hkust.clap.monitor.Monitor.loopBegin(52);
for (int i = 0; i < ips.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(52);
{
            String ip = ips[i];
            String ftpIp = ip.replace('.', ',');
            if (!ip.startsWith("0.")) {
                try {
                    client.connect(ip, getListenerPort());
                } catch (FTPConnectionClosedException e) {
                    Thread.sleep(200);
                    client.connect(ip, getListenerPort());
                }
                client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
                client.pasv();
                assertTrue(client.getReplyString().indexOf(ftpIp) > -1);
                client.quit();
                client.disconnect();
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(52);

    }
}
