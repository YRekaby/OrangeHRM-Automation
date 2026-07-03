package Context;

import Models.Employee;

public class TestContext {
    private static Employee employee;
    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        TestContext.employee = employee;
    }

    public static void clear() {
        employee = null;
    }

}
