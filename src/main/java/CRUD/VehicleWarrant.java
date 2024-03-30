package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import databaseManagement.*;

public class VehicleWarrant {
    private DatabaseManager databaseManager;
    
    public String vin;
    public String dateIssued;
    public String warrantReason;
    public Boolean outstanding;
    public int warrantID;

    public VehicleWarrant(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
 // ******** Setter Methods ********//
	
	
 		public void setVin(String vin) {
 				
 				this.vin = vin;
 				
 			}
 		
 		public void setDateIssued(String dateIssued) {
 			
 			this.dateIssued = dateIssued;
 			
 		}
 		
 		public void setReason(String reason) {
 			
 			this.warrantReason = reason;
 			
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
 		
 	 	public int getWarrantID() {
 	 		return warrantID;
 	 	}
 		public String getVin() {
 			
 			return vin;
 			
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
 		
 		//******** Database Methods ***********

    public void insertVehicleWarrant(String vin, String dateIssued, String warrantReason, String outstanding) {

     
        String sql = String.format(
                "INSERT INTO VEHICLE_WARRANTS (VIN, DATE_ISSUED, WARRANT_REASON, OUTSTANDING) VALUES ('%s', '%s', '%s', %b)",
                vin, dateIssued, warrantReason, outstanding);

        databaseManager.executeUpdate(sql);

        System.out.println("Vehicle warrant added to the database!");
    }

    public void editVehicleWarrant(String warrantID, String vin, String dateIssued, String warrantReason, String outstanding) {
        
    	VehicleWarrant vehicleWarrant = findVehicleWarrant(Integer.valueOf(warrantID));

        if (!inSystem(vehicleWarrant)) {
            return;
        }
        	 
	// Validate VIN number
	

        String sqlQuery = String.format(
                "UPDATE VEHICLE_WARRANTS SET VIN = '%s', DATE_ISSUED = '%s', WARRANT_REASON = '%s', OUTSTANDING = %b WHERE WARRANT_ID = %d",
                vin, dateIssued, warrantReason,outstanding, Integer.valueOf(warrantID));

        databaseManager.executeUpdate(sqlQuery);

        System.out.println("Vehicle warrant edited");
    }

    public void deleteVehicleWarrant(String warrantID) {
    	
        VehicleWarrant vehicleWarrant = findVehicleWarrant(Integer.valueOf(warrantID));

        if (!inSystem(vehicleWarrant)) {
            return;
        }

        String sqlDelete = String.format("DELETE FROM VEHICLE_WARRANTS WHERE WARRANT_ID = %d", warrantID);

        databaseManager.executeUpdate(sqlDelete);

        System.out.println("Vehicle warrant " + warrantID + " removed from system");
    }

    public VehicleWarrant findVehicleWarrant(int warrantID) {
    	
        VehicleWarrant vehicleWarrant = new VehicleWarrant(this.databaseManager);

        String sqlQuery = String.format("SELECT * FROM VEHICLE_WARRANTS WHERE WARRANT_ID = %d", warrantID);

        ResultSet result = databaseManager.executeQuery(sqlQuery);

        if (nullCheck(result))
            return null;

        return logData(result, vehicleWarrant);
    }
    
    public VehicleWarrant findVehicleWarrant(String warrantID) {
    	
        return (findVehicleWarrant(Integer.valueOf(warrantID)));
    }

    public String toString() {
        return "VIN: " + this.vin + " Date Issued: " + this.dateIssued + " Warrant Reason: " + this.warrantReason
                + " Outstanding: " + this.outstanding;
    }
    
    //*************** Private Helper Methods *******************

    private VehicleWarrant logData(ResultSet result, VehicleWarrant vehicleWarrant) {
        try {
            while (result.next()) {
                vehicleWarrant.vin = result.getString("VIN");
                vehicleWarrant.dateIssued = result.getString("DATE_ISSUED");
                vehicleWarrant.warrantReason = result.getString("WARRANT_REASON");
                vehicleWarrant.outstanding = result.getBoolean("OUTSTANDING");

                return vehicleWarrant;
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
                System.out.println("Vehicle warrant not in the system!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    private boolean emptyField(VehicleWarrant vehicleWarrant) {
        return emptyField(vehicleWarrant.vin, vehicleWarrant.dateIssued, vehicleWarrant.warrantReason,
                vehicleWarrant.outstanding);
    }

    private boolean emptyField(String vin, String dateIssued, String warrantReason, Boolean outstanding) {
        if (vin == null || vin.isEmpty()) {
            System.out.println("Cannot leave VIN blank!");
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


 
    //*********** Object Methods *******
  
    private boolean inSystem(VehicleWarrant vehicleWarrant) {
        if (vehicleWarrant == null) {
            System.out.println("Vehicle warrant not in the system!");
            return false;
        }

        return true;
    }
    
    public void editVehicleWarrant(int warrantID, VehicleWarrant newVehicleWarrant) {
        
    	editVehicleWarrant(String.valueOf(warrantID), newVehicleWarrant.getVin(), newVehicleWarrant.getdateIssued(), newVehicleWarrant.getReason(),
                newVehicleWarrant.getOutstanding());
    	
    }

    public void insertVehicleWarrant(VehicleWarrant vehicleWarrant) {
        insertVehicleWarrant(vehicleWarrant.getVin(), vehicleWarrant.getdateIssued(), vehicleWarrant.getReason(),
                vehicleWarrant.getOutstanding());
    }
}
