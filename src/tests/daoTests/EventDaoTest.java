/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.daoTests;

import dao.EventDao;
import models.Events;
import org.junit.*;
import java.sql.*;
import java.util.ArrayList;

public class EventDaoTest {

    @After
    public void tearDown()
    {
        try {
            EventDao eventDao = new EventDao();
            eventDao.clearTables();
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void insertEvent() //Inserting a normal event
    {
        EventDao eventDao = new EventDao();
        Events testEvent = new Events("yes", "no", "false", 1000, 4000,"m","1234", "death", 1969);
        try {
            eventDao.initializeDatabase();
            eventDao.clearTables();
            Assert.assertTrue(eventDao.insertEvent(testEvent));
            Events initEvent = eventDao.findSingleEvent("yes");
            Assert.assertEquals(initEvent, testEvent);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test(expected = SQLException.class)
    public void insertEventFail() throws SQLException //Inserting an event that already exists (same eventID)
    {
        EventDao eventDao = new EventDao();
        Events testEvent = new Events("yes", "no", "false", 1000, 4000,"m","1234", "death", 1969);
        try {
            eventDao.initializeDatabase();
            eventDao.clearTables();
            Assert.assertTrue(eventDao.insertEvent(testEvent));
            eventDao.insertEvent(testEvent);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
            throw databaseError;
        }
    }

    @Test
    public void findSingleEvent() //Finding a simple, single event
    {
        EventDao eventDao = new EventDao();
        Events testEvent = new Events("yes", "no", "false", 1000.0, 4000.0,"m","1234", "death", 1969);
        try {
            eventDao.initializeDatabase();
            eventDao.clearTables();
            Assert.assertTrue(eventDao.insertEvent(testEvent));
            Events initEvent = eventDao.findSingleEvent("yes");
            Assert.assertEquals(initEvent, testEvent);
            eventDao.clearTables();
            Assert.assertNull(eventDao.findSingleEvent("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findSingleEventFail() //Trying to find an event that doesn't exist
    {
        EventDao eventDao = new EventDao();
        Events testEvent = new Events("yes", "no", "false", 1000, 4000,"m","1234", "death", 1969);
        try {
            eventDao.initializeDatabase();
            eventDao.clearTables();
            Assert.assertNull(eventDao.findSingleEvent("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findAllEvents() //Finding all simple events under a simple userID
    {
        EventDao eventDao = new EventDao();
        Events eventOne = new Events("yes", "no", "false", 1000, 4000,"m","tokyo", "death", 1969);
        Events eventTwo = new Events("no", "no", "can", 999, 3333,"stuff","yessir", "more death", 1900);
        Events eventThree = new Events("nope", "no", "yup", 494, 1029304,"not America","not New York", "birth", 1870);
        Events eventFour = new Events("1010", "yes", "whocares", 4293, 4059309,"Iraq","1234", "death", 1400);

        ArrayList<Events> eventsArray = new ArrayList<Events>();
        eventsArray.add(eventOne);
        eventsArray.add(eventTwo);
        eventsArray.add(eventThree);

        try {
            eventDao.initializeDatabase();
            eventDao.clearTables();
            Assert.assertTrue(eventDao.insertEvent(eventOne));
            Assert.assertTrue(eventDao.insertEvent(eventTwo));
            Assert.assertTrue(eventDao.insertEvent(eventThree));
            Assert.assertTrue(eventDao.insertEvent(eventFour));

            ArrayList<Events> eventsArrayFromDao = eventDao.findAllEvents("no");

            for (int i = 0; i < eventsArray.size(); i++) {
                Assert.assertEquals(eventsArray.get(i), eventsArrayFromDao.get(i));
            }
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findAllEventsFail() //Finding events under a userID, making sure it didn't pull others with it
    {
        EventDao eventDao = new EventDao();
        Events eventOne = new Events("yes", "no", "false", 1000, 4000,"m","tokyo", "death", 1969);
        Events eventTwo = new Events("no", "no", "can", 999, 3333,"stuff","yessir", "more death", 1900);
        Events eventThree = new Events("nope", "no", "yup", 494, 1029304,"not America","not New York", "birth", 1870);
        Events eventFour = new Events("1010", "yes", "whocares", 4293, 4059309,"Iraq","1234", "death", 1400);

        ArrayList<Events> eventsArray = new ArrayList<Events>();
        eventsArray.add(eventOne);
        eventsArray.add(eventTwo);
        eventsArray.add(eventFour);

        try {
            eventDao.initializeDatabase();
            eventDao.clearTables();
            Assert.assertTrue(eventDao.insertEvent(eventOne));
            Assert.assertTrue(eventDao.insertEvent(eventTwo));
            Assert.assertTrue(eventDao.insertEvent(eventThree));
            Assert.assertTrue(eventDao.insertEvent(eventFour));

            ArrayList<Events> eventsArrayFromDao = eventDao.findAllEvents("no");

            for (int i = 0; i < eventsArray.size(); i++) {
                if (eventsArray.get(i) == eventFour){
                    Assert.assertNotEquals(eventsArray.get(i), eventsArrayFromDao.get(i));
                }
                else {
                    Assert.assertEquals(eventsArray.get(i), eventsArrayFromDao.get(i));
                }
            }
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }
}