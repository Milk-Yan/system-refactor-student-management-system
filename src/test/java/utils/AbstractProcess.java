package utils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractProcess {
    protected static final int PROCESS_SLEEP_MILLI = 800;
    protected static final int WAITFOR_SEC = 5;

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
        StringBuilder outputBuilder = new StringBuilder();
        Thread.sleep(PROCESS_SLEEP_MILLI);

        outputBuilder.append(getOutputFromInputStreamWithRead(stdout));
        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
            Thread.sleep(PROCESS_SLEEP_MILLI);
            outputBuilder.append(getOutputFromInputStreamWithRead(stdout));
        }

        // Wait for the program, if does not terminate on its own throw an error
        boolean timeoutBoolean = p.waitFor(WAITFOR_SEC, TimeUnit.SECONDS);
        if(!timeoutBoolean){
            System.err.println(outputBuilder.toString());
            throw new TimeoutException(getOutputFromInputStreamWithRead(stdout));
        }

        // Final read in the case that the program generated output during wait
        outputBuilder.append(getOutputFromInputStreamWithRead(stdout));

        // Remove carriage returns
        return outputBuilder.toString().replaceAll("\r", "");
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
        Thread.sleep(PROCESS_SLEEP_MILLI);

        String currentOutput = getOutputFromInputStreamWithRead(stdout);
        System.out.println(currentOutput);
        outputBuilder.append(currentOutput);
        for (String input : inputData) {
            System.out.println(input);
            stdin.write(input + "\n");
            stdin.flush();
            Thread.sleep(PROCESS_SLEEP_MILLI);
            
            currentOutput = getOutputFromInputStreamWithRead(stdout);
            System.out.println(currentOutput);
            outputBuilder.append(currentOutput);
        }

        boolean timeoutBoolean = p.waitFor(WAITFOR_SEC, TimeUnit.SECONDS);
        if(!timeoutBoolean){
            throw new TimeoutException();
        }

        // Final read in the case that the program generated output during wait
        outputBuilder.append(getOutputFromInputStreamWithRead(stdout));

        // Remove carriage returns
        return outputBuilder.toString().replaceAll("\r", "");
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
            char c = (char) stdout.read();
            // There is a problem reading \u2013 for inputstream from processbuilder, so need to replace with '?'
            if(c=='\u0096' || c=='\u00E2') {
                c='?';
            }
            if(c=='\u0080' || c=='\u0093') {
                // do nothing
                continue;
            }
            outputBuilder.append(c);
        }
        return outputBuilder.toString();
    }
}
