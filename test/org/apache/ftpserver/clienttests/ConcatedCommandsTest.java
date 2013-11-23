package org.apache.ftpserver.clienttests;

import org.apache.commons.net.ftp.FTPReply;

/**
 * Tests that commands sent simultaniously are handled correctly.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> *
 */
public class ConcatedCommandsTest extends ClientTestTemplate {

    public void testLogin() throws Exception {
        assertEquals(331, client.sendCommand("USER admin\r\nPASS admin"));
        client.completePendingCommand();
        assertEquals(230, client.getReplyCode());
        assertTrue(FTPReply.isPositiveCompletion(client.noop()));
    }
}
