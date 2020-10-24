package testentercourseworkmark;

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

public class TestEnterCourseworkMark extends AbstractTestProcess {

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests general case where the user enters coursework mark for student
     */
    @Test
    public void testEnterCourseworkMark() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("U1800001L"); // Enter student ID
        inputList.add("SE0001"); // Enter course ID
        inputList.add("1"); // Select mark to enter
        inputList.add("99"); // Enter coursework mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests general case where the user updates coursework mark for student
     */
    @Test
    public void testUpdateCourseworkMark() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("U1822843I"); // Enter student ID
        inputList.add("SE2005"); // Enter course ID
        inputList.add("1"); // Select mark to enter
        inputList.add("99"); // Enter coursework mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }


    /**
     * Tests case where the user enters coursework mark for student while asking for help where possible
     */
    @Test
    public void testEnterCourseworkMarkStudentHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("-h"); // List all student IDs
        inputList.add("U1723456K"); // Enter student ID
        inputList.add("-h"); // List all course IDs
        inputList.add("SE0001"); // Enter course ID
        inputList.add("1"); // Select mark to enter
        inputList.add("99"); // Enter coursework mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests case where the user quits entering the coursework for a student
     */
    @Test
    public void testEnterCourseworkMarkStudentQuit() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("U1723456K"); // Enter student ID
        inputList.add("SE0001"); // Enter course ID
        inputList.add("2"); // Select mark to enter
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }


    /**
     * Tests case where the user attempts to enter mark for a student that is not registered for said course
     */
    @Test
    public void testEnterCourseworkMarkStudentNotRegistered() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("U1722744J"); // Enter student ID
        inputList.add("SE0001"); // Enter course ID
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests case where user attempts to enter mark for student while encountering every error case
     */
    @Test
    public void testEnterCourseworkMarkStudentErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("INVALIDSTUDENT"); // Enter invalid student
        inputList.add("U1722744J"); // Enter student ID
        inputList.add("INVALIDCOURSE"); // Enter invalid course
        inputList.add("SE2001"); // Enter course ID
        inputList.add("-1"); // Enter OOB selection
        inputList.add("4"); // Enter OOB selection
        inputList.add("1"); // Select mark to enter
        inputList.add("-1"); // Enter OOB mark
        inputList.add("101"); // Enter OOB mark
        inputList.add("100"); // Enter mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }


}
