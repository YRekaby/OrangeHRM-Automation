package Hook;

import Context.TestContext;
import Utils.DriverFactory;
import Utils.JsonFileManager;
import Utils.LoggerUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.apache.logging.log4j.Logger;




public class Hooks {

    private static final Logger logger = LoggerUtil.getLogger(Hooks.class);


    @Before
    public void setUp(Scenario scenario) {
        logger.info("========== Scenario Started: {} ==========", scenario.getName());

        TestContext.clear();
        String browser = JsonFileManager.getValue("browser");
        DriverFactory.getDriver(browser);
        DriverFactory.getDriver().get(JsonFileManager.getValue("url"));
    }


    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {

            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", scenario.getName() + " Failed");

            logger.error("Scenario FAILED: {}", scenario.getName());

        } else {
            logger.info("Scenario PASSED: {}", scenario.getName());
        }

        logger.info("========== Scenario Finished: {} [{}] ==========", scenario.getName(), scenario.getStatus());
        DriverFactory.quitDriver();
    }
}



