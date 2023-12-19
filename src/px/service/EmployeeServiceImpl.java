package px.service;

import px.dao.IEmployeeService;
import px.entity.Employee;
import px.exception.EmployeeNotFoundException;
import px.exception.DatabaseConnectionException;
import px.util.DBUtil;
import px.exception.InvalidInputException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements IEmployeeService {

    // SQL Queries
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM Employee WHERE EmployeeID = ?";
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM Employee";
    private static final String INSERT_EMPLOYEE = "INSERT INTO Employee (employeeID,FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE Employee SET FirstName=?, LastName=?, DateOfBirth=?, Gender=?, Email=?, PhoneNumber=?, Address=?, Position=?, JoiningDate=?, TerminationDate=? WHERE EmployeeID=?";
    private static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM Employee WHERE EmployeeID=?";

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException, DatabaseConnectionException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {
            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractEmployeeFromResultSet(resultSet);
                } else {
                    throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching employee by ID");
        }
    }

    @Override
    public List<Employee> getAllEmployees() throws DatabaseConnectionException {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_EMPLOYEES)) {

            while (resultSet.next()) {
                employees.add(extractEmployeeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching all employees");
        }
        return employees;
    }

    @Override
    public void addEmployee(Employee employeeData) throws DatabaseConnectionException, InvalidInputException {
        ValidationService.validateEmployeeData(employeeData);

        // Rest of your code for adding the employee
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE)) {

            setEmployeeDataToStatement(employeeData, preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error adding employee");
        }
    }


    @Override
    public void updateEmployee(Employee employeeData) throws DatabaseConnectionException {
        try {
            ValidationService.validateEmployeeData(employeeData);
        } catch (InvalidInputException e) {
            e.printStackTrace();
//            throw e; // Rethrow the exception to indicate validation failure
        }

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE)) {

            setEmployeeDataToStatement(employeeData, preparedStatement);
            preparedStatement.setInt(11, employeeData.getEmployeeID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error updating employee");
        }
    }

    @Override
    public void removeEmployee(int employeeId) throws DatabaseConnectionException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)) {

            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error removing employee");
        }
    }

    private Employee extractEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeID(resultSet.getInt("EmployeeID"));
        employee.setFirstName(resultSet.getString("FirstName"));
        employee.setLastName(resultSet.getString("LastName"));
        employee.setDateOfBirth(resultSet.getDate("DateOfBirth").toLocalDate());
        employee.setGender(resultSet.getString("Gender"));
        employee.setEmail(resultSet.getString("Email"));
        employee.setPhoneNumber(resultSet.getString("PhoneNumber"));
        employee.setAddress(resultSet.getString("Address"));
        employee.setPosition(resultSet.getString("Position"));
        employee.setJoiningDate(resultSet.getDate("JoiningDate").toLocalDate());
        
        // Check if TerminationDate is null in the database before setting it
        Date terminationDate = resultSet.getDate("TerminationDate");
        if (terminationDate != null) {
            employee.setTerminationDate(terminationDate.toLocalDate());
        }

        // Set other properties as needed

        return employee;
    }


    private void setEmployeeDataToStatement(Employee employeeData, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, employeeData.getEmployeeID());
    	preparedStatement.setString(2, employeeData.getFirstName());
        preparedStatement.setString(3, employeeData.getLastName());
        preparedStatement.setDate(4, Date.valueOf(employeeData.getDateOfBirth()));
        preparedStatement.setString(5, employeeData.getGender());
        preparedStatement.setString(6, employeeData.getEmail());
        preparedStatement.setString(7, employeeData.getPhoneNumber());
        preparedStatement.setString(8, employeeData.getAddress());
        preparedStatement.setString(9, employeeData.getPosition());
        preparedStatement.setDate(10, Date.valueOf(employeeData.getJoiningDate()));
        preparedStatement.setDate(11, employeeData.getTerminationDate() != null ?
                Date.valueOf(employeeData.getTerminationDate()) : null);
    }
}
