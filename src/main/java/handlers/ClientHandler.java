package handlers;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static String PING = "PING";

    private static String ECHO = "ECHO";
    Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
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
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.charAt(0) == '*') {
                    int argNumber = Integer.parseInt(line.substring(1));
                    if (argNumber == 0) {
                        break;
                    }
                    String instruction = readArg(reader);
                    int readArg = 1;
                    if (PING.equalsIgnoreCase(instruction)) {
                        readArg += handlePING(writer);
                    } else if (ECHO.equalsIgnoreCase(instruction)) {
                        readArg += handleECHO(reader, writer);
                    }
                    if (readArg < argNumber) {
                        throw new IllegalStateException("There are some args that should be read");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int handlePING(BufferedWriter writer) throws IOException {
        writer.write("+PONG\r\n");
        writer.flush();
        return 0;

    }

    private String readArg(BufferedReader reader) throws IOException {
        String numberBytesLines = reader.readLine();
        if (numberBytesLines.charAt(0) != '$') {
            throw new IllegalStateException();
        }
        return reader.readLine();
    }

    private int handleECHO(BufferedReader reader, BufferedWriter writer) throws IOException{
        String message = readArg(reader);
        writer.write(toRedisProtocolMessage(message));
        writer.flush();
        return 1;
    }

    private String toRedisProtocolMessage(String message) {
        return "$" + message.length() + "\r\n" + message + "\r\n";
    }
}
