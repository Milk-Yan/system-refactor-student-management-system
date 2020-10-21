import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestEnterCourseAssessmentComponents extends AbstractTestProcess {

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests the general case where the user successfully sets components
     */
    @Test
    public void testEnterCourseAssessmentComponents() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("6"); // Enter components
        inputList.add("SE2006"); // Enter course ID
        inputList.add("1"); // This course has a final exam
        inputList.add("40"); // Enter final exam weightage
        inputList.add("1"); // Enter number of main components
        inputList.add("Coursework"); // Enter name of main component
        inputList.add("60"); // Enter weightage of main component
        inputList.add("2"); // Enter number of sub components
        inputList.add("Assignment"); // Enter name of sub component
        inputList.add("70"); // Enter sub component weightage
        inputList.add("Lab"); // Enter name of sub component
        inputList.add("30"); // Enter sub component weightage
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user successfully sets components without a final
     */
    @Test
    public void testEnterCourseAssessmentComponentsNoFinal() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("6"); // Enter components
        inputList.add("SE2006"); // Enter course ID
        inputList.add("2"); // This course has no final exam
        inputList.add("1"); // Enter number of main components
        inputList.add("Coursework"); // Enter name of main component
        inputList.add("100"); // Enter weightage of main component
        inputList.add("2"); // Enter number of sub components
        inputList.add("Assignment"); // Enter name of sub component
        inputList.add("70"); // Enter sub component weightage
        inputList.add("Lab"); // Enter name of sub component
        inputList.add("30"); // Enter sub component weightage
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user successfully sets components without sub components
     */
    @Test
    public void testEnterCourseAssessmentComponentsNoSubcomponents() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("6"); // Enter components
        inputList.add("SE2006"); // Enter course ID
        inputList.add("1"); // This course has a final exam
        inputList.add("40"); // Enter final exam weightage
        inputList.add("1"); // Enter number of main components
        inputList.add("Coursework"); // Enter name of main component
        inputList.add("60"); // Enter weightage of main component
        inputList.add("0"); // Enter no sub components
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user successfully sets components without sub components
     */
    @Test
    public void testEnterCourseAssessmentComponentsAlreadyExists() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("6"); // Enter components
        inputList.add("SE2007"); // Enter course ID with components already set
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user sets components while encountering every error statement
     */
    @Test
    public void testEnterCourseAssessmentComponentsErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("6"); // Enter components
        inputList.add("INVALIDCOURSE"); // Enter invalid course ID
        inputList.add("SE3005"); // Enter course ID
        inputList.add("-1"); // OOB selection
        inputList.add("3"); // OOB selection
        inputList.add("1"); // This course has a final
        inputList.add("100"); // Enter final exam weightage that is too large
        inputList.add("40"); // Enter final exam weightage

        inputList.add("-1"); // Enter OOB number of main components
        inputList.add("1"); // Enter number of main components
        inputList.add("Coursework"); // Enter name of main component
        inputList.add("-1"); // Enter OOB weightage of main component
        inputList.add("61"); // Enter OOB weightage of main component
        inputList.add("59"); // Enter weightage of main component that does not tally to 100SS
        inputList.add("0"); // Enter number of sub components

        inputList.add("Coursework"); // Renter name of main component
        inputList.add("60"); // Renter weightage of main component

        inputList.add("2"); // Enter number of sub components
        inputList.add("Assignment"); // Enter name of sub component
        inputList.add("-1"); // Enter OOB sub component weightage
        inputList.add("101"); // Enter OOB sub component weightage
        inputList.add("70"); // Enter sub component weightage
        inputList.add("Lab"); // Enter name of sub component
        inputList.add("-1"); // Enter sub component weightage
        inputList.add("31"); // Enter sub component weightage
        inputList.add("30"); // Enter sub component weightage
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests the case where the user successfully sets components while asking for help where possible
     */
    @Test
    public void testEnterCourseAssessmentComponentsWithHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("6"); // Enter components
        inputList.add("-h"); // List all course IDs
        inputList.add("SE2006"); // Enter course ID
        inputList.add("1"); // This course has a final exam
        inputList.add("40"); // Enter final exam weightage
        inputList.add("1"); // Enter number of main components
        inputList.add("Coursework"); // Enter name of main component
        inputList.add("60"); // Enter weightage of main component
        inputList.add("2"); // Enter number of sub components
        inputList.add("Assignment"); // Enter name of sub component
        inputList.add("70"); // Enter sub component weightage
        inputList.add("Lab"); // Enter name of sub component
        inputList.add("30"); // Enter sub component weightage
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }


}
