/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package generators;

import java.util.ArrayList;
import models.Persons;
import models.Events;

/** GenerationData class contains both the family tree and the list of events for each member in that tree, contains:
 * family tree
 * events for every member of family tree
 */
public class GenerationData {

    private ArrayList<Persons> personsArray;
    private ArrayList<Events> eventsArray;

    // ========================== Constructors ========================================
    public GenerationData(ArrayList<Persons> personsArray, ArrayList<Events> eventsArray)
    {
        this.personsArray = personsArray;
        this.eventsArray = eventsArray;
    }

    public GenerationData()
    {
        personsArray = null;
        eventsArray = null;
    }

    //_______________________________ Getters and Setters __________________________________________

    public ArrayList<Persons> getPersonsArray()
    {
        return personsArray;
    }

    public void setPersonsArray(ArrayList<Persons> personsArray)
    {
        this.personsArray = personsArray;
    }

    public ArrayList<Events> getEventsArray()
    {
        return eventsArray;
    }

    public void setEventsArray(ArrayList<Events> eventsArray)
    {
        this.eventsArray = eventsArray;
    }
}
