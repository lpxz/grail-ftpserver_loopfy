package org.apache.ftpserver.ssl;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class MinaImplicitSSLTest extends ImplicitSecurityTestTemplate {

    protected String getAuthValue() {
        return "SSL";
    }
}
