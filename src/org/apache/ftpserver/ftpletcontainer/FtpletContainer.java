package org.apache.ftpserver.ftpletcontainer;

import java.util.Map;
import org.apache.ftpserver.ftplet.Ftplet;

/**
 * Interface describing an Ftplet container. Ftplet containers extend the
 * {@link Ftplet} interface and forward any events to the Ftplets hosted by the
 * container.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface FtpletContainer extends Ftplet {

    /**
     * Retrive the {@link Ftplet} identified by the name (as provided in the
     * {@link #addFtplet(String, Ftplet)} method.
     * 
     * @param name
     *            The name of the Ftplet to retrive
     * @return The Ftplet if found, or null if the name is unknown to the
     *         container.
     */
    Ftplet getFtplet(String name);

    /**
     * Retrive all Ftplets registered with this container
     * 
     * @return A map of all Ftplets with their name as the key
     */
    Map<String, Ftplet> getFtplets();
}
