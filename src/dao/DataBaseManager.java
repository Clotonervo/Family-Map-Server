/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package dao;

import java.sql.*;


/** DataBaseManager class manages all basic utility functions of DAOs like opening and closing connections*/
public class DataBaseManager {

//______________________________- Protected Functions used by other DAOs- _____________________________
    protected Connection connection;
    protected boolean allDatabaseOperationsSucceeded;

    //--*******************-- Opens the Database ---*********************----
    protected void openDatabase()
    {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException databaseError) {
            System.out.println("ERROR! Could not load database driver");
        }
        String dbName = "FMServerDatabase.db";
        String connectionURL = "jdbc:sqlite:" + dbName;

        connection = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
        }
        catch (SQLException databaseError) {
            System.out.println("Something went wrong in 'openConnection' function");
        }
    }

    //---*****************--- Closes the Database --*************************----
    protected void closeDatabase() throws SQLException
    {
        try {
            if (allDatabaseOperationsSucceeded) {
                allDatabaseOperationsSucceeded = false;
                connection.commit();
            }
            else {
                connection.rollback();
            }
        }
        catch (SQLException databaseError) {
            System.out.println("Trouble with operations, rollback");
        }
        finally {
            connection.close();
        }

        connection = null;

    }
//____________________________- End Protected Functions -_______________________
//
//
//
//________________________________ Initializes the Database _____________________________
    /** initializeDatabase
     * Initializes the database with all tables
     * @return boolean true if complete
     * @throws SQLException deals with errors in the database
     */
    public boolean initializeDatabase() throws SQLException
    {
        boolean success = false;
        try {
            openDatabase();
            success = createTables();
            closeDatabase();
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            return false;
        }
        allDatabaseOperationsSucceeded = false;

        return success;
    }


//_______________________________ Clears the whole database _____________________________
    /** clearTables clears all tables from the database
     * @return boolean either true or false if command was successful
     * @throws SQLException deals with errors in the database
     */
    public boolean clearTables() throws SQLException
    {
        boolean deleteSuccess = false;
        boolean createSuccess = false;
        try {
            openDatabase();
            deleteSuccess = deleteTables();
            createSuccess = createTables();
            closeDatabase();

        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            try {
                closeDatabase();
            }
            catch(SQLException closeDbEx){
                closeDbEx.printStackTrace();
            }
            throw databaseError;
        }
        allDatabaseOperationsSucceeded = false;

        return deleteSuccess && createSuccess;
    }

    //---**********************-- deletes all the tables --********************---
    private boolean deleteTables() throws SQLException
    {
        PreparedStatement stmt = null;
        try {
            String sql =  "DROP TABLE IF EXISTS users; \n";
            String sql2 = "DROP TABLE IF EXISTS events; \n";
            String sql3 = "DROP TABLE IF EXISTS persons; \n";
            String sql4 = "DROP TABLE IF EXISTS authtokens; \n";

            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt = connection.prepareStatement(sql2);
            stmt.executeUpdate();
            stmt = connection.prepareStatement(sql3);
            stmt.executeUpdate();
            stmt = connection.prepareStatement(sql4);
            stmt.executeUpdate();

            allDatabaseOperationsSucceeded = true;
       }
        catch (SQLException databaseError){
           System.out.println(databaseError.toString());
           allDatabaseOperationsSucceeded = false;
           throw databaseError;
       }
       finally {
           if (stmt != null){
               stmt.close();
           }
       }

        return allDatabaseOperationsSucceeded;
    }

    //---***************--- recreates all the tables --******************---
    private boolean createTables() throws SQLException
    {

        PreparedStatement stmt = null;
        try {
            String sql = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "\t`username`\tvarchar ( 255 ) NOT NULL UNIQUE,\n" +
                    "\t`password`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`email_address`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`first_name`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`last_name`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`gender`\tvarchar ( 32 ) NOT NULL,\n" +
                    "\t`person_id`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\tPRIMARY KEY(`username`)\n" +
                    ");\n\n";

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
            String sql3 = "CREATE TABLE IF NOT EXISTS `events` (\n" +
                    "\t`id`\tvarchar ( 255 ) NOT NULL UNIQUE,\n" +
                    "\t`user_id`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`person_id`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`latitude`\treal NOT NULL,\n" +
                    "\t`longitude`\treal NOT NULL,\n" +
                    "\t`country`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`city`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`type`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`year`\tint NOT NULL,\n" +
                    "\tPRIMARY KEY(`id`)\n" +
                    ");\n" +
                    "\n";
            String sql4 = "CREATE TABLE IF NOT EXISTS `authtokens` (\n" +
                    "\t`authorization_token`\tvarchar ( 255 ) NOT NULL UNIQUE,\n" +
                    "\t`username`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\tPRIMARY KEY(`authorization_token`)\n" +
                    ");\n";

            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt = connection.prepareStatement(sql2);
            stmt.executeUpdate();
            stmt = connection.prepareStatement(sql3);
            stmt.executeUpdate();
            stmt = connection.prepareStatement(sql4);
            stmt.executeUpdate();

            allDatabaseOperationsSucceeded = true;
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            allDatabaseOperationsSucceeded = false;
            throw databaseError;
        }
        finally {
            if (stmt != null){
                stmt.close();
            }
        }

        return allDatabaseOperationsSucceeded;
    }
}
