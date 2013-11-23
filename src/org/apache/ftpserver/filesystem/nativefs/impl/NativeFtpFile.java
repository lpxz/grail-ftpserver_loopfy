package org.apache.ftpserver.filesystem.nativefs.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.impl.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * This class wraps native file object.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class NativeFtpFile implements FtpFile {

    private final Logger LOG = LoggerFactory.getLogger(NativeFtpFile.class);

    private String fileName;

    private File file;

    private User user;

    /**
     * Constructor, internal do not use directly.
     */
    protected NativeFtpFile(final String fileName, final File file, final User user) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName can not be null");
        }
        if (file == null) {
            throw new IllegalArgumentException("file can not be null");
        }
        if (fileName.length() == 0) {
            throw new IllegalArgumentException("fileName can not be empty");
        } else if (fileName.charAt(0) != '/') {
            throw new IllegalArgumentException("fileName must be an absolut path");
        }
        this.fileName = fileName;
        this.file = file;
        this.user = user;
    }

    /**
     * Get full name.
     */
    public String getAbsolutePath() {
        String fullName = fileName;
        int filelen = fullName.length();
        if ((filelen != 1) && (fullName.charAt(filelen - 1) == '/')) {
            fullName = fullName.substring(0, filelen - 1);
        }
        return fullName;
    }

    /**
     * Get short name.
     */
    public String getName() {
        if (fileName.equals("/")) {
            return "/";
        }
        String shortName = fileName;
        int filelen = fileName.length();
        if (shortName.charAt(filelen - 1) == '/') {
            shortName = shortName.substring(0, filelen - 1);
        }
        int slashIndex = shortName.lastIndexOf('/');
        if (slashIndex != -1) {
            shortName = shortName.substring(slashIndex + 1);
        }
        return shortName;
    }

    /**
     * Is a hidden file?
     */
    public boolean isHidden() {
        return file.isHidden();
    }

    /**
     * Is it a directory?
     */
    public boolean isDirectory() {
        return file.isDirectory();
    }

    /**
     * Is it a file?
     */
    public boolean isFile() {
        return file.isFile();
    }

    /**
     * Does this file exists?
     */
    public boolean doesExist() {
        return file.exists();
    }

    /**
     * Get file size.
     */
    public long getSize() {
        return file.length();
    }

    /**
     * Get file owner.
     */
    public String getOwnerName() {
        return "user";
    }

    /**
     * Get group name
     */
    public String getGroupName() {
        return "group";
    }

    /**
     * Get link count
     */
    public int getLinkCount() {
        return file.isDirectory() ? 3 : 1;
    }

    /**
     * Get last modified time.
     */
    public long getLastModified() {
        return file.lastModified();
    }

    /**
     * {@inheritDoc}
     */
    public boolean setLastModified(long time) {
        return file.setLastModified(time);
    }

    /**
     * Check read permission.
     */
    public boolean isReadable() {
        return file.canRead();
    }

    /**
     * Check file write permission.
     */
    public boolean isWritable() {
        LOG.debug("Checking authorization for " + getAbsolutePath());
        if (user.authorize(new WriteRequest(getAbsolutePath())) == null) {
            LOG.debug("Not authorized");
            return false;
        }
        LOG.debug("Checking if file exists");
        if (file.exists()) {
            LOG.debug("Checking can write: " + file.canWrite());
            return file.canWrite();
        }
        LOG.debug("Authorized");
        return true;
    }

    /**
     * Has delete permission.
     */
    public boolean isRemovable() {
        if ("/".equals(fileName)) {
            return false;
        }
        String fullName = getAbsolutePath();
        if (user.authorize(new WriteRequest(fullName)) == null) {
            return false;
        }
        int indexOfSlash = fullName.lastIndexOf('/');
        String parentFullName;
        if (indexOfSlash == 0) {
            parentFullName = "/";
        } else {
            parentFullName = fullName.substring(0, indexOfSlash);
        }
        NativeFtpFile parentObject = new NativeFtpFile(parentFullName, file.getAbsoluteFile().getParentFile(), user);
        return parentObject.isWritable();
    }

    /**
     * Delete file.
     */
    public boolean delete() {
        boolean retVal = false;
        if (isRemovable()) {
            retVal = file.delete();
        }
        return retVal;
    }

    /**
     * Move file object.
     */
    public boolean move(final FtpFile dest) {
        boolean retVal = false;
        if (dest.isWritable() && isReadable()) {
            File destFile = ((NativeFtpFile) dest).file;
            if (destFile.exists()) {
                retVal = false;
            } else {
                retVal = file.renameTo(destFile);
            }
        }
        return retVal;
    }

    /**
     * Create directory.
     */
    public boolean mkdir() {
        boolean retVal = false;
        if (isWritable()) {
            retVal = file.mkdir();
        }
        return retVal;
    }

    /**
     * Get the physical file object.
     */
    public File getPhysicalFile() {
        return file;
    }

    /**
     * List files. If not a directory or does not exist, null will be returned.
     */
    public List<FtpFile> listFiles() {
        if (!file.isDirectory()) {
            return null;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return null;
        }
        Arrays.sort(files, new Comparator<File>() {

            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        String virtualFileStr = getAbsolutePath();
        if (virtualFileStr.charAt(virtualFileStr.length() - 1) != '/') {
            virtualFileStr += '/';
        }
        FtpFile[] virtualFiles = new FtpFile[files.length];
        edu.hkust.clap.monitor.Monitor.loopBegin(35);
for (int i = 0; i < files.length; ++i) { 
edu.hkust.clap.monitor.Monitor.loopInc(35);
{
            File fileObj = files[i];
            String fileName = virtualFileStr + fileObj.getName();
            virtualFiles[i] = new NativeFtpFile(fileName, fileObj, user);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(35);

        return Collections.unmodifiableList(Arrays.asList(virtualFiles));
    }

    /**
     * Create output stream for writing.
     */
    public OutputStream createOutputStream(final long offset) throws IOException {
        if (!isWritable()) {
            throw new IOException("No write permission : " + file.getName());
        }
        final RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(offset);
        raf.seek(offset);
        return new FileOutputStream(raf.getFD()) {

            @Override
            public void close() throws IOException {
                super.close();
                raf.close();
            }
        };
    }

    /**
     * Create input stream for reading.
     */
    public InputStream createInputStream(final long offset) throws IOException {
        if (!isReadable()) {
            throw new IOException("No read permission : " + file.getName());
        }
        final RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(offset);
        return new FileInputStream(raf.getFD()) {

            @Override
            public void close() throws IOException {
                super.close();
                raf.close();
            }
        };
    }

    /**
     * Normalize separate character. Separate character should be '/' always.
     */
    public static final String normalizeSeparateChar(final String pathName) {
        String normalizedPathName = pathName.replace(File.separatorChar, '/');
        normalizedPathName = normalizedPathName.replace('\\', '/');
        return normalizedPathName;
    }

    /**
     * Get the physical canonical file name. It works like
     * File.getCanonicalPath().
     * 
     * @param rootDir
     *            The root directory.
     * @param currDir
     *            The current directory. It will always be with respect to the
     *            root directory.
     * @param fileName
     *            The input file name.
     * @return The return string will always begin with the root directory. It
     *         will never be null.
     */
    public static final String getPhysicalName(final String rootDir, final String currDir, final String fileName) {
        return getPhysicalName(rootDir, currDir, fileName, false);
    }

    public static final String getPhysicalName(final String rootDir, final String currDir, final String fileName, final boolean caseInsensitive) {
        String normalizedRootDir = normalizeSeparateChar(rootDir);
        if (normalizedRootDir.charAt(normalizedRootDir.length() - 1) != '/') {
            normalizedRootDir += '/';
        }
        String normalizedFileName = normalizeSeparateChar(fileName);
        String resArg;
        String normalizedCurrDir = currDir;
        if (normalizedFileName.charAt(0) != '/') {
            if (normalizedCurrDir == null) {
                normalizedCurrDir = "/";
            }
            if (normalizedCurrDir.length() == 0) {
                normalizedCurrDir = "/";
            }
            normalizedCurrDir = normalizeSeparateChar(normalizedCurrDir);
            if (normalizedCurrDir.charAt(0) != '/') {
                normalizedCurrDir = '/' + normalizedCurrDir;
            }
            if (normalizedCurrDir.charAt(normalizedCurrDir.length() - 1) != '/') {
                normalizedCurrDir += '/';
            }
            resArg = normalizedRootDir + normalizedCurrDir.substring(1);
        } else {
            resArg = normalizedRootDir;
        }
        if (resArg.charAt(resArg.length() - 1) == '/') {
            resArg = resArg.substring(0, resArg.length() - 1);
        }
        StringTokenizer st = new StringTokenizer(normalizedFileName, "/");
        edu.hkust.clap.monitor.Monitor.loopBegin(36);
while (st.hasMoreTokens()) { 
edu.hkust.clap.monitor.Monitor.loopInc(36);
{
            String tok = st.nextToken();
            if (tok.equals(".")) {
                continue;
            }
            if (tok.equals("..")) {
                if (resArg.startsWith(normalizedRootDir)) {
                    int slashIndex = resArg.lastIndexOf('/');
                    if (slashIndex != -1) {
                        resArg = resArg.substring(0, slashIndex);
                    }
                }
                continue;
            }
            if (tok.equals("~")) {
                resArg = normalizedRootDir.substring(0, normalizedRootDir.length() - 1);
                continue;
            }
            if (caseInsensitive) {
                File[] matches = new File(resArg).listFiles(new NameEqualsFileFilter(tok, true));
                if (matches != null && matches.length > 0) {
                    tok = matches[0].getName();
                }
            }
            resArg = resArg + '/' + tok;
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(36);

        if ((resArg.length()) + 1 == normalizedRootDir.length()) {
            resArg += '/';
        }
        if (!resArg.regionMatches(0, normalizedRootDir, 0, normalizedRootDir.length())) {
            resArg = normalizedRootDir;
        }
        return resArg;
    }

    /**
     * Implements equals by comparing getCanonicalPath() for the underlying file instabnce.
     * Ignores the fileName and User fields
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NativeFtpFile) {
            String thisCanonicalPath;
            String otherCanonicalPath;
            try {
                thisCanonicalPath = this.file.getCanonicalPath();
                otherCanonicalPath = ((NativeFtpFile) obj).file.getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException("Failed to get the canonical path", e);
            }
            return thisCanonicalPath.equals(otherCanonicalPath);
        }
        return false;
    }

    @Override
    public int hashCode() {
        try {
            return file.getCanonicalPath().hashCode();
        } catch (IOException e) {
            return 0;
        }
    }
}
