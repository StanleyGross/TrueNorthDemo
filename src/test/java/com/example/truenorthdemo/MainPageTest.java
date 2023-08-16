package com.example.truenorthdemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private final Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void SearchWithoutCriteria() {
        wait.until(d -> driver.findElement(By.xpath("//h1[contains(text(),'Get Github Repos')]")).isDisplayed());
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//strong[contains(text(),'Github user not found')]")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'No repos')]")).isDisplayed());
    }

    @Test
    public void SearchWithCriteriaButWithoutRepos() {
        wait.until(d -> driver.findElement(By.xpath("//h1[contains(text(),'Get Github Repos')]")).isDisplayed());
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("StanleyGross25");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//strong[contains(text(),'Github user not found')]")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'No repos')]")).isDisplayed());
    }

    @Test
    public void SearchWithCriteriaWithRepos() {
        wait.until(d -> driver.findElement(By.xpath("//h1[contains(text(),'Get Github Repos')]")).isDisplayed());
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("StanleyGross");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//strong[contains(text(),'Success!')]")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.xpath("//li[@class='repo-row']")).isDisplayed());
    }
}
