import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class RefactoredProcess extends AbstractProcess {
    private File originalDataDirectory;
    private File testRefactoredDataWorkspace;

    /**
     * Constructor that initializes directories for use later as well as creating a copy of the original data files
     * for testing
     */
    public RefactoredProcess() throws URISyntaxException, IOException {
        this.originalDataDirectory = new File(TestAddStudent.class.getResource("originaldata").toURI());
        this.testRefactoredDataWorkspace = new File("testdata/refactored");
        File testRefactoredDataDirectory = new File("testdata/refactored/data");
        FileUtils.copyDirectory(originalDataDirectory, testRefactoredDataDirectory);
    }


    @Override
    protected ProcessBuilder getProcessBuilder() {
        String refactoredClassPath = new File("target/project-2-team-8-1.0-SNAPSHOT-jar-with-dependencies.jar").getAbsolutePath();
        //String mainClassName = "com.softeng306.main.Main";
        // Runs the class files in the target folder created from mavem
        // As such the compile lifecycle needs to be run before any test
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", refactoredClassPath);
        // Places the path of the process in the test directory specified such that it does not affect
        // the real data files
        pb.directory(testRefactoredDataWorkspace);
        pb.redirectErrorStream(true); // merge output and error stream
        return pb;
    }
}
