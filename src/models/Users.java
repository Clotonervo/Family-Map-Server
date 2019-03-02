/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package models;

/** Users class is a model class that contains the information such as
 * username of user
 * password of user
 * email of user
 * first and last name of user
 * gender of user
 * and a person Id that identifies them
 */
public class Users {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    // ========================== Constructors ========================================
    public Users()
    {
        this.userName = null;
        this.password = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.personID = null;
    }

    public Users(String userNameID, String userPassword, String userEmail, String userFirstName,
                 String userLastName, String userGender, String userPersonID)
    {
        this.userName = userNameID;
        this.password = userPassword;
        this.email = userEmail;
        this.firstName = userFirstName;
        this.lastName = userLastName;
        this.gender = userGender;
        this.personID = userPersonID;
    }

    //_______________________________ Getters and Setters __________________________________________

    public String getUserNameID()
    {
        return userName;
    }

    public void setUserNameID(String userNameID)
    {
        this.userName = userNameID;
    }

    public String getUserPassword()
    {
        return password;
    }

    public void setUserPassword(String userPassword)
    {
        this.password = userPassword;
    }

    public String getUserEmail()
    {
        return email;
    }

    public void setUserEmail(String userEmail)
    {
        this.email = userEmail;
    }

    public String getUserFirstName()
    {
        return firstName;
    }

    public void setUserFirstName(String userFirstName)
    {
        this.firstName = userFirstName;
    }

    public String getUserLastName()
    {
        return lastName;
    }

    public void setUserLastName(String userLastName)
    {
        this.lastName = userLastName;
    }

    public String getUserGender()
    {
        return gender;
    }

    public void setUserGender(String userGender)
    {
        this.gender = userGender;
    }

    public String getUserPersonID()
    {
        return personID;
    }

    public void setUserPersonID(String userPersonID)
    {
        this.personID = userPersonID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Users users = (Users) o;
        return userName.equals(users.userName) && password.equals(users.password) &&
                email.equals(users.email) && firstName.equals(users.firstName) &&
                lastName.equals(users.lastName) && gender.equals(users.gender) &&
                personID.equals(users.personID);
    }

}
