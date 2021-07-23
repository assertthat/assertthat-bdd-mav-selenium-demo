package com.assertthat;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class StepDefinition {

    WebDriver driver;
    Scenario scenario;

    @Given("I am on Google home page")
    public void i_am_on_Google_home_page() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.xpath("//*[text()='I agree']")).click();

    }

    @When("I search for {string}")
    public void i_search_for(String string) {
        driver.findElement(By.cssSelector("[name='q']")).sendKeys(string);
        driver.findElement(By.cssSelector("[name='q']")).sendKeys(Keys.ENTER);
    }


    @Then("I see that the first result is {string}")
    public void i_see_that_the_first_result_is(String string) throws IOException {
        WebElement result =
                driver.findElements(By.className("g")).get(0).findElement(By.tagName("h3"));
        assertEquals(string, result.getText().trim());
    }

    @Before
    public void beforeScenario(Scenario sc) {
        scenario = sc;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void afterScenario(Scenario sc) throws IOException {
        scenario = sc;
        if(sc.isFailed()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if(driver.findElements(By.className("g")).size()>0) {
                WebElement result =
                        driver.findElements(By.className("g")).get(0).findElement(By.tagName("h3"));
                ImageIO.write(Shutterbug.shootPage(driver,
                        ScrollStrategy.VIEWPORT_ONLY, true).highlightWithText(result,
                        Color.RED,
                        2, "First result text is incorrect", Color.RED, new Font("SansSerif", Font.BOLD, 20)).getImage(),
                        "png",
                        baos);
                baos.flush();
                scenario.attach(baos.toByteArray(), "image/png", "Search result");
            }
        }
        driver.quit();
    }
}