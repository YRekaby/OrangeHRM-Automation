package Runners;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/Features/login.feature",
        glue = {"StepDefs", "Hook"},
        plugin = {"pretty", "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)

public class LoginRunner extends AbstractTestNGCucumberTests {
}
