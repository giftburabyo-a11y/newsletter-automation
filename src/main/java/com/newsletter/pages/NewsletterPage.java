package com.newsletter.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewsletterPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
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
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void submitWithEnterKey() {
        emailInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void dismissModal() {
        wait.until(ExpectedConditions.elementToBeClickable(dismissButton));
        dismissButton.click();
        wait.until(ExpectedConditions.invisibilityOf(successModal));
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
        wait.until(ExpectedConditions.visibilityOf(successModal));
        return successModal.isDisplayed();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public String getConfirmedEmail() {
        return confirmEmail.getText();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getEmailValue() {
        return emailInput.getAttribute("value");
    }
}

