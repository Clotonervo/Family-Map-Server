/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package handlers;

/** InStreamToString is a utility class that converts an InputStream to a string */
public class InStreamToString {

    //_______________________________ Converts an OutStream to a String __________________________________________
    /** convertStreamToString converts an InputStream to a string
     * @param inputStream an InputStream that needs converting into a String
     * @return String that is the converted InputStream
     */
    public String convertStreamToString(java.io.InputStream inputStream)
    {
        java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
