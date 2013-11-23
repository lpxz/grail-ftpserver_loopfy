package org.apache.ftpserver.ftpletcontainer;

import java.io.IOException;
import org.apache.ftpserver.ftplet.FtpException;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class FtpLetThrowFtpExceptionTest extends FtpLetReturnDisconnectTest {

    protected void throwException() throws FtpException, IOException {
        throw new FtpException();
    }
}
