package Pages;

import Models.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimePage extends BasePage {

    private final By timesheetsMenu = By.xpath("//span[normalize-space()='Timesheets']");
    private final By myTimesheetsOption = By.xpath("//a[normalize-space()='My Timesheets']");
    private final By employeeTimesheetsOption = By.xpath("//a[normalize-space()='Employee Timesheets']");
    private final By timeHeader = By.xpath("//h6[contains(text(),'Time')]");

    private final By editButton = By.xpath("//button[normalize-space()='Edit']");
    private final By projectField = By.xpath("//input[@placeholder='Type for hints...']");
    private final By activityDropdown = By.xpath("//div[contains(@class,'oxd-select-text')]");
    private final By mondayHoursField = By.xpath("(//input[contains(@class,'oxd-input--active')])[2]");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By submitButton = By.xpath("//button[normalize-space()='Submit']");
    private final By projectSuggestion = By.xpath("//div[@role='listbox']//span");
    private final By activityOption = By.xpath("//div[@role='listbox']//span");
    private final By timesheetStatus = By.xpath("//p[contains(normalize-space(),'Status:')]");
    private final By employeeTimesheetsTab = By.xpath("//a[normalize-space()='Employee Timesheets']");
    private final By employeeNameField = By.xpath("//input[@placeholder='Type for hints...']");
    private final By employeeSuggestion = By.xpath("//div[@role='listbox']//span");
    private final By viewButton = By.xpath("//button[normalize-space()='View']");
    private final By approveButton = By.xpath("//button[normalize-space()='Approve']");

    public TimePage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilLoaded() {
        waitForPageToLoad(timesheetsMenu);
    }

    public void openMyTimesheet() {
        logger.info("Opening My Timesheet.");
        click(timesheetsMenu);

    }

    public void openEmployeeTimesheets() {
        click(timesheetsMenu);
        click(employeeTimesheetsOption);
        waitForPageToLoad(timeHeader);
    }

    public void editTimesheet() {
        click(editButton);
        waitForPageToLoad(projectField);
    }

    public void fillTimesheet(Employee employee) {

        logger.info("Entering project hours.");

        editTimesheet();

        type(projectField, employee.getProject());

        click(projectSuggestion);


        click(activityDropdown);
        click(By.xpath("//div[@role='listbox']//span[text()='"
                + employee.getActivity() + "']"));

        type(mondayHoursField, employee.getMondayHours());

        click(saveButton);

        waitForLoaderToDisappear();

        click(submitButton);

        logger.info("Submitting timesheet.");

        waitForLoaderToDisappear();
    }

    public String getTimesheetStatus() {
        waitForTextToAppear(timesheetStatus);
        return getText(timesheetStatus);

    }

    public void openEmployeeTimesheet(Employee employee) {


        type(employeeNameField, employee.getFirstName());

        click(employeeSuggestion);

        click(viewButton);
    }
    public void approveTimesheet() {

        click(approveButton);

        logger.info("Approving timesheet.");

        waitForLoaderToDisappear();
    }
}