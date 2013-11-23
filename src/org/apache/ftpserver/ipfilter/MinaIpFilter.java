package org.apache.ftpserver.ipfilter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * An implementation of Mina Filter to filter clients based on the originating
 * IP address.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 */
public class MinaIpFilter extends IoFilterAdapter {

    /**
	 * The actual <code>IpFilter</code> used by this filter.
	 */
    private IpFilter filter = null;

    /**
	 * Creates a new instance of <code>MinaIpFilter</code>.
	 * 
	 * @param filter
	 *            the filter
	 */
    public MinaIpFilter(IpFilter filter) {
        this.filter = filter;
    }

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) {
        SocketAddress remoteAddress = session.getRemoteAddress();
        if (remoteAddress instanceof InetSocketAddress) {
            InetAddress ipAddress = ((InetSocketAddress) remoteAddress).getAddress();
            if (!filter.accept(ipAddress)) {
                session.close(true);
            } else {
                nextFilter.sessionCreated(session);
            }
        }
    }
}
