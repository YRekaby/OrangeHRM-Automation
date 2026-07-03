package StepDefs;

import Context.TestContext;
import Models.Employee;
import Pages.*;
import Utils.DriverFactory;
import Utils.EmployeeManager;
import Utils.ExcelManager;
import Utils.JsonFileManager;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class TimesheetStepDef {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private TimePage timePage;
    private PIMPage pimPage;
    private AddEmployeePage addEmployeePage;

    @Given("Employee logs in system")
    public void employeeIsLoggedIn() {

        Employee employee = ExcelManager.getEmployee(1);

        loginPage = new LoginPage(DriverFactory.getDriver());

        dashboardPage = loginPage.login(
                employee.getUsername(),
                employee.getPassword());

        if (dashboardPage == null) {
            EmployeeManager.createEmployee(employee);

            loginPage = new LoginPage(DriverFactory.getDriver());

            dashboardPage = loginPage.login(
                    employee.getUsername(),
                    employee.getPassword());

        }

    TestContext.setEmployee(employee);
    }

    @When("Employee opens My Timesheet")
    public void employeeOpensMyTimesheet() {

        timePage = dashboardPage.navigateToTime();
        timePage.openMyTimesheet();
    }

    @When("Employee fills and submits the timesheet")
    public void employeeFillsAndSubmitsTheTimesheet() {

        timePage.fillTimesheet(TestContext.getEmployee());
    }

    @Then("Timesheet should be submitted successfully")
    public void timesheetShouldBeSubmittedSuccessfully() {

        timePage.waitUntilLoaded();
        Assert.assertTrue(timePage.getTimesheetStatus().contains("Submitted"));

    }

    @Given("Admin logs into the Time module")
    public void adminLogsIntoTheTimeModule() {
        dashboardPage.logout();
        loginPage = new LoginPage(DriverFactory.getDriver());

        dashboardPage = loginPage.login(
                JsonFileManager.getValue("adminUsername"),
                JsonFileManager.getValue("adminPassword")
        );
    }

    @When("Admin opens the employee submitted timesheet")
    public void adminOpensTheEmployeeSubmittedTimesheet() {
        dashboardPage = new DashboardPage(DriverFactory.getDriver());
        timePage = dashboardPage.navigateToTime();
        timePage.openEmployeeTimesheet(TestContext.getEmployee());
    }

    @When("Admin approves the timesheet")
    public void adminApprovesTheTimesheet() {

        timePage.approveTimesheet();
    }

    @Then("Timesheet should be approved successfully")
    public void timesheetShouldBeApprovedSuccessfully() {

        Assert.assertTrue(timePage.getTimesheetStatus().contains("Approved"));
    }
}