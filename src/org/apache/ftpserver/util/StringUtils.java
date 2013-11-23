package org.apache.ftpserver.util;

import java.util.Map;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * String utility methods.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class StringUtils {

    /**
     * This is a string replacement method.
     */
    public static final String replaceString(String source, String oldStr, String newStr) {
        StringBuilder sb = new StringBuilder(source.length());
        int sind = 0;
        int cind = 0;
        edu.hkust.clap.monitor.Monitor.loopBegin(1);
while ((cind = source.indexOf(oldStr, sind)) != -1) { 
edu.hkust.clap.monitor.Monitor.loopInc(1);
{
            sb.append(source.substring(sind, cind));
            sb.append(newStr);
            sind = cind + oldStr.length();
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(1);

        sb.append(source.substring(sind));
        return sb.toString();
    }

    /**
     * Replace string
     */
    public static final String replaceString(String source, Object[] args) {
        int startIndex = 0;
        int openIndex = source.indexOf('{', startIndex);
        if (openIndex == -1) {
            return source;
        }
        int closeIndex = source.indexOf('}', startIndex);
        if ((closeIndex == -1) || (openIndex > closeIndex)) {
            return source;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(source.substring(startIndex, openIndex));
        edu.hkust.clap.monitor.Monitor.loopBegin(2);
while (true) { 
edu.hkust.clap.monitor.Monitor.loopInc(2);
{
            String intStr = source.substring(openIndex + 1, closeIndex);
            int index = Integer.parseInt(intStr);
            sb.append(args[index]);
            startIndex = closeIndex + 1;
            openIndex = source.indexOf('{', startIndex);
            if (openIndex == -1) {
                sb.append(source.substring(startIndex));
                break;
            }
            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                sb.append(source.substring(startIndex));
                break;
            }
            sb.append(source.substring(startIndex, openIndex));
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(2);

        return sb.toString();
    }

    /**
     * Replace string.
     */
    public static final String replaceString(String source, Map<String, Object> args) {
        int startIndex = 0;
        int openIndex = source.indexOf('{', startIndex);
        if (openIndex == -1) {
            return source;
        }
        int closeIndex = source.indexOf('}', startIndex);
        if ((closeIndex == -1) || (openIndex > closeIndex)) {
            return source;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(source.substring(startIndex, openIndex));
        edu.hkust.clap.monitor.Monitor.loopBegin(3);
while (true) { 
edu.hkust.clap.monitor.Monitor.loopInc(3);
{
            String key = source.substring(openIndex + 1, closeIndex);
            Object val = args.get(key);
            if (val != null) {
                sb.append(val);
            }
            startIndex = closeIndex + 1;
            openIndex = source.indexOf('{', startIndex);
            if (openIndex == -1) {
                sb.append(source.substring(startIndex));
                break;
            }
            closeIndex = source.indexOf('}', startIndex);
            if ((closeIndex == -1) || (openIndex > closeIndex)) {
                sb.append(source.substring(startIndex));
                break;
            }
            sb.append(source.substring(startIndex, openIndex));
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(3);

        return sb.toString();
    }

    /**
         * This method is used to insert HTML block dynamically
         *
         * @param source the HTML code to be processes
         * @param bReplaceNl if true '\n' will be replaced by <br>
         * @param bReplaceTag if true '<' will be replaced by &lt; and 
         *                          '>' will be replaced by &gt;
         * @param bReplaceQuote if true '\"' will be replaced by &quot; 
         */
    public static final String formatHtml(String source, boolean bReplaceNl, boolean bReplaceTag, boolean bReplaceQuote) {
        StringBuilder sb = new StringBuilder();
        int len = source.length();
        edu.hkust.clap.monitor.Monitor.loopBegin(4);
for (int i = 0; i < len; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(4);
{
            char c = source.charAt(i);
            switch(c) {
                case '\"':
                    if (bReplaceQuote) sb.append("&quot;"); else sb.append(c);
                    break;
                case '<':
                    if (bReplaceTag) sb.append("&lt;"); else sb.append(c);
                    break;
                case '>':
                    if (bReplaceTag) sb.append("&gt;"); else sb.append(c);
                    break;
                case '\n':
                    if (bReplaceNl) {
                        if (bReplaceTag) sb.append("&lt;br&gt;"); else sb.append("<br>");
                    } else {
                        sb.append(c);
                    }
                    break;
                case '\r':
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(4);

        return sb.toString();
    }

    /**
     * Pad string object
     */
    public static final String pad(String src, char padChar, boolean rightPad, int totalLength) {
        int srcLength = src.length();
        if (srcLength >= totalLength) {
            return src;
        }
        int padLength = totalLength - srcLength;
        StringBuilder sb = new StringBuilder(padLength);
        edu.hkust.clap.monitor.Monitor.loopBegin(5);
for (int i = 0; i < padLength; ++i) { 
edu.hkust.clap.monitor.Monitor.loopInc(5);
{
            sb.append(padChar);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(5);

        if (rightPad) {
            return src + sb.toString();
        } else {
            return sb.toString() + src;
        }
    }

    /**
     * Get hex string from byte array
     */
    public static final String toHexString(byte[] res) {
        StringBuilder sb = new StringBuilder(res.length << 1);
        edu.hkust.clap.monitor.Monitor.loopBegin(6);
for (int i = 0; i < res.length; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(6);
{
            String digit = Integer.toHexString(0xFF & res[i]);
            if (digit.length() == 1) {
                sb.append('0');
            }
            sb.append(digit);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(6);

        return sb.toString().toUpperCase();
    }

    /**
     * Get byte array from hex string
     */
    public static final byte[] toByteArray(String hexString) {
        int arrLength = hexString.length() >> 1;
        byte buff[] = new byte[arrLength];
        edu.hkust.clap.monitor.Monitor.loopBegin(7);
for (int i = 0; i < arrLength; i++) { 
edu.hkust.clap.monitor.Monitor.loopInc(7);
{
            int index = i << 1;
            String digit = hexString.substring(index, index + 2);
            buff[i] = (byte) Integer.parseInt(digit, 16);
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(7);

        return buff;
    }
}
