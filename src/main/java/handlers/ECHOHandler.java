package handlers;

import utils.ProtocolUtils;

import java.io.*;

public class ECHOHandler implements IHandler {

    @Override
    public int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException {
        String message = ProtocolUtils.readString(in);
        ProtocolUtils.writesMessage(out, message);
        return 1;
    }
}
