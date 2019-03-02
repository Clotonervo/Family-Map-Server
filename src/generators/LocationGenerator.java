/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package generators;

import com.google.gson.*;
import models.Events;
import java.io.*;
import java.util.Random;

/** Location Generator's sole purpose is to return an event with a random location loaded from locations.json */
public class LocationGenerator {

//______________________________________ Generate Random Event Location ________________________________________________
    /** generateLoc parses locations.json and supplies a random location
     * @return Event object with random location variables loaded
     */
    public Events generateLoc()
    {
        Random rand = new Random();
        Events eventWithLocation = new Events();

        try {
            FileReader fileReader = new FileReader(new File("/Users/samhopkins/Desktop/FMSServer/json/locations.json"));
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObject = (JsonObject) jsonParser.parse(fileReader);
            JsonArray locArray = (JsonArray) rootObject.get("data");

            int index = rand.nextInt(locArray.size());
            JsonObject currLocation = (JsonObject)locArray.get(index);

            String city = currLocation.get("city").toString()
                    .substring(1, currLocation.get("city").toString().length() - 1);
            String country = currLocation.get("country")
                    .toString().substring(1, currLocation.get("country").toString().length() - 1);

            eventWithLocation.setEventCity(city);
            eventWithLocation.setEventCountry(country);
            eventWithLocation.setEventLatitude(currLocation.get("latitude").getAsDouble());
            eventWithLocation.setEventLongitude(currLocation.get("longitude").getAsDouble());

            return eventWithLocation;
        }
        catch (FileNotFoundException fileNotFound){
            fileNotFound.printStackTrace();
        }

        return new Events();
    }
}
