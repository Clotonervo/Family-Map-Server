/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package generators;

import com.google.gson.*;
import models.Events;
import models.Persons;
import java.io.*;
import java.util.*;

/** Event Generator deals with all functions that are needed to generate random events, contains:
 * Location generator object
 * username id
 * arrayList of events
 * randomizer
 */
public class EventGenerator {
    private LocationGenerator locationGenerator = new LocationGenerator();
    private Random random = new Random();
    private String userName;
    private ArrayList<Events> allEvents;

// ========================== Constructors ========================================
    public EventGenerator(String usernameInput)
    {
        userName = usernameInput;
        allEvents = new ArrayList<Events>();
    }

    public ArrayList<Events> getAllEvents()
    {
        return allEvents;
    }

//______________________________________ Generate Random Event Type _________________________________________________
    /** generateEventType generates a random event from eventtypes.json
     * @return String that is a randomly generated event type
     */
    public String generateEventType()
    {
        Random rand = new Random();
        String eventTypeToReturn;

        try {
            FileReader fileReader = new FileReader(new File("/Users/samhopkins/IdeaProjects/FamilyMapServer/res/json/eventtypes.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObject = (JsonObject) jsonParser.parse(fileReader);
            JsonArray eventTypeArray = (JsonArray) rootObject.get("data");

            int index = rand.nextInt(eventTypeArray.size());
            eventTypeToReturn = eventTypeArray.get(index).toString();
            eventTypeToReturn = eventTypeToReturn.substring(1, eventTypeToReturn.length() -1);

            return eventTypeToReturn;
        }
        catch (FileNotFoundException fileNotFound) {
            fileNotFound.printStackTrace();
        }

        return "null";
    }

//______________________________________ Create Marriage Event _________________________________________________
    /** createMarraige creates a marriage event between too people and puts it in an arraylist
     * @param husband husband person, male in marriage
     * @param wife wife person, female in marriage
     * @param currYear year so that the marriage corresponds with the generation and with both people
     */
    public void createMarriage(Persons husband, Persons wife, int currYear)
    {
        int yearOfMarriage = 0;
        yearOfMarriage = currYear + random.nextInt(6) + 22;

        Events currMarriage = locationGenerator.generateLoc();
        currMarriage.setEventDescendantID(userName);
        currMarriage.setEventType("Marriage");
        currMarriage.setEventYear(yearOfMarriage);
        currMarriage.setEventPersonID(husband.getPersonID());

        Events marriageDup = new Events();
        marriageDup.setEventPersonID(wife.getPersonID());
        marriageDup.setEventType("Marriage");
        marriageDup.setEventDescendantID(userName);
        marriageDup.setEventYear(yearOfMarriage);
        marriageDup.setEventLongitude(currMarriage.getEventLongitude());
        marriageDup.setEventLatitude(currMarriage.getEventLatitude());
        marriageDup.setEventCity(currMarriage.getEventCity());
        marriageDup.setEventCountry(currMarriage.getEventCountry());

        allEvents.add(currMarriage);
        allEvents.add(marriageDup);
    }

//______________________________________ Create Death Event _________________________________________________
    /** createDeath creates a death even for a person
     * @param currPerson person who dies
     * @param currYear year used to keep deaths within a generation
     */
    public void createDeath(Persons currPerson, int currYear)
    {
        Events death = locationGenerator.generateLoc();
        int averageLifespan = 30;

        int yearOfDeath = 0;
        yearOfDeath = currYear + averageLifespan + random.nextInt(50);

        if (yearOfDeath > 2018){
            yearOfDeath = 2018;
        }

        death.setEventPersonID(currPerson.getPersonID());
        death.setEventType("Death");
        death.setEventDescendantID(userName);
        death.setEventYear(yearOfDeath);

        allEvents.add(death);
    }

//______________________________________ Create Birth Event _________________________________________________
    /** createBirth creates a birth event for a person
     * @param currPerson person who is born
     * @param currYear year used to help keep birth within a generation
     */
    public void createBirth(Persons currPerson, int currYear)
    {
        Events birth = locationGenerator.generateLoc();

        int yearOfBirth = 0;
        yearOfBirth = currYear - random.nextInt(10);

        birth.setEventPersonID(currPerson.getPersonID());
        birth.setEventType("Birth");
        birth.setEventDescendantID(userName);
        birth.setEventYear(yearOfBirth);

        allEvents.add(birth);

    }

//______________________________________ Create Random Event _________________________________________________
    /** createRandomEvent creates a random event for a person
     * @param currPerson person who passes through event
     * @param currYear year used to help keep event within the lifetime/generation
     */
    public void createRandomEvent(Persons currPerson, int currYear)
    {
        int yearsUntilLifeHappens = 10;
        int yearOfEvent = currYear + yearsUntilLifeHappens + random.nextInt(20);

        Events randomEvent = locationGenerator.generateLoc();
        randomEvent.setEventYear(yearOfEvent);
        randomEvent.setEventType(generateEventType());
        randomEvent.setEventDescendantID(userName);
        randomEvent.setEventPersonID(currPerson.getPersonID());

        allEvents.add(randomEvent);
    }
}
