package org.apache.ftpserver.command.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.ftpserver.command.Command;
import org.apache.ftpserver.command.CommandFactory;
import org.apache.ftpserver.command.CommandFactoryFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Command factory to return appropriate command implementation depending on the
 * FTP request command string.
 *
 * <strong><strong>Internal class, do not use directly.</strong></strong>
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class DefaultCommandFactory implements CommandFactory {

    /**
     * Internal constructor, use {@link CommandFactoryFactory} instead
     */
    public DefaultCommandFactory(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    private Map<String, Command> commandMap = new HashMap<String, Command>();

    /**
     * Get command. Returns null if not found.
     */
    public Command getCommand(final String cmdName) {
        if (cmdName == null || cmdName.equals("")) {
            return null;
        }
        String upperCaseCmdName = cmdName.toUpperCase();
        return commandMap.get(upperCaseCmdName);
    }
}
