package databaseManagement;


import java.sql.ResultSet;
import java.sql.SQLException;


public class RecordValidation {
	
	private DatabaseManager databaseManager;

	
	public RecordValidation(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	public boolean checkVehicleRecordExistence(String vin) {
		
		vin = vin.toUpperCase();
		String type = "VIN";
		String table = "VEHICLEINFO";

		return isInSystem(vin, type, table);
		
	}
	public boolean checkDriverRecordExistence(String licenseNumber) {
		
		licenseNumber = licenseNumber.toUpperCase();
		String type = "LICENSENUMBER";
		String table = "DRIVERINFO";

		return isInSystem(licenseNumber, type, table);
	}
	public boolean checkVehWarrRecordExistence(String vehicleWarrantID) {
		
		String type = "WARRANTID";
		
		String vehMun = "VEHICLEWARRANTSMUNICIPLE";
		String vehProv = "VEHICLEWARRANTSPROV";
				
		// Check if value is in municipal or provincial records
		return isInSystem(vehicleWarrantID, type, vehMun, vehProv);
		
	}
	public boolean checkDriWarrRecordExistence(String driverWarrantID) {
		
		String type = "WARRANTID";
		
		String drivMun = "DRIVERWARRANTSMUNICIPLE";
		String drivProv = "DRIVERWARRANTSPROV";

				
		// Check if value is in municipal or provincial records
		return isInSystem(driverWarrantID, type, drivMun, drivProv);
		
	}
	public boolean checkVehCitRecordExistence(String vehicleCitationID) {
		
		String type = "CITATIONID";
		
		String vehMun = "VEHICLECITATIONSMUN";
		String vehProv = "VEHICLECITATIONSPROV";
				
		// Check if value is in municipal or provincial records
		return isInSystem(vehicleCitationID, type, vehMun, vehProv);
		
	}
	public boolean checkDriCitRecordExistence(String driverCitationID) {
		
		String type = "CITATIONID";
		
		String drivMun = "DRIVINGCITATIONSMUN";
		String drivProv = "DRIVINGCITATIONSPROV";
		
		// Check if value is in municipal or provincial records
		return isInSystem(driverCitationID, type, drivMun, drivProv);
		
	}
	public boolean checkOfficerRecordExistence(String badgeNumber) {
		
		String type = "BADGENUMBER";
		String table = "OFFICERINFO";
				
		return isInSystem(badgeNumber, type, table);
		
	}

	public boolean checkTrafficSchoolRecordExistence(String citationID) {
		
		String trafficSchool = "TRAFFICSCHOOL";
		String trafficSchCit = "CITATIONIDTS";
			
		// Check traffic school
		return (isInSystem(citationID, trafficSchCit, trafficSchool));
		
	}
	public boolean checkAccountRecordExistence(String accountID) {
		
		String type = "ACCOUNTID";
		String table = "ACCOUNTS";
			
		// Check traffic school
		return (isInSystem(accountID, type, table));
		
	}
	
	
	public boolean checkLoginInfo(String username, String password, String agency) {
		
		username = username.toLowerCase();
		
		
		// Build string to search login credentials within database
		String sqlCredentialCheck = String.format("SELECT * FROM TCRS.ACCOUNTS WHERE AGENCY = '%s' AND USERNAME = '%s' AND PASSWORDACC = '%s'", agency, username, password);
		
		// Execute search and return results		
		 ResultSet result = this.databaseManager.executeQuery(sqlCredentialCheck);
		 
			// If there is no next, then the VIN is not in the database and therefore valid
			try {
				if(result.next()) {
					System.out.println("Login succesfull! ");
					return true;
				}
				else {
					System.out.println("Account not in the system!");
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	//************************ Helper Methods **********************
	// Check if unique
	private boolean isUnique(String entry, String column, String table) {
		
		// Search the system
		String query = String.format("SELECT %s FROM TCRS.%s WHERE %s='%s'", column, table, column, entry);
		
		// Return false if already in the system, true if unique
		ResultSet result = this.databaseManager.executeQuery(query);
		// If there is no next, then the VIN is not in the database and therefore valid
		try {
			if(result.next()) {
				return false;
			}
			else
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Return whether or not the search found a match in the system
	private boolean isInSystem(String entry, String column, String table) {
		
		if (isUnique(entry, column, table)) {
			
			System.out.println(String.format("%s was not found in the system!", entry));
			return false;
			
		}
		
		return true;
	}
	
	// Return whether or not the search found a match in the system
	private boolean isInSystem(String entry, String type, String setOne, String setTwo) {
		
		if (isUnique(entry, type, setOne) && isUnique(entry, type, setTwo)) {
			
			System.out.println(String.format("%s was not found in the system!", entry));
			return false;
			
		}
		
		return true;
	}

	
}
