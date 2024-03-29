package org.apache.ftpserver.ftpletcontainer;

import java.io.IOException;
import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletContext;
import org.apache.ftpserver.ftplet.FtpletResult;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class MockFtplet extends DefaultFtplet {

    protected static MockFtpletCallback callback = new MockFtpletCallback();

    public FtpletContext context;

    public boolean destroyed = false;

    public void destroy() {
        destroyed = true;
        callback.destroy();
    }

    public void init(FtpletContext ftpletContext) throws FtpException {
        this.context = ftpletContext;
        callback.init(ftpletContext);
    }

    public FtpletResult onAppendEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onAppendEnd(session, request);
    }

    public FtpletResult onAppendStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onAppendStart(session, request);
    }

    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
        return callback.onConnect(session);
    }

    public FtpletResult onDeleteEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onDeleteEnd(session, request);
    }

    public FtpletResult onDeleteStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onDeleteStart(session, request);
    }

    public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
        return callback.onDisconnect(session);
    }

    public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onDownloadEnd(session, request);
    }

    public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onDownloadStart(session, request);
    }

    public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onLogin(session, request);
    }

    public FtpletResult onMkdirEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onMkdirEnd(session, request);
    }

    public FtpletResult onMkdirStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onMkdirStart(session, request);
    }

    public FtpletResult onRenameEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onRenameEnd(session, request);
    }

    public FtpletResult onRenameStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onRenameStart(session, request);
    }

    public FtpletResult onRmdirEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onRmdirEnd(session, request);
    }

    public FtpletResult onRmdirStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onRmdirStart(session, request);
    }

    public FtpletResult onSite(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onSite(session, request);
    }

    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onUploadEnd(session, request);
    }

    public FtpletResult onUploadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onUploadStart(session, request);
    }

    public FtpletResult onUploadUniqueEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onUploadUniqueEnd(session, request);
    }

    public FtpletResult onUploadUniqueStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return callback.onUploadUniqueStart(session, request);
    }
}
