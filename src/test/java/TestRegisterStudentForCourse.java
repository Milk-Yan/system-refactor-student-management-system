import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestRegisterStudentForCourse extends AbstractTestProcess {

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @Test
    public void testRegisterStudentForCourse() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("3"); // Register student for course
        inputList.add("U1734756J"); // Enter student ID
        inputList.add("ECSE"); // Enter interested department
        inputList.add("CS"); // Enter interested department
        inputList.add("SE2001"); // Enter course ID
        inputList.add("1"); // Enter lecture group
        inputList.add("1"); // Enter lab group
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

}
