package CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import databaseManagement.*;

public class TrafficSchool {

    private DatabaseManager databaseManager;

    public int citationID;
    public String session1Date;
    public String session2Date;
    public String session3Date;
    public String session4Date;
    public String session1Attendance;
    public String session2Attendance;
    public String session3Attendance;
    public String session4Attendance;

    public TrafficSchool(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    public int getCitationID() {
        return citationID;
    }

    public String getSession1Date() {
        return session1Date;
    }

    public String getSession2Date() {
        return session2Date;
    }

    public String getSession3Date() {
        return session3Date;
    }

    public String getSession4Date() {
        return session4Date;
    }

    public String getSession1Attendance() {
        return session1Attendance;
    }

    public String getSession2Attendance() {
        return session2Attendance;
    }

    public String getSession3Attendance() {
        return session3Attendance;
    }

    public String getSession4Attendance() {
        return session4Attendance;
    }
    
    public void setSession1Date(String session1Date) {
        this.session1Date = session1Date;;
    }

    public void setSession2Date(String session2Date) {
        this.session2Date = session2Date;
    }

    public void setSession3Date(String session3Date) {
        this.session3Date = session3Date;
    }

    public void setSession4Date(String session4Date) {
        this.session4Date = session4Date;
    }

    public void setSession1Attendance(String session1Attendance) {
        this.session1Attendance = session1Attendance;
    }

    public void setSession2Attendance(String session2Attendance) {
        this.session2Attendance = session2Attendance;
    }

    public void setSession3Attendance(String session3Attendance) {
        this.session3Attendance = session3Attendance;
    }

    public void setSession4Attendance(String session4Attendance) {
        this.session4Attendance =  session4Attendance;
    }

    public void insertEnrollment(TrafficSchool trafficSchool) {
        insertEnrollment(String.valueOf(trafficSchool.citationID), trafficSchool.session1Date, trafficSchool.session2Date,
                trafficSchool.session3Date, trafficSchool.session4Date, trafficSchool.session1Attendance,
                trafficSchool.session2Attendance, trafficSchool.session3Attendance, trafficSchool.session4Attendance);
    }

    public void insertEnrollment(String citationID, String session1Date, String session2Date, String session3Date,
            String session4Date, String session1Attendance, String session2Attendance, String session3Attendance,
            String session4Attendance) {
       
        String sql = String.format(
                "INSERT INTO TCRS.TRAFFICSCHOOL (CITATIONIDTS, SESSION1DATE, SESSION2DATE, SESSION3DATE, SESSION4DATE, SESSION1ATTENDANCE, SESSION2ATTENDANCE, SESSION3ATTENDANCE, SESSION4ATTENDANCE) "
                        + "VALUES ('%d', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                Integer.valueOf(citationID), session1Date, session2Date,
                session3Date, session4Date, session1Attendance,
                session2Attendance, session3Attendance, session4Attendance);

        databaseManager.executeUpdate(sql);

        System.out.println("Enrollment added to the database!");
    }

    public void editEnrollment(int citationID, TrafficSchool newTrafficSchool) {
        TrafficSchool trafficSchool = findEnrollment(citationID);

        if (!inSystem(trafficSchool)) {
            return;
        }

        String sqlQuery = String.format(
                "UPDATE TCRS.TRAFFICSCHOOL SET SESSION1DATE = '%s', SESSION2DATE = '%s', SESSION3DATE = '%s', SESSION4DATE = '%s', SESSION1ATTENDANCE = '%s', SESSION2ATTENDANCE = '%s', SESSION3ATTENDANCE = '%s', SESSION4ATTENDANCE = '%s'"
                        + " WHERE CITATIONIDTS = '%d'",
                newTrafficSchool.session1Date, newTrafficSchool.session2Date, newTrafficSchool.session3Date,
                newTrafficSchool.session4Date, newTrafficSchool.session1Attendance, newTrafficSchool.session2Attendance,
                newTrafficSchool.session3Attendance, newTrafficSchool.session4Attendance, trafficSchool.citationID);

        databaseManager.executeUpdate(sqlQuery);

        System.out.println("Enrollment edited");
    }

    public void deleteEnrollment(int citationID) {
        TrafficSchool trafficSchool = findEnrollment(citationID);

        if (!inSystem(trafficSchool)) {
            return;
        }

        String sqlDelete = String.format("DELETE FROM TCRS.TRAFFICSCHOOL WHERE CITATIONIDTS= '%d'", citationID);

        databaseManager.executeUpdate(sqlDelete);

        System.out.println("Enrollment " + citationID + " removed from system");
    }

    public TrafficSchool findEnrollment(int citationID) {
        TrafficSchool trafficSchool = new TrafficSchool(this.databaseManager);

        String sqlQuery = String.format("SELECT * FROM TCRS.TRAFFICSCHOOL WHERE CITATIONIDTS='%d'", citationID);

        ResultSet result = databaseManager.executeQuery(sqlQuery);

        if (nullCheck(result))
            return null;

        return logData(result, trafficSchool);
    }

    public String toString() {
        return "Citation ID " + this.citationID + " Session 1 Date: " + this.session1Date + " Session 2 Date: "
                + this.session2Date + " Session 3 Date: " + this.session3Date + " Session 4 Date: " + this.session4Date
                + " Session 1 Attendance: " + this.session1Attendance + " Session 2 Attendance: "
                + this.session2Attendance + " Session 3 Attendance: " + this.session3Attendance
                + " Session 4 Attendance: " + this.session4Attendance;
    }

    // helper methods

    private TrafficSchool logData(ResultSet result, TrafficSchool trafficSchool) {
        try {
            while (result.next()) {
                trafficSchool.citationID = result.getInt("CITATIONIDTS");
                trafficSchool.session1Date = result.getString("session1Date");
                trafficSchool.session2Date = result.getString("session2Date");
                trafficSchool.session3Date = result.getString("session3Date");
                trafficSchool.session4Date = result.getString("session4Date");
                trafficSchool.session1Attendance = result.getString("session1Attendance");
                trafficSchool.session2Attendance = result.getString("session2Attendance");
                trafficSchool.session3Attendance = result.getString("session3Attendance");
                trafficSchool.session4Attendance = result.getString("session4Attendance");

                return trafficSchool;
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
                System.out.println("Enrollment not in the system!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    private boolean emptyField(TrafficSchool trafficSchool) {
        return emptyField(trafficSchool.citationID, trafficSchool.session1Date, trafficSchool.session2Date,
                trafficSchool.session3Date, trafficSchool.session4Date, trafficSchool.session1Attendance,
                trafficSchool.session2Attendance, trafficSchool.session3Attendance, trafficSchool.session4Attendance);
    }

    private boolean emptyField(int citationID, String session1Date, String session2Date, String session3Date,
            String session4Date, String session1Attendance, String session2Attendance, String session3Attendance,
            String session4Attendance) {
        if (citationID == 0) {
            System.out.println(String.format("Cannot leave citation ID blank!"));
            return true;
        }
        if (session1Date == null || session1Date.isEmpty()) {
            System.out.println(String.format("Cannot leave session 1 date blank!"));
            return true;
        }
        if (session2Date == null || session2Date.isEmpty()) {
            System.out.println(String.format("Cannot leave session 2 date blank!"));
            return true;
        }
        if (session3Date == null || session3Date.isEmpty()) {
            System.out.println(String.format("Cannot leave session 3 date blank!"));
            return true;
        }
        if (session4Date == null || session4Date.isEmpty()) {
            System.out.println(String.format("Cannot leave session 4 date blank!"));
            return true;
        }
        if (session1Attendance == null || session1Attendance.isEmpty()) {
            System.out.println(String.format("Cannot leave session 1 attendance blank!"));
            return true;
        }
        if (session2Attendance == null || session2Attendance.isEmpty()) {
            System.out.println(String.format("Cannot leave session 2 attendance blank!"));
            return true;
        }
        if (session3Attendance == null || session3Attendance.isEmpty()) {
            System.out.println(String.format("Cannot leave session 3 attendance blank!"));
            return true;
        }
        if (session4Attendance == null || session4Attendance.isEmpty()) {
            System.out.println(String.format("Cannot leave session 4 attendance blank!"));
            return true;
        }

        return false;
    }

   

    private boolean inSystem(TrafficSchool trafficSchool) {
        if (trafficSchool == null) {
            System.out.println("Enrollment not in the system!");
            return false;
        }

        return true;
    }
}