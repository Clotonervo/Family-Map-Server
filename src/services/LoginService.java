/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package services;

import dao.AuthTokenDao;
import dao.UserDao;
import models.AuthorizationToken;
import requests.LoginRequest;
import results.LoginResult;
import models.Users;
import java.sql.SQLException;

/** LoginService contains methods relating to login command, accesses DAOs, contains:
 * AuthorizationToken
 * AuthTokenDao
 * UserDao
 * User
 */
public class LoginService {

    private AuthorizationToken authToken = new AuthorizationToken();
    private AuthTokenDao authTokenDao = new AuthTokenDao();
    private UserDao userDao = new UserDao();
    private Users userToFind;

//______________________________________ Login User _________________________________________________
    /** login logs a user in after checking for validity of information passed
     * @param logReq object containing login information
     * @return LoginResult object containing either user info if successful or error message if not
     */
    public LoginResult login(LoginRequest logReq)
    {
        try {
            if (!isLoginValid(logReq)) {
                return new LoginResult("Invalid input");
            }
            userToFind = userDao.findUser(logReq.getLoginUserName());

            if (userToFind == null) {
                return new LoginResult("User not found or does not exist");
            }
            else if (!userToFind.getUserPassword().equals(logReq.getLoginPassWord())) {
                return new LoginResult("Incorrect Password");
            }
            else {
                authToken.setUsername(logReq.getLoginUserName());
                authTokenDao.insertToken(authToken);
                return new LoginResult(authToken.getAuthToken(), userToFind.getUserNameID(), userToFind.getUserPersonID());
            }
        }
        catch (SQLException databaseError) {
            return new LoginResult("Internal Server Error");
        }
    }

    //---********************---- checks validity of login information ---**********************----
    private boolean isLoginValid(LoginRequest logReq)
    {
        return !(logReq.getLoginPassWord() == null || logReq.getLoginUserName() == null);
    }


}
