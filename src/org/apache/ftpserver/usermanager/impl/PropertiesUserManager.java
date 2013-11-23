package org.apache.ftpserver.usermanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.apache.ftpserver.FtpServerConfigurationException;
import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.util.BaseProperties;
import org.apache.ftpserver.util.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <p>Properties file based <code>UserManager</code> implementation. We use
 * <code>user.properties</code> file to store user data.</p>
 *
 * </p>The file will use the following properties for storing users:</p>
 * <table>
 * <tr>
 *      <th>Property</th>
 *      <th>Documentation</th>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.homedirectory</td>
 *      <td>Path to the home directory for the user, based on the file system implementation used</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.userpassword</td>
 *      <td>The password for the user. Can be in clear text, MD5 hash or salted SHA hash based on the 
 *              configuration on the user manager
 *      </td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.enableflag</td>
 *      <td>true if the user is enabled, false otherwise</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.writepermission</td>
 *      <td>true if the user is allowed to upload files and create directories, false otherwise</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.idletime</td>
 *      <td>The number of seconds the user is allowed to be idle before disconnected. 
 *              0 disables the idle timeout
 *      </td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.maxloginnumber</td>
 *      <td>The maximum number of concurrent logins by the user. 0 disables the check.</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.maxloginperip</td>
 *      <td>The maximum number of concurrent logins from the same IP address by the user. 0 disables the check.</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.uploadrate</td>
 *      <td>The maximum number of bytes per second the user is allowed to upload files. 0 disables the check.</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.downloadrate</td>
 *      <td>The maximum number of bytes per second the user is allowed to download files. 0 disables the check.</td>
 * </tr>
 * </table>
 * 
 * <p>Example:</p>
 * <pre>
 * ftpserver.user.admin.homedirectory=/ftproot
 * ftpserver.user.admin.userpassword=admin
 * ftpserver.user.admin.enableflag=true
 * ftpserver.user.admin.writepermission=true
 * ftpserver.user.admin.idletime=0
 * ftpserver.user.admin.maxloginnumber=0
 * ftpserver.user.admin.maxloginperip=0
 * ftpserver.user.admin.uploadrate=0
 * ftpserver.user.admin.downloadrate=0
 * </pre>
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class PropertiesUserManager extends AbstractUserManager {

    private final Logger LOG = LoggerFactory.getLogger(PropertiesUserManager.class);

    private static final String PREFIX = "ftpserver.user.";

    private BaseProperties userDataProp;

    private File userDataFile;

    private URL userUrl;

    /**
     * Internal constructor, do not use directly. Use {@link PropertiesUserManagerFactory} instead.
     */
    public PropertiesUserManager(PasswordEncryptor passwordEncryptor, File userDataFile, String adminName) {
        super(adminName, passwordEncryptor);
        loadFromFile(userDataFile);
    }

    /**
     * Internal constructor, do not use directly. Use {@link PropertiesUserManagerFactory} instead.
     */
    public PropertiesUserManager(PasswordEncryptor passwordEncryptor, URL userDataPath, String adminName) {
        super(adminName, passwordEncryptor);
        loadFromUrl(userDataPath);
    }

    private void loadFromFile(File userDataFile) {
        try {
            userDataProp = new BaseProperties();
            if (userDataFile != null) {
                LOG.debug("File configured, will try loading");
                if (userDataFile.exists()) {
                    this.userDataFile = userDataFile;
                    LOG.debug("File found on file system");
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(userDataFile);
                        userDataProp.load(fis);
                    } finally {
                        IoUtils.close(fis);
                    }
                } else {
                    LOG.debug("File not found on file system, try loading from classpath");
                    InputStream is = getClass().getClassLoader().getResourceAsStream(userDataFile.getPath());
                    if (is != null) {
                        try {
                            userDataProp.load(is);
                        } finally {
                            IoUtils.close(is);
                        }
                    } else {
                        throw new FtpServerConfigurationException("User data file specified but could not be located, " + "neither on the file system or in the classpath: " + userDataFile.getPath());
                    }
                }
            }
        } catch (IOException e) {
            throw new FtpServerConfigurationException("Error loading user data file : " + userDataFile, e);
        }
    }

    private void loadFromUrl(URL userDataPath) {
        try {
            userDataProp = new BaseProperties();
            if (userDataPath != null) {
                LOG.debug("URL configured, will try loading");
                userUrl = userDataPath;
                InputStream is = null;
                is = userDataPath.openStream();
                try {
                    userDataProp.load(is);
                } finally {
                    IoUtils.close(is);
                }
            }
        } catch (IOException e) {
            throw new FtpServerConfigurationException("Error loading user data resource : " + userDataPath, e);
        }
    }

    /**
     * Reloads the contents of the user.properties file. This allows any manual modifications to the file to be recognised by the running server.
     */
    public void refresh() {
        synchronized (userDataProp) {
            if (userDataFile != null) {
                LOG.debug("Refreshing user manager using file: " + userDataFile.getAbsolutePath());
                loadFromFile(userDataFile);
            } else {
                LOG.debug("Refreshing user manager using URL: " + userUrl.toString());
                loadFromUrl(userUrl);
            }
        }
    }

    /**
     * Retrive the file backing this user manager
     * @return The file
     */
    public File getFile() {
        return userDataFile;
    }

    /**
     * Save user data. Store the properties.
     */
    public synchronized void save(User usr) throws FtpException {
        if (usr.getName() == null) {
            throw new NullPointerException("User name is null.");
        }
        String thisPrefix = PREFIX + usr.getName() + '.';
        userDataProp.setProperty(thisPrefix + ATTR_PASSWORD, getPassword(usr));
        String home = usr.getHomeDirectory();
        if (home == null) {
            home = "/";
        }
        userDataProp.setProperty(thisPrefix + ATTR_HOME, home);
        userDataProp.setProperty(thisPrefix + ATTR_ENABLE, usr.getEnabled());
        userDataProp.setProperty(thisPrefix + ATTR_WRITE_PERM, usr.authorize(new WriteRequest()) != null);
        userDataProp.setProperty(thisPrefix + ATTR_MAX_IDLE_TIME, usr.getMaxIdleTime());
        TransferRateRequest transferRateRequest = new TransferRateRequest();
        transferRateRequest = (TransferRateRequest) usr.authorize(transferRateRequest);
        if (transferRateRequest != null) {
            userDataProp.setProperty(thisPrefix + ATTR_MAX_UPLOAD_RATE, transferRateRequest.getMaxUploadRate());
            userDataProp.setProperty(thisPrefix + ATTR_MAX_DOWNLOAD_RATE, transferRateRequest.getMaxDownloadRate());
        } else {
            userDataProp.remove(thisPrefix + ATTR_MAX_UPLOAD_RATE);
            userDataProp.remove(thisPrefix + ATTR_MAX_DOWNLOAD_RATE);
        }
        ConcurrentLoginRequest concurrentLoginRequest = new ConcurrentLoginRequest(0, 0);
        concurrentLoginRequest = (ConcurrentLoginRequest) usr.authorize(concurrentLoginRequest);
        if (concurrentLoginRequest != null) {
            userDataProp.setProperty(thisPrefix + ATTR_MAX_LOGIN_NUMBER, concurrentLoginRequest.getMaxConcurrentLogins());
            userDataProp.setProperty(thisPrefix + ATTR_MAX_LOGIN_PER_IP, concurrentLoginRequest.getMaxConcurrentLoginsPerIP());
        } else {
            userDataProp.remove(thisPrefix + ATTR_MAX_LOGIN_NUMBER);
            userDataProp.remove(thisPrefix + ATTR_MAX_LOGIN_PER_IP);
        }
        saveUserData();
    }

    /**
     * @throws FtpException
     */
    private void saveUserData() throws FtpException {
        if (userDataFile == null) {
            return;
        }
        File dir = userDataFile.getAbsoluteFile().getParentFile();
        if (dir != null && !dir.exists() && !dir.mkdirs()) {
            String dirName = dir.getAbsolutePath();
            throw new FtpServerConfigurationException("Cannot create directory for user data file : " + dirName);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(userDataFile);
            userDataProp.store(fos, "Generated file - don't edit (please)");
        } catch (IOException ex) {
            LOG.error("Failed saving user data", ex);
            throw new FtpException("Failed saving user data", ex);
        } finally {
            IoUtils.close(fos);
        }
    }

    /**
     * Delete an user. Removes all this user entries from the properties. After
     * removing the corresponding from the properties, save the data.
     */
    public void delete(String usrName) throws FtpException {
        String thisPrefix = PREFIX + usrName + '.';
        Enumeration<?> propNames = userDataProp.propertyNames();
        ArrayList<String> remKeys = new ArrayList<String>();
        edu.hkust.clap.monitor.Monitor.loopBegin(45);
while (propNames.hasMoreElements()) { 
edu.hkust.clap.monitor.Monitor.loopInc(45);
{
            String thisKey = propNames.nextElement().toString();
            if (thisKey.startsWith(thisPrefix)) {
                remKeys.add(thisKey);
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(45);

        Iterator<String> remKeysIt = remKeys.iterator();
        edu.hkust.clap.monitor.Monitor.loopBegin(46);
while (remKeysIt.hasNext()) { 
edu.hkust.clap.monitor.Monitor.loopInc(46);
{
            userDataProp.remove(remKeysIt.next());
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(46);

        saveUserData();
    }

    /**
     * Get user password. Returns the encrypted value.
     * 
     * <pre>
     * If the password value is not null
     *    password = new password 
     * else 
     *   if user does exist
     *     password = old password
     *   else 
     *     password = &quot;&quot;
     * </pre>
     */
    private String getPassword(User usr) {
        String name = usr.getName();
        String password = usr.getPassword();
        if (password != null) {
            password = getPasswordEncryptor().encrypt(password);
        } else {
            String blankPassword = getPasswordEncryptor().encrypt("");
            if (doesExist(name)) {
                String key = PREFIX + name + '.' + ATTR_PASSWORD;
                password = userDataProp.getProperty(key, blankPassword);
            } else {
                password = blankPassword;
            }
        }
        return password;
    }

    /**
     * Get all user names.
     */
    public String[] getAllUserNames() {
        String suffix = '.' + ATTR_HOME;
        ArrayList<String> ulst = new ArrayList<String>();
        Enumeration<?> allKeys = userDataProp.propertyNames();
        int prefixlen = PREFIX.length();
        int suffixlen = suffix.length();
        edu.hkust.clap.monitor.Monitor.loopBegin(47);
while (allKeys.hasMoreElements()) { 
edu.hkust.clap.monitor.Monitor.loopInc(47);
{
            String key = (String) allKeys.nextElement();
            if (key.endsWith(suffix)) {
                String name = key.substring(prefixlen);
                int endIndex = name.length() - suffixlen;
                name = name.substring(0, endIndex);
                ulst.add(name);
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(47);

        Collections.sort(ulst);
        return ulst.toArray(new String[0]);
    }

    /**
     * Load user data.
     */
    public User getUserByName(String userName) {
        if (!doesExist(userName)) {
            return null;
        }
        String baseKey = PREFIX + userName + '.';
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setEnabled(userDataProp.getBoolean(baseKey + ATTR_ENABLE, true));
        user.setHomeDirectory(userDataProp.getProperty(baseKey + ATTR_HOME, "/"));
        List<Authority> authorities = new ArrayList<Authority>();
        if (userDataProp.getBoolean(baseKey + ATTR_WRITE_PERM, false)) {
            authorities.add(new WritePermission());
        }
        int maxLogin = userDataProp.getInteger(baseKey + ATTR_MAX_LOGIN_NUMBER, 0);
        int maxLoginPerIP = userDataProp.getInteger(baseKey + ATTR_MAX_LOGIN_PER_IP, 0);
        authorities.add(new ConcurrentLoginPermission(maxLogin, maxLoginPerIP));
        int uploadRate = userDataProp.getInteger(baseKey + ATTR_MAX_UPLOAD_RATE, 0);
        int downloadRate = userDataProp.getInteger(baseKey + ATTR_MAX_DOWNLOAD_RATE, 0);
        authorities.add(new TransferRatePermission(downloadRate, uploadRate));
        user.setAuthorities(authorities);
        user.setMaxIdleTime(userDataProp.getInteger(baseKey + ATTR_MAX_IDLE_TIME, 0));
        return user;
    }

    /**
     * User existance check
     */
    public boolean doesExist(String name) {
        String key = PREFIX + name + '.' + ATTR_HOME;
        return userDataProp.containsKey(key);
    }

    /**
     * User authenticate method
     */
    public User authenticate(Authentication authentication) throws AuthenticationFailedException {
        if (authentication instanceof UsernamePasswordAuthentication) {
            UsernamePasswordAuthentication upauth = (UsernamePasswordAuthentication) authentication;
            String user = upauth.getUsername();
            String password = upauth.getPassword();
            if (user == null) {
                throw new AuthenticationFailedException("Authentication failed");
            }
            if (password == null) {
                password = "";
            }
            String storedPassword = userDataProp.getProperty(PREFIX + user + '.' + ATTR_PASSWORD);
            if (storedPassword == null) {
                throw new AuthenticationFailedException("Authentication failed");
            }
            if (getPasswordEncryptor().matches(password, storedPassword)) {
                return getUserByName(user);
            } else {
                throw new AuthenticationFailedException("Authentication failed");
            }
        } else if (authentication instanceof AnonymousAuthentication) {
            if (doesExist("anonymous")) {
                return getUserByName("anonymous");
            } else {
                throw new AuthenticationFailedException("Authentication failed");
            }
        } else {
            throw new IllegalArgumentException("Authentication not supported by this user manager");
        }
    }

    /**
     * Close the user manager - remove existing entries.
     */
    public synchronized void dispose() {
        if (userDataProp != null) {
            userDataProp.clear();
            userDataProp = null;
        }
    }
}
