/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package results;

/** RegisterResult contains either:
 * A username,
 * a person id,
 * an authToken,
 * An error message if not successful
 */
public class RegisterResult {
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    // ========================== Constructors ========================================
    public RegisterResult(String token, String userName, String personID)
    {
        this.authToken = token;
        this.userName = userName;
        this.personID = personID;
        this.message = null;
    }

    public RegisterResult(String error)
    {
        authToken = null;
        userName = null;
        personID = null;
        message = error;
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
        return message;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.message = errorMessage;
    }
}
