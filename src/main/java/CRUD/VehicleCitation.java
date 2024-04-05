package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseManagement.DatabaseManager;

import databaseManagement.RecordValidation;

public class VehicleCitation {
	
	private DatabaseManager databaseManager;

	public int citationId;
	public String vin;
	public int ISSUINGOFFICERBadgeNumber;
	public String dateIssued;
	public String reason;
	public Double fineAmount;
	public boolean Paid;
	
	public VehicleCitation(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	// ******** Setter Methods ********//
	
		
		public void setVin(String vin) {
				
				this.vin = vin;
				
			}
		
		public void setISSUINGOFFICERBadgeNumber(String ISSUINGOFFICERBadgeNumber) {
			
			if(!isNumber(ISSUINGOFFICERBadgeNumber)) {
				System.out.println("Invaild badge number!");
				return;
			}
						
			int badge = Integer.valueOf(ISSUINGOFFICERBadgeNumber);
			
			this.ISSUINGOFFICERBadgeNumber = badge;
			
		}
		
		public void setDateIssued(String dateIssued) {
			
			this.dateIssued = dateIssued;
			
		}
		
		public void setReason(String reason) {
			
			this.reason = reason;
			
		}
		
		public void setfine(String fine) {
			
			// Check if account Id is an number
			if(!isNumber(fine)) {
				System.out.println("Unable set demerit points! Demerit points number!");
				return;
			}
			
			double citFine = Double.valueOf(fine);
		 		
		 		this.fineAmount = citFine;
			
		}
		
		public void setPaid(String Paid) {
			
			Boolean PaidBool;
			if (Paid.equalsIgnoreCase("Yes")) {
				PaidBool = true;
			}
			else
				PaidBool = false;
			this.Paid = PaidBool;
			
		}
		
		//********* Getter Methods ********//
		
		public String getcitationId() {
			
			String citId = String.valueOf(citationId);
			
			return citId;
			
		}

		public String getVin() {
			
			return vin;
			
		}
		
		public String getISSUINGOFFICERBadgeNumber() {
				
			String badge = String.valueOf(ISSUINGOFFICERBadgeNumber);

			return badge;
				
			}
		
		public String getdateIssued() {
			
			return dateIssued;
			
		}
		
		public String getReason() {
			
			return reason;
			
		}
		
		public String getFineAmount() {
			
			return String.valueOf(fineAmount);
			
		}
		
		public String getPaid() {
			
			String PaidBool;
			if (Paid) {
				PaidBool = "Yes";
			}
			else
				PaidBool = "No";
			
			return PaidBool;
			
		}
	
		//********** Database Methods **********//

	
	public int insertVehicleCitation (String vin, String officer, String dateIssued, String reason,  String fine, String Paid) {		
		
		if(!isNumber(officer)) {
			System.out.println("Invaild badge number account!");
			return -1;
		}
		else if(!isNumber(fine)) {
			System.out.println("Invaild fine amount!");
			return -1;
		}
		
		// Validate format and if officer is in the system
		 if (!validBadgeNumber(officer)) {
			 System.out.println("Officer badge number not in the system!");
			 return -1;
		 }
		 
		// Validate VIN number
		 if (!valiadteVin(vin)) {
			 System.out.println("Vin not in the system!");
			 return -1;
		 }
		 
		// Create SQL query string
	    String sql = String.format("INSERT INTO TCRS.VEHICLECITATIONSMUNICIPLE (ISSUINGOFFICERIDM , VINCITATIONM , "
	    		+ "CITATIONREASON ,  CITATIONDATE , FINEAMOUNT, PAYMENTSTATUS )"
	    		+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", officer, vin, 
				reason, dateIssued, fine, Paid);
	    
	 // Generate new entry and return Id statement
	    int generatedId = databaseManager.executeInsertReturnId(sql);
	    
	    System.out.println("Vehcile citation added to the database!");
	    
	    return generatedId;
		
	}
	
	public int editVehicleCitation (String citID, String vin, String officer, String dateIssued, String reason,  String fine, String Paid) {
		
		if(!isNumber(citID)) {
			System.out.println("Invaild vehicle citation, unable to edit account!");
			return -1;
		}
		
		// First confirm account exist, and if so return account information
		VehicleCitation citation = findCitation(citID);
		
		// Check to see if the account is in the system
		if (!inSystem(citation)) {
			return -1;
		}
		
		// Validate format and if officer is in the system
		 if (!validBadgeNumber(officer)) {
			 System.out.println("Officer badge number not in the system!");
			 return -1;
		 }
		 
		// Validate VIN number
		 if (!valiadteVin(vin)) {
			 System.out.println("Vin not in the system!");
			 return -1;
		 }
		
		// Build edit query in system based on citation ID
		String sqlQuery = String.format("UPDATE TCRS.VEHICLECITATIONSMUNICIPLE SET ISSUINGOFFICERIDM = '%s', VINCITATIONM = '%s', CITATIONREASON = '%s', "
				+ "CITATIONDATE = '%s', FINEAMOUNT = '%s', PAYMENTSTATUS = '%s' WHERE CITATIONID = %d", 
				officer, vin, reason, dateIssued, fine, Paid, Integer.valueOf(citID));

		// Execute query
		databaseManager.executeUpdate(sqlQuery);
		
		System.out.println("Account edited");
		
		return 0;
	}
	
	public void deleteVehicleCitation (String citID) {
		
		if(!isNumber(citID)) {
			System.out.println("Invaild vehicle citation, unable to delete account!");
			return;
		}

		// First confirm account exist, and if so return account information
		VehicleCitation citation = findCitation(citID);
		
		// Check to see if the account is in the system
		if (!inSystem(citation)) {
			return;
		}
				
		// Create query to delete account
		String sqlDelete = String.format("DELETE FROM TCRS.VEHICLECITATIONSMUNICIPLE WHERE CITATIONID= %d", Integer.valueOf(citID));
		
		// Execute deleting of account
		databaseManager.executeUpdate(sqlDelete);
		
		System.out.println("Citation " + citID + " removed from system!");

	}

	public void autoInputVehicleCitation(int citationID) {
		
	}
	
	public String toString() {
		
		return "VIN Number " + this.vin + " Officer Badge Number: " + this.ISSUINGOFFICERBadgeNumber + " Date Issued: " + this.dateIssued
				+ " Reason:" + this.reason + " Fine Amount: $" + this.fineAmount + " Paid: " + this.Paid;
	}
	
	// Find account using account user name
	public VehicleCitation findCitation (String citationID) {
		
		// Create account to hold new found account information
		VehicleCitation findCitation = new VehicleCitation(this.databaseManager);
		
		// Build SQL query using static method
		String sqlQuery = String.format("SELECT * FROM TCRS.VEHICLECITATIONSMUNICIPLE WHERE CITATIONID= %d", Integer.valueOf(citationID));

		// Execute finding account SELECT query
		ResultSet result = databaseManager.executeQuery(sqlQuery);
		
		// Check if the query did not match  in the system
		if (nullCheck(result))
    		return null;
		
    	
    	// Return the found account logged into findAcc
    	return logData(result, findCitation);
    	
	}
	
	// Find account using account user name
	public VehicleCitation findCitationVin (String vin) {
		
		// Create account to hold new found account information
		VehicleCitation findCitation = new VehicleCitation(this.databaseManager);
		
		// Build SQL query using static method
		String sqlQuery = String.format("SELECT * FROM TCRS.VEHICLECITATIONSMUNICIPLE WHERE VINCITATIONM='%s'", vin);

		// Execute finding account SELECT query
		ResultSet result = databaseManager.executeQuery(sqlQuery);
		
		// Check if the query did not match  in the system
		if (nullCheck(result))
    		return null;
		
    	
    	// Return the found account logged into findAcc
    	return logData(result, findCitation);
    	
	}
	//********* Object Methods ******
	
	public int insertVehicleCitation (VehicleCitation citation) {
		
		return insertVehicleCitation(citation.getVin(), citation.getISSUINGOFFICERBadgeNumber(), citation.getdateIssued(),
				citation.getReason(), citation.getFineAmount(), citation.getPaid());
		
	}
	
	public int editVehicleCitation (String citID, VehicleCitation citationNew) {
		
		return editVehicleCitation ( citID,  citationNew.getVin(),  citationNew.getISSUINGOFFICERBadgeNumber(),  citationNew.getdateIssued(),  citationNew.getReason(),   citationNew.getFineAmount(),  citationNew.getPaid());

		
	}
	//**************************** Private Helper Methods ********************************************
	
	private VehicleCitation logData(ResultSet result, VehicleCitation citation) {
		
		try {
			while (result.next()) {
			     //Retrieve data by column index or name
				citation.citationId = result.getInt("CITATIONID");
				citation.vin = result.getString("VINCITATIONM");
				citation.ISSUINGOFFICERBadgeNumber = result.getInt("ISSUINGOFFICERIDM");
				citation.dateIssued = result.getString("CITATIONDATE");
				citation.reason = result.getString("CITATIONREASON");
				String fine = result.getString("FINEAMOUNT");
				String paid = result.getString("PAYMENTSTATUS");
				
				//covert fine amount to double
				citation.fineAmount = Double.valueOf(fine.substring(1));
				
				// convert payment status to boolean
				if(paid.equalsIgnoreCase("Yes")) {
					citation.Paid = true;
				}
				else {
					citation.Paid = false;
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
		private boolean valiadteVin(String vin) {
			
			// Create validation objects
			RecordValidation records = new RecordValidation(this.databaseManager);
		    
			// Check if already in the system
			if(records.checkVehicleRecordExistence(vin)) {
			        return true;
			}

		    return false;
		}
	
	private boolean inSystem(VehicleCitation citation) {
		
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
	        	System.out.println("Non number was found!");
	            return false;
	        }
	    }

	    return true;
	}
	

}