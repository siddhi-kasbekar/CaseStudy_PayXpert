package px.dao;

import px.entity.Payroll;
import px.exception.DatabaseConnectionException;
import px.exception.PayrollGenerationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IPayrollService {
    void generatePayroll(int payrollID,int employeeId,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal basicSalary,
            BigDecimal overtimePay,
            BigDecimal deductions,
            BigDecimal netSalary) throws DatabaseConnectionException,PayrollGenerationException;

    Payroll getPayrollById(int payrollId)throws DatabaseConnectionException;

    List<Payroll> getPayrollsForEmployee(int employeeId) throws DatabaseConnectionException;

    List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate) throws DatabaseConnectionException;
}

