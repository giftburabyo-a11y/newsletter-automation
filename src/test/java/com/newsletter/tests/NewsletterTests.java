package com.newsletter.tests;

import com.newsletter.base.BaseTest;
import com.newsletter.pages.NewsletterPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsletterTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(NewsletterTests.class);

    @Test
    public void TC01_ValidEmailSubmission() {
        log.info("TC01 — Valid Email Submission");
        NewsletterPage page = new NewsletterPage(driver);
        try {
            helper.submitAndExpectSuccess(page, "valid1@example.com", "Valid email submitted successfully");
            Assertions.assertEquals("valid1@example.com", page.getConfirmedEmail());
            log.info("TC01 — PASSED");
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
            helper.submitAndExpectError(page, "invalidemail.com", "Error displayed correctly");
            log.info("TC02 — PASSED");
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
            helper.submitAndExpectError(page, "", "Empty email validation works");
            log.info("TC03 — PASSED");
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
            helper.submitAndExpectSuccess(page, "  spaced@example.com  ", "Spaces handled correctly");
            log.info("TC04 — PASSED");
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
            helper.submitAndExpectSuccess(page, "UPPERCASE@EXAMPLE.COM", "Uppercase email accepted");
            log.info("TC05 — PASSED");
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
            helper.submitAndExpectError(page, "double@@example.com", "Multiple @ rejected");
            log.info("TC06 — PASSED");
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
            helper.submitAndExpectError(page, "nodomain@", "Missing domain rejected");
            log.info("TC07 — PASSED");
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
            helper.submitAndExpectSuccess(page, "special!@example.com", "Special characters allowed if valid");
            log.info("TC08 — PASSED");
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
            for (int i = 0; i < 50; i++) sb.append("a");
            String longEmail = sb + "@example.com";
            log.info("TC09 — Generated long email of length: {}", longEmail.length());
            helper.submitAndExpectSuccess(page, longEmail, "Long email handled");
            log.info("TC09 — PASSED");
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
            helper.waitForModal(page);
            page.dismissModal();
            helper.assertModalHidden(page, "Modal dismissed successfully");
            log.info("TC10 — PASSED");
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
            helper.assertErrorHidden(page, "Error message hidden initially");
            log.info("TC11 — PASSED");
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
            helper.assertErrorVisible(page, "Error message visible on invalid email");
            log.info("TC12 — PASSED");
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
            helper.submitAndExpectError(page, "invalid2", "Error message text correct");
            log.info("TC13 — PASSED");
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
            helper.assertModalHidden(page, "Modal hidden initially");
            log.info("TC15 — PASSED");
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
            helper.submitAndExpectSuccess(page, "success1@example.com", "Modal appears after successful submission");
            log.info("TC16 — PASSED");
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
            helper.waitForModal(page);
            helper.assertModalVisible(page, "Form submitted using Enter key");
            log.info("TC17 — PASSED");
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