import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestEnterExamMark extends AbstractTestProcess {

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests general case where the user updates exam mark for student
     */
    @Test
    public void testEnterExamMark() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("8"); // Enter exam mark
        inputList.add("U1800001L"); // Enter student ID
        inputList.add("SE0001"); // Enter course ID
        inputList.add("99"); // Enter exam mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests general case where the user updates exam mark for student
     */
    @Test
    public void testUpdateExamMark() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("8"); // Enter exam mark
        inputList.add("U1822843I"); // Enter student ID
        inputList.add("SE2005"); // Enter course ID
        inputList.add("99"); // Enter exam mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }


    /**
     * Tests case where the user enters exam mark for student while asking for help where possible
     */
    @Test
    public void testEnterExamMarkStudentHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("8"); // Enter exam mark
        inputList.add("-h"); // List all student IDs
        inputList.add("U1723456K"); // Enter student ID
        inputList.add("-h"); // List all course IDs
        inputList.add("SE0001"); // Enter course ID
        inputList.add("99"); // Enter exam mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }


    /**
     * Tests case where the user attempts to enter mark for a student that is not registered for said course
     */
    @Test
    public void testEnterExamMarkStudentNotRegistered() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("8"); // Enter exam mark
        inputList.add("U1722744J"); // Enter student ID
        inputList.add("SE0001"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user attempts to enter mark for student while encountering every error case
     */
    @Test
    public void testEnterExamMarkStudentErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("8"); // Enter exam mark
        inputList.add("INVALIDSTUDENT"); // Enter invalid student
        inputList.add("U1722744J"); // Enter student ID
        inputList.add("INVALIDCOURSE"); // Enter invalid course
        inputList.add("SE2001"); // Enter course ID
        inputList.add("-1"); // Enter OOB mark
        inputList.add("101"); // Enter OOB mark
        inputList.add("100"); // Enter mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user attempts to enter mark for student while encountering every error case
     * Would prefer for this test case be set up in the data files automatically
     */
    @Test
    public void testEnterNonExistentExamMarkStudent() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        // Need to set coursework first
        inputList.add("6"); // Enter components
        inputList.add("SE3005"); // Enter course ID
        inputList.add("2"); // This course has no final exam
        inputList.add("1"); // Enter number of main components
        inputList.add("Coursework"); // Enter name of main component
        inputList.add("100"); // Enter weightage of main component
        inputList.add("2"); // Enter number of sub components
        inputList.add("Assignment"); // Enter name of sub component
        inputList.add("70"); // Enter sub component weightage
        inputList.add("Lab"); // Enter name of sub component
        inputList.add("30"); // Enter sub component weightage


        // Need to register student for course
        inputList.add("3"); // Register student for course
        inputList.add("U1734756J"); // Enter student ID
        inputList.add("ECSE"); // Enter interested department
        inputList.add("SE3005"); // Enter course ID
        inputList.add("1"); // Enter lecture group
        inputList.add("1"); // Enter lab group

        // Enter the non-existent exam mark
        inputList.add("8"); // Enter exam mark
        inputList.add("U1734756J"); // Enter student ID
        inputList.add("SE3005"); // Enter course ID
        inputList.add("100"); // Enter mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }
}
