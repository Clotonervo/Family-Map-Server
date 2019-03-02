/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package dao;

import models.Persons;
import java.sql.*;
import java.util.*;

/** PersonDao interacts with the database with everything that has to do with the Person table */
public class PersonDao extends DataBaseManager {

//______________________________________ Insert Person Functions ______________________________________________________
    /** insertPerson
     * @param personToInsert Persons object that is wanting to be inserted
     * @return boolean true if insert was a success, false if not
     * @throws SQLException deals with database errors
     */
    public boolean insertPerson(Persons personToInsert) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        boolean success = false;
        try {
            openDatabase();
            success = insertPersonHelper(personToInsert);
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

    //---********************-- Person Helper ----**********************---
    private boolean insertPersonHelper(Persons personToInsert) throws SQLException
    {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO persons(id, user_id, first_name, last_name, " +
                    "gender, father_id, mother_id, spouse_id) " +
                    "VALUES(?,?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, personToInsert.getPersonID());
            stmt.setString(2, personToInsert.getDescendantID());
            stmt.setString(3, personToInsert.getPersonFirstName());
            stmt.setString(4, personToInsert.getPersonLastName());
            stmt.setString(5, personToInsert.getPersonGender());
            stmt.setString(6, personToInsert.getPersonFatherID());
            stmt.setString(7, personToInsert.getPersonMotherID());
            stmt.setString(8, personToInsert.getPersonSpouseID());

            if (stmt.executeUpdate() == 1){
                allDatabaseOperationsSucceeded = true;
            }
            else{
                System.out.println("Something is up: insertPersons(), PersonDao.java");
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

//______________________________________ Find one Person ______________________________________________________
    /** findSinglePerson
     * @param personID that is a person ID to find, as well as an AuthToken
     * @return Persons object that has a single person in it
     * @throws SQLException deals with database errors
     */
    public Persons findSinglePerson(String personID) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        Persons personToReturn = null;
        try {
            openDatabase();
            personToReturn = lookForPerson(personID);
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

        return personToReturn;
    }

    //--****************-- look for a single person (personHelper) --******************--
    private Persons lookForPerson(String personID) throws SQLException
    {
        Persons person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                person = new Persons(rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("father_id"),
                        rs.getString("mother_id"),
                        rs.getString("spouse_id"));

                return person;
            }
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            throw databaseError;
        }

        return null;
    }

//______________________________________ Find an Array of Persons ______________________________________________________
    /** findAllPersons
     * @param usernameInput username of the user
     * @return Persons[] object of list of people that are found under said username
     * @throws SQLException deals with database errors
     */
    public ArrayList<Persons> findAllPersons(String usernameInput) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        ArrayList<Persons> personsToReturn = null;
        try {
            openDatabase();
            personsToReturn = lookForAllPersons(usernameInput);
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

        return personsToReturn;
    }

    //----*****************-- look for all people under username (personArrayHelper) ---********************--
    private ArrayList<Persons> lookForAllPersons(String usernameInput) throws SQLException
    {
        ArrayList<Persons> personArray = new ArrayList<Persons>();
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE user_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usernameInput);
            rs = stmt.executeQuery();
            while (rs.next() == true) {
                Persons person = new Persons(rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("father_id"),
                        rs.getString("mother_id"),
                        rs.getString("spouse_id"));

                personArray.add(person);
            }
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            throw databaseError;
        }
        if (personArray.size() == 0){
            return null;
        }
        else {
            return personArray;
        }
    }

//______________________________________ Delete the Persons Table ______________________________________________________
    /** deletePersons function deletes all database information in the persons table
     * @return true if successful, false if not
     * @throws SQLException that deals with errors in the database
     */
    public boolean deletePersons() throws SQLException
    {
        boolean success;
        PreparedStatement stmt = null;
        try {
            openDatabase();
            String sql =  "DROP TABLE IF EXISTS persons; \n";

            String sql2 = "CREATE TABLE IF NOT EXISTS `persons` (\n" +
                    "\t`id`\tvarchar ( 255 ) NOT NULL UNIQUE,\n" +
                    "\t`user_id`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`first_name`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`last_name`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`gender`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`father_id`\tvarchar ( 255 ),\n" +
                    "\t`mother_id`\tvarchar ( 255 ),\n" +
                    "\t`spouse_id`\tvarchar ( 255 ),\n" +
                    "\tPRIMARY KEY(`id`)\n" +
                    ");\n" +
                    "\n";

            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt = connection.prepareStatement(sql2);
            stmt.executeUpdate();

            allDatabaseOperationsSucceeded = true;
            success = true;
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
        finally {
            if (stmt != null){
                stmt.close();
            }
        }

        return success;
    }

//______________________________________ Delete People From User ______________________________________________________
    /** clearPersonInfoFromUser clears all people under a user
     * @param userName string containing user_id
     * @return true if successful, false if there was a database error
     */
    public boolean clearPersonInfoFromUser(String userName)
    {
        String sql = "DELETE FROM persons WHERE user_id = ?;";

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
