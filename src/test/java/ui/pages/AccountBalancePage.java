package ui.pages;

import core.base.BaseClass;
import core.context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountBalancePage extends BaseClass {

    // Locators
    private final By accountBalanceMenu = By.linkText("Accounts Overview");
    private final By balanceAmount = By.xpath("//table[@id='accountTable']//tr[2]/td[2]");


    public AccountBalancePage(TestContext context) {
        super(context); // Pass TestContext to the BaseClass constructor
    }

    // Method to navigate to account balance section
    public void navigateToAccountBalance() {
        driver.findElement(accountBalanceMenu).click();
    }

    // Method to check if account balance is displayed
    public boolean isAccountBalanceDisplayed() {
        return driver.findElement(balanceAmount).isDisplayed();
    }
}
