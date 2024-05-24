package handlers;

import common.NodeManager;
import memory.CacheValue;
import utils.ArgUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SETHandler implements IHandler {
    private static final String PX = "PX";

    @Override
    public int handle(BufferedReader reader, BufferedWriter writer, Integer remainedArgs) throws IOException {
        String key = ArgUtils.readArg(reader);
        String value = ArgUtils.readArg(reader);
        Integer usedArgs = 2;
        int expirationTime = -1;
        if (remainedArgs > 2) {
            ++usedArgs;
            if (PX.equalsIgnoreCase(ArgUtils.readArg(reader))) {
                expirationTime = Integer.parseInt(ArgUtils.readArg(reader));
                ++usedArgs;
            }
        }
        NodeManager.DEFAULT_DB.set(key, new CacheValue(value, expirationTime));
        writer.write("+OK\r\n");
        writer.flush();
        return usedArgs;
    }
}
