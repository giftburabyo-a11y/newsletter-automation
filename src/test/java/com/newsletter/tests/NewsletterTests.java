package com.newsletter.tests;

import com.newsletter.base.BaseTest;
import com.newsletter.pages.NewsletterPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class NewsletterTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(NewsletterTests.class);

    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    private void waitForModal(NewsletterPage page) {
        log.info("Waiting for success modal to appear");
        wait.until(ExpectedConditions.visibilityOf(page.getSuccessModal()));
        log.info("Success modal is visible");
    }

    @Test
    public void TC01_ValidEmailSubmission() {
        log.info("TC01 — Valid Email Submission");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("valid1@example.com");
            page.clickSubmit();
            waitForModal(page);

            Assertions.assertTrue(page.isModalDisplayed());
            Assertions.assertEquals("valid1@example.com", page.getConfirmedEmail());
            log.info("TC01 — PASSED");
            test.pass("Valid email submitted successfully");

        } catch (Exception e) {
            log.error("TC01 — FAILED: {}", e.getMessage());
            captureFailure("TC01-verify Valid Email Submission");
            Assertions.fail();
        }
    }

    @Test
    public void TC02_InvalidEmail_NoAtSymbol() {
        log.info("TC02 — Invalid Email No @ Symbol");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("invalidemail.com");
            page.clickSubmit();

            Assertions.assertEquals("Valid email required", page.getErrorMessage());
            log.info("TC02 — PASSED");
            test.pass("Error displayed correctly");

        } catch (Exception e) {
            log.error("TC02 — FAILED: {}", e.getMessage());
            captureFailure("TC02-verify Invalid Email No @ Symbol");
            Assertions.fail();
        }
    }

    @Test
    public void TC03_EmptyEmail() {
        log.info("TC03 — Empty Email");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("");
            page.clickSubmit();

            Assertions.assertEquals("Valid email required", page.getErrorMessage());
            log.info("TC03 — PASSED");
            test.pass("Empty email validation works");

        } catch (Exception e) {
            log.error("TC03 — FAILED: {}", e.getMessage());
            captureFailure("TC03-verify Empty Email Shows Error");
            Assertions.fail();
        }
    }

    @Test
    public void TC04_EmailWithSpaces() {
        log.info("TC04 — Email With Spaces");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("  spaced@example.com  ");
            page.clickSubmit();
            waitForModal(page);

            Assertions.assertTrue(page.isModalDisplayed());
            log.info("TC04 — PASSED");
            test.pass("Spaces handled correctly");

        } catch (Exception e) {
            log.error("TC04 — FAILED: {}", e.getMessage());
            captureFailure("TC04-verify Email With Spaces Handled");
            Assertions.fail();
        }
    }

    @Test
    public void TC05_EmailWithUppercase() {
        log.info("TC05 — Email With Uppercase");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("UPPERCASE@EXAMPLE.COM");
            page.clickSubmit();
            waitForModal(page);

            Assertions.assertTrue(page.isModalDisplayed());
            log.info("TC05 — PASSED");
            test.pass("Uppercase email accepted");

        } catch (Exception e) {
            log.error("TC05 — FAILED: {}", e.getMessage());
            captureFailure("TC05-verify Uppercase Email Accepted");
            Assertions.fail();
        }
    }

    @Test
    public void TC06_MultipleAtSymbols() {
        log.info("TC06 — Multiple @ Symbols");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("double@@example.com");
            page.clickSubmit();

            Assertions.assertEquals("Valid email required", page.getErrorMessage());
            log.info("TC06 — PASSED");
            test.pass("Multiple @ rejected");

        } catch (Exception e) {
            log.error("TC06 — FAILED: {}", e.getMessage());
            captureFailure("TC06-verify Multiple @ Symbols Rejected");
            Assertions.fail();
        }
    }

    @Test
    public void TC07_NoDomain() {
        log.info("TC07 — No Domain");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("nodomain@");
            page.clickSubmit();

            Assertions.assertEquals("Valid email required", page.getErrorMessage());
            log.info("TC07 — PASSED");
            test.pass("Missing domain rejected");

        } catch (Exception e) {
            log.error("TC07 — FAILED: {}", e.getMessage());
            captureFailure("TC07-verify Missing Domain Rejected");
            Assertions.fail();
        }
    }

    @Test
    public void TC08_SpecialCharacters() {
        log.info("TC08 — Special Characters");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("special!@example.com");
            page.clickSubmit();
            waitForModal(page);

            Assertions.assertTrue(page.isModalDisplayed());
            log.info("TC08 — PASSED");
            test.pass("Special characters allowed if valid");

        } catch (Exception e) {
            log.error("TC08 — FAILED: {}", e.getMessage());
            captureFailure("TC08-verify Special Characters Email Accepted");
            Assertions.fail();
        }
    }

    @Test
    public void TC09_VeryLongEmail() {
        log.info("TC09 — Very Long Email");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < 50; i++) sb.append("a");
            String longEmail = sb + "@example.com";
            log.info("TC09 — Generated long email of length: {}", longEmail.length());

            page.enterEmail(longEmail);
            page.clickSubmit();
            waitForModal(page);

            Assertions.assertTrue(page.isModalDisplayed());
            log.info("TC09 — PASSED");
            test.pass("Long email handled");

        } catch (Exception e) {
            log.error("TC09 — FAILED: {}", e.getMessage());
            captureFailure("TC09-verify Very Long Email Handled");
            Assertions.fail();
        }
    }

    @Test
    public void TC10_DismissModal() {
        log.info("TC10 — Dismiss Modal");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("dismiss1@example.com");
            page.clickSubmit();
            waitForModal(page);

            page.dismissModal();
            Assertions.assertFalse(page.isModalDisplayed());
            log.info("TC10 — PASSED");
            test.pass("Modal dismissed successfully");

        } catch (Exception e) {
            log.error("TC10 — FAILED: {}", e.getMessage());
            captureFailure("TC10-verify Modal Can Be Dismissed");
            Assertions.fail();
        }
    }

    @Test
    public void TC11_ErrorMessageHiddenInitially() {
        log.info("TC11 — Error Message Hidden Initially");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            Assertions.assertFalse(page.isErrorMessageDisplayed());
            log.info("TC11 — PASSED");
            test.pass("Error message hidden initially");

        } catch (Exception e) {
            log.error("TC11 — FAILED: {}", e.getMessage());
            captureFailure("TC11-verify Error Message Hidden Initially");
            Assertions.fail();
        }
    }

    @Test
    public void TC12_ErrorMessageVisibleWhenInvalid() {
        log.info("TC12 — Error Message Visible When Invalid");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("invalid1");
            page.clickSubmit();
            Assertions.assertTrue(page.isErrorMessageDisplayed());
            log.info("TC12 — PASSED");
            test.pass("Error message visible on invalid email");

        } catch (Exception e) {
            log.error("TC12 — FAILED: {}", e.getMessage());
            captureFailure("TC12-verify Error Message Visible On Invalid Email");
            Assertions.fail();
        }
    }

    @Test
    public void TC13_ErrorMessageTextCorrect() {
        log.info("TC13 — Error Message Text Correct");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("invalid2");
            page.clickSubmit();
            Assertions.assertEquals("Valid email required", page.getErrorMessage());
            log.info("TC13 — PASSED");
            test.pass("Error message text correct");

        } catch (Exception e) {
            log.error("TC13 — FAILED: {}", e.getMessage());
            captureFailure("TC13-verify Error Message Text Is Correct");
            Assertions.fail();
        }
    }

    @Test
    public void TC14_EmailInputGetsErrorClass() {
        log.info("TC14 — Email Input Gets Error Class");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("invalid3");
            page.clickSubmit();
            String classes = page.getEmailInput().getAttribute("class");
            log.info("TC14 — Email input classes: '{}'", classes);
            Assertions.assertTrue(classes.contains("error") || classes.contains("invalid"));
            log.info("TC14 — PASSED");
            test.pass("Email input gets error class on invalid input");

        } catch (Exception e) {
            log.error("TC14 — FAILED: {}", e.getMessage());
            captureFailure("TC14-verify Email Input Gets Error Class On Invalid Input");
            Assertions.fail();
        }
    }

    @Test
    public void TC15_ModalHiddenInitially() {
        log.info("TC15 — Modal Hidden Initially");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            Assertions.assertFalse(page.isModalDisplayed());
            log.info("TC15 — PASSED");
            test.pass("Modal hidden initially");

        } catch (Exception e) {
            log.error("TC15 — FAILED: {}", e.getMessage());
            captureFailure("TC15-verify Modal Hidden Initially");
            Assertions.fail();
        }
    }

    @Test
    public void TC16_ModalVisibleAfterSuccess() {
        log.info("TC16 — Modal Visible After Success");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("success1@example.com");
            page.clickSubmit();
            waitForModal(page);

            Assertions.assertTrue(page.isModalDisplayed());
            log.info("TC16 — PASSED");
            test.pass("Modal appears after successful submission");

        } catch (Exception e) {
            log.error("TC16 — FAILED: {}", e.getMessage());
            captureFailure("TC16-verify Modal Visible After Successful Submission");
            Assertions.fail();
        }
    }

    @Test
    public void TC17_SubmitUsingEnterKey() {
        log.info("TC17 — Submit Using Enter Key");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("enter1@example.com");
            log.info("TC17 — Pressing Enter key");
            page.getEmailInput().sendKeys(Keys.ENTER);
            waitForModal(page);

            Assertions.assertTrue(page.isModalDisplayed());
            log.info("TC17 — PASSED");
            test.pass("Form submitted using Enter key");

        } catch (Exception e) {
            log.error("TC17 — FAILED: {}", e.getMessage());
            captureFailure("TC17-verify Form Submission With Enter Key");
            Assertions.fail();
        }
    }

    @Test
    public void TC18_PageRefreshResetsForm() {
        log.info("TC18 — Page Refresh Resets Form");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            page.enterEmail("refresh1@example.com");
            log.info("TC18 — Refreshing page");
            driver.navigate().refresh();

            Assertions.assertEquals("", page.getEmailInput().getAttribute("value"));
            log.info("TC18 — PASSED");
            test.pass("Page refresh resets form");

        } catch (Exception e) {
            log.error("TC18 — FAILED: {}", e.getMessage());
            captureFailure("TC18-verify Page Refresh Resets Form State");
            Assertions.fail();
        }
    }

    @Test
    public void TC19_FormIDExists() {
        log.info("TC19 — Form ID Exists");
        NewsletterPage page = new NewsletterPage(driver);

        try {
            String formId = page.getNewsletterForm().getAttribute("id");
            log.info("TC19 — Form ID found: '{}'", formId);
            Assertions.assertEquals("newsletter-form", formId);
            log.info("TC19 — PASSED");
            test.pass("Form ID exists and is correct");

        } catch (Exception e) {
            log.error("TC19 — FAILED: {}", e.getMessage());
            captureFailure("TC19-verify Form ID Exists");
            Assertions.fail();
        }
    }
}