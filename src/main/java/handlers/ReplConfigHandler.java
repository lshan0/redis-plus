package handlers;

import utils.ProtocolUtils;

import java.io.*;

public class ReplConfigHandler implements IHandler {

    private static String LISTENING_PORT = "listening-port";
    private static String CAPA = "capa";


    @Override
    public int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException {
        Integer usedArgs = 2;
        String configType = ProtocolUtils.readString(in);
        if (LISTENING_PORT.equalsIgnoreCase(configType)) {
            Integer replicaPort = Integer.valueOf(ProtocolUtils.readString(in));
        }

        if (CAPA.equalsIgnoreCase(configType)) {
            String capa = ProtocolUtils.readString(in);
        }

        if (remainedArgs - 2 > 0) {
            usedArgs += handle(out, in, remainedArgs - 2);
        } else {
            out.write("+OK\r\n".getBytes());
            out.flush();
        }
        return usedArgs;
    }
}
