/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import requests.LoadRequest;
import results.LoadResult;
import services.LoadService;
import java.net.*;
import java.io.*;

/** LoadHandler handles all load requests, contains
 * InStreamToString object
 */
public class LoadHandler implements HttpHandler {

    private InStreamToString inStreamToString = new InStreamToString();

//______________________________________ Http Handler (Load) _________________________________________________
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
                LoadService loadService = new LoadService();
                String body = inStreamToString.convertStreamToString(exchange.getRequestBody());

                LoadRequest loadReq = gson.fromJson(body, LoadRequest.class);

                LoadResult loadResult = loadService.loadInfo(loadReq);

                String respData = gson.toJson(loadResult);

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
