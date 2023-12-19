package px.dao;

import px.entity.FinancialRecord;
import px.exception.DatabaseConnectionException;
import px.exception.FinancialRecordException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IFinancialRecordService {
    void addFinancialRecord(int employeeId, String description, BigDecimal amount, String recordType)
            throws DatabaseConnectionException,FinancialRecordException;

    FinancialRecord getFinancialRecordById(int recordId) throws DatabaseConnectionException;

    List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) throws DatabaseConnectionException;

    List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate) throws DatabaseConnectionException;
}
