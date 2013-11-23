package driver;

import java.io.IOException;
import java.net.InetAddress;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ftpserver.clienttests.ClientTestTemplate;

public class FTPClient0 {

    private static InetAddress server;

    private static int NUM = 2;

    public static void main(String[] args) {
        try {
            server = InetAddress.getByName("localhost");
            Thread[] threads = new Thread[NUM];
            edu.hkust.clap.monitor.Monitor.loopBegin(43);
for (int i = 0; i < NUM; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(43);
{
                threads[i] = new MyThread(server, FTPMainDriver.cttinstance.serverport);
                threads[i].start();
            }} 
edu.hkust.clap.monitor.Monitor.loopEnd(43);

            edu.hkust.clap.monitor.Monitor.loopBegin(44);
for (int i = 0; i < NUM; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(44);
{
                threads[i].join();
            }} 
edu.hkust.clap.monitor.Monitor.loopEnd(44);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}

class MyThread extends Thread {

    private InetAddress server;

    private int port;

    public MyThread(InetAddress server, int port) {
        super();
        this.server = server;
        this.port = port;
    }

    public void run() {
        testUpload(ClientTestTemplate.ADMIN_USERNAME, ClientTestTemplate.ADMIN_PASSWORD);
    }

    private void testUpload(String username, String password) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            System.out.println("Connected to " + server + ".");
            ftp.login(username, password);
            if (FTPMainDriver.TEST_FILE1.exists()) {
                System.out.println("deleting files");
                ftp.deleteFile(FTPMainDriver.TEST_FILE1.getName());
                System.out.println("now: " + FTPMainDriver.TEST_FILE1.exists());
            }
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }
}
