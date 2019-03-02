/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.daoTests;

import dao.PersonDao;
import dao.UserDao;
import org.junit.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import models.Persons;

public class PersonDaoTest {

    @After
    public void tearDown()
    {
        try {
            PersonDao personDao = new PersonDao();
            personDao.clearTables();
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void insertPerson() //Inserting a basic person object
    {
        PersonDao personDao = new PersonDao();
        Persons testPerson = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            Assert.assertTrue(personDao.insertPerson(testPerson));
            Persons initPer = personDao.findSinglePerson("yes");
            Assert.assertEquals(initPer, testPerson);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test(expected = SQLException.class)
    public void insertPersonFail() throws SQLException //Inserting a person object that already exists
    {
        PersonDao personDao = new PersonDao();
        Persons testPerson = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            personDao.insertPerson(testPerson);
            personDao.insertPerson(testPerson);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
            throw databaseError;
        }
    }

    @Test
    public void findSinglePerson() //Finding a basic person with a simple userID
    {
        PersonDao personDao = new PersonDao();
        Persons testPerson = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            Assert.assertTrue(personDao.insertPerson(testPerson));
            Persons initPer = personDao.findSinglePerson("yes");
            Assert.assertEquals(initPer, testPerson);
            personDao.clearTables();
            Assert.assertNull(personDao.findSinglePerson("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findSinglePersonFail() //Finding a person that doesn't exist
    {
        PersonDao personDao = new PersonDao();
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            Assert.assertNull(personDao.findSinglePerson("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findAllPersons() //Finding all people under a userID
    {
        PersonDao personDao = new PersonDao();
        Persons personOne = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("no", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("1010", "false","jenny","F.","x",null,"yup",null);

        ArrayList<Persons> currArray = new ArrayList<Persons>();
        currArray.add(personOne);
        currArray.add(personTwo);
        currArray.add(personThree);
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            Assert.assertTrue(personDao.insertPerson(personOne));
            Assert.assertTrue(personDao.insertPerson(personTwo));
            Assert.assertTrue(personDao.insertPerson(personThree));
            Assert.assertTrue(personDao.insertPerson(personFour));

            ArrayList<Persons> newPersonArrayFromDao = personDao.findAllPersons("no");
            for (int i = 0; i < currArray.size(); i++){
                Assert.assertEquals(newPersonArrayFromDao.get(i), currArray.get(i));
            }
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findAllPersonsFail() //Checking to see if it pulled other People not under the same userID
    {
        PersonDao personDao = new PersonDao();
        Persons personOne = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("no", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("1010", "false","jenny","F.","x",null,"yup",null);

        ArrayList<Persons> currArray = new ArrayList<Persons>();
        currArray.add(personOne);
        currArray.add(personTwo);
        currArray.add(personFour);
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            Assert.assertTrue(personDao.insertPerson(personOne));
            Assert.assertTrue(personDao.insertPerson(personTwo));
            Assert.assertTrue(personDao.insertPerson(personThree));
            Assert.assertTrue(personDao.insertPerson(personFour));

            ArrayList<Persons> newPersonArrayFromDao = personDao.findAllPersons("no");
            for (int i = 0; i < currArray.size(); i++){
                if (currArray.get(i) == personFour){
                    Assert.assertNotEquals(newPersonArrayFromDao.get(i), currArray.get(i));
                }
                else {
                    Assert.assertEquals(newPersonArrayFromDao.get(i), currArray.get(i));
                }
            }
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void clearPerson() //Test on clearing the person table
    {
        PersonDao personDao = new PersonDao();
        Persons testPerson = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            Assert.assertTrue(personDao.insertPerson(testPerson));
            Persons initP = personDao.findSinglePerson("yes");
            Assert.assertEquals(initP, testPerson);
            personDao.deletePersons();
            Assert.assertNull(personDao.findSinglePerson("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }
}