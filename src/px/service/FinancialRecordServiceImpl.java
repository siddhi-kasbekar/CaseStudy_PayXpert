package px.service;

import px.dao.IFinancialRecordService;
import px.entity.FinancialRecord;
import px.exception.DatabaseConnectionException;
import px.exception.FinancialRecordException;
import px.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialRecordServiceImpl implements IFinancialRecordService {

    // SQL Queries
    private static final String INSERT_FINANCIAL_RECORD =
            "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_FINANCIAL_RECORD_BY_ID =
            "SELECT * FROM FinancialRecord WHERE RecordID = ?";
    private static final String SELECT_FINANCIAL_RECORDS_FOR_EMPLOYEE =
            "SELECT * FROM FinancialRecord WHERE EmployeeID = ?";
    private static final String SELECT_FINANCIAL_RECORDS_FOR_DATE =
            "SELECT * FROM FinancialRecord WHERE RecordDate = ?";

    @Override
    public void addFinancialRecord(int employeeId, String description, BigDecimal amount, String recordType)
            throws FinancialRecordException, DatabaseConnectionException {
        try {
            try (Connection connection = DBUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FINANCIAL_RECORD,
                         Statement.RETURN_GENERATED_KEYS)) {

                // Set parameters for the prepared statement
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setDate(2, Date.valueOf(LocalDate.now())); // Assuming current date for RecordDate
                preparedStatement.setString(3, description);
                preparedStatement.setBigDecimal(4, amount);
                preparedStatement.setString(5, recordType);

                // Execute the update and retrieve the generated key (RecordID)
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new FinancialRecordException("Failed to add financial record, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int recordId = generatedKeys.getInt(1);
                        // Further logic if needed
                    } else {
                        throw new FinancialRecordException("Failed to add financial record, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error adding financial record");
        }
    }

    @Override
    public FinancialRecord getFinancialRecordById(int recordId) throws DatabaseConnectionException {
        // Your logic to retrieve a financial record by ID
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FINANCIAL_RECORD_BY_ID)) {

            preparedStatement.setInt(1, recordId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractFinancialRecordFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching financial record by ID");
        }
        return null;
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) throws DatabaseConnectionException {
        // Your logic to retrieve all financial records for a specific employee
        List<FinancialRecord> financialRecords = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FINANCIAL_RECORDS_FOR_EMPLOYEE)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    financialRecords.add(extractFinancialRecordFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching financial records for employee");
        }
        return financialRecords;
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate) throws DatabaseConnectionException {
        // Your logic to retrieve all financial records for a specific date
        List<FinancialRecord> financialRecords = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FINANCIAL_RECORDS_FOR_DATE)) {

            preparedStatement.setDate(1, Date.valueOf(recordDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    financialRecords.add(extractFinancialRecordFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error fetching financial records for date");
        }
        return financialRecords;
    }

    private FinancialRecord extractFinancialRecordFromResultSet(ResultSet resultSet) throws SQLException {
        FinancialRecord financialRecord = new FinancialRecord();
        financialRecord.setRecordID(resultSet.getInt("RecordID"));
        financialRecord.setEmployeeID(resultSet.getInt("EmployeeID"));
        financialRecord.setRecordDate(resultSet.getDate("RecordDate").toLocalDate());
        financialRecord.setDescription(resultSet.getString("Description"));
        financialRecord.setAmount(resultSet.getBigDecimal("Amount"));
        financialRecord.setRecordType(resultSet.getString("RecordType"));
        // Set other properties based on your database schema
        return financialRecord;
    }
}

