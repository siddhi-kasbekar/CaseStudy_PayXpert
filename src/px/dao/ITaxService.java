package px.dao;

import px.entity.Tax;
import px.exception.DatabaseConnectionException;
import px.exception.TaxCalculationException;

import java.util.List;

public interface ITaxService {
    void calculateTax(int employeeId, int taxYear) throws DatabaseConnectionException,TaxCalculationException;

    Tax getTaxById(int taxId) throws DatabaseConnectionException;

    List<Tax> getTaxesForEmployee(int employeeId) throws DatabaseConnectionException;

    List<Tax> getTaxesForYear(int taxYear) throws DatabaseConnectionException;
}
