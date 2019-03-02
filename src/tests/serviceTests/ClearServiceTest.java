/* For my brackets, I use a different style with methods than I do with any of the other brackets.
I talked to Professor Rodham and he approved this as long as I was consistent */


package tests.serviceTests;

import org.junit.Assert;
import org.junit.Test;
import results.ClearResult;
import services.ClearService;

public class ClearServiceTest {

    @Test
    public void clearDbTest() //Clearing the database with service method
    {
        ClearService clearService = new ClearService();
        ClearResult clearResult = clearService.clearDb();

        Assert.assertTrue(clearResult.getResult().equals("Clear succeeded."));
    }
}