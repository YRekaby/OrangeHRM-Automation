package StepDefs;

import Context.TestContext;
import Models.Employee;
import Pages.*;
import Utils.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class EmergencyDependentStepDef {
    private static final Logger logger = LoggerUtil.getLogger(EmergencyDependentStepDef.class);
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyInfoPage myInfoPage;


    @Given("Employee logs in")
    public void employeeLogsIn() {

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

        myInfoPage = dashboardPage.navigateToMyInfo();
    }
    @When("Employee deletes the existing emergency contact")
    public void employeeDeletesTheExistingEmergencyContact() {
        myInfoPage.deleteEmergencyContact();
    }

    @When("Employee adds a new emergency contact")
    public void employeeAddsANewEmergencyContact() {
        myInfoPage.updateEmergencyContact(TestContext.getEmployee());
    }


    @When("Employee adds a dependent")
    public void employeeAddsADependent() {

        myInfoPage.addDependent(TestContext.getEmployee());
    }

    @Then("Emergency contact and dependent should be saved successfully")
    public void emergencyContactAndDependentShouldBeSavedSuccessfully() {

        myInfoPage.waitUntilSaved();
    }

    @Given("Admin logs into the system")
    public void adminLogsIntoTheSystem() {
        dashboardPage.logout();
        loginPage = new LoginPage(DriverFactory.getDriver());

        dashboardPage = loginPage.login(JsonFileManager.getValue("adminUsername"), JsonFileManager.getValue("adminPassword"));
    }

    @When("Admin navigates to employee profile")
    public void adminNavigatesToEmployeeProfile() {
        Employee employee = TestContext.getEmployee();

        PIMPage pimPage = dashboardPage.navigateToPIM();

        EmployeeListPage employeeListPage = pimPage.navigateToEmployeeList();

        employeeListPage.searchEmployee(employee.getFirstName() + " " + employee.getLastName());
        myInfoPage = employeeListPage.openEmployeeProfile();
    }

    @Then("Admin reviews and confirm updates")
    public void adminReviewsAndConfirmUpdates() {
        Employee employee = TestContext.getEmployee();

        myInfoPage.openEmergencyContacts();
        try {
            Assert.assertEquals(myInfoPage.getEmergencyContactName(), employee.getEmergencyContactName());
            Assert.assertEquals(myInfoPage.getEmergencyContactRelationship(), employee.getEmergencyRelationship());
            Assert.assertEquals(myInfoPage.getEmergencyContactNumber(), employee.getEmergencyContactNumber());
            logger.info("Assertion PASSED: Emergency contact added successfully");
        } catch (AssertionError e) {
            logger.error("Assertion FAILED: Emergency contact not added!.", e);
            throw e;
        }



        myInfoPage.openDependents();
        try {
            Assert.assertEquals(myInfoPage.getDependentName(), employee.getDependentName());
            Assert.assertEquals(myInfoPage.getDependentRelationship(), employee.getDependentRelationship());
            Assert.assertEquals(myInfoPage.getDependentDOB(), employee.getDependentDob());
            logger.info("Assertion PASSED: Dependent added successfully");
        } catch (AssertionError e) {
            logger.error("Assertion FAILED: Dependent not added!.", e);
            throw e;
        }






    }

}

