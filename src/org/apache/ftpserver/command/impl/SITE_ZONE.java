package org.apache.ftpserver.command.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Displays the FTP server timezone in RFC 822 format.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class SITE_ZONE extends AbstractCommand {

    private static final SimpleDateFormat TIMEZONE_FMT = new SimpleDateFormat("Z");

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        String timezone = TIMEZONE_FMT.format(new Date());
        session.write(new DefaultFtpReply(FtpReply.REPLY_200_COMMAND_OKAY, timezone));
    }
}
