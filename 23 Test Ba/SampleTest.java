import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
public class SampleTest {

    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "Google");
        desiredCapabilities.setCapability("app", "E:\\test\\1.apk");

        URL remoteUrl = new URL("http://localhost:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
    }

    @Test
    public void sampleTest() {
        MobileElement el1 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Login_UserName");
        el1.click();
        el1.sendKeys("123");
        (new TouchAction(driver)).tap(PointOption.point(1005, 1682)).perform();
        MobileElement el2 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Login_UserPassword");
        el2.sendKeys("123");
        (new TouchAction(driver)).tap(PointOption.point(1005, 1672)).perform();
        MobileElement el3 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Login_button_register");
        el3.click();
        MobileElement el4 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_UserName");
        el4.sendKeys("123");
        MobileElement el5 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_UserPassword");
        el5.click();
        el5.sendKeys("123");
        MobileElement el6 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_UserName_real");
        el6.click();
        el6.sendKeys("123");
        MobileElement el7 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_UserPhone");
        el7.sendKeys("123123123");
        MobileElement el8 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_radioButton1");
        el8.click();
        (new TouchAction(driver)).tap(PointOption.point(994, 1679)).perform();
        MobileElement el9 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_button_confirm");
        el9.click();
        MobileElement el10 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_UserPhone");
        el10.sendKeys("12312312312");
        el10.clear();
        el10.sendKeys("12312312312");
        (new TouchAction(driver)).tap(PointOption.point(955, 1707)).perform();
        (new TouchAction(driver)).tap(PointOption.point(1019, 1697)).perform();
        MobileElement el11 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_button_confirm");
        el11.click();
        (new TouchAction(driver)).tap(PointOption.point(980, 1693)).perform();
        MobileElement el12 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_UserPassword");
        el12.click();
        el12.clear();
        el12.sendKeys("12312312312");
        (new TouchAction(driver)).tap(PointOption.point(1009, 1682)).perform();
        (new TouchAction(driver)).tap(PointOption.point(1009, 1679)).perform();
        (new TouchAction(driver)).tap(PointOption.point(966, 1679)).perform();
        (new TouchAction(driver)).tap(PointOption.point(991, 1682)).perform();
        MobileElement el13 = (MobileElement) driver.findElementById("com.example.mynewapplication:id/Register_button_confirm");
        el13.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
