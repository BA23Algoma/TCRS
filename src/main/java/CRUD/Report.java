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

    public void generateVehicleReport(String[] tables, String[] fields, String vin) {
        String sqlQuery = String.format("SELECT * FROM %s WHERE VIN='%s'", tables[0], vin);
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("Vehicle not found in the system!");
            return;
        }
        logData(result, fields);
    }

    public void generateDriverReport(String[] tables, String[] fields, String license) {
        String sqlQuery = String.format("SELECT * FROM %s WHERE LICENSENUMBER='%s'", tables[0], license);
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("Driver not found in the system!");
            return;
        }
        logData(result, fields);
    }

    public void generateDrivingRecord(String[] tables, String[] fields, String license,
            String startDate, String endDate) {
        String sqlQuery = String.format("SELECT * FROM %s WHERE LICENSENUMBER='%s' AND DATE BETWEEN '%s' AND '%s'",
                tables[0], license, startDate, endDate);
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("No driving records found for the given period!");
            return;
        }
        logData(result, fields);
    }

    public void generateCitationSummary(String[] tables, String[] fields,
            Optional<String> licenseNumber,
            Optional<String> licensePlate,
            Optional<Integer> officeBadge,
            Optional<String> startDate,
            Optional<String> endDate,
            Optional<String> reason,
            Optional<String> paid) {
        String sqlQuery = "SELECT * FROM " + tables[0] + " WHERE ";
        if (licenseNumber.isPresent()) {
            sqlQuery += "LICENSENUMBER='" + licenseNumber.get();
        }

        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 5);
        ResultSet result = databaseManager.executeQuery(sqlQuery);
    }

    public void generateOutstandingWarrants(String[] tables, String[] fields) {
        String sqlQuery = String.format("SELECT * FROM %s WHERE OUTSTANDING=TRUE", tables[0]);
        ResultSet result = databaseManager.executeQuery(sqlQuery);
        if (nullCheck(result)) {
            System.out.println("No outstanding warrants found!");
            return;
        }
        logData(result, fields);
    }

    // helper methods
    private void logData(ResultSet result, String[] fields) {
        try {
            while (result.next()) {
                for (String field : fields) {
                    System.out.println(field + ": " + result.getString(field));
                }
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
}
