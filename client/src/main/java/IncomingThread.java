import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IncomingThread extends Thread {

    private static Logger logger = Logger.getInstance();
    private final Socket socket;
    private final BufferedReader reader;

    public IncomingThread(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String text = reader.readLine();//получить от сокета сообщения

                GsonBuilder builder = new GsonBuilder();//преобразовать джсон в месседж
                Gson gson = builder.create();

                try {
                    Message message = gson.fromJson(text, Message.class);
                    logger.saveMessage(message.formatPrintToConsole());

                    String messageForConsole = message.formatPrintToConsole();//каким-то образом преобразовать в сообщение для консоли

                    System.out.println(messageForConsole);//отправить в консоль
                } catch (JsonSyntaxException e) {
                    logger.saveMessage(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.saveMessage(e.getMessage());
        }
    }
}
