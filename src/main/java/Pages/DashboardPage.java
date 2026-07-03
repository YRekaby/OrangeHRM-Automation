package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By adminModule = By.xpath("//span[normalize-space()='Admin']");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By myInfoModule = By.xpath("//span[text()='My Info']");
    private final By timeModule = By.xpath("//span[text()='Time']");
    private final By userDropdown = By.cssSelector(".oxd-userdropdown-icon");
    private final By logoutButton = By.linkText("Logout");

    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardHeader);
    }

    public PIMPage navigateToPIM() {
        logger.info("Navigating to PIM page");
        click(pimMenu);
        return new PIMPage(driver);
    }

    public UserManagementPage navigateToAdmin() {
        logger.info("Navigating to User Management page");
        click(adminModule);
        return new UserManagementPage(driver);
    }

    public MyInfoPage navigateToMyInfo() {
        logger.info("Navigating to My Info page");
        click(myInfoModule);
        MyInfoPage page = new MyInfoPage(driver);
        page.waitUntilLoaded();

        return page;
    }

    public TimePage navigateToTime() {
        logger.info("Navigating to Time page");
        click(timeModule);
        TimePage timePage = new TimePage(driver);
        timePage.waitUntilLoaded();
        return timePage;
    }

    public LoginPage logout() {
        logger.info("Logging out!");
        waitForLoaderToDisappear();
        click(userDropdown);
        click(logoutButton);

        return new LoginPage(driver);
    }

    public boolean isAdminModuleDisplayed() {
        return isPresent(adminModule);
    }
}