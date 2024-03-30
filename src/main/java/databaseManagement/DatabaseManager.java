package databaseManagement;

import java.sql.*;

public class DatabaseManager {
	
    private static final String URL = "jdbc:h2:./src/main/resources/tcrs_db"; // Database URL
    private static final String USER = "tcrs"; // Database user name
    private static final String PASSWORD = ""; // Database password
    public Connection connection;
    
    // Initialize connection object
    public DatabaseManager() {
    	
    	this.connection = null;

    }
    
    // Establish connection to database
    public void connectToDatabase() {
    	
      	try {
            // Load the H2 JDBC driver
            Class.forName("org.h2.Driver");

            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
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
    
    
    // Close connection to database
    public void disconnectFromDatabase() {
    	try {         
    		
    		//Finally close connection and all resources associated with connection
    		if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database!");
            }
        } catch (SQLException e) {
        	handleSQLException(e);
        }
    }
    
    // Used for searching the database with SELECT command
    public ResultSet executeQuery(String sql) {
    	
    	//Initialize resultSet for executing queries
    	ResultSet resultSet = null;
    	
        // Attempt to query database
        try {
        	
			Statement statement = connection.createStatement();
			
			// Execute a query
			resultSet = statement.executeQuery(sql);	
			
       
        } catch (SQLException e) {
        	handleSQLException(e);
		}
    	
        return resultSet;
    }
    
    
    // Updates the database, INSERT, DELETE, UPDATE commands
    public void executeUpdate(String sql) {
    	
        // Attempt to query database
        try {
        	
        	//Initialize resultSet for executing queries
        	
			Statement statement = connection.createStatement();
			
			// Execute a query
			statement.executeUpdate(sql);	
			
       
        } catch (SQLException e) {
        	handleSQLException(e);
		}
    	    	
    }
    
    // Execute parameterized queries for security and flexibility
    public void executeParameterizedUpdate(String sql, Object... params) {
    	
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	
            // Set parameters dynamically
            for (int i = 0; i < params.length; i++) {
            	
                statement.setObject(i + 1, params[i]);
                
            }
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
        	
            handleSQLException(e);
            
        }
    }
    
    // Execute parameterized queries for security and flexibility
    public ResultSet executeParameterizedQuery(String sql, Object... params) {
    	
    	//Initialize resultSet for executing queries
    	ResultSet resultSet = null;
    	
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	
            // Set parameters dynamically
            //for (int i = 0; i < params.length; i++) {
            	
            //	 statement.setObject(i + 1, params[i]);
            //}
            
            resultSet =  statement.executeQuery();
            
        } catch (SQLException e) {
        	
            handleSQLException(e);
        }
        
        return resultSet;
    }
    

    
    public void handleSQLException(SQLException e) {
    	System.err.println("SQL Exception occurred:");
    	e.printStackTrace();
    }
    
    
}