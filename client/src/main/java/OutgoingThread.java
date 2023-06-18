import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class OutgoingThread extends Thread {
    private static Logger logger = Logger.getInstance();
    private final Socket socket;
    private final PrintWriter out;
    private final String name;

    public OutgoingThread(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name = name;
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!socket.isClosed()) {
            String messageFromConsole = scanner.nextLine();// получаем строку из консоли, которую ввёл пользовалель

            if ("/exit".equals(messageFromConsole)) {
                try {
                    closeSocket();
                } catch (IOException e) {
                    logger.saveMessage(e.getMessage());
                }
                break;
            }

            if (messageFromConsole != null && !messageFromConsole.equals("")) {
                Message message = new Message(name, messageFromConsole);// создать объект message
                logger.saveMessage(message.formatPrintToConsole());

                GsonBuilder builder = new GsonBuilder();// конвертировать message  в  json
                Gson gson = builder.create();

                out.println(gson.toJson(message));//  отправить json серверу
            }
        }
    }
    private void closeSocket() throws IOException {
        out.close();
        socket.close();
    }
}
