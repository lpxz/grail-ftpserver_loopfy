package org.apache.ftpserver.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.ftpserver.DataConnectionConfiguration;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServerConfigurationException;
import org.apache.ftpserver.ipfilter.IpFilter;
import org.apache.ftpserver.listener.nio.NioListener;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.mina.filter.firewall.Subnet;

/**
 * Factory for listeners. Listeners themselves are immutable and must be 
 * created using this factory.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ListenerFactory {

    private String serverAddress;

    private int port = 21;

    private SslConfiguration ssl;

    private boolean implicitSsl = false;

    private DataConnectionConfiguration dataConnectionConfig = new DataConnectionConfigurationFactory().createDataConnectionConfiguration();

    private int idleTimeout = 300;

    private List<InetAddress> blockedAddresses;

    private List<Subnet> blockedSubnets;

    /**
     * The IP filter
     */
    private IpFilter ipFilter = null;

    /**
     * Default constructor
     */
    public ListenerFactory() {
    }

    /**
     * Copy constructor, will copy properties from the provided listener.
     * @param listener The listener which properties will be used for this factory
     */
    public ListenerFactory(Listener listener) {
        serverAddress = listener.getServerAddress();
        port = listener.getPort();
        ssl = listener.getSslConfiguration();
        implicitSsl = listener.isImplicitSsl();
        dataConnectionConfig = listener.getDataConnectionConfiguration();
        idleTimeout = listener.getIdleTimeout();
        blockedAddresses = listener.getBlockedAddresses();
        blockedSubnets = listener.getBlockedSubnets();
        this.ipFilter = listener.getIpFilter();
    }

    /**
     * Create a listener based on the settings of this factory. The listener is immutable.
     * @return The created listener
     */
    public Listener createListener() {
        try {
            InetAddress.getByName(serverAddress);
        } catch (UnknownHostException e) {
            throw new FtpServerConfigurationException("Unknown host", e);
        }
        if (ipFilter != null) {
            if (blockedAddresses != null || blockedSubnets != null) {
                throw new IllegalStateException("Usage of IPFilter in combination with blockedAddesses/subnets is not supported. ");
            }
        }
        if (blockedAddresses != null || blockedSubnets != null) {
            return new NioListener(serverAddress, port, implicitSsl, ssl, dataConnectionConfig, idleTimeout, blockedAddresses, blockedSubnets);
        } else {
            return new NioListener(serverAddress, port, implicitSsl, ssl, dataConnectionConfig, idleTimeout, ipFilter);
        }
    }

    /**
     * Is listeners created by this factory in SSL mode automatically or must the client explicitly
     * request to use SSL
     * 
     * @return true is listeners created by this factory is automatically in SSL mode, false
     *         otherwise
     */
    public boolean isImplicitSsl() {
        return implicitSsl;
    }

    /**
     * Should listeners created by this factory be in SSL mode automatically or must the client
     * explicitly request to use SSL
     * 
     * @param implicitSsl
     *            true is listeners created by this factory should automatically be in SSL mode,
     *            false otherwise
     */
    public void setImplicitSsl(boolean implicitSsl) {
        this.implicitSsl = implicitSsl;
    }

    /**
     * Get the port on which listeners created by this factory is waiting for requests. 
     * 
     * @return The port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port on which listeners created by this factory will accept requests. Or set to 0
     * (zero) is the port should be automatically assigned
     * 
     * @param port
     *            The port to use.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the {@link InetAddress} used for binding the local socket. Defaults
     * to null, that is, the server binds to all available network interfaces
     * 
     * @return The local socket {@link InetAddress}, if set
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * Set the {@link InetAddress} used for binding the local socket. Defaults
     * to null, that is, the server binds to all available network interfaces
     * 
     * @param serverAddress
     *            The local socket {@link InetAddress}
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * Get the {@link SslConfiguration} used for listeners created by this factory
     * 
     * @return The {@link SslConfiguration}
     */
    public SslConfiguration getSslConfiguration() {
        return ssl;
    }

    /**
     * Set the {@link SslConfiguration} to use by listeners created by this factory
     * @param ssl The {@link SslConfiguration}
     */
    public void setSslConfiguration(SslConfiguration ssl) {
        this.ssl = ssl;
    }

    /**
     * Get configuration for data connections made within listeners created by this factory
     * 
     * @return The data connection configuration
     */
    public DataConnectionConfiguration getDataConnectionConfiguration() {
        return dataConnectionConfig;
    }

    /**
     * Set configuration for data connections made within listeners created by this factory
     * 
     * @param dataConnectionConfig
     *            The data connection configuration
     */
    public void setDataConnectionConfiguration(DataConnectionConfiguration dataConnectionConfig) {
        this.dataConnectionConfig = dataConnectionConfig;
    }

    /**
     * Get the number of seconds during which no network activity 
     * is allowed before a session is closed due to inactivity.  
     * @return The idle time out
     */
    public int getIdleTimeout() {
        return idleTimeout;
    }

    /**
     * Set the number of seconds during which no network activity 
     * is allowed before a session is closed due to inactivity.  
     *
     * @param idleTimeout The idle timeout in seconds
     */
    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    /**
     * @deprecated Replaced by the IpFilter.    
     * Retrieves the {@link InetAddress} for which listeners created by this factory blocks
     * connections
     * 
     * @return The list of {@link InetAddress}es
     */
    @Deprecated
    public List<InetAddress> getBlockedAddresses() {
        return blockedAddresses;
    }

    /**
     * @deprecated Replaced by the IpFilter.    
     * Sets the {@link InetAddress} that listeners created by this factory will block from
     * connecting
     * 
     * @param blockedAddresses
     *            The list of {@link InetAddress}es
     */
    @Deprecated
    public void setBlockedAddresses(List<InetAddress> blockedAddresses) {
        this.blockedAddresses = blockedAddresses;
    }

    /**
     * @deprecated Replaced by the IpFilter.    
     * Retrives the {@link Subnet}s for which listeners created by this factory blocks connections
     * 
     * @return The list of {@link Subnet}s
     */
    @Deprecated
    public List<Subnet> getBlockedSubnets() {
        return blockedSubnets;
    }

    /**
     * @deprecated Replaced by the IpFilter.    
     * Sets the {@link Subnet}s that listeners created by this factory will block from connecting
     * @param blockedSubnets 
     *  The list of {@link Subnet}s
     * @param blockedAddresses
     */
    @Deprecated
    public void setBlockedSubnets(List<Subnet> blockedSubnets) {
        this.blockedSubnets = blockedSubnets;
    }

    /**
	 * Returns the currently configured IP filter, if any.
	 * 
	 * @return the currently configured IP filter, if any. Returns
	 *         <code>null</code>, if no IP filter is configured.
	 */
    public IpFilter getIpFilter() {
        return ipFilter;
    }

    /**
	 * Sets the IP filter to the given filter.
	 * 
	 * @param ipFilter
	 *            the IP filter.
	 */
    public void setIpFilter(IpFilter ipFilter) {
        this.ipFilter = ipFilter;
    }
}
