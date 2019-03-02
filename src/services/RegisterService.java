/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package services;

import dao.*;
import generators.DataGenerator;
import generators.GenerationData;
import models.*;
import requests.RegisterRequest;
import results.RegisterResult;
import java.sql.SQLException;
import java.util.ArrayList;

/** RegisterService has all methods that are required to register a new user, contains
 * UserDao
 * PersonDao
 * EventDao
 * AuthTokenDao
 * AuthorizationToken
 * User
 * Person
 * DataGenerator
 * int defaultNumOfGenerations which is the default number of generations when generating a family tree
 */
public class RegisterService {

    private UserDao userDao = new UserDao();
    private PersonDao personDao = new PersonDao();
    private EventDao eventDao = new EventDao();
    private AuthTokenDao authTokenDao = new AuthTokenDao();
    private AuthorizationToken authToken = new AuthorizationToken();
    private Users userToRegister = new Users();
    private Persons userPerson = new Persons();
    private DataGenerator dataGenerator = new DataGenerator();
    private int defaultNumOfGenerations = 4;

//______________________________________ Register a New User _________________________________________________
    /** registerNewUser
     * @param  regReq object with info in it about registration
     * @return RegisterResult object with info or error message
     */
    public RegisterResult registerNewUser(RegisterRequest regReq)
    {
        if (!isInputValid(regReq)){
            return new RegisterResult("Invalid Input");
        }
        setUserAndPerson(regReq);

        try {
            Users userTest = userDao.findUser(userToRegister.getUserNameID());

            if (userTest == null) {
                userDao.insertUser(userToRegister);
                authToken = new AuthorizationToken(userToRegister.getUserNameID());
                authTokenDao.insertToken(authToken);

                GenerationData generationData = dataGenerator.generateGenerations(defaultNumOfGenerations, userPerson);

                insertPersons(generationData.getPersonsArray());
                insertEvents(generationData.getEventsArray());

                RegisterResult regResult = new RegisterResult(authToken.getAuthToken(),
                        userToRegister.getUserNameID(), userPerson.getPersonID());

                return regResult;
            }
            else {
                return new RegisterResult("Username already exists!");
            }

        }
        catch (SQLException databaseError){
            return new RegisterResult(databaseError.toString());
        }
    }

    //---********************---- checks to see if register input is valid ---**********************----
    private boolean isInputValid (RegisterRequest regReq)
    {
        return !((regReq.getUserNameID() == null) ||
                (regReq.getUserEmail() == null) ||
                (regReq.getUserFirstName() == null) ||
                (regReq.getUserLastName() == null) ||
                (regReq.getUserPassword() == null) ||
                (regReq.getUserGender() == null));
    }

    //---********************---- turns the register request into a user and person object ---**********************----
    private void setUserAndPerson(RegisterRequest regReq)
    {
        userToRegister.setUserNameID(regReq.getUserNameID());
        userToRegister.setUserPassword(regReq.getUserPassword());
        userToRegister.setUserEmail(regReq.getUserEmail());
        userToRegister.setUserFirstName(regReq.getUserFirstName());
        userToRegister.setUserLastName(regReq.getUserLastName());
        userToRegister.setUserGender(regReq.getUserGender());
        userToRegister.setUserPersonID(userPerson.getPersonID());

        userPerson.setDescendantID(userToRegister.getUserNameID());
        userPerson.setPersonFirstName(userToRegister.getUserFirstName());
        userPerson.setPersonLastName(userToRegister.getUserLastName());
        userPerson.setPersonGender(userToRegister.getUserGender());
    }

    //---********************---- inserts an array of people into database ---**********************----
    private void insertPersons (ArrayList<Persons> personArray) throws SQLException
    {
        for (int i = 0; i < personArray.size(); i++){
            personDao.insertPerson(personArray.get(i));
        }
    }

    //---********************---- inserts an array of events into database ---**********************----
    private void insertEvents (ArrayList<Events> eventsArray) throws SQLException
    {
        for (int k = 0; k < eventsArray.size(); k++){
            eventDao.insertEvent(eventsArray.get(k));
        }
    }


}
