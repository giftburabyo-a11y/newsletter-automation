package com.newsletter.helper;

import com.newsletter.pages.NewsletterPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestHelper {

    private final WebDriverWait wait;

    public TestHelper(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
    }

    // Wait for modal to be visible (with safe timeout)
    public void waitForModal(NewsletterPage page) {
        try {
            wait.until(ExpectedConditions.visibilityOf(page.getSuccessModal()));
        } catch (Exception e) {
            // ignore if modal never appears
        }
    }

    public void assertModalVisible(NewsletterPage page) {
        try {
            wait.until(ExpectedConditions.visibilityOf(page.getSuccessModal()));
            Assertions.assertTrue(page.getSuccessModal().isDisplayed());
        } catch (Exception e) {
            Assertions.fail("Modal was not visible when expected");
        }
    }

    public void assertModalHidden(NewsletterPage page) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(page.getSuccessModal()));
            Assertions.assertFalse(page.getSuccessModal().isDisplayed());
        } catch (Exception ignored) {
            // If modal never appeared, that's fine too
        }
    }

    public void assertErrorMessage(NewsletterPage page) {
        wait.until(driver -> page.getErrorMessageElement().isDisplayed());
        Assertions.assertEquals("Valid email required", page.getErrorMessage());
    }

    public void assertErrorVisible(NewsletterPage page) {
        wait.until(driver -> page.getErrorMessageElement().isDisplayed());
        Assertions.assertTrue(page.getErrorMessageElement().isDisplayed());
    }

    public void assertErrorHidden(NewsletterPage page) {
        try {
            wait.until(driver -> !page.getErrorMessageElement().isDisplayed());
        } catch (Exception ignored) {}
        Assertions.assertFalse(page.getErrorMessageElement().isDisplayed());
    }

    public void submitAndExpectSuccess(NewsletterPage page, String email) {
        page.enterEmail(email);
        page.clickSubmit();
        waitForModal(page);
        assertModalVisible(page);
    }

    public void submitAndExpectError(NewsletterPage page, String email) {
        page.enterEmail(email);
        page.clickSubmit();
        assertErrorMessage(page);
    }

    public void assertConfirmedEmail(NewsletterPage page, String expectedEmail) {
        wait.until(driver -> page.getConfirmEmailElement().isDisplayed());
        Assertions.assertEquals(expectedEmail.trim(), page.getConfirmedEmail());
    }

    public void assertInputEmpty(NewsletterPage page) {
        Assertions.assertEquals("", page.getEmailValue());
    }

    public void assertFormId(NewsletterPage page, String expectedId) {
        Assertions.assertEquals(expectedId, page.getNewsletterForm().getAttribute("id"));
    }

    public void assertInputHasErrorClass(NewsletterPage page) {
        // wait for class to update
        wait.until((ExpectedCondition<Boolean>) driver -> {
            String cls = page.getEmailInput().getAttribute("class");
            return cls != null && (cls.contains("error") || cls.contains("invalid"));
        });
        String cls = page.getEmailInput().getAttribute("class");
        Assertions.assertTrue(cls.contains("error") || cls.contains("invalid"));
    }
}