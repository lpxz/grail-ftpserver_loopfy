package org.apache.ftpserver.command.impl.listing;

import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.util.DateUtils;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Formats files according to the MLST specification
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class MLSTFileFormater implements FileFormater {

    private static final String[] DEFAULT_TYPES = new String[] { "Size", "Modify", "Type" };

    private static final char[] NEWLINE = { '\r', '\n' };

    private String[] selectedTypes = DEFAULT_TYPES;

    /**
     * @param selectedTypes
     *            The types to show in the formated file
     */
    public MLSTFileFormater(String[] selectedTypes) {
        if (selectedTypes != null) {
            this.selectedTypes = selectedTypes.clone();
        }
    }

    /**
     * @see FileFormater#format(FtpFile)
     */
    public String format(FtpFile file) {
        StringBuilder sb = new StringBuilder();
        edu.hkust.clap.monitor.Monitor.loopBegin(33);
for (int i = 0; i < selectedTypes.length; ++i) { 
edu.hkust.clap.monitor.Monitor.loopInc(33);
{
            String type = selectedTypes[i];
            if (type.equalsIgnoreCase("size")) {
                sb.append("Size=");
                sb.append(String.valueOf(file.getSize()));
                sb.append(';');
            } else if (type.equalsIgnoreCase("modify")) {
                String timeStr = DateUtils.getFtpDate(file.getLastModified());
                sb.append("Modify=");
                sb.append(timeStr);
                sb.append(';');
            } else if (type.equalsIgnoreCase("type")) {
                if (file.isFile()) {
                    sb.append("Type=file;");
                } else if (file.isDirectory()) {
                    sb.append("Type=dir;");
                }
            } else if (type.equalsIgnoreCase("perm")) {
                sb.append("Perm=");
                if (file.isReadable()) {
                    if (file.isFile()) {
                        sb.append('r');
                    } else if (file.isDirectory()) {
                        sb.append('e');
                        sb.append('l');
                    }
                }
                if (file.isWritable()) {
                    if (file.isFile()) {
                        sb.append('a');
                        sb.append('d');
                        sb.append('f');
                        sb.append('w');
                    } else if (file.isDirectory()) {
                        sb.append('f');
                        sb.append('p');
                        sb.append('c');
                        sb.append('m');
                    }
                }
                sb.append(';');
            }
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(33);

        sb.append(' ');
        sb.append(file.getName());
        sb.append(NEWLINE);
        return sb.toString();
    }
}
