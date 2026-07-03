package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PIMPage extends BasePage {

    private final By addEmployeeButton = By.xpath("//a[text()='Add Employee']");
    private final By employeeListButton = By.xpath("//a[text()='Employee List']");

    public PIMPage(WebDriver driver){
        super (driver);
    }

    public AddEmployeePage navigateToAddEmployee() {
        click(addEmployeeButton);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        addEmployeePage.waitUntilLoaded();
        return addEmployeePage;
    }

    public EmployeeListPage navigateToEmployeeList() {
        click(employeeListButton);
        return new EmployeeListPage(driver);
    }

}
