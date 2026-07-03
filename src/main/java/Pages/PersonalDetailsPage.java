package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalDetailsPage extends BasePage {
    private final By personalDetailsHeader =
            By.xpath("//h6[normalize-space()='Personal Details']");

    public PersonalDetailsPage(WebDriver driver) {
        super (driver);
    }
    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader));

    }

    public boolean isLoaded() {
        try {
            waitUntilLoaded();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
