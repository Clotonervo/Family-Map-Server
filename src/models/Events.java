/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package models;

import java.util.Objects;
import java.util.UUID;

/** Events class contains information of events such as: unique event Id,
 * a user that is a descendant,
 * a person to which the event happened
 * A latitude and longitude to identify the location of the event
 * a country and city of the event
 * a year in which the event happened
 * and what the event was
 */
public class Events {

    private String eventID;
    private String descendant;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    // ========================== Constructors ========================================
    public Events()
    {
        this.eventID = UUID.randomUUID().toString();
        this.descendant = null;
        this.personID = null;
        this.latitude = 0;
        this.longitude = 0;
        this.country = null;
        this.city = null;
        this.eventType = null;
        this.year = 0;
    }

    public Events(String eventID, String eventDescendantID, String eventPersonID, double eventLatitude,
                  double eventLongitude, String eventCountry, String eventCity, String eventType, int eventYear)
    {
        this.eventID = eventID;
        this.descendant = eventDescendantID;
        this.personID = eventPersonID;
        this.latitude = eventLatitude;
        this.longitude = eventLongitude;
        this.country = eventCountry;
        this.city = eventCity;
        this.eventType = eventType;
        this.year = eventYear;
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

    public String getEventDescendantID()
    {
        return descendant;
    }

    public void setEventDescendantID(String eventDescendantID)
    {
        this.descendant = eventDescendantID;
    }

    public String getEventPersonID()
    {
        return personID;
    }

    public void setEventPersonID(String eventPersonID)
    {
        this.personID = eventPersonID;
    }

    public double getEventLatitude()
    {
        return latitude;
    }

    public void setEventLatitude(double eventLatitude)
    {
        this.latitude = eventLatitude;
    }

    public double getEventLongitude()
    {
        return longitude;
    }

    public void setEventLongitude(double eventLongitude)
    {
        this.longitude = eventLongitude;
    }

    public String getEventCountry()
    {
        return country;
    }

    public void setEventCountry(String eventCountry)
    {
        this.country = eventCountry;
    }

    public String getEventCity()
    {
        return city;
    }

    public void setEventCity(String eventCity)
    {
        this.city = eventCity;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public int getEventYear()
    {
        return year;
    }

    public void setEventYear(int eventYear)
    {
        this.year = eventYear;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Events events = (Events) o;
        return Double.compare(events.latitude, latitude) == 0 && Double.compare(events.longitude, longitude) == 0 &&
                year == events.year && Objects.equals(eventID, events.eventID) &&
                Objects.equals(descendant, events.descendant) && Objects.equals(personID, events.personID) &&
                Objects.equals(country, events.country) && Objects.equals(city, events.city) &&
                Objects.equals(eventType, events.eventType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(eventID, descendant, personID, latitude, longitude, country, city, eventType, year);
    }
}
