package px.util;

import px.entity.Payroll;
import px.entity.Tax;
import px.entity.FinancialRecord;

import java.util.List;

public class ReportGenerator {

    public static void generatePayrollReport(List<Payroll> payrolls) {
        // Logic to generate and print payroll report
        System.out.println("Payroll Report:");
        for (Payroll payroll : payrolls) {
            System.out.println("Employee ID: " + payroll.getEmployeeID());
            System.out.println("Period: " + payroll.getPayPeriodStartDate() + " to " + payroll.getPayPeriodEndDate());
            // Add more details as needed
            System.out.println("Net Salary: " + payroll.getNetSalary());
            System.out.println("------------------------");
        }
    }

    public static void generateTaxReport(List<Tax> taxes) {
        // Logic to generate and print tax report
        System.out.println("Tax Report:");
        for (Tax tax : taxes) {
            System.out.println("Employee ID: " + tax.getEmployeeID());
            System.out.println("Tax Year: " + tax.getTaxYear());
            // Add more details as needed
            System.out.println("Tax Amount: " + tax.getTaxAmount());
            System.out.println("------------------------");
        }
    }

    public static void generateFinancialRecordReport(List<FinancialRecord> financialRecords) {
        // Logic to generate and print financial record report
        System.out.println("Financial Record Report:");
        for (FinancialRecord financialRecord : financialRecords) {
            System.out.println("Record ID: " + financialRecord.getRecordID());
            System.out.println("Employee ID: " + financialRecord.getEmployeeID());
            System.out.println("Date: " + financialRecord.getRecordDate());
            // Add more details as needed
            System.out.println("Amount: " + financialRecord.getAmount());
            System.out.println("------------------------");
        }
    }

}

