package handlers;

import memory.DatabaseManager;
import utils.ArgUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SETHandler implements IHandler {

    @Override
    public int handle(BufferedReader reader, BufferedWriter writer) throws IOException {
        String key = ArgUtils.readArg(reader);
        String value = ArgUtils.readArg(reader);
        DatabaseManager.DEFAULT_DB.set(key, value);
        writer.write("+OK\r\n");
        writer.flush();
        return 2;
    }
}
