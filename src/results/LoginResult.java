/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */

package results;

/** LoginResult returns with either a:
 * username,
 * person ID,
 * an authtoken if successful
 * an error message if not successful
 */
public class LoginResult {

    private String authToken;
    private String userName;
    private String personID;
    private String errorMessage;

    // ========================== Constructors ========================================
    public LoginResult(String token, String userName, String personID)
    {
        this.authToken = token;
        this.userName = userName;
        this.personID = personID;
        this.errorMessage = null;
    }

    public LoginResult(String error)
    {
        authToken = null;
        userName = null;
        personID = null;
        errorMessage = error;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getToken()
    {
        return authToken;
    }

    public void setToken(String token)
    {
        this.authToken = token;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
