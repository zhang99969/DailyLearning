import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SampleTest {

    private AndroidDriver driver;
    private static AppiumDriver<AndroidElement> driver2;
    @Before
    public void setUp() throws MalformedURLException {//for ApiDemos的app
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "Google");
        desiredCapabilities.setCapability("appPackage", "io.appium.android.apis");
        desiredCapabilities.setCapability("appActivity", ".ApiDemos");
        //desiredCapabilities.setCapability("app", "E:\\test\\ApiDemos-debug.apk");
        desiredCapabilities.setCapability("automationName", "uiautomator2");  //special for toast 只在测试toast时取消注释
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver(remoteUrl, desiredCapabilities); //for normal test
       // driver2 = new AppiumDriver<>(remoteUrl, desiredCapabilities); //special for testwebview 只在测试webveiw时取消注释
    }
//两个driver似乎不能同时出现

    public void setUpForToast() throws MalformedURLException {//for ApiDemos的app
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "Google");
        desiredCapabilities.setCapability("appPackage", "io.appium.android.apis");
        desiredCapabilities.setCapability("appActivity", ".ApiDemos");
        desiredCapabilities.setCapability("automationName", "uiautomator2");  //special for toast 只在测试toast时取消注释
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver(remoteUrl, desiredCapabilities); //for normal test
    }
    public void setUpForWebview() throws MalformedURLException {//for ApiDemos的app
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "Google");
        desiredCapabilities.setCapability("appPackage", "io.appium.android.apis");
        desiredCapabilities.setCapability("appActivity", ".ApiDemos");
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");
        driver2 = new AppiumDriver<>(remoteUrl, desiredCapabilities); //special for testwebview 只在测试webveiw时取消注释
    }

    public void setUpOneyuan() throws MalformedURLException {//for OneYuan一元夺宝的app
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "Google");
        desiredCapabilities.setCapability("app", "E:\\test\\1.apk");
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
    }
    public void setUpforXueqiu() throws MalformedURLException {//for 雪球的app
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "Google");
        desiredCapabilities.setCapability("appPackage", "com.xueqiu.android");
        desiredCapabilities.setCapability("appActivity", ".view.WelcomeActivityAlias");
        desiredCapabilities.setCapability("automationName", "appium");
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
    }
    @Test
    public void testToast1() throws InterruptedException {//对应ApiDemos的app 对应setUpForToast()函数
        Thread.sleep(1000);
        driver.findElementByAccessibilityId("Views").click();
        Thread.sleep(1000);
     (new TouchAction(driver)).press(PointOption.point(500, 1500)).moveTo(PointOption.point(500, 200)).release().perform();
        driver.findElementByAccessibilityId("Popup Menu").click();
        driver.findElementByAccessibilityId("Make a Popup!").click();
      //  driver.findElementByXPath("//*[contains(@text,'MAKE')]").click();
        Thread.sleep(1000);
        driver.findElementByXPath("//*[contains(@text,'Search')]").click();
        Thread.sleep(500);
        for (int i=0;i<5;i++) {
           // System.out.println(driver.findElementByXPath("//*[@class='android.widget.Toast']").getText());
            int size=driver.findElementsByXPath("//*[@class='android.widget.Toast']").size();
            System.out.println(size);
            System.out.println(driver.findElementByXPath("//*[@class='android.widget.Toast']").getText());
            Thread.sleep(500);
        }
    }

    @Test
    public void testToast2() throws InterruptedException {//对应ApiDemos的app 对应setUpForToast()函数
        WebDriverWait wait = new WebDriverWait(driver,2);
        driver.findElementByAccessibilityId("Views").click();
        Thread.sleep(1000);
        (new TouchAction(driver)).press(PointOption.point(500, 1500)).moveTo(PointOption.point(500, 200)).release().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@content-desc='Popup Menu']"))).click();
        driver.findElementByAccessibilityId("Make a Popup!").click();
        Thread.sleep(1000);
        driver.findElementByXPath("//*[contains(@text,'Search')]").click();
        Thread.sleep(500);
        String toastXPath="//*[@package='com.android.settings']";
        System.out.println(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(toastXPath))).getText());
        System.out.println(driver.findElementByXPath(toastXPath).getText());
        for (int i=0;i<5;i++) {
            int size=driver.findElementsByXPath(toastXPath).size();
            System.out.println(size);
            Thread.sleep(500);
        }
    }

    @Test
    public void testToast3() throws InterruptedException {//对应ApiDemos的app 对应setUpForToast()函数
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,2);
       // Dimension screenSize = driver.manage().window().getSize();
        driver.findElementByAccessibilityId("Views").click();
        Thread.sleep(1000);
        (new TouchAction(driver)).press(PointOption.point(500, 1500)).moveTo(PointOption.point(500, 200)).release().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@content-desc='Popup Menu']"))).click();
        Thread.sleep(1000);
        driver.findElementByAccessibilityId("Make a Popup!").click();
        Thread.sleep(1000);
        driver.findElementByXPath("//*[contains(@text,'Search')]").click();
        String toastXPath="//*[@class='android.widget.Toast']";
        //System.out.println(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(toastXPath))));
        System.out.println(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(toastXPath))).getText());
        System.out.println(driver.findElementByXPath(toastXPath).getText());
        for (int i=0;i<5;i++) {
            int size=driver.findElementsByXPath(toastXPath).size();
            System.out.println(size);
            Thread.sleep(500);
        }
    }

    @Test
    public void testPerformance() throws Exception {//测试性能 对应OneYuan一元夺宝的app 对应setUpOneyuan()函数
        System.out.println(driver.getPerformanceData("com.example.mynewapplication","memoryinfo",10));
        System.out.println(driver.getPerformanceData("com.example.mynewapplication","cpuinfo",10));
        System.out.println(driver.getPerformanceData("com.example.mynewapplication","batteryinfo",10));
        System.out.println(driver.getPerformanceData("com.example.mynewapplication","networkinfo",10));
    }

    @Test
    public void testWebView() throws InterruptedException {//对应ApiDemos的app 对应setUpForWebview()函数
        driver2.findElementByAccessibilityId("Views").click();
        Thread.sleep(1000);
        AndroidElement list = driver2.findElement(By.id("android:id/list"));
       // WebElement list = driver.findElement(By.id("android:id/list"));
        MobileElement webview = list
                .findElement(MobileBy
                        .AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                                + "new UiSelector().text(\"WebView\"));"));
        webview.click();
        System.out.println(driver2.getContextHandles());
        for(AndroidElement e: driver2.findElementsByXPath("//*")){
            System.out.println(e.getTagName());
            System.out.println(e.getText());
        }
    }

    @Test
    public void rolldown() throws InterruptedException{//实现向下滚动 对应ApiDemos的app 可以对应setUp()函数
        driver.findElementByAccessibilityId("Views").click();
        Thread.sleep(1000);
        WebElement list = driver.findElement(By.id("android:id/list"));
        MobileElement Rool = list.findElement(MobileBy.AndroidUIAutomator
                ("new UiScrollable(new UiSelector()).scrollIntoView("+
                "new UiSelector().text(\"WebView\"));"));
        Rool.click();
        Thread.sleep(10000);
    }


    @After
    public void tearDown() {
        driver.quit();
    }

}
