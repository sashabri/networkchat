import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Введите имя ");

        Scanner scanner = new Scanner(System.in);
        String nameClient = scanner.nextLine();

        SettingsReader settingsReader = new SettingsReader();
        int port = settingsReader.readPort();
        String host = "localhost";

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Вы подключились к серверу " + host + ":" + port + " под именем " + nameClient);

            IncomingThread incomingThread = new IncomingThread(socket);
            incomingThread.start();

            OutgoingThread outgoingThread = new OutgoingThread(socket, nameClient);
            outgoingThread.start();

            incomingThread.join();
            outgoingThread.join();
        }
    }
}
