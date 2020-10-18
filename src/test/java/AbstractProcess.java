import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractProcess {


    public String getOutput(List<String> inputData) throws IOException, InterruptedException, URISyntaxException, TimeoutException {
        ProcessBuilder pb = getProcessBuilder();
        Process p = pb.start();

        PrintWriter stdin = new PrintWriter(p.getOutputStream());//input
        InputStream stdout = p.getInputStream(); // output and error stream

        Thread.sleep(200);
        for (String input : inputData) {
            stdin.write(input + "\n");
            stdin.flush();
            Thread.sleep(200);
        }

        boolean timeoutBoolean = p.waitFor(2, TimeUnit.SECONDS);
        if(!timeoutBoolean){
            throw new TimeoutException(getOutputFromInputStreamWithRead(stdout));
        }

        return getOutputFromInputStreamWithRead(stdout);
    }

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


    protected abstract ProcessBuilder getProcessBuilder() throws URISyntaxException;

    private String getOutputFromInputStreamWithRead(InputStream stdout) throws IOException {
        StringBuilder outputBuilder = new StringBuilder();
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
