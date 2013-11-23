package org.apache.ftpserver.util;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * This is a simplified regular character mattching class. Supports *?^[]-
 * pattern characters.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class RegularExpr {

    private char[] pattern;

    /**
     * Constructor.
     * 
     * @param pattern
     *            regular expression
     */
    public RegularExpr(String pattern) {
        this.pattern = pattern.toCharArray();
    }

    /**
     * Compare string with a regular expression.
     */
    public boolean isMatch(String name) {
        if ((pattern.length == 1) && (pattern[0] == '*')) {
            return true;
        }
        return isMatch(name.toCharArray(), 0, 0);
    }

    /**
     * Is a match?
     */
    private boolean isMatch(char[] strName, int strIndex, int patternIndex) {
//        edu.hkust.clap.monitor.Monitor.loopBegin(56);
while (true) { 
//edu.hkust.clap.monitor.Monitor.loopInc(56);
{
            if (patternIndex >= pattern.length) {
                return strIndex == strName.length;
            }
            char pc = pattern[patternIndex++];
            switch(pc) {
                case '[':
                    if (strIndex >= strName.length) {
                        return false;
                    }
                    char fc = strName[strIndex++];
                    char lastc = 0;
                    boolean bMatch = false;
                    boolean bNegete = false;
                    boolean bFirst = true;
                    edu.hkust.clap.monitor.Monitor.loopBegin(57);
while (true) { 
edu.hkust.clap.monitor.Monitor.loopInc(57);
{
                        if (patternIndex >= pattern.length) {
                            return false;
                        }
                        pc = pattern[patternIndex++];
                        if (pc == ']') {
                            if (bFirst) {
                                bMatch = true;
                            }
                            break;
                        }
                        if (bMatch) {
                            continue;
                        }
                        if ((pc == '^') && bFirst) {
                            bNegete = true;
                            continue;
                        }
                        bFirst = false;
                        if (pc == '-') {
                            if (patternIndex >= pattern.length) {
                                return false;
                            }
                            pc = pattern[patternIndex++];
                            bMatch = (fc >= lastc) && (fc <= pc);
                            lastc = pc;
                        } else {
                            lastc = pc;
                            bMatch = (pc == fc);
                        }
                    }} 
edu.hkust.clap.monitor.Monitor.loopEnd(57);

                    if (bNegete) {
                        if (bMatch) {
                            return false;
                        }
                    } else {
                        if (!bMatch) {
                            return false;
                        }
                    }
                    break;
                case '*':
                    if (patternIndex >= pattern.length) {
                        return true;
                    }
                    do {
                        if (isMatch(strName, strIndex++, patternIndex)) {
                            return true;
                        }
                    } while (strIndex < strName.length);
                    return false;
                case '?':
                    if (strIndex >= strName.length) {
                        return false;
                    }
                    strIndex++;
                    break;
                default:
                    if (strIndex >= strName.length) {
                        return false;
                    }
                    if (strName[strIndex++] != pc) {
                        return false;
                    }
                    break;
            }
        }} 
//edu.hkust.clap.monitor.Monitor.loopEnd(56);

    }
}
