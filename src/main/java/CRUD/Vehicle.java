package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseManagement.*;

public class Vehicle {

    private DatabaseManager databaseManager;

    public String vin;
    public String licensePlate;
    public String make;
    public String model;
    public int year;
    public String registeredStatus;

    public Vehicle(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
 // ******** Setter Methods ********//
	
 	public void setlicensePlate(String licensePlate) {
 		
 		this.licensePlate = licensePlate;
 		
 	}
 	
 	public void setMake(String make) {
 			
 			this.make = make;
 			
 		}
 	
 	public void setModel(String model) {
 		
 		this.model = model;
 		
 	}
 	
 	
 	public void setYear(String yearCar) {
 		
 	// Check if account Id is an number
	if(!isNumber(yearCar)) {
		System.out.println("Unable set year! Check year of car!");
		return;
	}
	
	int year = Integer.valueOf(yearCar);
 		
 		this.year = year;
 		
 	}
 	
 	//********* Getter Methods ********//
    
 	public String getVin() {
 		
 		return vin;
 		
 	}
 	public String getLicensePlate() {
 		
 		return licensePlate;
 		
 	}
 	
 	public String getMake() {
 			
 			return make;
 			
 		}
 	
 	public String getModel() {
 		
 		return model;
 		
 	}
 	
 	
 	public String getYear() {
 		
 		// Convert in to String
 		
	String yearVeh = String.valueOf(year);
 		
		return yearVeh;

 		
 	}
 	
 	public String getRegisteredStatus() {
 		
 		return registeredStatus;
 		
 	}
 	
 	
 	
 	//********** Database Methods **********//
 	

    public void insertVehicle(Vehicle vehicle) {
        insertVehicle(vehicle.getVin(), vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(),
                vehicle.getRegisteredStatus());
    }

    // Insert Vehicle
    public void insertVehicle(String vin, String licensePlate, String make, String model, String year,
            String registeredStatus) {
    	
      

        String sql = String.format("INSERT INTO TCRS.VEHICLEINFO (VIN, LICENSEPLATE, MAKE, MODEL, CARYEAR, REGISTEREDSTATUS) "
                + "VALUES ('%s', '%s', '%s', '%s', %d, '%s')", vin, licensePlate, make,
                model, Integer.valueOf(year), registeredStatus);

        databaseManager.executeUpdate(sql);

        System.out.println("Vehicle added to the database!");
    }

    // Edit Vehicle
    public void editVehicle(String vin, String licensePlate, String make, String model, String year,
            String registeredStatus) {
        
    	Vehicle vehicle = findVehicle(vin);

        if (!inSystem(vehicle)) {
            return;
        }
        
        String sqlQuery = String.format("UPDATE TCRS.VEHICLEINFO SET LICENSEPLATE = '%s', MAKE = '%s', MODEL = '%s', "
                + "CARYEAR = %d, REGISTEREDSTATUS = '%s' WHERE VIN = '%s'", licensePlate, make,
                model, Integer.valueOf(year), registeredStatus, vin);
        
        databaseManager.executeUpdate(sqlQuery);
        
        System.out.println("Vehicle edited");
    }

    public void deleteVehicle(String vin) {
    	
        Vehicle vehicle = findVehicle(vin);
        
        if (!inSystem(vehicle)) {
            return;
        }
        
        String sqlDelete = String.format("DELETE FROM TCRS.VEHICLEINFO WHERE VIN= '%s'", vin);
        
        databaseManager.executeUpdate(sqlDelete);
        
        System.out.println("Vehicle " + vin + " removed from system");
    }

    public Vehicle findVehicle(String vin) {
    	
        Vehicle vehicle = new Vehicle(this.databaseManager);

        String sqlQuery = String.format("SELECT * FROM TCRS.VEHICLEINFO WHERE VIN='%s'", vin);

        ResultSet result = databaseManager.executeQuery(sqlQuery);

        if (nullCheck(result))
            return null;

        return logData(result, vehicle);
    }

    public String toString() {
        return "VIN: " + this.vin + " License Plate: " + this.licensePlate + " Make: " + this.make + " Model: "
                + this.model + " Year: " + this.year + " Registered Status: " + this.registeredStatus;
    }

    // helper methods
    private Vehicle logData(ResultSet result, Vehicle vehicle) {
        try {
            while (result.next()) {
                vehicle.vin = result.getString("VIN");
                vehicle.licensePlate = result.getString("LICENSEPLATE");
                vehicle.make = result.getString("MAKE");
                vehicle.model = result.getString("MODEL");
                vehicle.year = result.getInt("CARYEAR") ;
                vehicle.registeredStatus = result.getString("REGISTEREDSTATUS");

                return vehicle;
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
                System.out.println("Vehicle not in the system!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private boolean emptyField(Vehicle vehicle) {
        return emptyField(vehicle.vin, vehicle.licensePlate, vehicle.make, vehicle.model, vehicle.registeredStatus);
    }

    private boolean emptyField(String vin, String licensePlate, String make, String model, String registeredStatus) {
        if (vin == null || vin.isEmpty()) {
            System.out.println(String.format("Cannot leave VIN blank!"));
            return true;
        }
        if (licensePlate == null || licensePlate.isEmpty()) {
            System.out.println(String.format("Cannot leave license plate blank!"));
            return true;
        }
        if (make == null || make.isEmpty()) {
            System.out.println(String.format("Cannot leave make blank!"));
            return true;
        }
        if (model == null || model.isEmpty()) {
            System.out.println(String.format("Cannot leave model blank!"));
            return true;
        }
        if (registeredStatus == null || registeredStatus.isEmpty()) {
            System.out.println(String.format("Cannot leave registered status blank!"));
            return true;
        }

        return false;
    }

   
    
    

    private void editVehicle(String vin, Vehicle newVehicle) {
        
    	Vehicle vehicle = findVehicle(vin);

        if (!inSystem(vehicle)) {
            return;
        }
        
        String sqlQuery = String.format("UPDATE TCRS.VEHICLEINFO SET LICENSEPLATE = '%s', MAKE = '%s', MODEL = '%s', "
                + "YEAR = %d, REGISTEREDSTATUS = '%s' WHERE VIN = '%s'", newVehicle.getLicensePlate(), newVehicle.getMake(),
                newVehicle.getModel(), Integer.valueOf(newVehicle.getYear()), newVehicle.getRegisteredStatus(), vehicle.getVin());
        databaseManager.executeUpdate(sqlQuery);
        System.out.println("Vehicle edited");
    }

    private boolean inSystem(Vehicle vehicle) {
        if (vehicle == null) {
            System.out.println("Vehicle not in the system!");
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