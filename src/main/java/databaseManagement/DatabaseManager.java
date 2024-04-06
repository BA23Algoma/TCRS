package databaseManagement;

import java.io.IOException;
import java.sql.*;

public class DatabaseManager {
	
    private static final String URL = "jdbc:h2:./src/main/Resources/trcs_db"; // Database URL
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
    
 // Updates the database, INSERT, DELETE, UPDATE commands
    public int executeInsertReturnId(String sql) {
    	
		int generatedId = -1; // Default value if ID retrieval fails

    	// Attempt to insert into database and retrieve generated ID
	    try (Statement statement = connection.createStatement()) {
	        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

	        if (affectedRows == 0) {
	            throw new SQLException("Creating account failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                generatedId = generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creating account failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	        handleSQLException(e);
	    }
	        
	        if (generatedId != -1) {
	            System.out.println("Entry added to the database! Generated ID: " + generatedId);
	        }
	    
	        return generatedId;
    	    	
    }
    
    // Load database if not already in system
    public void loadDatabase(Connection connection) throws SQLException {
    	
    	String database = "-- H2 2.2.224; \r\n"
    			+ "SET IGNORECASE 1;              \r\n"
    			+ ";              \r\n"
    			+ "CREATE USER IF NOT EXISTS \"TCRS\" SALT '8712ea25282c960b' HASH '90c6bffbf3b80058157ec132aa13d6f74af9e7e526b6cea867f80c1406e7bd0b' ADMIN;        \r\n"
    			+ "CREATE SCHEMA IF NOT EXISTS \"TCRS\" AUTHORIZATION \"TCRS\";       \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLEINFO\"(\r\n"
    			+ "    \"VIN\" VARCHAR_IGNORECASE(25) NOT NULL,\r\n"
    			+ "    \"LICENSEPLATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"MAKE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"MODEL\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"CARYEAR\" INTEGER,\r\n"
    			+ "    \"REGISTEREDSTATUS\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");             \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEINFO\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_8\" PRIMARY KEY(\"VIN\");      \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLEINFO;               \r\n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLEINFO\" VALUES\r\n"
    			+ "('927840315QXPOWRUE', 'HIJK567', 'Honda', 'Civic', 2020, 'Valid'),\r\n"
    			+ "('682539140JFBKVDPC', 'VIXY678', 'Toyota', 'Corolla', 2019, 'Stolen'),\r\n"
    			+ "('345217896LHTOZYXW', 'PFRS234', 'Ford', 'Fusion', 2018, 'Expired'),\r\n"
    			+ "('859724613GIJXQKBF', 'NOPQ234', 'Chevrolet', 'Impala', 2017, 'Valid'),\r\n"
    			+ "('416893275YKTVNLEJ', 'WXYZ123', 'Subaru', 'Outback', 2016, 'Valid'),\r\n"
    			+ "('723961485EZSGRUMW', 'VAXY789', 'Hyundai', 'Elantra', 2015, 'Valid'),\r\n"
    			+ "('598136247QXPJOWTF', 'HIJK679', 'Nissan', 'Altima', 2014, 'Valid'),\r\n"
    			+ "('364852791FIUZLMNX', 'FGHI789', 'Mazda', 'Mazda3', 2013, 'Valid'),\r\n"
    			+ "('971582346EYQFXOMS', 'JKLM789', 'Volvo', 'S60', 2012, 'Wanted'),\r\n"
    			+ "('214897563ZMYVLWQK', 'HIJK568', 'Jeep', 'Cherokee', 2011, 'Valid');    \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVERINFO\"(\r\n"
    			+ "    \"FIRSTNAME\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"LASTNAME\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"LICENSENUMBER\" VARCHAR_IGNORECASE(255) NOT NULL,\r\n"
    			+ "    \"LICENSEPLATE\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"LICENSESTATUS\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"DEMERITPOINTS\" INTEGER\r\n"
    			+ ");         \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERINFO\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_4\" PRIMARY KEY(\"LICENSENUMBER\");             \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVERINFO;\r\n"
    			+ "INSERT INTO \"TCRS\".\"DRIVERINFO\" VALUES\r\n"
    			+ "('John', 'Smith', 'A12345678912345', 'HIJK567', 'Valid', 2),\r\n"
    			+ "('Jane', 'Doe', 'B23456789023456', 'VIXY678', 'Valid', 3),\r\n"
    			+ "('Michael', 'Johnson', 'C34567890134567', 'PFRS234', 'Valid', 1),\r\n"
    			+ "('Emily', 'Williams', 'D45678901245678', 'NOPQ234', 'Valid', 0),\r\n"
    			+ "('Christopher', 'Brown', 'E56789012356789', 'WXYZ123', 'Suspended', 5),\r\n"
    			+ "('Amanda', 'Jones', 'F67890123467890', 'VAXY789', 'Valid', 4),\r\n"
    			+ "('Sarah', 'Lee', 'G78901234578901', 'HIJK679', 'Valid', 2),\r\n"
    			+ "('David', 'Rodriguez', 'H89012345689012', 'FGHI789', 'Valid', 3),\r\n"
    			+ "('Jennifer', 'Martinez', 'I90123456790123', 'JKLM789', 'Valid', 1),\r\n"
    			+ "('Daniel', 'Nguyen', 'J01234567801234', 'HIJK568', 'Revoked', 0);   \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"OFFICERINFO\"(\r\n"
    			+ "    \"BADGENUMBER\" VARCHAR_IGNORECASE(10) NOT NULL,\r\n"
    			+ "    \"LASTNAME\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"FIRSTNAME\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");\r\n"
    			+ "ALTER TABLE \"TCRS\".\"OFFICERINFO\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_3\" PRIMARY KEY(\"BADGENUMBER\");              \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.OFFICERINFO;               \r\n"
    			+ "INSERT INTO \"TCRS\".\"OFFICERINFO\" VALUES\r\n"
    			+ "('10001', 'Smith', 'John'),\r\n"
    			+ "('10002', 'Johnson', 'David'),\r\n"
    			+ "('10003', 'Williams', 'Chris'),\r\n"
    			+ "('10004', 'Brown', 'Mike'),\r\n"
    			+ "('10005', 'Jones', 'Sarah'),\r\n"
    			+ "('10006', 'Garcia', 'Robert'),\r\n"
    			+ "('10007', 'Miller', 'Jennifer'),\r\n"
    			+ "('10008', 'Davis', 'Patricia'),\r\n"
    			+ "('10009', 'Rodriguez', 'James'),\r\n"
    			+ "('10010', 'Martinez', 'Linda');   \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\"(\r\n"
    			+ "    \"CITATIONID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\r\n"
    			+ "    \"ISSUINGOFFICERIDM\" VARCHAR_IGNORECASE(10),\r\n"
    			+ "    \"VINCITATIONM\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");       \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1\" PRIMARY KEY(\"CITATIONID\"); \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLECITATIONSMUNICIPLE; \r\n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" (ISSUINGOFFICERIDM, VINCITATIONM, CITATIONREASON, CITATIONDATE, FINEAMOUNT, PAYMENTSTATUS) VALUES\r\n"
    			+ "('10001', '927840315QXPOWRUE', 'Parking Violation', '2014-06-05', '250', 'Paid'),\r\n"
    			+ "('10001', '927840315QXPOWRUE', 'Fix-It Ticket', '2002-09-15', '300', 'Unpaid'),\r\n"
    			+ "('10001', '345217896LHTOZYXW', 'Parking Violation', '2019-06-29', '150', 'Paid'),\r\n"
    			+ "('10001', '345217896LHTOZYXW', 'Parking Violation', '2008-08-18', '400', 'Paid'),\r\n"
    			+ "('10002', '416893275YKTVNLEJ', 'Parking Violation', '2013-09-15', '200', 'Unpaid'),\r\n"
    			+ "('10002', '723961485EZSGRUMW', 'Fix-It Ticket', '2012-09-15', '275', 'Paid'),\r\n"
    			+ "('10002', '598136247QXPJOWTF', 'Fix-It Ticket', '2017-04-23', '225', 'Paid'),\r\n"
    			+ "('10002', '364852791FIUZLMNX', 'Fix-It Ticket', '2002-09-15', '200', 'Unpaid'),\r\n"
    			+ "('10003', '971582346EYQFXOMS', 'Fix-It Ticket', '2015-07-14', '350', 'Paid'),\r\n"
    			+ "('10003', '214897563ZMYVLWQK', 'Fix-It Ticket', '2000-01-19', '275', 'Paid');         \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLECITATIONSPROV\"(\r\n"
    			+ "    \"CITATIONID\" INTEGER NOT NULL,\r\n"
    			+ "    \"ISSUINGOFFICERIDP\" VARCHAR_IGNORECASE(10),\r\n"
    			+ "    \"VINCITATIONP\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");           \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C\" PRIMARY KEY(\"CITATIONID\");      \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLECITATIONSPROV;      \r\n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLECITATIONSPROV\" (CITATIONID, ISSUINGOFFICERIDP, vinCitationP, citationreason, citationDate, fineAmount, paymentStatus) VALUES\r\n"
    			+ "(1, '10001', '927840315QXPOWRUE', 'Parking Violation', '2014-05-06', '250', 'Paid'),\r\n"
    			+ "(2, '10001', '927840315QXPOWRUE', 'Fix-it Ticket', '2002-09-15', '300', 'Unpaid'),\r\n"
    			+ "(3, '10001', '345217896LHTOZYXW', 'Parking Violation', '2019-06-29', '150', 'Paid'),\r\n"
    			+ "(4, '10001', '345217896LHTOZYXW', 'Parking Violation', '2008-08-18', '400', 'Paid'),\r\n"
    			+ "(5, '10002', '416893275YKTVNLEJ', 'Parking Violation', '2013-09-15', '200', 'Unpaid'),\r\n"
    			+ "(6, '10002', '723961485EZSGRUMW', 'Fix-it Ticket', '2012-09-15', '275', 'Paid'),\r\n"
    			+ "(7, '10002', '598136247QXPJOWTF', 'Fix-it Ticket', '2017-04-23', '225', 'Paid'),\r\n"
    			+ "(8, '10002', '364852791FIUZLMNX', 'Fix-it Ticket', '2002-09-15', '200', 'Unpaid'),\r\n"
    			+ "(9, '10003', '971582346EYQFXOMS', 'Fix-it Ticket', '2015-07-14', '350', 'Paid'),\r\n"
    			+ "(10, '10003', '214897563ZMYVLWQK', 'Fix-it Ticket', '2000-01-19', '275', 'Paid');              \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\"(\r\n"
    			+ "    \"WARRANTIDV\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\r\n"
    			+ "    \"VINWARRANTM\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");              \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_3A\" PRIMARY KEY(\"WARRANTIDV\");\r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLEWARRANTSMUNICIPLE;  \r\n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\" ( vinWarrantM, reason, warrantDate, outstanding) VALUES\r\n"
    			+ "('927840315QXPOWRUE', 'Stolen vehicle', '2023-08-03', 'Yes'),\r\n"
    			+ "('682539140JFBKVDPC', 'Expired registration', '2022-11-14', 'No'),\r\n"
    			+ "('345217896LHTOZYXW', 'Outstanding fines', '2022-05-29', 'Yes'),\r\n"
    			+ "('859724613GIJXQKBF', 'Suspected involvement in crime', '2023-09-05', 'No'),\r\n"
    			+ "('416893275YKTVNLEJ', 'Abandoned vehicle', '2013-06-22', 'Yes'),\r\n"
    			+ "('723961485EZSGRUMW', 'Unpaid parking tickets', '2022-02-18', 'Yes'),\r\n"
    			+ "('598136247QXPJOWTF', 'Reported stolen', '2016-12-09', 'No'),\r\n"
    			+ "('364852791FIUZLMNX', 'Unauthorized modification', '2023-04-26', 'Yes'),\r\n"
    			+ "('971582346EYQFXOMS', 'Suspected involvement in hit and run', '2019-10-31', 'Yes'),\r\n"
    			+ "('214897563ZMYVLWQK', 'Vehicle inspection overdue', '2022-07-11', 'No');  \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\"(\r\n"
    			+ "    \"WARRANTIDV\" INTEGER NOT NULL,\r\n"
    			+ "    \"VINWARRANTP\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");   \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_2\" PRIMARY KEY(\"WARRANTIDV\");      \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.VEHICLEWARRANTSPROV;       \r\n"
    			+ "INSERT INTO \"TCRS\".\"VEHICLEWARRANTSPROV\" ( WARRANTIDV, VINWARRANTP, reason, WARRANTDATE, outstanding) VALUES\r\n"
    			+ "(1, '927840315QXPOWRUE', 'Stolen vehicle', '2023-08-03', 'Yes'),\r\n"
    			+ "(2, '682539140JFBKVDPC', 'Expired registration', '2022-11-14', 'No'),\r\n"
    			+ "(3, '345217896LHTOZYXW', 'Outstanding fines', '2022-05-29', 'Yes'),\r\n"
    			+ "(4, '859724613GIJXQKBF', 'Suspected involvement in crime', '2023-09-05', 'No'),\r\n"
    			+ "(5, '416893275YKTVNLEJ', 'Abandoned vehicle', '2013-06-22', 'Yes'),\r\n"
    			+ "(6, '723961485EZSGRUMW', 'Unpaid parking tickets', '2022-02-18', 'Yes'),\r\n"
    			+ "(7, '598136247QXPJOWTF', 'Reported stolen', '2016-12-09', 'No'),\r\n"
    			+ "(8, '364852791FIUZLMNX', 'Unauthorized modification', '2023-04-26', 'Yes'),\r\n"
    			+ "(9, '971582346EYQFXOMS', 'Suspected involvement in hit and run', '2019-10-31', 'Yes'),\r\n"
    			+ "(10, '214897563ZMYVLWQK', 'Vehicle inspection overdue', '2022-07-11', 'No');       \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\"(\r\n"
    			+ "    \"WARRANTID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\r\n"
    			+ "    \"DRIVERIDWARRANTM\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");            \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_15\" PRIMARY KEY(\"WARRANTID\");   \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVERWARRANTSMUNICIPLE;   \r\n"
    			+ "INSERT INTO \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\" ( driverIDWarrantM, reason, warrantDate, outstanding) VALUES\r\n"
    			+ "('A12345678912345', 'Speeding', '2023-05-14', 'Yes'),\r\n"
    			+ "('B23456789023456', 'Reckless driving', '2022-12-08', 'Yes'),\r\n"
    			+ "('C34567890134567', 'Driving without license', '2021-09-22', 'No'),\r\n"
    			+ "('D45678901245678', 'DUI', '2022-07-03', 'Yes'),\r\n"
    			+ "('E56789012356789', 'Running red light', '2023-11-11', 'Yes'),\r\n"
    			+ "('F67890123467890', 'Driving with suspended license', '2024-03-29', 'No'),\r\n"
    			+ "('G78901234578901', 'Failure to yield', '2022-08-18', 'Yes'),\r\n"
    			+ "('H89012345689012', 'Improper lane change', '2021-06-05', 'No'),\r\n"
    			+ "('I90123456790123', 'Hit and run', '2023-02-09', 'Yes'),\r\n"
    			+ "('J01234567801234', 'Driving without insurance', '2022-10-30', 'Yes');           \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVERWARRANTSPROV\"(\r\n"
    			+ "    \"WARRANTID\" INTEGER NOT NULL,\r\n"
    			+ "    \"DRIVERIDWARRANTP\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"REASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"WARRANTDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"OUTSTANDING\" VARCHAR_IGNORECASE(255)\r\n"
    			+ "); \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_11\" PRIMARY KEY(\"WARRANTID\");        \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVERWARRANTSPROV;        \r\n"
    			+ "INSERT INTO \"TCRS\".\"DRIVERWARRANTSPROV\" ( WARRANTID, driverIDWarrantP, reason, warrantDate, outstanding) VALUES\r\n"
    			+ "(1, 'A12345678912345', 'Speeding', '2023-05-14', 'Yes'),\r\n"
    			+ "(2, 'B23456789023456', 'Reckless driving', '2022-12-08', 'Yes'),\r\n"
    			+ "(3, 'C34567890134567', 'Driving without license', '2021-09-22', 'No'),\r\n"
    			+ "(4, 'D45678901245678', 'DUI', '2022-07-03', 'Yes'),\r\n"
    			+ "(5, 'E56789012356789', 'Running red light', '2023-11-11', 'Yes'),\r\n"
    			+ "(6, 'F67890123467890', 'Driving with suspended license', '2024-03-29', 'No'),\r\n"
    			+ "(7, 'G78901234578901', 'Failure to yield', '2022-08-18', 'Yes'),\r\n"
    			+ "(8, 'H89012345689012', 'Improper lane change', '2021-06-05', 'No'),\r\n"
    			+ "(9, 'I90123456790123', 'Hit and run', '2023-02-09', 'Yes'),\r\n"
    			+ "(10, 'J01234567801234', 'Driving without insurance', '2022-10-30', 'Yes');\r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\"(\r\n"
    			+ "    \"CITATIONID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\r\n"
    			+ "    \"ISSUINGOFFICERIDM\" VARCHAR_IGNORECASE(10),\r\n"
    			+ "    \"DRIVERIDCITATIONM\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"REPORTABLE\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");        \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_7\" PRIMARY KEY(\"CITATIONID\"); \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.DRIVINGCITATIONSMUNICIPLE; \r\n"
    			+ "INSERT INTO \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" ( ISSUINGOFFICERIDM, DRIVERIDCITATIONM, CITATIONREASON, CITATIONDATE, fineAmount, paymentStatus, reportable) VALUES\r\n"
    			+ "('10001', 'A12345678912345', 'Speeding', '2014-05-06', '150', 'Paid', 'Yes'),\r\n"
    			+ "('10001', 'B23456789023456', 'DUI', '2002-09-15', '400', 'Unpaid', 'Yes'),\r\n"
    			+ "('10002', 'C34567890134567', 'DUI', '2019-06-29', '250', 'Paid', 'TBD'),\r\n"
    			+ "('10002', 'A12345678912345', 'Speeding', '2008-08-18', '200', 'Paid', 'Yes'),\r\n"
    			+ "('10006', 'E56789012356789', 'Reckless Driving', '2013-09-15', '300', 'Unpaid', 'Yes'),\r\n"
    			+ "('10006', 'J01234567801234', 'Speeding', '2012-09-15', '200', 'Paid', 'Yes'),\r\n"
    			+ "('10002', 'G78901234578901', 'DUI', '2017-04-23', '400', 'Unpaid', 'Yes'),\r\n"
    			+ "('10007', 'H89012345689012', 'Speeding', '2002-09-15', '200', 'Paid', 'Yes'),\r\n"
    			+ "('10008', 'B23456789023456', 'Reckless Driving', '2015-07-14', '300', 'Paid', 'Yes'),\r\n"
    			+ "('10001', 'J01234567801234', 'Speeding', '2000-01-19', '250', 'Unpaid', 'TBD');       \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\"(\r\n"
    			+ "    \"CITATIONID\" INTEGER NOT NULL,\r\n"
    			+ "    \"ISSUINGOFFICERIDP\" VARCHAR_IGNORECASE(10),\r\n"
    			+ "    \"DRIVERIDCITATIONP\" VARCHAR_IGNORECASE(25),\r\n"
    			+ "    \"CITATIONREASON\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"CITATIONDATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"FINEAMOUNT\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"PAYMENTSTATUS\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");      \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_88\" PRIMARY KEY(\"CITATIONID\");     \r\n"
    			+ "-- 8 +/- SELECT COUNT(*) FROM TCRS.DRIVINGCITATIONSPROV;       \r\n"
    			+ "INSERT INTO \"TCRS\".\"DRIVINGCITATIONSPROV\" ( CITATIONID, ISSUINGOFFICERIDP, DRIVERIDCITATIONP, CITATIONREASON, CITATIONDATE, fineAmount, paymentStatus) VALUES\r\n"
    			+ "(1, '10001', 'A12345678912345', 'Speeding', '2014-05-06', '150', 'Paid'),\r\n"
    			+ "(2, '10001', 'B23456789023456', 'DUI', '2002-09-15', '400', 'Unpaid'),\r\n"
    			+ "(3, '10002', 'A12345678912345', 'Speeding', '2008-08-18', '200', 'Paid'),\r\n"
    			+ "(4, '10006', 'E56789012356789', 'Reckless Driving', '2013-09-15', '300', 'Unpaid'),\r\n"
    			+ "(5, '10006', 'J01234567801234', 'Speeding', '2012-09-15', '200', 'Paid'),\r\n"
    			+ "(6, '10002', 'G78901234578901', 'DUI', '2017-04-23', '400', 'Unpaid'),\r\n"
    			+ "(7, '10007', 'H89012345689012', 'Speeding', '2002-09-15', '200', 'Paid'),\r\n"
    			+ "(8, '10008', 'B23456789023456', 'Reckless Driving', '2015-07-14', '300', 'Paid');   \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"TRAFFICSCHOOL\"(\r\n"
    			+ "    \"CITATIONIDTS\" INTEGER NOT NULL,\r\n"
    			+ "    \"SESSION1DATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"SESSION2DATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"SESSION3DATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"SESSION4DATE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"SESSION1ATTENDANCE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"SESSION2ATTENDANCE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"SESSION3ATTENDANCE\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"SESSION4ATTENDANCE\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");     \r\n"
    			+ "ALTER TABLE \"TCRS\".\"TRAFFICSCHOOL\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_E\" PRIMARY KEY(\"CITATIONIDTS\");           \r\n"
    			+ "-- 2 +/- SELECT COUNT(*) FROM TCRS.TRAFFICSCHOOL;              \r\n"
    			+ "INSERT INTO \"TCRS\".\"TRAFFICSCHOOL\" VALUES\r\n"
    			+ "(3, '2023-08-02', '2023-08-10', '2023-08-10', '2023-08-15', 'Yes', 'Yes', 'Yes', 'Yes'),\r\n"
    			+ "(10, '2023-09-02', '2023-09-02', '2023-10-02', '2023-10-02', 'No', 'No', 'Yes', 'Yes');     \r\n"
    			+ "CREATE CACHED TABLE \"TCRS\".\"ACCOUNTS\"(\r\n"
    			+ "    \"ACCOUNTID\" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,\r\n"
    			+ "    \"USERNAME\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"PASSWORDACC\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"FIRSTNAME\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"LASTNAME\" VARCHAR_IGNORECASE(255),\r\n"
    			+ "    \"AGENCY\" VARCHAR_IGNORECASE(255)\r\n"
    			+ ");            \r\n"
    			+ "ALTER TABLE \"TCRS\".\"ACCOUNTS\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_A\" PRIMARY KEY(\"ACCOUNTID\");   \r\n"
    			+ "-- 10 +/- SELECT COUNT(*) FROM TCRS.ACCOUNTS;  \r\n"
    			+ "INSERT INTO \"TCRS\".\"ACCOUNTS\" ( username, passwordAcc, firstName, lastName, agency) VALUES\r\n"
    			+ "('badewale', '239592830', 'Banki', 'Adewale', 'Administration'),\r\n"
    			+ "('gamjoun', '239349660', 'Ghizlane', 'Amjoun', 'Administration'),\r\n"
    			+ "('brajaie', '239394290', 'Ben', 'Rajaie', 'Administration'),\r\n"
    			+ "('kuwaechi', '199677980', 'Kennedy', 'Uwaechi', 'Administration'),\r\n"
    			+ "('sjohn', '10002', 'Smith', 'John', 'Provincial'),\r\n"
    			+ "('jdavid', '10003', 'Johnson', 'David', 'Municipal'),\r\n"
    			+ "('wchris', '10004', 'Williams', 'Chris', 'Provincial'),\r\n"
    			+ "('bmike', '10005', 'Brown', 'Mike', 'Municipal'),\r\n"
    			+ "('jsarah', '10006', 'Jones', 'Sarah', 'Provincial'),\r\n"
    			+ "('grobert', '10007', 'Garcia', 'Robert', 'Municipal');          \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_15D\" FOREIGN KEY(\"DRIVERIDWARRANTM\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;   \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_117\" FOREIGN KEY(\"WARRANTID\") REFERENCES \"TCRS\".\"DRIVERWARRANTSMUNICIPLE\"(\"WARRANTID\") NOCHECK;      \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_711\" FOREIGN KEY(\"DRIVERIDCITATIONM\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;\r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_71\" FOREIGN KEY(\"ISSUINGOFFICERIDM\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK; \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_885A\" FOREIGN KEY(\"ISSUINGOFFICERIDP\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK;    \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C205\" FOREIGN KEY(\"VINCITATIONP\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;  \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_3A9\" FOREIGN KEY(\"VINWARRANTM\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;\r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C2\" FOREIGN KEY(\"CITATIONID\") REFERENCES \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\"(\"CITATIONID\") NOCHECK; \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_2B\" FOREIGN KEY(\"WARRANTIDV\") REFERENCES \"TCRS\".\"VEHICLEWARRANTSMUNICIPLE\"(\"WARRANTIDV\") NOCHECK; \r\n"
    			+ "ALTER TABLE \"TCRS\".\"TRAFFICSCHOOL\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_E7\" FOREIGN KEY(\"CITATIONIDTS\") REFERENCES \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\"(\"CITATIONID\") NOCHECK;      \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_C20\" FOREIGN KEY(\"ISSUINGOFFICERIDP\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK;     \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_885\" FOREIGN KEY(\"CITATIONID\") REFERENCES \"TCRS\".\"DRIVINGCITATIONSMUNICIPLE\"(\"CITATIONID\") NOCHECK;\r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLEWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_2BC\" FOREIGN KEY(\"VINWARRANTP\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;     \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVINGCITATIONSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_885AD\" FOREIGN KEY(\"DRIVERIDCITATIONP\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;   \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1B\" FOREIGN KEY(\"ISSUINGOFFICERIDM\") REFERENCES \"TCRS\".\"OFFICERINFO\"(\"BADGENUMBER\") NOCHECK; \r\n"
    			+ "ALTER TABLE \"TCRS\".\"DRIVERWARRANTSPROV\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1179\" FOREIGN KEY(\"DRIVERIDWARRANTP\") REFERENCES \"TCRS\".\"DRIVERINFO\"(\"LICENSENUMBER\") NOCHECK;       \r\n"
    			+ "ALTER TABLE \"TCRS\".\"VEHICLECITATIONSMUNICIPLE\" ADD CONSTRAINT \"TCRS\".\"CONSTRAINT_1B4\" FOREIGN KEY(\"VINCITATIONM\") REFERENCES \"TCRS\".\"VEHICLEINFO\"(\"VIN\") NOCHECK;              \r\n";
 
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
