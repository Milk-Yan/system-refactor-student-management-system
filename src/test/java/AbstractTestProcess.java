import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public abstract class AbstractTestProcess {
    protected static AbstractProcess originalProcess;
    protected static AbstractProcess refactoredProcess;

    /**
     * Compares output between the class files of the system generated and the jar of the original system
     * @param inputList the list of inputs to provide to both programs
     * @throws TimeoutException Indicates that the program took too long to respond most likely due to a stall
     */
    protected void compareOutputsBetweenRefactoredAndOriginal(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = AbstractTestProcess.originalProcess.getOutput(inputList);
        String refactoredOutput = AbstractTestProcess.refactoredProcess.getOutput(inputList);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }

    /**
     * Compares output between the class files of the system generated and the jar of the original system
     * Also prints the output for debugging
     * @param inputList the list of inputs to provide to both programs
     * @throws TimeoutException Indicates that the program took too long to respond most likely due to a stall
     */
    protected void compareOutputsBetweenRefactoredAndOriginalDebug(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = AbstractTestProcess.originalProcess.getOutputDebug(inputList);
        String refactoredOutput = AbstractTestProcess.refactoredProcess.getOutput(inputList);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }

    /**
     * Compares lines between the class files of the system generated and the jar of the original system
     * Ensures every line of information in the original jar is in the new class files
     * @param inputList the list of inputs to provide to both programs
     * @throws TimeoutException Indicates that the program took too long to respond most likely due to a stall
     */
    protected void compareLinesBetweenRefactoredAndOriginal(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = AbstractTestProcess.originalProcess.getOutput(inputList);
        String refactoredOutput = AbstractTestProcess.refactoredProcess.getOutput(inputList);

        String[] originalOutputList = originalOutput.split("\n");
        Assert.assertEquals(originalOutput.length(), refactoredOutput.length());
        for(String originalLine : originalOutputList) {
            Assert.assertTrue(refactoredOutput.contains(originalLine));
        }
    }


}
