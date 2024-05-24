package handlers;

import common.NodeManager;
import utils.ArgUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GETHandler implements IHandler {

    @Override
    public int handle(BufferedReader reader, BufferedWriter writer, Integer remainedArgs) throws IOException {
        String key = ArgUtils.readArg(reader);
        writer.write(ArgUtils.toRedisProtocolMessage(NodeManager.DEFAULT_DB.get(key)));
        writer.flush();
        return 1;
    }
}
