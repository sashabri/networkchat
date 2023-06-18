import org.junit.Assert;
import org.junit.Test;

public class MessageTest {
    @Test
    public void formatPrintToConsoleTest(){
        Message testObj =new Message("Anya", "Hi");

        String expected = "[" + testObj.getTime() + "] [ Anya ] Hi";

        String actual = testObj.formatPrintToConsole();

        Assert.assertEquals(expected, actual);
    }
}
