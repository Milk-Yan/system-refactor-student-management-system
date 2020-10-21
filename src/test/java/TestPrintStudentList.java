import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestPrintStudentList extends AbstractTestProcess {

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests the general cases where the user prints the student list by lecture, tutorial and lab groups
     */
    @Test
    public void testPrintStudentList() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        for (int i = 1; i <= 3; i++) {
            List<String> inputList = new ArrayList<>();
            inputList.add("5"); // Print student list
            inputList.add("SE2005"); // Enter course ID
            inputList.add(String.valueOf(i)); // Print by specific group
            inputList.add("11"); // Exit program

            compareOutputsBetweenRefactoredAndOriginal(inputList);
        }
    }

    /**
     * Tests the case where the user prints the student list by tutorial group when it doesnt exist
     */
    @Test
    public void testPrintStudentListNoTutorials() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("5"); // Print student list
        inputList.add("SE0001"); // Enter course ID
        inputList.add("2"); // Print by non existent tutorial group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user prints the student list by lab group when it doesnt exist
     */
    @Test
    public void testPrintStudentListNoLabs() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("5"); // Print student list
        inputList.add("SE0001"); // Enter course ID
        inputList.add("3"); // Print by non existent lab group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user prints the student list while encountering all errors
     */
    @Test
    public void testPrintStudentListErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("5"); // Print student list
        inputList.add("INVALIDCOURSE"); // Enter invalid course ID
        inputList.add("SE2005"); // Enter course ID
        inputList.add("-1"); // Print by OOB group
        inputList.add("4"); // Print by OOB group
        inputList.add("1"); // Print by lecture group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user prints the student list while asking for help where possible
     */
    @Test
    public void testPrintStudentListErrorsWithHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("5"); // Print student list
        inputList.add("-h"); // List all course IDs
        inputList.add("SE2005"); // Enter course ID
        inputList.add("1"); // Print by lecture group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }


}
