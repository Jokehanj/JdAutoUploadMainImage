import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeHelp {
    public static WebDriver initChromeDrive(String url) {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setExperimentalOption("debuggerAddress", "127.0.0.1:19222");

        WebDriver chromeDrive = new ChromeDriver(chromeOptions);
        chromeDrive.get(url);

        return chromeDrive;
    }

    public static void checkElementForWait(By by, WebDriver chromeDrive) {

        WebDriverWait wait = new WebDriverWait(chromeDrive, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("wareId")));
    }
}
