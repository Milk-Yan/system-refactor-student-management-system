import com.softeng306.main.Main;
import org.apache.commons.io.IOUtils;
import org.junit.*;

import org.apache.commons.io.FileUtils;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class TestAddStudent {
    private static AbstractProcess originalProcess;
    private static AbstractProcess refactoredProcess;

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalProcess = new OriginalProcess();
        refactoredProcess = new RefactoredProcess();
    }

    @AfterClass
    public static void teardownResources() throws IOException {
    }

    @Before
    public void setupIO() {

    }

    @After
    public void teardownIO() {
    }

    @Test
    public void test() throws IOException, InterruptedException, URISyntaxException {
        List<String> inputList = new ArrayList<>();
        inputList.add("1");
        inputList.add("1");
        inputList.add("U1829394C");
        inputList.add("Jerry");
        inputList.add("ECSE");
        inputList.add("MALE");
        inputList.add("1");
        inputList.add("11");
        String originalOutput = TestAddStudent.originalProcess.getOutput(inputList);
        String refactoredOutput = TestAddStudent.refactoredProcess.getOutput(inputList);
        System.out.println(originalOutput);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }
}
