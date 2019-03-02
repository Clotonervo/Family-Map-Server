/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import services.PersonService;
import results.AllPersonResults;
import results.SinglePersonResults;
import java.net.*;
import java.io.*;

/** PersonHandler handles person requests */
public class PersonHandler implements HttpHandler {

//______________________________________ Http Handler (Person) _________________________________________________
    /** handle handles (duh) the http requests and directs services
     * @param exchange http exchange object
     * @throws IOException if something goes wrong in reading files and such
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        Gson gson = new Gson();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    PersonService personService = new PersonService();

                    String authToken = reqHeaders.getFirst("Authorization");
                    String uRI = exchange.getRequestURI().toString();
                    String respData = "Invalid request";

                    if (uRI.equals("/person")){
                        AllPersonResults allResult = personService.allPersons(authToken);
                        respData = gson.toJson(allResult);
                    }
                    else if (uRI.substring(0,8).equals("/person/")){
                        SinglePersonResults singleResult = personService.singlePerson(uRI.substring(8), authToken);
                        respData = gson.toJson(singleResult);
                    }
                    else {
                        respData = "Invalid request";
                    }

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();

                    success = true;

                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    //---********************---- writes a string to an outputStream ---**********************----
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
