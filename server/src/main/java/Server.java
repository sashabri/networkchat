import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final String SERVER_NAME = "СЕРВЕР";
    private static final List<ServerThreads> clientList = new CopyOnWriteArrayList<>();

    public static void sendMessageAll(Message message) {
        for (ServerThreads serverThreads : clientList) {
            serverThreads.sendMessage(message);
        }
    }

    public static void removeServerThreads(ServerThreads serverThreads) {
        clientList.remove(serverThreads);
    }

    public static void main(String[] args) throws IOException {
        SettingsReader settingsReader = SettingsReader.getInstance();
        int port = settingsReader.readPort();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запустился на порту " + port);
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();

                Message message = new Message(SERVER_NAME, "Подключился новый пользователь");
                sendMessageAll(message);

                try {
                    clientList.add(new ServerThreads(clientSocket));
                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        }
    }
}
