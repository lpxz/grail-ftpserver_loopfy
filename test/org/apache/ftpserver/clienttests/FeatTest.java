package org.apache.ftpserver.clienttests;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class FeatTest extends ClientTestTemplate {

    public void test() throws Exception {
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        client.sendCommand("FEAT");
        String[] featReplies = client.getReplyString().split("\r\n");
        edu.hkust.clap.monitor.Monitor.loopBegin(18);
for (int i = 0; i < featReplies.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(18);
{
            if (i == 0) {
                assertEquals("211-Extensions supported", featReplies[i]);
            } else if (i + 1 == featReplies.length) {
                assertEquals("211 End", featReplies[i]);
            } else {
                assertEquals(' ', featReplies[i].charAt(0));
                assertTrue(featReplies[i].charAt(1) != ' ');
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(18);

    }
}
