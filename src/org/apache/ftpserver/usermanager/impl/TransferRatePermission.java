package org.apache.ftpserver.usermanager.impl;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.AuthorizationRequest;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * The max upload rate permission
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class TransferRatePermission implements Authority {

    private int maxDownloadRate;

    private int maxUploadRate;

    public TransferRatePermission(int maxDownloadRate, int maxUploadRate) {
        this.maxDownloadRate = maxDownloadRate;
        this.maxUploadRate = maxUploadRate;
    }

    /**
     * @see Authority#authorize(AuthorizationRequest)
     */
    public AuthorizationRequest authorize(AuthorizationRequest request) {
        if (request instanceof TransferRateRequest) {
            TransferRateRequest transferRateRequest = (TransferRateRequest) request;
            transferRateRequest.setMaxDownloadRate(maxDownloadRate);
            transferRateRequest.setMaxUploadRate(maxUploadRate);
            return transferRateRequest;
        } else {
            return null;
        }
    }

    /**
     * @see Authority#canAuthorize(AuthorizationRequest)
     */
    public boolean canAuthorize(AuthorizationRequest request) {
        return request instanceof TransferRateRequest;
    }
}
