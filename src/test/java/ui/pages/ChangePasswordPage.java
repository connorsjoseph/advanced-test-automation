package ui.pages;

import core.base.BaseClass;
import core.context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage extends BaseClass {

    // Locators (replace with actual IDs or XPaths)
    private final By oldPasswordField = By.id("oldPassword");
    private final By newPasswordField = By.id("newPassword");
    private final By confirmPasswordField = By.id("confirmPassword");
    private final By changePasswordButton = By.id("changePasswordBtn");
    private final By successMessage = By.id("password-change-success");
    private final By errorMessage = By.id("password-change-error");

    public ChangePasswordPage(TestContext context) {
        super(context);  // ✅ pass TestContext to BaseClass
    }

    // Method to enter the old password
    public void enterOldPassword(String oldPassword) {
        driver.findElement(oldPasswordField).clear();
        driver.findElement(oldPasswordField).sendKeys(oldPassword);
    }

    // Method to enter the new password
    public void enterNewPassword(String newPassword) {
        driver.findElement(newPasswordField).clear();
        driver.findElement(newPasswordField).sendKeys(newPassword);
    }

    // Method to confirm the new password
    public void confirmNewPassword(String confirmPassword) {
        driver.findElement(confirmPasswordField).clear();
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
    }

    // Method to click the "Change Password" button
    public void clickChangePasswordButton() {
        driver.findElement(changePasswordButton).click();
    }

    // Method to check if password change is successful
    public boolean isPasswordChanged() {
        return driver.findElement(successMessage).isDisplayed();
    }

    // Method to check if there is an error message (incorrect old password, etc.)
    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }
}
