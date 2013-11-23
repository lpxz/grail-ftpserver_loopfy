package org.apache.ftpserver.message.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.ftpserver.FtpServerConfigurationException;
import org.apache.ftpserver.message.MessageResource;
import org.apache.ftpserver.message.MessageResourceFactory;
import org.apache.ftpserver.util.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Class to get FtpServer reply messages. This supports i18n. Basic message
 * search path is:
 * 
 * <strong><strong>Internal class, do not use directly.</strong></strong>
 * 
 * Custom Language Specific Messages -> Default Language Specific Messages ->
 * Custom Common Messages -> Default Common Messages -> null (not found)
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class DefaultMessageResource implements MessageResource {

    private final Logger LOG = LoggerFactory.getLogger(DefaultMessageResource.class);

    private static final String RESOURCE_PATH = "org/apache/ftpserver/message/";

    private List<String> languages;

    private Map<String, PropertiesPair> messages;

    /**
     * Internal constructor, do not use directly. Use {@link MessageResourceFactory} instead.
     */
    public DefaultMessageResource(List<String> languages, File customMessageDirectory) {
        if (languages != null) {
            this.languages = Collections.unmodifiableList(languages);
        }
        messages = new HashMap<String, PropertiesPair>();
        if (languages != null) {
            for (String language : languages) {
                PropertiesPair pair = createPropertiesPair(language, customMessageDirectory);
                messages.put(language, pair);
            }
        }
        PropertiesPair pair = createPropertiesPair(null, customMessageDirectory);
        messages.put(null, pair);
    }

    private static class PropertiesPair {

        public Properties defaultProperties = new Properties();

        public Properties customProperties = new Properties();
    }

    /**
     * Create Properties pair object. It stores the default and the custom
     * messages.
     */
    private PropertiesPair createPropertiesPair(String lang, File customMessageDirectory) {
        PropertiesPair pair = new PropertiesPair();
        String defaultResourceName;
        if (lang == null) {
            defaultResourceName = RESOURCE_PATH + "FtpStatus.properties";
        } else {
            defaultResourceName = RESOURCE_PATH + "FtpStatus_" + lang + ".properties";
        }
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(defaultResourceName);
            if (in != null) {
                try {
                    pair.defaultProperties.load(in);
                } catch (IOException e) {
                    throw new FtpServerConfigurationException("Failed to load messages from \"" + defaultResourceName + "\", file not found in classpath");
                }
            } else {
                throw new FtpServerConfigurationException("Failed to load messages from \"" + defaultResourceName + "\", file not found in classpath");
            }
        } finally {
            IoUtils.close(in);
        }
        File resourceFile = null;
        if (lang == null) {
            resourceFile = new File(customMessageDirectory, "FtpStatus.gen");
        } else {
            resourceFile = new File(customMessageDirectory, "FtpStatus_" + lang + ".gen");
        }
        in = null;
        try {
            if (resourceFile.exists()) {
                in = new FileInputStream(resourceFile);
                pair.customProperties.load(in);
            }
        } catch (Exception ex) {
            LOG.warn("MessageResourceImpl.createPropertiesPair()", ex);
            throw new FtpServerConfigurationException("MessageResourceImpl.createPropertiesPair()", ex);
        } finally {
            IoUtils.close(in);
        }
        return pair;
    }

    /**
     * Get all the available languages.
     */
    public List<String> getAvailableLanguages() {
        if (languages == null) {
            return null;
        } else {
            return Collections.unmodifiableList(languages);
        }
    }

    /**
     * Get the message. If the message not found, it will return null.
     */
    public String getMessage(int code, String subId, String language) {
        String key = String.valueOf(code);
        if (subId != null) {
            key = key + '.' + subId;
        }
        String value = null;
        PropertiesPair pair = null;
        if (language != null) {
            language = language.toLowerCase();
            pair = messages.get(language);
            if (pair != null) {
                value = pair.customProperties.getProperty(key);
                if (value == null) {
                    value = pair.defaultProperties.getProperty(key);
                }
            }
        }
        if (value == null) {
            pair = messages.get(null);
            if (pair != null) {
                value = pair.customProperties.getProperty(key);
                if (value == null) {
                    value = pair.defaultProperties.getProperty(key);
                }
            }
        }
        return value;
    }

    /**
     * Get all messages.
     */
    public Map<String, String> getMessages(String language) {
        Properties messages = new Properties();
        PropertiesPair pair = this.messages.get(null);
        if (pair != null) {
            messages.putAll(pair.defaultProperties);
            messages.putAll(pair.customProperties);
        }
        if (language != null) {
            language = language.toLowerCase();
            pair = this.messages.get(language);
            if (pair != null) {
                messages.putAll(pair.defaultProperties);
                messages.putAll(pair.customProperties);
            }
        }
        Map<String, String> result = new HashMap<String, String>();
        for (Object key : messages.keySet()) {
            result.put(key.toString(), messages.getProperty(key.toString()));
        }
        return Collections.unmodifiableMap(result);
    }

    /**
     * Dispose component - clear all maps.
     */
    public void dispose() {
        Iterator<String> it = messages.keySet().iterator();
        edu.hkust.clap.monitor.Monitor.loopBegin(32);
while (it.hasNext()) { 
edu.hkust.clap.monitor.Monitor.loopInc(32);
{
            String language = it.next();
            PropertiesPair pair = messages.get(language);
            pair.customProperties.clear();
            pair.defaultProperties.clear();
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(32);

        messages.clear();
    }
}
