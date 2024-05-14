package handlers;

import utils.ArgUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ECHOHandler implements IHandler {

    @Override
    public int handle(BufferedReader reader, BufferedWriter writer) throws IOException {
        String message = ArgUtils.readArg(reader);
        writer.write(ArgUtils.toRedisProtocolMessage(message));
        return 1;
    }
}
