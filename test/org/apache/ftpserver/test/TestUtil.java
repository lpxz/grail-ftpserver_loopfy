package org.apache.ftpserver.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import junit.framework.TestCase;
import org.apache.ftpserver.util.IoUtils;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class TestUtil {

    private static final int DEFAULT_PORT = 12321;

    public static File getBaseDir() {
        String basedir = System.getProperty("basedir");
        if (basedir != null) {
            return new File(basedir);
        } else {
            return new File(".");
        }
    }

    /**
     * Attempts to find a free port
     * 
     * @throws IOException
     * 
     * @throws IOException
     */
    public static int findFreePort() throws IOException {
        return findFreePort(DEFAULT_PORT);
    }

    /**
     * Attempts to find a free port
     * @param initPort The first port to try, before resolving to 
     *   brute force searching
     * @throws IOException
     * 
     * @throws IOException
     */
    public static int findFreePort(int initPort) throws IOException {
        int port = -1;
        ServerSocket tmpSocket = null;
        try {
            tmpSocket = new ServerSocket(initPort);
            port = initPort;
            System.out.println("Using default port: " + port);
        } catch (IOException e) {
            System.out.println("Failed to use specified port");
            try {
                int attempts = 0;
                edu.hkust.clap.monitor.Monitor.loopBegin(9);
while (port < 1024 && attempts < 2000) { 
edu.hkust.clap.monitor.Monitor.loopInc(9);
{
                    attempts++;
                    tmpSocket = new ServerSocket(0);
                    port = tmpSocket.getLocalPort();
                }} 
edu.hkust.clap.monitor.Monitor.loopEnd(9);

            } catch (IOException e1) {
                throw new IOException("Failed to find a port to use for testing: " + e1.getMessage());
            }
        } finally {
            if (tmpSocket != null) {
                try {
                    tmpSocket.close();
                } catch (IOException e) {
                }
                tmpSocket = null;
            }
        }
        return port;
    }

    public static String[] getHostAddresses() throws Exception {
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        List<String> hostIps = new ArrayList<String>();
        edu.hkust.clap.monitor.Monitor.loopBegin(10);
while (nifs.hasMoreElements()) { 
edu.hkust.clap.monitor.Monitor.loopInc(10);
{
            NetworkInterface nif = (NetworkInterface) nifs.nextElement();
            Enumeration<InetAddress> ips = nif.getInetAddresses();
            edu.hkust.clap.monitor.Monitor.loopBegin(11);
while (ips.hasMoreElements()) { 
edu.hkust.clap.monitor.Monitor.loopInc(11);
{
                InetAddress ip = (InetAddress) ips.nextElement();
                if (ip instanceof java.net.Inet4Address) {
                    hostIps.add(ip.getHostAddress());
                } else {
                }
            }} 
edu.hkust.clap.monitor.Monitor.loopEnd(11);

        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(10);

        return hostIps.toArray(new String[0]);
    }

    public static InetAddress findNonLocalhostIp() throws Exception {
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        edu.hkust.clap.monitor.Monitor.loopBegin(12);
while (nifs.hasMoreElements()) { 
edu.hkust.clap.monitor.Monitor.loopInc(12);
{
            NetworkInterface nif = (NetworkInterface) nifs.nextElement();
            Enumeration<InetAddress> ips = nif.getInetAddresses();
            edu.hkust.clap.monitor.Monitor.loopBegin(13);
while (ips.hasMoreElements()) { 
edu.hkust.clap.monitor.Monitor.loopInc(13);
{
                InetAddress ip = (InetAddress) ips.nextElement();
                if (ip instanceof java.net.Inet4Address && !ip.isLoopbackAddress()) {
                    return ip;
                } else {
                }
            }} 
edu.hkust.clap.monitor.Monitor.loopEnd(13);

        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(12);

        return null;
    }

    public static void writeDataToFile(File file, byte[] data) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
        } finally {
            IoUtils.close(fos);
        }
    }

    public static void assertFileEqual(byte[] expected, File file) throws Exception {
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;
        try {
            baos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);
            IoUtils.copy(fis, baos, 1024);
            byte[] actual = baos.toByteArray();
            assertArraysEqual(expected, actual);
        } finally {
            IoUtils.close(fis);
            IoUtils.close(baos);
        }
    }

    public static void assertInArrays(Object expected, Object[] actual) {
        boolean found = false;
        edu.hkust.clap.monitor.Monitor.loopBegin(14);
for (int i = 0; i < actual.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(14);
{
            Object object = actual[i];
            if (object.equals(expected)) {
                found = true;
                break;
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(14);

        if (!found) {
            TestCase.fail("Expected value not in array");
        }
    }

    public static void assertArraysEqual(byte[] expected, byte[] actual) {
        if (actual.length != expected.length) {
            TestCase.fail("Arrays are of different length");
        }
        edu.hkust.clap.monitor.Monitor.loopBegin(15);
for (int i = 0; i < actual.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(15);
{
            if (actual[i] != expected[i]) {
                TestCase.fail("Arrays differ at position " + i);
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(15);

    }
}
