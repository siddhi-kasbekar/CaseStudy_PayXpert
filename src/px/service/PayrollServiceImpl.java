package px.service;

import px.dao.IPayrollService;
import px.entity.Payroll;
import px.exception.DatabaseConnectionException;
import px.exception.PayrollGenerationException;
import px.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollServiceImpl implements IPayrollService {

	// SQL Queries
	private static final String INSERT_PAYROLL = "INSERT INTO Payroll (payrollID,EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_PAYROLL_BY_ID = "SELECT * FROM Payroll WHERE PayrollID = ?";
	private static final String SELECT_PAYROLLS_FOR_EMPLOYEE = "SELECT * FROM Payroll WHERE EmployeeID = ?";
	private static final String SELECT_PAYROLLS_FOR_PERIOD = "SELECT * FROM Payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ?";

	@Override
	public void generatePayroll(int payrollID, int employeeId, LocalDate startDate, LocalDate endDate,
			BigDecimal basicSalary, BigDecimal overtimePay, BigDecimal deductions, BigDecimal netSalary)
			throws PayrollGenerationException, DatabaseConnectionException {
// Your logic for generating payroll for the specified employee and date range
		try {
// Sample code to insert a payroll record into the database
			try (Connection connection = DBUtil.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PAYROLL)) {

// Set parameters for the prepared statement
				preparedStatement.setInt(1, payrollID);
				preparedStatement.setInt(2, employeeId);
				preparedStatement.setDate(3, Date.valueOf(startDate));
				preparedStatement.setDate(4, Date.valueOf(endDate));
				preparedStatement.setBigDecimal(5, basicSalary);
				preparedStatement.setBigDecimal(6, overtimePay);
				preparedStatement.setBigDecimal(7, deductions);
				preparedStatement.setBigDecimal(8, netSalary);

// Execute the update
				int affectedRows = preparedStatement.executeUpdate();
				if (affectedRows == 0) {
					throw new PayrollGenerationException("Failed to generate payroll, no rows affected.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException("Error generating payroll");
		}
	}

	@Override
	public Payroll getPayrollById(int payrollId) throws DatabaseConnectionException {
		// Your logic to retrieve a payroll record by ID
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAYROLL_BY_ID)) {

			preparedStatement.setInt(1, payrollId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return extractPayrollFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException("Error fetching payroll by ID");
		}
		return null;
	}

	@Override
	public List<Payroll> getPayrollsForEmployee(int employeeId) throws DatabaseConnectionException {
		// Your logic to retrieve all payroll records for a specific employee
		List<Payroll> payrolls = new ArrayList<>();
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAYROLLS_FOR_EMPLOYEE)) {

			preparedStatement.setInt(1, employeeId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					payrolls.add(extractPayrollFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException("Error fetching payrolls for employee");
		}
		return payrolls;
	}

	@Override
	public List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate)
			throws DatabaseConnectionException {
		// Your logic to retrieve all payroll records for a specific date range
		List<Payroll> payrolls = new ArrayList<>();
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAYROLLS_FOR_PERIOD)) {

			preparedStatement.setDate(1, Date.valueOf(startDate));
			preparedStatement.setDate(2, Date.valueOf(endDate));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					payrolls.add(extractPayrollFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException("Error fetching payrolls for period");
		}
		return payrolls;
	}

	private Payroll extractPayrollFromResultSet(ResultSet resultSet) throws SQLException {
		Payroll payroll = new Payroll();
		payroll.setPayrollID(resultSet.getInt("PayrollID"));
		payroll.setEmployeeID(resultSet.getInt("EmployeeID"));
		payroll.setPayPeriodStartDate(resultSet.getDate("PayPeriodStartDate").toLocalDate());
		payroll.setPayPeriodEndDate(resultSet.getDate("PayPeriodEndDate").toLocalDate());
		payroll.setBasicSalary(resultSet.getBigDecimal("BasicSalary"));
		payroll.setOvertimePay(resultSet.getBigDecimal("OvertimePay"));
		payroll.setDeductions(resultSet.getBigDecimal("Deductions"));
		payroll.setNetSalary(resultSet.getBigDecimal("NetSalary"));
		// Set other properties based on your database schema
		return payroll;
	}

	private void setPayrollDataToStatement(Payroll payrollData, PreparedStatement preparedStatement)
			throws SQLException {
		preparedStatement.setInt(1, payrollData.getPayrollID());
		preparedStatement.setInt(2, payrollData.getEmployeeID());
		preparedStatement.setDate(3, Date.valueOf(payrollData.getPayPeriodStartDate()));
		preparedStatement.setDate(4, Date.valueOf(payrollData.getPayPeriodEndDate()));
		preparedStatement.setBigDecimal(5, payrollData.getBasicSalary());
		preparedStatement.setBigDecimal(6, payrollData.getOvertimePay());
		preparedStatement.setBigDecimal(7, payrollData.getDeductions());
		preparedStatement.setBigDecimal(8, payrollData.getNetSalary());
		// Set other properties based on your database schema
	}

}
