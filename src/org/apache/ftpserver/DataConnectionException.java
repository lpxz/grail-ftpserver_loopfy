package org.apache.ftpserver;

import org.apache.ftpserver.ftplet.FtpException;

/**
 * Thrown if a data connection can not be established
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> 
 */
public class DataConnectionException extends FtpException {

    private static final long serialVersionUID = -1328383839917648987L;

    /**
     * Default constructor.
     */
    public DataConnectionException() {
        super();
    }

    /**
     * Constructs a <code>DataConnectionException</code> object with a message.
     * 
     * @param msg
     *            a description of the exception
     */
    public DataConnectionException(final String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>DataConnectionException</code> object with a
     * <code>Throwable</code> cause.
     * 
     * @param th
     *            the original cause
     */
    public DataConnectionException(final Throwable th) {
        super(th);
    }

    /**
     * Constructs a <code>DataConnectionException</code> object with a
     * <code>Throwable</code> cause.
     * @param msg a description of the exception
     * 
     * @param th
     *            the original cause
     */
    public DataConnectionException(final String msg, final Throwable th) {
        super(msg, th);
    }
}
