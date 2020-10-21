import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class TestAddStudent extends AbstractTestProcess{

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests general case where user creates a student
     */
    @Test
    public void testAddStudent() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("1"); // Add a student
        inputList.add("1"); // Manually enter student ID
        inputList.add("U1829394C"); // Enter student ID
        inputList.add("Jerry"); // Enter student name
        inputList.add("ECSE"); // Enter student department
        inputList.add("MALE"); // Enter student gender
        inputList.add("1"); // Enter student school year
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user creates student while asking for help where possible
     */
    @Test
    public void testAddStudentWithHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("1"); // Add a student
        inputList.add("1"); // Manually enter student ID
        inputList.add("U1829394D"); // Enter student ID
        inputList.add("Gerald"); // Enter student name
        inputList.add("-h"); // List all departments
        inputList.add("ECSE"); // Enter student department
        inputList.add("-h"); // List all genders
        inputList.add("MALE"); // Enter student gender
        inputList.add("1"); // Enter student school year
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user creates student while asking for help where possible
     */
    @Test
    public void testAddStudentErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("1"); // Add a student
        inputList.add("1"); // Manually enter student ID
        inputList.add("INVALIDID"); // Enter invalid student ID
        inputList.add("U1829394E"); // Enter student ID
        inputList.add("21"); // Enter invalid student name
        inputList.add("Gerald"); // Enter student name
        inputList.add("INVALIDDEPARTMENT"); // Enter invalid department
        inputList.add("ECSE"); // Enter student department
        inputList.add("INVALIDGENDER"); // Enter invalid gender
        inputList.add("MALE"); // Enter student gender
        inputList.add("-1"); // Enter OOB student school year
        inputList.add("5"); // Enter OOB student school year
        inputList.add("1"); // Enter student school year
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user creates student while generating student automatically
     */
    @Test
    public void testAddStudentAutomatically() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("1"); // Add a student
        inputList.add("2"); // Auto generate student ID
        inputList.add("Geraldine"); // Enter student name
        inputList.add("CS"); // Enter student department
        inputList.add("MALE"); // Enter student gender
        inputList.add("-1"); // Enter OOB student school year
        inputList.add("5"); // Enter OOB student school year
        inputList.add("1"); // Enter student school year
        inputList.add("11"); // Exit program

        // Assert that the student is created and printed
        Assert.assertTrue(refactoredProcess.getOutput(inputList).contains("| Geraldine | CS | MALE | 1 | not available"));

        // Need to replace both data files as the data could be different
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }
}
