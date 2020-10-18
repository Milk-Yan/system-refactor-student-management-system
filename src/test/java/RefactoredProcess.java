import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class RefactoredProcess extends AbstractProcess {
    private File originalDataDirectory;
    private File testRefactoredDataWorkspace;

    public RefactoredProcess() throws URISyntaxException, IOException {
        this.originalDataDirectory = new File(TestAddStudent.class.getResource("originaldata").toURI());
        this.testRefactoredDataWorkspace = new File("testdata/refactored");
        File testRefactoredDataDirectory = new File("testdata/refactored/data");
        FileUtils.copyDirectory(originalDataDirectory, testRefactoredDataDirectory);
    }


    @Override
    protected ProcessBuilder getProcessBuilder() {
        String refactoredClassPath = new File("./target/classes").toURI().getPath();
        String mainClassName = "com.softeng306.main.Main";

        ProcessBuilder pb = new ProcessBuilder("java", "-cp", refactoredClassPath, mainClassName);
        pb.directory(testRefactoredDataWorkspace);
        pb.redirectErrorStream(true); // merge output and error stream
        return pb;
    }
}
