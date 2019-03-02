/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package dao;

import models.AuthorizationToken;

import java.sql.PreparedStatement;
import java.sql.*;

/** AuthTokenDao deals with all database manipulation that has to do with the AuthToken table in the database */
public class AuthTokenDao extends DataBaseManager{

//______________________________________ Insert an Authorization Token _________________________________________________
    /** insertToken adds a new authToken to the database
     * @param tokenToInsert AuthToken that is wanting to be inserted into table
     * @return boolean true or false depending if the token is inserted in the database and is valid
     * @throws SQLException deals with errors in the database
     */
    public boolean insertToken(AuthorizationToken tokenToInsert) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        boolean success = false;
        try {
            openDatabase();
            success = insertTokenHelper(tokenToInsert);
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

    //---********************---- insert token helper ---**********************----
    private boolean insertTokenHelper(AuthorizationToken tokenToInsert) throws SQLException
    {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO authtokens(authorization_token, username) VALUES(?,?)";

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, tokenToInsert.getAuthToken());
            stmt.setString(2, tokenToInsert.getUsername());


            if (stmt.executeUpdate() == 1){
                allDatabaseOperationsSucceeded = true;
            }
            else{
                System.out.println("Something is up: insertToken(), AuthTokenDao.java");
                allDatabaseOperationsSucceeded = false;
            }
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            throw databaseError;
        }
        finally {
            if (stmt != null) stmt.close();
        }

        return allDatabaseOperationsSucceeded;
    }

//______________________________________ Check if a token is valid _____________________________________________________
    /** checkToken checks to see if a token is valid (is contained in the database)
     * @param token that is the authtoken wanting to check
     * @return AuthorizationToken null or with username and authtoken depending if the token is found in the database
     * @throws SQLException deals with errors in the database
     */
    public AuthorizationToken checkToken(String token) throws SQLException
    {
        allDatabaseOperationsSucceeded = false;
        AuthorizationToken tokenToReturn = null;
        try {
            openDatabase();
            tokenToReturn = lookForToken(token);
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

        return tokenToReturn;
    }

    //--********************-- look for a token given an auth identifier --******************--
    private AuthorizationToken lookForToken(String token) throws SQLException
    {
        AuthorizationToken authToken = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM authtokens WHERE authorization_token = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                authToken = new AuthorizationToken(rs.getString("authorization_token"),
                        rs.getString("username"));
                return authToken;
            }
        }
        catch (SQLException databaseError){
            System.out.println(databaseError.toString());
            throw databaseError;
        }

        return null;
    }

}
