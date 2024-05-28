package handlers;

import common.NodeManager;
import memory.CacheValue;
import utils.ProtocolUtils;

import java.io.*;

public class SETHandler implements IHandler {
    private static final String PX = "PX";

    @Override
    public int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException {
        String key = ProtocolUtils.readString(in);
        String value = ProtocolUtils.readString(in);
        Integer usedArgs = 2;
        int expirationTime = -1;
        if (remainedArgs > 2) {
            ++usedArgs;
            if (PX.equalsIgnoreCase(ProtocolUtils.readString(in))) {
                expirationTime = Integer.parseInt(ProtocolUtils.readString(in));
                ++usedArgs;
            }
        }
        NodeManager.DEFAULT_DB.set(key, new CacheValue(value, expirationTime));
        out.write("+OK\r\n".getBytes());
        out.flush();
        return usedArgs;
    }
}
