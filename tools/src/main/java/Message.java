import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String name;
    private String text;
    private String time;
    public Message(String name, String text) {
        this.name = name;
        this.text = text;
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
        this.time = formatter.format(new Date());
    }
    public String getTime() {
        return time;
    }

    public String formatPrintToConsole() {
        return "[" + time + "] " + "[ " + name + " ] " + text;
    }
}
