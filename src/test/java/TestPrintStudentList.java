import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestPrintStudentList {
    private static AbstractProcess originalProcess;
    private static AbstractProcess refactoredProcess;

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @Test
    public void testPrintStudentListByLectureGroup() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("5"); // Print student list
        inputList.add("SE2005"); // Enter course ID
        inputList.add("1"); // Print by lecture group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    @Test
    public void testPrintStudentListByTutorialGroup() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("5"); // Print student list
        inputList.add("SE2005"); // Enter course ID
        inputList.add("2"); // Print by tutorial group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    @Test
    public void testPrintStudentListByLabGroup() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("5"); // Print student list
        inputList.add("SE2005"); // Enter course ID
        inputList.add("3"); // Print by lab group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    private void compareOutputsBetweenRefactoredAndOriginal(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = TestPrintStudentList.originalProcess.getOutput(inputList);
        String refactoredOutput = TestPrintStudentList.refactoredProcess.getOutput(inputList);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }

}
