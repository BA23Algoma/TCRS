package databaseManagement;

import java.io.IOException;

import CRUD.*;

public class Test_Database {
	
	public static void main(String[] args) {
	    
        // Connect to the database
            
		DatabaseManager connection = new DatabaseManager();
		RecordValidation valid = new RecordValidation(connection);
		
		try {
			connection.connectToDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Account driver = new Account(connection);
		
		valid.checkLoginInfo("gamjoun", "239349660", "Admin");
		
		int latest = driver.insertAccount("Admin", "Banki", "Adewale", "algomaStudent", "ThunderBird");
		
		System.out.println("Latest Id is :" + latest);
		
        connection.disconnectFromDatabase();
           
	}
}
