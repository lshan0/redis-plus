package handlers;

import common.Info;
import common.NodeManager;
import utils.ProtocolUtils;

import java.io.*;
import java.util.Base64;

public class PSYNCHandler implements IHandler {
    @Override
    public int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException {
        String argMasterId = ProtocolUtils.readString(in);
        String argOffset = ProtocolUtils.readString(in);
        String masterId = (String) NodeManager.metaData.getInfo(Info.MASTER_REPLID);
        String offset = "0";
        out.write(String.format("+FULLRESYNC %s %s \r\n", masterId, offset).getBytes());

        if ("?".equalsIgnoreCase(argMasterId) && "-1".equalsIgnoreCase(argOffset)) {
            String emptyRDBB64 = "UkVESVMwMDEx+glyZWRpcy12ZXIFNy4yLjD6CnJlZGlzLWJpdHPAQPoFY3RpbWXCbQi8ZfoIdXNlZC1tZW3CsMQQAPoIYW9mLWJhc2XAAP/wbjv+wP9aog==";
            byte[] RDBBIN = Base64.getDecoder().decode(emptyRDBB64);
            ProtocolUtils.writesBytes(out, RDBBIN, false, false);
        }

        out.flush();
        return 2;
    }
}
