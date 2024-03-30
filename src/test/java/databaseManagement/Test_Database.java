package databaseManagement;

import CRUD.*;

public class Test_Database {
	
	public static void main(String[] args) {
	    
        // Connect to the database
            
		DatabaseManager connection = new DatabaseManager();
		InputDataValidation validate = new InputDataValidation();
		RecordValidation valid = new RecordValidation(connection);
		
		connection.connectToTestDatabase();
		
		Vehicle driver = new Vehicle(connection);
		
		driver = driver.findVehicle("927840315QXPOWRUE");
		
		System.out.println(driver.toString());
		
        connection.disconnectFromDatabase();
           
	}
}
