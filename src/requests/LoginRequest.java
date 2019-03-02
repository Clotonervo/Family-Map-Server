/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package requests;

/** Login Request is a request for an authToken from an existing user, contains:
 * Username
 * Password
 */
public class LoginRequest {

    private String userName;
    private String password;

    // ========================== Constructors ========================================
    public LoginRequest()
    {
        userName = null;
        password = null;
    }

    public LoginRequest(String loginUserName, String loginPassWord)
    {
        this.userName = loginUserName;
        this.password = loginPassWord;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getLoginUserName() {
        return userName;
    }

    public void setLoginUserName(String loginUserName) {
        this.userName = loginUserName;
    }

    public String getLoginPassWord() {
        return password;
    }

    public void setLoginPassWord(String loginPassWord) {
        this.password = loginPassWord;
    }
}
