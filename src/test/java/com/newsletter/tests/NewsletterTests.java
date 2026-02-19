    package com.newsletter.tests;

    import com.newsletter.base.BaseTest;
    import com.newsletter.pages.NewsletterPage;
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.Test;
    import org.openqa.selenium.Keys;
    import org.openqa.selenium.support.ui.ExpectedConditions;
    import org.openqa.selenium.support.ui.WebDriverWait;

    import java.time.Duration;

    public class NewsletterTests extends BaseTest {

        private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        private void waitForModal(NewsletterPage page) {
            wait.until(ExpectedConditions.visibilityOf(page.getSuccessModal()));
        }

        @Test
        public void TC01_ValidEmailSubmission() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("valid1@example.com");
                page.clickSubmit();
                waitForModal(page);

                Assertions.assertTrue(page.isModalDisplayed());
                Assertions.assertEquals("valid1@example.com", page.getConfirmedEmail());
                test.pass("Valid email submitted successfully");

            } catch (Exception e) {
                captureFailure("TC01-verify Valid Email Submission");
                Assertions.fail();
            }
        }

        @Test
        public void TC02_InvalidEmail_NoAtSymbol() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("invalidemail.com");
                page.clickSubmit();

                Assertions.assertEquals("Valid email required", page.getErrorMessage());
                test.pass("Error displayed correctly");

            } catch (Exception e) {
                captureFailure("TC02-verify Invalid Email No @ Symbol");
                Assertions.fail();
            }
        }

        @Test
        public void TC03_EmptyEmail() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("");
                page.clickSubmit();

                Assertions.assertEquals("Valid email required", page.getErrorMessage());
                test.pass("Empty email validation works");

            } catch (Exception e) {
                captureFailure("TC03-verify Empty Email Shows Error");
                Assertions.fail();
            }
        }

        @Test
        public void TC04_EmailWithSpaces() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("  spaced@example.com  ");
                page.clickSubmit();
                waitForModal(page);

                Assertions.assertTrue(page.isModalDisplayed());
                test.pass("Spaces handled correctly");

            } catch (Exception e) {
                captureFailure("TC04-verify Email With Spaces Handled");
                Assertions.fail();
            }
        }

        @Test
        public void TC05_EmailWithUppercase() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("UPPERCASE@EXAMPLE.COM");
                page.clickSubmit();
                waitForModal(page);

                Assertions.assertTrue(page.isModalDisplayed());
                test.pass("Uppercase email accepted");

            } catch (Exception e) {
                captureFailure("TC05-verify Uppercase Email Accepted");
                Assertions.fail();
            }
        }
        @Test
        public void TC06_MultipleAtSymbols() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("double@@example.com");
                page.clickSubmit();

                Assertions.assertEquals("Valid email required", page.getErrorMessage());
                test.pass("Multiple @ rejected");

            } catch (Exception e) {
                captureFailure("TC06-verify Multiple @ Symbols Rejected");
                Assertions.fail();
            }
        }

        @Test
        public void TC07_NoDomain() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("nodomain@");
                page.clickSubmit();

                Assertions.assertEquals("Valid email required", page.getErrorMessage());
                test.pass("Missing domain rejected");

            } catch (Exception e) {
                captureFailure("TC07-verify Missing Domain Rejected");
                Assertions.fail();
            }
        }

    //    @Test
    //    public void TC08_SpecialCharacters() {
    //        NewsletterPage page = new NewsletterPage(driver);
    //
    //        try {
    //            page.enterEmail("special!@example.com");
    //            page.clickSubmit();
    //            waitForModal(page);
    //
    //            Assertions.assertTrue(page.isModalDisplayed());
    //            test.pass("Special characters allowed if valid");
    //
    //        } catch (Exception e) {
    //            captureFailure("TC08-verify Special Characters Email Accepted");
    //            Assertions.fail();
    //        }
    //    }

        @Test
        public void TC09_VeryLongEmail() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < 50; i++) sb.append("a");
                String longEmail = sb + "@example.com";

                page.enterEmail(longEmail);
                page.clickSubmit();
                waitForModal(page);

                Assertions.assertTrue(page.isModalDisplayed());
                test.pass("Long email handled");

            } catch (Exception e) {
                captureFailure("TC09-verify Very Long Email Handled");
                Assertions.fail();
            }
        }

    //    @Test
    //    public void TC10_DismissModal() {
    //        NewsletterPage page = new NewsletterPage(driver);
    //
    //        try {
    //            page.enterEmail("dismiss1@example.com");
    //            page.clickSubmit();
    //            waitForModal(page);
    //
    //            page.dismissModal();
    //            Assertions.assertFalse(page.isModalDisplayed());
    //            test.pass("Modal dismissed successfully");
    //
    //        } catch (Exception e) {
    //            captureFailure("TC10-verify Modal Can Be Dismissed");
    //            Assertions.fail();
    //        }
    //    }

        @Test
        public void TC11_ErrorMessageHiddenInitially() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                Assertions.assertFalse(page.isErrorMessageDisplayed());
                test.pass("Error message hidden initially");

            } catch (Exception e) {
                captureFailure("TC11-verify Error Message Hidden Initially");
                Assertions.fail();
            }
        }

        @Test
        public void TC12_ErrorMessageVisibleWhenInvalid() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("invalid1");
                page.clickSubmit();
                Assertions.assertTrue(page.isErrorMessageDisplayed());
                test.pass("Error message visible on invalid email");

            } catch (Exception e) {
                captureFailure("TC12-verify Error Message Visible On Invalid Email");
                Assertions.fail();
            }
        }

        @Test
        public void TC13_ErrorMessageTextCorrect() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("invalid2");
                page.clickSubmit();
                Assertions.assertEquals("Valid email required", page.getErrorMessage());
                test.pass("Error message text correct");

            } catch (Exception e) {
                captureFailure("TC13-verify Error Message Text Is Correct");
                Assertions.fail();
            }
        }

    //    @Test
    //    public void TC14_EmailInputGetsErrorClass() {
    //        NewsletterPage page = new NewsletterPage(driver);
    //
    //        try {
    //            page.enterEmail("invalid3");
    //            page.clickSubmit();
    //            String classes = page.getEmailInput().getAttribute("class");
    //            Assertions.assertTrue(classes.contains("error") || classes.contains("invalid"));
    //            test.pass("Email input gets error class on invalid input");
    //
    //        } catch (Exception e) {
    //            captureFailure("TC14-verify Email Input Gets Error Class On Invalid Input");
    //            Assertions.fail();
    //        }
    //    }

    //    @Test
    //    public void TC15_ModalHiddenInitially() {
    //        NewsletterPage page = new NewsletterPage(driver);
    //
    //        try {
    //            Assertions.assertFalse(page.isModalDisplayed());
    //            test.pass("Modal hidden initially");
    //
    //        } catch (Exception e) {
    //            captureFailure("TC15-verify Modal Hidden Initially");
    //            Assertions.fail();
    //        }
    //    }

        @Test
        public void TC16_ModalVisibleAfterSuccess() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("success1@example.com");
                page.clickSubmit();
                waitForModal(page);

                Assertions.assertTrue(page.isModalDisplayed());
                test.pass("Modal appears after successful submission");

            } catch (Exception e) {
                captureFailure("TC16-verify Modal Visible After Successful Submission");
                Assertions.fail();
            }
        }

        @Test
        public void TC17_SubmitUsingEnterKey() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("enter1@example.com");
                page.getEmailInput().sendKeys(Keys.ENTER);
                waitForModal(page);

                Assertions.assertTrue(page.isModalDisplayed());
                test.pass("Form submitted using Enter key");

            } catch (Exception e) {
                captureFailure("TC17-verify Form Submission With Enter Key");
                Assertions.fail();
            }
        }

        @Test
        public void TC18_PageRefreshResetsForm() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                page.enterEmail("refresh1@example.com");
                driver.navigate().refresh();

                Assertions.assertEquals("", page.getEmailInput().getAttribute("value"));
                test.pass("Page refresh resets form");

            } catch (Exception e) {
                captureFailure("TC18-verify Page Refresh Resets Form State");
                Assertions.fail();
            }
        }

        @Test
        public void TC19_FormIDExists() {
            NewsletterPage page = new NewsletterPage(driver);

            try {
                Assertions.assertEquals("newsletter-form", page.getNewsletterForm().getAttribute("id"));
                test.pass("Form ID exists and is correct");

            } catch (Exception e) {
                captureFailure("TC19-verify Form ID Exists");
                Assertions.fail();
            }
        }
    }

