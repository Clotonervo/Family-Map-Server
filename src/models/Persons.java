/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package models;

import java.util.Objects;
import java.util.UUID;

/** Persons class, contains information for a person such as:
 * Username who is a Descendant of said person
 * First and last name
 * Gender of person
 * Father person ID (optional)
 * Mother person ID (optional)
 * Spouse person ID (optional)
 */
public class Persons {

    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

// ========================== Constructors ========================================
    public Persons()
    {
        this.personID = UUID.randomUUID().toString();
        this.descendant = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.father = null;
        this.mother = null;
        this.spouse = null;
    }

    public Persons(String personID, String descendantID, String personFirstName, String personLastName,
                   String personGender, String personFatherID, String personMotherID, String personSpouseID)
    {
        this.personID = personID;
        this.descendant = descendantID;
        this.firstName = personFirstName;
        this.lastName = personLastName;
        this.gender = personGender;
        this.father = personFatherID;
        this.mother = personMotherID;
        this.spouse = personSpouseID;
    }

    //_______________________________ Getters and Setters __________________________________________

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public String getDescendantID()
    {
        return descendant;
    }

    public void setDescendantID(String descendantID)
    {
        this.descendant = descendantID;
    }

    public String getPersonFirstName()
    {
        return firstName;
    }

    public void setPersonFirstName(String personFirstName)
    {
        this.firstName = personFirstName;
    }

    public String getPersonLastName()
    {
        return lastName;
    }

    public void setPersonLastName(String personLastName)
    {
        this.lastName = personLastName;
    }

    public String getPersonGender()
    {
        return gender;
    }

    public void setPersonGender(String personGender)
    {
        this.gender = personGender;
    }

    public String getPersonFatherID()
    {
        return father;
    }

    public void setPersonFatherID(String personFatherID)
    {
        this.father = personFatherID;
    }

    public String getPersonMotherID()
    {
        return mother;
    }

    public void setPersonMotherID(String personMotherID)
    {
        this.mother = personMotherID;
    }

    public String getPersonSpouseID()
    {
        return spouse;
    }

    public void setPersonSpouseID(String personSpouseID)
    {
        this.spouse = personSpouseID;
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
        Persons persons = (Persons) o;
        return personID.equals(persons.personID) && descendant.equals(persons.descendant) &&
                firstName.equals(persons.firstName) && lastName.equals(persons.lastName) &&
                gender.equals(persons.gender) && Objects.equals(father, persons.father) &&
                Objects.equals(mother, persons.mother) && Objects.equals(spouse, persons.spouse);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(personID, descendant, firstName, lastName, gender, father, mother, spouse);
    }
}
