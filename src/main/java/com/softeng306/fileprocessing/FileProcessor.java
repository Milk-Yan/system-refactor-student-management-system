package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

public abstract class FileProcessor implements IFileProcessor {

    /**
     * Write information into a file
     *
     * @param filename          the file to write into
     * @param collectionToWrite the information to write
     * @throws IOException
     */
    @Override
    public void writeToFile(String filename, List<?> collectionToWrite) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File file = Paths.get(filename).toFile();
        FileWriter fileWriter = new FileWriter(file, true);

        clearFileContents(filename);
        objectMapper.writeValue(fileWriter, collectionToWrite);
    }

    /**
     * Clears the file contents
     * @param filename file to clear
     */
    @Override
    public void clearFileContents(String filename) {
        try {
            new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            // don't do anything
        }
    }

}
