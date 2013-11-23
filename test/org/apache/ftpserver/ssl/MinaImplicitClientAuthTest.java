package org.apache.ftpserver.ssl;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class MinaImplicitClientAuthTest extends MinaClientAuthTest {

    protected boolean useImplicit() {
        return true;
    }
}
