package org.apache.ftpserver.listener.nio;

import java.nio.charset.Charset;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineDecoder;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * Factory for creating decoders and encoders
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class FtpServerProtocolCodecFactory implements ProtocolCodecFactory {

    private ProtocolDecoder decoder = new TextLineDecoder(Charset.forName("UTF-8"));

    private ProtocolEncoder encoder = new FtpResponseEncoder();

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }
}
