/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package results;

/** ClearResult is the result of the clear command to the user, contains:
 * A string with either an error message or success message
 */
public class ClearResult {

    private String result;

    // ========================== Constructor ========================================
    public ClearResult(String result)
    {
        this.result = result;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
