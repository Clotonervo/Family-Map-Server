/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */

package services;

import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import generators.DataGenerator;
import generators.GenerationData;
import models.Events;
import results.FillResult;
import models.Users;
import models.Persons;

import java.sql.SQLException;
import java.util.ArrayList;

/** FillService calls the DAOs in order to fill up the database with information, contains:
 * UserDao
 * PersonDao
 * EventDao
 */
public class FillService {

    private UserDao userDao = new UserDao();
    private PersonDao personDao = new PersonDao();
    private EventDao eventDao = new EventDao();

//______________________________________ Fill Database _________________________________________________
    /** fill fills the database with generated information
     * @param  username object containing information about filling the database
     * @return FillResult object with either an error message or a success message
     */
    public FillResult fill(String username, int numOfGenerations)
    {
        if (numOfGenerations <= 0){
            return new FillResult("Number of Generations invalid");
        }

        try {
            DataGenerator dataGenerator = new DataGenerator();
            Users currUser = userDao.findUser(username);
            if (currUser == null){
                return new FillResult("User not found!");
            }
            else if (!personDao.clearPersonInfoFromUser(currUser.getUserNameID()) ||
                    !eventDao.clearEventInfoFromUser(currUser.getUserNameID())){
                return new FillResult("Error when clearing Person and Event data");
            }
            else {

                GenerationData genData = dataGenerator.generateGenerations(numOfGenerations, setPerson(currUser));
                insertPersons(genData.getPersonsArray());
                insertEvents(genData.getEventsArray());

                return new FillResult("Successfully added " + genData.getPersonsArray().size() + " persons and "
                        + genData.getEventsArray().size() + " events to the database.");
            }
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
        return new FillResult("Error");
    }

    //---********************---- converts the user into a person object ---**********************----
    private Persons setPerson(Users user)
    {
        Persons userPerson = new Persons();
        userPerson.setDescendantID(user.getUserNameID());
        userPerson.setPersonFirstName(user.getUserFirstName());
        userPerson.setPersonLastName(user.getUserLastName());
        userPerson.setPersonGender(user.getUserGender());
        return userPerson;
    }

    //---********************---- inserts array of person objects into the database ---**********************----
    private void insertPersons (ArrayList<Persons> personArray) throws SQLException
    {
        for (int i = 0; i < personArray.size(); i++){
            personDao.insertPerson(personArray.get(i));
        }
    }

    //---********************---- inserts array of event objects into the database ---**********************----
    private void insertEvents (ArrayList<Events> eventsArray) throws SQLException
    {
        for (int k = 0; k < eventsArray.size(); k++){
            eventDao.insertEvent(eventsArray.get(k));
        }
    }
}
