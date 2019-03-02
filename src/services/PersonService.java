/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package services;

import dao.AuthTokenDao;
import dao.PersonDao;
import models.AuthorizationToken;
import results.AllPersonResults;
import results.SinglePersonResults;
import models.Persons;
import java.sql.SQLException;
import java.util.ArrayList;

/** PersonService class contains functions to call the DAO with different commmands regarding people, contains
 * AuthTokenDao
 * PersonDao
 */
public class PersonService {

    private AuthTokenDao authTokenDao = new AuthTokenDao();
    private PersonDao personDao = new PersonDao();

//______________________________________ Find Single Person _________________________________________________
    /** singlePerson
     * @param personID personID which is the id of a person and an AuthToken object
     * @param authToken that will be determined as valid or not and identify the user
     * @return PersonResults object that contains a single person or an error message
     */
    public SinglePersonResults singlePerson(String personID, String authToken)
    {
        try {
            AuthorizationToken checkedAuthToken = authTokenDao.checkToken(authToken);

            if (checkedAuthToken == null){
                return new SinglePersonResults("Invalid Authorization Token");
            }
            else {
                Persons foundPerson = personDao.findSinglePerson(personID);
                if (foundPerson == null){
                    return new SinglePersonResults("Person not found");
                }
                else if (!checkedAuthToken.getUsername().equals(foundPerson.getDescendantID())){
                    return new SinglePersonResults("Person not registered under current user");
                }
                else {
                    SinglePersonResults resultToReturn = new SinglePersonResults(foundPerson);
                    return resultToReturn;
                }
            }
        }
        catch (SQLException databaseError){
            return new SinglePersonResults("Error in locating person");
        }
    }

//______________________________________ Find All People _________________________________________________
    /** allPerson
     * @param  authToken AuthToken object that will be used to identify the user
     * @return PersonResults object that contains a all persons under said user or an error message
     */
    public AllPersonResults allPersons(String authToken)
    {
        try {
            AuthorizationToken checkedAuthToken = authTokenDao.checkToken(authToken);

            if (checkedAuthToken == null){
                return new AllPersonResults("Invalid Authorization Token");
            }
            else {
                ArrayList allFoundPersons = personDao.findAllPersons(checkedAuthToken.getUsername());

                if (allFoundPersons == null){
                    return new AllPersonResults("There are no people under user");
                }
                else {
                    AllPersonResults resultToReturn = new AllPersonResults(allFoundPersons);
                    return resultToReturn;
                }
            }
        }
        catch (SQLException databaseError){
            return new AllPersonResults("Error in locating all people");
        }
    }

}
