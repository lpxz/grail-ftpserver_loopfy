package org.apache.ftpserver.command.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.apache.ftpserver.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command for changing the modified time of a file. 
 * <p>
 * Specified in the following document:
 * http://www.omz13.com/downloads/draft-somers-ftp-mfxx-00.html#anchor8
 * </p>
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class MFMT extends AbstractCommand {

    private final Logger LOG = LoggerFactory.getLogger(MFMT.class);

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException {
        session.resetState();
        String argument = request.getArgument();
        if (argument == null || argument.trim().length() == 0) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "MFMT.invalid", null));
            return;
        }
        String[] arguments = argument.split(" ", 2);
        if (arguments.length != 2) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "MFMT.invalid", null));
            return;
        }
        String timestamp = arguments[0].trim();
        try {
            Date time = DateUtils.parseFTPDate(timestamp);
            String fileName = arguments[1].trim();
            FtpFile file = null;
            try {
                file = session.getFileSystemView().getFile(fileName);
            } catch (Exception ex) {
                LOG.debug("Exception getting the file object: " + fileName, ex);
            }
            if (file == null || !file.doesExist()) {
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "MFMT.filemissing", fileName));
                return;
            }
            if (!file.isFile()) {
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "MFMT.invalid", null));
                return;
            }
            if (!file.setLastModified(time.getTime())) {
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "MFMT", fileName));
                return;
            }
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_213_FILE_STATUS, "MFMT", "ModifyTime=" + timestamp + "; " + fileName));
            return;
        } catch (ParseException e) {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "MFMT.invalid", null));
            return;
        }
    }
}
