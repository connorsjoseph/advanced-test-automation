package ui.pages;

import core.base.BaseClass;
import core.context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BaseClass {

    private final By userProfileIcon = By.id("userIcon");

    public DashboardPage(TestContext context) {
        super(context);
    }

    public boolean isUserLoggedIn() {
        return driver.findElement(userProfileIcon).isDisplayed();
    }
}
