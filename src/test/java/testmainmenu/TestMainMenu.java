package testmainmenu;

import org.junit.Before;
import org.junit.Test;
import utils.AbstractTestProcess;
import utils.OriginalProcess;
import utils.RefactoredProcess;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestMainMenu extends AbstractTestProcess {

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests general case where user gets student transcript
     */
    @Test
    public void testPrintOptions() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("0"); // Print list of options
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests general case where user gets student transcript
     */
    @Test
    public void testMainMenuErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("-1"); // OOB option
        inputList.add("12"); // OOB option
        inputList.add("zero");
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }


}
