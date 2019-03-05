/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package handlers;

import com.sun.net.httpserver.*;
import java.net.*;
import java.io.*;
import java.nio.file.*;

/** DefaultHandler handles all default requests, such as finding the index.html or supplying other requested files */
public class DefaultHandler implements HttpHandler {

//______________________________________ Http Handler (Default) _________________________________________________
    /** handle handles (duh) the http requests and directs services
     * @param exchange http exchange object
     * @throws IOException if something goes wrong in reading files and such
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try {

            String filePathStr;
            String uRI = exchange.getRequestURI().toString();

            if (uRI.equals("/")) {
                filePathStr = "/Users/samhopkins/IdeaProjects/FamilyMapServer/res/web/index.html";
            }
            else {
                filePathStr = "/Users/samhopkins/IdeaProjects/FamilyMapServer/res/web/" + uRI;
            }

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            Path filePath = FileSystems.getDefault().getPath(filePathStr);

            Files.copy(filePath, respBody);
            respBody.close();

        }
        catch (IOException inputException) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            inputException.printStackTrace();
        }
    }

}
