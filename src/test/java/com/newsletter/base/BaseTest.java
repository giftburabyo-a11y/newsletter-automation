package com.newsletter.base;

import com.newsletter.helper.TestHelper;
import com.newsletter.pages.NewsletterPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected TestHelper helper;
    protected NewsletterPage page; // <-- added here

    @RegisterExtension
    public AfterTestExecutionCallback failureWatcher = context -> {
        if (context.getExecutionException().isPresent()) {
            captureScreenshot();
        }
    };

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        boolean isCI = Boolean.parseBoolean(System.getProperty("headless", "false")) ||
                System.getenv("CI") != null;

        if (isCI) {
            options.addArguments("--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        helper = new TestHelper(driver, wait);

        driver.get("https://burabyo.github.io/Newsletter-sign-up-form/");

        page = new NewsletterPage(driver); // <-- initialize page here
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] captureScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}