package handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class PINGHandler implements IHandler {

    @Override
    public int handle(BufferedReader reader, BufferedWriter writer, Integer remainedArgs) throws IOException {
        writer.write("+PONG\r\n");
        writer.flush();
        return 0;
    }
}
