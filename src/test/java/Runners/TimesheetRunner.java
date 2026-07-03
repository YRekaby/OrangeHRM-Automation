package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features/Timesheet.feature",
        glue = {"StepDefs","Hook"},
        plugin = {"pretty", "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)
public class TimesheetRunner extends AbstractTestNGCucumberTests {
}