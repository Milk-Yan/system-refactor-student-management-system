import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TestEnterCourseworkMark extends AbstractTestProcess{

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @Test
    public void testEnterCourseworkMark() throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        List<String> inputList = new ArrayList<>();
        inputList.add("7"); // Enter coursework mark
        inputList.add("U1722744J"); // Enter student ID
        inputList.add("SE2005"); // Enter course ID
        inputList.add("1"); // Select mark to enter
        inputList.add("99"); // Enter coursework mark
        inputList.add("11"); // Exit program

        compareOutputsBetweenRefactoredAndOriginal(inputList);
    }

}
