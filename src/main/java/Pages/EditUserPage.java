package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EditUserPage extends BasePage {

    public EditUserPage(WebDriver driver) {
        super(driver);
    }


    private final By userRoleDropdown = By.xpath("(//div[contains(@class,'oxd-select-text')])[1]");
    private final By statusDropdown = By.xpath("(//div[contains(@class,'oxd-select-text')])[2]");
    private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");

    public void selectUserRole(String role) {
        logger.info("Selecting role for user");

        click(userRoleDropdown);
        By roleOption = By.xpath(String.format("//div[@role='option']//span[normalize-space()='%s']", role));
        click(roleOption);
    }
    public void waitUntilRoleUpdated() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[normalize-space()='Successfully Updated']")));
    }

    public void clickSave() {
        click(saveButton);
        waitUntilRoleUpdated();
    }

    public void updateUserRole(String role) {
        selectUserRole(role);
        clickSave();
    }


}