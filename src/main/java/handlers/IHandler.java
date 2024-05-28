package handlers;

import java.io.*;

public interface IHandler {


    int handle(OutputStream out, InputStream in, Integer remainedArgs) throws IOException;

}