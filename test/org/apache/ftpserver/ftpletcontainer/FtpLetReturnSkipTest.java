package org.apache.ftpserver.ftpletcontainer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.clienttests.ClientTestTemplate;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.apache.ftpserver.test.TestUtil;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class FtpLetReturnSkipTest extends ClientTestTemplate {

    private static final byte[] TESTDATA = "TESTDATA".getBytes();

    private static final byte[] DOUBLE_TESTDATA = "TESTDATATESTDATA".getBytes();

    private static final File TEST_FILE1 = new File(ROOT_DIR, "test1.txt");

    private static final File TEST_FILE2 = new File(ROOT_DIR, "test2.txt");

    private static final File TEST_DIR1 = new File(ROOT_DIR, "dir1");

    ;

    protected FtpletResult mockReturnValue = FtpletResult.SKIP;

    protected void setUp() throws Exception {
        MockFtplet.callback = new MockFtpletCallback();
        initDirs();
        initServer();
        connectClient();
    }

    protected FtpServerFactory createServer() throws Exception {
        FtpServerFactory server = super.createServer();
        Map<String, Ftplet> ftplets = new LinkedHashMap<String, Ftplet>();
        ftplets.put("f1", new MockFtplet());
        server.setFtplets(ftplets);
        return server;
    }

    public void testLogin() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_530_NOT_LOGGED_IN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        assertTrue(client.login(ADMIN_USERNAME, ADMIN_PASSWORD));
    }

    public void testExceptionDuringDeleteStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onDeleteStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(client.deleteFile(TEST_FILE1.getName()));
        assertTrue(TEST_FILE1.exists());
    }

    public void testExceptionDuringDeleteEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onDeleteEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(client.deleteFile(TEST_FILE1.getName()));
        assertFalse(TEST_FILE1.exists());
    }

    public void testExceptionDuringMkdirStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onMkdirStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(client.makeDirectory(TEST_DIR1.getName()));
        assertFalse(TEST_DIR1.exists());
    }

    public void testExceptionDuringMkdirEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onMkdirEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(client.makeDirectory(TEST_DIR1.getName()));
        assertTrue(TEST_DIR1.exists());
    }

    public void testExceptionDuringRmdirStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onRmdirStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        TEST_DIR1.mkdirs();
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(client.removeDirectory(TEST_DIR1.getName()));
        assertTrue(TEST_DIR1.exists());
    }

    public void testExceptionDuringRmdirEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onRmdirEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        TEST_DIR1.mkdirs();
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(client.removeDirectory(TEST_DIR1.getName()));
        assertFalse(TEST_DIR1.exists());
    }

    public void testExceptionDuringSite() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onSite(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_553_REQUESTED_ACTION_NOT_TAKEN_FILE_NAME_NOT_ALLOWED, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        client.site("HELP");
    }

    public void testExceptionDuringRenameStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onRenameStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_553_REQUESTED_ACTION_NOT_TAKEN_FILE_NAME_NOT_ALLOWED, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(client.rename(TEST_FILE1.getName(), TEST_FILE2.getName()));
        assertTrue(TEST_FILE1.exists());
        assertFalse(TEST_FILE2.exists());
    }

    public void testExceptionDuringRenameEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onRenameEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(client.rename(TEST_FILE1.getName(), TEST_FILE2.getName()));
        assertFalse(TEST_FILE1.exists());
        assertTrue(TEST_FILE2.exists());
    }

    public void testExceptionDuringDownloadStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        client.retrieveFileStream(TEST_FILE1.getName());
        assertTrue(FTPReply.isNegativePermanent(client.getReplyCode()));
    }

    public void testExceptionDuringDownloadEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        assertTrue(client.retrieveFile(TEST_FILE1.getName(), baos));
        TestUtil.assertArraysEqual(TESTDATA, baos.toByteArray());
    }

    public void testExceptionDuringAppendStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onAppendStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(client.appendFile(TEST_FILE1.getName(), new ByteArrayInputStream(TESTDATA)));
        TestUtil.assertFileEqual(TESTDATA, TEST_FILE1);
    }

    public void testExceptionDuringAppendEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onAppendEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        TestUtil.writeDataToFile(TEST_FILE1, TESTDATA);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(client.appendFile(TEST_FILE1.getName(), new ByteArrayInputStream(TESTDATA)));
        TestUtil.assertFileEqual(DOUBLE_TESTDATA, TEST_FILE1);
    }

    public void testExceptionDuringUploadStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onUploadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(client.storeFile(TEST_FILE1.getName(), new ByteArrayInputStream(TESTDATA)));
        assertFalse(TEST_FILE1.exists());
    }

    public void testExceptionDuringUploadEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(client.storeFile(TEST_FILE1.getName(), new ByteArrayInputStream(TESTDATA)));
        TestUtil.assertFileEqual(TESTDATA, TEST_FILE1);
    }

    public void testExceptionDuringUploadUniqueStart() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onUploadUniqueStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
                session.write(new DefaultFtpReply(FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN, "foo"));
                throwException();
                return mockReturnValue;
            }
        };
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertFalse(client.storeUniqueFile(TEST_FILE1.getName(), new ByteArrayInputStream(TESTDATA)));
        assertEquals(ROOT_DIR.listFiles().length, 0);
    }

    public void testExceptionDuringUploadUniqueEnd() throws Exception {
        MockFtplet.callback = new MockFtpletCallback() {

            public FtpletResult onUploadUniqueEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
                throwException();
                return mockReturnValue;
            }
        };
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertTrue(client.storeUniqueFile(new ByteArrayInputStream(TESTDATA)));
        TestUtil.assertFileEqual(TESTDATA, ROOT_DIR.listFiles()[0]);
    }

    protected void throwException() throws FtpException, IOException {
    }
}
