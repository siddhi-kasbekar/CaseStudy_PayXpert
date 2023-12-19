package Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Before;
import org.junit.Test;
import px.dao.IEmployeeService;
import px.entity.Employee;
import px.exception.DatabaseConnectionException;
import px.exception.EmployeeNotFoundException;
import px.exception.InvalidInputException;
import px.service.EmployeeServiceImpl;

public class EmployeeServiceImplTest {

    private IEmployeeService employeeService;

    @Before
    public void setUp() {
        // Initialize the employeeService before each test
        employeeService = new EmployeeServiceImpl();
    }

    @Test
    public void testGetEmployeeById() throws EmployeeNotFoundException, DatabaseConnectionException {
        // Test case for successfully retrieving an employee by ID
        Employee employee = employeeService.getEmployeeById(2);
        assertEquals("siddh", employee.getFirstName());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testGetEmployeeByIdNotFound() throws EmployeeNotFoundException, DatabaseConnectionException {
        // Test case for attempting to retrieve a non-existing employee by ID
        employeeService.getEmployeeById(1000);
    }

//    @Test
//    public void testDeleteEmployee() throws EmployeeNotFoundException, DatabaseConnectionException {
//        // Test case for successfully deleting an employee
//        int employeeIdToDelete = 1; // Assuming employee with ID 1 exists
//        employeeService.removeEmployee(employeeIdToDelete);
//
//        // Attempt to retrieve the deleted employee should result in an exception
//        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(employeeIdToDelete));
//    }
    
    @Test
    public void testAddEmployeeWithValidData() {
        // Create an employee with valid data
        Employee validEmployee = createValidEmployee();

        // Use assertDoesNotThrow to verify that no exceptions are thrown
        assertDoesNotThrow(() -> employeeService.addEmployee(validEmployee));
    }

//    @Test
//    void testAddEmployeeWithInvalidData() {
//        // Create an employee with invalid data
//        Employee invalidEmployee = createInvalidEmployee();
//
//        // Use assertThrows to verify that the InvalidInputException is thrown
//        assertThrows(InvalidInputException.class, () ->
//                employeeService.addEmployee(invalidEmployee));
//    }

    private Employee createValidEmployee() {
        // Create an employee with valid data (adjust as needed)
        Employee employee = new Employee();
        employee.setEmployeeID(3);
        employee.setFirstName("Stacy");
        employee.setLastName("Walsh");
        employee.setDateOfBirth("2002-02-25"); // replace someInvalidDate with an invalid date
        employee.setGender("InvalidGender");
        employee.setEmail("stacy.walsh@example.com");
        employee.setPhoneNumber("1234567810");
        employee.setAddress("InvalidAddress");
        employee.setPosition("InvalidPosition");
        employee.setJoiningDate("2022-01-01"); // replace someInvalidDate with an invalid date
        employee.setTerminationDate("2025-01-01");
        return employee;
    }

    private Employee createInvalidEmployee() {
        // Create an employee with invalid data (adjust as needed)
        Employee employee = new Employee();
      employee.setFirstName(null);
         employee.setLastName(null);       
         return employee;
    }
   
}

