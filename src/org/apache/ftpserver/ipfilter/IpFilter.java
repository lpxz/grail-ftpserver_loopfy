package org.apache.ftpserver.ipfilter;

import java.net.InetAddress;

/**
 * The interface for filtering connections based on the client's IP address.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 */
public interface IpFilter {

    /**
	 * Tells whether or not the given IP address is accepted by this filter.
	 * 
	 * @param address
	 *            the IP address to check
	 * @return <code>true</code>, if the given IP address is accepted by this
	 *         filter; <code>false</code>, otherwise.
	 */
    public boolean accept(InetAddress address);
}
