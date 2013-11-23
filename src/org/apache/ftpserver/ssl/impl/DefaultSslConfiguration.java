package org.apache.ftpserver.ssl.impl;

import java.security.GeneralSecurityException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import org.apache.ftpserver.ssl.ClientAuth;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.util.ClassUtils;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Used to configure the SSL settings for the control channel or the data
 * channel.
 * 
 * <strong><strong>Internal class, do not use directly.</strong></strong>
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class DefaultSslConfiguration implements SslConfiguration {

    private KeyManagerFactory keyManagerFactory;

    private TrustManagerFactory trustManagerFactory;

    private String sslProtocol = "TLS";

    private ClientAuth clientAuth = ClientAuth.NONE;

    private String keyAlias;

    private String[] enabledCipherSuites;

    /**
     * Internal constructor, do not use directly. Instead, use {@link SslConfigurationFactory}
     */
    public DefaultSslConfiguration(KeyManagerFactory keyManagerFactory, TrustManagerFactory trustManagerFactory, ClientAuth clientAuthReqd, String sslProtocol, String[] enabledCipherSuites, String keyAlias) {
        super();
        this.clientAuth = clientAuthReqd;
        this.enabledCipherSuites = enabledCipherSuites;
        this.keyAlias = keyAlias;
        this.keyManagerFactory = keyManagerFactory;
        this.sslProtocol = sslProtocol;
        this.trustManagerFactory = trustManagerFactory;
    }

    /**
     * @see SslConfiguration#getSSLContext(String)
     */
    public SSLContext getSSLContext(String protocol) throws GeneralSecurityException {
        if (protocol == null) {
            protocol = sslProtocol;
        }
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        edu.hkust.clap.monitor.Monitor.loopBegin(40);
for (int i = 0; i < keyManagers.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(40);
{
            if (ClassUtils.extendsClass(keyManagers[i].getClass(), "javax.net.ssl.X509ExtendedKeyManager")) {
                keyManagers[i] = new ExtendedAliasKeyManager(keyManagers[i], keyAlias);
            } else if (keyManagers[i] instanceof X509KeyManager) {
                keyManagers[i] = new AliasKeyManager(keyManagers[i], keyAlias);
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(40);

        SSLContext ctx = SSLContext.getInstance(protocol);
        ctx.init(keyManagers, trustManagerFactory.getTrustManagers(), null);
        return ctx;
    }

    /**
     * @see SslConfiguration#getClientAuth()
     */
    public ClientAuth getClientAuth() {
        return clientAuth;
    }

    /**
     * @see SslConfiguration#getSSLContext()
     */
    public SSLContext getSSLContext() throws GeneralSecurityException {
        return getSSLContext(sslProtocol);
    }

    /**
     * @see SslConfiguration#getEnabledCipherSuites()
     */
    public String[] getEnabledCipherSuites() {
        if (enabledCipherSuites != null) {
            return enabledCipherSuites.clone();
        } else {
            return null;
        }
    }
}
