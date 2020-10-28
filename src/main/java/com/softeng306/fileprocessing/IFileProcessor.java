package com.softeng306.fileprocessing;

import java.io.*;
import java.util.List;

public interface IFileProcessor {

    /**
     * Write information into a file
     * @param filename          the file to write into
     * @param collectionToWrite the information to write
     * @throws IOException
     */
    void writeToFile(String filename, List<?> collectionToWrite) throws IOException;

    /**
     * Clears the file contents
     * @param filename file to clear
     */
    void clearFileContents(String filename);


}
