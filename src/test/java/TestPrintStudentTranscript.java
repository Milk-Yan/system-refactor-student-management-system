import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestPrintStudentTranscript extends AbstractTestProcess {

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests general case where user gets student transcript
     */
    @Test
    public void testPrintStudentTranscript() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("10"); // Print course statistics
        inputList.add("U1234567L"); // Enter course ID
        inputList.add("11"); // Exit program

        // Compare output lines as original system uses HashMap#entrySet() which is pseudorandom
        compareLinesBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user gets student transcript with help
     */
    @Test
    public void testPrintStudentTranscriptWithHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("10"); // Print course statistics
        inputList.add("-h"); // List all course statistics
        inputList.add("U1234567L"); // Enter course ID
        inputList.add("11"); // Exit program

        // Compare output lines as original system uses HashMap#entrySet() which is pseudorandom
        compareLinesBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user gets student transcript encountering every error statements
     */
    @Test
    public void testPrintStudentTranscriptErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("10"); // Print course statistics
        inputList.add("INVALIDSTUDENT"); // Enter invalid course
        inputList.add("U1234567L"); // Enter course ID
        inputList.add("11"); // Exit program

        // Compare output lines as original system uses HashMap#entrySet() which is pseudorandom
        compareLinesBetweenRefactoredAndOriginal(inputList);
    }

}
