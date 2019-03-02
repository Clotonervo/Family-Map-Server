/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package results;

/** LoadResult is the result of /load command to the user, contains either:
 * A string that is an error message or a success message
 */
public class LoadResult {

    private String message;

    // ========================== Constructor ========================================
    public LoadResult(String result)
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
