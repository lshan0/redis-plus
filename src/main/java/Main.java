import common.Info;
import common.NodeManager;
import common.Role;
import runners.ClientHandlerRunner;
import utils.ArgUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static int port = 6379;

    public static void main(String[] args) throws IOException {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        List<String> argsList = new ArrayList<>(List.of(args));
        if (argsList.contains("--port")) {
            port = Integer.parseInt(argsList.get(argsList.indexOf("--port") + 1));
        }
        initNode(argsList);
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
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

    private static void initNode(List<String> argsList) throws IOException{
        if (argsList.contains("--replicaof")) {
            String masterHostAndPort = argsList.get(argsList.indexOf("--replicaof") + 1);
            String[] hostAndPort = masterHostAndPort.split(" +");
            try (Socket masterConnection = new Socket(hostAndPort[0], Integer.parseInt(hostAndPort[1]))) {
                handshakeMaster(masterConnection);
            }
            NodeManager.metaData.setInfo(Info.ROLE, Role.SLAVE);
        } else {
            NodeManager.metaData.setInfo(Info.ROLE, Role.MASTER);
            NodeManager.metaData.setInfo(Info.MASTER_REPLID, "8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb");
            NodeManager.metaData.setInfo(Info.MASTER_REPL_OFFSET, "0");
        }
    }

    private static void handshakeMaster(Socket masterConnection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(masterConnection.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(masterConnection.getOutputStream()));
        String responseLine = null;
        writer.write(ArgUtils.toPackage(List.of("PING")));
        writer.flush();
        responseLine = reader.readLine();
        assert responseLine.equalsIgnoreCase("+PONG");

        writer.write(ArgUtils.toPackage(List.of("REPLCONF", "listening-port", "6380")));
        writer.flush();
        responseLine = reader.readLine();
        assert "+OK".equalsIgnoreCase(responseLine);

        writer.write(ArgUtils.toPackage(List.of("REPLCONF", "capa", "psync2")));
        writer.flush();
        responseLine = reader.readLine();
        assert "+OK".equalsIgnoreCase(responseLine);
    }
}
