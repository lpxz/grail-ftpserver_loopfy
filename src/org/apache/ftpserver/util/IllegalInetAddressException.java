package org.apache.ftpserver.util;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Thrown if the provided string representation does not match a valid IP
 * address
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class IllegalInetAddressException extends IllegalArgumentException {

    private static final long serialVersionUID = -7771719692741419933L;

    public IllegalInetAddressException() {
        super();
    }

    public IllegalInetAddressException(String s) {
        super(s);
    }
}
