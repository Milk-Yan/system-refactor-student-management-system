import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

public abstract class AbstractProcess {


    public String getOutput(List<String> inputData) throws IOException, InterruptedException, URISyntaxException {
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
        p.waitFor();

        return getOutputFromInputStreamWithRead(stdout);
    }

    public String getOutputDebug(List<String> inputData) throws IOException, InterruptedException, URISyntaxException {
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
        p.waitFor();

        return outputBuilder.toString();
    }


    protected abstract ProcessBuilder getProcessBuilder() throws URISyntaxException;

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



}
