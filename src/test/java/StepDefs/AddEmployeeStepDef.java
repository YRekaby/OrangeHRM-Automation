package StepDefs;

import Context.TestContext;
import Models.Employee;
import Pages.*;
import Utils.DriverFactory;
import Utils.ExcelManager;
import Utils.JsonFileManager;
import Utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddEmployeeStepDef {
    private static final Logger logger = LoggerUtil.getLogger(AddEmployeeStepDef.class);
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PIMPage pimPage;
    private AddEmployeePage addEmployeePage;
    private PersonalDetailsPage personalDetailsPage;
    private UserManagementPage userManagementPage;
    private EditUserPage editUserPage;

    @Given("Admin is logged in")
    public void adminLoggedIn() {

        loginPage = new LoginPage(DriverFactory.getDriver());

                dashboardPage = loginPage.login(
                        JsonFileManager.getValue("adminUsername"),
                        JsonFileManager.getValue("adminPassword")
                );

    }

    @When("Admin navigates to Add Employee Page")
    public void adminNavigatesToAddEmployeePage() {

        pimPage = dashboardPage.navigateToPIM();
        addEmployeePage = pimPage.navigateToAddEmployee();
    }

    @When("Admin enters employee details")
    public void adminEntersEmployeeDetails() {
        Employee employee = ExcelManager.getEmployee(1);
        TestContext.setEmployee(employee);
        addEmployeePage.createEmployee(employee);
    }


    @When("Admin saves the employee")
    public void adminSavesEmployee() {
        addEmployeePage.clickSave();
        personalDetailsPage = new PersonalDetailsPage(DriverFactory.getDriver());
        personalDetailsPage.waitUntilLoaded();
    }

    @When("Admin assigns the employee role")
    public void adminAssignsEmployeeRole() {

        Employee employee = TestContext.getEmployee();
        userManagementPage = dashboardPage.navigateToAdmin();
        editUserPage = userManagementPage.openUser(employee.getUsername());
        editUserPage.updateUserRole(employee.getRole());
    }

    @When("Admin logs out")
    public void adminLogsOut() {
        loginPage = dashboardPage.logout();
    }

    @When("Employee logs in with assigned credentials")
    public void employeeLogsInWithAssignedCredentials() {
        Employee employee = TestContext.getEmployee();

        dashboardPage = loginPage.login(employee.getUsername(), employee.getPassword());

    }

    @Then("Employee should have the correct access rights")
    public void employeeShouldHaveCorrectAccessRights() {

        Employee employee = TestContext.getEmployee();

        if (employee.getRole().equalsIgnoreCase("Admin")) {

            Assert.assertTrue(dashboardPage.isAdminModuleDisplayed(), "Admin module should be visible.");

        } else {

            Assert.assertFalse(dashboardPage.isAdminModuleDisplayed(), "Admin module should not be visible.");
        }
    }



}
