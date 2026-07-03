package Utils;

import Models.Employee;
import Pages.*;
import org.apache.logging.log4j.Logger;


public class EmployeeManager {
    private static final Logger logger = LoggerUtil.getLogger(EmployeeManager.class);

    public static void createEmployee(Employee employee) {

        logger.info("Creating a new employee");

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        DashboardPage dashboard = loginPage.login(JsonFileManager.getValue("adminUsername"), JsonFileManager.getValue("adminPassword"));

        PIMPage pimPage = dashboard.navigateToPIM();
        AddEmployeePage addEmployeePage = pimPage.navigateToAddEmployee();
        addEmployeePage.createEmployee(employee);
        addEmployeePage.clickSave();

        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(DriverFactory.getDriver());
        personalDetailsPage.waitUntilLoaded();

        UserManagementPage userManagementPage = dashboard.navigateToAdmin();

        EditUserPage editUserPage = userManagementPage.openUser(employee.getUsername());

        editUserPage.updateUserRole(employee.getRole());

        dashboard.logout();
    }
}