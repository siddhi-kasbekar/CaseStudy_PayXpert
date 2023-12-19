package px.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // Define the JDBC URL for MySQL
    private static final String url = "jdbc:mysql://localhost:3306/payxpert";
    
    // Define the database credentials
    private static final String username = "root";
    private static final String password = "Sid@2002";

    // Load the JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading MySQL JDBC driver");
        }
    }

    // Create a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
