package org.apache.ftpserver.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Random;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * IO utility methods.
 *
 * <b>Note: Why not use commons-io?</b>
 * While many of these utility methods are also provided by the Apache
 * commons-io library we prefer to our own implementation to, using a external
 * library might cause additional constraints on users embedding FtpServer.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class IoUtils {

    /**
     * Random number generator to make unique file name
     */
    private static final Random RANDOM_GEN = new Random(System.currentTimeMillis());

    /**
     * Get a <code>BufferedInputStream</code>.
     */
    public static final BufferedInputStream getBufferedInputStream(InputStream in) {
        BufferedInputStream bin = null;
        if (in instanceof java.io.BufferedInputStream) {
            bin = (BufferedInputStream) in;
        } else {
            bin = new BufferedInputStream(in);
        }
        return bin;
    }

    /**
     * Get a <code>BufferedOutputStream</code>.
     */
    public static final BufferedOutputStream getBufferedOutputStream(OutputStream out) {
        BufferedOutputStream bout = null;
        if (out instanceof java.io.BufferedOutputStream) {
            bout = (BufferedOutputStream) out;
        } else {
            bout = new BufferedOutputStream(out);
        }
        return bout;
    }

    /**
     * Get <code>BufferedReader</code>.
     */
    public static final BufferedReader getBufferedReader(Reader reader) {
        BufferedReader buffered = null;
        if (reader instanceof java.io.BufferedReader) {
            buffered = (BufferedReader) reader;
        } else {
            buffered = new BufferedReader(reader);
        }
        return buffered;
    }

    /**
     * Get <code>BufferedWriter</code>.
     */
    public static final BufferedWriter getBufferedWriter(Writer wr) {
        BufferedWriter bw = null;
        if (wr instanceof java.io.BufferedWriter) {
            bw = (BufferedWriter) wr;
        } else {
            bw = new BufferedWriter(wr);
        }
        return bw;
    }

    /**
     * Get unique file object.
     */
    public static final File getUniqueFile(File oldFile) {
        File newFile = oldFile;
        edu.hkust.clap.monitor.Monitor.loopBegin(23);
while (true) { 
edu.hkust.clap.monitor.Monitor.loopInc(23);
{
            if (!newFile.exists()) {
                break;
            }
            newFile = new File(oldFile.getAbsolutePath() + '.' + Math.abs(RANDOM_GEN.nextLong()));
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(23);

        return newFile;
    }

    /**
     * No exception <code>InputStream</code> close method.
     */
    public static final void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * No exception <code>OutputStream</code> close method.
     */
    public static final void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * No exception <code>java.io.Reader</code> close method.
     */
    public static final void close(Reader rd) {
        if (rd != null) {
            try {
                rd.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * No exception <code>java.io.Writer</code> close method.
     */
    public static final void close(Writer wr) {
        if (wr != null) {
            try {
                wr.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Get exception stack trace.
     */
    public static final String getStackTrace(Throwable ex) {
        String result = "";
        if (ex != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                pw.close();
                sw.close();
                result = sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
     * 
     * @param bufferSize
     *            Size of internal buffer to use.
     */
    public static final void copy(Reader input, Writer output, int bufferSize) throws IOException {
        char buffer[] = new char[bufferSize];
        int n = 0;
        edu.hkust.clap.monitor.Monitor.loopBegin(24);
while ((n = input.read(buffer)) != -1) { 
edu.hkust.clap.monitor.Monitor.loopInc(24);
{
            output.write(buffer, 0, n);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(24);

    }

    /**
     * Copy chars from a <code>InputStream</code> to a <code>OutputStream</code>
     * .
     * 
     * @param bufferSize
     *            Size of internal buffer to use.
     */
    public static final void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte buffer[] = new byte[bufferSize];
        int n = 0;
        edu.hkust.clap.monitor.Monitor.loopBegin(24);
while ((n = input.read(buffer)) != -1) { 
edu.hkust.clap.monitor.Monitor.loopInc(24);
{
            output.write(buffer, 0, n);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(24);

    }

    /**
     * Read fully from reader
     */
    public static final String readFully(Reader reader) throws IOException {
        StringWriter writer = new StringWriter();
        copy(reader, writer, 1024);
        return writer.toString();
    }

    /**
     * Read fully from stream
     */
    public static final String readFully(InputStream input) throws IOException {
        StringWriter writer = new StringWriter();
        InputStreamReader reader = new InputStreamReader(input);
        copy(reader, writer, 1024);
        return writer.toString();
    }

    public static final void delete(File file) throws IOException {
        if (!file.exists()) {
            return;
        } else if (file.isDirectory()) {
            deleteDir(file);
        } else {
            deleteFile(file);
        }
    }

    private static final void deleteDir(File dir) throws IOException {
        File[] children = dir.listFiles();
        if (children == null) {
            return;
        }
        edu.hkust.clap.monitor.Monitor.loopBegin(25);
for (int i = 0; i < children.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(25);
{
            File file = children[i];
            delete(file);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(25);

        if (!dir.delete()) {
            throw new IOException("Failed to delete directory: " + dir);
        }
    }

    private static final void deleteFile(File file) throws IOException {
        if (!file.delete()) {
            if (OS.isFamilyWindows()) {
                System.gc();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (!file.delete()) {
                throw new IOException("Failed to delete file: " + file);
            }
        }
    }
}
