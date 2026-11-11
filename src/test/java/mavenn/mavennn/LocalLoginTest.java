package mavenn.mavennn;


import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocalLoginTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {

        // Configure Chrome in headless mode
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        // Start Chrome browser
        driver = new ChromeDriver(options);

        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeMethod
    public void openLoginPage() {

        // Locate local HTML file
        File file = new File("webpage/login.html");

        String pageURL = file.toURI().toString();

        driver.get(pageURL);
    }

    @Test(priority = 1)
    public void validLoginTest() {

        driver.findElement(By.id("username"))
              .sendKeys("admin");

        driver.findElement(By.id("password"))
              .sendKeys("admin123");

        driver.findElement(By.id("loginBtn"))
              .click();

        String actualMessage =
            driver.findElement(By.id("message")).getText();

        Assert.assertEquals(
            actualMessage,
            "Login Successful",
            "Valid login test failed"
        );

        System.out.println("Valid Login Test Passed");
    }

    @Test(priority = 2)
    public void invalidLoginTest() {

        driver.findElement(By.id("username"))
              .sendKeys("wronguser");

        driver.findElement(By.id("password"))
              .sendKeys("wrongpass");

        driver.findElement(By.id("loginBtn"))
              .click();

        String actualMessage =
            driver.findElement(By.id("message")).getText();

        Assert.assertEquals(
            actualMessage,
            "Invalid Username or Password",
            "Invalid login test failed"
        );

        System.out.println("Invalid Login Test Passed");
    }

    @AfterClass
    public void closeBrowser() {

        if (driver != null) {
            driver.quit();
        }

        System.out.println("Browser Closed Successfully");
    }
}

