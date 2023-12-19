package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import px.dao.IPayrollService;
import px.entity.Payroll;
import px.exception.DatabaseConnectionException;
import px.exception.PayrollGenerationException;
import px.service.PayrollServiceImpl;
import px.util.DBUtil;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;


class PayrollServiceImplTest {

	private IPayrollService payrollService;
    private Connection connection;

    @BeforeEach
    void setUp() throws DatabaseConnectionException, SQLException {
        // Connect  existing database for testing
        connection = DBUtil.getConnection();

        payrollService = new PayrollServiceImpl();
    }

    @Test
    void testGeneratePayroll() {
        try {
            // Test case for generating payroll
            payrollService.generatePayroll(2, 2,
                    LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
                    BigDecimal.valueOf(5000), BigDecimal.valueOf(200), BigDecimal.valueOf(100),
                    BigDecimal.valueOf(5100));

            assertTrue(true);
        } catch (PayrollGenerationException | DatabaseConnectionException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
    
    @Test
    void testGetPayrollsForEmployee() {
        try {

            List<Payroll> payrolls = payrollService.getPayrollsForEmployee(1);

            assertNotNull(payrolls);
            
            assertEquals(1, payrolls.size()); // expected number of rows
            System.out.println(" Payrolls: " + payrolls);

        } catch ( DatabaseConnectionException e) {
            // If an exception occurs, fail the test
            fail("Exception thrown: " + e.getMessage());
        }
    }

}
