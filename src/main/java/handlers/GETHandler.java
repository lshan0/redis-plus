package handlers;

import memory.DatabaseManager;
import utils.ArgUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GETHandler implements IHandler {

    @Override
    public int handle(BufferedReader reader, BufferedWriter writer) throws IOException {
        String key = ArgUtils.readArg(reader);
        writer.write(ArgUtils.toRedisProtocolMessage(DatabaseManager.DEFAULT_DB.get(key)));
        writer.flush();
        return 1;
    }
}
