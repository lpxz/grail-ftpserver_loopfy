package org.apache.ftpserver.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Provides support for parsing a passive ports string as well as keeping track
 * of reserved passive ports.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class PassivePorts {

    private Logger log = LoggerFactory.getLogger(PassivePorts.class);

    private static final int MAX_PORT = 65535;

    private static final Integer MAX_PORT_INTEGER = Integer.valueOf(MAX_PORT);

    private List<Integer> freeList;

    private Set<Integer> usedList;

    private Random r = new Random();

    private String passivePortsString;

    private boolean checkIfBound;

    /**
     * Parse a string containing passive ports
     * 
     * @param portsString
     *            A string of passive ports, can contain a single port (as an
     *            integer), multiple ports seperated by commas (e.g.
     *            123,124,125) or ranges of ports, including open ended ranges
     *            (e.g. 123-125, 30000-, -1023). Combinations for single ports
     *            and ranges is also supported.
     * @return A list of Integer objects, based on the parsed string
     * @throws IllegalArgumentException
     *             If any of of the ports in the string is invalid (e.g. not an
     *             integer or too large for a port number)
     */
    private static Set<Integer> parse(final String portsString) {
        Set<Integer> passivePortsList = new HashSet<Integer>();
        boolean inRange = false;
        Integer lastPort = Integer.valueOf(1);
        StringTokenizer st = new StringTokenizer(portsString, ",;-", true);
        edu.hkust.clap.monitor.Monitor.loopBegin(53);
while (st.hasMoreTokens()) { 
edu.hkust.clap.monitor.Monitor.loopInc(53);
{
            String token = st.nextToken().trim();
            if (",".equals(token) || ";".equals(token)) {
                if (inRange) {
                    fillRange(passivePortsList, lastPort, MAX_PORT_INTEGER);
                }
                lastPort = Integer.valueOf(1);
                inRange = false;
            } else if ("-".equals(token)) {
                inRange = true;
            } else if (token.length() == 0) {
            } else {
                Integer port = Integer.valueOf(token);
                verifyPort(port);
                if (inRange) {
                    fillRange(passivePortsList, lastPort, port);
                    inRange = false;
                }
                addPort(passivePortsList, port);
                lastPort = port;
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(53);

        if (inRange) {
            fillRange(passivePortsList, lastPort, MAX_PORT_INTEGER);
        }
        return passivePortsList;
    }

    /**
     * Fill a range of ports
     */
    private static void fillRange(final Set<Integer> passivePortsList, final Integer beginPort, final Integer endPort) {
        edu.hkust.clap.monitor.Monitor.loopBegin(54);
for (int i = beginPort; i <= endPort; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(54);
{
            addPort(passivePortsList, Integer.valueOf(i));
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(54);

    }

    /**
     * Add a single port if not already in list
     */
    private static void addPort(final Set<Integer> passivePortsList, final Integer port) {
        passivePortsList.add(port);
    }

    /**
     * Verify that the port is within the range of allowed ports
     */
    private static void verifyPort(final int port) {
        if (port < 0) {
            throw new IllegalArgumentException("Port can not be negative: " + port);
        } else if (port > MAX_PORT) {
            throw new IllegalArgumentException("Port too large: " + port);
        }
    }

    public PassivePorts(final String passivePorts, boolean checkIfBound) {
        this(parse(passivePorts), checkIfBound);
        this.passivePortsString = passivePorts;
    }

    public PassivePorts(Set<Integer> passivePorts, boolean checkIfBound) {
        if (passivePorts == null) {
            throw new NullPointerException("passivePorts can not be null");
        } else if (passivePorts.isEmpty()) {
            passivePorts = new HashSet<Integer>();
            passivePorts.add(0);
        }
        this.freeList = new ArrayList<Integer>(passivePorts);
        this.usedList = new HashSet<Integer>(passivePorts.size());
        this.checkIfBound = checkIfBound;
    }

    /**
     * Checks that the port of not bound by another application
     */
    private boolean checkPortUnbound(int port) {
        if (!checkIfBound) {
            return true;
        }
        if (port == 0) {
            return true;
        }
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
    }

    public synchronized int reserveNextPort() {
        List<Integer> freeCopy = new ArrayList<Integer>(freeList);
        edu.hkust.clap.monitor.Monitor.loopBegin(55);
while (freeCopy.size() > 0) { 
edu.hkust.clap.monitor.Monitor.loopInc(55);
{
            int i = r.nextInt(freeCopy.size());
            Integer ret = freeCopy.get(i);
            if (ret == 0) {
                return 0;
            } else if (checkPortUnbound(ret)) {
                freeList.remove(i);
                usedList.add(ret);
                return ret;
            } else {
                freeCopy.remove(i);
                log.warn("Passive port in use by another process: " + ret);
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(55);

        return -1;
    }

    public synchronized void releasePort(final int port) {
        if (port == 0) {
        } else if (usedList.remove(port)) {
            freeList.add(port);
        } else {
            log.warn("Releasing unreserved passive port: " + port);
        }
    }

    @Override
    public String toString() {
        if (passivePortsString != null) {
            return passivePortsString;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer port : freeList) {
            sb.append(port);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
