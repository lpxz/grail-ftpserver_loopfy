package org.apache.ftpserver.ssl;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class MinaExplicitSSLTest extends ExplicitSecurityTestTemplate {

    protected String getAuthValue() {
        return "SSL";
    }
}
