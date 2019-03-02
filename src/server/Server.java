/* Family Map Server
Created by: Samuel Hopkins
sam.hopkins@blastsoccer.net
NetID: h0pkins3

BEFORE GRADING:
- I have my code separated into a few different categories to help maintain clean looking code and clarity
- All public functions and Classes are documented using Javadoc format, as well as another divider you will soon see
- All private functions are NOT documented using Javadoc, but are briefly described using distinct dividers
- Tests are not documented like rest of code, using instead single line comments to briefly describe the test
- For my brackets, I use a different style with methods than I do with any of the other brackets. I talked to Professor
    Rodham and he approved this as long as I was consistent
- I tried my best to make the test look readable due to the long lines of constructor code that goes into making bulks
    of data, but honestly I don't think it looks that good the way I did it. That being said, I was aiming for consistency,
    so that's what you get
- As far as spacing goes, I tried to do what I think looks good, so don't read too much into how I separate lines if you
    think it looks funky
 */

package server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handlers.*;

/** The main server.Server class. This class will take requests from the client and sort it into a number of different paths */
public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

//______________________________________ Main Server Function _________________________________________________
    /** main
     * @author    Sam Hopkins
     * @param     args (JSON file)
     * @return    A string (JSON file) of whether the command was successful
     * */
    public static void main(String[] args)
    {
        String portNumber = args[0];
        new Server().run(portNumber);
    }

    //---********************---- runs the server and receives requests ---**********************----
    private void run(String portNumber)
    {

        System.out.println("Initializing HTTP server.Server");

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        }
        catch (IOException inputException) {
            inputException.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new DefaultHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("server.Server started");
    }


}
