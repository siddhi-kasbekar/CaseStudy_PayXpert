package Main;

import px.dao.*;
import px.entity.Employee;
import px.entity.Payroll;
import px.entity.Tax;
import px.entity.FinancialRecord;
import px.exception.DatabaseConnectionException;
import px.exception.EmployeeNotFoundException;
import px.exception.PayrollGenerationException;
import px.exception.TaxCalculationException;
import px.exception.FinancialRecordException;
import px.exception.InvalidInputException;
import px.util.ReportGenerator;
import px.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Payxpert {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private static final IEmployeeService employeeService = new EmployeeServiceImpl();
    private static final IPayrollService payrollService = new PayrollServiceImpl();
    private static final ITaxService taxService = new TaxServiceImpl();
    private static final IFinancialRecordService financialRecordService = new FinancialRecordServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        handleGetEmployeeById(scanner);
                        break;
                    case 2:
                        handleGetAllEmployees();
                        break;
                    case 3:
                        handleAddEmployee(scanner);
                        break;
                    case 4:
                        handleGeneratePayroll(scanner);
                        break;
                    case 5:
                        handleCalculateTax(scanner);
                        break;
                    case 6:
                        handleAddFinancialRecord(scanner);
                        break;
                    case 7:
                        handleViewReports();
                        break;
                    case 8:
                        System.out.println("Exiting Payxpert. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
    	System.out.println("\n=== Payxpert Menu ===");
        System.out.println("1. Get Employee by ID");
        System.out.println("2. Get All Employees");
        System.out.println("3. Add Employee");
        System.out.println("4. Generate Payroll");
        System.out.println("5. Calculate Tax");
        System.out.println("6. Add Financial Record");
        System.out.println("7. View Reports"); // You can add more options
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleGetEmployeeById(Scanner scanner) {
        // Get employee ID from the user
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            // Call the service method to get employee by ID
            Employee employee = employeeService.getEmployeeById(employeeId);
            System.out.println("Employee found:\n" + employee);
        } catch (EmployeeNotFoundException e) {
            System.out.println("Employee not found: " + e.getMessage());
        }
        catch (DatabaseConnectionException e) {
            // Handle the exception, you can log it or print an error message
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private static void handleGetAllEmployees() {
        // Call the service method to get all employees
        List<Employee> employees;
		try {
			employees = employeeService.getAllEmployees();
			System.out.println("All Employees:");
	        for (Employee employee : employees) {
	            System.out.println(employee);
	        }
		} catch (DatabaseConnectionException e) {
	        // Handle the exception, you can log it or print an error message
	        System.out.println("Error connecting to the database: " + e.getMessage());
	    }
        
    }

    private static void handleAddEmployee(Scanner scanner) {
        // Get employee details from the user and create a new Employee object
    	System.out.print("Enter employeeID: ");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
        String dobInput = scanner.nextLine();

        // Parse the date input into a LocalDate object
        LocalDate dateOfBirth = LocalDate.parse(dobInput, dateFormatter);

        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phoneNumber: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter position: ");
        String position = scanner.nextLine();
        
        System.out.print("Enter Joining Date (yyyy-MM-dd): ");
        String joiningDateInput = scanner.nextLine();
        LocalDate joiningDate = LocalDate.parse(joiningDateInput, dateFormatter);

        System.out.print("Enter Termination Date (yyyy-MM-dd): ");
        String terminationDateInput = scanner.nextLine();
        LocalDate terminationDate =  LocalDate.parse(terminationDateInput, dateFormatter);


        Employee newEmployee = new Employee();
        newEmployee.setEmployeeID(employeeID);
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        newEmployee.setDateOfBirth(dateOfBirth);
        newEmployee.setGender(gender);
        newEmployee.setEmail(email);
        newEmployee.setPhoneNumber(phoneNumber);
        newEmployee.setAddress(address);
        newEmployee.setPosition(position);
        newEmployee.setJoiningDate(joiningDate);
        newEmployee.setTerminationDate(terminationDate);

        // Call the service method to add the new employee
        try {
			employeeService.addEmployee(newEmployee);
		} catch (DatabaseConnectionException e) {
	        // Handle the exception, you can log it or print an error message
	        System.out.println("Error connecting to the database: " + e.getMessage());
	    } catch (InvalidInputException e) {
	        System.out.println("Invalid Input: " + e.getMessage());
//			e.printStackTrace();
		}
        System.out.println("Employee added successfully.");
    }

    private static void handleGeneratePayroll(Scanner scanner) {
        // Get payrollID,employee ID, start date, end date, basic salary, overtime pay, deductions, and net salary from the user
    	System.out.print("Enter payroll ID: ");
        int payrollID = scanner.nextInt();
    	System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Payroll Start Date (yyyy-MM-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Payroll End Date (yyyy-MM-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Basic Salary: ");
        BigDecimal basicSalary = scanner.nextBigDecimal();
        System.out.print("Enter Overtime Pay: ");
        BigDecimal overtimePay = scanner.nextBigDecimal();
        System.out.print("Enter Deductions: ");
        BigDecimal deductions = scanner.nextBigDecimal();
//        System.out.print("Enter Net Salary: ");
//        BigDecimal netSalary = scanner.nextBigDecimal();

        try {
            BigDecimal netSalary = basicSalary.add(overtimePay).subtract(deductions);

            // Call the service method to generate payroll
            payrollService.generatePayroll(payrollID,employeeId, startDate, endDate, basicSalary, overtimePay, deductions, netSalary);
            System.out.println("Payroll generated successfully.");
        } catch (PayrollGenerationException e) {
            System.out.println("Error generating payroll: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            // Handle the exception, you can log it or print an error message
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }


    private static void handleCalculateTax(Scanner scanner) {
        // Get employee ID and tax year from the user
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Tax Year: ");
        int taxYear = scanner.nextInt();

        try {
            // Call the service method to calculate tax
            taxService.calculateTax(employeeId, taxYear);
            System.out.println("Tax calculated successfully.");
        } catch (TaxCalculationException e) {
            System.out.println("Error calculating tax: " + e.getMessage());
        }
        catch (DatabaseConnectionException e) {
            // Handle the exception, you can log it or print an error message
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private static void handleAddFinancialRecord(Scanner scanner) {
        // Get employee ID, description, amount, and record type from the user
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Amount: ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter Record Type: ");
        String recordType = scanner.nextLine();

        try {
            // Call the service method to add financial record
            financialRecordService.addFinancialRecord(employeeId, description, amount, recordType);
            System.out.println("Financial record added successfully.");
        } catch (FinancialRecordException e) {
            System.out.println("Error adding financial record: " + e.getMessage());
        }
        catch (DatabaseConnectionException e) {
            // Handle the exception, you can log it or print an error message
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private static void handleViewReports() {
        // Get relevant data from the service methods
        List<Payroll> payrolls;
		try {
			payrolls = payrollService.getPayrollsForPeriod(LocalDate.now().minusMonths(1), LocalDate.now());
			List<Tax> taxes = taxService.getTaxesForYear(LocalDate.now().getYear());
	        List<FinancialRecord> financialRecords = financialRecordService.getFinancialRecordsForDate(LocalDate.now());

	        // Generate and print reports using the ReportGenerator
	        ReportGenerator.generatePayrollReport(payrolls);
	        ReportGenerator.generateTaxReport(taxes);
	        ReportGenerator.generateFinancialRecordReport(financialRecords);
		} catch (DatabaseConnectionException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());

		}
        
    }
}
