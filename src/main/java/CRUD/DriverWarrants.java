package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import databaseManagement.*;

public class DriverWarrants {
    private DatabaseManager databaseManager;
    
    private int warrantID;
    private String licenseNumber;
    private String dateIssued;
    private String warrantReason;
    private Boolean outstanding;

    public DriverWarrants(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
 // ******** Setter Methods ********//
	
	
 	public void setlicense(String licenseNumber) {
 			
 			this.licenseNumber = licenseNumber;
 			
 		}
 	
 	public void setDateIssued(String dateIssued) {
 		
 		this.dateIssued = dateIssued;
 		
 	}

 	public void setWarrantReason(String warrantReason) {
 		
 		this.warrantReason = warrantReason;
 		
 	}
 	
 	
 	public void setOutstanding(String outstandingWarrant) {
 		
 		Boolean outstanding;
 		
 		if(outstandingWarrant.equalsIgnoreCase("Yes")) {
 			
 			outstanding = true;
 			
 		}
 		else 
 			outstanding = false;
 		
 		this.outstanding = outstanding;
 		
 	}
 	
 	
 	//********* Getter Methods ********//
 	
 	public String getWarrantId() {
 		
 		String warrID = String.valueOf(warrantID);
 		
 		return warrID;
 		
 	}

 	public String getLicenseNumber() {
 		
 		return licenseNumber;
 		
 	}
 	
 	
 	public String getdateIssued() {
 		
 		return dateIssued;
 		
 	}
 	
 	public String getReason() {
 		
 		return warrantReason;
 		
 	}
 	
 	
 	
 	public String getOutstanding() {

 		String outstandingWarrant;
		
		if(outstanding == true) {
			
			outstandingWarrant = "Yes";
			
		}
		else 
			outstandingWarrant = "No";
		
 		return outstandingWarrant;
 		
 	}
 		

 	// ******** Class Methods *********
 	
    public int insertDriverWarrant(String licenseNumber, String dateIssued, String warrantReason,
            String outstanding) {

       

        String sql = String.format(
                "INSERT INTO TCRS.DRIVERWARRANTSMUNICIPLE (DRIVERIDWARRANTM, WARRANTDATE, REASON, OUTSTANDING) VALUES ('%s', '%s', '%s', %b)",
                licenseNumber, dateIssued, warrantReason, outstanding);

        // Generate new entry and return Id statement
	    int generatedId = databaseManager.executeInsertReturnId(sql);
	    
	    System.out.println("Driver warrant added to the database!");
	    
	    return generatedId;
	    
    }
    
    

    public void editDriverWarrant(String warrantID, String licenseNumber, String dateIssued, String warrantReason,
            String outstanding) {
    	
    	// Check if account Id is an number
    	if(!isNumber(warrantID)) {
    		System.out.println("Unable edit warrant! Check Id  number!");
    		return;
    	}
    	
    	int warrID = Integer.valueOf(warrantID);
    	
        DriverWarrants driverWarrant = findDriverWarrant(warrID);

        if (!inSystem(driverWarrant)) {
            return;
        }
     		
		// Validate if license is in system
		 if (!validLicenseNumber(licenseNumber)) {
			 System.out.println("Licenser not in the system!");
			 return;
		 }

        String sqlQuery = String.format(
                "UPDATE TCRS.DRIVERWARRANTSMUNICIPLE SET DRIVERIDWARRANTM = '%s', WARRANTDATE = '%s', REASON = '%s', OUTSTANDING = %b WHERE WARRANTID = %d",
                licenseNumber, dateIssued, warrantReason, outstanding, warrantID);

        databaseManager.executeUpdate(sqlQuery);

        System.out.println("Driver warrant edited");
    }
   

    public void deleteDriverWarrant(String warrantID) {
    	
    	// Check if account Id is an number
    	if(!isNumber(warrantID)) {
    		System.out.println("Unable to delete warrant! Check Id number!");
    		return;
    	}
    	
    	int warrId = Integer.valueOf(warrantID);
    	
        DriverWarrants driverWarrant = findDriverWarrant(warrId);

        if (!inSystem(driverWarrant)) {
            return;
        }

        String sqlDelete = String.format("DELETE FROM TCRS.DRIVERWARRANTSMUNICIPLE WHERE WARRANTID = %d", warrantID);

        databaseManager.executeUpdate(sqlDelete);

        System.out.println("Driver warrant " + warrantID + " removed from system");
    }

    public DriverWarrants findDriverWarrant(int warrantID) {
        DriverWarrants driverWarrant = new DriverWarrants(this.databaseManager);

        String sqlQuery = String.format("SELECT * FROM TCRS.DRIVERWARRANTSMUNICIPLE WHERE WARRANTID = %d", warrantID);

        ResultSet result = databaseManager.executeQuery(sqlQuery);

        if (nullCheck(result))
            return null;

        return logData(result, driverWarrant);
    }
    
    // Find class with string paramter
    public DriverWarrants findDriverWarrant(String warrantID) {
        
    	// Check if account Id is an number
    	if(!isNumber(warrantID)) {
    		System.out.println("Unable find warant! Check warrant ID number!");
    		return null;
    	}
    		int warrID = Integer.valueOf(warrantID);
    		
    		return findDriverWarrant( warrID);
    	
    }

    public String toString() {
        return "License Number: " + this.licenseNumber + " Date Issued: " + this.dateIssued + " Warrant Reason: "
                + this.warrantReason + " Outstanding: " + this.outstanding;
    }

    // ************* Object Based Methods *********
    
    // Insert using Warrant Object
    public void insertDriverWarrant(DriverWarrants driverWarrant) {

        insertDriverWarrant(driverWarrant.getLicenseNumber(), driverWarrant.getdateIssued(), driverWarrant.getReason(),
        		driverWarrant.getOutstanding());
    }
    
    public void editDriverWarrant(int warrantID, DriverWarrants newDriverWarrant) {

    	editDriverWarrant(newDriverWarrant.getWarrantId(),  newDriverWarrant.getLicenseNumber(),  newDriverWarrant.getdateIssued(),  newDriverWarrant.getReason(), newDriverWarrant.getOutstanding());
    	
    }
    
    
    // ********** Private Helper Methods *********
    
    private DriverWarrants logData(ResultSet result, DriverWarrants driverWarrant) {
        try {
            while (result.next()) {
                driverWarrant.licenseNumber = result.getString("DRIVERIDWARRANTM");
                driverWarrant.dateIssued = result.getString("WARRANTDATE");
                driverWarrant.warrantReason = result.getString("REASON");
                driverWarrant.outstanding = result.getBoolean("OUTSTANDING");

                return driverWarrant;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
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
        try {
            if (!result.isBeforeFirst()) {
                System.out.println("Driver warrant not in the system!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    private boolean emptyField(DriverWarrants driverWarrant) {
        return emptyField(driverWarrant.licenseNumber, driverWarrant.dateIssued, driverWarrant.warrantReason,
                driverWarrant.outstanding);
    }

    private boolean emptyField(String licenseNumber, String dateIssued, String warrantReason, Boolean outstanding) {
        if (licenseNumber == null || licenseNumber.isEmpty()) {
            System.out.println("Cannot leave license number blank!");
            return true;
        }
        if (dateIssued == null || dateIssued.isEmpty()) {
            System.out.println("Cannot leave date issued blank!");
            return true;
        }
        if (warrantReason == null || warrantReason.isEmpty()) {
            System.out.println("Cannot leave warrant reason blank!");
            return true;
        }
        if (outstanding == null) {
            System.out.println("Cannot leave outstanding field blank!");
            return true;
        }

        return false;
    }
    
   

    
    private boolean inSystem(DriverWarrants driverWarrant) {
        if (driverWarrant == null) {
            System.out.println("Driver warrant not in the system!");
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
 	
}
