import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class TestAddCourse extends AbstractTestProcess{

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

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

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

}
