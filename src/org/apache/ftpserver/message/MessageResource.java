package org.apache.ftpserver.message;

import java.util.List;
import java.util.Map;

/**
 * This is message resource interface.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface MessageResource {

    /**
     * Get all the available languages.
     * @return A list of available languages
     */
    List<String> getAvailableLanguages();

    /**
     * Get the message for the corresponding code and sub id. If not found it
     * will return null.
     * @param code The reply code
     * @param subId The sub ID
     * @param language The language
     * @return The message matching the provided inputs, or null if not found
     */
    String getMessage(int code, String subId, String language);

    /**
     * Get all the messages.
     * @param language The language
     * @return All messages for the provided language
     */
    Map<String, String> getMessages(String language);
}
