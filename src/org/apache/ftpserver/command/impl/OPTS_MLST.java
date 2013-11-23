package org.apache.ftpserver.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Client-Server listing negotation. Instruct the server what listing types to
 * include in machine directory/file listings.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class OPTS_MLST extends AbstractCommand {

    private static final String[] AVAILABLE_TYPES = { "Size", "Modify", "Type", "Perm" };

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request) throws IOException, FtpException {
        session.resetState();
        String argument = request.getArgument();
        String listTypes;
        String types[];
        int spIndex = argument.indexOf(' ');
        if (spIndex == -1) {
            types = new String[0];
            listTypes = "";
        } else {
            listTypes = argument.substring(spIndex + 1);
            StringTokenizer st = new StringTokenizer(listTypes, ";");
            types = new String[st.countTokens()];
            edu.hkust.clap.monitor.Monitor.loopBegin(29);
for (int i = 0; i < types.length; ++i) { 
edu.hkust.clap.monitor.Monitor.loopInc(29);
{
                types[i] = st.nextToken();
            }} 
edu.hkust.clap.monitor.Monitor.loopEnd(29);

        }
        String[] validatedTypes = validateSelectedTypes(types);
        if (validatedTypes != null) {
            session.setAttribute("MLST.types", validatedTypes);
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_200_COMMAND_OKAY, "OPTS.MLST", listTypes));
        } else {
            session.write(LocalizedFtpReply.translate(session, request, context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "OPTS.MLST", listTypes));
        }
    }

    private String[] validateSelectedTypes(final String types[]) {
        if (types == null) {
            return new String[0];
        }
        List<String> selectedTypes = new ArrayList<String>();
        edu.hkust.clap.monitor.Monitor.loopBegin(30);
for (int i = 0; i < types.length; ++i) { 
edu.hkust.clap.monitor.Monitor.loopInc(30);
{
            edu.hkust.clap.monitor.Monitor.loopBegin(31);
for (int j = 0; j < AVAILABLE_TYPES.length; ++j) { 
edu.hkust.clap.monitor.Monitor.loopInc(31);
{
                if (AVAILABLE_TYPES[j].equalsIgnoreCase(types[i])) {
                    selectedTypes.add(AVAILABLE_TYPES[j]);
                    break;
                }
            }} 
edu.hkust.clap.monitor.Monitor.loopEnd(31);

        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(30);

        return selectedTypes.toArray(new String[0]);
    }
}
