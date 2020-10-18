import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class OriginalProcess extends AbstractProcess {
    private File originalDataDirectory;
    private File testOriginalDataWorkspace;

    public OriginalProcess() throws URISyntaxException, IOException {
        this.originalDataDirectory = new File(TestAddStudent.class.getResource("originaldata").toURI());
        this.testOriginalDataWorkspace = new File("testdata/original");
        File testOriginalDataDirectory = new File("testdata/original/data");
        FileUtils.copyDirectory(originalDataDirectory, testOriginalDataDirectory);
    }


    @Override
    protected ProcessBuilder getProcessBuilder() throws URISyntaxException {
        String originalJarPath = new File(TestAddStudent.class.getResource("project-2-team-8.jar").toURI()).getPath();
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", originalJarPath);
        pb.directory(testOriginalDataWorkspace);
        pb.redirectErrorStream(true); // merge output and error stream
        return pb;
    }
}
