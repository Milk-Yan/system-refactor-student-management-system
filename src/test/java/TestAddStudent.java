import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class TestAddStudent extends AbstractTestProcess{

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @Test
    public void testAddStudent() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("1"); // Add a student
        inputList.add("1"); // Manually enter student ID
        inputList.add("U1829394C"); // Enter student ID
        inputList.add("Jerry"); // Enter student name
        inputList.add("ECSE"); // Enter student department
        inputList.add("MALE"); // Enter student gender
        inputList.add("1"); // Enter student school year
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

}
