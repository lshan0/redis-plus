import common.NodeManager;
import runners.ClientHandlerRunner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 6379;

        List<String> argsList = new ArrayList<>(List.of(args));
        if (argsList.contains("--port")) {
            port = Integer.parseInt(argsList.get(argsList.indexOf("--port") + 1));
        }
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            NodeManager.initMetaData();
            // Wait for connection from client.
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (!serverSocket.isClosed()) {
                executorService.execute(new ClientHandlerRunner(serverSocket.accept()));
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
