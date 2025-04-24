package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT_NUMBER = 6666;
    private static ServerSocket serverSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;
    private static List<Socket> currentSockets = new ArrayList<>();

    public static void start() throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("Server started");
        while (true) {
            for (Socket socket : currentSockets) {
                if(socket.isClosed()){
                    currentSockets.remove(socket);
                    continue;
                }

            }
            Socket socket = serverSocket.accept();
            String socketInfo = "Клиент " + socket.getInetAddress() + ":" + socket.getPort() + " подключен.";
            System.out.println(socketInfo);
            currentSockets.add(socket);
            clientHandler = new ClientHandler(socket);
            thread = new Thread(clientHandler);
            thread.start();
            System.out.flush();
        }
    }

    protected void finalize() throws IOException {
        serverSocket.close();
    }
}
