package handlers;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    public void handle(Socket clientSocket) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream()));

        while (clientSocket.isConnected()) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.equalsIgnoreCase("PING")) {
                writer.write("+PONG\r\n");
                writer.flush();
            }
        }
    }
}
