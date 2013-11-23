package org.apache.ftpserver.clienttests;

import java.io.File;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.ftpserver.test.TestUtil;
import org.apache.ftpserver.util.DateUtils;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class ListTest extends ClientTestTemplate {

    private static final File TEST_FILE1 = new File(ROOT_DIR, "test1.txt");

    private static final File TEST_FILE2 = new File(ROOT_DIR, "test2.txt");

    private static final File TEST_DIR1 = new File(ROOT_DIR, "dir1");

    private static final File TEST_DIR2 = new File(ROOT_DIR, "dir2");

    private static final File TEST_FILE_IN_DIR1 = new File(TEST_DIR1, "test3.txt");

    private static final File TEST_DIR_IN_DIR1 = new File(TEST_DIR1, "dir3");

    private byte[] testData;

    protected void setUp() throws Exception {
        super.setUp();
        testData = "TESDATA".getBytes("UTF-8");
        FTPClientConfig config = new FTPClientConfig("UNIX");
        client.configure(config);
        client.login(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public void testListFilesInDir() throws Exception {
        TEST_DIR1.mkdirs();
        TEST_FILE_IN_DIR1.createNewFile();
        TEST_DIR_IN_DIR1.mkdirs();
        FTPFile[] files = client.listFiles(TEST_DIR1.getName());
        assertEquals(2, files.length);
        FTPFile file = getFile(files, TEST_FILE_IN_DIR1.getName());
        assertEquals(TEST_FILE_IN_DIR1.getName(), file.getName());
        assertEquals(0, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertTrue(file.isFile());
        assertFalse(file.isDirectory());
        file = getFile(files, TEST_DIR_IN_DIR1.getName());
        assertEquals(TEST_DIR_IN_DIR1.getName(), file.getName());
        assertEquals(0, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertFalse(file.isFile());
        assertTrue(file.isDirectory());
    }

    public void testListFilesInNonExistingDir() throws Exception {
        assertEquals(450, client.sendCommand("LIST", "nonexisting"));
    }

    public void testListFile() throws Exception {
        TEST_DIR1.mkdirs();
        TEST_FILE1.createNewFile();
        TEST_FILE2.createNewFile();
        FTPFile[] files = client.listFiles(TEST_FILE1.getName());
        assertEquals(1, files.length);
        FTPFile file = getFile(files, TEST_FILE1.getName());
        assertEquals(TEST_FILE1.getName(), file.getName());
        assertEquals(0, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertTrue(file.isFile());
        assertFalse(file.isDirectory());
        Calendar expectedTimestamp = Calendar.getInstance();
        expectedTimestamp.setTimeInMillis(TEST_FILE1.lastModified());
        expectedTimestamp.clear(Calendar.SECOND);
        expectedTimestamp.clear(Calendar.MILLISECOND);
        assertEquals(expectedTimestamp, file.getTimestamp());
    }

    public void testListFileNoArgument() throws Exception {
        TEST_DIR1.mkdirs();
        FTPFile[] files = client.listFiles();
        assertEquals(1, files.length);
        FTPFile file = getFile(files, TEST_DIR1.getName());
        assertEquals(TEST_DIR1.getName(), file.getName());
        assertEquals(0, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertFalse(file.isFile());
        assertTrue(file.isDirectory());
    }

    public void testListFiles() throws Exception {
        TEST_FILE1.createNewFile();
        TEST_FILE2.createNewFile();
        TEST_DIR1.mkdirs();
        TEST_DIR2.mkdirs();
        TestUtil.writeDataToFile(TEST_FILE1, testData);
        FTPFile[] files = client.listFiles();
        assertEquals(4, files.length);
        FTPFile file = getFile(files, TEST_FILE1.getName());
        assertEquals(TEST_FILE1.getName(), file.getName());
        assertEquals(testData.length, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertTrue(file.isFile());
        assertFalse(file.isDirectory());
        file = getFile(files, TEST_FILE2.getName());
        assertEquals(TEST_FILE2.getName(), file.getName());
        assertEquals(0, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertTrue(file.isFile());
        assertFalse(file.isDirectory());
        file = getFile(files, TEST_DIR1.getName());
        assertEquals(TEST_DIR1.getName(), file.getName());
        assertEquals(0, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertFalse(file.isFile());
        assertTrue(file.isDirectory());
        file = getFile(files, TEST_DIR2.getName());
        assertEquals(TEST_DIR2.getName(), file.getName());
        assertEquals(0, file.getSize());
        assertEquals("group", file.getGroup());
        assertEquals("user", file.getUser());
        assertFalse(file.isFile());
        assertTrue(file.isDirectory());
    }

    public void testListFileNonExistingFile() throws Exception {
        TEST_DIR1.mkdirs();
        assertEquals(450, client.sendCommand("LIST", TEST_DIR1.getName() + "/nonexisting"));
    }

    public void testListNames() throws Exception {
        TEST_FILE1.createNewFile();
        TEST_FILE2.createNewFile();
        TEST_DIR1.mkdirs();
        TEST_DIR2.mkdirs();
        String[] files = client.listNames();
        assertEquals(4, files.length);
        TestUtil.assertInArrays(TEST_FILE1.getName(), files);
        TestUtil.assertInArrays(TEST_FILE2.getName(), files);
        TestUtil.assertInArrays(TEST_DIR1.getName(), files);
        TestUtil.assertInArrays(TEST_DIR2.getName(), files);
    }

    public void testListName() throws Exception {
        TEST_FILE1.createNewFile();
        TEST_FILE2.createNewFile();
        TEST_DIR1.mkdirs();
        String[] files = client.listNames(TEST_FILE2.getName());
        assertEquals(1, files.length);
        TestUtil.assertInArrays(TEST_FILE2.getName(), files);
    }

    public void testMLST() throws Exception {
        TEST_FILE1.createNewFile();
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("MLST " + TEST_FILE1.getName())));
        String[] reply = client.getReplyString().split("\\r\\n");
        assertEquals("Size=0;Modify=" + DateUtils.getFtpDate(TEST_FILE1.lastModified()) + ";Type=file; " + TEST_FILE1.getName(), reply[1]);
    }

    public void testOPTSMLST() throws Exception {
        TEST_FILE1.createNewFile();
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("OPTS MLST Size;Modify")));
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("MLST " + TEST_FILE1.getName())));
        String[] reply = client.getReplyString().split("\\r\\n");
        assertEquals("Size=0;Modify=" + DateUtils.getFtpDate(TEST_FILE1.lastModified()) + "; " + TEST_FILE1.getName(), reply[1]);
    }

    public void testOPTSMLSTCaseInsensitive() throws Exception {
        TEST_FILE1.createNewFile();
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("OPTS MLST size;Modify")));
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("MLST " + TEST_FILE1.getName())));
        String[] reply = client.getReplyString().split("\\r\\n");
        assertEquals("Size=0;Modify=" + DateUtils.getFtpDate(TEST_FILE1.lastModified()) + "; " + TEST_FILE1.getName(), reply[1]);
    }

    /**
     * "Facts requested that are not
     * supported, or that are inappropriate to the file or directory being
     * listed should simply be omitted from the MLSx output."
     * 
     * http://tools.ietf.org/html/rfc3659#section-7.9
     */
    public void testOPTSMLSTUnknownFact() throws Exception {
        TEST_FILE1.createNewFile();
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("OPTS MLST Foo;Size")));
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("MLST " + TEST_FILE1.getName())));
        String[] reply = client.getReplyString().split("\\r\\n");
        assertEquals("Size=0; " + TEST_FILE1.getName(), reply[1]);
    }

    /**
     * "Facts requested that are not
     * supported, or that are inappropriate to the file or directory being
     * listed should simply be omitted from the MLSx output."
     * 
     * http://tools.ietf.org/html/rfc3659#section-7.9
     */
    public void testOPTSMLSTNoFacts() throws Exception {
        TEST_FILE1.createNewFile();
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("OPTS MLST")));
        assertTrue(FTPReply.isPositiveCompletion(client.sendCommand("MLST " + TEST_FILE1.getName())));
        String[] reply = client.getReplyString().split("\\r\\n");
        assertEquals(" " + TEST_FILE1.getName(), reply[1]);
    }

    private FTPFile getFile(FTPFile[] files, String name) {
        edu.hkust.clap.monitor.Monitor.loopBegin(59);
for (int i = 0; i < files.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(59);
{
            FTPFile file = files[i];
            if (name.equals(file.getName())) {
                return file;
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(59);

        return null;
    }
}
