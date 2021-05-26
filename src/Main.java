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

            // 如果出错，尝试5次
            for (int i = 0; i < 5; i++) {
                try {
                    doUploadItem(file);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();

                    if (i == 4) {
                        System.out.println("=========================================失败啦---》" + file.getName());
                    }
                }
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

        String simplename = washSimpleName(file);

        if (simplename.isEmpty()) {
            return;
        }

        wareIdElement.sendKeys(simplename);

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

        // 编辑后可能出现的弹框保护
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

        // 选择定时功能
        By isPublishSchedule = By.id("isPublishSchedule");
        ChromeHelp.checkElementForWait(isPublishSchedule, chromeDrive);
        chromeDrive.findElement(isPublishSchedule).click();

        try {
            // 通过js来移除readonly属性
            String removeAttrReadonly = "document.getElementById('publishTime').removeAttribute('readonly');";
            ((JavascriptExecutor) chromeDrive).executeScript(removeAttrReadonly);
            String removeAttrOnfocus= "document.getElementById('publishTime').removeAttribute('onfocus');";
            ((JavascriptExecutor) chromeDrive).executeScript(removeAttrOnfocus);

            try {
                Thread.sleep(100);
            } catch (InterruptedException k) {
                k.printStackTrace();
            }

            chromeDrive.findElement(By.id("publishTime")).sendKeys("2021-07-08 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();

            try {
                Thread.sleep(100);
            } catch (InterruptedException k) {
                k.printStackTrace();
            }

            // 通过js来移除readonly属性
            String removeAttrReadonly = "document.getElementById('publishTime').removeAttribute('readonly');";
            ((JavascriptExecutor) chromeDrive).executeScript(removeAttrReadonly);
            String removeAttrOnfocus= "document.getElementById('publishTime').removeAttribute('onfocus');";
            ((JavascriptExecutor) chromeDrive).executeScript(removeAttrOnfocus);

            // 获取js执行器
            JavascriptExecutor js = (JavascriptExecutor) chromeDrive;
            String script = "document.getElementById('publishTime').value='2021-07-08 00:00:00';";
            js.executeScript(script);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException p) {
                p.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chromeDrive.findElement(By.className("webuploader-element-invisible")).sendKeys(file.getAbsolutePath());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 上传成功弹框点击
        try {
            By dialogConfirm = By.xpath("//*[@id=\"system_alert\"]/div[3]/button/span[2]");

            ChromeHelp.checkElementForWait(dialogConfirm, chromeDrive);

            chromeDrive.findElement(dialogConfirm).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 20; i > 0; i--) {

            try {
                chromeDrive.findElements(By.className("heart-img")).get(i).click();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String washSimpleName(File file) {
        String simpleName = file.getName();

        String[] split = simpleName.split("-");

        if (split.length != 0) {
            return split[0];
        }

        return "";
    }
}