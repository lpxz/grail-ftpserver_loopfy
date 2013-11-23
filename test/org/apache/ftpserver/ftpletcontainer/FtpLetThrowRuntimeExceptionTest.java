package org.apache.ftpserver.ftpletcontainer;

import java.io.IOException;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class FtpLetThrowRuntimeExceptionTest extends FtpLetThrowFtpExceptionTest {

    protected void throwException() throws IOException {
        throw new IOException();
    }
}
