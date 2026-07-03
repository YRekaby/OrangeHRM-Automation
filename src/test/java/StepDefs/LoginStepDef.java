package StepDefs;

import Pages.DashboardPage;
import Pages.LoginPage;
import Utils.DriverFactory;
import Utils.JsonFileManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginStepDef {

    private DashboardPage dashboardPage;

    @Given("user is on login page")
    public void userIsOnLoginPage(){


    }

    @When("user logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        dashboardPage = loginPage.login(JsonFileManager.getValue("adminUsername"), JsonFileManager.getValue("adminPassword"));
    }


    @Then("dashboard should be displayed")
    public void isDashboardDisplayed() {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed());
    }
}
