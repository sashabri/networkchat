import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

public class ServerThreads extends Thread {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private static Logger logger = Logger.getInstance();

    public ServerThreads(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String message = reader.readLine(); // получаем сообщения от клиента
                logger.saveMessage(message);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    Message msg = gson.fromJson(message, Message.class);
                    Server.sendMessageAll(msg);
                } catch (Exception e) {
                    logger.saveMessage(e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.saveMessage(e.getMessage());
        }
        Server.removeServerThreads(this);
    }

    public void sendMessage(Message msg) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            writer.write(gson.toJson(msg) + "\n");
            writer.flush();
        } catch (IOException e) {
            logger.saveMessage(e.getMessage());
        }
    }
}

