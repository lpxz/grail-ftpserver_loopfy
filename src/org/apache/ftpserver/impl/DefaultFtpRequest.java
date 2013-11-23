package org.apache.ftpserver.impl;

import org.apache.ftpserver.ftplet.FtpRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * FTP request object.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class DefaultFtpRequest implements FtpRequest {

    private String line;

    private String command;

    private String argument;

    /**
     * Default constructor.
     */
    public DefaultFtpRequest(final String requestLine) {
        parse(requestLine);
    }

    /**
     * Parse the ftp command line.
     */
    private void parse(final String lineToParse) {
        line = lineToParse.trim();
        command = null;
        argument = null;
        int spInd = line.indexOf(' ');
        if (spInd != -1) {
            argument = line.substring(spInd + 1);
            if (argument.equals("")) {
                argument = null;
            }
            command = line.substring(0, spInd).toUpperCase();
        } else {
            command = line.toUpperCase();
        }
        if ((command.length() > 0) && (command.charAt(0) == 'X')) {
            command = command.substring(1);
        }
    }

    /**
     * Get the ftp command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get ftp input argument.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Get the ftp request line.
     */
    public String getRequestLine() {
        return line;
    }

    /**
     * Has argument.
     */
    public boolean hasArgument() {
        return getArgument() != null;
    }

    public String toString() {
        return getRequestLine();
    }
}
