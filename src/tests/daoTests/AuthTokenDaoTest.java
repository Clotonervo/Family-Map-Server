/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */

package tests.daoTests;

import dao.AuthTokenDao;
import dao.UserDao;
import models.AuthorizationToken;
import static org.junit.Assert.*;
import org.junit.*;
import java.sql.*;

public class AuthTokenDaoTest {

    @After
    public void tearDown()
    {
        try {
            AuthTokenDao authDao = new AuthTokenDao();
            authDao.clearTables();
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void insertToken() //Checking if basic functions work
    {
        AuthTokenDao authDao = new AuthTokenDao();
        AuthorizationToken token = new AuthorizationToken("xxyyxx", "clotonervo");
        try {
            authDao.initializeDatabase();
            authDao.clearTables();
            authDao.insertToken(token);
            AuthorizationToken initToken = authDao.checkToken("xxyyxx");
            Assert.assertEquals(initToken, token);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test(expected = SQLException.class)
    public void insertTokenFail() throws SQLException //Checking if inserting an already existing authtoken is allowed
    {
        AuthTokenDao authDao = new AuthTokenDao();
        AuthorizationToken token = new AuthorizationToken("xxyyxx", "clotonervo");
        try {
            authDao.initializeDatabase();
            authDao.clearTables();
            authDao.insertToken(token);
            authDao.insertToken(token);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
            throw databaseError;
        }
    }


    @Test
    public void checkToken() //Checking if basic functionality is working
    {
        AuthTokenDao authDao = new AuthTokenDao();
        AuthorizationToken token = new AuthorizationToken("xxyyxx", "clotonervo");
        try {
            authDao.initializeDatabase();
            authDao.clearTables();
            Assert.assertNull(authDao.checkToken("xxyyxx"));
            authDao.insertToken(token);
            AuthorizationToken initToken = authDao.checkToken("xxyyxx");
            Assert.assertEquals(initToken, token);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void checkTokenFail() //Checking if returns a non-existant authToken
    {
        AuthTokenDao authDao = new AuthTokenDao();
        try {
            authDao.initializeDatabase();
            authDao.clearTables();
            Assert.assertNull(authDao.checkToken("xxyyxx"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }
}