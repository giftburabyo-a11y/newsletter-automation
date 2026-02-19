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

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeEach
    public void setup(TestInfo testInfo) {

        extent = ExtentManager.getInstance();
        test = extent.createTest(testInfo.getDisplayName());

        ChromeOptions options = new ChromeOptions();

        boolean isCI = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || System.getenv("CI") != null;

        if (isCI) {
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",   // bypass /dev/shm size limit
                    "--disable-gpu",
                    "--window-size=1920,1080",
                    "--remote-debugging-port=0",
                    "--disable-extensions",
                    "--disable-software-rasterizer",
                    "--shm-size=2g"              // allocate 2GB shared memory
            );
        }

        String driverPath = System.getProperty("webdriver.chrome.driver");
        if (driverPath != null && !driverPath.isEmpty()) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }

        String chromeBinary = System.getProperty("webdriver.chrome.binary");
        if (chromeBinary != null && !chromeBinary.isEmpty()) {
            options.setBinary(chromeBinary);
        }

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://burabyo.github.io/Newsletter-sign-up-form/");
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }

    protected void captureFailure(String testName) {
        String path = ScreenshotUtil.captureScreenshot(driver, testName);
        test.fail("Test Failed").addScreenCaptureFromPath(path);
    }
}