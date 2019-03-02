package results;

import models.Persons;

import java.util.ArrayList;

/** PersonResults is the result of the /person command and contains:
 * an list of Person objects under user
 * an error message string
 */
public class AllPersonResults {

    private ArrayList<Persons> data;
    private String errorMessage;

    // ========================== Constructors ========================================
    public AllPersonResults() {}

    public AllPersonResults(ArrayList<Persons> personsArray)
    {
        data = personsArray;
        errorMessage = null;
    }

    public AllPersonResults(String errors)
    {
        this.errorMessage = errors;
        this.data = null;
    }

    //_______________________________ Getters and Setters __________________________________________
    public ArrayList<Persons> getPersonsArray()
    {
        return data;
    }

    public void setPersonsArray(ArrayList<Persons> personsArray)
    {
        this.data = personsArray;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
