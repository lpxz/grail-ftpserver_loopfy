package org.apache.ftpserver.command.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.command.impl.listing.DirectoryLister;
import org.apache.ftpserver.command.impl.listing.FileFormater;
import org.apache.ftpserver.command.impl.listing.LISTFileFormater;
import org.apache.ftpserver.command.impl.listing.ListArgument;
import org.apache.ftpserver.command.impl.listing.ListArgumentParser;
import org.apache.ftpserver.command.impl.listing.NLSTFileFormater;
import org.apache.ftpserver.ftplet.DataConnection;
import org.apache.ftpserver.ftplet.DataConnectionFactory;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.IODataConnectionFactory;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>NLST [&lt;SP&gt; &lt;pathname&gt;] &lt;CRLF&gt;</code><br>
 * 
 * This command causes a directory listing to be sent from server to user site.
 * The pathname should specify a directory or other system-specific file group
 * descriptor; a null argument implies the current directory. The server will
 * return a stream of names of files and no other information.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class NLST extends AbstractCommand {

    private final Logger LOG = LoggerFactory.getLogger(NLST.class);

    private static final NLSTFileFormater NLST_FILE_FORMATER = new NLSTFileFormater();

    private static final LISTFileFormater LIST_FILE_FORMATER = new LISTFileFormater();

    private DirectoryLister directoryLister = new DirectoryLister();

    /**
     * Execute command
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        try {
            session.resetState();
            DataConnectionFactory connFactory = session.getDataConnection();
            if (connFactory instanceof IODataConnectionFactory) {
                InetAddress address = ((IODataConnectionFactory) connFactory).getInetAddress();
                if (address == null) {
                    session.write(new DefaultFtpReply(FtpReply.REPLY_503_BAD_SEQUENCE_OF_COMMANDS, "PORT or PASV must be issued first"));
                    return;
                }
            }
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_150_FILE_STATUS_OKAY, "NLST", null));
            DataConnection dataConnection;
            try {
                dataConnection = session.getDataConnection().openConnection();
            } catch (Exception e) {
                LOG.debug("Exception getting the output data stream", e);
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_425_CANT_OPEN_DATA_CONNECTION, "NLST", null));
                return;
            }
            boolean failure = false;
            try {
                ListArgument parsedArg = ListArgumentParser.parse(request.getArgument());
                FileFormater formater;
                if (parsedArg.hasOption('l')) {
                    formater = LIST_FILE_FORMATER;
                } else {
                    formater = NLST_FILE_FORMATER;
                }
                dataConnection.transferToClient(session.getFtpletSession(), directoryLister.listFiles(parsedArg, session.getFileSystemView(), formater));
            } catch (SocketException ex) {
                LOG.debug("Socket exception during data transfer", ex);
                failure = true;
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_426_CONNECTION_CLOSED_TRANSFER_ABORTED, "NLST", null));
            } catch (IOException ex) {
                LOG.debug("IOException during data transfer", ex);
                failure = true;
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_551_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN, "NLST", null));
            } catch (IllegalArgumentException e) {
                LOG.debug("Illegal listing syntax: " + request.getArgument(), e);
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "LIST", null));
            }
            if (!failure) {
                session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_226_CLOSING_DATA_CONNECTION, "NLST", null));
            }
        } finally {
            session.getDataConnection().closeDataConnection();
        }
    }
}
