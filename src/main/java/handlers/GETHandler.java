package handlers;

import common.NodeManager;
import utils.ProtocolUtils;

import java.io.*;

public class GETHandler implements IHandler {

    @Override
    public int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException {
        String key = ProtocolUtils.readString(in);
        ProtocolUtils.writesMessage(out, NodeManager.DEFAULT_DB.get(key));
        return 1;
    }
}
