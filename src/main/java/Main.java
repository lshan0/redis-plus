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
        checkMaster(writer, reader);
        replConfigStep(writer, reader);
        psyncMasterStep(writer, reader);
    }

    private static void checkMaster(BufferedWriter writer, BufferedReader reader) throws IOException {
        String reponseLine;
        reponseLine = sendPackage(writer, reader, ArgUtils.toPackage(List.of("PING")));
        assert reponseLine.equalsIgnoreCase("+PONG");
    }

    private static String sendPackage(BufferedWriter writer, BufferedReader reader, String message) throws IOException {
        String responseLine;
        writer.write(message);
        writer.flush();
        
        responseLine = reader.readLine();
        assert "+OK".equalsIgnoreCase(responseLine);
        return responseLine;
    }

    private static void replConfigStep(BufferedWriter writer, BufferedReader reader) throws IOException {
        String reponseLine;
        reponseLine = sendPackage(writer, reader, ArgUtils.toPackage(List.of("REPLCONF", "listening-port", String.valueOf(port))));
        assert "+OK".equalsIgnoreCase(reponseLine);

        reponseLine = sendPackage(writer, reader, ArgUtils.toPackage(List.of("REPLCONF", "capa", "psync2")));
        assert "+OK".equalsIgnoreCase(reponseLine);
    }

    private static void psyncMasterStep(BufferedWriter writer, BufferedReader reader) throws IOException {
        String responseLine;
        responseLine = sendPackage(writer, reader, ArgUtils.toPackage(List.of("PSYNC", "?", "-1")));

        String[] values = responseLine.split(" +");
        NodeManager.metaData.setInfo(Info.MASTER_REPLID, values[1]);
        NodeManager.metaData.setInfo(Info.MASTER_REPL_OFFSET, values[2]);
    }
}
