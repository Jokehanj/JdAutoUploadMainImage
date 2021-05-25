import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.File;

public class Main {

    private static WebDriver chromeDrive;

    public static void main(String[] args) {

        chromeDrive = ChromeHelp.initChromeDrive("https://vcp.jd.com");

        // 打开商品管理

        By manager = By.xpath("/html/body/section/div/header/nav[2]/div[1]/div/div/div/div[3]/a");

        chromeDrive.findElement(manager).click();

        String fileDir = "/Users/hanji/Downloads/uploads";

        File uploadDir = new File(fileDir);
        if (uploadDir.listFiles() == null) {
            return;
        }

        for (File file : uploadDir.listFiles()) {

            if (file.getName().contains(".DS_Store")) {
                continue;
            }

            try {
                doUploadItem(file);
            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("=========================================失败啦---》" + file.getName());
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void doUploadItem(File file) {

        chromeDrive.findElement(By.id("M-ITEM-DRAFT")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        By wareId = By.id("wareId");

        ChromeHelp.checkElementForWait(wareId, chromeDrive);

        WebElement wareIdElement = chromeDrive.findElement(wareId);

        wareIdElement.clear();
        wareIdElement.sendKeys(file.getName().replace(".jpg", ""));

        // 点击查找
        chromeDrive.findElement(By.xpath("//*[@id=\"serchFrom\"]/table/tbody/tr[2]/td/input[1]")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        By editBtn = By.xpath("//*[@id=\"td_draftId\"]/div/button[1]");

        ChromeHelp.checkElementForWait(editBtn, chromeDrive);

        // 点击编辑
        chromeDrive.findElement(editBtn).click();

        try {
            By dialogConfirm = By.xpath("//*[@id=\"system_confirm\"]/div[3]/button[2]/span[2]");

            ChromeHelp.checkElementForWait(dialogConfirm, chromeDrive);

            chromeDrive.findElement(dialogConfirm).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 点击下一页
        By nextPage = By.id("cid3Submit");
        ChromeHelp.checkElementForWait(nextPage, chromeDrive);
        chromeDrive.findElement(nextPage).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 点击主图信息
        By mainImageInfo = By.id("dd3");
        chromeDrive.findElement(mainImageInfo).click();

        By isPublishSchedule = By.id("isPublishSchedule");

        ChromeHelp.checkElementForWait(isPublishSchedule, chromeDrive);

        chromeDrive.findElement(isPublishSchedule).click();

        try {
            chromeDrive.findElement(By.id("publishTime")).sendKeys("2021-07-08 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
            // 获取js执行器
            JavascriptExecutor js = (JavascriptExecutor) chromeDrive;
            String script = "document.getElementById('publishTime').value='2021-07-08 00:00:00';";
            js.executeScript(script);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chromeDrive.findElement(By.className("webuploader-element-invisible")).sendKeys(file.getAbsolutePath());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chromeDrive.findElement(By.xpath("//*[@id=\"img-widget-2\"]/div[2]/img[4]")).click();
    }
}