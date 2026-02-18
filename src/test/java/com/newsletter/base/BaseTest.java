package com.newsletter.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.newsletter.utils.ExtentManager;
import com.newsletter.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeEach
    public void setup(TestInfo testInfo) {

        extent = ExtentManager.getInstance();
        test = extent.createTest(testInfo.getDisplayName());

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

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

