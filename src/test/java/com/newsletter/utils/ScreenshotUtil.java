package com.newsletter.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String directory = "screenshots/";
        new File(directory).mkdirs();

        String filePath = directory + testName + "_" + System.currentTimeMillis() + ".png";

        try {
            Files.copy(source.toPath(), Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}
