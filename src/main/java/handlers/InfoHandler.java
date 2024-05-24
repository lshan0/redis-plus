package handlers;

import common.NodeManager;
import utils.ArgUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class InfoHandler implements IHandler{

    private static final String REPLICATION = "replication";

    @Override
    public int handle(BufferedReader reader, BufferedWriter writer, Integer remainedArgs) throws IOException {
        String infoTarget = ArgUtils.readArg(reader);
        if (REPLICATION.equalsIgnoreCase(infoTarget)) {
            writer.write(
                    ArgUtils.toRedisProtocolMessage(NodeManager.metaData.toString()));
        }
        writer.flush();
        return 1;
    }
}
