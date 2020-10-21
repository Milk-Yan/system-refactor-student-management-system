import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestPrintCourseStatistics extends AbstractTestProcess {

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests general case where user gets course statistics
     */
    @Test
    public void testPrintCourseStatistics() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("9"); // Print course statistics
        inputList.add("SE2005"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user gets course statistics with help
     */
    @Test
    public void testPrintCourseStatisticsWithHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("9"); // Print course statistics
        inputList.add("-h"); // List all course statistics
        inputList.add("SE2005"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    /**
     * Tests case where user gets course statistics encountering every error statements
     */
    @Test
    public void testPrintCourseStatisticsErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("9"); // Print course statistics
        inputList.add("INVALIDCOURSE"); // Enter invalid course
        inputList.add("SE2005"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

}
