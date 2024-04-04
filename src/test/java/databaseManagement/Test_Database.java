package databaseManagement;


import java.io.IOException;
import java.time.Year;
import java.util.Objects;
import java.util.Optional;

import CRUD.*;

public class Test_Database {
	
	public static void main(String[] args) {
	    
        // Connect to the database
            
		DatabaseManager connection = new DatabaseManager();
		RecordValidation valid = new RecordValidation(connection);
		//InputValidation test = new InputValidation();
		
		Boolean check = validateDate("2025-01-01");
		
		System.out.print(check);
		try {
			connection.connectToDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VehicleCitation citation = new VehicleCitation(connection);

		valid.checkLoginInfo("gamjoun", "239349660", "Administration");
		
		if(valid.checkVehCitRecordExistence("1"))
			System.out.print("Citation is in the system!");
		
		citation = citation.findCitation("1");
				
		//System.out.println("\n" + citation.toString());
		
		//citation.toString();
		
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
	
public static boolean validateDate(String date) {
		
		String type = "date";
		
		if(!isValidLength(date, 10, type)){
			return false;
		}
		
		return dateFormat(date);
		
	}

//Check if the length is the correct
	private static boolean isValidLength(String data, int length, String category) {
		
		if (data.length() == length)
			return true;
		
		System.out.println(String.format("%s is the wrong length!", category));
		return false;
	}
	
	
private static boolean dateFormat(String date) {
		
		char seperator = '-';
		String yearString = date.substring(0,4);
		String monthString = date.substring(5,7);
		String dayString = date.substring(8);
		
		int month = Integer.valueOf(monthString);
		int day = Integer.valueOf(dayString);
		int year = Integer.valueOf(yearString);  
		System.out.println("year: "+ year + " month: " + month + " day: " + day);		
		
		String invalid = "Invalid date input";
		
		if (month < 1 || month > 12) {
			System.out.println(invalid);
			return false;
		}
		
		int numDays = numOfDays(month, year);
		
		if (day < 1 || numDays < day || year > Year.now().getValue()) {
			System.out.println(invalid);
			return false;
		}
	
		
		return Character.isDigit(date.charAt(0)) &&
		        Character.isDigit(date.charAt(1)) &&
		        Character.isDigit(date.charAt(2)) &&
		        Character.isDigit(date.charAt(3)) &&
		        Objects.equals(date.charAt(4), seperator) &&
		        Character.isDigit(date.charAt(5)) &&
		        Character.isDigit(date.charAt(6)) &&
		        Objects.equals(date.charAt(7), seperator) &&
		        Character.isDigit(date.charAt(8)) &&
		        Character.isDigit(date.charAt(9));
		
	}
	
	private static int numOfDays(int month, int year) {
		int days = 31;
		
		switch (month) {
				
				case 1: case 3: case 5: 
				case 7: case 8: case 10: 
				case 12:{
					days = 31;
					break;
				}
				case 4: case 6: case 9:
				case 11:{
					days = 30;
					break;
				}
				case 2:{
					if (Year.of(year).isLeap())
						days = 29;
					else
						days = 28;
					break;
				}
					
				}
		
		return days;
	}
}
