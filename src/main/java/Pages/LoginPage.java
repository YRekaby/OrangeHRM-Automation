package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By loginErrorMessage = By.xpath("//p[contains(.,'Invalid credentials')]");


    public DashboardPage login(String username, String password) {

        logger.info("Logging in with user: {}", username);

        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);

        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
            logger.info("Login successful for user: {}", username);
            return new DashboardPage(driver);
        } catch (Exception e) {
            if (isPresent(loginErrorMessage)) {
                logger.warn("Invalid credentials for user: {}", username);
            } else {
                logger.error("Login failed because the dashboard did not load.");
            }
            return null;

        }
    }
}

