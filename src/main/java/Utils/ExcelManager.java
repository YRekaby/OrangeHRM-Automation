package Utils;

import Models.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelManager {

    private static final String FILE_PATH =
            "src/test/resources/TestData/Employees.xlsx";

    private static final DataFormatter formatter = new DataFormatter();

    public static Employee getEmployee(int rowNumber) {

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Employees");

            if (sheet == null) {
                throw new RuntimeException("Sheet 'Employees' was not found.");
            }

            // Read the header row
            Row headerRow = sheet.getRow(0);

            Map<String, Integer> columns = new HashMap<>();

            for (Cell cell : headerRow) {
                columns.put(
                        formatter.formatCellValue(cell).trim(),
                        cell.getColumnIndex()
                );
            }

            // Read employee row
            Row row = sheet.getRow(rowNumber);

            if (row == null) {
                throw new RuntimeException(
                        "Row " + rowNumber + " was not found."
                );
            }

            Employee employee = new Employee();

            employee.setFirstName(getCellValue(row, columns, "FirstName"));
            employee.setLastName(getCellValue(row, columns, "LastName"));
            employee.setUsername(getCellValue(row, columns, "Username"));
            employee.setPassword(getCellValue(row, columns, "Password"));
            employee.setRole(getCellValue(row, columns, "Role"));

            employee.setNationality(getCellValue(row, columns, "Nationality"));
            employee.setMaritalStatus(getCellValue(row, columns, "MaritalStatus"));

            employee.setStreet(getCellValue(row, columns, "Street"));
            employee.setCity(getCellValue(row, columns, "City"));
            employee.setState(getCellValue(row, columns, "State"));
            employee.setZipCode(getCellValue(row, columns, "ZipCode"));
            employee.setMobile(getCellValue(row, columns, "Mobile"));

            employee.setEmergencyContactName(getCellValue(row, columns, "EmergencyContactName"));
            employee.setEmergencyRelationship(getCellValue(row, columns, "EmergencyRelationship"));
            employee.setEmergencyContactNumber(getCellValue(row, columns, "EmergencyContactNumber"));

            employee.setDependentName(getCellValue(row, columns, "DependentName"));
            employee.setDependentRelationship(getCellValue(row, columns, "DependentRelationship"));
            employee.setDependentDob(getCellValue(row, columns, "DependentDOB"));

            employee.setProject(getCellValue(row, columns, "Project"));
            employee.setActivity(getCellValue(row, columns ,"Activity"));
            employee.setMondayHours(getCellValue(row, columns, "MondayHours"));

            return employee;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to read Employees.xlsx",
                    e
            );
        }
    }

    private static String getCellValue(Row row,
                                       Map<String, Integer> columns,
                                       String columnName) {

        Integer columnIndex = columns.get(columnName);

        if (columnIndex == null) {
            throw new RuntimeException(
                    "Column '" + columnName + "' was not found in Excel."
            );
        }

        Cell cell = row.getCell(columnIndex);

        return formatter.formatCellValue(cell).trim();
    }
}