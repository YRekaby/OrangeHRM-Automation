package Pages;

import Models.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MyInfoPage extends BasePage {
    private final By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");

    private final By nationalityDropdown = By.xpath("//label[normalize-space()='Nationality']/ancestor::div[contains(@class,'oxd-input-group')]//i");
    private final By maritalStatusDropdown = By.xpath("//label[normalize-space()='Marital Status']/ancestor::div[contains(@class,'oxd-input-group')]//i");
    private final By personalDetailsSaveButton = By.xpath("(//button[@type='submit'])[1]");


    private final By contactDetailsTab = By.xpath("//a[text()='Contact Details']");
    private final By emergencyContactsTab = By.xpath("//a[text()='Emergency Contacts']");

    private final By streetField = By.xpath("//label[normalize-space()='Street 1']/following::input[1]");
    private final By cityField = By.xpath("//label[normalize-space()='City']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By stateField = By.xpath("//label[normalize-space()='State/Province']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By zipCodeField = By.xpath("//label[normalize-space()='Zip/Postal Code']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By mobileField = By.xpath("//label[normalize-space()='Mobile']/parent::div/following-sibling::div/input");
    private final By contactDetailsSaveButton = By.xpath("(//button[@type='submit'])[1]");

    private final By emergencyContactsAddButton = By.xpath("//button[normalize-space()='Add']");
    private final By emergencyNameField = By.xpath("//label[normalize-space()='Name']/parent::div/following-sibling::div/input");
    private final By emergencyRelationshipField = By.xpath("//label[normalize-space()='Relationship']/parent::div/following-sibling::div/input");
    private final By emergencyMobileField = By.xpath("//label[normalize-space()='Mobile']/parent::div/following-sibling::div/input");
    private final By emergencyContactSaveButton = By.xpath("(//button[@type='submit'])[1]");
    private final By emergencyDeleteButton = By.xpath("//div[@class='oxd-table-body']//button[.//i[contains(@class,'bi-trash')]][1]");
    private final By confirmDeleteButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private final By emergencyNameValue = By.xpath(".//div[@role='cell'][2]/div");
    private final By emergencyRelationshipValue = By.xpath(".//div[@role='cell'][3]/div");
    private final By emergencyMobileValue = By.xpath(".//div[@role='cell'][5]/div");


    private final By dependentsTab = By.xpath("//a[normalize-space()='Dependents']");
    private final By addDependentButton = By.xpath("//h6[normalize-space()='Assigned Dependents']/following::button[1]");
    private final By dependentNameField = By.xpath("//label[normalize-space()='Name']/following::input[1]");
    private final By relationshipDropdown = By.xpath("//label[normalize-space()='Relationship']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By dateOfBirthField = By.xpath("//label[normalize-space()='Date of Birth']/following::input[@placeholder='yyyy-dd-mm'][1]");
    private final By saveDependentButton = By.xpath("//button[@type='submit']");

    private final By dependentNameValue = By.xpath("//div[@class='oxd-table-card']//div[@role='cell'][2]/div");
    private final By dependentRelationshipValue = By.xpath("//div[text()='" + dependentNameValue + "']/parent::div/parent::div/div[@role='cell'][3]/div");
    private final By dependentDOBValue = By.xpath("//div[text()='" + dependentNameValue + "']/parent::div/parent::div/div[@role='cell'][4]/div");

    public MyInfoPage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilLoaded() {
        waitForPageToLoad(personalDetailsHeader);
    }
    public void waitUntilSaved() {
        waitForLoaderToDisappear();
    }

    private void selectDropdownValue(By dropdown, String value) {
        waitForLoaderToDisappear();
        waitForClickable(dropdown).click();
        click(By.xpath("//div[@role='listbox']//span[text()='" + value + "']"));
    }

    public void waitUntilUpdated() {
        waitForLoaderToDisappear();
    }


    public void updatePersonalDetails(Employee employee) {

        selectDropdownValue(nationalityDropdown, employee.getNationality());
        selectDropdownValue(maritalStatusDropdown, employee.getMaritalStatus());
        click(personalDetailsSaveButton);
        waitUntilUpdated();
    }

    public void openContactDetails() {
        click(contactDetailsTab);
        waitForPageToLoad(streetField);
    }

    public void updateContactDetails(Employee employee) {
        logger.info("Adding contact details");

        openContactDetails();

        type(streetField, employee.getStreet());
        type(cityField, employee.getCity());
        type(stateField, employee.getState());
        type(zipCodeField, employee.getZipCode());
        type(mobileField, employee.getMobile());

        click(contactDetailsSaveButton);

        waitUntilUpdated();
    }

    public void openEmergencyContacts() {
        click(emergencyContactsTab);
        waitForPageToLoad(emergencyContactsAddButton);
    }

    public void updateEmergencyContact(Employee employee) {
        logger.info("Adding emergency contact");
        openEmergencyContacts();

        click(emergencyContactsAddButton);

        type(emergencyNameField, employee.getEmergencyContactName());
        type(emergencyRelationshipField, employee.getEmergencyRelationship());
        type(emergencyMobileField, employee.getEmergencyContactNumber());

        click(emergencyContactSaveButton);

    }



    public String getStreet() {
        waitForAttributeToHaveValue(streetField);
        return getAttribute(streetField, "value");
    }

    public String getCity() {
        waitForAttributeToHaveValue(cityField);
        return getAttribute(cityField, "value");
    }

    public String getState() {
        waitForAttributeToHaveValue(stateField);
        return getAttribute(stateField, "value");
    }

    public String getZipCode() {
        waitForAttributeToHaveValue(zipCodeField);
        return getAttribute(zipCodeField, "value");
    }

    public String getMobile() {
        waitForAttributeToHaveValue(mobileField);
        return getAttribute(mobileField, "value");
    }

    public String getEmergencyContactName() {
        waitForTextToAppear(emergencyNameValue);
        return getText(emergencyNameValue);
    }

    public String getEmergencyContactRelationship() {
        waitForTextToAppear(emergencyRelationshipValue);
        return getText(emergencyRelationshipValue);
    }

    public String getEmergencyContactNumber() {
        waitForTextToAppear(emergencyMobileValue);
        return getText(emergencyMobileValue);
    }


    public void deleteEmergencyContact() {

        openEmergencyContacts();

        if (isPresent(emergencyDeleteButton)) {

            click(emergencyDeleteButton);
            click(confirmDeleteButton);

            waitUntilUpdated();
        }
    }
    public String getDependentName() {
        waitForTextToAppear(dependentNameValue);
        return getText(dependentNameValue);
    }

    public String getDependentRelationship() {
        waitForTextToAppear(dependentRelationshipValue);
        return getText(dependentRelationshipValue);
    }

    public String getDependentDOB() {
        waitForTextToAppear(dependentDOBValue);
        return getText(dependentDOBValue);
    }
    public void openDependents() {
        click(dependentsTab);
        waitForPageToLoad(addDependentButton);
    }

    public void addDependent(Employee employee) {
        logger.info("Adding dependent");

        openDependents();

        click(addDependentButton);

        type(dependentNameField, employee.getDependentName());

        click(relationshipDropdown);
        click(By.xpath("//div[@role='listbox']//span[text()='"
                + employee.getDependentRelationship() + "']"));

        type(dateOfBirthField, employee.getDependentDob());

        click(saveDependentButton);

        waitUntilUpdated();
    }
}