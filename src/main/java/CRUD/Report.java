package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    			"CITATIONDATE", "FINEAMOUNT", "PAYMENTSTATUS", "REPORTABLE"};
        
        String sqlQuery = String.format("SELECT * FROM TCRS.DRIVINGCITATIONSMUNICIPLE WHERE DRIVERIDCITATIONM='%s' AND REPORTABLE='Yes' "
        		+ "AND PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') BETWEEN PARSEDATETIME('%s', 'yyyy-MM-dd') AND PARSEDATETIME('%s', 'yyyy-MM-dd')",
        		license, startDate, endDate);
        
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("No driving records found for the given period!");
            return null;
        }
        return generateDriverReport(license) + "\n" + logData(result, fields);
    }

    public String generateCitationSummary(
            Optional<String> tfIssuingOff,
            Optional<String> tfStartDate,
            Optional<Integer> tfEndDate,
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
        	sqlQueryDriver += "ISSUEINGOFFICERIDM='" + tfIssuingOff.get();
        	sqlQueryVehicle += "ISSUEINGOFFICERIDM='" + tfIssuingOff.get();
        	initial = false;
        }
        
        // Date present
        if (tfStartDate.isPresent() && tfEndDate.isPresent()) { // Start and end date
        	sqlQueryDriver += initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle += initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
        	sqlQueryDriver += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') BETWEEN PARSEDATETIME('%s', 'yyyy-MM-dd') AND PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get(), tfEndDate.get());
        	sqlQueryVehicle += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') BETWEEN PARSEDATETIME('%s', 'yyyy-MM-dd') AND PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get(), tfEndDate.get());
        }
        else if (tfStartDate.isPresent()) { // Only start date
        		sqlQueryDriver += initialCondition(sqlQueryDriver, initial);
        		sqlQueryVehicle += initialCondition(sqlQueryVehicle, initial);
        		initial = false;
     
         	sqlQueryDriver += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') >= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get());
        	sqlQueryVehicle += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') >= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfStartDate.get());
       
        }
        else if (tfEndDate.isPresent()) { // Only end date
        	sqlQueryDriver += initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle += initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
         	sqlQueryDriver += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') <= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfEndDate.get());
        	sqlQueryVehicle += String.format(" PARSEDATETIME(CITATIONDATE, 'yyyy-MM-dd') <= PARSEDATETIME('%s', 'yyyy-MM-dd')", tfEndDate.get());
       
        }
        
        if (cbReasonDrivVeh.isPresent()) { // Reason given
        	sqlQueryDriver += initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle += initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
        	sqlQueryDriver += "CITATIONREASON='" + cbReasonDrivVeh.get();
        	sqlQueryVehicle += "CITATIONREASON='" + cbReasonDrivVeh.get();

        }
        if (cbPaid.isPresent()) { // Payment status given
        	sqlQueryDriver += initialCondition(sqlQueryDriver, initial);
    		sqlQueryVehicle += initialCondition(sqlQueryVehicle, initial);
    		initial = false;
    		
        	sqlQueryDriver += "PAYMENTSTATUS='" + cbPaid.get();
        	sqlQueryVehicle += "PAYMENTSTATUS='" + cbPaid.get();

        }

        
        ResultSet resultDriver = databaseManager.executeQuery(sqlQueryDriver);
        ResultSet resultVehicle = databaseManager.executeQuery(sqlQueryVehicle);
                
        return "Driving Citations\n" + logData(resultDriver, fieldDriver) + "\nVehicle Citations\n" + logData(resultVehicle, fieldVehicle);
    }

    public String generateOutstandingWarrants() {
    	
    	String[] fieldDriver= {"WARRANTID", "DRIVERIDWARRANTM", "REASON", "WARRANTDATE", "OUTSTANDING"};
    	String[] fieldVehicle= {"WARRANTIDVM", "VINWARRANTM", "REASON", "WARRANTDATE", "OUTSTANDING"};
    	
        String sqlQueryDriver = String.format("SELECT * FROM TCRS.DRIVERWARRANTSMUNICIPLE WHERE OUTSTANDING='Yes'");
        String sqlQueryVehicle = String.format("SELECT * FROM TCRS.VEHICLEWARRANTSMUNICIPLE WHERE OUTSTANDING='Yes'");

        
        ResultSet resultDriver = databaseManager.executeQuery(sqlQueryDriver);
        ResultSet resultVehicle = databaseManager.executeQuery(sqlQueryVehicle);
        
        if (nullCheck(resultDriver) && nullCheck(resultVehicle)) {
            System.out.println("No outstanding warrants found!");
            return null;
        }
        return "Outstanding Driving Warrant\n" + logData(resultDriver, fieldDriver) + "\nOutstanding Vehicle Warrants\n" + logData(resultVehicle, fieldVehicle);
    }

    // helper methods
    private String logData(ResultSet result, String[] fields) {
    	String report = "";
        try {
            while (result.next()) {
                for (String field : fields) {
                	report = report + (field + ": " + result.getString(field)) + " ";
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
