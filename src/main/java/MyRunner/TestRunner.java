package MyRunner;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = "src/main/java/Features",
        glue = {"stepDefinitions"},
        tags = {"~@Ignore"},
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class TestRunner {
	
    private TestNGCucumberRunner testNGCucumberRunner;
  
    public static RemoteWebDriver connection;
    
    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
    	 testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "version", "platform" })
    public void setUpClass(String browser, String version, String platform) throws Exception {

    		String username = System.getenv("LT_USERNAME") == null ? "sachin.pathare" : System.getenv("LT_USERNAME");
    		String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "lrmS8Waqgx9broX5WoTETPeIxBYRwvbnVBZnK4k2XZ02hS2Z9I" : System.getenv("LT_ACCESS_KEY");

    		DesiredCapabilities capability = new DesiredCapabilities();    		
    		capability.setCapability(CapabilityType.BROWSER_NAME, browser);
    		capability.setCapability(CapabilityType.VERSION,version);
    		capability.setCapability(CapabilityType.PLATFORM, platform);
    		    		
    		capability.setCapability("build", "Cucumber Sample Build");
    		
    		capability.setCapability("network", true);
    		capability.setCapability("video", true);
    		capability.setCapability("console", true);
    		capability.setCapability("visual", true);

    		String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
    		System.out.println(gridURL);
    		connection = new RemoteWebDriver(new URL(gridURL), capability);
    		System.out.println(capability);
    		System.out.println(connection.getSessionId());
}
 
    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }
 
    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }
 
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
}