package com.newsletter.utils;

import com.aventstack.extentreports.ExtentTest;
import com.newsletter.pages.NewsletterPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelper {

    private static final Logger log = LoggerFactory.getLogger(TestHelper.class);

    private final WebDriverWait wait;
    private final ExtentTest test;

    public TestHelper(WebDriverWait wait, ExtentTest test) {
        this.wait = wait;
        this.test = test;
    }

    // Wait for success modal to appear
    public void waitForModal(NewsletterPage page) {
        log.info("Waiting for success modal to appear");
        wait.until(ExpectedConditions.visibilityOf(page.getSuccessModal()));
        log.info("Success modal is visible");
    }

    // Assert modal is visible and pass report
    public void assertModalVisible(NewsletterPage page, String passMessage) {
        Assertions.assertTrue(page.isModalDisplayed());
        log.info("Modal is displayed — PASSED");
        test.pass(passMessage);
    }

    // Assert modal is NOT visible
    public void assertModalHidden(NewsletterPage page, String passMessage) {
        Assertions.assertFalse(page.isModalDisplayed());
        log.info("Modal is hidden — PASSED");
        test.pass(passMessage);
    }

    // Assert error message text
    public void assertErrorMessage(NewsletterPage page, String passMessage) {
        Assertions.assertEquals("Valid email required", page.getErrorMessage());
        log.info("Error message correct — PASSED");
        test.pass(passMessage);
    }

    // Assert error message is visible
    public void assertErrorVisible(NewsletterPage page, String passMessage) {
        Assertions.assertTrue(page.isErrorMessageDisplayed());
        log.info("Error message visible — PASSED");
        test.pass(passMessage);
    }

    // Assert error message is hidden
    public void assertErrorHidden(NewsletterPage page, String passMessage) {
        Assertions.assertFalse(page.isErrorMessageDisplayed());
        log.info("Error message hidden — PASSED");
        test.pass(passMessage);
    }

    // Submit email and expect success modal
    public void submitAndExpectSuccess(NewsletterPage page, String email, String passMessage) {
        page.enterEmail(email);
        page.clickSubmit();
        waitForModal(page);
        assertModalVisible(page, passMessage);
    }

    // Submit email and expect error message
    public void submitAndExpectError(NewsletterPage page, String email, String passMessage) {
        page.enterEmail(email);
        page.clickSubmit();
        assertErrorMessage(page, passMessage);
    }
}