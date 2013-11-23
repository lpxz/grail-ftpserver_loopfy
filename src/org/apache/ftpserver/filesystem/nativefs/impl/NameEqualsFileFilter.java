package org.apache.ftpserver.filesystem.nativefs.impl;

import java.io.File;
import java.io.FileFilter;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * FileFilter used for simple file name matching
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class NameEqualsFileFilter implements FileFilter {

    private String nameToMatch;

    private boolean caseInsensitive = false;

    /**
     * Constructor
     * 
     * @param nameToMatch
     *            The exact file name to match
     * @param caseInsensitive
     *            Wether that match should be case insensitive
     */
    public NameEqualsFileFilter(final String nameToMatch, final boolean caseInsensitive) {
        this.nameToMatch = nameToMatch;
        this.caseInsensitive = caseInsensitive;
    }

    public boolean accept(final File file) {
        if (caseInsensitive) {
            return file.getName().equalsIgnoreCase(nameToMatch);
        } else {
            return file.getName().equals(nameToMatch);
        }
    }
}
