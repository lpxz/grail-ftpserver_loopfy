package org.apache.ftpserver.clienttests;

import org.apache.commons.net.ftp.FTPReply;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class SystTest extends ClientTestTemplate {

    protected void setUp() throws Exception {
        super.setUp();
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public void testSyst() throws Exception {
        assertTrue(FTPReply.isPositiveCompletion(client.syst()));
        assertEquals("215 UNIX Type: Apache FtpServer", client.getReplyString().trim());
    }
}
