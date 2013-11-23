package org.apache.ftpserver.command;

/**
 * Command factory interface.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public interface CommandFactory {

    /**
     * Get the command instance.
     * @param commandName The name of the command to create
     * @return The {@link Command} matching the provided name, or
     *   null if no such command exists.
     */
    Command getCommand(String commandName);
}
