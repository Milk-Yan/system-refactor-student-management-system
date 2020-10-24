package testintegration;

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

public class TestIntegration extends AbstractTestProcess {
    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests case where student is added, registered to a course, then printed in the student list
     */
    @Test
    public void testAddStudentRegisterStudentPrintStudentList() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList1 = new ArrayList<>();
        inputList1.add("1"); // Add a student
        inputList1.add("1"); // Manually enter student ID
        inputList1.add("U1829394C"); // Enter student ID
        inputList1.add("Jerry"); // Enter student name
        inputList1.add("ECSE"); // Enter student department
        inputList1.add("MALE"); // Enter student gender
        inputList1.add("1"); // Enter student school year
        inputList1.add("11"); // Exit program
        compareOutputsBetweenRefactoredAndOriginal(inputList1, Thread.currentThread().getStackTrace()[1].getMethodName()+"1");

        List<String> inputList2 = new ArrayList<>();
        inputList2.add("3"); // Register student for course
        inputList2.add("-h"); // List all students
        inputList2.add("U1829394C"); // Enter student ID
        inputList2.add("ECSE"); // Enter interested department
        inputList2.add("SE2001"); // Enter course ID
        inputList2.add("1"); // Enter lecture group
        inputList2.add("1"); // Enter lab group
        inputList2.add("11"); // Exit program
        compareOutputsBetweenRefactoredAndOriginal(inputList2, Thread.currentThread().getStackTrace()[1].getMethodName()+"2");

        List<String> inputList3 = new ArrayList<>();
        // Loop through all group types to print
        for (int i = 1; i <= 2; i++) {
            inputList3.add("5"); // Print student list
            inputList3.add("SE2001"); // Enter course ID
            inputList3.add(String.valueOf(i)); // Print by specific group
        }
        inputList3.add("11"); // Exit program
        compareOutputsBetweenRefactoredAndOriginal(inputList3, Thread.currentThread().getStackTrace()[1].getMethodName()+"3");
    }

    /**
     * Tests case where exam mark is entered for student, and transcript is printed
     */
    @Test
    public void testEnterExamMarkPrintTranscript() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList1 = new ArrayList<>();
        inputList1.add("8"); // Enter exam mark
        inputList1.add("U1822843I"); // Enter student ID
        inputList1.add("SE2005"); // Enter course ID
        inputList1.add("99"); // Enter exam mark
        inputList1.add("11"); // Exit program
        compareOutputsBetweenRefactoredAndOriginal(inputList1, Thread.currentThread().getStackTrace()[1].getMethodName()+"1");

        List<String> inputList2 = new ArrayList<>();
        inputList2.add("10"); // Print course statistics
        inputList2.add("U1822843I"); // Enter student ID
        inputList2.add("11");
        compareLinesBetweenRefactoredAndOriginal(inputList2, Thread.currentThread().getStackTrace()[1].getMethodName()+"2");
    }

    /**
     * Tests case where course is added and course statistics are printed
     */
    @Test
    public void testAddCoursePrintCourseStatistics() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList1 = new ArrayList<>();
        inputList1.add("2"); // Add a course
        inputList1.add("SE1111"); // Enter course ID
        inputList1.add("Software Architecture"); // Enter course name
        inputList1.add("3"); // Enter course vacancy
        inputList1.add("3"); // Enter AU
        inputList1.add("ECSE"); // Enter course departments
        inputList1.add("CORE"); // Enter course type
        inputList1.add("1"); // Enter number of lecture groups
        inputList1.add("1"); // Enter weekly lecture hour
        inputList1.add("lec1"); // Enter name of lecture group
        inputList1.add("3"); // Enter vacancy of lecture group
        inputList1.add("0"); // Enter number of tutorial groups
        inputList1.add("0"); // Enter number of lab groups
        inputList1.add("P1234561A"); // Enter prof in charge id
        inputList1.add("2"); // Set course components later
        inputList1.add("11"); // Exit program
        compareOutputsBetweenRefactoredAndOriginal(inputList1, Thread.currentThread().getStackTrace()[1].getMethodName()+"1");

        List<String> inputList2 = new ArrayList<>();
        inputList2.add("9"); // Print course statistics
        inputList2.add("SE1111"); // Enter student ID
        inputList2.add("11"); // Exit program
        compareOutputsBetweenRefactoredAndOriginal(inputList2, Thread.currentThread().getStackTrace()[1].getMethodName()+"2");
    }
}
