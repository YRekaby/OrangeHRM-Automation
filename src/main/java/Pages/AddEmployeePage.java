package Pages;

import Models.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddEmployeePage extends BasePage {

    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");
    private final By employeeIdField = By.xpath("//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By createLoginDetailsSwitch = By.cssSelector(".oxd-switch-wrapper label");
    private final By usernameField = By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By passwordField = By.xpath("//label[normalize-space()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By confirmPasswordField = By.xpath("//label[normalize-space()='Confirm Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By saveButton = By.cssSelector("button[type='submit']");;

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }
    public void waitUntilLoaded() {
        waitForLoaderToDisappear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
    }

    public void createEmployee(Employee employee) {
        logger.info("Creating employee: {} {}", employee.getFirstName(), employee.getLastName());
        type(firstNameField, employee.getFirstName());
        type(lastNameField, employee.getLastName());
        click(createLoginDetailsSwitch);
        type(usernameField, employee.getUsername());
        type(passwordField, employee.getPassword());
        type(confirmPasswordField, employee.getPassword());
        }

    public void clickSave() {
        click(saveButton);
        logger.info("Employee information entered successfully.");
    }

    public String getEmployeeId() {
        return getAttribute(employeeIdField, "value");
        }
}
