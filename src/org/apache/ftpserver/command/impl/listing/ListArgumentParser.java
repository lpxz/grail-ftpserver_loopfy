package org.apache.ftpserver.command.impl.listing;

import java.util.StringTokenizer;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Parses a list argument (e.g. for LIST or NLST) into a {@link ListArgument}
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ListArgumentParser {

    /**
     * Parse the argument
     * 
     * @param argument
     *            The argument string
     * @return The parsed argument
     * @throws IllegalArgumentException
     *             If the argument string is incorrectly formated
     */
    public static ListArgument parse(String argument) {
        String file = "./";
        String options = "";
        String pattern = "*";
        if (argument != null) {
            argument = argument.trim();
            StringBuilder optionsSb = new StringBuilder(4);
            StringBuilder fileSb = new StringBuilder(16);
            StringTokenizer st = new StringTokenizer(argument, " ", true);
            edu.hkust.clap.monitor.Monitor.loopBegin(48);
while (st.hasMoreTokens()) { 
edu.hkust.clap.monitor.Monitor.loopInc(48);
{
                String token = st.nextToken();
                if (fileSb.length() != 0) {
                    fileSb.append(token);
                } else if (token.equals(" ")) {
                    continue;
                } else if (token.charAt(0) == '-') {
                    if (token.length() > 1) {
                        optionsSb.append(token.substring(1));
                    }
                } else {
                    fileSb.append(token);
                }
            }} 
edu.hkust.clap.monitor.Monitor.loopEnd(48);

            if (fileSb.length() != 0) {
                file = fileSb.toString();
            }
            options = optionsSb.toString();
        }
        int slashIndex = file.lastIndexOf('/');
        if (slashIndex == -1) {
            if (containsPattern(file)) {
                pattern = file;
                file = "./";
            }
        } else if (slashIndex != (file.length() - 1)) {
            String after = file.substring(slashIndex + 1);
            if (containsPattern(after)) {
                pattern = file.substring(slashIndex + 1);
                file = file.substring(0, slashIndex + 1);
            }
            if (containsPattern(file)) {
                throw new IllegalArgumentException("Directory path can not contain regular expression");
            }
        }
        if ("*".equals(pattern) || "".equals(pattern)) {
            pattern = null;
        }
        return new ListArgument(file, pattern, options.toCharArray());
    }

    private static boolean containsPattern(String file) {
        return file.indexOf('*') > -1 || file.indexOf('?') > -1 || file.indexOf('[') > -1;
    }
}
