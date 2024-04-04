package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import databaseManagement.*;

public class Report {

    private DatabaseManager databaseManager;

    public Report(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    } 

    public String generateVehicleReport(String vin) {
    	String[] fields = {"VIN", "LICENSEPLATE","MAKE","MODEL", "CARYEAR","REGISTEREDSTATUS"};
        String sqlQuery = String.format("SELECT * FROM TCRS.VEHICLEINFO WHERE VIN='%s'", vin);
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("Vehicle not found in the system!");
            return null;
        }
        return logData(result, fields);
    }

    public String generateDriverReport(String license) {
    	String[] fields = {"FIRSTNAME", "LASTNAME","LICENSENUMBER","LICENSEPLATE", "LICENSESTATUS","DEMERITPOINTS"};
        String sqlQuery = String.format("SELECT * FROM TCRS.DRIVERINFO WHERE LICENSENUMBER='%s'", license);
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("Driver not found in the system!");
            return null;
        }
        return logData(result, fields);
    }

    public String generateDrivingRecord(String license, String startDate, String endDate) {
    	
    	String[] fields = {"CITATIONID", "ISSUEINGOFFICERIDM", "DRIVERIDCITATIONM", "CITATIONREASON", 
    			"CITATIONDATE", "FINEAMOUNT", "PAYMENTSTATUS"};
        
        String sqlQuery = String.format("SELECT * FROM TCRS.DRIVINGCITATIONSMUNICIPLE WHERE DRIVERIDCITATIONM='%s' AND REPORTABLE='Yes' "
        		+ "AND PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') BETWEEN PARSEDATETIME('%s', 'yyyy-MM-dd') AND PARSEDATETIME('%s', 'yyyy-MM-dd')",
        		license, startDate, endDate);
        
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("No citation records found for the given period!");
            return generateDriverReport(license);
        }
        return generateDriverReport(license) + "\nCitation History\n\n" + logData(result, fields);
    }

    public String generateCitationSummary(
            Optional<String> tfIssuingOff,
            Optional<String> tfStartDate,
            Optional<String> tfEndDate,
            Optional<String> cbReasonDrivVeh,
            Optional<String> cbPaid) {
    	
    	String[] fieldDriver= {"CITATIONID", "ISSUEINGOFFICERIDM", "DRIVERIDCITATIONM", "CITATIONREASON",
    	        "CITATIONDATE", "FINEAMOUNT", "PAYMENTSTATUS", "REPORTABLE"};
    	String[] fieldVehicle= {"CITATIONID", "ISSUEINGOFFICERIDM", "VINCITATIONM", "CITATIONREASON",
    		    "CITATIONDATE", "FINEAMOUNT", "PAYMENTSTATUS"};
    	
        String sqlQueryDriver = String.format("SELECT * FROM TCRS.DRIVINGCITATIONSMUNICIPLE");
        String sqlQueryVehicle = String.format("SELECT * FROM TCRS.VEHICLECITATIONSMUNICIPLE");

        Boolean initial = true; // check whether or not to use AND in sql string

        // Issuing officer present
        if (tfIssuingOff.isPresent()) {
        	sqlQueryDriver = initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle = initialCondition(sqlQueryVehicle, initial);
    		initial = false;
        	
        	sqlQueryDriver += " ISSUEINGOFFICERIDM=" + tfIssuingOff.get();
        	sqlQueryVehicle += " ISSUEINGOFFICERIDM=" + tfIssuingOff.get();
        }
        
        // Date present
        if (tfStartDate.isPresent() && tfEndDate.isPresent()) { // Start and end date
        	sqlQueryDriver = initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle = initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
        	sqlQueryDriver += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') BETWEEN PARSEDATETIME('%s', 'yyyy-MM-dd') AND PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get(), tfEndDate.get());
        	sqlQueryVehicle += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') BETWEEN PARSEDATETIME('%s', 'yyyy-MM-dd') AND PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get(), tfEndDate.get());
        }
        else if (tfStartDate.isPresent()) { // Only start date
        		sqlQueryDriver = initialCondition(sqlQueryDriver, initial);
        		sqlQueryVehicle = initialCondition(sqlQueryVehicle, initial);
        		initial = false;
     
         	sqlQueryDriver += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') >= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get());
        	sqlQueryVehicle += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') >= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get());
       
        }
        else if (tfEndDate.isPresent()) { // Only end date
        	sqlQueryDriver = initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle = initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
         	sqlQueryDriver += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') <= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfEndDate.get());
        	sqlQueryVehicle += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') <= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfEndDate.get());
       
        }
        
        if (cbReasonDrivVeh.isPresent()) { // Reason given
        	sqlQueryDriver = initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle = initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
        	sqlQueryDriver += String.format("CITATIONREASON= '%s'", cbReasonDrivVeh.get());
        	sqlQueryVehicle += String.format("CITATIONREASON= '%s'", cbReasonDrivVeh.get());

        }
        if (cbPaid.isPresent()) { // Payment status given
        	sqlQueryDriver = initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle = initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
        	sqlQueryDriver += String.format("PAYMENTSTATUS= '%s'", cbPaid.get());
        	sqlQueryVehicle += String.format("PAYMENTSTATUS= '%s'", cbPaid.get());

        }

        
        ResultSet resultDriver = databaseManager.executeQuery(sqlQueryDriver);
        ResultSet resultVehicle = databaseManager.executeQuery(sqlQueryVehicle);
                
        return "Driving Citations\n" + logData(resultDriver, fieldDriver) + "\nVehicle Citations\n" + logData(resultVehicle, fieldVehicle);
    }

    public String generateOutstandingWarrants() {
    	
    	String[] fieldDriver= {"WARRANTID", "DRIVERIDWARRANTM", "REASON", "WARRANTDATE"};
    	String[] fieldVehicle= {"WARRANTIDVM", "VINWARRANTM", "REASON", "WARRANTDATE"};
    	
        String sqlQueryDriver = String.format("SELECT * FROM TCRS.DRIVERWARRANTSMUNICIPLE WHERE OUTSTANDING='Yes'");
        String sqlQueryVehicle = String.format("SELECT * FROM TCRS.VEHICLEWARRANTSMUNICIPLE WHERE OUTSTANDING='Yes'");

        
        ResultSet resultDriver = databaseManager.executeQuery(sqlQueryDriver);
        ResultSet resultVehicle = databaseManager.executeQuery(sqlQueryVehicle);
        
        if (nullCheck(resultDriver) && nullCheck(resultVehicle)) {
            System.out.println("No outstanding warrants found!");
            return null;
        }
        return "Outstanding Driver Warrants\n\n" + logData(resultDriver, fieldDriver) + "\nOutstanding Vehicle Warrants\n\n" + logData(resultVehicle, fieldVehicle);
    }

    // helper methods
    private String logData(ResultSet result, String[] fields) {
    	String report = "";
    	 Map<String, String> columnDisplayNames = new HashMap<>();
    	 	
    	 	columnDisplayNames.put("CITATIONID", "Citation ID");
    	    columnDisplayNames.put("ISSUEINGOFFICERIDM", "Issuing Officer ID");
    	    columnDisplayNames.put("DRIVERIDCITATIONM", "License Number");
    	    columnDisplayNames.put("CITATIONREASON", "Citation Reason");
    	    columnDisplayNames.put("CITATIONDATE", "Citation Date");
    	    columnDisplayNames.put("FINEAMOUNT", "Fine Amount");
    	    columnDisplayNames.put("PAYMENTSTATUS", "Payment Status");
    	    columnDisplayNames.put("REPORTABLE", "Reportable");
    	    columnDisplayNames.put("FIRSTNAME", "First Name");
    	    columnDisplayNames.put("LASTNAME", "Last Name");
    	    columnDisplayNames.put("LICENSENUMBER", "License Number");
    	    columnDisplayNames.put("LICENSEPLATE", "License Plate");
    	    columnDisplayNames.put("LICENSESTATUS", "License Status");
    	    columnDisplayNames.put("DEMERITPOINTS", "Demerit Points");
    	    
    	    // Vehicle related columns
    	    columnDisplayNames.put("VIN", "VIN");
    	    columnDisplayNames.put("MAKE", "Make");
    	    columnDisplayNames.put("MODEL", "Model");
    	    columnDisplayNames.put("CARYEAR", "Car Year");
    	    columnDisplayNames.put("REGISTEREDSTATUS", "Registered Status");
    	    
    	    // Vehicle citation related columns
    	    columnDisplayNames.put("VINCITATIONM", "VIN");
    	    
    	    // Warrant related columns
    	    columnDisplayNames.put("WARRANTID", "Warrant ID");
    	    columnDisplayNames.put("WARRANTIDVM", "Warrant ID");
    	    columnDisplayNames.put("VINWARRANTM", "VIN");
    	    columnDisplayNames.put("DRIVERIDWARRANTM", "License Number");
    	    columnDisplayNames.put("REASON", "Reason");
    	    columnDisplayNames.put("WARRANTDATE", "Warrant Date");
    	    columnDisplayNames.put("OUTSTANDING", "Outstanding");
    	    
    	    
        try {
            while (result.next()) {
                for (String field : fields) {
                	
                	String displayName = columnDisplayNames.get(field);
                   
                    report += (displayName + ": " + result.getString(field)) + "\n";
                }
                report = report + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return report;
    }

    private boolean nullCheck(ResultSet result) {
        try {
            if (!result.isBeforeFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
    
    private String initialCondition(String sqlQuery, Boolean initial) {
    	if (initial) {
        	sqlQuery += " WHERE ";
    	}
    	else {
    		sqlQuery += " AND ";

    	}
    	return sqlQuery;
    }
}
