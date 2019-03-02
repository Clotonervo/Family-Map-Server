/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.serviceTests;

import models.Events;
import models.Persons;
import models.Users;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import requests.LoadRequest;
import results.LoadResult;
import services.ClearService;
import services.LoadService;

public class LoadServiceTest {

    @Before
    public void setUp()
    {
        ClearService clearService = new ClearService();
        clearService.clearDb();
    }

    @Test
    public void loadInfo()  //Loading test info into database
    {
        Events eventOne = new Events("yes", "no", "false", 1000, 4000,"m","tokyo", "death", 1969);
        Events eventTwo = new Events("no", "no", "can", 999, 3333,"stuff","yessir", "more death", 1900);
        Events eventThree = new Events("nope", "no", "yup", 494, 1029304,"not America","not New York", "birth", 1870);
        Events eventFour = new Events("1010", "yes", "whocares", 4293, 4059309,"Iraq","1234", "death", 1400);

        Persons personOne = new Persons("102-", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("1-39", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("2345", "yes","jenny","F.","x",null,"yup",null);

        Users userOne = new Users("yes", "no", "false", "john", "doe","m","1234");
        Users userTwo = new Users("no", "whe", "whawaha", "jack", "nabbit","m","54321");

        Events[] eventArray = new Events[] {eventOne, eventTwo, eventThree, eventFour};
        Persons[] personArray = new Persons[] {personOne, personTwo, personThree, personFour};
        Users[] userArray = new Users[] {userOne, userTwo};

        LoadRequest loadRequest = new LoadRequest(userArray, personArray, eventArray);
        LoadService loadService = new LoadService();
        LoadResult loadResult = loadService.loadInfo(loadRequest);

        Assert.assertEquals(loadResult.getResult(), "Successfully added 2 users, 4 persons, and 4 events to the database");

    }

    @Test
    public void loadFail() //Loading info where one of the events is null, expected failure
    {
        Events eventOne = new Events("yes", "no", "false", 1000, 4000,"m","tokyo", "death", 1969);
        Events eventTwo = new Events("no", "no", "can", 999, 3333,"stuff","yessir", "more death", 1900);
        Events eventThree = new Events("nope", "no", "yup", 494, 1029304,"not America","not New York", "birth", 1870);
        Events eventFour = new Events("1010", "yes", "whocares", 4293, 4059309,"Iraq","1234", "death", 1400);
        Events errorEvent = new Events();

        Persons personOne = new Persons("102-", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("1-39", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("2345", "false","jenny","F.","x",null,"yup",null);

        Users userOne = new Users("yes", "no", "false", "john", "doe","m","1234");
        Users userTwo = new Users("no", "whe", "whawaha", "jack", "nabbit","m","54321");

        Events[] eventArray = new Events[] {eventOne, eventTwo, eventThree, eventFour, errorEvent};
        Persons[] personArray = new Persons[] {personOne, personTwo, personThree, personFour};
        Users[] userArray = new Users[] {userOne, userTwo};

        LoadRequest loadRequest = new LoadRequest(userArray, personArray, eventArray);
        LoadService loadService = new LoadService();
        LoadResult loadResult = loadService.loadInfo(loadRequest);

        Assert.assertEquals(loadResult.getResult(), "Invalid input parameters");

    }
}