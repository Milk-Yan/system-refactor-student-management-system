import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractProcess {

    /**
     * Gets the output of the process specified by the subclass
     * @param inputData the list of commands to be inputted to the commandline during execution
     * @return the command line output as a string
     */
    public String getOutput(List<String> inputData) throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        // Hook method to get specific process
        ProcessBuilder pb = getProcessBuilder();
        Process p = pb.start();

        // Gets specific streams to be used
        PrintWriter stdin = new PrintWriter(p.getOutputStream());//input
        InputStream stdout = p.getInputStream(); // output and error stream

        // Requirement of sleeping thread to allow the process to execute
        Thread.sleep(200);
        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
            Thread.sleep(200);
        }

        // Wait for the program, if does not terminate on its own throw an error
        boolean timeoutBoolean = p.waitFor(2, TimeUnit.SECONDS);
        if(!timeoutBoolean){
            throw new TimeoutException(getOutputFromInputStreamWithRead(stdout));
        }

        return getOutputFromInputStreamWithRead(stdout);
    }

    /**
     * Gets the output of the process specified by the subclass. Different to getOutput in that it will
     * print the output as it is read.
     * @param inputData the list of commands to be inputted to the commandline during execution
     * @return the command line output as a string
     */
    public String getOutputDebug(List<String> inputData) throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        ProcessBuilder pb = getProcessBuilder();
        Process p = pb.start();

        PrintWriter stdin = new PrintWriter(p.getOutputStream());//input
        InputStream stdout = p.getInputStream(); // output and error stream

        StringBuilder outputBuilder = new StringBuilder();
        Thread.sleep(200);
        String currentOutput = getOutputFromInputStreamWithRead(stdout);
        System.out.println(currentOutput);
        outputBuilder.append(currentOutput);
        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
            Thread.sleep(200);
            currentOutput = getOutputFromInputStreamWithRead(stdout);
            System.out.println(currentOutput);
            outputBuilder.append(currentOutput);
        }

        boolean timeoutBoolean = p.waitFor(2, TimeUnit.SECONDS);
        if(!timeoutBoolean){
            throw new TimeoutException();
        }

        return outputBuilder.toString();
    }

    /**
     * Hook method to get the specific processbuilder that will need to be implemented by subclasses
     * @return a processbuilder that will be run in getOutput
     * @throws URISyntaxException
     */
    protected abstract ProcessBuilder getProcessBuilder() throws URISyntaxException;

    /**
     * Reads the input stream given
     * @param stdout the stream to be read
     * @return a string with the contents of the input stream
     */
    private String getOutputFromInputStreamWithRead(InputStream stdout) throws IOException {
        StringBuilder outputBuilder = new StringBuilder();
        // While thre is still information available, read from the stream
        while (stdout.available() != 0) {
            outputBuilder.append((char) stdout.read());
        }
        return outputBuilder.toString();
    }

    private String getOutputFromInputStreamWithBufferedReader(InputStream stdout) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        StringBuilder outputBuilder = new StringBuilder();
        while (br.ready()) {
            String line = br.readLine();
            outputBuilder.append(line).append("\n");
        }
        return outputBuilder.toString();
    }
}
