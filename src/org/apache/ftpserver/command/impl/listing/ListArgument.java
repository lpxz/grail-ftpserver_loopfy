package org.apache.ftpserver.command.impl.listing;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Contains the parsed argument for a list command (e.g. LIST or NLST)
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ListArgument {

    private String file;

    private String pattern;

    private char[] options;

    /**
     * @param file
     *            The file path including the directory
     * @param pattern
     *            A regular expression pattern that files must match
     * @param options
     *            List options, such as -la
     */
    public ListArgument(String file, String pattern, char[] options) {
        this.file = file;
        this.pattern = pattern;
        if (options == null) {
            this.options = new char[0];
        } else {
            this.options = options.clone();
        }
    }

    /**
     * The listing options,
     * 
     * @return All options
     */
    public char[] getOptions() {
        return options.clone();
    }

    /**
     * The regular expression pattern that files must match
     * 
     * @return The regular expression
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Checks if a certain option is set
     * 
     * @param option
     *            The option to check
     * @return true if the option is set
     */
    public boolean hasOption(char option) {
        edu.hkust.clap.monitor.Monitor.loopBegin(8);
for (int i = 0; i < options.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(8);
{
            if (option == options[i]) {
                return true;
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(8);

        return false;
    }

    /**
     * The file path including the directory
     * 
     * @return The file path
     */
    public String getFile() {
        return file;
    }
}
