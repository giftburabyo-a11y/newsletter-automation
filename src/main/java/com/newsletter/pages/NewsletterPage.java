package com.newsletter.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class NewsletterPage {

    private static final Logger log = LoggerFactory.getLogger(NewsletterPage.class);

    private final WebDriverWait wait;

    public NewsletterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("NewsletterPage initialised");
    }

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(className = "submit-btn")
    private WebElement submitButton;

    @FindBy(className = "error-message")
    private WebElement errorMessage;

    @FindBy(id = "modal")
    private WebElement successModal;

    @FindBy(id = "confirm-email")
    private WebElement confirmEmail;

    @FindBy(id = "dismiss-btn")
    private WebElement dismissButton;

    @FindBy(id = "newsletter-form")
    private WebElement newsletterForm;

    // --- Actions ---
    public void enterEmail(String email) {
        log.info("Entering email: '{}'", email);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickSubmit() {
        log.info("Clicking submit button");
        submitButton.click();
    }

    public void submitWithEnterKey() {
        log.info("Submitting form with Enter key");
        emailInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void dismissModal() {
        log.info("Dismissing modal");
        wait.until(ExpectedConditions.elementToBeClickable(dismissButton));
        dismissButton.click();
        wait.until(ExpectedConditions.invisibilityOf(successModal));
        log.info("Modal dismissed");
    }

    // --- Getters ---
    public WebElement getEmailInput() {
        return emailInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getErrorMessageElement() {
        return errorMessage;
    }

    public WebElement getSuccessModal() {
        return successModal;
    }

    public WebElement getConfirmEmailElement() {
        return confirmEmail;
    }

    public WebElement getDismissButton() {
        return dismissButton;
    }

    public WebElement getNewsletterForm() {
        return newsletterForm;
    }

    // --- Helper Methods ---
    public boolean isModalDisplayed() {
        log.info("Checking if modal is displayed");
        wait.until(ExpectedConditions.visibilityOf(successModal));
        boolean displayed = successModal.isDisplayed();
        log.info("Modal displayed: {}", displayed);
        return displayed;
    }

    public boolean isErrorMessageDisplayed() {
        boolean displayed = errorMessage.isDisplayed();
        log.info("Error message displayed: {}", displayed);
        return displayed;
    }

    public String getConfirmedEmail() {
        String email = confirmEmail.getText();
        log.info("Confirmed email in modal: {}", email);
        return email;
    }

    public String getErrorMessage() {
        String message = errorMessage.getText();
        log.info("Error message text: '{}'", message);
        return message;
    }

    public String getEmailValue() {
        String value = emailInput.getAttribute("value");
        log.info("Current email input value: '{}'", value);
        return value;
    }
}