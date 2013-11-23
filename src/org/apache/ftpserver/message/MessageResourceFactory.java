package org.apache.ftpserver.message;

import java.io.File;
import java.util.List;
import org.apache.ftpserver.message.impl.DefaultMessageResource;

/**
 * Factory for creating message resource implementation
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class MessageResourceFactory {

    private List<String> languages;

    private File customMessageDirectory;

    /**
     * Create an {@link MessageResource} based on the configuration on this factory
     * @return The {@link MessageResource} instance
     */
    public MessageResource createMessageResource() {
        return new DefaultMessageResource(languages, customMessageDirectory);
    }

    /**
     * The languages for which messages are available 
     * @return The list of available languages
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Set the languages for which messages are available 
     * @param languages The list of available languages
     */
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    /**
     * The directory where custom message bundles can be located
     * @return The {@link File} denoting the directory with message bundles
     */
    public File getCustomMessageDirectory() {
        return customMessageDirectory;
    }

    /**
     * Set the directory where custom message bundles can be located
     * @param customMessageDirectory The {@link File} denoting the directory with message bundles
     */
    public void setCustomMessageDirectory(File customMessageDirectory) {
        this.customMessageDirectory = customMessageDirectory;
    }
}
