package CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseManagement.*;

public class Officer {

	public int badgeNumber;
	public String lastName;
	public String firstName;
	
	private DatabaseManager databaseManager;
		
	public Officer(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	// ******** Setter Methods ********//
	
		public void setBadgeNumber(String badgeNumber) {
			
			if(!isNumber(badgeNumber)) {
				System.out.println("Invaild badge number account!");
				return;
			}
			
			int badge = Integer.valueOf(badgeNumber);
			
			this.badgeNumber = badge;
			
		}
		
		public void setFirstName(String firstName) {
			
			this.firstName = firstName;
			
		}
		
		public void setLastName(String lastName) {
			
			this.lastName = lastName;
			
		}
		
		
		//********* Getter Methods ********//
		
		public String getBadgeNumber() {
			
			
			String badge = String.valueOf(badgeNumber);
			
			return badge;
			
		}

		
		public String getFirstName() {
			
			return firstName;
			
		}
		
		public String getLastName() {
			
			return lastName;
			
		}
		
	
	// Insertion method
	public void insertOfficer (String badge, String firstName, String lastName) {
		
		
		if(!isNumber(badge)) {
			System.out.println("Invaild badge number account!");
			return;
		}
		
		// Validate correct formats of input data
		 
		
		// Create SQL query string
	    String sql = String.format("INSERT INTO TCRS.OFFICERINFO (LASTNAME , FIRSTNAME , BADGENUMBER ) "
	    		+ "VALUES ('%s', '%s', '%s')", lastName, firstName, badge);
	    
	    // Pass prepared statement to databaseManager for execution
	    databaseManager.executeUpdate(sql);
	    
	    System.out.println("Account added to the database!");
		
	}
	
	// Edit officer based on the badge number
	public void editOfficer (String badge, String firsName, String lastName) {
		
		if(!isNumber(badge)) {
			System.out.println("Invaild badge number, unable to edit account!");
			return;
		}
		
		int badgeNumber = Integer.valueOf(badge);
		
		// First confirm account exist, and if so return account information
		Officer officer = findOfficer(badgeNumber);
		
		
		// Check to see if the account is in the system
		if (!inSystem(officer)) {
			return;
		}
		
		// Build edit query in system based on account ID
		String sqlQuery = String.format("UPDATE TCRS.OFFICERINFO SET LASTNAME = '%s', FIRSTNAME = '%s'"
				+ " WHERE BADGENUMBER = %d", lastName, firstName, badge);

		// Execute query
		databaseManager.executeUpdate(sqlQuery);
		
		System.out.println("Account edited");
		
	}
	
	public void deleteOfficer (String badge) {
		
		if(!isNumber(badge)) {
			System.out.println("Invaild badge number, unable to delete account!");
			return;
		}
		
		int badgeNumber = Integer.valueOf(badge);
		
		
		// First confirm account exist, and if so return account information
		Officer officer = findOfficer(badgeNumber);
		
		// Check to see if the account is in the system
		if (!inSystem(officer)) {
			return;
		}
				
		// Create query to delete account
		String sqlDelete = String.format("DELETE FROM TCRS.OFFICERINFO WHERE BADGENUMBER= %d", badgeNumber);
		
		// Execute deleting of account
		databaseManager.executeUpdate(sqlDelete);
		
		System.out.println("Officer " + badgeNumber + " removed from system");
				
	}
	
	public Officer findOfficer (String badge) {
		
		if(!isNumber(badge)) {
			System.out.println("Invaild badge number, unable to find account!");
			return null;
		}
		
		int badgeNumber = Integer.valueOf(badge);
		
		// Create account to hold new found account information
		Officer officer = new Officer(this.databaseManager);
		
		/// Create query to delete account
		String sqlQuery = String.format("SELECT * FROM TCRS.OFFICERINFO WHERE BADGENUMBER = %d", badgeNumber);
				
		// Execute finding account SELECT query
		ResultSet result = databaseManager.executeQuery(sqlQuery);
		
		// Check if the query did not match  in the system, return empty account
		if (nullCheck(result)) {
    		return null;
		}
    	
    	// Return the found account logged into findAcc
    	return logData(result, officer);
		    	
	}
	
	public void autoInputOfficer(String badgeNumber) {
		
	}
	
	public String toString() {
		
		return "Badge Number " + this.badgeNumber + " First Name: " + this.firstName + " Last Name: " + this.lastName;
	}
	
	//*********** Object Methods *****************
	
	// Insert new account using officer class
	public void insertOfficer (Officer officer) {
		
		insertOfficer(officer.getBadgeNumber(), officer.getFirstName(), officer.getLastName());
		
	}
	
	// Edit officer based on the badge number
	public void editOfficer (String badge, Officer newAccount) {
		
		
	}
	
	
	
	//**************************** Private Helper Methods ********************************************
	
		private Officer logData(ResultSet result, Officer officer) {
			
			try {
				while (result.next()) {
				     //Retrieve data by column index or name
					officer.badgeNumber = result.getInt("badgeNumber");
					officer.firstName = result.getString("firstName");
					officer.lastName = result.getString("lastName");
				    				
				return officer;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} finally {
		        // Close the result set
		        try {
		            if (result != null) {
		                result.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
			
			return null;
		}
		
		private boolean nullCheck(ResultSet result) {
			
			// Check if the select statement returned a value
	    	try {
				if (!result.isBeforeFirst()) {
					System.out.println("Officer not in the system!");
					return true;
				}
		    	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return true;
			}
	    	
	    	return false;
	    	
		}
		
		// Helper method for empty field
		private boolean emptyField(Officer officer) {
			
			return emptyField(officer.firstName, officer.lastName);
			
		}
		
		// Helper method for empty field
		private boolean emptyField(String first, String last) {
			
			// Check if any of the fields (except accountID) are empty
		    if (first == null || first.isEmpty()) {
				 System.out.println(String.format("Cannot leave first name blank!"));
		        return true;
		    }
		    if (last == null || last.isEmpty()) {
				 System.out.println(String.format("Cannot leave last name blank!"));

		        return true;
		    }
		    
		    // All fields are non-empty, so return true
		    return false;
		}
		
		// Check to ensure the account information is valid
		
		
		private boolean inSystem(Officer officer) {
			
			if (officer == null) {
				System.out.println("Officer not in the system!");
				return false;
			}
			
			return true;
		}
		
		// Check if only numbers, with range
		private boolean isNumber(String str) {
		    
			return isNumber(str, 0, (str.length() - 1));
		}
		
		// Check if only numbers, with range
		private boolean isNumber(String str, int begin, int end) {
		    char[] c = str.toCharArray();

		    for (int i = begin; i < end; i++ ) {
		        if(!Character.isDigit(c[i])) {
		        	System.out.println("Non number was found!");
		            return false;
		        }
		    }

		    return true;
		}
		
		private Officer findOfficer (int badgeNumber) {
			
			// Create account to hold new found account information
			Officer officer = new Officer(this.databaseManager);
			
			/// Create query to delete account
			String sqlQuery = String.format("SELECT * FROM TCRS.OFFICERINFO WHERE BADGENUMBER = %d", badgeNumber);
					
			// Execute finding account SELECT query
			ResultSet result = databaseManager.executeQuery(sqlQuery);
			
			// Check if the query did not match  in the system, return empty account
			if (nullCheck(result)) {
	    		return null;
			}
	    	
	    	// Return the found account logged into findAcc
	    	return logData(result, officer);
			    	
		}


}