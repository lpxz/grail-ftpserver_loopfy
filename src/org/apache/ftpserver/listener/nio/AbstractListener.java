package org.apache.ftpserver.listener.nio;

import java.net.InetAddress;
import java.util.List;
import org.apache.ftpserver.DataConnectionConfiguration;
import org.apache.ftpserver.ipfilter.DefaultIpFilter;
import org.apache.ftpserver.ipfilter.IpFilter;
import org.apache.ftpserver.ipfilter.IpFilterType;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.mina.filter.firewall.Subnet;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Common base class for listener implementations
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public abstract class AbstractListener implements Listener {

    private String serverAddress;

    private int port = 21;

    private SslConfiguration ssl;

    private boolean implicitSsl = false;

    private int idleTimeout;

    private List<InetAddress> blockedAddresses;

    private List<Subnet> blockedSubnets;

    private IpFilter ipFilter = null;

    private DataConnectionConfiguration dataConnectionConfig;

    /**
     * @deprecated Use the constructor with IpFilter instead. 
     * Constructor for internal use, do not use directly. Instead use {@link ListenerFactory}
     */
    @Deprecated
    public AbstractListener(String serverAddress, int port, boolean implicitSsl, SslConfiguration sslConfiguration, DataConnectionConfiguration dataConnectionConfig, int idleTimeout, List<InetAddress> blockedAddresses, List<Subnet> blockedSubnets) {
        this(serverAddress, port, implicitSsl, sslConfiguration, dataConnectionConfig, idleTimeout, createBlackListFilter(blockedAddresses, blockedSubnets));
        this.blockedAddresses = blockedAddresses;
        this.blockedSubnets = blockedSubnets;
    }

    /**
     * Constructor for internal use, do not use directly. Instead use {@link ListenerFactory}
     */
    public AbstractListener(String serverAddress, int port, boolean implicitSsl, SslConfiguration sslConfiguration, DataConnectionConfiguration dataConnectionConfig, int idleTimeout, IpFilter ipFilter) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.implicitSsl = implicitSsl;
        this.dataConnectionConfig = dataConnectionConfig;
        this.ssl = sslConfiguration;
        this.idleTimeout = idleTimeout;
        this.ipFilter = ipFilter;
    }

    /**
     * Creates an IpFilter that blacklists the given IP addresses and/or Subnets. 
     * @param blockedAddresses the addresses to block
     * @param blockedSubnets the subnets to block
     * @return an IpFilter that blacklists the given IP addresses and/or Subnets.
     */
    private static IpFilter createBlackListFilter(List<InetAddress> blockedAddresses, List<Subnet> blockedSubnets) {
        if (blockedAddresses == null && blockedSubnets == null) {
            return null;
        }
        DefaultIpFilter ipFilter = new DefaultIpFilter(IpFilterType.DENY);
        if (blockedSubnets != null) {
            ipFilter.addAll(blockedSubnets);
        }
        if (blockedAddresses != null) {
            for (InetAddress address : blockedAddresses) {
                ipFilter.add(new Subnet(address, 32));
            }
        }
        return ipFilter;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isImplicitSsl() {
        return implicitSsl;
    }

    /**
     * {@inheritDoc}
     */
    public int getPort() {
        return port;
    }

    /**
     * Used internally to update the port after binding
     * @param port
     */
    protected void setPort(int port) {
        this.port = port;
    }

    /**
     * {@inheritDoc}
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * {@inheritDoc}
     */
    public SslConfiguration getSslConfiguration() {
        return ssl;
    }

    /**
     * {@inheritDoc}
     */
    public DataConnectionConfiguration getDataConnectionConfiguration() {
        return dataConnectionConfig;
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
     * Retrives the {@link InetAddress} for which this listener blocks
     * connections
     * 
     * @return The list of {@link InetAddress}es
     */
    public List<InetAddress> getBlockedAddresses() {
        return blockedAddresses;
    }

    /**
     * Retrieves the {@link Subnet}s for this listener blocks connections
     * 
     * @return The list of {@link Subnet}s
     */
    public List<Subnet> getBlockedSubnets() {
        return blockedSubnets;
    }

    public IpFilter getIpFilter() {
        return ipFilter;
    }
}
