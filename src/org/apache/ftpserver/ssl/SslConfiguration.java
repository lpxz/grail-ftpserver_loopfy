package org.apache.ftpserver.ssl;

import java.security.GeneralSecurityException;
import javax.net.ssl.SSLContext;

/**
 * SSL configuration
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface SslConfiguration {

    /**
     * Return the SSL context for this configuration
     * 
     * @return The {@link SSLContext}
     * @throws GeneralSecurityException
     */
    SSLContext getSSLContext() throws GeneralSecurityException;

    /**
     * Return the SSL context for this configuration given the specified
     * protocol
     * 
     * @param protocol
     *            The protocol, SSL or TLS must be supported
     * @return The {@link SSLContext}
     * @throws GeneralSecurityException
     */
    SSLContext getSSLContext(String protocol) throws GeneralSecurityException;

    /**
     * Returns the cipher suites that should be enabled for this connection.
     * Must return null if the default (as decided by the JVM) cipher suites
     * should be used.
     * 
     * @return An array of cipher suites, or null.
     */
    String[] getEnabledCipherSuites();

    /**
     * Return the required client authentication setting
     * 
     * @return {@link ClientAuth#NEED} if client authentication is required,
     *         {@link ClientAuth#WANT} is client authentication is wanted or
     *         {@link ClientAuth#NONE} if no client authentication is the be
     *         performed
     */
    ClientAuth getClientAuth();
}
