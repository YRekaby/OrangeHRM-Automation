package Pages;

import Utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;



    public BasePage(WebDriver driver) {

        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected final Logger logger = LoggerUtil.getLogger(getClass());


    protected void click(By locator) {
        waitForLoaderToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        waitForLoaderToDisappear();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.click();

        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isDisplayed(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    protected String getAttribute(By locator, String attribute) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getAttribute(attribute);
    }

    protected void waitForLoaderToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".oxd-form-loader")));
    }

    protected void waitForPageToLoad(By uniqueElement) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(uniqueElement));
    }

    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected WebElement waitForClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForAttributeToHaveValue(By locator) {
        wait.until(driver -> {
            String value = driver.findElement(locator).getAttribute("value");
            return value != null && !value.trim().isEmpty();
        });
    }
    protected void waitForTextToAppear(By locator) {
        wait.until(driver -> {
            String text = driver.findElement(locator).getText();
            return text != null && !text.trim().isEmpty();
        });
    }

    private final By successToast = By.cssSelector(".oxd-toast.oxd-toast--success");
    public void waitForSuccessToast() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successToast));
    }
}