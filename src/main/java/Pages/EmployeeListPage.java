package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmployeeListPage extends BasePage {

    private final By employeeNameField = By.xpath("//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By autoCompleteOption = By.xpath("//div[@role='listbox']//span");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By editButton = By.xpath("//div[contains(@class,'oxd-table-body')]//button[.//i[contains(@class,'bi-pencil-fill')]]");
    public EmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public void searchEmployee(String employeeName) {

        type(employeeNameField, employeeName);
        click(autoCompleteOption);
        click(searchButton);
    }

    public MyInfoPage openEmployeeProfile() {

        click(editButton);
        MyInfoPage page = new MyInfoPage(driver);
        page.waitUntilLoaded();

        return page;
    }
}