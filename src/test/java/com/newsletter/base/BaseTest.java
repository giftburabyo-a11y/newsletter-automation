package com.newsletter.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.newsletter.utils.ExtentManager;
import com.newsletter.utils.ScreenshotUtil;
import com.newsletter.utils.TestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    protected WebDriverWait wait;
    protected TestHelper helper;

    @BeforeEach
    public void setup(TestInfo testInfo) {

        log.info("========================================");
        log.info("Starting test: {}", testInfo.getDisplayName());
        log.info("========================================");

        extent = ExtentManager.getInstance();
        test = extent.createTest(testInfo.getDisplayName());

        ChromeOptions options = new ChromeOptions();

        boolean isCI = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || System.getenv("CI") != null;

        log.info("Running in CI mode: {}", isCI);

        if (isCI) {
            log.info("Applying headless Chrome options");
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--window-size=1920,1080",
                    "--remote-debugging-port=0",
                    "--disable-extensions",
                    "--disable-software-rasterizer",
                    "--shm-size=2g"
            );
        }

        log.info("Launching ChromeDriver...");
        driver = new ChromeDriver(options);
        log.info("ChromeDriver launched successfully");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // initialise wait and helper here so every test has access
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        helper = new TestHelper(wait, test);

        log.info("Navigating to application URL");
        driver.get("https://burabyo.github.io/Newsletter-sign-up-form/");
        log.info("Page loaded: {}", driver.getTitle());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        log.info("Tearing down test: {}", testInfo.getDisplayName());
        if (driver != null) {
            driver.quit();
            log.info("Browser closed");
        }
        extent.flush();
        log.info("Report flushed");
        log.info("========================================");
    }

    protected void captureFailure(String testName) {
        log.error("Test FAILED: {}", testName);
        log.error("Capturing screenshot...");
        String path = ScreenshotUtil.captureScreenshot(driver, testName);
        log.error("Screenshot saved to: {}", path);
        test.fail("Test Failed").addScreenCaptureFromPath(path);
    }
}