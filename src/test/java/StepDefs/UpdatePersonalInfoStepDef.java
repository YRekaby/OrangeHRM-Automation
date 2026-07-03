package StepDefs;

import Context.TestContext;
import Models.Employee;
import Pages.*;
import Utils.*;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;



public class UpdatePersonalInfoStepDef {

    private static final Logger logger = LoggerUtil.getLogger(UpdatePersonalInfoStepDef.class);

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyInfoPage myInfoPage;
    private PIMPage pimPage;
    private EmployeeListPage employeeListPage;

    @Given("Employee is logged in")
    public void employeeIsLoggedIn() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        Employee employee = ExcelManager.getEmployee(1);
        dashboardPage = loginPage.login(employee.getUsername(), employee.getPassword());

        if (dashboardPage == null) {
            EmployeeManager.createEmployee(employee);

            loginPage = new LoginPage(DriverFactory.getDriver());

            dashboardPage = loginPage.login(
                    employee.getUsername(),
                    employee.getPassword());

        }
        TestContext.setEmployee(employee);
    }

    @When("Employee updates personal information")
    public void employeeUpdatesPersonalInformation() {

        Employee employee = TestContext.getEmployee();

        myInfoPage = dashboardPage.navigateToMyInfo();

        myInfoPage.updatePersonalDetails(employee);
        myInfoPage.updateContactDetails(employee);
        myInfoPage.updateEmergencyContact(employee);
    }

    @When("Employee logs out")
    public void employeeLogsOut() {
        loginPage = dashboardPage.logout();
    }

    @When("Admin logs in")
    public void adminLogsIn() {

        dashboardPage = loginPage.login(
                JsonFileManager.getValue("adminUsername"),
                JsonFileManager.getValue("adminPassword")
        );
    }

    @When("Admin opens employee profile")
    public void adminOpensEmployeeProfile() {

        Employee employee = TestContext.getEmployee();

        pimPage = dashboardPage.navigateToPIM();

        employeeListPage = pimPage.navigateToEmployeeList();

        employeeListPage.searchEmployee(employee.getFirstName() + " " + employee.getLastName());
        myInfoPage = employeeListPage.openEmployeeProfile();
    }

    @Then("Admin should see the updated personal information")

    public void adminShouldSeeUpdatedPersonalInformation() {

        Employee employee = TestContext.getEmployee();


        myInfoPage.openContactDetails();
        try {
            Assert.assertEquals(myInfoPage.getStreet(), employee.getStreet());
            Assert.assertEquals(myInfoPage.getCity(), employee.getCity());
            Assert.assertEquals(myInfoPage.getState(), employee.getState());
            Assert.assertEquals(myInfoPage.getZipCode(), employee.getZipCode());
            Assert.assertEquals(myInfoPage.getMobile(), employee.getMobile());
            logger.info("Assertion PASSED: Personal Info Updated successfully");
        } catch (AssertionError e) {
            logger.error("Assertion FAILED: Info not updated!.", e);
            throw e;
        }
    }

}