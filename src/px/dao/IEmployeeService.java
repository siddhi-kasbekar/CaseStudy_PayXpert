package px.dao;


import px.entity.Employee;
import px.exception.DatabaseConnectionException;
import px.exception.EmployeeNotFoundException;
import px.exception.InvalidInputException;

import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException,DatabaseConnectionException;

    List<Employee> getAllEmployees() throws DatabaseConnectionException;

    void addEmployee(Employee employeeData) throws DatabaseConnectionException,InvalidInputException;

    void updateEmployee(Employee employeeData) throws DatabaseConnectionException;

    void removeEmployee(int employeeId) throws DatabaseConnectionException;
}
