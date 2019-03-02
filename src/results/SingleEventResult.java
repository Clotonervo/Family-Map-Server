/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package results;

import models.Events;

/** SingleEventResult is the result of the /event/[event-ID] command, contains:
 * a userID that is a descendant,
 * a personID of the person who experienced the event,
 * A latitude and longitude to identify the location of the event,
 * a country and city of the event,
 * a year in which the event happened,
 * what the event was,
 * an error message if something went wrong
 */
public class SingleEventResult {

    private String eventID;
    private String descendant;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    private String message;

    // ========================== Constructors ========================================
    public SingleEventResult(String error)
    {
        message = error;
    }

    public SingleEventResult(Events events, String userName)
    {
        eventID = events.getEventID();
        descendant = userName;
        personID = events.getEventPersonID();
        latitude = events.getEventLatitude();
        longitude = events.getEventLongitude();
        country = events.getEventCountry();
        city = events.getEventCity();
        eventType = events.getEventType();
        year = events.getEventYear();
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getEventID()
    {
        return eventID;
    }

    public void setEventID(String eventID)
    {
        this.eventID = eventID;
    }

    public String getDescendant()
    {
        return descendant;
    }

    public void setDescendant(String descendant)
    {
        this.descendant = descendant;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public String getErrorMessage()
    {
        return message;
    }

    public void setErrorMessage(String message)
    {
        this.message = message;
    }
}
