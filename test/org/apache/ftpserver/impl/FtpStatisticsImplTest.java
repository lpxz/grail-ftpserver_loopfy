package org.apache.ftpserver.impl;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class FtpStatisticsImplTest extends ServerFtpStatisticsTestTemplate {

    protected DefaultFtpStatistics createStatistics() {
        return new DefaultFtpStatistics();
    }
}
