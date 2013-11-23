package org.apache.ftpserver.ssl;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public abstract class ImplicitSecurityTestTemplate extends ExplicitSecurityTestTemplate {

    protected boolean useImplicit() {
        return true;
    }
}
