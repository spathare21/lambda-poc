package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import MyRunner.*;

public class ToDoStepDefinition extends TestRunner {

	public RemoteWebDriver driver = this.connection;

	@Before
	public void updateName(Scenario scenario) {
		driver.executeScript("lambda-name="+scenario.getName());
	}

	@Given("^user is on home Page$")
	public void user_already_on_home_page() {
		System.out.println(driver.getCapabilities());
		driver.get("https://lambdatest.github.io/sample-todo-app/");

	}

	@When("^select First Item$")
	public void select_first_item() {
		driver.findElement(By.name("li1")).click();
	}

	@Then("^select second item$")
	public void select_second_item() {
		driver.findElement(By.name("li2")).click();
	}

	@Then("^add new item$")
	public void add_new_item() {
		driver.findElement(By.id("sampletodotext")).clear();
		driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
		driver.findElement(By.id("addbutton")).click();
	}

	@Then("^verify added item$")
	public void verify_added_item() {
		String item = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
		Assert.assertTrue(item.contains("Yey, Let's add it to list"));
	}

	@After
	public void close_the_browser(Scenario scenario) {
		driver.executeScript("lambda-status=" + (scenario.isFailed() ? "failed" : "passed"));
		driver.quit();
	}

}
