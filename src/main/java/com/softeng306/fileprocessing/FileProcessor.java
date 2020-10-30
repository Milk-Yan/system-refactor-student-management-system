package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

/**
 * Abstract implementation of a file processor for a given domain type.
 *
 * @param <T> The domain type that this file processor deals with.
 */
public abstract class FileProcessor<T> implements IFileProcessor<T> {

    /**
     * {@inheritDoc} Writes into a JSON file.
     */
    @Override
    public void writeToFile(String filePath, List<T> listToWrite) throws IOException {
        // The serialization feature allows the json to be formatted such that it is easier for
        // a human to decipher.
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File file = Paths.get(filePath).toFile();
        FileWriter fileWriter = new FileWriter(file, true);

        clearFileContents(filePath);
        objectMapper.writeValue(fileWriter, listToWrite);
    }

    /**
     * Clears the contents of a file.
     *
     * @param filePath The path to the file to clear.
     */
    @Override
    public void clearFileContents(String filePath) {
        try (PrintWriter ignored = new PrintWriter(filePath)) {
        } catch (FileNotFoundException e) {
            // don't do anything because it is ok if we don't clear a file
            // that doesn't exist.
        }
    }
    
}
