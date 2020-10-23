package utils;

import org.junit.Assert;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public abstract class AbstractTestProcess {
    protected static AbstractProcess originalProcess;
    protected static AbstractProcess refactoredProcess;

    /**
     * Compares output between the class files of the system generated and the jar of the original system
     * @param inputList  the list of inputs to provide to both programs
     * @param methodName the name of the method so that the output txt file can be located
     * @throws TimeoutException Indicates that the program took too long to respond most likely due to a stall
     */
    protected void compareOutputsBetweenRefactoredAndOriginal(List<String> inputList, String methodName) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = getOriginalOutputFromFile(methodName);
        String refactoredOutput = AbstractTestProcess.refactoredProcess.getOutput(inputList);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }

    /**
     * Compares lines between the class files of the system generated and the jar of the original system
     * Ensures every line of information in the original jar is in the new class files
     * @param inputList the list of inputs to provide to both programs
     * @param methodName the name of the method to locate the output txt
     * @throws TimeoutException Indicates that the program took too long to respond most likely due to a stall
     */
    protected void compareLinesBetweenRefactoredAndOriginal(List<String> inputList, String methodName) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = getOriginalOutputFromFile(methodName);
        String refactoredOutput = AbstractTestProcess.refactoredProcess.getOutput(inputList);

        String[] originalOutputList = originalOutput.split("\n");
        Assert.assertEquals(originalOutput.length(), refactoredOutput.length());
        for (String originalLine : originalOutputList) {
            Assert.assertTrue(refactoredOutput.contains(originalLine));
        }
    }

    /**
     * Gets the output string from the specified txt file dictated by the method name
     * @param methodName to get the txt file
     * @return the output string
     */
    protected String getOriginalOutputFromFile(String methodName) throws URISyntaxException, IOException {
        URI fileURI = this.getClass().getResource(methodName + ".txt").toURI();
        File file = new File(fileURI);

        InputStream filestream = new FileInputStream(file);

        StringBuilder outputBuilder = new StringBuilder();
        // While thre is still information available, read from the stream
        while (filestream.available() != 0) {
            outputBuilder.append((char) filestream.read());
        }
        return outputBuilder.toString();
    }


    /**
     * Compares output between the class files of the system generated and the jar of the original system
     * Output obtained through running the jar directly
     * Also prints the output for debugging
     * @param inputList the list of inputs to provide to both programs
     * @throws TimeoutException Indicates that the program took too long to respond most likely due to a stall
     */
    protected void compareOutputsBetweenRefactoredAndOriginalDebug(List<String> inputList) throws InterruptedException, IOException, URISyntaxException, TimeoutException {
        String originalOutput = AbstractTestProcess.originalProcess.getOutputDebug(inputList);
        String refactoredOutput = AbstractTestProcess.refactoredProcess.getOutput(inputList);
        Assert.assertEquals(originalOutput, refactoredOutput);
    }


    /**
     * Writes the specified output to the specified output folder dictated by the method name
     * The file written will be in the format <methodname>.txt
     * @param output string to write
     * @param methodName file to write to with name of method
     */
    protected void writeJarOutputToFile(String output, String methodName) throws InterruptedException, URISyntaxException, TimeoutException, IOException {
        try {
            File originalOutputDataDirectory = new File("originaloutput");
            originalOutputDataDirectory.mkdirs();
            FileWriter writer = new FileWriter("originaloutput/" + methodName + ".txt");
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred with writing the file.");
            e.printStackTrace();
        }
    }

}
