/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.daoTests;

import dao.UserDao;
import models.Users;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;


import java.sql.SQLException;

import static org.junit.Assert.*;


public class UserDaoTest {


    @After
    public void tearDown()
    {
        try {
            UserDao userDao = new UserDao();
            userDao.clearTables();
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void registerNewUser() //Registering a simple new user
    {
        UserDao userDao = new UserDao();
        Users testUser = new Users("yes", "no", "false", "john", "doe","m","1234");
        try {
            userDao.initializeDatabase();
            userDao.clearTables();
            userDao.insertUser(testUser);
            Users initUser = userDao.findUser("yes");
            Assert.assertEquals(initUser, testUser);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }

    }

    @Test
    public void findUser() //Finding a simple user using userID
    {
        UserDao userDao = new UserDao();
        Users testUser = new Users("yes", "no", "false", "john", "doe","m","1234");
        try {
            userDao.initializeDatabase();
            userDao.clearTables();
            Assert.assertNull(userDao.findUser("yes"));
            userDao.insertUser(testUser);
            Users initUser = userDao.findUser("yes");
            Assert.assertEquals(initUser, testUser);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test(expected = SQLException.class)
    public void registerNewUserFalse() throws SQLException //Registering the same user twice
    {
        UserDao userDao = new UserDao();
        Users testUser = new Users("yes", "no", "false", "john", "doe","m","1234");
        try {
            userDao.initializeDatabase();
            userDao.clearTables();
            userDao.insertUser(testUser);
            userDao.insertUser(testUser);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
            throw databaseError;
        }
    }


    @Test
    public void findUserFalse() //Finding a user that does not exist
    {
        UserDao userDao = new UserDao();
        Users testUser = new Users("yes", "no", "false", "john", "doe","m","1234");
        try {
            userDao.initializeDatabase();
            userDao.clearTables();
            userDao.insertUser(testUser);
            Assert.assertNull(userDao.findUser("notthere"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void clearUsers() //Clearing the user information
    {
        UserDao userDao = new UserDao();
        Users testUser = new Users("yes", "no", "false", "john", "doe","m","1234");
        try {
            userDao.initializeDatabase();
            userDao.clearTables();
            Assert.assertNull(userDao.findUser("yes"));
            userDao.insertUser(testUser);
            Users initUser = userDao.findUser("yes");
            Assert.assertEquals(initUser, testUser);
            Assert.assertTrue(userDao.clearUsers());
            Assert.assertNull(userDao.findUser("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }
}