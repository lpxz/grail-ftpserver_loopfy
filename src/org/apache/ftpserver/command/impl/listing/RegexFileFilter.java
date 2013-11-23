package org.apache.ftpserver.command.impl.listing;

import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.util.RegularExpr;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Selects files which short name matches a regular expression
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class RegexFileFilter implements FileFilter {

    private RegularExpr regex;

    private FileFilter wrappedFilter;

    /**
     * Constructor with a regular expression
     * 
     * @param regex
     *            The regular expression to select by
     */
    public RegexFileFilter(String regex) {
        this.regex = new RegularExpr(regex);
    }

    /**
     * Constructor with a wrapped filter, allows for chaining filters
     * 
     * @param regex
     *            The regular expression to select by
     * @param wrappedFilter
     *            The {@link FileFilter} to wrap
     */
    public RegexFileFilter(String regex, FileFilter wrappedFilter) {
        this(regex);
        this.wrappedFilter = wrappedFilter;
    }

    /**
     * @see FileFilter#accept(FtpFile)
     */
    public boolean accept(FtpFile file) {
        if (wrappedFilter != null && !wrappedFilter.accept(file)) {
            return false;
        }
        return regex.isMatch(file.getName());
    }
}
