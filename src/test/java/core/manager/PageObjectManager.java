package core.manager;

import core.context.TestContext;
import ui.pages.DashboardPage;
import ui.pages.LoginPage;
import java.util.Objects;

public class PageObjectManager implements AutoCloseable {
    private final TestContext context;
    private volatile LoginPage loginPage;
    private volatile DashboardPage dashboardPage;

    public PageObjectManager(TestContext context) {
        this.context = Objects.requireNonNull(context, "TestContext cannot be null");
    }

    public synchronized LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(context);
        }
        return loginPage;
    }

    public synchronized DashboardPage getDashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage(context);
        }
        return dashboardPage;
    }

    @Override
    public void close() {
        if (loginPage != null) {
            loginPage = null;
        }
        if (dashboardPage != null) {
            dashboardPage.tearDown();
            dashboardPage = null;
        }
    }
}
