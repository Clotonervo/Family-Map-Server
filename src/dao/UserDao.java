/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package dao;

import models.Users;
import java.sql.*;

/** UserDao is granted accesses to the User table in the database. It manages registering new users and login info */
public class UserDao extends DataBaseManager{



//______________________________________ Insert User Functions _________________________________________________________
    /** insertUser
     * @param userToInsert User Object with all information needed to be put in the database
     * @return boolean true if inserted correctly and false if failed
     * @throws SQLException if something goes wrong
     */
    public boolean insertUser(Users userToInsert) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        boolean registerSuccess = false;
        try {
            openDatabase();
            registerSuccess = insertUserHelper(userToInsert);
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

        return registerSuccess;
    }

    //---**************---- insertUserHelper ----*****************----
    private boolean insertUserHelper(Users userToInsert) throws SQLException
    {
        PreparedStatement stmt = null;
        try {

            String sql = "INSERT INTO users(username, password, email_address," +
                    " first_name, last_name, gender, person_id) " +
                    "VALUES(?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userToInsert.getUserNameID());
            stmt.setString(2, userToInsert.getUserPassword());
            stmt.setString(3, userToInsert.getUserEmail());
            stmt.setString(4, userToInsert.getUserFirstName());
            stmt.setString(5, userToInsert.getUserLastName());
            stmt.setString(6, userToInsert.getUserGender());
            stmt.setString(7, userToInsert.getUserPersonID());


            if (stmt.executeUpdate() == 1){
                allDatabaseOperationsSucceeded = true;
            }
            else{
                System.out.println("Something is up: insertUser(), UserDao.java");
                allDatabaseOperationsSucceeded = false;
            }
        }
        catch (SQLException databaseError) {
            System.out.println(databaseError.toString());
            throw databaseError;
        }
        finally {
            if (stmt != null) stmt.close();
        }

        return allDatabaseOperationsSucceeded;
    }

//______________________________________ Find User Functions ___________________________________________________________
    /** findUser
     * @param inputUsername username that will be used to find user information in the database
     * @return User if found, else null
     * @throws SQLException deals with errors in the database
     * */
    public Users findUser (String inputUsername) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        Users userToReturn = null;
        try {
            openDatabase();
            userToReturn = lookForUser(inputUsername);
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

        return userToReturn;
    }

    //----***************---- look for a single user helper ---****************----
    private Users lookForUser (String inputUsername) throws SQLException
    {
        Users user = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, inputUsername);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                user = new Users(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email_address"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("person_id"));

                return user;
            }
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            throw databaseError;
        }

        return null;
    }

//______________________________________ Clear User Table ______________________________________________________________
    /** clearUsers clears all information stored in the users table
     * @return true if successful, false if not
     * @throws SQLException deals with errors with the database
     */
    public boolean clearUsers() throws SQLException
    {
        boolean success = false;
        PreparedStatement stmt = null;
        try {
            openDatabase();
            String sql =  "DROP TABLE IF EXISTS users; \n";

            String sql2 = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "\t`username`\tvarchar ( 255 ) NOT NULL UNIQUE,\n" +
                    "\t`password`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`email_address`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`first_name`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`last_name`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\t`gender`\tvarchar ( 32 ) NOT NULL,\n" +
                    "\t`person_id`\tvarchar ( 255 ) NOT NULL,\n" +
                    "\tPRIMARY KEY(`username`)\n" +
                    ");\n";

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
            if (stmt != null) stmt.close();
        }

        return success;
    }
}
