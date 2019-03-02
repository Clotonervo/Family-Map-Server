/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package handlers;

import com.sun.net.httpserver.*;
import results.AllEventResults;
import results.SingleEventResult;
import services.EventService;
import java.net.*;
import java.io.*;
import com.google.gson.*;

/** EventHandler handles all event commands that are requested */
public class EventHandler implements HttpHandler {

//______________________________________ Http Handler (Event) _________________________________________________
    /** handle handles (duh) the http requests and directs services
     * @param exchange http exchange object
     * @throws IOException if something goes wrong in reading files and such
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {

                    String authToken = reqHeaders.getFirst("Authorization");
                    String uRI = exchange.getRequestURI().toString();
                    String respData;

                    respData = directPath(uRI, authToken);

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
        catch (IOException inputException) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            inputException.printStackTrace();
        }
    }

    private String directPath(String command, String authToken)
    {
        String respData;
        EventService eventService = new EventService();
        Gson gson = new Gson();

        if (command.equals("/event")){
            AllEventResults allResult = eventService.allEvents(authToken);
            if (!(allResult.getErrorMessage() == null)){
                respData = "{ \"message\": \"" + allResult.getErrorMessage() + "\"}";
            }
            else {
                respData = gson.toJson(allResult);
            }
        }
        else if (command.substring(0,7).equals("/event/")){
            SingleEventResult singleResult = eventService.singleEvent(command.substring(7), authToken);
            if (!(singleResult.getErrorMessage() == null)){
                respData = "{ \"message\": \"" + singleResult.getErrorMessage() + "\"}";
            }
            else {
                respData = gson.toJson(singleResult);
            }
        }
        else {
            respData = "Invalid request";
        }

        return respData;
    }

    //---********************---- writes a string to an outputStream ---**********************----
    private void writeString(String str, OutputStream outStream) throws IOException
    {
        OutputStreamWriter streamWriter = new OutputStreamWriter(outStream);
        streamWriter.write(str);
        streamWriter.flush();
    }

}
