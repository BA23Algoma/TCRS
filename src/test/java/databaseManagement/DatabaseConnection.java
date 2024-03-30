package databaseManagement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:h2:~/src/main/resources/trcs_script"; // Database URL
    private static final String USER = "tcrs"; // Database user name
    private static final String PASSWORD = ""; // Database password
    public Connection connection = null;

    
    public DatabaseConnection() {
    	try {
            // Load the H2 JDBC driver
            Class.forName("org.h2.Driver");

            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Use the connection to perform database operations
            // Check if the connection is successful
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to connect to the database!");
            }
            
    	} catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

