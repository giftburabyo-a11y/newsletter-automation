package com.newsletter.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.newsletter.utils.ExtentManager;
import com.newsletter.utils.ScreenshotUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

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

        String driverPath = System.getProperty("webdriver.chrome.driver");
        log.info("ChromeDriver path: {}", driverPath);

        String chromeBinary = System.getProperty("webdriver.chrome.binary");
        log.info("Chrome binary path: {}", chromeBinary);

        if (driverPath != null && !driverPath.isEmpty()) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }

        if (chromeBinary != null && !chromeBinary.isEmpty()) {
            options.setBinary(chromeBinary);
        }

        log.info("Launching ChromeDriver...");
        driver = new ChromeDriver(options);
        log.info("ChromeDriver launched successfully");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

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