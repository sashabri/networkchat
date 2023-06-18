import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SettingsReader {
    private static final String FILE_NAME = "settings.txt";
    private static SettingsReader settingsReader;

    SettingsReader() {
    }

    public static SettingsReader getInstance() {
        if (settingsReader == null) {
            settingsReader = new SettingsReader();
            return settingsReader;
        }
        return settingsReader;
    }

    public int readPort() throws IOException {
        int result = 0;

        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String a = "";
        while ((a = br.readLine()) != null) {
            result = Integer.parseInt(a);
        }

        return result;
    }
}
