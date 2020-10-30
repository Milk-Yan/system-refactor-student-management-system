package com.softeng306.fileprocessing;

import java.io.*;
import java.util.List;

/**
 * Interface that defines responsibilities of loading and writing to files.
 */
public interface IFileProcessor<T> {

    /**
     * Write information from a list into a file.
     *
     * @param filePath    The path to the file to write into.
     * @param listToWrite The information to write.
     * @throws IOException If the write could not occur.
     */
    void writeToFile(String filePath, List<T> listToWrite) throws IOException;

    /**
     * Clears the contents of a file.
     *
     * @param filePath The path to the file to clear.
     */
    void clearFileContents(String filePath);

    /**
     * Loads a list from a file.
     *
     * @return The list that is loaded.
     */
    List<T> loadFile();

    /**
     * Writes a new object to a file.
     *
     * @param entry The new object to write.
     */
    void writeNewEntryToFile(T entry);

    /**
     * Update the contents of a file using a list that contains
     * the updated information
     *
     * @param updatedList list that contains updated information
     */
    void updateFileContents(List<T> updatedList);
}
