/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package generators;

import models.Persons;
import java.util.*;

/** DataGenerator is the class that dictates the generation of fake person and event data, contains:
 * Array of people
 * Name Generator Object
 * Location Generator Object
 * Event Generator Object
 * username
 * a randomizer
 */
public class DataGenerator {

    private ArrayList<Persons> familyTree;
    private NameGenerator nameGenerator = new NameGenerator();
    private LocationGenerator locationGenerator = new LocationGenerator();
    private EventGenerator eventGenerator;
    private String userName;
    private Random random = new Random();

//______________________________________ Start Generating Family Tree _________________________________________________
    /** generateGenerations starts the generation process
     * @param numOfGenerations integer of how many generations are wanted
     * @param userPerson person that is the root of the family tree
     * @return GenerationData object that contains the family tree and corresponding events
     */
    public GenerationData generateGenerations(int numOfGenerations, Persons userPerson)
    {
        userName = userPerson.getDescendantID();
        eventGenerator = new EventGenerator(userName);

        startFamilyTree(userPerson, numOfGenerations);

        return new GenerationData(familyTree, eventGenerator.getAllEvents());
    }

    //---********************---- starts the family tree recursion with the root person ---**********************----
    private void startFamilyTree(Persons rootPerson, int numOfGenerations)
    {
        familyTree = new ArrayList<Persons>();
        familyTree.add(rootPerson);
        int currYear = 2000;

        eventGenerator.createBirth(rootPerson, currYear);

        generateMomAndDad(rootPerson, numOfGenerations - 1, currYear);
    }

    //---********************---- generates a mom and dad and continues up family tree ---**********************----
    private void generateMomAndDad(Persons currPerson, int generation, int currYear)
    {
        int generationalGap = 40;
        currYear = currYear - generationalGap - random.nextInt(10);

        Persons mother = new Persons();
        Persons father = createFather(currPerson, mother.getPersonID());
        mother = createMother(mother, father);

        currPerson.setPersonFatherID(father.getPersonID());
        currPerson.setPersonMotherID(mother.getPersonID());

        lifeGenerator(mother, father, currYear);

        familyTree.add(mother);
        familyTree.add(father);

        if (generation != 0) {
            generateMomAndDad(mother, generation - 1, currYear);
            generateMomAndDad(father, generation - 1, currYear);
        }
    }

    //---********************---- creates a father person ---**********************----
    private Persons createFather(Persons child, String spouse)
    {
        Persons dad = new Persons();
        dad.setPersonFirstName(nameGenerator.generateMaleName());
        dad.setPersonGender("m");
        dad.setPersonSpouseID(spouse);
        dad.setDescendantID(userName);

        if (child.getPersonGender().equals("m")) {
            dad.setPersonLastName(child.getPersonLastName());
        }
        else {
            dad.setPersonLastName(nameGenerator.generateLastName());
        }

        return dad;
    }

    //---********************---- creates a mother person ---**********************----
    private Persons createMother(Persons mom, Persons husband)
    {
        mom.setPersonFirstName(nameGenerator.generateFemaleName());
        mom.setPersonLastName(nameGenerator.generateLastName());
        mom.setPersonSpouseID(husband.getPersonID());
        mom.setPersonGender("f");
        mom.setDescendantID(userName);
        return mom;
    }

    //---********************---- generates life events for each mother and father person ---**********************----
    private void lifeGenerator(Persons mother, Persons father, int currYear)
    {
        eventGenerator.createBirth(father, currYear);
        eventGenerator.createBirth(mother, currYear);
        eventGenerator.createMarriage(father, mother, currYear);

        int lifeWheel = random.nextInt(4);

        if (currYear < 1920){
            eventGenerator.createDeath(father, currYear);
            eventGenerator.createDeath(mother, currYear);
        }
        else if (lifeWheel == 0){
            eventGenerator.createDeath(father, currYear);
            eventGenerator.createRandomEvent(mother, currYear);
        }
        else if (lifeWheel == 1){
            eventGenerator.createDeath(mother, currYear);
            eventGenerator.createRandomEvent(father, currYear);
        }

        if (lifeWheel == 2){
            eventGenerator.createRandomEvent(father, currYear);
        }
        else if (lifeWheel == 3){
            eventGenerator.createRandomEvent(mother, currYear);
        }
    }

}
