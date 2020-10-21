import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestCheckAvailableSlotsInClass extends AbstractTestProcess{

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests the general case of user checking slots in class successfully
     */
    @Test
    public void testCheckAvailableSlotsInClass() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("4"); // Check available slots in class
        inputList.add("SE2001"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case of user checking slots in class while encountering every error statement
     */
    @Test
    public void testCheckAvailableSlotsInClassErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("4"); // Check available slots in class
        inputList.add("INVALIDCOURSEID"); // Enter invalid course ID
        inputList.add("SE2001"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }


    /**
     * Tests the case of user checking slots in class while asking for help where possible
     */
    @Test
    public void testCheckAvailableSlotsInClassWithHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("4"); // Check available slots in class
        inputList.add("-h"); // List all course IDs
        inputList.add("SE2001"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

}
