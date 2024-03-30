package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseManagement.DatabaseManager;

import databaseManagement.RecordValidation;


public class DriverCitation {
	
	private DatabaseManager databaseManager;

	public int citationId;
	public String license;
	public int ISSUINGOFFICERIDP;
	public String dateIssued;
	public String reason;
	public Double fineAmount;
	public String Paid;
	public boolean Reportable;
	
	public DriverCitation(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	// ******** Setter Methods ********//
	
	
	public void setlicense(String license) {
			
			this.license = license;
			
		}
	
	public void setISSUINGOFFICERIDP(String ISSUINGOFFICERIDP) {
		
		
		if(!isNumber(ISSUINGOFFICERIDP)) {
			System.out.println("Invaild badge number account!");
			return;
		}
		
		int badge = Integer.valueOf(ISSUINGOFFICERIDP);
		
		this.ISSUINGOFFICERIDP = badge;
		
	}
	
	public void setDateIssued(String dateIssued) {
		
		this.dateIssued = dateIssued;
		
	}
	
	public void setReason(String reason) {
		
		this.reason = reason;
		
	}
	
	public void setFineAmount(String fineAmount) {
		
		if(!isNumber(fineAmount)) {
			System.out.println("Invaild badge number account!");
			return;
		}
		
		double fine = Double.valueOf(fineAmount);
		
		this.fineAmount = fine;
		
	}
	
	public void setPaid(String Paid) {
		
		this.Paid = Paid;
		
	}
	
	public void setReportable(String Report) {
		
		Boolean Reportable;
		
		if(Report.equalsIgnoreCase("Yes")) {
			
			Reportable = true;
			
		}
		else 
			Reportable = false;
		
		this.Reportable = Reportable;
		
	}
	
	
	//********* Getter Methods ********//
	
	public String getcitationId() {
		
		String citId = String.valueOf(citationId);
		
		return citId;
		
	}

	public String getLicense() {
		
		return license;
		
	}
	
	public String getIssuingOfficerBadgeNumber() {
			
		String badge = String.valueOf(fineAmount);

		return badge;
			
		}
	
	public String getdateIssued() {
		
		return dateIssued;
		
	}
	
	public String getReason() {
		
		return reason;
		
	}
	
	public String getFineAmount() {
		
		String fine = String.valueOf(ISSUINGOFFICERIDP);

		return fine;
			
		}
	
	public String getPaid() {
		
		return Paid;
		
	}
	
	public String getReportable() {
		
		String Report;
		
		if(Reportable == true) {
			
			Report = "Yes";
			
		}
		else 
			Report = "No";
		
	
		return Report;
		
	}
		
			//********** Database Methods **********//


	
	public void insertDriverCitation (String license, String officer, String dateIssued, String reason,  String fine, String Paid, String reportable) {		
				
		if(!isNumber(officer)) {
			System.out.println("Invaild badge number account!");
			return;
		}
		else if(!isNumber(fine)) {
			System.out.println("Invaild fine amount!");
			return;
		}
		
		// Validate correct formats of input data
		 if (!validBadgeNumber(officer)) {
			 System.out.println("Officer badge number not in the system!");
			 return;
		 }
		
		// Validate if license is in system
		 if (!validLicenseNumber(license)) {
			 System.out.println("Licenser not in the system!");
			 return;
		 }
		 
		 if (fineAmount < 0) {
			 System.out.println("Invalid fine amount");
			 return;
		 }
		 
		// Create SQL query string
	    String sql = String.format("INSERT INTO TCRS.DRIVINGCITATIONSMUNICIPLE (ISSUEINGOFFICERIDM , DRIVERIDCITATIONM , "
	    		+ "CITATIONREASON ,  CITATIONDATE , FINEAMOUNT, PAYMENTSTATUS, REPORTABLE )"
	    		+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s' )", officer, license, 
				reason, dateIssued, fine, Paid, reportable);
	    
	    // Pass prepared statement to databaseManager for execution
	    databaseManager.executeUpdate(sql);
	    
	    System.out.println("Citaion added to the municipal database!");
	    
	 
	    // Insert provincial if reportable
	    if (reportable.equalsIgnoreCase("Yes")) {
	    	
	    	// Create query
	    	String sqlProv = String.format("INSERT INTO TCRS.DRIVINGCITATIONSPROV (ISSUINGOFFICERIDP , DRIVERIDCITATIONP , "
		    		+ "CITATIONREASON ,  CITATIONDATE , FINEAMOUNT, PAYMENTSTATUS )"
		    		+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s' )", officer, license, 
					reason, dateIssued, fine, Paid);
	    	
	    	// Insert into provincial database
	    	databaseManager.executeUpdate(sql);
	    	
		    System.out.println("Citation was also added to the provincial database!");

	    }
		
	}
	
	// Insert driver using driver citation object
	public void insertDriverCitation (DriverCitation citation) {
		
		String badgeNumber = String.valueOf(citation.ISSUINGOFFICERIDP);
		String fine = String.valueOf(citation.fineAmount);

		
		insertDriverCitation(citation.license, badgeNumber, citation.dateIssued,
				citation.reason, fine, citation.Paid, citation.getReportable());

		
	}
	
	
	public void editDriverCitation (String citID, String license, String ISSUINGOFFICERIDP, String dateIssued, String reason, String fineAmount, String Paid, String Reportable) {
		
		if(!isNumber(citID)) {
			System.out.println("Invaild citation ID, unable to edit account!");
			return;
		}
		
		int citationID = Integer.valueOf(citID);
		
		// First confirm account exist, and if so return account information
		DriverCitation citation = findCitation(citationID);
		
		// Check to see if the account is in the system
		if (!inSystem(citation)) {
			return;
		}
		
		// Validate correct formats of input data
		 if (!validBadgeNumber(ISSUINGOFFICERIDP)) {
			 System.out.println("Officer badge number not in the system!");
			 return;
		 }
		
		// Validate correct formats of input data
		 if (!validLicenseNumber(license)) {
			 System.out.println("Licenser not in the system!");
			 return;
		 }
		 
		
		// Build edit query in system based on citation ID
		String sqlQuery = String.format("UPDATE TCRS.DRIVINGCITATIONSMUNICIPLE SET ISSUEINGOFFICERIDM = '%s', DRIVERIDCITATIONM = '%s', CITATIONREASON = '%s', "
				+ "CITATIONDATE = '%s', FINEAMOUNT = '%s', PAYMENTSTATUS = '%s', REPORTABLE = '%s' WHERE CITATIONID = %d", 
				ISSUINGOFFICERIDP, license, reason, dateIssued, fineAmount, Paid, citID, Reportable);
		
		// Execute query
		databaseManager.executeUpdate(sqlQuery);
		
		System.out.println("Account edited");
		
		// Update provincial if reportable
		if (Reportable.equalsIgnoreCase("Yes")) {
	    	
	    	// Create query
			String sqlQueryProv = String.format("UPDATE TCRS.DRIVINGCITATIONSPROV SET ISSUINGOFFICERIDP = '%s', DRIVERIDCITATIONP = '%s', CITATIONREASON = '%s', "
					+ "CITATIONDATE = '%s', FINEAMOUNT = '%s', PAYMENTSTATUS = '%s' WHERE CITATIONID = %d", 
					ISSUINGOFFICERIDP, license, reason, dateIssued, fineAmount, Paid, citID);
	    	
	    	// Insert into provincial database
			databaseManager.executeUpdate(sqlQueryProv);
			
		    System.out.println("Citation was also ediited in the provincial database!");

	    }
	}
	
	// Edit driver citation using driver citation object for input
	public void editDriverCitation (String citID, DriverCitation citationNew) {
		
		String badge = String.valueOf(citationNew.ISSUINGOFFICERIDP);
		String fine = String.valueOf(citationNew.fineAmount);
		String report;
		if (citationNew.Reportable) {
			report = "Yes";
		}
		else {
			report = "No";
		}

		
		editDriverCitation (citID, citationNew.license, badge, citationNew.dateIssued, citationNew.reason, fine, citationNew.Paid, report);

		
	}
	
	public void deleteDriverCitation (String citID) {
		
		if(!isNumber(citID)) {
			System.out.println("Invaild citation ID, unable to delete account!");
			return;
		}
		
		int citationID = Integer.valueOf(citID);

		// First confirm account exist, and if so return account information
		DriverCitation citation = findCitation(citationID);
		
		// Check to see if the account is in the system
		if (!inSystem(citation)) {
			return;
		}
				
		// Create query to delete account
		String sqlDelete = String.format("DELETE FROM TCRS.DRIVINGCITATIONSMUNICIPLE WHERE CITATIONID= %d", citationID);
		
		// Since municipal is a foreign key for provincial, provincial data must be removed first
		if (citation.Reportable == true) {
			
			String sqlDeleteProv = String.format("DELETE FROM TCRS.DRIVINGCITATIONSPROV WHERE CITATIONID= %d", citationID);
			// Execute deleting of account
			databaseManager.executeUpdate(sqlDeleteProv);
			
			System.out.println("Citation " + citationID + " removed from provincial system!");
			
		}
		// Execute deleting of account
		databaseManager.executeUpdate(sqlDelete);
		
		System.out.println("Citation " + citationID + " removed from municipal system!");

	}

	public void autoInputDriverCitation(int citationID) {
		
	}
	
	public String toString() {
		
		return "License Number " + this.license + " Officer Badge Number: " + this.ISSUINGOFFICERIDP + " Date Issued: " + this.dateIssued
				+ " Reason:" + this.reason + " Fine Amount: $" + this.fineAmount + " Paid: " + this.Paid + " Reportable " + this.Reportable;
	}
	
	// Auto fill data
		public String[][] autoInputAccount (String citID) {
			
			
			// First confirm account exist, and if so return account information
			DriverCitation citation = findCitation(citID);
			
			// Check to see if the account is in the system
			if (!inSystem(citation)) {
				return null;
			}
			
			// Convert all values into strings
			String badge = String.valueOf(citation.ISSUINGOFFICERIDP);
			String fine = String.valueOf(citation.fineAmount);
			String report;
			if (citation.Reportable) {
				report = "Yes";
			}
			else {
				report = "No";

			}
			
			// Build autoFill using array of strings
			String autoFill[][] = {
					{ "license", citation.license},
					{ "ISSUINGOFFICERIDP", badge},
					{ "dateIssued", citation.dateIssued},
					{ "reason", citation.reason},
					{ "fineAmount", fine},
					{ "Paid", Paid},
					{ "Reportable", report}
			};

			return autoFill;
			
		}
	
	public DriverCitation findCitation (String citID) {
		
		if(!isNumber(citID)) {
			System.out.println("Invaild citation ID, unable to find citation in system!");
			return null;
		}
		
		int citationID = Integer.valueOf(citID);
		
		// Run private helper method
		return findCitation(citationID);
    	
	}
	
	
	// Find account using account user name
	public DriverCitation findCitationLicense (String license) {
		
		// Create account to hold new found account information
		DriverCitation findCitation = new DriverCitation(this.databaseManager);
		
		// Build SQL query using static method
		String sqlQuery = String.format("SELECT * FROM TCRS.DRIVINGCITATIONSMUNICIPLE WHERE DRIVERIDCITATIONP='%s'", license);

		// Execute finding account SELECT query
		ResultSet result = databaseManager.executeQuery(sqlQuery);
		
		// Check if the query did not match  in the system
		if (nullCheck(result))
    		return null;
		
    	
    	// Return the found account logged into findAcc
    	return logData(result, findCitation);
    	
	}
	
	
	//**************************** Private Helper Methods ********************************************
	
	private DriverCitation logData(ResultSet result, DriverCitation citation) {
		
		try {
			while (result.next()) {
			     //Retrieve data by column index or name
				citation.citationId = result.getInt("CITATIONID");
				citation.license = result.getString("DRIVERIDCITATIONM");
				citation.ISSUINGOFFICERIDP = result.getInt("ISSUEINGOFFICERIDM");
				citation.dateIssued = result.getString("CITATIONDATE");
				citation.reason = result.getString("CITATIONREASON");
				String fine = result.getString("FINEAMOUNT");
				citation.Paid = result.getString("PAYMENTSTATUS");
				String report = result.getString("PAYMENTSTATUS");
				
				//covert fine amount to double
				citation.fineAmount = Double.valueOf(fine.substring(1));
				
				// convert payment status to boolean
				if(report.equalsIgnoreCase("Yes")) {
					citation.Reportable = true;
				}
				else {
					citation.Reportable = false;
				}
			    				
			return citation;
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
	
	// Find account using account user name
		private DriverCitation findCitation (int citationID) {
			
			// Create account to hold new found account information
			DriverCitation findCitation = new DriverCitation(this.databaseManager);
			
			// Build SQL query using static method
			String sqlQuery = String.format("SELECT * FROM TCRS.DRIVINGCITATIONSMUNICIPLE WHERE CITATIONID='%d'", citationID);

			// Execute finding account SELECT query
			ResultSet result = databaseManager.executeQuery(sqlQuery);
			
			// Check if the query did not match  in the system
			if (nullCheck(result))
	    		return null;
			
	    	
	    	// Return the found account logged into findAcc
	    	return logData(result, findCitation);
	    	
		}
	
	private boolean nullCheck(ResultSet result) {
		
		// Check if the select statement returned a value
    	try {
			if (!result.isBeforeFirst()) {
				System.out.println("Citation not in the system!");
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
	private boolean emptyField(DriverCitation citation) {
		
		return emptyField(citation.license, citation.dateIssued, citation.reason);
		
	}
	
	// Helper method for empty field
	private boolean emptyField(String license, String dateIssued, String reason) {
		
		// Check if any of the fields (except accountID) are empty
	    if (license == null || license.isEmpty()) {
			 System.out.println(String.format("Cannot leave first name blank!"));
	        return true;
	    }
	    if (dateIssued == null || dateIssued.isEmpty()) {
			 System.out.println(String.format("Cannot leave last name blank!"));

	        return true;
	    }
	    if (reason == null || reason.isEmpty()) {
			 System.out.println(String.format("Cannot leave last name blank!"));

	        return true;
	    }

	    
	    // All fields are non-empty, so return true
	    return false;
	}
	
	// Check to ensure the account information is valid
	private boolean validBadgeNumber(String badgeNumber) {
		
		// Create validation objects
		RecordValidation records = new RecordValidation(this.databaseManager);
	    
		// Check if already in the system
		if(records.checkOfficerRecordExistence(badgeNumber)) {
		        return true;
		}

	    return false;
	}
	
	// Check to ensure the account information is valid
		private boolean validLicenseNumber(String license) {
			
			// Create validation objects
			RecordValidation records = new RecordValidation(this.databaseManager);
		    
			// Check if already in the system
			if(records.checkOfficerRecordExistence(license)) {
			        return true;
			}

		    return false;
		}
	
	private boolean inSystem(DriverCitation citation) {
		
		if (citation == null) {
			System.out.println("Vehicle citation not in the system!");
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
	        	System.out.println("Non number was found in incorrect position!");
	            return false;
	        }
	    }

	    return true;
	}
	

}
