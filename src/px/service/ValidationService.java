package px.service;

import px.entity.Employee;
import px.exception.InvalidInputException;

import java.util.regex.Pattern;

public class ValidationService {

    // Regular expression for a valid email address
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Validate and enforce business rules for employee data
    public static void validateEmployeeData(Employee employeeData) throws InvalidInputException {
        validateStringInput(employeeData.getFirstName(), "First name");
        validateStringInput(employeeData.getLastName(), "Last name");
        validateEmail(employeeData.getEmail());
        validatePhoneNumber(employeeData.getPhoneNumber());
//        validateDateOfBirth(employeeData.getDateOfBirth());
        // Add more validations as needed
    }

    // Validate a string input (not null and not empty)
    private static void validateStringInput(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " must not be null or empty");
        }
    }

    // Validate an email address using a regular expression
    private static void validateEmail(String email) throws InvalidInputException {
        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new InvalidInputException("Invalid email address");
        }
    }

    // Validate a phone number (simple validation for illustration purposes)
    private static void validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        // For simplicity, let's assume a valid phone number is a string with only digits
        if (phoneNumber == null || !phoneNumber.matches("\\d+")) {
            throw new InvalidInputException("Invalid phone number");
        }
    }

    // Validate the date of birth (simple validation for illustration purposes)
    private static void validateDateOfBirth(java.sql.Date dateOfBirth) throws InvalidInputException {
        if (dateOfBirth == null) {
            throw new InvalidInputException("Date of birth must not be null");
        }
        // Add more date of birth validations as needed
    }
}
