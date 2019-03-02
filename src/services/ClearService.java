/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package services;

import dao.DataBaseManager;
import results.ClearResult;
import java.sql.SQLException;

/** ClearService contains methods for clearing the database */
public class ClearService {

//______________________________________ Clear Service Function _________________________________________________
    /** clearDb uses DAOs to clear the whole database
     * @return ClearResult object that contains either an error message or success message
     */
    public ClearResult clearDb()
    {
        DataBaseManager dbManager = new DataBaseManager();
        try {
            if (dbManager.clearTables()) {
                return new ClearResult("Clear succeeded.");
            }
            else {
                return new ClearResult("Error with clearing the Database");
            }
        }
        catch (SQLException databaseError) {
            return new ClearResult(databaseError.toString());
        }
    }

}
