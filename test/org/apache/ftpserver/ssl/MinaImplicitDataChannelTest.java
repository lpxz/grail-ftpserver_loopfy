package org.apache.ftpserver.ssl;

import java.io.ByteArrayInputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.ftp.FTPSSocketFactory;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.impl.ServerDataConnectionFactory;

/**
* @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class MinaImplicitDataChannelTest extends ImplicitSecurityTestTemplate {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected String getAuthValue() {
        return "SSL";
    }

    protected DataConnectionConfigurationFactory createDataConnectionConfigurationFactory() {
        DataConnectionConfigurationFactory result = super.createDataConnectionConfigurationFactory();
        result.setImplicitSsl(true);
        return result;
    }

    protected boolean useImplicit() {
        return true;
    }

    /**
     * Simple test that the {@link ServerDataConnectionFactory#isSecure()} 
     * works as expected
     */
    public void testThatDataChannelIsSecure() {
        assertTrue(getActiveSession().getDataConnection().isSecure());
    }

    /**
     * Test that implicit SSL data connections works with clients that
     * use implicit SSL for the data connection, without sending PROT P. 
     * In this case in active mode.
     * 
     * The inherited tests from {@link ExplicitSecurityTestTemplate} ensures that 
     * data transfers work when using PROT P
     */
    public void testStoreWithoutProtPInActiveMode() throws Exception {
        secureClientDataConnection();
        assertTrue(getActiveSession().getDataConnection().isSecure());
        client.storeFile(TEST_FILE1.getName(), new ByteArrayInputStream(TEST_DATA));
        assertTrue(TEST_FILE1.exists());
        assertEquals(TEST_DATA.length, TEST_FILE1.length());
    }

    /**
     * Test that implicit SSL data connections works with clients that
     * use implicit SSL for the data connection, without sending PROT P. 
     * In this case in active mode.
     */
    public void testStoreWithProtPInPassiveMode() throws Exception {
        secureClientDataConnection();
        client.enterLocalPassiveMode();
        assertTrue(getActiveSession().getDataConnection().isSecure());
        client.storeFile(TEST_FILE1.getName(), new ByteArrayInputStream(TEST_DATA));
        assertTrue(TEST_FILE1.exists());
        assertEquals(TEST_DATA.length, TEST_FILE1.length());
    }

    private void secureClientDataConnection() throws NoSuchAlgorithmException, KeyManagementException {
        FTPSClient sclient = (FTPSClient) client;
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(new KeyManager[] { clientKeyManager }, new TrustManager[] { clientTrustManager }, null);
        sclient.setSocketFactory(new FTPSSocketFactory(context));
        SSLServerSocketFactory ssf = context.getServerSocketFactory();
        sclient.setServerSocketFactory(ssf);
    }
}
