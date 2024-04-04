package databaseManagement;

import java.io.IOException;
import java.util.Optional;

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
		
		//Account driver = new Account(connection);

		valid.checkLoginInfo("gamjoun", "239349660", "Administration");
		
		//int latest = driver.insertAccount("Admin", "Banki", "Adewale", "algomaStudent", "ThunderBird");
		
		Report report = new Report(connection);
		
		// String warrantcheck = report.generateCitationSummary(java.util.Optional.empty(), java.util.Optional.empty(), java.util.Optional.empty(), java.util.Optional.empty(), java.util.Optional.empty());
		
		
		Optional<String> badge = Optional.of("10002");
		Optional<String> startDate = Optional.of("2015-01-01");
		Optional<String> endDate = Optional.of("2018-01-01");
		Optional<String> reason = Optional.of("Fix-It Ticket");
		Optional<String> paid = Optional.of("Unpaid");


		
		String citationSummary = report.generateCitationSummary(badge, startDate, endDate, java.util.Optional.empty(), paid);

		
		//String warrantcheck = report.generateOutstandingWarrants();
		
		String startDateDriving = ("2015-01-01");
		String endDateDriving = ("2018-01-01");
		
		//String driverRecord = report.generateDrivingRecord("A12345678912345", startDateDriving, endDateDriving);
		
		System.out.println(citationSummary);
		
        connection.disconnectFromDatabase();
           
	}
}
