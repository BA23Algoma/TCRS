package databaseManagement;

import java.io.IOException;
import java.sql.*;

public class DatabaseManager {
	
    private static final String URL = "jdbc:h2:./src/main/resources/tcrs_db"; // Database URL
    private static final String USER = "tcrs"; // Database user name
    private static final String PASSWORD = ""; // Database password
    public Connection connection;
    
    // Initialize connection object
    public DatabaseManager() {
    	
    	this.connection = null;

    }
    
 public void connectToDatabase() throws IOException {
    	
      	try {
            // Load the H2 JDBC driver
            Class.forName("org.h2.Driver");

            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Use the connection to perform database operations
            // Check if the connection is successful
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to connect to the database!");
            }
                        
            // Check if the schema exists
            ResultSet rs = connection.getMetaData().getSchemas();
            boolean schemaExists = false;
            
            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase("TCRS")) {
                    schemaExists = true;
                    break;
                }
            }
            
            if (!schemaExists) {
                // Schema does not exist, load and execute script
                loadDatabase(connection);
                System.out.println("Schema created successfully.");
            } else {
                System.out.println("Schema already exists.");
            }
            
            } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    // Close connection to database
    public void disconnectFromDatabase() {
    	try {         
    		
    		//Finally close connection and all resources associated with connection
    		if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database!");
            }
        } catch (SQLException e) {
        	handleSQLException(e);
        }
    }
    
    // Used for searching the database with SELECT command
    public ResultSet executeQuery(String sql) {
    	
    	//Initialize resultSet for executing queries
    	ResultSet resultSet = null;
    	
        // Attempt to query database
        try {
        	
			Statement statement = connection.createStatement();
			
			// Execute a query
			resultSet = statement.executeQuery(sql);	
			
       
        } catch (SQLException e) {
        	handleSQLException(e);
		}
    	
        return resultSet;
    }
    
    
    // Updates the database, INSERT, DELETE, UPDATE commands
    public void executeUpdate(String sql) {
    	
        // Attempt to query database
        try {
        	
        	//Initialize resultSet for executing queries
        	
			Statement statement = connection.createStatement();
			
			// Execute a query
			statement.executeUpdate(sql);	
			
       
        } catch (SQLException e) {
        	handleSQLException(e);
		}
    	    	
    }
    
    // Load database if not already in system
    public void loadDatabase(Connection connection) throws SQLException {
    	
    	String database = "-- H2 2.2.224; \n"
    			+ "SET IGNORECASE 1;              \n"
    			+ ";              \n"
    			+ "CREATE USER IF NOT EXISTS \"TCRS\" SALT '8712ea25282c960b' HASH '90c6bffbf3b80058157ec132aa13d6f74af9e7e526b6cea867f80c1406e7bd0b' ADMIN;        \n"
    			+ "CREATE SCHEMA IF NOT EXISTS \"TCRS\" AUTHORIZATION \"TCRS\";       \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLEINFO\"(\n"
    			+ "    \"VIN\" VARCHAR_IGNORECASE(25) NOT NULL,\n"
    			+ "    \"LICENSEPLATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"MAKE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"MODEL\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"CARYEAR\" INTEGER,\n"
    			+ "    \"REGISTEREDSTATUS\" VARCHAR_IGNORECASE(255)\n"
    			+ ");             \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEINFO\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_8\" PRIMARY KEY(\"VIN\");      \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLEINFO;               \n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLEINFO\" VALUES\n"
    			+ "('927840315QXPOWRUE', 'HIJK567', 'Honda', 'Civic', 2020, 'Valid'),\n"
    			+ "('682539140JFBKVDPC', 'VIXY678', 'Toyota', 'Corolla', 2019, 'Stolen'),\n"
    			+ "('345217896LHTOZYXW', 'PFRS234', 'Ford', 'Fusion', 2018, 'Expired'),\n"
    			+ "('859724613GIJXQKBF', 'NOPQ234', 'Chevrolet', 'Impala', 2017, 'Valid'),\n"
    			+ "('416893275YKTVNLEJ', 'WXYZ123', 'Subaru', 'Outback', 2016, 'Valid'),\n"
    			+ "('723961485EZSGRUMW', 'VAXY789', 'Hyundai', 'Elantra', 2015, 'Valid'),\n"
    			+ "('598136247QXPJOWTF', 'HIJK679', 'Nissan', 'Altima', 2014, 'Valid'),\n"
    			+ "('364852791FIUZLMNX', 'FGHI789', 'Mazda', 'Mazda3', 2013, 'Valid'),\n"
    			+ "('971582346EYQFXOMS', 'JKLM789', 'Volvo', 'S60', 2012, 'Wanted'),\n"
    			+ "('214897563ZMYVLWQK', 'HIJK568', 'Jeep', 'Cherokee', 2011, 'Valid');    \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVERINFO\"(\n"
    			+ "    \"FIRSTNAME\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"LASTNAME\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"LICENSENUMBER\" VARCHAR_IGNORECASE(255) NOT NULL,\n"
    			+ "    \"LICENSEPLATE\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"LICENSESTATUS\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"DEMERITPOINTS\" INTEGER\n"
    			+ ");         \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERINFO\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_4\" PRIMARY KEY(\"LICENSENUMBER\");             \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVERINFO;\n"
    			+ "INSERT INTO \"TCRS\".\"DRIVERINFO\" VALUES\n"
    			+ "('John', 'Smith', 'A12345678912345', 'HIJK567', 'Valid', 2),\n"
    			+ "('Jane', 'Doe', 'B23456789023456', 'VIXY678', 'Valid', 3),\n"
    			+ "('Michael', 'Johnson', 'C34567890134567', 'PFRS234', 'Valid', 1),\n"
    			+ "('Emily', 'Williams', 'D45678901245678', 'NOPQ234', 'Valid', 0),\n"
    			+ "('Christopher', 'Brown', 'E56789012356789', 'WXYZ123', 'Suspended', 5),\n"
    			+ "('Amanda', 'Jones', 'F67890123467890', 'VAXY789', 'Valid', 4),\n"
    			+ "('Sarah', 'Lee', 'G78901234578901', 'HIJK679', 'Valid', 2),\n"
    			+ "('David', 'Rodriguez', 'H89012345689012', 'FGHI789', 'Valid', 3),\n"
    			+ "('Jennifer', 'Martinez', 'I90123456790123', 'JKLM789', 'Valid', 1),\n"
    			+ "('Daniel', 'Nguyen', 'J01234567801234', 'HIJK568', 'Revoked', 0);   \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"OFFICERINFO\"(\n"
    			+ "    \"BADGENUMBER\" VARCHAR_IGNORECASE(10) NOT NULL,\n"
    			+ "    \"LASTNAME\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"FIRSTNAME\" VARCHAR_IGNORECASE(255)\n"
    			+ ");\n"
    			+ "ALTER TABLE \"TCRS\".\"OFFICERINFO\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_3\" PRIMARY KEY(\"BADGENUMBER\");              \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.OFFICERINFO;               \n"
    			+ "INSERT INTO \"TCRS\".\"OFFICERINFO\" VALUES\n"
    			+ "('10001', 'Smith', 'John'),\n"
    			+ "('10002', 'Johnson', 'David'),\n"
    			+ "('10003', 'Williams', 'Chris'),\n"
    			+ "('10004', 'Brown', 'Mike'),\n"
    			+ "('10005', 'Jones', 'Sarah'),\n"
    			+ "('10006', 'Garcia', 'Robert'),\n"
    			+ "('10007', 'Miller', 'Jennifer'),\n"
    			+ "('10008', 'Davis', 'Patricia'),\n"
    			+ "('10009', 'Rodriguez', 'James'),\n"
    			+ "('10010', 'Martinez', 'Linda');   \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\"(\n"
    			+ "    \"CITATIONID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 11) NOT NULL,\n"
    			+ "    \"ISSUEINGOFFICERIDM\" VARCHAR_IGNORECASE(10),\n"
    			+ "    \"VINCITATIONM\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255)\n"
    			+ ");       \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1\" PRIMARY KEY(\"CITATIONID\"); \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLECITATIONSMUNICIPLE; \n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" VALUES\n"
    			+ "(1, '10001', '927840315QXPOWRUE', 'Parking Violation', '2014-06-05', '250', 'Paid'),\n"
    			+ "(2, '10001', '927840315QXPOWRUE', 'Fix-It Ticket', '2002-09-15', '300', 'Unpaid'),\n"
    			+ "(3, '10001', '345217896LHTOZYXW', 'Parking Violation', '2019-06-29', '150', 'Paid'),\n"
    			+ "(4, '10001', '345217896LHTOZYXW', 'Parking Violation', '2008-08-18', '400', 'Paid'),\n"
    			+ "(5, '10002', '416893275YKTVNLEJ', 'Parking Violation', '2013-09-15', '200', 'Unpaid'),\n"
    			+ "(6, '10002', '723961485EZSGRUMW', 'Fix-It Ticket', '2012-09-15', '275', 'Paid'),\n"
    			+ "(7, '10002', '598136247QXPJOWTF', 'Fix-It Ticket', '2017-04-23', '225', 'Paid'),\n"
    			+ "(8, '10002', '364852791FIUZLMNX', 'Fix-It Ticket', '2002-09-15', '200', 'Unpaid'),\n"
    			+ "(9, '10003', '971582346EYQFXOMS', 'Fix-It Ticket', '2015-07-14', '350', 'Paid'),\n"
    			+ "(10, '10003', '214897563ZMYVLWQK', 'Fix-It Ticket', '2000-01-19', '275', 'Paid');         \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLECITATIONSPROV\"(\n"
    			+ "    \"CITATIONID\" INTEGER NOT NULL,\n"
    			+ "    \"ISSUEINGOFFICERIDP\" VARCHAR_IGNORECASE(10),\n"
    			+ "    \"VINCITATIONP\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255)\n"
    			+ ");           \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C\" PRIMARY KEY(\"CITATIONID\");      \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLECITATIONSPROV;      \n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLECITATIONSPROV\" VALUES\n"
    			+ "(1, '10001', '927840315QXPOWRUE', 'Parking Violation', '2014-05-06', '250', 'Paid'),\n"
    			+ "(2, '10001', '927840315QXPOWRUE', 'Fix-it Ticket', '2002-09-15', '300', 'Unpaid'),\n"
    			+ "(3, '10001', '345217896LHTOZYXW', 'Parking Violation', '2019-06-29', '150', 'Paid'),\n"
    			+ "(4, '10001', '345217896LHTOZYXW', 'Parking Violation', '2008-08-18', '400', 'Paid'),\n"
    			+ "(5, '10002', '416893275YKTVNLEJ', 'Parking Violation', '2013-09-15', '200', 'Unpaid'),\n"
    			+ "(6, '10002', '723961485EZSGRUMW', 'Fix-it Ticket', '2012-09-15', '275', 'Paid'),\n"
    			+ "(7, '10002', '598136247QXPJOWTF', 'Fix-it Ticket', '2017-04-23', '225', 'Paid'),\n"
    			+ "(8, '10002', '364852791FIUZLMNX', 'Fix-it Ticket', '2002-09-15', '200', 'Unpaid'),\n"
    			+ "(9, '10003', '971582346EYQFXOMS', 'Fix-it Ticket', '2015-07-14', '350', 'Paid'),\n"
    			+ "(10, '10003', '214897563ZMYVLWQK', 'Fix-it Ticket', '2000-01-19', '275', 'Paid');              \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\"(\n"
    			+ "    \"WARRANTIDVM\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 11) NOT NULL,\n"
    			+ "    \"VINWARRANTM\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\n"
    			+ ");              \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_3A\" PRIMARY KEY(\"WARRANTIDVM\");\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLEWARRANTSMUNICIPLE;  \n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\" VALUES\n"
    			+ "(1, '927840315QXPOWRUE', 'Stolen vehicle', 'YYYY-MM-DD', 'Yes'),\n"
    			+ "(2, '682539140JFBKVDPC', 'Expired registration', '2022-11-14', 'No'),\n"
    			+ "(3, '345217896LHTOZYXW', 'Outstanding fines', '2022-05-29', 'Yes'),\n"
    			+ "(4, '859724613GIJXQKBF', 'Suspected involvement in crime', 'YYYY-MM-DD', 'No'),\n"
    			+ "(5, '416893275YKTVNLEJ', 'Abandoned vehicle', '2013-06-22', 'Yes'),\n"
    			+ "(6, '723961485EZSGRUMW', 'Unpaid parking tickets', '2022-02-18', 'Yes'),\n"
    			+ "(7, '598136247QXPJOWTF', 'Reported stolen', 'YYYY-MM-DD', 'No'),\n"
    			+ "(8, '364852791FIUZLMNX', 'Unauthorized modification', '2023-04-26', 'Yes'),\n"
    			+ "(9, '971582346EYQFXOMS', 'Suspected involvement in hit and run', '2019-10-31', 'Yes'),\n"
    			+ "(10, '214897563ZMYVLWQK', 'Vehicle inspection overdue', 'YYYY-MM-DD', 'No');  \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\"(\n"
    			+ "    \"WARRANTIDVP\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\n"
    			+ "    \"VINWARRANTP\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\n"
    			+ ");   \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_2\" PRIMARY KEY(\"WARRANTIDVP\");      \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLEWARRANTSPROV;       \n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLEWARRANTSPROV\" VALUES\n"
    			+ "(1, '927840315QXPOWRUE', 'Stolen vehicle', 'YYYY-MM-DD', 'Yes'),\n"
    			+ "(2, '682539140JFBKVDPC', 'Expired registration', '2022-11-14', 'No'),\n"
    			+ "(3, '345217896LHTOZYXW', 'Outstanding fines', '2022-05-29', 'Yes'),\n"
    			+ "(4, '859724613GIJXQKBF', 'Suspected involvement in crime', 'YYYY-MM-DD', 'No'),\n"
    			+ "(5, '416893275YKTVNLEJ', 'Abandoned vehicle', '2013-06-22', 'Yes'),\n"
    			+ "(6, '723961485EZSGRUMW', 'Unpaid parking tickets', '2022-02-18', 'Yes'),\n"
    			+ "(7, '598136247QXPJOWTF', 'Reported stolen', 'YYYY-MM-DD', 'No'),\n"
    			+ "(8, '364852791FIUZLMNX', 'Unauthorized modification', '2023-04-26', 'Yes'),\n"
    			+ "(9, '971582346EYQFXOMS', 'Suspected involvement in hit and run', '2019-10-31', 'Yes'),\n"
    			+ "(10, '214897563ZMYVLWQK', 'Vehicle inspection overdue', 'YYYY-MM-DD', 'No');       \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\"(\n"
    			+ "    \"WARRANTID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 11) NOT NULL,\n"
    			+ "    \"DRIVERIDWARRANTM\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\n"
    			+ ");            \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_15\" PRIMARY KEY(\"WARRANTID\");   \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVERWARRANTSMUNICIPLE;   \n"
    			+ "INSERT INTO \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\" VALUES\n"
    			+ "(1, 'A12345678912345', 'Speeding', '2023-05-14', 'Yes'),\n"
    			+ "(2, 'B23456789023456', 'Reckless driving', 'YYYY-MM-DD', 'Yes'),\n"
    			+ "(3, 'C34567890134567', 'Driving without license', '2021-09-22', 'No'),\n"
    			+ "(4, 'D45678901245678', 'DUI', 'YYYY-MM-DD', 'Yes'),\n"
    			+ "(5, 'E56789012356789', 'Running red light', '2023-11-11', 'Yes'),\n"
    			+ "(6, 'F67890123467890', 'Driving with suspended license', '2024-03-29', 'No'),\n"
    			+ "(7, 'G78901234578901', 'Failure to yield', '2022-08-18', 'Yes'),\n"
    			+ "(8, 'H89012345689012', 'Improper lane change', 'YYYY-MM-DD', 'No'),\n"
    			+ "(9, 'I90123456790123', 'Hit and run', 'YYYY-MM-DD', 'Yes'),\n"
    			+ "(10, 'J01234567801234', 'Driving without insurance', '2022-10-30', 'Yes');           \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVERWARRANTSPROV\"(\n"
    			+ "    \"WARRANTID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\n"
    			+ "    \"DRIVERIDWARRANTP\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\n"
    			+ "); \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_11\" PRIMARY KEY(\"WARRANTID\");        \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVERWARRANTSPROV;        \n"
    			+ "INSERT INTO \"TCRS\".\"DRIVERWARRANTSPROV\" VALUES\n"
    			+ "(1, 'A12345678912345', 'Speeding', '2023-05-14', 'Yes'),\n"
    			+ "(2, 'B23456789023456', 'Reckless driving', 'YYYY-DD-08', 'Yes'),\n"
    			+ "(3, 'C34567890134567', 'Driving without license', '2021-09-22', 'No'),\n"
    			+ "(4, 'D45678901245678', 'DUI', 'YYYY-DD-03', 'Yes'),\n"
    			+ "(5, 'E56789012356789', 'Running red light', '2023-11-11', 'Yes'),\n"
    			+ "(6, 'F67890123467890', 'Driving with suspended license', '2024-03-29', 'No'),\n"
    			+ "(7, 'G78901234578901', 'Failure to yield', '2022-08-18', 'Yes'),\n"
    			+ "(8, 'H89012345689012', 'Improper lane change', 'YYYY-DD-05', 'No'),\n"
    			+ "(9, 'I90123456790123', 'Hit and run', 'YYYY-DD-09', 'Yes'),\n"
    			+ "(10, 'J01234567801234', 'Driving without insurance', '2022-10-30', 'Yes');\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\"(\n"
    			+ "    \"CITATIONID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 13) NOT NULL,\n"
    			+ "    \"ISSUEINGOFFICERIDM\" VARCHAR_IGNORECASE(10),\n"
    			+ "    \"DRIVERIDCITATIONM\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"REPORTABLE\" VARCHAR_IGNORECASE(255)\n"
    			+ ");        \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_7\" PRIMARY KEY(\"CITATIONID\"); \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVINGCITATIONSMUNICIPLE; \n"
    			+ "INSERT INTO \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" VALUES\n"
    			+ "(1, '10001', 'A12345678912345', 'Speeding', '2014-05-06', '150', 'Paid', 'Yes'),\n"
    			+ "(2, '10001', 'B23456789023456', 'DUI', '2002-09-15', '400', 'Unpaid', 'Yes'),\n"
    			+ "(3, '10002', 'C34567890134567', 'Moving Vehicle Code Violation', '2019-06-29', '250', 'Paid', 'TBD'),\n"
    			+ "(4, '10002', 'A12345678912345', 'Speeding', '2008-08-18', '200', 'Paid', 'Yes'),\n"
    			+ "(5, '10006', 'E56789012356789', 'Reckless Driving', '2013-09-15', '300', 'Unpaid', 'Yes'),\n"
    			+ "(6, '10006', 'J01234567801234', 'Speeding', '2012-09-15', '200', 'Paid', 'Yes'),\n"
    			+ "(7, '10002', 'G78901234578901', 'DUI', '2017-04-23', '400', 'Unpaid', 'Yes'),\n"
    			+ "(8, '10007', 'H89012345689012', 'Speeding', '2002-09-15', '200', 'Paid', 'Yes'),\n"
    			+ "(9, '10008', 'B23456789023456', 'Reckless Driving', '2015-07-14', '300', 'Paid', 'Yes'),\n"
    			+ "(10, '10001', 'J01234567801234', 'Moving Vehicle Code Violation', '2000-01-19', '250', 'Unpaid', 'TBD');       \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\"(\n"
    			+ "    \"CITATIONID\" INTEGER NOT NULL,\n"
    			+ "    \"ISSUEINGOFFICERIDP\" VARCHAR_IGNORECASE(10),\n"
    			+ "    \"DRIVERIDCITATIONP\" VARCHAR_IGNORECASE(25),\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255)\n"
    			+ ");      \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_88\" PRIMARY KEY(\"CITATIONID\");     \n"
    			+ "-- 8 +/- SELECT COUNT(*) FROM TCRS.DRIVINGCITATIONSPROV;       \n"
    			+ "INSERT INTO \"TCRS\".\"DRIVINGCITATIONSPROV\" VALUES\n"
    			+ "(1, '10001', 'A12345678912345', 'Speeding', '2014-05-06', '150', 'Paid'),\n"
    			+ "(2, '10001', 'B23456789023456', 'DUI', '2002-09-15', '400', 'Unpaid'),\n"
    			+ "(4, '10002', 'A12345678912345', 'Speeding', '2008-08-18', '200', 'Paid'),\n"
    			+ "(5, '10006', 'E56789012356789', 'Reckless Driving', '2013-09-15', '300', 'Unpaid'),\n"
    			+ "(6, '10006', 'J01234567801234', 'Speeding', '2012-09-15', '200', 'Paid'),\n"
    			+ "(7, '10002', 'G78901234578901', 'DUI', '2017-04-23', '400', 'Unpaid'),\n"
    			+ "(8, '10007', 'H89012345689012', 'Speeding', '2002-09-15', '200', 'Paid'),\n"
    			+ "(9, '10008', 'B23456789023456', 'Reckless Driving', '2015-07-14', '300', 'Paid');   \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"TRAFFICSCHOOL\"(\n"
    			+ "    \"CITATIONIDTS\" INTEGER NOT NULL,\n"
    			+ "    \"SESSION1DATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"SESSION2DATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"SESSION3DATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"SESSION4DATE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"SESSION1ATTENDANCE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"SESSION2ATTENDANCE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"SESSION3ATTENDANCE\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"SESSION4ATTENDANCE\" VARCHAR_IGNORECASE(255)\n"
    			+ ");     \n"
    			+ "ALTER TABLE \"TCRS\".\"TRAFFICSCHOOL\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_E\" PRIMARY KEY(\"CITATIONIDTS\");           \n"
    			+ "-- 2 +/- SELECT COUNT(*) FROM TCRS.TRAFFICSCHOOL;              \n"
    			+ "INSERT INTO \"TCRS\".\"TRAFFICSCHOOL\" VALUES\n"
    			+ "(3, '2023-08-02', '2023-08-10', '2023-08-10', '2023-08-15', 'Yes', 'Yes', 'Yes', 'Yes'),\n"
    			+ "(10, '2023-09-02', '2023-09-02', '2023-10-02', '2023-10-02', 'No', 'No', 'Yes', 'Yes');     \n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"ACCOUNTS\"(\n"
    			+ "    \"ACCOUNTID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\n"
    			+ "    \"USERNAME\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"PASSWORDACC\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"FIRSTNAME\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"LASTNAME\" VARCHAR_IGNORECASE(255),\n"
    			+ "    \"AGENCY\" VARCHAR_IGNORECASE(255)\n"
    			+ ");            \n"
    			+ "ALTER TABLE \"TCRS\".\"ACCOUNTS\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_A\" PRIMARY KEY(\"ACCOUNTID\");   \n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.ACCOUNTS;  \n"
    			+ "INSERT INTO \"TCRS\".\"ACCOUNTS\" VALUES\n"
    			+ "(1, 'badewale', '239592830', 'Banki', 'Adewale', 'Admin'),\n"
    			+ "(2, 'gamjoun', '239349660', 'Ghizlane', 'Amjoun', 'Admin'),\n"
    			+ "(3, 'brajaie', '239394290', 'Ben', 'Rajaie', 'Admin'),\n"
    			+ "(4, 'kuwaechi', '199677980', 'Kennedy', 'Uwaechi', 'Admin'),\n"
    			+ "(5, 'sjohn', '10002', 'Smith', 'John', 'Provincial'),\n"
    			+ "(6, 'jdavid', '10003', 'Johnson', 'David', 'Local'),\n"
    			+ "(7, 'wchris', '10004', 'Williams', 'Chris', 'Provincial'),\n"
    			+ "(8, 'bmike', '10005', 'Brown', 'Mike', 'Local'),\n"
    			+ "(9, 'jsarah', '10006', 'Jones', 'Sarah', 'Provincial'),\n"
    			+ "(10, 'grobert', '10007', 'Garcia', 'Robert', 'Local');          \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_15D\" FOREIGN KEY(\"DRIVERIDWARRANTM\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;   \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_117\" FOREIGN KEY(\"WARRANTID\") REFERENCES \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\"(\"WARRANTID\") NOCHECK;      \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_711\" FOREIGN KEY(\"DRIVERIDCITATIONM\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_71\" FOREIGN KEY(\"ISSUEINGOFFICERIDM\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK; \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_885A\" FOREIGN KEY(\"ISSUEINGOFFICERIDP\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK;    \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C205\" FOREIGN KEY(\"VINCITATIONP\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;  \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_3A9\" FOREIGN KEY(\"VINWARRANTM\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C2\" FOREIGN KEY(\"CITATIONID\") REFERENCES \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\"(\"CITATIONID\") NOCHECK; \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_2B\" FOREIGN KEY(\"WARRANTIDVP\") REFERENCES \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\"(\"WARRANTIDVM\") NOCHECK; \n"
    			+ "ALTER TABLE \"TCRS\".\"TRAFFICSCHOOL\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_E7\" FOREIGN KEY(\"CITATIONIDTS\") REFERENCES \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\"(\"CITATIONID\") NOCHECK;      \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C20\" FOREIGN KEY(\"ISSUEINGOFFICERIDP\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK;     \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_885\" FOREIGN KEY(\"CITATIONID\") REFERENCES \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\"(\"CITATIONID\") NOCHECK;\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_2BC\" FOREIGN KEY(\"VINWARRANTP\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;     \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_885AD\" FOREIGN KEY(\"DRIVERIDCITATIONP\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;   \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1B\" FOREIGN KEY(\"ISSUEINGOFFICERIDM\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK; \n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1179\" FOREIGN KEY(\"DRIVERIDWARRANTP\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;       \n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1B4\" FOREIGN KEY(\"VINCITATIONM\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;              \n"
    			+ "";
 
    	    // Define your SQL script as a string
    	    
    	    try (Statement statement = connection.createStatement()) {
    	        // Execute the SQL script
    	        statement.execute(database);
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        throw e; // Re-throw the exception to handle it at a higher level if needed
    	    }
    	}
    

    
    public void handleSQLException(SQLException e) {
    	System.err.println("SQL Exception occurred:");
    	e.printStackTrace();
    }
    
    
}