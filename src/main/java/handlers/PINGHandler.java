package handlers;

import java.io.*;

public class PINGHandler implements IHandler {

    @Override
    public int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException {
        out.write("+PONG\r\n".getBytes());
        out.flush();
        return 0;

    }
}
