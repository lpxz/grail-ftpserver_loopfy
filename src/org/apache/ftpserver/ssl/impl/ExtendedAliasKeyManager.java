package org.apache.ftpserver.ssl.impl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * X509KeyManager which allows selection of a specific keypair and certificate
 * chain (identified by their keystore alias name) to be used by the server to
 * authenticate itself to SSL clients.
 * 
 * Based of org.apache.tomcat.util.net.jsse.JSSEKeyManager.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public final class ExtendedAliasKeyManager extends X509ExtendedKeyManager {

    private X509ExtendedKeyManager delegate;

    private String serverKeyAlias;

    /**
     * Constructor.
     * 
     * @param mgr
     *            The X509KeyManager used as a delegate
     * @param keyStore
     * @param serverKeyAlias
     *            The alias name of the server's keypair and supporting
     *            certificate chain
     * @param keyAlias
     */
    public ExtendedAliasKeyManager(KeyManager mgr, String keyAlias) {
        this.delegate = (X509ExtendedKeyManager) mgr;
        this.serverKeyAlias = keyAlias;
    }

    /**
     * Choose an alias to authenticate the client side of a secure socket, given
     * the public key type and the list of certificate issuer authorities
     * recognized by the peer (if any).
     * 
     * @param keyType
     *            The key algorithm type name(s), ordered with the
     *            most-preferred key type first
     * @param issuers
     *            The list of acceptable CA issuer subject names, or null if it
     *            does not matter which issuers are used
     * @param socket
     *            The socket to be used for this connection. This parameter can
     *            be null, in which case this method will return the most
     *            generic alias to use
     * 
     * @return The alias name for the desired key, or null if there are no
     *         matches
     */
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return delegate.chooseClientAlias(keyType, issuers, socket);
    }

    /**
     * Returns this key manager's server key alias that was provided in the
     * constructor if matching the key type.
     * 
     * @param keyType
     *            The key algorithm type name
     * @param issuers
     *            The list of acceptable CA issuer subject names, or null if it
     *            does not matter which issuers are used (ignored)
     * @param socket
     *            The socket to be used for this connection. This parameter can
     *            be null, in which case this method will return the most
     *            generic alias to use (ignored)
     * 
     * @return Alias name for the desired key
     */
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        if (serverKeyAlias != null) {
            PrivateKey key = delegate.getPrivateKey(serverKeyAlias);
            if (key != null) {
                if (key.getAlgorithm().equals(keyType)) {
                    return serverKeyAlias;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return delegate.chooseServerAlias(keyType, issuers, socket);
        }
    }

    /**
     * Returns the certificate chain associated with the given alias.
     * 
     * @param alias
     *            The alias name
     * 
     * @return Certificate chain (ordered with the user's certificate first and
     *         the root certificate authority last), or null if the alias can't
     *         be found
     */
    public X509Certificate[] getCertificateChain(String alias) {
        return delegate.getCertificateChain(alias);
    }

    /**
     * Get the matching aliases for authenticating the client side of a secure
     * socket, given the public key type and the list of certificate issuer
     * authorities recognized by the peer (if any).
     * 
     * @param keyType
     *            The key algorithm type name
     * @param issuers
     *            The list of acceptable CA issuer subject names, or null if it
     *            does not matter which issuers are used
     * 
     * @return Array of the matching alias names, or null if there were no
     *         matches
     */
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return delegate.getClientAliases(keyType, issuers);
    }

    /**
     * Get the matching aliases for authenticating the server side of a secure
     * socket, given the public key type and the list of certificate issuer
     * authorities recognized by the peer (if any).
     * 
     * @param keyType
     *            The key algorithm type name
     * @param issuers
     *            The list of acceptable CA issuer subject names, or null if it
     *            does not matter which issuers are used
     * 
     * @return Array of the matching alias names, or null if there were no
     *         matches
     */
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return delegate.getServerAliases(keyType, issuers);
    }

    /**
     * Returns the key associated with the given alias.
     * 
     * @param alias
     *            The alias name
     * 
     * @return The requested key, or null if the alias can't be found
     */
    public PrivateKey getPrivateKey(String alias) {
        return delegate.getPrivateKey(alias);
    }

    /**
     * Choose an alias to authenticate the client side of a secure socket, given
     * the public key type and the list of certificate issuer authorities
     * recognized by the peer (if any).
     * 
     * @param keyType
     *            The key algorithm type name
     * @param issuers
     *            The list of acceptable CA issuer subject names, or null if it
     *            does not matter which issuers are used (ignored)
     * @param socket
     *            The socket to be used for this connection. This parameter can
     *            be null, in which case this method will return the most
     *            generic alias to use (ignored)
     * @return The alias name for the desired key, or null if there are no
     *         matches
     */
    public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
        return delegate.chooseEngineClientAlias(keyType, issuers, engine);
    }

    /**
     * Returns this key manager's server key alias that was provided in the
     * constructor if matching the key type.
     * 
     * @param keyType
     *            The key algorithm type name
     * @param issuers
     *            The list of acceptable CA issuer subject names, or null if it
     *            does not matter which issuers are used (ignored)
     * @param socket
     *            The socket to be used for this connection. This parameter can
     *            be null, in which case this method will return the most
     *            generic alias to use (ignored)
     * 
     * @return Alias name for the desired key
     */
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
        if (serverKeyAlias != null) {
            PrivateKey key = delegate.getPrivateKey(serverKeyAlias);
            if (key != null) {
                if (key.getAlgorithm().equals(keyType)) {
                    return serverKeyAlias;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return delegate.chooseEngineServerAlias(keyType, issuers, engine);
        }
    }
}
