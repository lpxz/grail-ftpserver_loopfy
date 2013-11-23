package org.apache.ftpserver.clienttests;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class ListPassiveTest extends ListTest {

    protected void setUp() throws Exception {
        super.setUp();
        client.setRemoteVerificationEnabled(false);
        client.enterLocalPassiveMode();
    }
}
