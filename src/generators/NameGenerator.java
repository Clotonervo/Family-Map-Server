/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package generators;

import com.google.gson.*;
import java.io.*;
import java.util.Random;

/** NameGenerator class is used to pull random names from files and use them in generating the family tree */
public class NameGenerator {

//______________________________________ Generate Random Male Name _________________________________________________
    /** generateMaleName generates a random male name from mnames.json
     * @return string that is a random male name
     */
    public String generateMaleName()
    {
        Random rand = new Random();

        try {
            FileReader fileReader = new FileReader(new File("/Users/samhopkins/Desktop/FMSServer/json/mnames.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObject = (JsonObject) jsonParser.parse(fileReader);
            JsonArray nameArray = (JsonArray) rootObject.get("data");

            int index = rand.nextInt(nameArray.size());
            String nameM = nameArray.get(index).toString();
            nameM = nameM.substring(1, nameM.length() - 1);
            return nameM;
        }
        catch (FileNotFoundException fileNotFound){
            fileNotFound.printStackTrace();
        }

        return "Error";
    }

//______________________________________ Generate Random Female Name _________________________________________________
    /** generateFemaleName generates a random female name from fnames.json
     * @return string that is a random female name
     */
    public String generateFemaleName()
    {
        Random rand = new Random();

        try {
            FileReader fileReader = new FileReader(new File("/Users/samhopkins/Desktop/FMSServer/json/fnames.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObject = (JsonObject) jsonParser.parse(fileReader);
            JsonArray nameArray = (JsonArray) rootObject.get("data");

            int index = rand.nextInt(nameArray.size());
            String nameF = nameArray.get(index).toString();
            nameF = nameF.substring(1, nameF.length() - 1);
            return nameF;
        }
        catch (FileNotFoundException fileNotFound){
            fileNotFound.printStackTrace();
        }

        return "Error";
    }

//______________________________________ Generate Random Last Name _________________________________________________
    /** generateLastName generates a random last name from snames.json
     * @return string that is a random last name
     */
    public String generateLastName()
    {
        Random rand = new Random();

        try {
            FileReader fileReader = new FileReader(new File("/Users/samhopkins/Desktop/FMSServer/json/snames.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObject = (JsonObject) jsonParser.parse(fileReader);
            JsonArray nameArray = (JsonArray) rootObject.get("data");

            int index = rand.nextInt(nameArray.size());
            String nameLast = nameArray.get(index).toString();
            nameLast = nameLast.substring(1, nameLast.length() - 1);
            return nameLast;
        }
        catch (FileNotFoundException fileNotFound){
            fileNotFound.printStackTrace();
        }

        return "Error";
    }
}
