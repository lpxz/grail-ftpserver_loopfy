package org.apache.ftpserver.ssl;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class MinaExplicitTLSTest extends ExplicitSecurityTestTemplate {

    protected String getAuthValue() {
        return "TLS";
    }
}
