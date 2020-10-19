import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestPrintStudentTranscript {
    private static AbstractProcess originalProcess;
    private static AbstractProcess refactoredProcess;

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @Test
    public void testPrintStudentTranscript() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("10"); // Print course statistics
        inputList.add("U1234567L"); // Enter course ID
        inputList.add("11"); // Exit program

        compareLinesBetweenRefactoredAndOriginal(inputList);
    }

    private void compareOutputsBetweenRefactoredAndOriginal(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = TestPrintStudentTranscript.originalProcess.getOutput(inputList);
        String refactoredOutput = TestPrintStudentTranscript.refactoredProcess.getOutput(inputList);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }

    private void compareLinesBetweenRefactoredAndOriginal(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = TestPrintStudentTranscript.originalProcess.getOutput(inputList);
        String refactoredOutput = TestPrintStudentTranscript.refactoredProcess.getOutput(inputList);

        String[] originalOutputList = originalOutput.split("\n");
        Assert.assertEquals(originalOutput.length(), refactoredOutput.length());
        for(String originalLine : originalOutputList) {
            Assert.assertTrue(refactoredOutput.contains(originalLine));
        }
    }
}
