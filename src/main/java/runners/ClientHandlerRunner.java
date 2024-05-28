package runners;

import handlers.HandlerFactory;
import utils.ProtocolUtils;

import java.io.*;
import java.net.Socket;


public class ClientHandlerRunner implements Runnable {

    Socket clientSocket;

    public ClientHandlerRunner(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()));
            while (clientSocket.isConnected()) {
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();
                String line = ProtocolUtils.readStringLine(in);
                if (line == null) {
                    break;
                }
                if (line.charAt(0) == '*') {
                    int argNumber = Integer.parseInt(line.substring(1));
                    if (argNumber == 0) {
                        break;
                    }
                    String instruction = ProtocolUtils.readString(in);
                    int readArg = 1;
                    readArg += HandlerFactory.getInstance().createHandler(instruction).handle(out, in,argNumber - readArg);
                    if (readArg < argNumber) {
                        throw new IllegalStateException("There are some args that should be read");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}