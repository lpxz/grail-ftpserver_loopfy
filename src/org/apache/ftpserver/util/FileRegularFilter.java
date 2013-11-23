package org.apache.ftpserver.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * This is regular expression filename filter.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class FileRegularFilter implements FilenameFilter {

    private RegularExpr regularExpr = null;

    /**
     * Constructor.
     * 
     * @param pattern
     *            regular expression
     */
    public FileRegularFilter(String pattern) {
        if ((pattern == null) || pattern.equals("") || pattern.equals("*")) {
            regularExpr = null;
        } else {
            regularExpr = new RegularExpr(pattern);
        }
    }

    /**
     * Tests if a specified file should be included in a file list.
     * 
     * @param dir
     *            - the directory in which the file was found
     * @param name
     *            - the name of the file.
     */
    public boolean accept(File dir, String name) {
        if (regularExpr == null) {
            return true;
        }
        return regularExpr.isMatch(name);
    }
}
