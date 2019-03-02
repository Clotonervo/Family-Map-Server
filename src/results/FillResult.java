/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package results;

/** FillResult contains the result of the /fill command, either
 * a string that contains either an error message or success message
 */
public class FillResult {

    private String message;

    // ========================== Constructor ========================================
    public FillResult(String result)
    {
        this.message = result;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getResult()
    {
        return message;
    }

    public void setResult(String result)
    {
        this.message = result;
    }
}
