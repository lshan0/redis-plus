package handlers;

import common.Info;
import common.NodeManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class PSYNCHandler implements IHandler {
    @Override
    public int handle(BufferedReader reader, BufferedWriter writer, Integer remainedArgs) throws IOException {
        String masterId = (String) NodeManager.metaData.getInfo(Info.MASTER_REPLID);
        String offset = "0";
        writer.write(String.format("+FULLRESYNC %s %s \r\n", masterId, offset));
        writer.flush();
        return 2;
    }
}
