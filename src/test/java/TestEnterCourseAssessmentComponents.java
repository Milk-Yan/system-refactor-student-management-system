import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestEnterCourseAssessmentComponents extends AbstractTestProcess{

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @Test
    public void testEnterCourseAssessmentComponents() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("6"); // Enter components
        inputList.add("SE2006"); // Enter course ID
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

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

}
