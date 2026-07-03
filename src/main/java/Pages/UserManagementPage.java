package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserManagementPage extends BasePage {

    public UserManagementPage(WebDriver driver) {
        super(driver);
    }


    private final By usernameField = By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[@type='submit' and normalize-space()='Search']");
    private final By editButton = By.xpath("//i[contains(@class,'bi-pencil-fill')]/parent::button");

    public void searchUser(String username) {
        type(usernameField, username);
        click(searchButton);
    }

    public EditUserPage clickEditUser() {
        click(editButton);
        return new EditUserPage(driver);
    }

    public EditUserPage openUser(String username) {
        searchUser(username);
        return clickEditUser();
    }
}