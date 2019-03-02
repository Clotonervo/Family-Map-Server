/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package services;

import dao.AuthTokenDao;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import models.*;
import requests.LoadRequest;
import results.LoadResult;
import java.sql.SQLException;

/** Contains methods about clearing then loading data into the database, contains:
 * AuthTokenDao
 * UserDao
 * PersonDao
 * EventDao
 * an Authorization Token object
 */
public class LoadService {
    private AuthTokenDao authTokenDao = new AuthTokenDao();
    private UserDao userDao = new UserDao();
    private PersonDao personDao = new PersonDao();
    private EventDao eventDao = new EventDao();
    private AuthorizationToken authToken;

//______________________________________ Load Information into Database ________________________________________________
    /** loadInfo
     * @param loadReq object with information to load into database
     * @return LoadResult object with error message or success message
     */
    public LoadResult loadInfo(LoadRequest loadReq)
    {
        try {
            if (!checkInputs(loadReq)){
                return new LoadResult("Invalid input parameters");
            }

            userDao.clearTables();
            String insertUsersResult = insertUserArray(loadReq.getUserArray());
            String insertPersonsResult = insertPersonsArray(loadReq.getPersonArray());
            String insertEventsResult = insertEventsArray(loadReq.getEventArray());

            if(!insertUsersResult.equals("Done!")){
                return new LoadResult(insertUsersResult);
            }
            else if (!insertPersonsResult.equals("Done!")){
                return new LoadResult(insertPersonsResult);
            }
            else if (!insertEventsResult.equals("Done!")){
                return new LoadResult(insertEventsResult);
            }
            else {
                return new LoadResult("Successfully added " +
                        loadReq.getUserArray().length + " users, " +
                        loadReq.getPersonArray().length + " persons, and " +
                        loadReq.getEventArray().length + " events to the database");
            }

        }
        catch (SQLException databaseError){
            return new LoadResult("Error: Database Error");
        }
    }

    //---********************-- inserts an array of users into database (and error checks) ---**********************--
    private String insertUserArray(Users[] userArray) throws SQLException
    {
        if (userArray.length == 0){
            return "No Users to Insert!";
        }

        for (int i = 0; i < userArray.length; i++){
            Users userTest = userDao.findUser(userArray[i].getUserNameID());
            if (userTest == null) {
                userDao.insertUser(userArray[i]);
                authToken = new AuthorizationToken(userArray[i].getUserNameID());
                authTokenDao.insertToken(authToken);
            }
            else {
                return "User already exists!";
            }
        }
        return "Done!";
    }

    //---********************-- inserts an array of people into database (and error checks) ---**********************--
    private String insertPersonsArray(Persons[] personArray) throws  SQLException
    {
        if (personArray.length == 0){
            return "No Persons to Insert!";
        }

        for (int j = 0; j < personArray.length; j++){
            Persons personTest = personDao.findSinglePerson(personArray[j].getPersonID());
            Users userTest = userDao.findUser(personArray[j].getDescendantID());
            if (userTest == null){
                return "User doesn't exist";
            }
            else if (personTest == null){
                personDao.insertPerson(personArray[j]);
            }
            else {
                return "One or more people already in database!";
            }
        }
        return "Done!";
    }

    //---********************-- inserts an array of events into database (and error checks) --**********************--
    private String insertEventsArray(Events[] eventsArray) throws SQLException
    {
        if (eventsArray.length == 0){
            return "No Events to Insert!";
        }

        for (int k = 0; k < eventsArray.length; k++){
            Events eventTest = eventDao.findSingleEvent(eventsArray[k].getEventID());
            Users userTest = userDao.findUser(eventsArray[k].getEventDescendantID());
            if (userTest == null){
                return "User doesn't exist";
            }
            else if (eventTest == null){
                eventDao.insertEvent(eventsArray[k]);
            }
            else {
                return "One or more events already in database!";
            }
        }
        return "Done!";
    }

    //---********************---- checks LoadRequest input to make sure its valid ---**********************----
    private boolean checkInputs(LoadRequest loadReq)
    {
        return (areUsersValid(loadReq.getUserArray()) &&
                arePersonsValid(loadReq.getPersonArray()) &&
                areEventsValid(loadReq.getEventArray()));
    }

    //---********************---- checks user array validity ---**********************----
    private boolean areUsersValid(Users[] userArray)
    {
        for (int i = 0; i < userArray.length; i++){
            Users currUser = userArray[i];
            if (currUser.getUserNameID() == null || currUser.getUserPassword() == null ||
                    currUser.getUserFirstName() == null || currUser.getUserLastName() == null ||
                    currUser.getUserEmail() == null || currUser.getUserGender() == null ||
                    currUser.getUserPersonID() == null) {

                return false;
            }
        }
        return true;
    }

    //---********************---- checks person array validity ---**********************----
    private boolean arePersonsValid(Persons[] personArray)
    {
        for (int j = 0; j < personArray.length; j++){
            Persons currPerson = personArray[j];
            if (currPerson.getPersonID() == null ||
                    currPerson.getDescendantID() == null ||
                    currPerson.getPersonFirstName() == null ||
                    currPerson.getPersonLastName() == null ||
                    currPerson.getPersonGender() == null) {

                return false;
            }
        }
        return true;
    }

    //---********************---- checks event validity ---**********************----
    private boolean areEventsValid(Events[] eventArray)
    {
        for (int k = 0; k < eventArray.length; k++){
            Events currEvent = eventArray[k];
            if (currEvent.getEventID() == null ||
                    currEvent.getEventDescendantID() == null ||
                    currEvent.getEventPersonID() == null ||
                    currEvent.getEventCity() == null ||
                    currEvent.getEventCountry() == null ||
                    currEvent.getEventType() == null) {

                return false;
            }
        }
        return true;
    }
}
