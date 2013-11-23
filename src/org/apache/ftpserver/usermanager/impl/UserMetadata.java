package org.apache.ftpserver.usermanager.impl;

import java.net.InetAddress;
import java.security.cert.Certificate;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * User metadata used during authentication
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class UserMetadata {

    private Certificate[] certificateChain;

    private InetAddress inetAddress;

    /**
     * Retrive the certificate chain used for an SSL connection.
     * 
     * @return The certificate chain, can be null if no peer certificate is
     *         available (e.g. SSL not used)
     */
    public Certificate[] getCertificateChain() {
        if (certificateChain != null) {
            return certificateChain.clone();
        } else {
            return null;
        }
    }

    /**
     * Set the certificate chain
     * 
     * @param certificateChain
     *            The certificate chain to set
     */
    public void setCertificateChain(final Certificate[] certificateChain) {
        if (certificateChain != null) {
            this.certificateChain = certificateChain.clone();
        } else {
            this.certificateChain = null;
        }
    }

    /**
     * Retrive the remote IP adress of the client
     * 
     * @return The client IP adress
     */
    public InetAddress getInetAddress() {
        return inetAddress;
    }

    /**
     * Set the remote IP adress of the client
     * 
     * @param inetAddress
     *            The client IP adress
     */
    public void setInetAddress(final InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }
}
