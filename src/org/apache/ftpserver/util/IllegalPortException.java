package org.apache.ftpserver.util;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Thrown if the provided string representation does not match a valid IP port
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class IllegalPortException extends IllegalArgumentException {

    private static final long serialVersionUID = -7771719692741419931L;

    public IllegalPortException() {
        super();
    }

    public IllegalPortException(String s) {
        super(s);
    }
}
