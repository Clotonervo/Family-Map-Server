/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package dao;

import models.Events;
import java.sql.*;
import java.util.ArrayList;

/** Event Dao class deals with all manipulation of the database regarding event data*/
public class EventDao extends DataBaseManager{


//______________________________________ Insert Event Functions ______________________________________________________
    /** insertEvent
     * @param eventToInsert Events object that is wanting to be inserted
     * @return boolean true if insert was a success, false if not
     * @throws SQLException deals with errors in the database
     */
    public boolean insertEvent(Events eventToInsert) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        boolean success = false;
        try {
            openDatabase();
            success = insertEventHelper(eventToInsert);
            closeDatabase();
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            try {
                closeDatabase();
            }
            catch (SQLException closeDbError){
                closeDbError.printStackTrace();
            }
            throw databaseError;
        }

        return success;
    }

    //---****************--- insertEventHelper --*********************-------
    private boolean insertEventHelper (Events eventToInsert) throws SQLException
    {
        PreparedStatement stmt = null;
        try {

            String sql = "INSERT INTO events(id, user_id, person_id, " +
                    "latitude, longitude, country, city, type, year) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, eventToInsert.getEventID());
            stmt.setString(2, eventToInsert.getEventDescendantID());
            stmt.setString(3, eventToInsert.getEventPersonID());
            stmt.setDouble(4, eventToInsert.getEventLatitude());
            stmt.setDouble(5, eventToInsert.getEventLongitude());
            stmt.setString(6, eventToInsert.getEventCountry());
            stmt.setString(7, eventToInsert.getEventCity());
            stmt.setString(8, eventToInsert.getEventType());
            stmt.setInt(9, eventToInsert.getEventYear());


            if (stmt.executeUpdate() == 1){
                allDatabaseOperationsSucceeded = true;
            }
            else{
                System.out.println("Something is up: insertEvent(), EventDao.java");
                allDatabaseOperationsSucceeded = false;
            }
        }
        catch (SQLException databaseError) {
            System.out.println(databaseError.toString());
            throw databaseError;
        }
        finally {
            if (stmt != null){
                stmt.close();
            }
        }

        return allDatabaseOperationsSucceeded;
    }


//______________________________________ Insert User Functions ______________________________________________________
    /** findSingleEvent
     * @param eventID that is an event ID to find
     * @return Events object that has a single event in it
     * @throws SQLException deals with errors in the database
     */
    public Events findSingleEvent(String eventID) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        Events eventToReturn = null;
        try {
            openDatabase();
            eventToReturn = lookForEvent(eventID);
            closeDatabase();
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            try {
                closeDatabase();
            }
            catch (SQLException closeDbError){
                closeDbError.printStackTrace();
            }
            throw databaseError;
        }

        return eventToReturn;
    }

    //---******************--- look for a single event --********************-----
    private Events lookForEvent(String eventID) throws SQLException
    {
        Events event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                event = new Events(rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("person_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("type"),
                        rs.getInt("year"));

                return event;
            }
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            throw databaseError;
        }

        return null;
    }


//______________________________________ Insert User Functions ______________________________________________________
    /** findAllEvents
     * @param userID username of the user
     * @return ArrayList<Events> object of list of events that are found under said userID
     * @throws SQLException deals with errors in the database
     */
    public ArrayList<Events> findAllEvents(String userID) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        ArrayList<Events> eventsToReturn = null;
        try {
            openDatabase();
            eventsToReturn = lookForAllEvents(userID);
            closeDatabase();
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            try {
                closeDatabase();
            }
            catch (SQLException closeDbError){
                closeDbError.printStackTrace();
            }
            throw databaseError;
        }

        return eventsToReturn;
    }

    //----****************---- look for all events under a username ---******************------
    private ArrayList<Events> lookForAllEvents(String userID) throws SQLException
    {
        ArrayList<Events> eventArray = new ArrayList<Events>();
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE user_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            while (rs.next() == true) {
                Events event = new Events(rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("person_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("type"),
                        rs.getInt("year"));

                eventArray.add(event);
            }
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            throw databaseError;
        }

        if (eventArray.size() == 0){
            return null;
        }
        else {
            return eventArray;
        }
    }

//______________________________________ Delete Events From User ______________________________________________________
    /**
     * clearEventInfoFromUser
     * @param userName that is the userID that we need to delete people
     * @return true if successful, false if not
     */
    public boolean clearEventInfoFromUser(String userName)
    {
        String sql = "DELETE FROM events WHERE user_id = ?;";

        try {
            openDatabase();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.executeUpdate();
            allDatabaseOperationsSucceeded = true;
            closeDatabase();
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            try {
                closeDatabase();
            }
            catch (SQLException closeDbError){
                closeDbError.printStackTrace();
            }
            return false;
        }

        return true;
    }

}
