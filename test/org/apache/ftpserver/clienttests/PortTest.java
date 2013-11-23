package org.apache.ftpserver.clienttests;

import java.net.InetAddress;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>
*
*/
public class PortTest extends ClientTestTemplate {

    protected void setUp() throws Exception {
        super.setUp();
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public void testInvalidIpAndPort() throws Exception {
        assertEquals(501, client.port(InetAddress.getByName("0.0.0.0"), 0));
    }
}
