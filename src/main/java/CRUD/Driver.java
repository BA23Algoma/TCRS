package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseManagement.*;

public class Driver {

    private DatabaseManager databaseManager;

    public String licenseNumber;
    public String licensePlate;
    public String firstName;
    public String lastName;
    public String licenseStatus;
    public int demeritPoints;

    public Driver(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
 // ******** Setter Methods ********//
	
 	public void setlicensePlate(String licensePlate) {
 		
 		this.licensePlate = licensePlate;
 		
 	}
 	
 	public void setFirstName(String firstName) {
 			
 			this.firstName = firstName;
 			
 		}
 	
 	public void setLastName(String lastName) {
 		
 		this.lastName = lastName;
 		
 	}
 	
 	public void setLicenseStatus(String licenseStatus) {
 		
 		this.licenseStatus = licenseStatus;
 		
 	}
 	
 	public void setdemeritPoints(String demPoints) {
 		
 	// Check if account Id is an number
	if(!isNumber(demPoints)) {
		System.out.println("Unable set demerit points! Demerit points number!");
		return;
	}
	
	int demeritPoints = Integer.valueOf(demPoints);
 		
 		this.demeritPoints = demeritPoints;
 		
 	}
 	
 	//********* Getter Methods ********//
 	
 	public String getLicensePlate() {
 		
 		return licensePlate;
 		
 	}
 	
 	public String getLicenseNumber() {
 		return licenseNumber;
 	}
 	public String getFirstName() {
 			
 			return firstName;
 			
 		}
 	
 	public String getLastName() {
 		
 		return lastName;
 		
 	}
 	
 	public String getLicenseStatus() {
 		
 		return licenseStatus;
 		
 	}
 	
 	public String getDemeritPoints() {
 		
 		// Convert in to String
 		
	String demPoints = String.valueOf(demeritPoints);
 		
		return demPoints;

 		
 	}
 	
 	
 	//********** Database Methods **********//
 	

    public void insertDriver(String licenseNumber, String licensePlate, String firstName, String lastName,
            String licenseStatus, String demeritPoints) {
        Driver driver = new Driver(databaseManager);

        driver.licenseNumber = licenseNumber;
        driver.licensePlate = licensePlate;
        driver.firstName = firstName;
        driver.lastName = lastName;
        driver.licenseStatus = licenseStatus;
        driver.demeritPoints = Integer.parseInt(demeritPoints);

       

        String sql = String.format(
                "INSERT INTO TCRS.DRIVERINFO (LICENSENUMBER, LICENSEPLATE, FIRSTNAME, LASTNAME, LICENSESTATUS, DEMERITPOINTS) "
                        + "VALUES ('%s', '%s', '%s', '%s', '%s', %d)",
                driver.licenseNumber, driver.licensePlate, driver.firstName,
                driver.lastName, driver.licenseStatus, driver.demeritPoints);

     // Pass prepared statement to databaseManager for execution
	    databaseManager.executeUpdate(sql);
	    
	    System.out.println("Driver added to the database!");


    }

  public void editDriver(String licenseNumber, String licensePlate, String firstName, String lastName,
          String licenseStatus, String demeritPoints) {
    	
        Driver driver = findDriver(licenseNumber);

        if (!inSystem(driver)) {
            return;
        }
        String sqlQuery = String.format(
                "UPDATE TCRS.DRIVERINFO SET LICENSEPLATE = '%s', FIRSTNAME = '%s', LASTNAME = '%s', "
                        + "LICENSESTATUS = '%s', DEMERITPOINTS = %d WHERE LICENSENUMBER = '%s'",
                licensePlate, firstName, lastName, licenseStatus, Integer.valueOf(demeritPoints), licenseNumber);
        databaseManager.executeUpdate(sqlQuery);
        
        System.out.println("Driver edited");
    }

    public void deleteDriver(String licenseNumber) {
    	
        Driver driver = findDriver(licenseNumber);
        if (!inSystem(driver)) {
            return;
        }
        String sqlDelete = String.format("DELETE FROM TCRS.DRIVERINFO WHERE LICENSENUMBER= '%s'", licenseNumber);
        databaseManager.executeUpdate(sqlDelete);
        System.out.println("Driver " + licenseNumber + " removed from system");
    }

    public Driver findDriver(String licenseNumber) {
        Driver driver = new Driver(this.databaseManager);

        String sqlQuery = String.format("SELECT * FROM TCRS.DRIVERINFO WHERE LICENSENUMBER='%s'", licenseNumber);

        ResultSet result = databaseManager.executeQuery(sqlQuery);

        if (nullCheck(result)) {
        	System.out.println("Not in system");
        	return null;
        }
            
        return logData(result, driver);
    }

    public String toString() {
        return "License Number: " + this.licenseNumber + " License Plate: " + this.licensePlate + " First Name: "
                + this.firstName + " Last Name: "
                + this.lastName + " License Status: " + this.licenseStatus + " Demerit Points: " + this.demeritPoints;
    }
    
 // Auto fill data
 	public String[][] autoInputAccount (String license) {
 		
 		
 		// First confirm account exist, and if so return account information
 		Driver findDriver = findDriver(license);
 		
 		// Check to see if the account is in the system
 		if (!inSystem(findDriver)) {
 			return null;
 		}
 		String strDemeritPoint = String.valueOf(demeritPoints);
 	    
 		String autoFill[][] = {
 				{ "licensePlate", findDriver.licensePlate},
 				{ "firstName", findDriver.firstName},
 				{ "lastName", findDriver.lastName},
 				{ "licenseStatus", findDriver.licenseStatus},
 				{ "demeritPoints", strDemeritPoint}
 		};

 		return autoFill;
 		
 	}

    // helper methods
    private Driver logData(ResultSet result, Driver driver) {
        try {
            while (result.next()) {
                driver.licenseNumber = result.getString("LICENSENUMBER");
                driver.licensePlate = result.getString("LICENSEPLATE");
                driver.firstName = result.getString("FIRSTNAME");
                driver.lastName = result.getString("LASTNAME");
                driver.licenseStatus = result.getString("LICENSESTATUS");
                driver.demeritPoints = result.getInt("DEMERITPOINTS");

                return driver;
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
                System.out.println("Driver not in the system!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

   

    private boolean inSystem(Driver driver) {
        if (driver == null) {
            System.out.println("Driver not in the system!");
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
	
	//********* Object Methods ********
	
    
  public void editDriver(String licenseNumber, Driver newDriver) {
    	
    	editDriver( licenseNumber,  newDriver.getLicensePlate(),  newDriver.getFirstName(),  newDriver.getLastName(),  newDriver.getLicenseStatus(), newDriver.getDemeritPoints());
        
    }
 	
}
