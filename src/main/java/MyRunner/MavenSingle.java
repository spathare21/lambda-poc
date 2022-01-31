package MyRunner;

import java.net.URL;
import java.util.HashMap;

import cucumber.api.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.lambdatest.tunnel.Tunnel;

public class MavenSingle {
    Tunnel t;

    WebDriver driver = null;
    public static String status = "passed";

    String username = System.getenv("LT_USERNAME");
    String access_key = System.getenv("LT_ACCESS_KEY");


    @BeforeTest
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "Single Maven Tunnel");
        capabilities.setCapability("name", "Maven Tunnel");
//        capabilities.setCapability("platform", "Windows 10");
//        capabilities.setCapability("browserName", "Chrome");
//        capabilities.setCapability("version","latest");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "HTC 10");
        capabilities.setCapability("platformVersion","7");
        capabilities.setCapability("tunnel",true);
        capabilities.setCapability("network",true);
        capabilities.setCapability("console",true);
        capabilities.setCapability("visual",true);

        //create tunnel instance
        t = new Tunnel();
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user", username);
        options.put("key", access_key);

        //start tunnel
        System.out.println("--------------------------------");
        t.start(options);
        System.out.println("--------------------------------");
        driver = new RemoteWebDriver(new URL("http://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), capabilities);
        System.out.println("Started session");
    }

    @Test()
    public void testTunnel() throws Exception {
        //Check LocalHost on XAMPP
        driver.get("http://localhost.lambdatest.com");
        // Let's check that the item we added is added in the list.
        driver.get("https://google.com");

        driver.get("https://www.linkedin.com/login");

        WebElement username=driver.findElement(By.id("username"));
        WebElement password=driver.findElement(By.id("password"));
        WebElement login=driver.findElement(By.xpath("//button[text()='Sign in']"));

        username.sendKeys("example@gmail.com");
        password.sendKeys("password");
        login.click();

    }

    @AfterTest
    public void tearDown() throws Exception {
        ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
        driver.quit();
        //close tunnel
        t.stop();
    }

    @After
    public void tear(){
        System.out.println(" \n --------------running after scenario------------- \n ");
    }

}
