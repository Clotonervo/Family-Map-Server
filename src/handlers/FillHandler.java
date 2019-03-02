/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import results.FillResult;
import services.FillService;
import java.net.*;
import java.io.*;

/** FillHandler handles all fill requests */
public class FillHandler implements HttpHandler {

//______________________________________ Http Handler (Fill) _________________________________________________
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
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                FillService fillService = new FillService();

                String uRI = exchange.getRequestURI().toString();
                String respData = "Invalid request";
                FillResult fillRes = null;
                uRI = uRI.substring(6);
                if (uRI.contains("/")) {
                    int index = uRI.indexOf("/");
                    fillRes = fillService.fill(uRI.substring(0, index), Integer.parseInt(uRI.substring(index + 1)));
                    respData = gson.toJson(fillRes);

                }
                else {
                    int defaultNumOfGenerations = 4;
                    fillRes = fillService.fill(uRI, defaultNumOfGenerations);
                    respData = gson.toJson(fillRes);

                }

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
