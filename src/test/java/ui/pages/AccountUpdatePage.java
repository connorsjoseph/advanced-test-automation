package ui.pages;

import core.base.BaseClass;
import core.context.TestContext;
import org.openqa.selenium.By;

public class AccountUpdatePage extends BaseClass {

    // Locators
    private final By emailField = By.id("email");
    private final By phoneField = By.id("phone");
    private final By saveButton = By.id("saveBtn");
    private final By emailSuccessMsg = By.id("email-update-success");
    private final By phoneSuccessMsg = By.id("phone-update-success");

    public AccountUpdatePage(TestContext context) {
        super(context);  // ✅ passes TestContext to BaseClass
    }

    // Method to update email
    public void updateEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    // Method to update phone number
    public void updatePhoneNumber(String phoneNumber) {
        driver.findElement(phoneField).clear();
        driver.findElement(phoneField).sendKeys(phoneNumber);
    }

    // Method to click save changes button
    public void saveChanges() {
        driver.findElement(saveButton).click();
    }

    // Method to check if email is updated
    public boolean isEmailUpdated() {
        return driver.findElement(emailSuccessMsg).isDisplayed();
    }

    // Method to check if phone number is updated
    public boolean isPhoneNumberUpdated() {
        return driver.findElement(phoneSuccessMsg).isDisplayed();
    }
}
