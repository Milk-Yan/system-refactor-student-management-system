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
    private static File originalDataDirectory;
    private static File testDirectory;
    private static File testOriginalDataWorkspace;
    private static File testRefactoredDataWorkspace;

    @BeforeClass
    public static void setupResources() throws URISyntaxException, IOException {
        originalDataDirectory = new File(TestAddStudent.class.getResource("originaldata").toURI());
        testDirectory = new File("test");

        testOriginalDataWorkspace = new File("test/original");
        File testOriginalDataDirectory = new File("test/original/data");
        FileUtils.copyDirectory(originalDataDirectory, testOriginalDataDirectory);

        testRefactoredDataWorkspace = new File("test/refactored");
        File testRefactoredDataDirectory = new File("test/refactored/data");
        FileUtils.copyDirectory(originalDataDirectory, testRefactoredDataDirectory);
    }

    @AfterClass
    public static void teardownResources() throws IOException {
        FileUtils.deleteDirectory(testDirectory);
    }

    @Before
    public void setupIO() {

    }

    @After
    public void teardownIO() {
    }

    private String getOriginalOutput(List<String> inputData) throws IOException, InterruptedException, URISyntaxException {
        String originalJarPath = new File(TestAddStudent.class.getResource("project-2-team-8.jar").toURI()).getPath();

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", originalJarPath);
        pb.directory(testOriginalDataWorkspace);
        Process p = pb.start();

        PrintWriter stdin = new PrintWriter(p.getOutputStream());//input
        pb.redirectErrorStream(true); // merge output and error stream
        InputStream stdout = p.getInputStream(); // output and error stream

        StringBuilder outputBuilder = new StringBuilder();
        Thread.sleep(100);
        outputBuilder.append(getOutputFromInputStreamWithRead(stdout));
        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
            Thread.sleep(100);
            outputBuilder.append(getOutputFromInputStreamWithRead(stdout));
        }
        p.waitFor();

        return outputBuilder.toString();
    }

    private String getOriginalOutputBR(List<String> inputData) throws IOException, InterruptedException, URISyntaxException {
        String originalJarPath = new File(TestAddStudent.class.getResource("project-2-team-8.jar").toURI()).getPath();

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", originalJarPath);
        pb.directory(testOriginalDataWorkspace);
        Process p = pb.start();

        PrintWriter stdin = new PrintWriter(p.getOutputStream()); // input
        pb.redirectErrorStream(true); // merge output and error stream
        InputStream stdout = p.getInputStream(); // output and error stream

        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
        }
        stdin.close();
        p.waitFor();
        String output = getOutputFromInputStreamWithBufferedReader(stdout);

        return output;
    }

    private String getOutputFromInputStreamWithRead(InputStream stdout) throws IOException, InterruptedException {
        StringBuilder outputBuilder = new StringBuilder();
        while (stdout.available() != 0) {
            outputBuilder.append((char) stdout.read());
        }
        return outputBuilder.toString();
    }

    private String getOutputFromInputStreamWithBufferedReader(InputStream stdout) throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        StringBuilder outputBuilder = new StringBuilder();
        while (br.ready()) {
            String line = br.readLine();
            outputBuilder.append(line + "\n");
        }
        return outputBuilder.toString();
    }

    private String getRefactoredOutput(List<String> inputData) throws IOException, InterruptedException {
        String refactoredClassPath = new File("./target/classes").toURI().getPath();
        String mainClassName = "com.softeng306.main.Main";

        ProcessBuilder pb = new ProcessBuilder("java", "-cp", refactoredClassPath, mainClassName);
        pb.directory(testRefactoredDataWorkspace);
        Process p = pb.start();

        PrintWriter stdin = new PrintWriter(p.getOutputStream());//input
        pb.redirectErrorStream(true); // merge output and error stream
        InputStream stdout = p.getInputStream(); // output and error stream

        StringBuilder outputBuilder = new StringBuilder();
        Thread.sleep(100);
        outputBuilder.append(getOutputFromInputStreamWithRead(stdout));
        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
            Thread.sleep(100);
            outputBuilder.append(getOutputFromInputStreamWithRead(stdout));
        }
        p.waitFor();

        return outputBuilder.toString();

    }

    private String getRefactoredOutputBR(List<String> inputData) throws IOException, InterruptedException {
        String refactoredClassPath = new File("./target/classes").toURI().getPath();
        String mainClassName = "com.softeng306.main.Main";

        ProcessBuilder pb = new ProcessBuilder("java", "-cp", refactoredClassPath, mainClassName);
        pb.directory(testRefactoredDataWorkspace);
        Process p = pb.start();

        PrintWriter stdin = new PrintWriter(p.getOutputStream());//input
        pb.redirectErrorStream(true); // merge output and error stream
        InputStream stdout = p.getInputStream(); // output and error stream

        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
        }
        stdin.close();
        p.waitFor();
        String output = getOutputFromInputStreamWithBufferedReader(stdout);

        return output;
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
        String originalOutput = getOriginalOutput(inputList);
        String refactoredOutput = getRefactoredOutput(inputList);
        System.out.println(refactoredOutput);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }
}
