package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.ftplet.AuthorizationRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Request for getting the maximum allowed transfer rates for a user
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class TransferRateRequest implements AuthorizationRequest {

    private int maxDownloadRate = 0;

    private int maxUploadRate = 0;

    /**
     * @return the maxDownloadRate
     */
    public int getMaxDownloadRate() {
        return maxDownloadRate;
    }

    /**
     * @param maxDownloadRate
     *            the maxDownloadRate to set
     */
    public void setMaxDownloadRate(int maxDownloadRate) {
        this.maxDownloadRate = maxDownloadRate;
    }

    /**
     * @return the maxUploadRate
     */
    public int getMaxUploadRate() {
        return maxUploadRate;
    }

    /**
     * @param maxUploadRate
     *            the maxUploadRate to set
     */
    public void setMaxUploadRate(int maxUploadRate) {
        this.maxUploadRate = maxUploadRate;
    }
}
