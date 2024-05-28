package handlers;

import utils.ArgUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ReplConfigHandler implements IHandler {

    private static String LISTENING_PORT = "listening-port";
    private static String CAPA = "capa";


    @Override
    public int handle(BufferedReader reader, BufferedWriter writer, Integer remainedArgs) throws IOException {
        String configType = ArgUtils.readArg(reader);
        if (LISTENING_PORT.equalsIgnoreCase(configType)) {
            Integer replicaPort = Integer.valueOf(ArgUtils.readArg(reader));
        }

        if (CAPA.equalsIgnoreCase(configType)) {
            String capa = ArgUtils.readArg(reader);
        }

        writer.write("+OK\r\n");
        writer.flush();
        return 2;
    }
}
