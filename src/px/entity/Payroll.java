package px.entity;

import java.util.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payroll {
    private int payrollID;
    private int employeeID;  
    private LocalDate payPeriodStartDate;
    private LocalDate payPeriodEndDate;
    private BigDecimal basicSalary;
    private BigDecimal overtimePay;
    private BigDecimal deductions;
    private BigDecimal netSalary;

    // Default constructor
    public Payroll() {
    }

    // Parameterized constructor
    public Payroll(int payrollID, int employeeID, LocalDate payPeriodStartDate,
                   LocalDate payPeriodEndDate, BigDecimal basicSalary, BigDecimal overtimePay,
                   BigDecimal deductions, BigDecimal netSalary) {
        this.payrollID = payrollID;
        this.employeeID = employeeID;
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = payPeriodEndDate;
        this.basicSalary = basicSalary;
        this.overtimePay = overtimePay;
        this.deductions = deductions;
        this.netSalary = netSalary;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollID=" + payrollID +
                ", employeeID=" + employeeID +
                ", payPeriodStartDate=" + payPeriodStartDate +
                ", payPeriodEndDate=" + payPeriodEndDate +
                ", basicSalary=" + basicSalary +
                ", overtimePay=" + overtimePay +
                ", deductions=" + deductions +
                ", netSalary=" + netSalary +
                '}';
    }


    // Getters and Setters

    public int getPayrollID() {
        return payrollID;
    }

    public void setPayrollID(int payrollID) {
        this.payrollID = payrollID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDate getPayPeriodStartDate() {
        return payPeriodStartDate;
    }

    public void setPayPeriodStartDate(LocalDate payPeriodStartDate) {
        this.payPeriodStartDate = payPeriodStartDate;
    }

    public LocalDate getPayPeriodEndDate() {
        return payPeriodEndDate;
    }

    public void setPayPeriodEndDate(LocalDate payPeriodEndDate) {
        this.payPeriodEndDate = payPeriodEndDate;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(BigDecimal overtimePay) {
        this.overtimePay = overtimePay;
    }

    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }
}

