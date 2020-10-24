package testregisterstudentforcourse;

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

public class TestRegisterStudentForCourse extends AbstractTestProcess {

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests the general case where user successfully registers a student to a course
     */
    @Test
    public void testRegisterStudentForCourse() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("3"); // Register student for course
        inputList.add("U1734756J"); // Enter student ID
        inputList.add("ECSE"); // Enter interested department
        inputList.add("SE2001"); // Enter course ID
        inputList.add("1"); // Enter lecture group
        inputList.add("1"); // Enter lab group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests the case where user encounters every error statement
     */
    @Test
    public void testRegisterStudentForCourseErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("3"); // Register student for course
        inputList.add("INVALIDID"); // Enter invalid student ID
        inputList.add("U1822832L"); // Enter student ID
        inputList.add("INVALIDDEPARTMENT"); // Enter invalid department
        inputList.add("ECSE"); // Enter interested department
        inputList.add("INVALIDCOURSE"); // Enter invalid course ID
        inputList.add("SE2001"); // Enter course ID
        inputList.add("-1"); // Enter OOB lecture group
        inputList.add("3"); // Enter OOB lecture group
        inputList.add("1"); // Enter lecture group
        inputList.add("-1"); // Enter OOB tutorial group
        inputList.add("3"); // Enter OOB tutorial group
        inputList.add("1"); // Enter lab group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests the case where user asks for help where possible
     */
    @Test
    public void testRegisterStudentForCourseWithHelp() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("3"); // Register student for course
        inputList.add("-h"); // List all student IDs
        inputList.add("U1723456K"); // Enter student ID
        inputList.add("-h"); // List all departments
        inputList.add("ECSE"); // Enter interested department
        inputList.add("-h"); // List all course IDs
        inputList.add("SE2001"); // Enter course ID
        inputList.add("1"); // Enter lecture group
        inputList.add("1"); // Enter lab group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests the case where user registers for an unfinished course
     */
    @Test
    public void testRegisterStudentForUnfinishedCourse() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("3"); // Register student for course
        inputList.add("U1734756J"); // Enter student ID
        inputList.add("ECSE"); // Enter interested department
        inputList.add("SE2006"); // Enter course ID for course that has not been set up
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests the case where student registers course that they have already registered for
     */
    @Test
    public void testRegisterStudentForAlreadyRegisteredCourse() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("3"); // Register student for course
        inputList.add("U1722744J"); // Enter student ID
        inputList.add("ECSE"); // Enter interested department
        inputList.add("SE2001"); // Enter course ID for course that has not been set up
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

}
