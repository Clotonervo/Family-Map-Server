/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.serviceTests;

import dao.UserDao;
import models.Users;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import requests.LoginRequest;
import results.LoginResult;
import services.LoginService;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class LoginServiceTest {

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

    @Before
    public void setUp()
    {
        try {
            UserDao userDao = new UserDao();
            userDao.clearTables();
            Users testUser = new Users("yes", "no", "false", "john", "doe","m","1234");
            userDao.insertUser(testUser);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void login() //Login with existing user
    {

        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(new LoginRequest("yes", "no"));

        Assert.assertTrue(loginResult.getUserName().equals("yes"));
        Assert.assertNull(loginResult.getErrorMessage());
        Assert.assertNotNull(loginResult.getPersonID());
        Assert.assertNotNull(loginResult.getToken());

    }

    @Test
    public void loginUserNotExist() throws SQLException //login when user does not exist
    {
        LoginService loginService = new LoginService();
        UserDao userDao = new UserDao();
        userDao.clearTables();

        LoginResult loginResult = loginService.login(new LoginRequest("yes", "no"));
        Assert.assertNull(loginResult.getUserName());
        Assert.assertNotNull(loginResult.getErrorMessage());
        Assert.assertNull(loginResult.getPersonID());
        Assert.assertNull(loginResult.getToken());
        Assert.assertTrue(loginResult.getErrorMessage().equals("User not found or does not exist"));

    }

    @Test
    public void loginWrongPassword() //login when given incorrect password
    {
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(new LoginRequest("yes", "nooooo"));

        Assert.assertNull(loginResult.getUserName());
        Assert.assertNotNull(loginResult.getErrorMessage());
        Assert.assertNull(loginResult.getPersonID());
        Assert.assertNull(loginResult.getToken());
        Assert.assertTrue(loginResult.getErrorMessage().equals("Incorrect Password"));
    }
}