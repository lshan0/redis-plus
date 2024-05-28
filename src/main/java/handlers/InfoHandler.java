package handlers;

import common.NodeManager;
import utils.ProtocolUtils;

import java.io.*;

public class InfoHandler implements IHandler{

    private static final String REPLICATION = "replication";

    @Override
    public int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException {
        String infoTarget = ProtocolUtils.readString(in);

        if (REPLICATION.equalsIgnoreCase(infoTarget)) {
            ProtocolUtils.writesMessage(out, NodeManager.metaData.toString());
        }
        return 1;
    }
}
