package px.service;

import px.dao.ITaxService;
import px.entity.Tax;
import px.exception.DatabaseConnectionException;
import px.exception.TaxCalculationException;
import px.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxServiceImpl implements ITaxService {

    // SQL Queries
    private static final String INSERT_TAX = "INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) VALUES (?, ?, ?, ?)";
    private static final String SELECT_TAX_BY_ID = "SELECT * FROM Tax WHERE TaxID = ?";
    private static final String SELECT_TAXES_FOR_EMPLOYEE = "SELECT * FROM Tax WHERE EmployeeID = ?";
    private static final String SELECT_TAXES_FOR_YEAR = "SELECT * FROM Tax WHERE TaxYear = ?";

    @Override
    public void calculateTax(int employeeId, int taxYear) throws TaxCalculationException, DatabaseConnectionException {
        // Your logic for calculating tax for the specified employee and tax year
        try {
            // Sample code to insert a tax record into the database
            try (Connection connection = DBUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAX, Statement.RETURN_GENERATED_KEYS)) {

                // Set parameters for the prepared statement
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setInt(2, taxYear);
                // Set other parameters (TaxableIncome, TaxAmount) based on your business logic

                // Execute the update and retrieve the generated key (TaxID)
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new TaxCalculationException("Failed to calculate tax, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int taxId = generatedKeys.getInt(1);
                        // Further logic if needed
                    } else {
                        throw new TaxCalculationException("Failed to calculate tax, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error calculating tax");
        }
    }

    @Override
    public Tax getTaxById(int taxId) throws DatabaseConnectionException {
        // Your logic to retrieve a tax record by ID
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAX_BY_ID)) {

            preparedStatement.setInt(1, taxId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractTaxFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching tax by ID");
        }
        return null;
    }

    @Override
    public List<Tax> getTaxesForEmployee(int employeeId) throws DatabaseConnectionException {
        // Your logic to retrieve all tax records for a specific employee
        List<Tax> taxes = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAXES_FOR_EMPLOYEE)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    taxes.add(extractTaxFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching taxes for employee");
        }
        return taxes;
    }

    @Override
    public List<Tax> getTaxesForYear(int taxYear) throws DatabaseConnectionException {
        // Your logic to retrieve all tax records for a specific tax year
        List<Tax> taxes = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAXES_FOR_YEAR)) {

            preparedStatement.setInt(1, taxYear);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    taxes.add(extractTaxFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching taxes for year");
        }
        return taxes;
    }

    private Tax extractTaxFromResultSet(ResultSet resultSet) throws SQLException {
        Tax tax = new Tax();
        tax.setTaxID(resultSet.getInt("TaxID"));
        // Set other properties based on your database schema
        return tax;
    }
}
