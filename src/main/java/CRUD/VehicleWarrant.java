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

    public int insertVehicleWarrant(String vin, String dateIssued, String warrantReason, String outstanding) {

     if(findVehicle(vin) == null) {
    	 return -1;
     }
    	
        String sql = String.format(
                "INSERT INTO TCRS.VEHICLEWARRANTSMUNICIPLE (VINWARRANTM, WARRANTDATE, REASON, OUTSTANDING) VALUES ('%s', '%s', '%s', '%s')",
                vin, dateIssued, warrantReason, outstanding);

        databaseManager.executeUpdate(sql);
        
        // Generate new entry and return Id statement
	    int generatedId = databaseManager.executeInsertReturnId(sql);
	    
	    System.out.println("Vehicle warrant added to the database!");
	    
	    return generatedId;
	    
        
    }

    public int editVehicleWarrant(String warrantID, String vin, String dateIssued, String warrantReason, String outstanding) {
        
    	VehicleWarrant vehicleWarrant = findVehicleWarrant(Integer.valueOf(warrantID));

        if (!inSystem(vehicleWarrant)) {
            return -1;
        }
        	 
        // Validate VIN number
        if(findVehicle(vin) == null) {
       	 return -1;
        }

        String sqlQuery = String.format(
                "UPDATE TCRS.VEHICLEWARRANTSMUNICIPLE SET VINWARRANTM = '%s', WARRANTDATE = '%s', REASON = '%s', OUTSTANDING = %b WHERE WARRANTIDVM = %d",
                vin, dateIssued, warrantReason,outstanding, Integer.valueOf(warrantID));

        databaseManager.executeUpdate(sqlQuery);

        System.out.println("Vehicle warrant edited");
        
        return 0;
    }

    public void deleteVehicleWarrant(String warrantID) {
    	
        VehicleWarrant vehicleWarrant = findVehicleWarrant(Integer.valueOf(warrantID));

        if (!inSystem(vehicleWarrant)) {
            return;
        }
        
        String sqlDelete = String.format ("DELETE FROM TCRS.VEHICLEWARRANTSMUNICIPLE WHERE WARRANTIDVM = %d", Integer.parseInt(warrantID));

        databaseManager.executeUpdate(sqlDelete);

        System.out.println("Vehicle warrant " + warrantID + " removed from system");
    }

    public VehicleWarrant findVehicleWarrant(int warrantID) {
    	
        VehicleWarrant vehicleWarrant = new VehicleWarrant(this.databaseManager);

        String sqlQuery = String.format("SELECT * FROM TCRS.VEHICLEWARRANTSMUNICIPLE WHERE WARRANTIDVM = %d", warrantID);

        ResultSet result = databaseManager.executeQuery(sqlQuery);

        if (nullCheck(result))
            return null;

        return logData(result, vehicleWarrant);
    }
    
    public VehicleWarrant findVehicleWarrant(String warrantID) {
    	
        return (findVehicleWarrant(Integer.valueOf(warrantID)));
    }
    
 public VehicleWarrant findVehicle(String vin) {
    	
        VehicleWarrant vehicleWarrant = new VehicleWarrant(this.databaseManager);

        String sqlQuery = String.format("SELECT * FROM TCRS.VEHICLEWARRANTSMUNICIPLE WHERE VINWARRANTM = '%s'", vin);

        ResultSet result = databaseManager.executeQuery(sqlQuery);

        if (nullCheck(result))
            return null;

        return logData(result, vehicleWarrant);
    }

    public String toString() {
        return "Warrant ID: " + warrantID + " VIN: " + this.vin + " Date Issued: " + this.dateIssued + " Warrant Reason: " + this.warrantReason
                + " Outstanding: " + this.outstanding;
    }
    
    //*************** Private Helper Methods *******************

    private VehicleWarrant logData(ResultSet result, VehicleWarrant vehicleWarrant) {
        try {
            while (result.next()) {
                vehicleWarrant.warrantID = Integer.valueOf(result.getString("WARRANTIDVM"));
                vehicleWarrant.vin = result.getString("VINWARRANTM");
                vehicleWarrant.dateIssued = result.getString("WARRANTDATE");
                vehicleWarrant.warrantReason = result.getString("REASON");
                String outstanding = result.getString("OUTSTANDING");
                
             // convert payment status to boolean
				if(outstanding.equalsIgnoreCase("Yes")) {
					vehicleWarrant.outstanding = true;
				}
				else {
					vehicleWarrant.outstanding = false;
				}

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