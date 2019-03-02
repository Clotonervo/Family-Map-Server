/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.serviceTests;

import dao.AuthTokenDao;
import dao.UserDao;
import models.AuthorizationToken;
import models.Users;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import results.AllPersonResults;
import results.FillResult;
import services.ClearService;

import static org.junit.Assert.*;
import services.FillService;
import services.PersonService;

import java.sql.SQLException;

public class FillServiceTest {

    @Before
    public void setUp() throws SQLException
    {
        ClearService clearService = new ClearService();
        clearService.clearDb();
        AuthTokenDao authTokenDao = new AuthTokenDao();
        authTokenDao.insertToken(new AuthorizationToken("1234", "no"));
        authTokenDao.insertToken(new AuthorizationToken("10", "nonexistant"));
        Users testUser = new Users("no", "yup", "false", "john", "doe","m","1234");
        UserDao userDao = new UserDao();
        userDao.insertUser(testUser);
    }

    @Test
    public void fill() //Basic fill, testing to see if it inserts the whole family tree
    {
        PersonService personService = new PersonService();
        AllPersonResults allPersonResults = personService.allPersons("1234");
        Assert.assertNull(allPersonResults.getPersonsArray());
        Assert.assertEquals(allPersonResults.getErrorMessage(), "There are no people under user");

        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill("no", 4);

        allPersonResults = personService.allPersons("1234");
        Assert.assertNotNull(allPersonResults.getPersonsArray());
        Assert.assertEquals(allPersonResults.getPersonsArray().size(), 31);
        Assert.assertNotEquals(fillResult.getResult(), "User not found!");
        Assert.assertNotEquals(fillResult.getResult(), "Number of Generations invalid");
        Assert.assertNotEquals(fillResult.getResult(), "Error");
    }

    @Test
    public void fillFail() //Fill failing due to invalid input, a negative number of generations
    {
        PersonService personService = new PersonService();
        AllPersonResults allPersonResults = personService.allPersons("1234");
        Assert.assertNull(allPersonResults.getPersonsArray());
        Assert.assertEquals(allPersonResults.getErrorMessage(), "There are no people under user");

        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill("no", -1);

        Assert.assertEquals("Number of Generations invalid", fillResult.getResult());
    }
}