package testaddcourse;

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


public class TestAddCourse extends AbstractTestProcess {

    @Before
    public void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    /**
     * Tests the general case where user creates a course without setting course components
     */
    @Test
    public void testAddCourse() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("2"); // Add a course
        inputList.add("SE1111"); // Enter course ID
        inputList.add("Software Architecture"); // Enter course name
        inputList.add("3"); // Enter course vacancy
        inputList.add("3"); // Enter AU
        inputList.add("ECSE"); // Enter course departments
        inputList.add("CORE"); // Enter course type
        inputList.add("1"); // Enter number of lecture groups
        inputList.add("1"); // Enter weekly lecture hour
        inputList.add("lec1"); // Enter name of lecture group
        inputList.add("3"); // Enter vacancy of lecture group
        inputList.add("0"); // Enter number of tutorial groups
        inputList.add("0"); // Enter number of lab groups
        inputList.add("P1234561A"); // Enter prof in charge id
        inputList.add("2"); // Set course components later
        inputList.add("11"); // Exit program

        compareLinesBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests the case where user asks for help where available
     */
    @Test
    public void testAddCourseWithHelpOptions() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("2"); // Add a course
        inputList.add("SE2222"); // Enter course ID
        inputList.add("Software Architecture 2"); // Enter course name
        inputList.add("3"); // Enter course vacancy
        inputList.add("3"); // Enter AU
        inputList.add("-h"); // List course departments
        inputList.add("CS"); // Enter course departments
        inputList.add("-h"); // List course types
        inputList.add("CORE"); // Enter course type
        inputList.add("1"); // Enter number of lecture groups
        inputList.add("1"); // Enter weekly lecture hour
        inputList.add("lec1"); // Enter name of lecture group
        inputList.add("3"); // Enter vacancy of lecture group
        inputList.add("0"); // Enter number of tutorial groups
        inputList.add("0"); // Enter number of lab groups
        inputList.add("-h"); // List professor ids
        inputList.add("P1234567J"); // Enter prof in charge id
        inputList.add("2"); // Set course components later
        inputList.add("11"); // Exit program

        compareLinesBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests the case where user encounters every error possible
     */
    @Test
    public void testAddCourseErrors() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("2"); // Add a course
        inputList.add("SEEDEES"); // Enter incorrect course ID format
        inputList.add("SE3333"); // Enter course ID
        inputList.add("Software Architecture 3"); // Enter course name
        inputList.add("-1"); // Enter invalid vacancy
        inputList.add("three"); // Enter non-integer vacancy
        inputList.add("3"); // Enter course vacancy
        inputList.add("-1"); // Enter out of bound AU
        inputList.add("11"); // Enter out of bound AU
        inputList.add("three"); // Enter non-integer AU
        inputList.add("3"); // Enter AU
        inputList.add("CD"); // Enter invalid course department
        inputList.add("CS"); // Enter course department
        inputList.add("MORE"); // Enter invalid course type
        inputList.add("CORE"); // Enter course type

        inputList.add("0"); // Enter invalid number of lecture groups
        inputList.add("4"); // Enter invalid number of lecture groups
        inputList.add("one"); // Enter non-integer format of lecture groups
        inputList.add("1"); // Enter number of lecture groups
        inputList.add("-1"); // Enter invalid weekly lecture hour
        inputList.add("4"); // Enter invalid weekly lecture hour
        inputList.add("one"); // Enter non-integer format weekly lecture hour
        inputList.add("1"); // Enter weekly lecture hour
        inputList.add("???"); // Enter invalid group name
        inputList.add("lec1"); // Enter name of lecture group
        inputList.add("-1"); // Enter invalid capacity of lecture group
        inputList.add("three"); // Enter non-integer format capacity of lecture group
        inputList.add("4"); // Enter invalid capacity of lecture group
        inputList.add("2"); // Enter invalid capacity of lecture group
        inputList.add("3"); // Enter capacity of lecture group

        inputList.add("-1"); // Enter invalid number of lab groups
        inputList.add("one"); // Enter non-integer format of lab groups
        inputList.add("1"); // Enter number of lab groups
        inputList.add("-1"); // Enter invalid weekly lab hour
        inputList.add("4"); // Enter invalid weekly lab hour
        inputList.add("one"); // Enter non-integer format weekly lab hour
        inputList.add("1"); // Enter weekly lab hour
        inputList.add("???"); // Enter invalid group name
        inputList.add("lab1"); // Enter name of lab group
        inputList.add("-1"); // Enter invalid capacity of lab group
        inputList.add("3"); // Enter capacity of lab group

        inputList.add("-1"); // Enter invalid number of tutorial groups
        inputList.add("one"); // Enter non-integer format of tutorial groups
        inputList.add("1"); // Enter number of tutorial groups
        inputList.add("-1"); // Enter invalid weekly tutorial hour
        inputList.add("4"); // Enter invalid weekly tutorial hour
        inputList.add("one"); // Enter non-integer format weekly tutorial hour
        inputList.add("1"); // Enter weekly tutorial hour
        inputList.add("???"); // Enter invalid group name
        inputList.add("tut1"); // Enter name of tutorial group
        inputList.add("-1"); // Enter invalid capacity of tutorial group
        inputList.add("3"); // Enter capacity of tutorial group

        inputList.add("PDONTEXIST"); // Enter invalid prof in charge id
        inputList.add("P1234567J"); // Enter prof in charge id

        inputList.add("3"); // Invalid choice
        inputList.add("2"); // Set course components later
        inputList.add("11"); // Exit program

        compareLinesBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * Tests the case where user creates course as well as setting course components
     */
    @Test
    public void testAddCourseWithWeightings() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("2"); // Add a course
        inputList.add("SE5555"); // Enter course ID
        inputList.add("Software Architecture 5"); // Enter course name
        inputList.add("3"); // Enter course vacancy
        inputList.add("3"); // Enter AU
        inputList.add("ECSE"); // Enter course departments
        inputList.add("CORE"); // Enter course type
        inputList.add("1"); // Enter number of lecture groups
        inputList.add("1"); // Enter weekly lecture hour
        inputList.add("lec1"); // Enter name of lecture group
        inputList.add("3"); // Enter vacancy of lecture group
        inputList.add("0"); // Enter number of tutorial groups
        inputList.add("0"); // Enter number of lab groups
        inputList.add("P1234561A"); // Enter prof in charge id
        inputList.add("1"); // Set course components now

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

        compareLinesBetweenRefactoredAndOriginal(inputList, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

}
