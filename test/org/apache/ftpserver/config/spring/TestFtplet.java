package org.apache.ftpserver.config.spring;

import org.apache.ftpserver.ftplet.DefaultFtplet;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class TestFtplet extends DefaultFtplet {

    private int foo;

    public int getFoo() {
        return foo;
    }

    public void setFoo(int foo) {
        this.foo = foo;
    }
}
