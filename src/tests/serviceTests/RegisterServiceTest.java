package tests.serviceTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import requests.RegisterRequest;
import results.RegisterResult;
import services.ClearService;
import services.RegisterService;

import static org.junit.Assert.*;

public class RegisterServiceTest {

    @Before
    public void setUp()
    {
        ClearService clearService = new ClearService();
        clearService.clearDb();
    }

    @Test
    public void registerNewUser() //Using the services to register a new user
    {
        RegisterService registerService = new RegisterService();
        RegisterRequest regReq = new RegisterRequest("yes", "no", "false", "john", "doe","m");

        RegisterResult regRes = registerService.registerNewUser(regReq);

        Assert.assertNotNull(regRes.getPersonID());
        Assert.assertNotNull(regRes.getToken());
        Assert.assertNotNull(regRes.getUserName());
        Assert.assertNull(regRes.getErrorMessage());

    }

    @Test
    public void registerNewUserInvalidInput() //Registering a new user with bad input
    {
        RegisterService registerService = new RegisterService();
        RegisterRequest regReq = new RegisterRequest("yes", "no", "false", "john", "doe","m");
        regReq.setUserGender(null);

        RegisterResult regRes = registerService.registerNewUser(regReq);

        Assert.assertNotNull(regRes.getErrorMessage());
        Assert.assertEquals(regRes.getErrorMessage(),"Invalid Input");
        Assert.assertNull(regRes.getToken());
        Assert.assertNull(regRes.getUserName());
        Assert.assertNull(regRes.getPersonID());

    }

    @Test
    public void registerNewUserAlreadyExists() //Registering a new user that is already in the database
    {
        RegisterService registerService = new RegisterService();
        RegisterRequest regReq = new RegisterRequest("yes", "no", "false", "john", "doe","m");
        RegisterResult regRes = registerService.registerNewUser(regReq);

        regRes = registerService.registerNewUser(regReq);

        Assert.assertNotNull(regRes.getErrorMessage());
        Assert.assertEquals(regRes.getErrorMessage(),"Username already exists!");
        Assert.assertNull(regRes.getToken());
        Assert.assertNull(regRes.getUserName());
        Assert.assertNull(regRes.getPersonID());

    }
}