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
    void TC01_ValidEmailSubmission() {
        log.info("TC01 — Valid Email Submission");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectSuccess(page, "valid1@example.com");

        Assertions.assertEquals(
                "valid1@example.com",
                page.getConfirmedEmail()
        );
    }

    @Test
    void TC02_InvalidEmail_NoAtSymbol() {
        log.info("TC02 — Invalid Email No @ Symbol");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectError(page, "invalidemail.com");
    }

    @Test
    void TC03_EmptyEmail() {
        log.info("TC03 — Empty Email");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectError(page, "");
    }

    @Test
    void TC04_EmailWithSpaces() {
        log.info("TC04 — Email With Spaces");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectSuccess(page, "  spaced@example.com  ");
    }

    @Test
    void TC05_EmailWithUppercase() {
        log.info("TC05 — Email With Uppercase");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectSuccess(page, "UPPERCASE@EXAMPLE.COM");
    }

    @Test
    void TC06_MultipleAtSymbols() {
        log.info("TC06 — Multiple @ Symbols");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectError(page, "double@@example.com");
    }

    @Test
    void TC07_NoDomain() {
        log.info("TC07 — No Domain");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectError(page, "nodomain@");
    }

    @Test
    void TC08_SpecialCharacters() {
        log.info("TC08 — Special Characters");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectSuccess(page, "special!@example.com");
    }

    @Test
    void TC09_VeryLongEmail() {
        log.info("TC09 — Very Long Email");

        NewsletterPage page = new NewsletterPage(driver);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) sb.append("a");

        String longEmail = sb + "@example.com";

        helper.submitAndExpectSuccess(page, longEmail);
    }

    @Test
    void TC10_DismissModal() {
        log.info("TC10 — Dismiss Modal");

        NewsletterPage page = new NewsletterPage(driver);

        page.enterEmail("dismiss1@example.com");
        page.clickSubmit();

        helper.waitForModal(page);

        page.dismissModal();

        helper.assertModalHidden(page);
    }

    @Test
    void TC11_ErrorMessageHiddenInitially() {
        log.info("TC11 — Error Message Hidden Initially");

        NewsletterPage page = new NewsletterPage(driver);

        helper.assertErrorHidden(page);
    }

    @Test
    void TC12_ErrorMessageVisibleWhenInvalid() {
        log.info("TC12 — Error Message Visible When Invalid");

        NewsletterPage page = new NewsletterPage(driver);

        page.enterEmail("invalid1");
        page.clickSubmit();

        helper.assertErrorVisible(page);
    }

    @Test
    void TC13_ErrorMessageTextCorrect() {
        log.info("TC13 — Error Message Text Correct");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectError(page, "invalid2");
    }

    @Test
    void TC14_EmailInputGetsErrorClass() {
        log.info("TC14 — Email Input Gets Error Class");

        NewsletterPage page = new NewsletterPage(driver);

        page.enterEmail("invalid3");
        page.clickSubmit();

        String classes = page.getEmailInput().getAttribute("class");

        Assertions.assertTrue(
                classes.contains("error") || classes.contains("invalid")
        );
    }

    @Test
    void TC15_ModalHiddenInitially() {
        log.info("TC15 — Modal Hidden Initially");

        NewsletterPage page = new NewsletterPage(driver);

        helper.assertModalHidden(page);
    }

    @Test
    void TC16_ModalVisibleAfterSuccess() {
        log.info("TC16 — Modal Visible After Success");

        NewsletterPage page = new NewsletterPage(driver);

        helper.submitAndExpectSuccess(page, "success1@example.com");
    }

    @Test
    void TC17_SubmitUsingEnterKey() {
        log.info("TC17 — Submit Using Enter Key");

        NewsletterPage page = new NewsletterPage(driver);

        page.enterEmail("enter1@example.com");

        page.getEmailInput().sendKeys(Keys.ENTER);

        helper.waitForModal(page);
        helper.assertModalVisible(page);
    }

    @Test
    void TC18_PageRefreshResetsForm() {
        log.info("TC18 — Page Refresh Resets Form");

        NewsletterPage page = new NewsletterPage(driver);

        page.enterEmail("refresh1@example.com");

        driver.navigate().refresh();

        Assertions.assertEquals(
                "",
                page.getEmailInput().getAttribute("value")
        );
    }

    @Test
    void TC19_FormIDExists() {
        log.info("TC19 — Form ID Exists");

        NewsletterPage page = new NewsletterPage(driver);

        String formId = page.getNewsletterForm().getAttribute("id");

        Assertions.assertEquals("newsletter-form", formId);
    }
}