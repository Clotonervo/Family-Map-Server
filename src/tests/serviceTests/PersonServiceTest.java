/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.serviceTests;

import dao.AuthTokenDao;
import dao.PersonDao;
import models.AuthorizationToken;
import models.Persons;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import results.SinglePersonResults;
import results.AllPersonResults;
import services.ClearService;
import services.PersonService;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PersonServiceTest {

    @Before
    public void setUp() throws SQLException
    {
        ClearService clearService = new ClearService();
        clearService.clearDb();
        AuthTokenDao authTokenDao = new AuthTokenDao();
        authTokenDao.insertToken(new AuthorizationToken("1234", "no"));
        authTokenDao.insertToken(new AuthorizationToken("10", "nonexistant"));
    }

    @Test
    public void singlePerson() throws SQLException //Find a single person from the database
    {
        PersonDao personDao = new PersonDao();
        Persons testPerson = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        personDao.insertPerson(testPerson);

        PersonService personService = new PersonService();
        SinglePersonResults singlePersonResults = personService.singlePerson("yes", "1234");

        Assert.assertNotNull(singlePersonResults.getDescendant());
        Assert.assertNotNull(singlePersonResults.getFatherID());
        Assert.assertNotNull(singlePersonResults.getFirstName());
        Assert.assertNotNull(singlePersonResults.getLastName());
        Assert.assertNotNull(singlePersonResults.getGender());
        Assert.assertNull(singlePersonResults.getMessage());
        Assert.assertEquals(singlePersonResults.getDescendant(), "no");

    }

    @Test
    public void singlePersonFail() throws SQLException //Find a single person without a valid auth token
    {
        PersonDao personDao = new PersonDao();
        Persons testPerson = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        personDao.insertPerson(testPerson);

        PersonService personService = new PersonService();
        SinglePersonResults singlePersonResults = personService.singlePerson("yes", "1235");

        Assert.assertNull(singlePersonResults.getDescendant());
        Assert.assertNull(singlePersonResults.getFatherID());
        Assert.assertNull(singlePersonResults.getFirstName());
        Assert.assertNull(singlePersonResults.getLastName());
        Assert.assertNull(singlePersonResults.getGender());
        Assert.assertNotNull(singlePersonResults.getMessage());
        Assert.assertEquals(singlePersonResults.getMessage(), "Invalid Authorization Token");

    }

    @Test
    public void allPersons() throws SQLException //Finding all people under a user
    {
        PersonDao personDao = new PersonDao();
        Persons personOne = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("no", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("1010", "false","jenny","F.","x",null,"yup",null);

        personDao.insertPerson(personOne);
        personDao.insertPerson(personTwo);
        personDao.insertPerson(personThree);
        personDao.insertPerson(personFour);

        PersonService personService = new PersonService();
        AllPersonResults personResults = personService.allPersons("1234");

        Assert.assertNotNull(personResults.getPersonsArray());
        Assert.assertEquals(personResults.getPersonsArray().size(), 3);
        Assert.assertNull(personResults.getErrorMessage());

        ArrayList<Persons> personArray = new ArrayList<Persons>();
        personArray.add(personOne);
        personArray.add(personTwo);
        personArray.add(personThree);

        for (int i = 0; i < personArray.size(); i++){
            Assert.assertEquals(personArray.get(i), personResults.getPersonsArray().get(i));
        }

    }

    @Test
    public void allPersonsFail() throws SQLException //Finding no one under a user without any people
    {
        PersonDao personDao = new PersonDao();
        Persons personOne = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("no", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("1010", "false","jenny","F.","x",null,"yup",null);

        personDao.insertPerson(personOne);
        personDao.insertPerson(personTwo);
        personDao.insertPerson(personThree);
        personDao.insertPerson(personFour);

        PersonService personService = new PersonService();
        AllPersonResults personResults = personService.allPersons("10");

        Assert.assertNull(personResults.getPersonsArray());
        Assert.assertNotNull(personResults.getErrorMessage());
        Assert.assertEquals(personResults.getErrorMessage(), "There are no people under user");

    }
}