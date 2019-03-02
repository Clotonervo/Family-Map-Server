/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package results;

import models.Events;
import java.util.ArrayList;

/** AllEventResults is the result of the /event command and contains:
 * a list of all events under the user,
 * an error message string
 */
public class AllEventResults {

    private ArrayList<Events> data;
    private String message;

    // ========================== Constructors ========================================
    public AllEventResults()
    {
        this.data = null;
        this.message = null;
    }

    public AllEventResults(ArrayList<Events> eList)
    {
        this.data = eList;
        this.message = null;
    }

    public AllEventResults(String errors)
    {
        this.message = errors;
        this.data = null;
    }


    //_______________________________ Getters and Setters __________________________________________
    public ArrayList<Events> getEventsArray()
    {
        return data;
    }

    public void setPersonsArray(ArrayList<Events> eventsArray)
    {
        this.data = eventsArray;
    }

    public String getErrorMessage()
    {
        return message;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.message = errorMessage;
    }

}
