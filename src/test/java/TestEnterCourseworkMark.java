import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestEnterCourseworkMark {
    private static AbstractProcess originalProcess;
    private static AbstractProcess refactoredProcess;

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @Test
    public void testEnterCourseworkMark() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("U1722744J"); // Enter student ID
        inputList.add("SE2005"); // Enter course ID
        inputList.add("1"); // Select mark to enter
        inputList.add("99"); // Enter coursework mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

    private void compareOutputsBetweenRefactoredAndOriginal(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = TestEnterCourseworkMark.originalProcess.getOutput(inputList);
        String refactoredOutput = TestEnterCourseworkMark.refactoredProcess.getOutput(inputList);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }

}
