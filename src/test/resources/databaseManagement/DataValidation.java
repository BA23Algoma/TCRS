
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.Year;


public class DataValidation {
	
	private DatabaseManager databaseManager;
	private Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
	
	public DataValidation(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	public boolean validateVIN(String vin) {
		
		String type = "VIN";
		int vinLength = 17;
		String table = "VEHICLEINFO";
		
		// Check the length of the VIN
		if (!isValidLength(vin,vinLength, type)) {
			return false;
		}
		else if (containsSpecialChar(vin, type)) {
			return false;
		}

		return isUnique(vin, type, table);
		
	}
	public boolean validateLicensePlate(String plate) {
		
		String type = "License plate";
		int plateLength = 7;
		String table = "VEHICLEINFO";
		
		// Check the length of the VIN
		if (!isValidLength(plate, plateLength,type)){
			return false;
		}
		else if (containsSpecialChar(plate, type)) {
			return false;
		}
		else if (!isAlpha(plate, 0, 3) || !isNumber(plate, 4, 6)) {
			System.out.println("Incorrect format for license plate!");
			System.out.println("License Plate Format: XXXXNNN");
			return false;
		}

		return isUnique(plate, type, table);
	}
	
	public boolean validateMake(String make) {
		
		// Check make input contains only letters
		return isAlpha(make);
		
	}
	
	public boolean validateModel(String model) {
		
		String type = "model";
		
		// Check no special characters
		return !containsSpecialChar(model, type);
		
	}
	public void validateYear(int year) {
		
		if (year < 1960 || 2024 < year) {
			System.out.println("Incorrect year! \nPleae enter corect year of car");
		}
		
	}
	public boolean validateFirstName(String firstName) {
		
		// Check if only letters entered
		return isAlpha(firstName);
		
	}
	public boolean validateLastName(String lastName) {
		
		// Check if only letters entered
		return isAlpha(lastName);
				
	}
	public boolean validateLicenseNumber(String licenseNumber) {
		
		String type = "LICENSENUMBER";
		int licenseLength = 15;
		String table = "DRIVERINFO";
		
		// Check length
		if (!isValidLength(licenseNumber, licenseLength, type)) {
			return false;
		}
		else if (!isAlpha(licenseNumber, 0, 1)) {
			return false;
		}
		else if (!isNumber(licenseNumber, 1, licenseLength - 1)) {
			return false;
		}
		return isUnique(licenseNumber, type, table);
		
	}
	public boolean validateDemeritPoints(int demeritPoints) {
		
		if (demeritPoints < 0 || 99 < demeritPoints)
			return false;
		
		return true;
		
	}
	public boolean validateBadgeNumber(int badgeNumber) {
		
		String type = "BADGENUMBER";
		int badgeStart = 10000;
		String table = "OFFICERINFO";
		
		if (badgeNumber < badgeStart )
			return false;
		
		String badge = String.valueOf(badgeNumber);
		
		return isUnique(badge, type, table);
		
	}
	public boolean validateCitationID(int citationID) {
		
		String type = "CITATIONID";
		
		String drivMun = "DRIVINGCITATIONSMUN";
		String drivProv = "DRIVINGCITATIONSPROV";
		String vehMun = "VEHICLECITATIONSMUN";
		String vehProv = "VEHICLECITATIONSPROV";
		
		String trafficSchool = "TRAFFICSCHOOL";
		String trafficSchCit = "CITATIONIDTS";
	
		
		String citation = String.valueOf(citationID);
		
		// Ensure number is valid
		if (negativeNum(citationID) || citationID == 0) {
			return false;
		}
		
		// Check if value is in municipal or provincial records
		if (!uniqueMunandProv(citation, type, drivMun, vehMun, drivProv, vehProv)) {
			return false;
		}
		
		// Check traffic school
		return (isUnique(citation, trafficSchCit, trafficSchool));
		
	}
	public boolean validateFineAmount(double fineAmount) {
		
		return negativeNum(fineAmount);
	}
	public boolean validateDate(String date) {
		
		String type = "date";
		
		if(!isValidLength(date, 10, type)){
			return false;
		}
		
		return dateFormat(date);
		
	}
	public boolean validateWarrantID(int warrantID) {
		
		String type = "WARRANTID";
		
		String drivMun = "DRIVERWARRANTSMUNICIPLE";
		String drivProv = "DRIVERWARRANTSPROV";
		String vehMun = "VEHICLEWARRANTSMUNICIPLE";
		String vehProv = "VEHICLEWARRANTSPROV";
		
		String warrant = String.valueOf(warrantID);
		
		// Ensure number is valid
		if (negativeNum(warrantID) || warrantID == 0) {
			return false;
		}
		
		// Check if value is in municipal or provincial records
		return uniqueMunandProv(warrant, type, drivMun, vehMun, drivProv, vehProv);
		
	}
	public boolean validateSessionNumber(int sessionNumber) {
		
		if(sessionNumber < 1 || 4 < sessionNumber) {
			System.out.println("Invalis session number");
			return false;
		}
		
		return true;
		
	}
	
	//************************ Helper Methods **********************
	// Check if unique
	private boolean isUnique(String entry, String column, String table) {
		
		String query = String.format("SELECT %s FROM TCRS.%s WHERE %s='%s'", column, table, column, entry);
		
		ResultSet result = this.databaseManager.executeQuery(query);
		// If there is no next, then the VIN is not in the database and therefore valid
		try {
			if(result.next()) {
				System.out.println(String.format("%s: %s is already in the system!", column, entry));
				return false;
			}
			else
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Check if the length is the correct
	private boolean isValidLength(String data, int length, String category) {
		
		if (data.length() == length)
			return true;
		
		System.out.println(String.format("%s is the wrong length!", category));
		return false;
	}
	
	// Check for special characters
	private boolean containsSpecialChar(String str, String category) {
		
		Matcher m = p.matcher(str);
		
		if (m.find()) {
			System.out.println(String.format("Invalid character in %s!", category));
			return true;
		}
		return false;
	}
	
	// Check if only letters
	
	private boolean isAlpha(String str) {
	    char[] chars = str.toCharArray();

	    for (char c: chars) {
	        if(!Character.isLetter(c)) {
	        	System.out.println("Character other than letters was entered!\nPlease review");
	            return false;
	        }
	    }

	    return true;
	}
	
	// Check if only letters, with range
	private boolean isAlpha(String str, int begin, int end) {
	    char[] c = str.toCharArray();

	    for (int i = begin; i < end; i++ ) {
	        if(!Character.isLetter(c[i])) {
	        	System.out.println("Incorrect character was entered!\nPlease review");
	            return false;
	        }
	    }

	    return true;
	}
	
	// Check if only numbers, with range
	private boolean isNumber(String str, int begin, int end) {
	    char[] c = str.toCharArray();

	    for (int i = begin; i < end; i++ ) {
	        if(!Character.isDigit(c[i])) {
	        	System.out.println("Non number was found in incorrect position!");
	            return false;
	        }
	    }

	    return true;
	}
	
	private boolean dateFormat(String date) {
		
		char seperator = '-';
		String monthString = date.substring(0,2);
		String dayString = date.substring(3,5);
		String yearString = date.substring(6);
		
		int month = Integer.valueOf(monthString);
		int day = Integer.valueOf(dayString);
		int year = Integer.valueOf(yearString);
		int numDays = 31;  
				
		int firstYear = 2024;
		
		String invalid = "Invalid date input";
		
		if (month < 1 || month > 12) {
			System.out.println(invalid);
			return false;
		}
		
		numDays = numOfDays(month, year);
		
		if (day < 1 || numDays < day || year < firstYear) {
			System.out.println(invalid);
			return false;
		}
	
		
		return Character.isDigit(date.charAt(0)) &&
        Character.isDigit(date.charAt(1)) &&
        Objects.equals(date.charAt(2), seperator) &&
        Character.isDigit(date.charAt(3)) &&
        Character.isDigit(date.charAt(4)) &&
        Objects.equals(date.charAt(5), seperator) &&
        Character.isDigit(date.charAt(6)) &&
        Character.isDigit(date.charAt(7)) &&
        Character.isDigit(date.charAt(8));
		
	}
	
	private int numOfDays(int month, int year) {
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
	
	private boolean negativeNum(int value) {
		
		if (value < 0) {
			System.out.println("Amount must be greater than zero!");
			return true;
		}
		
		return false;
	}
	
private boolean negativeNum(double value) {
		
		if (value < 0) {
			System.out.println("Amount must be greater than zero!");
			return true;
		}
		
		return false;
	}

private boolean uniqueMunandProv(String citation, String type, String drivMun, String vehMun, String drivProv, String vehProv) {
	
	// Check municipal

	if (!isUnique(citation, type, drivMun) || !isUnique(citation, type, vehMun)) {
		System.out.println(String.format("%s: %s is already in municipal system!", type, citation));
		return false;
	}
	
	// Check provincial
	
	if (!isUnique(citation, type, drivProv) || !isUnique(citation, type, vehProv)) {
		System.out.println(String.format("%s: %s is already in provincial system!", type, citation));
		return false;
	}
	
	return true;
			
}

}