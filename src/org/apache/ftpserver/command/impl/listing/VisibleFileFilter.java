package org.apache.ftpserver.command.impl.listing;

import org.apache.ftpserver.ftplet.FtpFile;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Selects files that are visible
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class VisibleFileFilter implements FileFilter {

    private FileFilter wrappedFilter;

    /**
     * Default constructor
     */
    public VisibleFileFilter() {
    }

    /**
     * Constructor with a wrapped filter, allows for chaining filters
     * 
     * @param wrappedFilter
     *            The {@link FileFilter} to wrap
     */
    public VisibleFileFilter(FileFilter wrappedFilter) {
        this.wrappedFilter = wrappedFilter;
    }

    /**
     * @see FileFilter#accept(FtpFile)
     */
    public boolean accept(FtpFile file) {
        if (wrappedFilter != null && !wrappedFilter.accept(file)) {
            return false;
        }
        return !file.isHidden();
    }
}
