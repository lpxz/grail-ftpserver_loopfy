package org.apache.ftpserver.clienttests;

import java.io.File;
import java.io.OutputStream;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;

/**
*
* Test for FTPSERVER-170
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class DataTransferTimeoutTest extends ClientTestTemplate {

    private static final String TEST_FILENAME = "test.txt";

    private static final File TEST_FILE = new File(ROOT_DIR, TEST_FILENAME);

    @Override
    protected FtpServerFactory createServer() throws Exception {
        FtpServerFactory serverFactory = super.createServer();
        ListenerFactory listenerFactory = new ListenerFactory(serverFactory.getListener("default"));
        listenerFactory.setIdleTimeout(1);
        serverFactory.addListener("default", listenerFactory.createListener());
        return serverFactory;
    }

    protected void setUp() throws Exception {
        super.setUp();
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public void testTimeoutForStore() throws Exception {
        OutputStream os = client.storeFileStream(TEST_FILENAME);
        os.write(1);
        edu.hkust.clap.monitor.Monitor.loopBegin(0);
for (int i = 0; i < 100; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(0);
{
            Thread.sleep(20);
            os.write(1);
            os.flush();
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(0);

        os.close();
        client.completePendingCommand();
        client.noop();
    }
}
