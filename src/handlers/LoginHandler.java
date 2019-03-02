/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package handlers;

import com.sun.net.httpserver.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;
import requests.LoginRequest;
import results.LoginResult;
import services.LoginService;

/** LoginHandler handles login requests, contains:
 * An InStreamToString object to make the inputStream a string
 */
public class LoginHandler implements HttpHandler {

    private InStreamToString inStreamToString = new InStreamToString();

//______________________________________ Http Handler (Login) _________________________________________________
    /** handle handles (duh) the http requests and directs services
     * @param exchange http exchange object
     * @throws IOException if something goes wrong in reading files and such
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;

        try {
            Gson gson = new Gson();
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream bodyInput = exchange.getRequestBody();
                String body = inStreamToString.convertStreamToString(bodyInput);
                LoginRequest logReq = gson.fromJson(body, LoginRequest.class);
                JsonObject json = gson.fromJson(body, JsonObject.class);

                LoginService service = new LoginService();
                LoginResult logResult = service.login(logReq);

                String respData = gson.toJson(logResult);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();

                success = true;
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

    //---********************---- writes a string to an outputStream ---**********************----
    private void writeString(String str, OutputStream outStream) throws IOException
    {
        OutputStreamWriter streamWriter = new OutputStreamWriter(outStream);
        streamWriter.write(str);
        streamWriter.flush();
    }
}
