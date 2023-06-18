import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String FILE_NAME = "file.log";
    private static Logger logger;

    private Logger() {
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
            return logger;
        }
        return logger;
    }

    public void saveMessage(String message) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(message);
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
